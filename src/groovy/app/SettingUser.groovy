package app

import java.awt.event.ItemEvent;

import app.widget.Constant as Constant
import app.widget.GeneralFunction; 
import stockcontrol.UserService;
import stockcontrol.ShiroUser;
import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authz.annotation.RequiresPermissions

import org.vaadin.dialogs.ConfirmDialog
import com.vaadin.data.Property
import com.vaadin.data.Property.ValueChangeEvent
import com.vaadin.data.fieldgroup.BeanFieldGroup
import com.vaadin.data.fieldgroup.FieldGroup
import com.vaadin.data.fieldgroup.FieldGroup.CommitException
import com.vaadin.data.util.BeanItem
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.event.Action
import com.vaadin.event.ItemClickEvent
import com.vaadin.event.Action.Handler
import com.vaadin.event.ItemClickEvent.ItemClickListener
import com.vaadin.event.MouseEvents.ClickEvent
import com.vaadin.event.MouseEvents.ClickListener
import com.vaadin.event.ShortcutAction.KeyCode
import com.vaadin.server.DefaultErrorHandler
import com.vaadin.server.UserError
import com.vaadin.ui.AbsoluteLayout
import com.vaadin.ui.Button
import com.vaadin.ui.Component
import com.vaadin.ui.Field
import com.vaadin.ui.FormLayout
import com.vaadin.ui.GridLayout
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.MenuBar
import com.vaadin.ui.Notification
import com.vaadin.ui.PasswordField
import com.vaadin.ui.Table
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Window
import com.vaadin.ui.MenuBar.MenuItem
import com.vaadin.grails.Grails

class SettingUser extends VerticalLayout{
	def selectedRow
	def itemlist
	def generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private Window window
	private TextField textId
	private TextField textName
	private PasswordField textPassword
	private Table table = new Table();
	private BeanItemContainer<ShiroUser> tableContainer;
	private FieldGroup fieldGroup;
	private def layout //AbsoluteLayout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = "";
	Subject currentUser;
	
	public SettingUser() {
		String Title = Constant.MenuGroup.Setting + ":" +
				Constant.MenuName.User + ":";
		currentUser = SecurityUtils.getSubject();
		
		initTable();

		HorizontalLayout menu = new HorizontalLayout()
		menu.setWidth("100%")
//		menu.addComponent(createAddButton())
//		menu.addComponent(createUpdateButton())
//		menu.addComponent(createDeleteButton())
//		
		addComponent(menu)
		
		menuBar = new MenuBar()
		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				switch(selectedItem.getText())
				{
					case "Add":
						def item = new BeanItem<ShiroUser>(tableContainer)
						windowAdd("Add");
					break
					case "Edit":
						if (table.getValue() != null)
						windowEdit(tableContainer.getItem(table.getValue()),"Edit");
					break;
					case "Delete":
						if (table.getValue() != null)
						windowDelete("Delete");
					break;
				}
			}
		};
	
		
		MenuItem saveMenu =  menuBar.addItem("Add", mycommand)	
		MenuItem updateMenu = menuBar.addItem("Edit", mycommand)
		MenuItem deleteMenu = menuBar.addItem("Delete", mycommand)
		menu.addComponent(menuBar)
		menuBar.setWidth("100%")	
		
		
	
		addComponent(table)
