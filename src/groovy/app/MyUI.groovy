package app


import java.awt.Event;

import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.grails.Grails;
import app.widget.GeneralFunction;

import stockcontrol.UserService;
import stockcontrol.ShiroUser;
import app.widget.GeneralFunction

import com.vaadin.event.ItemClickEvent
import com.vaadin.event.ItemClickEvent.ItemClickListener
import com.vaadin.navigator.Navigator
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.DefaultErrorHandler
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponent
import com.vaadin.ui.Button
import com.vaadin.ui.FormLayout
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.MenuBar
import com.vaadin.ui.Notification
import com.vaadin.ui.Panel
import com.vaadin.ui.PasswordField
import com.vaadin.ui.TextField
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Window
import com.vaadin.ui.MenuBar.MenuItem

import com.vaadin.annotations.Theme;

//@Theme("StockControl")
public class MyUI extends UI{
	Navigator navigator
	
	public String UserName = "";
	
	def generalFunction = new GeneralFunction()
	def windowChgPass
	def textId
	def textName
	def textOldPassword
	def textNewPassword
	def textConfirmPassword
	
	public class MainView extends VerticalLayout implements View {
		Panel panel
		MenuBar menuBar
		MenuItem userMenu
		
		@Autowired
		SettingUser masterUser = new SettingUser()
		
		@Autowired
		SettingRole masterRole = new SettingRole()
		
		public MainView() {
			setSizeFull()
			
			// Layout with menu on left and view area on right
			HorizontalLayout hLayout = new HorizontalLayout();
			hLayout.setSizeFull();
			
			// Menu on top of the screen
			menuBar = new MenuBar()
			menuBar.setWidth("100%")
			userMenu =  menuBar.addItem(UserName, null)
			userMenu.setStyleName("menuRight");
			userMenu.addItem("Change Password", new MenuBar.Command(){
				public void menuSelected(MenuItem selectedItem) {
					windowChangePassword("Change Password");
				}
			});
			userMenu.addItem("Logout", new MenuBar.Command(){
				public void menuSelected(MenuItem selectedItem) {
					SignOut();
				}
			});

			// Have a menu on the left side of the screen
			VerticalLayout menuContent = new VerticalLayout();
			menuContent.setWidth(null);
			menuContent.setMargin(true);
			// Create TreeView For left Content
			
			

			def tree = new TreeMenu()
		
			tree.addItemClickListener(new ItemClickListener() {
				@Override
				public void itemClick(ItemClickEvent event) {
					navigator.navigateTo("MAINVIEW" + "/" + event.getItemId());
				}
			})
			for(def item in tree.getItemIds()){
				tree.expandItemsRecursively(item)
			}
			menuContent.addComponent(tree)
			
			def menu = new Panel("Menu");
			menu.setHeight("100%");
			menu.setWidth(null);
			menu.setContent(menuContent);
			
			hLayout.addComponent(menu);

			// A panel that contains a content area on right
			panel = new Panel("Stock Control");
			panel.setSizeFull();
			
			hLayout.addComponent(panel);
			hLayout.setExpandRatio(panel, 1.0f);
			//hLayout.setWidth(null);
			addComponent(menuBar)
			setComponentAlignment(menuBar, Alignment.MIDDLE_RIGHT );
			addComponent(hLayout);
			setExpandRatio(hLayout, 1.0f);
			
		}
		
		@Override
		public void enter(ViewChangeEvent event) {
			// TODO Auto-generated method stub
			// Get the user name from the session
			UserName = String.valueOf(getSession().getAttribute("user"));
			Subject currentUser = SecurityUtils.getSubject();
			//print UserName + "/" + currentUser.getPrincipal()
			userMenu.setText(UserName)
			userMenu.setDescription("UserName")
			
			//print event.getParameters()
			VerticalLayout panelContent = new VerticalLayout();
			panelContent.setSizeFull();
//			panelContent.setMargin(true);
//			panelContent.setWidth(null);
			panel.setContent(panelContent); 

			if (event.getParameters() == null
				|| event.getParameters().isEmpty()) {
				panelContent.addComponent(
					new Label("Nothing to see here, " +
							  "just pass along."));
				return;
			}
			else{
				switch(event.getParameters())
				{
					case "Item": 
						panelContent.addComponent(new MasterItem())
					break
					case "Customer":
						panelContent.addComponent(new MasterContact())
					break
					case "Purchase Order":
						panelContent.addComponent(new TrPurchaseOrder())
					break
					case "Sales Order":
						panelContent.addComponent(new TrSalesOrder())
					break
					case "Purchase Receival":
						panelContent.addComponent(new TrPurchaseReceival())
					break
					case "Delivery Order":
						panelContent.addComponent(new TrDeliveryOrder())
					break
					case "StockAdjusment":
						panelContent.addComponent(new MasterCalendar())
					break
					case "User":
						panelContent.addComponent(masterUser)
					break
					case "Role":
						panelContent.addComponent(masterRole)
					break
				}
			}	
		}
	}
	
