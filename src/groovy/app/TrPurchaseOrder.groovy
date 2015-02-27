package app

import java.awt.event.ItemEvent;


import org.vaadin.actionbuttontextfield.ActionButtonTextField
import org.vaadin.actionbuttontextfield.widgetset.client.ActionButtonType;
import org.vaadin.dialogs.ConfirmDialog
import app.widget.GeneralFunction
import stockcontrol.Contact
import stockcontrol.ContactService
import stockcontrol.ItemService

import stockcontrol.PurchaseOrderDetail
import stockcontrol.PurchaseOrderService
import stockcontrol.PurchaseOrderDetailService
import stockcontrol.PurchaseOrder
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.TextChangeEvent;
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
import com.vaadin.ui.ComboBox
import com.vaadin.ui.Component
import com.vaadin.ui.DateField
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

class TrPurchaseOrder extends VerticalLayout{
	def selectedRow
	def itemlist
	GeneralFunction generalFunction = new GeneralFunction()
	private MenuBar menuBar
	private MenuBar menuBarDetail
	private Window window
	private TextField textId
	private TextField textIdDetail
	private TextField textCode
	private TextField textContact
	private DateField purchaseDate
	private TextField textQuantity
	private ComboBox comb
	private Table table = new Table()
	private Table tableDetail = new Table()
	private Table tableSearch = new Table()
	private BeanItemContainer<PurchaseOrder> tableContainer
	private BeanItemContainer tableSearchContainer
	private BeanItemContainer tableDetailContainer
	private FieldGroup fieldGroup;
	private FormLayout layout
	private Action actionDelete = new Action("Delete");
	private int code = 1;
	private static final int MAX_PAGE_LENGTH = 15;
	 
	public TrPurchaseOrder() {
		initTable();
		
		HorizontalLayout menu = new HorizontalLayout()
		menu.setWidth("100%")

		addComponent(menu)
		
		menuBar = new MenuBar()
		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				switch(selectedItem.getText())
				{
					case "Add":
					def item = new BeanItem<PurchaseOrder>(tableContainer)
					windowAdd("Add");
					break
					
					case "Edit":	
					if (table.getValue() != null)
					windowEdit(tableContainer.getItem(table.getValue()),"Edit");
					break;
					
					case "AddDetail":
					if (table.getValue() != null)
					windowAddDetail(tableContainer.getItem(table.getValue()),"Add Detail")
					break
					
					case "EditDetail":
					if (table.getValue() != null && tableDetail.getValue() != null)
					windowEditDetail(tableContainer.getItem(table.getValue()), tableDetailContainer.getItem(tableDetail.getValue()),"Edit Detail")
					break
					
					case "Delete":
					if (table.getValue() != null)
					ConfirmDialog.show(this.getUI(),"Delete ? " + tableContainer.getItem(table.getValue()).getItemProperty("id"),
							new ConfirmDialog.Listener() {
								public void onClose(ConfirmDialog dialog) {
									if (dialog.isConfirmed()) {
										def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
										object = Grails.get(PurchaseOrderService).softDeleteObject(object)
											if (object.errors.hasErrors())
											{
											Object[] tv = [textId]
											generalFunction.setErrorUI(tv,object)
											}else
											{
												initTable()
											}
									} else {
									
									}
							}
			        })
					break
					
					
					case "DeleteDetail":
					if (tableDetail.getValue() != null)
					ConfirmDialog.show(this.getUI(),"Delete ? " + tableDetailContainer.getItem(tableDetail.getValue()).getItemProperty("id"),
							new ConfirmDialog.Listener() {
								public void onClose(ConfirmDialog dialog) {
									if (dialog.isConfirmed()) {
										def object = [id:tableDetailContainer.getItem(tableDetail.getValue()).getItemProperty("id").toString()]
										object = Grails.get(PurchaseOrderDetailService).softDeleteObject(object)
											if (object.errors.hasErrors())
											{
											Object[] tv = [textId]
											generalFunction.setErrorUI(tv,object)
											}else
											{
												initTableDetail()
											}
									} else {
									
									}
							}
					})
					break
					
					case "Confirm":
					ConfirmDialog.show(this.getUI(),"Confirm ? " + tableContainer.getItem(table.getValue()).getItemProperty("id"),
						new ConfirmDialog.Listener() {
							public void onClose(ConfirmDialog dialog) {
								if (dialog.isConfirmed()) {
									def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
									object = Grails.get(PurchaseOrderService).confirmObject(object)
									if (object.errors.hasErrors())
									{
										Object[] tv = [textId]
										generalFunction.setErrorUI(tv,object)
									}else
									{
										initTable()
									}
									
								} else {
								
								}
						}
					}) 
					break
					
					case "Unconfirm":
					ConfirmDialog.show(this.getUI(),"Unconfirm ? " + tableContainer.getItem(table.getValue()).getItemProperty("id"),
						new ConfirmDialog.Listener() {
							public void onClose(ConfirmDialog dialog) {
								if (dialog.isConfirmed()) {
									def object = [id:tableContainer.getItem(table.getValue()).getItemProperty("id").toString()]
									object = Grails.get(PurchaseOrderService).unconfirmObject(object)
									if (object.errors.hasErrors())
									{
										Object[] tv = [textId]
										generalFunction.setErrorUI(tv,object)
									}else
									{
										initTable()
									}
									
								} else {
								
								}
						}
					})
					break
				}
			}
		};
	
		MenuItem saveMenu =  menuBar.addItem("Add",mycommand)
