package app

import java.awt.event.ItemEvent;


import org.vaadin.dialogs.ConfirmDialog
import app.widget.GeneralFunction
import stockcontrol.Contact
import stockcontrol.ContactService

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

class MasterContact extends VerticalLayout{
	def selectedRow
	def itemlist
	GeneralFunction generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private Window window
	private TextField textId
	private TextField textName
	private TextField textPhoneBook
	private TextField textAddress
	private Table table = new Table();
	private BeanItemContainer<Contact> tableContainer;
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	
	public MasterContact() {
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
					def item = new BeanItem<Contact>(tableContainer)
					windowAdd("Add");
					break
					case "Edit":
					if (table.getValue() != null)
					windowEdit(tableContainer.getItem(table.getValue()),"Edit");
					break;
					case "Delete":
					if (table.getValue() != null)
//					ConfirmDialog.show(null, null)
					ConfirmDialog.show(this.getUI(),"Delete ? " + tableContainer.getItem(table.getValue()).getItemProperty("id"),
							new ConfirmDialog.Listener() {
								public void onClose(ConfirmDialog dialog) {
									if (dialog.isConfirmed()) {
										def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
										Grails.get(ContactService).softDeleteObject(object)
										initTable()
									} else {
									
									}
							}
			        })
					break;
				}
			}
		};
	
		MenuItem saveMenu =  menuBar.addItem("Add",mycommand)
		MenuItem updateMenu = menuBar.addItem("Edit", mycommand)
		MenuItem deleteMenu = menuBar.addItem("Delete", mycommand)
		menu.addComponent(menuBar)
		menuBar.setWidth("100%")	
		
		
	
		addComponent(table)
//		table.setPageLength(table.size())
		}
	
	
	private Button createDeleteButton() {
		def saveButton = new Button("Delete", new Button.ClickListener() {
			void buttonClick(Button.ClickEvent event) {
				window.setCaption(textName.getValue())
				textName.discard()
			}
		  })
	}
	
	private Button createSaveButton() {
		def saveButton = new Button("Save", new Button.ClickListener() {
			
			void buttonClick(Button.ClickEvent event) {
				try{
					def object = [id:textId.getValue(),
								  name:textName.getValue(),
								  phoneBook:textPhoneBook.getValue(),
								  address:textAddress.getValue()]
					
					if (object.id == "")
					{
						object =  Grails.get(ContactService).createObject(object)
					}
					else
					{
						object =  Grails.get(ContactService).updateObject(object)
					}
					
					
					if (object.errors.hasErrors())
					{
						textName.setData("name")
						textPhoneBook.setData("phoneBook")
						textAddress.setData("address")
						Object[] tv = [textName,textPhoneBook,textAddress]
						generalFunction.setErrorUI(tv,object)
					}
					else
					{
						window.close()
					}
					initTable()
				}catch (MalformedURLException e)
				{
					Notification.show("Error",
						e.getMessage(),
						Notification.Type.WARNING_MESSAGE);
				}
				 
				
			}
		  })
}
	
	private void windowEdit(def item,String caption) {
		window = new Window(caption);
		window.setModal(true);
	    layout = new FormLayout();
		layout.setMargin(true);
		window.setContent(layout);
		textId = new TextField("Product Id:");
		textId.setPropertyDataSource(item.getItemProperty("id"))
		textId.setReadOnly(true)
		layout.addComponent(textId)
		textName = new TextField("Name:");
		textName.setPropertyDataSource(item.getItemProperty("name"))
		textName.setBuffered(true)
		textName.setImmediate(false)
		layout.addComponent(textName)
		textPhoneBook = new TextField("Phone:");
		textPhoneBook.setBuffered(true)
		textPhoneBook.setPropertyDataSource(item.getItemProperty("phoneBook"))	
		layout.addComponent(textPhoneBook)
		textAddress = new TextField("Address:");
		textAddress.setBuffered(true)
		textAddress.setPropertyDataSource(item.getItemProperty("address"))
		layout.addComponent(textAddress)
		layout.addComponent(createSaveButton())
		layout.addComponent(createDeleteButton())
		getUI().addWindow(window);
		}
	
	private void windowAdd(String caption) {
		window = new Window(caption);
		window.setModal(true);
		layout = new FormLayout();
		layout.setMargin(true);
		window.setContent(layout);
		def sku = new Label()
		textId = new TextField("Product Id:");
		textId.setReadOnly(true)
		layout.addComponent(textId)
		textName = new TextField("Name:");
		layout.addComponent(textName)
		textPhoneBook = new TextField("Phone:");
		layout.addComponent(textPhoneBook)
		textAddress = new TextField("Address:");
		layout.addComponent(textAddress)
		layout.addComponent(createSaveButton())
		layout.addComponent(createDeleteButton())
		getUI().addWindow(window);
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
		tableContainer = new BeanItemContainer<Contact>(Contact.class);
		//fillTableContainer(tableContainer);
	    itemlist = Grails.get(ContactService).getList()
		tableContainer.addAll(itemlist)
		table.setContainerDataSource(tableContainer);
		table.visibleColumns = ["name", "phoneBook","address","isDeleted","dateCreated","lastUpdated"]
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
