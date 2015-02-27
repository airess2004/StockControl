package app


import java.awt.Event;
import com.vaadin.event.ItemClickEvent
import com.vaadin.event.ItemClickEvent.ItemClickListener
import com.vaadin.navigator.Navigator
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.DefaultErrorHandler
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponent
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.Notification
import com.vaadin.ui.Panel
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Alignment;

public class MyUI extends UI{
	Navigator navigator
	
	public class MainView extends VerticalLayout implements View {
		Panel panel
		
		public MainView() {
			setSizeFull()
			
			// Layout with menu on left and view area on right
			HorizontalLayout hLayout = new HorizontalLayout();
			hLayout.setSizeFull();

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
			addComponent(hLayout);
			setExpandRatio(hLayout, 1.0f);
			
		}
		
		@Override
		public void enter(ViewChangeEvent event) {
			// TODO Auto-generated method stub
			print event.getParameters()
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
				}
			}
			
			
				
		}
		
		
	}
	
	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub
		getPage().setTitle("Stock Control");
		// Create a navigator to control the views
		navigator = new Navigator(this, this);
		 
		// Create and register the views
		navigator.addView("", new MainView());
		navigator.addView("MAINVIEW", new MainView());
		
	
	}

}
