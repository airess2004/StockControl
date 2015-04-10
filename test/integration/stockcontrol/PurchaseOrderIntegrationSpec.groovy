package stockcontrol

import grails.test.spock.IntegrationSpec

class PurchaseOrderIntegrationSpec extends IntegrationSpec {
	def purchaseOrderService
	def purchaseOrderDetailService
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
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2014,3,16)
		
		
		when: 'create is called'
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
				
		then: 'check has no error'
		purchaseOrder.hasErrors() == false
		purchaseOrder.isDeleted == false
		purchaseOrder.isConfirmed == false
		println "Create success"
	}
	
	void "test create validation customer not Null"() {
		setup: 'setting new Purchase Order'
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		
		when: 'create is called'
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
				
		then: 'check has error'
		purchaseOrder.hasErrors() == true
		println  "Validation success with error message : " + purchaseOrder.errors.getFieldError().defaultMessage

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
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and: 'setting Update Purchase Order'
		def updatePurchaseOrder = new PurchaseOrder()
		updatePurchaseOrder.id = purchaseOrder.id
		updatePurchaseOrder.code = "code2"
		updatePurchaseOrder.contact = contact2
		updatePurchaseOrder.purchaseDate = new Date(2015, 4, 16)
		
		when: 'update is called'
		purchaseOrder = purchaseOrderService.updateObject(updatePurchaseOrder)
				
		then: 'check has no error'
		purchaseOrder.hasErrors() == false
		purchaseOrder.isDeleted == false
		purchaseOrder.isConfirmed == false
		purchaseOrder.code == updatePurchaseOrder.code
		purchaseOrder.contact == updatePurchaseOrder.contact
		purchaseOrder.purchaseDate == updatePurchaseOrder.purchaseDate
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
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and: 'setting Update Purchase Order'
		def updatePurchaseOrder = new PurchaseOrder()
		updatePurchaseOrder.id = purchaseOrder.id
		updatePurchaseOrder.code = "code2"
		updatePurchaseOrder.purchaseDate = new Date(2015, 4, 16)
		
		when: 'update is called'
		purchaseOrder = purchaseOrderService.updateObject(updatePurchaseOrder)
				
		then: 'check has error'
		purchaseOrder.hasErrors() == true
		println  "Validation success with error message : " + purchaseOrder.errors.getFieldError().defaultMessage
	}
	
	//-----------------------------END UPDATE
	
	//-----------------------------DELETE
	void "test softdelete PurchaseOrder"() {
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
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		when: 'delete is called'
		purchaseOrder = purchaseOrderService.softDeleteObject(purchaseOrder)
				
		then: 'check has error'
		purchaseOrder.hasErrors() == false
		purchaseOrder.isDeleted == true
		println  "Delete Success"
	}
	
	void "test softdelete validation  purchaseOrder is confirmed"() {
		setup : 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName";
		contact.phoneBook = "88723";
		contact.address = "Jln. Kopi";
		contact = contactService.createObject(contact)
		
		and: 'setting new Purchase Order'
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder.isConfirmed = true
		purchaseOrder = purchaseOrder.save()
		 
		when: 'delete is called'
		purchaseOrder = purchaseOrderService.softDeleteObject(purchaseOrder)
				
		then: 'check has error'
		purchaseOrder.hasErrors() == true
		println  "Validation success with error message : " + purchaseOrder.errors.getGlobalError().defaultMessage
	}
	//-----------------------------END DELETE
	
	//-----------------------------CONFIRM
	void "test confirm PurchaseOrder"() {
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
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and : 'setting new PurchaseOrderDetail'
		def purchaseOrderDetail = new PurchaseOrderDetail()
		purchaseOrderDetail.purchaseOrder = purchaseOrder
		purchaseOrderDetail.code = "coded1"
		purchaseOrderDetail.item = item
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		purchaseOrder.addToPurchaseOrderDetails(purchaseOrderDetail)
		
		when: 'confirm is called'
		purchaseOrder = purchaseOrderService.confirmObject(purchaseOrder)
				
		then: 'check has error'
		purchaseOrder.hasErrors() == false
		purchaseOrder.isConfirmed == true
		println  "Confirm success"
	}
	
	void "test confirm validation PurchaseOrder must has PurchaseOrderDetails"() {
		setup : 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName"
		contact.phoneBook = "88723"
		contact.address = "Jln. Kopi"
		contact = contactService.createObject(contact)
		
		and: 'setting new Purchase Order'
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		when: 'delete is called'
		purchaseOrder = purchaseOrderService.confirmObject(purchaseOrder)
				
		then: 'check has error'
		purchaseOrder.hasErrors() == true
		println  "Validation success with error message : " + purchaseOrder.errors.getGlobalError().defaultMessage
	}
	
	//-----------------------------END CONFIRM
	
	//-----------------------------UNCONFIRM
	void "test unconfirm PurchaseOrder"() {
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
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and : 'setting new PurchaseOrderDetail'
		def purchaseOrderDetail = new PurchaseOrderDetail()
		purchaseOrderDetail.purchaseOrder = purchaseOrder
		purchaseOrderDetail.code = "coded1"
		purchaseOrderDetail.item = item
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		purchaseOrder.addToPurchaseOrderDetails(purchaseOrderDetail)
		
		when: 'confirm is called'
		purchaseOrder = purchaseOrderService.confirmObject(purchaseOrder)
		purchaseOrder = purchaseOrderService.unconfirmObject(purchaseOrder)
				
		then: 'check has error'
		purchaseOrder.hasErrors() == false
		purchaseOrder.isConfirmed == false
		purchaseOrder.confirmationDate == null
		println  "Confirm success"
	}
	
	void "test unconfirm validation PurchaseOrder isConfirmed must true"() {
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
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and : 'setting new PurchaseOrderDetail'
		def purchaseOrderDetail = new PurchaseOrderDetail()
		purchaseOrderDetail.purchaseOrder = purchaseOrder
		purchaseOrderDetail.code = "coded1"
		purchaseOrderDetail.item = item
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		purchaseOrder.addToPurchaseOrderDetails(purchaseOrderDetail)
		
		when: 'unconfirm is called'
		purchaseOrder = purchaseOrderService.unconfirmObject(purchaseOrder)
				
		then: 'check has error'
		purchaseOrder.hasErrors() == true
		println  "Validation success with error message : " + purchaseOrder.errors.getGlobalError().defaultMessage
	}
	//-----------------------------END UNCONFIRM
}
