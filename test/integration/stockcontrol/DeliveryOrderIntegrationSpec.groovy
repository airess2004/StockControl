package stockcontrol

import grails.test.spock.IntegrationSpec

class DeliveryOrderIntegrationSpec extends IntegrationSpec {

   
    def deliveryOrderService
	def deliveryOrderDetailService
	def contactService
	def itemService
	def salesOrderService
	def salesOrderDetailService
	
    def setup() {
    }

    def cleanup() {
    }
	//----------------------------------CREATE
    void "test create DeliveryOrder"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName";
		contact.phoneBook = "88723";
		contact.address = "Jln. Kopi";
		contact = contactService.createObject(contact)
		
		and: 'setting new Purchase Order'
		def salesOrder = new SalesOrder()
		salesOrder.code = "code1"
		salesOrder.contact = contact
		salesOrder.salesDate = new Date(2015, 3, 16)
		salesOrder = salesOrderService.createObject(salesOrder)
		
		and: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		
		when: 'create is called'
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
				
		then: 'check has no error'
		deliveryOrder.hasErrors() == false
		deliveryOrder.isDeleted == false
		deliveryOrder.isConfirmed == false
		println "Create success"
	}
	
	void "test create validation salesOrder not Null"() {
		setup: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		
		when: 'create is called'
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
				
		then: 'check has error'
		deliveryOrder.hasErrors() == true
		println  "Validation success with error message : " + deliveryOrder.errors.getFieldError().defaultMessage
	}
	//-----------------------------END CREATE
	
	//-----------------------------UPDATE
	void "test update DeliveryOrder"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName";
		contact.phoneBook = "88723";
		contact.address = "Jln. Kopi";
		contact = contactService.createObject(contact)
		
		and: 'setting new Purchase Order'
		def salesOrder = new SalesOrder()
		salesOrder.code = "code1"
		salesOrder.contact = contact
		salesOrder.salesDate = new Date(2015, 3, 16)
		salesOrder = salesOrderService.createObject(salesOrder)
		
		and: 'setting new Purchase Order2'
		def salesOrder2 = new SalesOrder()
		salesOrder2.code = "code2"
		salesOrder2.contact = contact
		salesOrder2.salesDate = new Date(2015, 3, 17)
		salesOrder2 = salesOrderService.createObject(salesOrder2)
		
		
		and: 'setting new Purchase Order'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		and: 'setting Update Purchase Order'
		def updateDeliveryOrder = new DeliveryOrder()
		updateDeliveryOrder.id = deliveryOrder.id
		updateDeliveryOrder.code = "code2"
		updateDeliveryOrder.salesOrder = salesOrder2
		updateDeliveryOrder.deliveryDate = new Date(2015, 4, 16)
		
		when: 'update is called'
		deliveryOrder = deliveryOrderService.updateObject(updateDeliveryOrder)
				
		then: 'check has no error'
		deliveryOrder.hasErrors() == false
		deliveryOrder.isDeleted == false
		deliveryOrder.isConfirmed == false
		deliveryOrder.code == updateDeliveryOrder.code
		deliveryOrder.salesOrder == updateDeliveryOrder.salesOrder
		deliveryOrder.deliveryDate == updateDeliveryOrder.deliveryDate
		println "Update success"
	}
	
	void "test update validation SalesOrder not null"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName";
		contact.phoneBook = "88723";
		contact.address = "Jln. Kopi";
		contact = contactService.createObject(contact)
		
		and: 'setting new Purchase Order'
		def salesOrder = new SalesOrder()
		salesOrder.code = "code1"
		salesOrder.contact = contact
		salesOrder.salesDate = new Date(2015, 3, 16)
		salesOrder = salesOrderService.createObject(salesOrder)
		
		and: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		and: 'setting Update DeliveryOrder'
		def updateDeliveryOrder = new DeliveryOrder()
		updateDeliveryOrder.id = deliveryOrder.id
		updateDeliveryOrder.code = "code2"
		updateDeliveryOrder.deliveryDate = new Date(2015, 4, 16)
		
		when: 'update is called'
		deliveryOrder = deliveryOrderService.updateObject(updateDeliveryOrder)
				
		then: 'check has error'
		deliveryOrder.hasErrors() == true
		println  "Validation success with error message : " + deliveryOrder.errors.getFieldError().defaultMessage
	}
	
	//-----------------------------END UPDATE
	
	//-----------------------------DELETE
	void "test softdelete DeliveryOrder"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName";
		contact.phoneBook = "88723";
		contact.address = "Jln. Kopi";
		contact = contactService.createObject(contact)
		
		and: 'setting new Purchase Order'
		def salesOrder = new SalesOrder()
		salesOrder.code = "code1"
		salesOrder.contact = contact
		salesOrder.salesDate = new Date(2015, 3, 16)
		salesOrder = salesOrderService.createObject(salesOrder)
		
		and: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		when: 'delete is called'
		deliveryOrder = deliveryOrderService.softDeleteObject(deliveryOrder)
				
		then: 'check has error'
		deliveryOrder.hasErrors() == false
		deliveryOrder.isDeleted == true
		println  "Delete Success"
	}
	
	void "test softdelete validation  deliveryOrder is confirmed"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName";
		contact.phoneBook = "88723";
		contact.address = "Jln. Kopi";
		contact = contactService.createObject(contact)
		
		and: 'setting new Purchase Order'
		def salesOrder = new SalesOrder()
		salesOrder.code = "code1"
		salesOrder.contact = contact
		salesOrder.salesDate = new Date(2015, 3, 16)
		salesOrder = salesOrderService.createObject(salesOrder)
		
		and: 'setting new Purchase Order'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder.isConfirmed = true
		deliveryOrder = deliveryOrder.save()
		 
		when: 'delete is called'
		deliveryOrder = deliveryOrderService.softDeleteObject(deliveryOrder)
				
		then: 'check has error'
		deliveryOrder.hasErrors() == true
		println  "Validation success with error message : " + deliveryOrder.errors.getGlobalError().defaultMessage
	}
	//-----------------------------END DELETE
	
	//-----------------------------CONFIRM
	void "test confirm DeliveryOrder"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName";
		contact.phoneBook = "88723";
		contact.address = "Jln. Kopi";
		contact = contactService.createObject(contact)
		
		and: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		item = itemService.createObject(item)
		
		and: 'setting new SalesOrder'
		def salesOrder = new SalesOrder()
		salesOrder.code = "code1"
		salesOrder.contact = contact
		salesOrder.salesDate = new Date(2015, 3, 16)
		salesOrder = salesOrderService.createObject(salesOrder)
		
		and : 'setting new SalesOrderDetail'
		def salesOrderDetail = new SalesOrderDetail()
		salesOrderDetail.salesOrder = salesOrder
		salesOrderDetail.code = "coded1"
		salesOrderDetail.item = item
		salesOrderDetail.quantity = 2
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		salesOrder.addToSalesOrderDetails(salesOrderDetail)
		
		and : 'confirm SalesOrder'
		salesOrder = salesOrderService.confirmObject(salesOrder)
		 
		and: 'setting new DeliveryOrder'
		DeliveryOrder deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		and : 'setting new DeliveryOrderDetail'
		def deliveryOrderDetail = new DeliveryOrderDetail()
		deliveryOrderDetail.deliveryOrder = deliveryOrder
		deliveryOrderDetail.code = "coded1"
		deliveryOrderDetail.salesOrderDetail = salesOrderDetail
		deliveryOrderDetail.quantity = 1
		deliveryOrderDetail = deliveryOrderDetailService.createObject(deliveryOrderDetail)
		deliveryOrder.addToDeliveryOrderDetails(deliveryOrderDetail)
		
		when: 'confirm is called'
		deliveryOrder = deliveryOrderService.confirmObject(deliveryOrder)
				
		then: 'check has error'
		deliveryOrder.hasErrors() == false
		deliveryOrder.isConfirmed == true
		deliveryOrder.deliveryOrderDetails.findAll{ it.isConfirmed == false && it.isDeleted == false }.size() == 0
		SalesOrderDetail.findById(salesOrderDetail.id).pendingDelivery == 1
		Item.findById(item.id).pendingDelivery == 1
		Item.findById(item.id).quantity == -1
		StockMutation.findBySourceDocumentIdAndSourceDocumentDetailId(deliveryOrder.id,deliveryOrderDetail.id).quantity == 1
		println  "Confirm success"
	}
	
	void "test confirm validation DeliveryOrder must has DeliveryOrderDetails"() {
		setup : 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName"
		contact.phoneBook = "88723"
		contact.address = "Jln. Kopi"
		contact = contactService.createObject(contact)
		
		and: 'setting new SalesOrder'
		def salesOrder = new SalesOrder()
		salesOrder.code = "code1"
		salesOrder.contact = contact
		salesOrder.salesDate = new Date(2015, 3, 16)
		salesOrder = salesOrderService.createObject(salesOrder)
		
		and: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		when: 'confirm is called'
		deliveryOrder = deliveryOrderService.confirmObject(deliveryOrder)
				
		then: 'check has error'
		deliveryOrder.hasErrors() == true
		println  "Validation success with error message : " + deliveryOrder.errors.getGlobalError().defaultMessage
	}
	
	//-----------------------------END CONFIRM
