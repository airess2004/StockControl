package stockcontrol

import grails.test.spock.IntegrationSpec

class SalesOrderIntegrationSpec extends IntegrationSpec {

   def salesOrderService
	def salesOrderDetailService
	def contactService
	def itemService
	
    def setup() {
    }

    def cleanup() {
    }
	//----------------------------------CREATE
    void "test create Purchase Order"() {
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
		
		when: 'create is called'
		salesOrder = salesOrderService.createObject(salesOrder)
				
		then: 'check has no error'
		salesOrder.hasErrors() == false
		salesOrder.isDeleted == false
		salesOrder.isConfirmed == false
		println "Create success"
	}
	
	void "test create validation customer not Null"() {
		setup: 'setting new Purchase Order'
		def salesOrder = new SalesOrder()
		salesOrder.code = "code1"
		salesOrder.salesDate = new Date(2015, 3, 16)
		
		when: 'create is called'
		salesOrder = salesOrderService.createObject(salesOrder)
				
		then: 'check has error'
		salesOrder.hasErrors() == true
		println  "Validation success with error message : " + salesOrder.errors.getFieldError().defaultMessage

	}
	//-----------------------------END CREATE
	
	//-----------------------------UPDATE
	void "test update Purchase Order"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName";
		contact.phoneBook = "88723";
		contact.address = "Jln. Kopi";
		contact = contactService.createObject(contact)
		
		and: 'setting new Contact2'
		def contact2 = new Contact()
		contact2.name = "contactName2";
		contact2.phoneBook = "887232";
		contact2.address = "Jln. Kopi2";
		contact2 = contactService.createObject(contact2)
		
		and: 'setting new Purchase Order'
		def salesOrder = new SalesOrder()
		salesOrder.code = "code1"
		salesOrder.contact = contact
		salesOrder.salesDate = new Date(2015, 3, 16)
		salesOrder = salesOrderService.createObject(salesOrder)
		
		and: 'setting Update Purchase Order'
		def updateSalesOrder = new SalesOrder()
		updateSalesOrder.id = salesOrder.id
		updateSalesOrder.code = "code2"
		updateSalesOrder.contact = contact2
		updateSalesOrder.salesDate = new Date(2015, 4, 16)
		
		when: 'update is called'
		salesOrder = salesOrderService.updateObject(updateSalesOrder)
				
		then: 'check has no error'
		salesOrder.hasErrors() == false
		salesOrder.isDeleted == false
		salesOrder.isConfirmed == false
		salesOrder.code == updateSalesOrder.code
		salesOrder.contact == updateSalesOrder.contact
		salesOrder.salesDate == updateSalesOrder.salesDate
		println "Update success"
	}
	
	void "test update validation customer not null"() {
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
		
		and: 'setting Update Purchase Order'
		def updateSalesOrder = new SalesOrder()
		updateSalesOrder.id = salesOrder.id
		updateSalesOrder.code = "code2"
		updateSalesOrder.salesDate = new Date(2015, 4, 16)
		
		when: 'update is called'
		salesOrder = salesOrderService.updateObject(updateSalesOrder)
				
		then: 'check has error'
		salesOrder.hasErrors() == true
		println  "Validation success with error message : " + salesOrder.errors.getFieldError().defaultMessage
	}
	
	//-----------------------------END UPDATE
	
	//-----------------------------DELETE
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
		
		when: 'delete is called'
		salesOrder = salesOrderService.softDeleteObject(salesOrder)
				
		then: 'check has error'
		salesOrder.hasErrors() == false
		salesOrder.isDeleted == true
		println  "Delete Success"
	}
	
	void "test softdelete validation  salesOrder is confirmed"() {
		setup : 'setting new Contact'
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
		salesOrder.isConfirmed = true
		salesOrder = salesOrder.save()
		 
		when: 'delete is called'
		salesOrder = salesOrderService.softDeleteObject(salesOrder)
				
		then: 'check has error'
		salesOrder.hasErrors() == true
		println  "Validation success with error message : " + salesOrder.errors.getGlobalError().defaultMessage
	}
	//-----------------------------END DELETE
	
	//-----------------------------CONFIRM
	void "test confirm SalesOrder"() {
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
		
		when: 'confirm is called'
		salesOrder = salesOrderService.confirmObject(salesOrder)
				
		then: 'check has error'
		salesOrder.hasErrors() == false
		salesOrder.isConfirmed == true
		println  "Confirm success"
	}
	
	void "test confirm validation SalesOrder must has SalesOrderDetails"() {
		setup : 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName"
		contact.phoneBook = "88723"
		contact.address = "Jln. Kopi"
		contact = contactService.createObject(contact)
		
		and: 'setting new Purchase Order'
		def salesOrder = new SalesOrder()
		salesOrder.code = "code1"
		salesOrder.contact = contact
		salesOrder.salesDate = new Date(2015, 3, 16)
		salesOrder = salesOrderService.createObject(salesOrder)
		
		when: 'delete is called'
		salesOrder = salesOrderService.confirmObject(salesOrder)
				
		then: 'check has error'
		salesOrder.hasErrors() == true
		println  "Validation success with error message : " + salesOrder.errors.getGlobalError().defaultMessage
	}
	
	//-----------------------------END CONFIRM
	
	//-----------------------------UNCONFIRM
	void "test unconfirm SalesOrder"() {
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
		
		when: 'confirm is called'
		salesOrder = salesOrderService.confirmObject(salesOrder)
		salesOrder = salesOrderService.unconfirmObject(salesOrder)
				
		then: 'check has error'
		salesOrder.hasErrors() == false
		salesOrder.isConfirmed == false
		salesOrder.confirmationDate == null
		println  "Confirm success"
	}
	
	void "test unconfirm validation SalesOrder isConfirmed must true"() {
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
		
		when: 'unconfirm is called'
		salesOrder = salesOrderService.unconfirmObject(salesOrder)
				
		then: 'check has error'
		salesOrder.hasErrors() == true
		println  "Validation success with error message : " + salesOrder.errors.getGlobalError().defaultMessage
	}
	//-----------------------------END UNCONFIRM
}
