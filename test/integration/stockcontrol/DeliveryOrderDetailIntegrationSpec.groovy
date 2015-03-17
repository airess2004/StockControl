package stockcontrol

import grails.test.spock.IntegrationSpec

class DeliveryOrderDetailIntegrationSpec extends IntegrationSpec {

   def contactService
	def itemService
	def deliveryOrderService
	def deliveryOrderDetailService
	def salesOrderService
	def salesOrderDetailService
	
	def setup() {
	}

	def cleanup() {
	}
	
	//-----------------------------------CREATE
	void "test create DeliveryOrderDetail"() {
		setup : 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName"
		contact.phoneBook = "88723"
		contact.address = "Jln. Kopi"
		contact = contactService.createObject(contact)
		
		and: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		item = itemService.createObject(item)
		
		and: 'setting new Purchase Order'
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
		salesOrderDetail.quantity = 1
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		salesOrder.addToSalesOrderDetails(salesOrderDetail)
		
		and: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
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
		
		when : 'create is called'
		deliveryOrderDetail = deliveryOrderDetailService.createObject(deliveryOrderDetail)
		
		then : 'check has no error'
		deliveryOrderDetail.hasErrors() == false
		deliveryOrderDetail.isDeleted == false
		deliveryOrderDetail.isConfirmed == false
		println "Create success"
	}
	
	void "test create validation item not null"() {
		setup : 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName"
		contact.phoneBook = "88723"
		contact.address = "Jln. Kopi"
		contact = contactService.createObject(contact)
		
		and: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		item = itemService.createObject(item)
		
		and: 'setting new Purchase Order'
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
		salesOrderDetail.quantity = 1
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		salesOrder.addToSalesOrderDetails(salesOrderDetail)
		
		and: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		and : 'setting new DeliveryOrderDetail'
		def deliveryOrderDetail = new DeliveryOrderDetail()
		deliveryOrderDetail.deliveryOrder = deliveryOrder
		deliveryOrderDetail.code = "coded1"
		deliveryOrderDetail.quantity = 1
		
		when : 'create is called'
		deliveryOrderDetail = deliveryOrderDetailService.createObject(deliveryOrderDetail)
		
		then : 'check has no error'
		deliveryOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + deliveryOrderDetail.errors.getFieldError().defaultMessage

	}
	
	void "test create validation quantity is <= 0"() {
		setup : 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName"
		contact.phoneBook = "88723"
		contact.address = "Jln. Kopi"
		contact = contactService.createObject(contact)
		
		and: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		item = itemService.createObject(item)
		
		and: 'setting new Purchase Order'
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
		salesOrderDetail.quantity = 1
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		salesOrder.addToSalesOrderDetails(salesOrderDetail)
		
		
		and: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		and : 'setting new DeliveryOrderDetail'
		def deliveryOrderDetail = new DeliveryOrderDetail()
		deliveryOrderDetail.deliveryOrder = deliveryOrder
		deliveryOrderDetail.code = "coded1"
		deliveryOrderDetail.salesOrderDetail = salesOrderDetail
		deliveryOrderDetail.quantity = 0
		
		when : 'create is called'
		deliveryOrderDetail = deliveryOrderDetailService.createObject(deliveryOrderDetail)
		
		then : 'check has no error'
		deliveryOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + deliveryOrderDetail.errors.getFieldError().defaultMessage
	}
	
	void "test create validation purchase order detail not null"() {
		setup : 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName"
		contact.phoneBook = "88723"
		contact.address = "Jln. Kopi"
		contact = contactService.createObject(contact)
		
		and: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		item = itemService.createObject(item)
		
		and: 'setting new Purchase Order'
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
		salesOrderDetail.quantity = 1
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		salesOrder.addToSalesOrderDetails(salesOrderDetail)
		
		and: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		and : 'setting new DeliveryOrderDetail'
		def deliveryOrderDetail = new DeliveryOrderDetail()
		deliveryOrderDetail.deliveryOrder = deliveryOrder
		deliveryOrderDetail.code = "coded1"
		deliveryOrderDetail.quantity = 1
		
		when : 'create is called'
		deliveryOrderDetail = deliveryOrderDetailService.createObject(deliveryOrderDetail)
		
		then : 'check has no error'
		deliveryOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + deliveryOrderDetail.errors.getFieldError().defaultMessage
	}
	