//	
	//-----------------------------UNCONFIRM
	void "test unconfirm DeliveryOrder"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName";
		contact.phoneBook = "88723";
		contact.address = "Jln. Kopi";
		contact = contactService.createObject(contact)
		
		and: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		item = itemService.createObject(item)
		
		and: 'setting new SalesOrder'
		def salesOrder = new SalesOrder()
		salesOrder.code = "code1"
		salesOrder.contact = contact
		salesOrder.salesDate = new Date(2015, 3, 16)
		salesOrder = salesOrderService.createObject(salesOrder)
		
		and : 'setting new SalesOrderDetail'
		def salesOrderDetail = new SalesOrderDetail()
		salesOrderDetail.salesOrder = salesOrder
		salesOrderDetail.code = "coded1"
		salesOrderDetail.item = item
		salesOrderDetail.quantity = 2
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		salesOrder.addToSalesOrderDetails(salesOrderDetail)
		
		and : 'confirm SalesOrder'
		salesOrder = salesOrderService.confirmObject(salesOrder)
		 
		and: 'setting new DeliveryOrder'
		DeliveryOrder deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		and : 'setting new DeliveryOrderDetail'
		def deliveryOrderDetail = new DeliveryOrderDetail()
		deliveryOrderDetail.deliveryOrder = deliveryOrder
		deliveryOrderDetail.code = "coded1"
		deliveryOrderDetail.salesOrderDetail = salesOrderDetail
		deliveryOrderDetail.quantity = 1
		deliveryOrderDetail = deliveryOrderDetailService.createObject(deliveryOrderDetail)
		deliveryOrder.addToDeliveryOrderDetails(deliveryOrderDetail)
		
		when: 'unconfirm is called'
		deliveryOrder = deliveryOrderService.confirmObject(deliveryOrder)
		deliveryOrder = deliveryOrderService.unconfirmObject(deliveryOrder)
		
		then: 'check has error'
		deliveryOrder.hasErrors() == false
		deliveryOrder.isConfirmed == false
		deliveryOrder.deliveryOrderDetails.findAll{ it.isConfirmed == true && it.isDeleted == false }.size() == 0
		SalesOrderDetail.findById(salesOrderDetail.id).pendingDelivery == 2
		Item.findById(item.id).pendingDelivery == 2
		Item.findById(item.id).quantity == 0
		StockMutation.findBySourceDocumentIdAndSourceDocumentDetailId(deliveryOrder.id,deliveryOrderDetail.id).quantity == 1
		println  "Unconfirm success"
	}
	
	void "test unconfirm validation DeliveryOrder isConfirmed must true"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName";
		contact.phoneBook = "88723";
		contact.address = "Jln. Kopi";
		contact = contactService.createObject(contact)
		
		and: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		item = itemService.createObject(item)
		
		and: 'setting new SalesOrder'
		def salesOrder = new SalesOrder()
		salesOrder.code = "code1"
		salesOrder.contact = contact
		salesOrder.salesDate = new Date(2015, 3, 16)
		salesOrder = salesOrderService.createObject(salesOrder)
		
		and : 'setting new SalesOrderDetail'
		def salesOrderDetail = new SalesOrderDetail()
		salesOrderDetail.salesOrder = salesOrder
		salesOrderDetail.code = "coded1"
		salesOrderDetail.item = item
		salesOrderDetail.quantity = 2
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		salesOrder.addToSalesOrderDetails(salesOrderDetail)
		
		and : 'confirm SalesOrder'
		salesOrder = salesOrderService.confirmObject(salesOrder)
		 
		and: 'setting new DeliveryOrder'
		DeliveryOrder deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		and : 'setting new DeliveryOrderDetail'
		def deliveryOrderDetail = new DeliveryOrderDetail()
		deliveryOrderDetail.deliveryOrder = deliveryOrder
		deliveryOrderDetail.code = "coded1"
		deliveryOrderDetail.salesOrderDetail = salesOrderDetail
		deliveryOrderDetail.quantity = 1
		deliveryOrderDetail = deliveryOrderDetailService.createObject(deliveryOrderDetail)
		deliveryOrder.addToDeliveryOrderDetails(deliveryOrderDetail)
		
		when: 'unconfirm is called'
		deliveryOrder = deliveryOrderService.unconfirmObject(deliveryOrder)
				
		then: 'check has error'
		deliveryOrder.hasErrors() == true
		println  "Validation success with error message : " + deliveryOrder.errors.getGlobalError().defaultMessage
	}
	//-----------------------------END UNCONFIRM
}