	public boolean SignOut()
	{
		// "Logout" the user
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated())
		{
			currentUser.logout();
			//currentUser.session.stop()
			getSession().setAttribute("user", null);
			//getSession().close()
			getUI().getNavigator().navigateTo("");
		}
		// Refresh this view, should redirect to login view
		getUI().getPage().getCurrent().getJavaScript().execute("window.location.reload();");
		//getApplication().getWindow().executeJavaScript("window.location.reload();");
		return true
	}
	
	public void windowChangePassword(String caption) {
		Subject currentUser = SecurityUtils.getSubject();
		def user = ShiroUser.findByUsername(currentUser.getPrincipal())
		windowChgPass = new Window(caption);
		windowChgPass.setModal(true);
		def layout = new FormLayout() //FormLayout();
		layout.setMargin(true);
		//layout.setWidth("600px");
		//layout.setHeight("300px");
		windowChgPass.setContent(layout);
		def name = new Label()
		textId = new TextField("User ID:");
		textId.setValue(user.id.toString())
		textId.setReadOnly(true)
		layout.addComponent(textId) //"left: 20px; top: 20px;"
		textName = new TextField("UserName:");
		textName.setValue(user.username)
		textName.setReadOnly(true)
		layout.addComponent(textName)
		textOldPassword = new PasswordField("Old Password:");
		layout.addComponent(textOldPassword)
		textNewPassword = new PasswordField("New Password:");
		layout.addComponent(textNewPassword)
		textConfirmPassword = new PasswordField("Confirm Password:");
		layout.addComponent(textConfirmPassword)
		layout.addComponent(createSaveButton())
		layout.addComponent(createCancelButton())
		getUI().addWindow(windowChgPass);
	}
	
	private Button createCancelButton() {
		def button = new Button("Cancel", new Button.ClickListener() {
			void buttonClick(Button.ClickEvent event) {
				windowChgPass.setCaption(textName.getValue())
				textName.discard()
				windowChgPass.close()
			}
		  })
	}
	
	private Button createSaveButton() {
		def button = new Button("Save", new Button.ClickListener() {
			
			void buttonClick(Button.ClickEvent event) {
				try{
					def object = [id:textId.getValue(),
								  username:textName.getValue(),
								  passwordHash:textNewPassword.getValue()]
					
					object =  Grails.get(UserService).updatePasswordObject(object, textOldPassword.getValue(), textConfirmPassword.getValue())
					
					
					if (object.errors.hasErrors())
					{
						textName.setData("username")
						textNewPassword.setData("newpasswordHash")
						textOldPassword.setData("oldpasswordHash")
						textConfirmPassword.setData("confirmpasswordHash")
						Object[] tv = [textName,textNewPassword,textOldPassword,textConfirmPassword]
						generalFunction.setErrorUI(tv,object)
					}
					else
					{
						windowChgPass.close()
					}
					//initTable()
				}catch (Exception e)// (MalformedURLException e)
				{
					Notification.show("Error\n",
						e.getLocalizedMessage(),
						Notification.Type.ERROR_MESSAGE);
				}
				 
				
			}
		  })
		//button.setClickShortcut(KeyCode.ENTER);
		//button.addStyleName("primary"); //Reindeer.BUTTON_DEFAULT
	}
	
	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub
		getPage().setTitle("Stock Control");
		// Create a navigator to control the views
		navigator = new Navigator(this, this);
		 
		// Create and register the views
		navigator.addView("", new LoginForm());
		navigator.addView("MAINVIEW", new MainView());
		
		// We use a view change handler to ensure the user is always redirected
		// to the login view if the user is not logged in.
		//
		navigator.addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {

				// Check if a user has logged in
				Subject currentUser = SecurityUtils.getSubject();
				boolean isLoggedIn = currentUser.isAuthenticated();
				boolean isLoginView = event.getNewView() instanceof LoginForm;

				if (!isLoggedIn && !isLoginView) {
					// Redirect to login view always if a user has not yet
					// logged in
					navigator.navigateTo(""/*LoginForm.NAME*/);
					return false;

				} else if (isLoggedIn && isLoginView) {
					// If someone tries to access to login view while logged in,
					// then cancel
					navigator.navigateTo("MAINVIEW")
					return false;
				}

				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {

			}
		});
	}

}
