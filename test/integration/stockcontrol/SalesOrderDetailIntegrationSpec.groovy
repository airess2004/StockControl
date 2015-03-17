package stockcontrol

import grails.test.spock.IntegrationSpec

class SalesOrderDetailIntegrationSpec extends IntegrationSpec {

    	def contactService
	def itemService
	def salesOrderService
	def salesOrderDetailService
	
    def setup() {
    }

    def cleanup() {
    }
	
	//-----------------------------------CREATE
    void "test create SalesOrderDetail"() {
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
		
		when : 'create is called'
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		
		then : 'check has no error'
		salesOrderDetail.hasErrors() == false
		salesOrderDetail.isDeleted == false
		salesOrderDetail.isConfirmed == false
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
		salesOrderDetail.quantity = 1
		
		when : 'create is called'
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		
		then : 'check has no error'
		salesOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + salesOrderDetail.errors.getFieldError().defaultMessage

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
		salesOrderDetail.quantity = 0
		salesOrderDetail.item = item
		
		when : 'create is called'
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		
		then : 'check has no error'
		salesOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + salesOrderDetail.errors.getFieldError().defaultMessage
	}
	
	void "test create validation salesorder not null"() {
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
		
		and : 'setting new SalesOrderDetail'
		def salesOrderDetail = new SalesOrderDetail()
		salesOrderDetail.code = "coded1"
		salesOrderDetail.quantity = 1
		salesOrderDetail.item = item
		
		when : 'create is called'
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		
		then : 'check has no error'
		salesOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + salesOrderDetail.errors.getFieldError().defaultMessage
	}
	
	//------------------------------------END CREATE
	//------------------------------------UPDATE
	void "test update SalesOrderDetail"() {
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
		salesOrderDetail.quantity = 1
		salesOrderDetail.item = item
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		
		and : 'setting new SalesOrderDetail'
		def updateSalesOrderDetail = new SalesOrderDetail()
		updateSalesOrderDetail.salesOrder = salesOrder
		updateSalesOrderDetail.id = salesOrderDetail.id
		updateSalesOrderDetail.code = "coded1"
		updateSalesOrderDetail.quantity = 2
		updateSalesOrderDetail.item = item2
		
		when: 'update is called'
		salesOrderDetail = salesOrderDetailService.updateObject(updateSalesOrderDetail)
				
		then: 'check has no error'
		salesOrder.hasErrors() == false
		salesOrderDetail.isDeleted == false
		salesOrderDetail.isConfirmed == false
		salesOrderDetail.code == updateSalesOrderDetail.code
		salesOrderDetail.item == updateSalesOrderDetail.item
		salesOrderDetail.quantity == updateSalesOrderDetail.quantity
		println "Update success"
	}
	
	void "test update validation item not null"() {
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
		salesOrderDetail.quantity = 1
		salesOrderDetail.item = item
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		
		and : 'setting new SalesOrderDetail'
		def updateSalesOrderDetail = new SalesOrderDetail()
		updateSalesOrderDetail.id = salesOrderDetail.id
		updateSalesOrderDetail.code = "coded1"
		updateSalesOrderDetail.quantity = 2
		
		when: 'update is called'
		salesOrderDetail = salesOrderDetailService.updateObject(updateSalesOrderDetail)
				
		then: 'check has no error'
		salesOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + salesOrderDetail.errors.getFieldError().defaultMessage
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
		salesOrderDetail.quantity = 1
		salesOrderDetail.item = item
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		
		and : 'setting new SalesOrderDetail'
		def updateSalesOrderDetail = new SalesOrderDetail()
		updateSalesOrderDetail.id = salesOrderDetail.id
		updateSalesOrderDetail.code = "coded1"
		updateSalesOrderDetail.quantity = 0
		updateSalesOrderDetail.item = item2
		
		when: 'update is called'
		salesOrderDetail = salesOrderDetailService.updateObject(updateSalesOrderDetail)
				
		then: 'check has no error'
		salesOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + salesOrderDetail.errors.getFieldError().defaultMessage
	}
	
	//------------------------------------END UPDATE
	//-------------------------------------DELETE
	void "test softdelete SalesOrder"() {
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
		salesOrderDetail.quantity = 1
		salesOrderDetail.item = item
		salesOrderDetail = salesOrderDetailService.createObject(salesOrderDetail)
		
		when: 'delete is called'
		salesOrderDetail = salesOrderDetailService.softDeleteObject(salesOrderDetail)
				
		then: 'check has error'
		salesOrderDetail.hasErrors() == false
		salesOrderDetail.isDeleted == true
		println  "Delete Success"
	}
	//----------------------------------------END DELETE
}