//		MenuItem saveDetailMenu =  menuBar.addItem("AddDetail",mycommand)
		MenuItem updateMenu = menuBar.addItem("Edit", mycommand)
//		MenuItem editDetailMenu = menuBar.addItem("EditDetail", mycommand)
		MenuItem confirmMenu = menuBar.addItem("Confirm", mycommand)
		MenuItem unconfirmMenu = menuBar.addItem("Unconfirm", mycommand)
		MenuItem deleteMenu = menuBar.addItem("Delete", mycommand)
		menu.addComponent(menuBar)
		menuBar.setWidth("100%")	
		
		
	
		addComponent(table)
		menuBarDetail = new MenuBar()
		MenuItem saveDetailMenu =  menuBarDetail.addItem("AddDetail",mycommand)
		MenuItem editDetailMenu = menuBarDetail.addItem("EditDetail", mycommand)
		MenuItem deleteDetailMenut = menuBarDetail.addItem("DeleteDetail",mycommand)
		menuBarDetail.setWidth("100%")
		menuBarDetail.setVisible(false)
		addComponent(menuBarDetail)
		addComponent(tableDetail)
//		table.setPageLength(table.size())
		}
	
	
	private Button createDeleteButton() {
		def saveButton = new Button("Cancel", new Button.ClickListener() {
			void buttonClick(Button.ClickEvent event) {
				window.close()
			}
		  })
	}
	
	private Button createContactButton() {
		def saveButton = new Button("...", new Button.ClickListener() {
			void buttonClick(Button.ClickEvent event) {
				windowSearch()
			}
		  })
	}
	
	private Button createSaveButton() {
		def saveButton = new Button("Save", new Button.ClickListener() {
			
			void buttonClick(Button.ClickEvent event) {
				try{
					def object = [id:textId.getValue(),
								  code:textCode.getValue().toString(),
								  purchaseDate:purchaseDate.getValue(),
								  contact: comb.getValue()]
					
					if (object.id == "")
					{
						object =  Grails.get(PurchaseOrderService).createObject(object)
					}
					else
					{
						object =  Grails.get(PurchaseOrderService).updateObject(object)
					}				
					if (object.errors.hasErrors())
					{
						textCode.setData("code")
						purchaseDate.setData("purchaseDate")
						comb.setData("contact")
						Object[] tv = [textCode,purchaseDate,comb]
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
	
	private Button createSaveDetailButton() {
		def saveButton = new Button("Save", new Button.ClickListener() {
			
			void buttonClick(Button.ClickEvent event) {
				try{
					
						
					def object = [ 	id: textIdDetail.getValue(),
									purchaseOrderId : textId.getValue(),
									code: textCode.getValue().toString(),
									quantity: java.text.NumberFormat.instance.parse(textQuantity.getValue()),
									item: comb.getValue()]
					
					if (object.id == "")
					{
						object =  Grails.get(PurchaseOrderDetailService).createObject(object)
					}
					else
					{
						object =  Grails.get(PurchaseOrderDetailService).updateObject(object)
					}
					
					
					if (object.errors.hasErrors())
					{
						textCode.setData("code")
						textQuantity.setData("quantity")
						comb.setData("item")
						Object[] tv = [textCode,comb,textQuantity]
						generalFunction.setErrorUI(tv,object)
					}
					else
					{
						window.close()
						initTableDetail()
					}
					
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
		textCode = new TextField("Code:");
		textCode.setPropertyDataSource(item.getItemProperty("code"))
		textCode.setBuffered(true)
		textCode.setImmediate(false)
		layout.addComponent(textCode)
		comb = new ComboBox("Contact:")
		tableSearchContainer = new BeanItemContainer<Contact>(Contact.class);
		itemlist = Grails.get(ContactService).getList()
		tableSearchContainer.addAll(itemlist)
		comb.setContainerDataSource(tableSearchContainer)
		comb.setItemCaptionPropertyId("name") 
		comb.select(comb.getItemIds().find{ it.id == item.getItemProperty("contact.id").value})
		comb.setBuffered(true)
		comb.setImmediate(false)
		layout.addComponent(comb);
		purchaseDate = new DateField("Purchase Date:")
		purchaseDate.setPropertyDataSource(item.getItemProperty("purchaseDate"))
		layout.addComponent(purchaseDate)
		layout.addComponent(createSaveButton())
		layout.addComponent(createDeleteButton())
		getUI().addWindow(window);
		}
	
	private void windowSearch(){
		window = new Window(caption)
		tableSearchContainer = new BeanItemContainer<Contact>(Contact.class);
		itemlist = Grails.get(ContactService).getList()
		tableContainer.addAll(itemlist)
		tableSearch.setContainerDataSource(tableContainer);
		tableSearch.visibleColumns = ["name", "phoneBook","address"]
		tableSearch.setSelectable(true)
		tableSearch.setImmediate(false)
		tableSearch.setSizeFull()
		window.setModal(true)
		def layout2 = new FormLayout()
		layout2.addComponent(table)
		window.setContent(layout2)
		getUI().addWindow(window)
	}
	
	private void windowAdd(String caption) {
		window = new Window(caption)
		window.setModal(true)
		def layout3 = new FormLayout()
		layout3.setMargin(true)
		window.setContent(layout3)
		def sku = new Label()
		textId = new TextField("Product Id:")
		textId.setReadOnly(true)
		layout3.addComponent(textId)
		textCode = new TextField("Code:");
		layout3.addComponent(textCode)
		comb = new ComboBox("Contact:")
		tableSearchContainer = new BeanItemContainer<Contact>(Contact.class);
		itemlist = Grails.get(ContactService).getList()
		tableSearchContainer.addAll(itemlist)
		comb.setContainerDataSource(tableSearchContainer)
		comb.setItemCaptionPropertyId("name")
		layout3.addComponent(comb);		
		purchaseDate = new DateField("Purchase Date:")
		layout3.addComponent(purchaseDate)  
		layout3.addComponent(createSaveButton())
		layout3.addComponent(createDeleteButton())
		
		getUI().addWindow(window);
		}
	
	private void windowAddDetail(item,String caption) {
		window = new Window(caption)
		window.setModal(true)
		def layout3 = new FormLayout()
		layout3.setMargin(true)
		window.setContent(layout3)
		def sku = new Label()
		textId = new TextField("Product Id:")
		textId.setPropertyDataSource(item.getItemProperty("id"))
		textId.setReadOnly(true)
		layout3.addComponent(textId) 
		textIdDetail = new TextField("Detail Id:")
		textIdDetail.setReadOnly(true)
		layout3.addComponent(textIdDetail)
		
		textCode = new TextField("Code:");
		layout3.addComponent(textCode)
		comb = new ComboBox("Item:")
		tableSearchContainer = new BeanItemContainer<Item>(Item.class);
		itemlist = Grails.get(ItemService).getList()
		tableSearchContainer.addAll(itemlist)
		
		comb.setContainerDataSource(tableSearchContainer)
		
		comb.setItemCaptionPropertyId("sku")
		layout3.addComponent(comb)
		textQuantity = new TextField("Quantity:")
		layout3.addComponent(textQuantity)
		layout3.addComponent(createSaveDetailButton())
		layout3.addComponent(createDeleteButton())
		
		getUI().addWindow(window);
		}
	
	private void windowEditDetail(item,itemDetail,String caption) {
		window = new Window(caption)
		window.setModal(true)
		def layout3 = new FormLayout()
		layout3.setMargin(true)
		window.setContent(layout3)
		textId = new TextField("Master Id:")
		textId.setPropertyDataSource(item.getItemProperty("id"))
		textId.setReadOnly(true)
		layout3.addComponent(textId)
		textIdDetail = new TextField("Detail Id:")
		textIdDetail.setPropertyDataSource(itemDetail.getItemProperty("id"))
		textIdDetail.setReadOnly(true)
		layout3.addComponent(textIdDetail)
		textCode = new TextField("Code:");
		textCode.setPropertyDataSource(itemDetail.getItemProperty("code"))
		textCode.setBuffered(true)
		textCode.setImmediate(false)
		layout3.addComponent(textCode)
		comb = new ComboBox("Item:")
		
		tableSearchContainer = new BeanItemContainer<Item>(Item.class);
		itemlist = Grails.get(ItemService).getList()
		tableSearchContainer.addAll(itemlist)
		
		comb.setContainerDataSource(tableSearchContainer)
		comb.setItemCaptionPropertyId("sku")
		comb.select(comb.getItemIds().find{ it.id == itemDetail.getItemProperty("item.id").value})
		
		layout3.addComponent(comb)
		textQuantity = new TextField("Quantity:")
		textQuantity.setPropertyDataSource(itemDetail.getItemProperty("quantity"))
		textQuantity.setBuffered(true)
		layout3.addComponent(textQuantity)
		layout3.addComponent(createSaveDetailButton())
		layout3.addComponent(createDeleteButton())
		
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
		tableContainer = new BeanItemContainer<PurchaseOrder>(PurchaseOrder.class);
		//fillTableContainer(tableContainer);
	    itemlist = Grails.get(PurchaseOrderService).getList()
		tableContainer.addNestedContainerProperty("contact.name");
		tableContainer.addNestedContainerProperty("contact.id");
		tableContainer.addAll(itemlist) 
		table.setColumnHeader("contact.name", "Contact")
		table.setContainerDataSource(tableContainer);
		table.visibleColumns = ["code", "contact.name","purchaseDate","isConfirmed","confirmationDate","isDeleted","dateCreated","lastUpdated"]
		table.setSelectable(true)
		table.setImmediate(false)
//		table.setPageLength(table.size())
		table.setSizeFull()
		table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				
//				selectedRow = table.getValue()
			
//				print selectedRow
			}
		});
		 
		table.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				selectedRow = table.getValue()
				if (selectedRow != null) {
					tableDetailContainer = new BeanItemContainer<PurchaseOrderDetail>(PurchaseOrderDetail.class);
					def ind = tableContainer.getItem(table.getValue()).getItemProperty("id").toString()
					
					def itemListDetail = Grails.get(PurchaseOrderDetailService).getList(ind)
					tableDetailContainer.addNestedContainerProperty("item.description");
					tableDetailContainer.addNestedContainerProperty("item.id");
					tableDetailContainer.addNestedContainerProperty("purchaseOrder.id");
					tableDetailContainer.addAll(itemListDetail)
					tableDetail.setColumnHeader("item.description", "Item")
					tableDetail.setContainerDataSource(tableDetailContainer);
					tableDetail.visibleColumns = ["code", "purchaseOrder.id","item.description","quantity","pendingReceival","isConfirmed","confirmationDate","isDeleted","dateCreated","lastUpdated"]
					tableDetail.setSelectable(true)
					tableDetail.setImmediate(false)
					tableDetail.setVisible(true)
					tableDetail.setSizeFull()
					menuBarDetail.setVisible(true)
				}
				else
				{
				 	tableDetail.setVisible(false)
					 menuBarDetail.setVisible(false)
				}
			}
		});

	}
	
	 void initTableDetail() {
		 tableDetailContainer = new BeanItemContainer<PurchaseOrderDetail>(PurchaseOrderDetail.class);
					 def ind = tableContainer.getItem(table.getValue()).getItemProperty("id").toString()
					 def itemListDetail = Grails.get(PurchaseOrderDetailService).getList(ind)
					 tableDetailContainer.addNestedContainerProperty("item.description");
					 tableDetailContainer.addNestedContainerProperty("item.id");
					 tableDetailContainer.addNestedContainerProperty("purchaseOrder.id");
					 tableDetailContainer.addAll(itemListDetail)
					 tableDetail.setColumnHeader("item.description", "Item")
					 tableDetail.setContainerDataSource(tableDetailContainer);
					 tableDetail.visibleColumns = ["code", "purchaseOrder.id","item.description","quantity","pendingReceival","isConfirmed","confirmationDate","isDeleted","dateCreated","lastUpdated"]
					 tableDetail.setSelectable(true)
					 tableDetail.setImmediate(false)
					 tableDetail.setVisible(true)
					 tableDetail.setSizeFull()
					 menuBarDetail.setVisible(true)
	 }
	 
	
}
