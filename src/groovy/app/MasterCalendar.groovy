package app

import java.awt.event.ItemEvent;




import org.apache.shiro.subject.Subject;

import org.vaadin.dialogs.ConfirmDialog

import app.widget.Constant as Constant
import app.widget.GeneralFunction
import stockcontrol.ItemService

import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authz.annotation.RequiresPermissions

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
import com.vaadin.server.DefaultErrorHandler
import com.vaadin.server.UserError
import com.vaadin.ui.Button
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Component
import com.vaadin.ui.Field
import com.vaadin.ui.FormLayout
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.MenuBar
import com.vaadin.ui.Notification
import com.vaadin.ui.Table
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Window
import com.vaadin.ui.MenuBar.MenuItem






import stockcontrol.Item

import com.vaadin.grails.Grails

class MasterCalendar extends VerticalLayout{
	def selectedRow
	def itemlist
	GeneralFunction generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private Window window
	private TextField textId
	private TextField textSKU
	private TextField textDescription
	private Table table = new Table();
	private BeanItemContainer<Item> tableContainer;
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	String Title = Constant.MenuGroup.Master + ":" + 
						Constant.MenuName.Item + ":";
	Subject currentUser;
	
	public MasterCalendar() {
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
						def item = new BeanItem<Item>(tableContainer);
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
	
		MenuItem saveMenu =  menuBar.addItem("Add",mycommand)
		MenuItem updateMenu = menuBar.addItem("Edit", mycommand)
		MenuItem deleteMenu = menuBar.addItem("Delete", mycommand)
		menu.addComponent(menuBar)
		menuBar.setWidth("100%")	
		
		Calendar cal = new Calendar("My Calendar");
//		addComponent(table)
		addComponent(cal)
//		table.setPageLength(table.size())
	}
	
	
	private Button createCancelButton() {
		def saveButton = new Button("Cancel", new Button.ClickListener() {
			void buttonClick(Button.ClickEvent event) {
				window.setCaption(textSKU.getValue())
				textSKU.discard()
				window.close()
			}
		  })
	}
	
	private Button createSaveButton() {
		def saveButton = new Button("Save", new Button.ClickListener() {
			
			void buttonClick(Button.ClickEvent event) {
				try{
					def object = [id:textId.getValue(),
								  sku:textSKU.getValue().toString(),
								  description:textDescription.getValue().toString()]
					
					if (object.id == "")
					{
						object =  Grails.get(ItemService).createObject(object)
					}
					else
					{
						object =  Grails.get(ItemService).updateObject(object)
					}
					
					
					if (object.errors.hasErrors())
					{
						textSKU.setData("sku")
						textDescription.setData("description")
						Object[] tv = [textSKU,textDescription]
						generalFunction.setErrorUI(tv,object)
					}
					else
					{
						window.close()
					}
					initTable()
				}catch (Exception e)
				{
					Notification.show("Error\n",
						e.getMessage(),
						Notification.Type.ERROR_MESSAGE);
				}
				 
				
			}
		  })
	}
	
	//@RequiresPermissions("Master:Item:Delete")
	private void windowDelete(String caption) {
		if (currentUser.isPermitted(Title + Constant.AccessType.Delete)) {
			ConfirmDialog.show(this.getUI(), caption + " ID:" + tableContainer.getItem(table.getValue()).getItemProperty("id") + " ? ",
			new ConfirmDialog.Listener() {
				public void onClose(ConfirmDialog dialog) {
					if (dialog.isConfirmed()) {
						def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
						Grails.get(ItemService).softDeleteObject(object)
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
	
	//@RequiresPermissions("Master:Item:Edit")
	private void windowEdit(def item,String caption) {
		if (currentUser.isPermitted(Title + Constant.AccessType.Edit)) {
			window = new Window(caption);
			window.setModal(true);
			layout = new FormLayout();
			layout.setMargin(true);
			window.setContent(layout);
			textId = new TextField("Product Id:");
			textId.setPropertyDataSource(item.getItemProperty("id"))
			textId.setReadOnly(true)
			layout.addComponent(textId)
			textSKU = new TextField("Code:");
			textSKU.setPropertyDataSource(item.getItemProperty("sku"))
			textSKU.setBuffered(true)
			textSKU.setImmediate(false)
			layout.addComponent(textSKU)
			textDescription = new TextField("Description:");
			textDescription.setPropertyDataSource(item.getItemProperty("description"))
			textDescription.setBuffered(true)
			textDescription.setImmediate(false)
			layout.addComponent(textDescription)
			layout.addComponent(createSaveButton())
			layout.addComponent(createCancelButton())
			getUI().addWindow(window);
		} else {
			Notification.show("Access Denied\n",
				"Anda tidak memiliki izin untuk Mengubah Record",
				Notification.Type.ERROR_MESSAGE);
		}
	}
	
	//@RequiresPermissions("Master:Item:Add")
	private void windowAdd(String caption) {
		if (currentUser.isPermitted(Title + Constant.AccessType.Add)) {
			window = new Window(caption);
			window.setModal(true);
			layout = new FormLayout();
			layout.setMargin(true);
			window.setContent(layout);
			def sku = new Label()
			textId = new TextField("Product Id:");
			textId.setReadOnly(true)
			layout.addComponent(textId)
			textSKU = new TextField("Code:");
			layout.addComponent(textSKU)
			textDescription = new TextField("Description:");
			layout.addComponent(textDescription)
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
		tableContainer = new BeanItemContainer<Item>(Item.class);
		//fillTableContainer(tableContainer);
	    itemlist = Grails.get(ItemService).getList()
		tableContainer.addAll(itemlist)
		table.setContainerDataSource(tableContainer);
		table.visibleColumns = ["sku", "description","quantity","pendingReceival","pendingDelivery","isDeleted","dateCreated","lastUpdated"]
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
