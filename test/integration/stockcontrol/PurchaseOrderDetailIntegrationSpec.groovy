package stockcontrol

import grails.test.spock.IntegrationSpec

class PurchaseOrderDetailIntegrationSpec extends IntegrationSpec {
	def contactService
	def itemService
	def purchaseOrderService
	def purchaseOrderDetailService
	
    def setup() {
    }

    def cleanup() {
    }
	
	//-----------------------------------CREATE
    void "test create PurchaseOrderDetail"() {
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
		
		when : 'create is called'
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		
		then : 'check has no error'
		purchaseOrderDetail.hasErrors() == false
		purchaseOrderDetail.isDeleted == false
		purchaseOrderDetail.isConfirmed == false
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
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and : 'setting new PurchaseOrderDetail'
		def purchaseOrderDetail = new PurchaseOrderDetail()
		purchaseOrderDetail.purchaseOrder = purchaseOrder
		purchaseOrderDetail.code = "coded1"
		purchaseOrderDetail.quantity = 1
		
		when : 'create is called'
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		
		then : 'check has no error'
		purchaseOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + purchaseOrderDetail.errors.getFieldError().defaultMessage

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
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and : 'setting new PurchaseOrderDetail'
		def purchaseOrderDetail = new PurchaseOrderDetail()
		purchaseOrderDetail.purchaseOrder = purchaseOrder
		purchaseOrderDetail.code = "coded1"
		purchaseOrderDetail.quantity = 0
		purchaseOrderDetail.item = item
		
		when : 'create is called'
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		
		then : 'check has no error'
		purchaseOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + purchaseOrderDetail.errors.getFieldError().defaultMessage
	}
	
	void "test create validation purchase order not null"() {
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
		
		and : 'setting new PurchaseOrderDetail'
		def purchaseOrderDetail = new PurchaseOrderDetail()
		purchaseOrderDetail.code = "coded1"
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail.item = item
		
		when : 'create is called'
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		
		then : 'check has no error'
		purchaseOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + purchaseOrderDetail.errors.getFieldError().defaultMessage
	}
	
	//------------------------------------END CREATE
	//------------------------------------UPDATE
	void "test update PurchaseOrderDetail"() {
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
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and : 'setting new PurchaseOrderDetail'
		def purchaseOrderDetail = new PurchaseOrderDetail()
		purchaseOrderDetail.purchaseOrder = purchaseOrder
		purchaseOrderDetail.code = "coded1"
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail.item = item
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		
		and : 'setting new PurchaseOrderDetail'
		def updatePurchaseOrderDetail = new PurchaseOrderDetail()
		updatePurchaseOrderDetail.id = purchaseOrderDetail.id
		updatePurchaseOrderDetail.code = "coded1"
		updatePurchaseOrderDetail.quantity = 2
		updatePurchaseOrderDetail.item = item2
		
		when: 'update is called'
		purchaseOrderDetail = purchaseOrderDetailService.updateObject(updatePurchaseOrderDetail)
				
		then: 'check has no error'
		purchaseOrder.hasErrors() == false
		purchaseOrderDetail.isDeleted == false
		purchaseOrderDetail.isConfirmed == false
		purchaseOrderDetail.code == updatePurchaseOrderDetail.code
		purchaseOrderDetail.item == updatePurchaseOrderDetail.item
		purchaseOrderDetail.quantity == updatePurchaseOrderDetail.quantity
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
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and : 'setting new PurchaseOrderDetail'
		def purchaseOrderDetail = new PurchaseOrderDetail()
		purchaseOrderDetail.purchaseOrder = purchaseOrder
		purchaseOrderDetail.code = "coded1"
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail.item = item
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		
		and : 'setting new PurchaseOrderDetail'
		def updatePurchaseOrderDetail = new PurchaseOrderDetail()
		updatePurchaseOrderDetail.id = purchaseOrderDetail.id
		updatePurchaseOrderDetail.code = "coded1"
		updatePurchaseOrderDetail.quantity = 2
		
		when: 'update is called'
		purchaseOrderDetail = purchaseOrderDetailService.updateObject(updatePurchaseOrderDetail)
				
		then: 'check has no error'
		purchaseOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + purchaseOrderDetail.errors.getFieldError().defaultMessage
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
		def purchaseOrder = new PurchaseOrder()
		purchaseOrder.code = "code1"
		purchaseOrder.contact = contact
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and : 'setting new PurchaseOrderDetail'
		def purchaseOrderDetail = new PurchaseOrderDetail()
		purchaseOrderDetail.purchaseOrder = purchaseOrder
		purchaseOrderDetail.code = "coded1"
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail.item = item
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		
		and : 'setting new PurchaseOrderDetail'
		def updatePurchaseOrderDetail = new PurchaseOrderDetail()
		updatePurchaseOrderDetail.id = purchaseOrderDetail.id
		updatePurchaseOrderDetail.code = "coded1"
		updatePurchaseOrderDetail.quantity = 0
		updatePurchaseOrderDetail.item = item2
		
		when: 'update is called'
		purchaseOrderDetail = purchaseOrderDetailService.updateObject(updatePurchaseOrderDetail)
				
		then: 'check has no error'
		purchaseOrderDetail.hasErrors() == true
		println  "Validation success with error message : " + purchaseOrderDetail.errors.getFieldError().defaultMessage
	}
	
	//------------------------------------END UPDATE
	//-------------------------------------DELETE
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
		
		and : 'setting new PurchaseOrderDetail'
		def purchaseOrderDetail = new PurchaseOrderDetail()
		purchaseOrderDetail.purchaseOrder = purchaseOrder
		purchaseOrderDetail.code = "coded1"
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail.item = item
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		
		when: 'delete is called'
		purchaseOrderDetail = purchaseOrderDetailService.softDeleteObject(purchaseOrderDetail)
				
		then: 'check has error'
		purchaseOrderDetail.hasErrors() == false
		purchaseOrderDetail.isDeleted == true
		println  "Delete Success"
	}
	//----------------------------------------END DELETE
}