	//------------------------------------END CREATE
	//------------------------------------UPDATE
	void "test update DeliveryOrderDetail"() {
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
				
		and: 'setting new Item2'
		def item2 = new Item()
		item2.sku = "newsku"
		item2.description = "ItemName"
		item2 = itemService.createObject(item2)
		
		and: 'setting new Purchase Order'
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
		salesOrderDetail.quantity = 1
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		salesOrder.addToSalesOrderDetails(salesOrderDetail)
		
		and : 'setting new SalesOrderDetail2'
		def salesOrderDetail2 = new SalesOrderDetail()
		salesOrderDetail2.salesOrder = salesOrder
		salesOrderDetail2.code = "coded1"
		salesOrderDetail2.item = item
		salesOrderDetail2.quantity = 1
		salesOrderDetail2 = salesOrderDetailService.createObject(salesOrderDetail2)
		salesOrder.addToSalesOrderDetails(salesOrderDetail2)
		
		and: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		and : 'setting new DeliveryOrderDetail'
		def deliveryOrderDetail = new DeliveryOrderDetail()
		deliveryOrderDetail.deliveryOrder = deliveryOrder
		deliveryOrderDetail.code = "coded1"
		deliveryOrderDetail.quantity = 1
		deliveryOrderDetail.salesOrderDetail = salesOrderDetail
		deliveryOrderDetail = deliveryOrderDetailService.createObject(deliveryOrderDetail)
		deliveryOrder.addToDeliveryOrderDetails(deliveryOrderDetail)
		
		and : 'setting Update DeliveryOrderDetail'
		def updateDeliveryOrderDetail = new DeliveryOrderDetail()
		updateDeliveryOrderDetail.id = deliveryOrderDetail.id
		updateDeliveryOrderDetail.deliveryOrder = deliveryOrder
		updateDeliveryOrderDetail.code = "coded2"
		updateDeliveryOrderDetail.quantity = 2
		updateDeliveryOrderDetail.salesOrderDetail = salesOrderDetail2
		
		when: 'update is called'
		deliveryOrderDetail = deliveryOrderDetailService.updateObject(updateDeliveryOrderDetail)
				
		then: 'check has no error'
		deliveryOrderDetail.hasErrors() == false
		deliveryOrderDetail.isDeleted == false
		deliveryOrderDetail.code == updateDeliveryOrderDetail.code
		deliveryOrderDetail.salesOrderDetail == updateDeliveryOrderDetail.salesOrderDetail
		deliveryOrderDetail.quantity == updateDeliveryOrderDetail.quantity
		println "Update success"
	}
	
	void "test update validation salesOrderDetail not null"() {
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
				
		and: 'setting new Item2'
		def item2 = new Item()
		item2.sku = "newsku"
		item2.description = "ItemName"
		item2 = itemService.createObject(item2)
		
		and: 'setting new Purchase Order'
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
		salesOrderDetail.quantity = 1
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		salesOrder.addToSalesOrderDetails(salesOrderDetail)
		
		and: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		and : 'setting new DeliveryOrderDetail'
		def deliveryOrderDetail = new DeliveryOrderDetail()
		deliveryOrderDetail.deliveryOrder = deliveryOrder
		deliveryOrderDetail.code = "coded1"
		deliveryOrderDetail.quantity = 1
		deliveryOrderDetail.salesOrderDetail = salesOrderDetail
		deliveryOrderDetail = deliveryOrderDetailService.createObject(deliveryOrderDetail)
		deliveryOrder.addToDeliveryOrderDetails(deliveryOrderDetail)
		
		and : 'setting new DeliveryOrderDetail'
		def updateDeliveryOrderDetail = new DeliveryOrderDetail()
		updateDeliveryOrderDetail.id = deliveryOrderDetail.id
		updateDeliveryOrderDetail.deliveryOrder = deliveryOrder
		updateDeliveryOrderDetail.code = "coded1"
		updateDeliveryOrderDetail.quantity = 2
		updateDeliveryOrderDetail.salesOrderDetail = salesOrderDetail
		
		when: 'update is called'
		deliveryOrderDetail = deliveryOrderDetailService.updateObject(updateDeliveryOrderDetail)
				
		then: 'check has no error'
		deliveryOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + deliveryOrderDetail.errors.getFieldError().defaultMessage
	}
	