//		table.setPageLength(table.size())
	}
	
	
	private Button createCancelButton() {
		def saveButton = new Button("Cancel", new Button.ClickListener() {
			void buttonClick(Button.ClickEvent event) {
				window.setCaption(textName.getValue())
				textName.discard()
				window.close()
			}
		  })
	}
	
	private Button createSaveButton() {
		def saveButton = new Button("Save", new Button.ClickListener() {
			
			void buttonClick(Button.ClickEvent event) {
				try{
					def object = [id:textId.getValue(),
								  username:textName.getValue(),
								  passwordHash:textPassword.getValue()]
					
					if (object.id == "")
					{
						object =  Grails.get(UserService).createObject(object)
					}
					else
					{
						object =  Grails.get(UserService).updateObject(object)
					}
					
					
					if (object.errors.hasErrors())
					{
						textName.setData("username")
						textPassword.setData("passwordHash")
						Object[] tv = [textName,textPassword]
						generalFunction.setErrorUI(tv,object)
					}
					else
					{
						window.close()
					}
					initTable()
				}catch (Exception e)// (MalformedURLException e)
				{
					Notification.show("Error\n",
						e.getMessage(),
						Notification.Type.ERROR_MESSAGE);
				}
				 
				
			}
		  })
		//saveButton.setClickShortcut(KeyCode.ENTER);
		//saveButton.addStyleName("primary"); //Reindeer.BUTTON_DEFAULT
	}
	
	//@RequiresPermissions("Setting:User:Delete")
	private void windowDelete(String caption) {
		if (currentUser.isPermitted(Title + Constant.AccessType.Delete)) {
			ConfirmDialog.show(this.getUI(), caption + " ID:" + tableContainer.getItem(table.getValue()).getItemProperty("id") + " ? ",
			new ConfirmDialog.Listener() {
				public void onClose(ConfirmDialog dialog) {
					if (dialog.isConfirmed()) {
						def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
						Grails.get(UserService).softDeleteObject(object)
						initTable()
					} else {
								
					}
				}
			})
		} else {
			Notification.show("Access Denied\n",
				"Anda tidak memiliki izin untuk Menghapus Record",
				Notification.Type.ERROR_MESSAGE);
		}
	}
	
	//@RequiresPermissions("Setting:User:Edit")
	private void windowEdit(def item,String caption) {
		if (currentUser.isPermitted(Title + Constant.AccessType.Edit)) {
			window = new Window(caption);
			window.setModal(true);
			layout = new FormLayout();
			layout.setMargin(true);
			//layout.setWidth("600px");
			//layout.setHeight("300px");
			window.setContent(layout);
			textId = new TextField("User ID:");
			textId.setPropertyDataSource(item.getItemProperty("id"))
			textId.setReadOnly(true)
			layout.addComponent(textId)
			textName = new TextField("UserName:");
			textName.setPropertyDataSource(item.getItemProperty("username"))
			textName.setBuffered(true)
			textName.setImmediate(false)
			layout.addComponent(textName)
			textPassword = new PasswordField("Password:");
			textPassword.setBuffered(true)
			textPassword.setPropertyDataSource(item.getItemProperty("passwordHash"))
			
			layout.addComponent(textPassword)
			layout.addComponent(createSaveButton())
			layout.addComponent(createCancelButton())
			getUI().addWindow(window);
		} else {
			Notification.show("Access Denied\n",
				"Anda tidak memiliki izin untuk Mengubah Record",
				Notification.Type.ERROR_MESSAGE);
		}
	}
	
	//@RequiresPermissions("Setting:User:Add")
	private void windowAdd(String caption) {
		if (currentUser.isPermitted(Title + Constant.AccessType.Add)) {
			window = new Window(caption);
			window.setModal(true);
			layout = new FormLayout() //FormLayout();
			layout.setMargin(true);
			//layout.setWidth("600px");
			//layout.setHeight("300px");
			window.setContent(layout);
			def name = new Label()
			textId = new TextField("User ID:");
			textId.setReadOnly(true)
			layout.addComponent(textId) //"left: 20px; top: 20px;"
			textName = new TextField("UserName:");
			layout.addComponent(textName)
			textPassword = new PasswordField("Password:");
			layout.addComponent(textPassword)
			layout.addComponent(createSaveButton())
			layout.addComponent(createCancelButton())
			getUI().addWindow(window);
		} else {
			Notification.show("Access Denied\n",
				"Anda tidak memiliki izin untuk Membuat Record",
				Notification.Type.ERROR_MESSAGE);
		}
	}
	
	 void updateTable() {
//		if (table.size() > MAX_PAGE_LENGTH) {
//		table.setPageLength(MAX_PAGE_LENGTH);
//		} else {
//		table.setPageLength(table.size());
//		}
//		table.markAsDirtyRecursive();
		}
	 
	 void initTable() {
		tableContainer = new BeanItemContainer<ShiroUser>(ShiroUser.class);
		//fillTableContainer(tableContainer);
	    itemlist = Grails.get(UserService).getList()
		tableContainer.addAll(itemlist)
		table.setContainerDataSource(tableContainer);
		table.visibleColumns = ["username", "passwordHash", "isDeleted","dateCreated","lastUpdated"]
		table.setSelectable(true)
		table.setImmediate(false)
//		table.setPageLength(table.size())
		table.setSizeFull()
		
//		table.addValueChangeListener(new Property.ValueChangeListener() {
//			public void valueChange(ValueChangeEvent event) {
//				selectedRow = table.getValue()
//			}
//		});

	}
	
	
	
}