	void "test update validation quantity is <= 0"() {
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
				
		and: 'setting new Item2'
		def item2 = new Item()
		item2.sku = "newsku"
		item2.description = "ItemName"
		item2 = itemService.createObject(item2)
		
		and: 'setting new Purchase Order'
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
		salesOrderDetail.quantity = 1
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		salesOrder.addToSalesOrderDetails(salesOrderDetail)
		
		and: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		and : 'setting new DeliveryOrderDetail'
		def deliveryOrderDetail = new DeliveryOrderDetail()
		deliveryOrderDetail.deliveryOrder = deliveryOrder
		deliveryOrderDetail.code = "coded1"
		deliveryOrderDetail.quantity = 1
		deliveryOrderDetail.salesOrderDetail = salesOrderDetail
		deliveryOrderDetail = deliveryOrderDetailService.createObject(deliveryOrderDetail)
		deliveryOrder.addToDeliveryOrderDetails(deliveryOrderDetail)
		
		and : 'setting new DeliveryOrderDetail'
		def updateDeliveryOrderDetail = new DeliveryOrderDetail()
		updateDeliveryOrderDetail.id = deliveryOrderDetail.id
		updateDeliveryOrderDetail.deliveryOrder = deliveryOrder
		updateDeliveryOrderDetail.code = "coded1"
		updateDeliveryOrderDetail.quantity = 0
		updateDeliveryOrderDetail.salesOrderDetail = salesOrderDetail
		
		when: 'update is called'
		deliveryOrderDetail = deliveryOrderDetailService.updateObject(updateDeliveryOrderDetail)
				
		then: 'check has no error'
		deliveryOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + deliveryOrderDetail.errors.getFieldError().defaultMessage
	}
	
	//------------------------------------END UPDATE
	//-------------------------------------DELETE
	void "test softdelete DeliveryOrder"() {
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
		
		and: 'setting new Purchase Order'
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
		salesOrderDetail.quantity = 1
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		salesOrder.addToSalesOrderDetails(salesOrderDetail)
		
		and: 'setting new DeliveryOrder'
		def deliveryOrder = new DeliveryOrder()
		deliveryOrder.code = "code1"
		deliveryOrder.salesOrder = salesOrder
		deliveryOrder.deliveryDate = new Date(2015, 3, 16)
		deliveryOrder = deliveryOrderService.createObject(deliveryOrder)
		
		and : 'setting new DeliveryOrderDetail'
		def deliveryOrderDetail = new DeliveryOrderDetail()
		deliveryOrderDetail.deliveryOrder = deliveryOrder
		deliveryOrderDetail.code = "coded1"
		deliveryOrderDetail.quantity = 1
		deliveryOrderDetail.salesOrderDetail = salesOrderDetail
		deliveryOrderDetail = deliveryOrderDetailService.createObject(deliveryOrderDetail)
		
		when: 'delete is called'
		deliveryOrderDetail = deliveryOrderDetailService.softDeleteObject(deliveryOrderDetail)
				
		then: 'check has error'
		deliveryOrderDetail.hasErrors() == false
		deliveryOrderDetail.isDeleted == true
		println  "Delete Success"
	}
	//----------------------------------------END DELETE

}
