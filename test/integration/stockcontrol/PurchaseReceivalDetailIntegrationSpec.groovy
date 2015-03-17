package stockcontrol

import grails.test.spock.IntegrationSpec

class PurchaseReceivalDetailIntegrationSpec extends IntegrationSpec {
	def contactService
	def itemService
	def purchaseReceivalService
	def purchaseReceivalDetailService
	def purchaseOrderService
	def purchaseOrderDetailService
	
	def setup() {
	}

	def cleanup() {
	}
	
	//-----------------------------------CREATE
	void "test create PurchaseReceivalDetail"() {
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
		
		and: 'setting new PurchaseReceival'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
		
		and : 'setting new PurchaseReceivalDetail'
		def purchaseReceivalDetail = new PurchaseReceivalDetail()
		purchaseReceivalDetail.purchaseReceival = purchaseReceival
		purchaseReceivalDetail.code = "coded1"
		purchaseReceivalDetail.purchaseOrderDetail = purchaseOrderDetail
		purchaseReceivalDetail.quantity = 1
		
		when : 'create is called'
		purchaseReceivalDetail = purchaseReceivalDetailService.createObject(purchaseReceivalDetail)
		
		then : 'check has no error'
		purchaseReceivalDetail.hasErrors() == false
		purchaseReceivalDetail.isDeleted == false
		purchaseReceivalDetail.isConfirmed == false
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
		purchaseOrderDetail.item = item
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		purchaseOrder.addToPurchaseOrderDetails(purchaseOrderDetail)
		
		and: 'setting new PurchaseReceival'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
		
		and : 'setting new PurchaseReceivalDetail'
		def purchaseReceivalDetail = new PurchaseReceivalDetail()
		purchaseReceivalDetail.purchaseReceival = purchaseReceival
		purchaseReceivalDetail.code = "coded1"
		purchaseReceivalDetail.quantity = 1
		
		when : 'create is called'
		purchaseReceivalDetail = purchaseReceivalDetailService.createObject(purchaseReceivalDetail)
		
		then : 'check has no error'
		purchaseReceivalDetail.hasErrors() == true
		println  "Validation success with error message : " + purchaseReceivalDetail.errors.getFieldError().defaultMessage

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
		purchaseOrderDetail.item = item
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		purchaseOrder.addToPurchaseOrderDetails(purchaseOrderDetail)
		
		
		and: 'setting new PurchaseReceival'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
		
		and : 'setting new PurchaseReceivalDetail'
		def purchaseReceivalDetail = new PurchaseReceivalDetail()
		purchaseReceivalDetail.purchaseReceival = purchaseReceival
		purchaseReceivalDetail.code = "coded1"
		purchaseReceivalDetail.purchaseOrderDetail = purchaseOrderDetail
		purchaseReceivalDetail.quantity = 0
		
		when : 'create is called'
		purchaseReceivalDetail = purchaseReceivalDetailService.createObject(purchaseReceivalDetail)
		
		then : 'check has no error'
		purchaseReceivalDetail.hasErrors() == true
		println  "Validation success with error message : " + purchaseReceivalDetail.errors.getFieldError().defaultMessage
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
		
		and: 'setting new PurchaseReceival'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
		
		and : 'setting new PurchaseReceivalDetail'
		def purchaseReceivalDetail = new PurchaseReceivalDetail()
		purchaseReceivalDetail.purchaseReceival = purchaseReceival
		purchaseReceivalDetail.code = "coded1"
		purchaseReceivalDetail.quantity = 1
		
		when : 'create is called'
		purchaseReceivalDetail = purchaseReceivalDetailService.createObject(purchaseReceivalDetail)
		
		then : 'check has no error'
		purchaseReceivalDetail.hasErrors() == true
		println  "Validation success with error message : " + purchaseReceivalDetail.errors.getFieldError().defaultMessage
	}
	
	//------------------------------------END CREATE
	//------------------------------------UPDATE
	void "test update PurchaseReceivalDetail"() {
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
		purchaseOrderDetail.item = item
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		purchaseOrder.addToPurchaseOrderDetails(purchaseOrderDetail)
		
		and : 'setting new PurchaseOrderDetail2'
		def purchaseOrderDetail2 = new PurchaseOrderDetail()
		purchaseOrderDetail2.purchaseOrder = purchaseOrder
		purchaseOrderDetail2.code = "coded1"
		purchaseOrderDetail2.item = item
		purchaseOrderDetail2.quantity = 1
		purchaseOrderDetail2 = purchaseOrderDetailService.createObject(purchaseOrderDetail2)
		purchaseOrder.addToPurchaseOrderDetails(purchaseOrderDetail2)
		
		and: 'setting new PurchaseReceival'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
		
		and : 'setting new PurchaseReceivalDetail'
		def purchaseReceivalDetail = new PurchaseReceivalDetail()
		purchaseReceivalDetail.purchaseReceival = purchaseReceival
		purchaseReceivalDetail.code = "coded1"
		purchaseReceivalDetail.quantity = 1
		purchaseReceivalDetail.purchaseOrderDetail = purchaseOrderDetail
		purchaseReceivalDetail = purchaseReceivalDetailService.createObject(purchaseReceivalDetail)
		purchaseReceival.addToPurchaseReceivalDetails(purchaseReceivalDetail)
		
		and : 'setting Update PurchaseReceivalDetail'
		def updatePurchaseReceivalDetail = new PurchaseReceivalDetail()
		updatePurchaseReceivalDetail.id = purchaseReceivalDetail.id
		updatePurchaseReceivalDetail.purchaseReceival = purchaseReceival
		updatePurchaseReceivalDetail.code = "coded2"
		updatePurchaseReceivalDetail.quantity = 2
		updatePurchaseReceivalDetail.purchaseOrderDetail = purchaseOrderDetail2
		
		when: 'update is called'
		purchaseReceivalDetail = purchaseReceivalDetailService.updateObject(updatePurchaseReceivalDetail)
				
		then: 'check has no error'
		purchaseReceivalDetail.hasErrors() == false
		purchaseReceivalDetail.isDeleted == false
		purchaseReceivalDetail.code == updatePurchaseReceivalDetail.code
		purchaseReceivalDetail.purchaseOrderDetail == updatePurchaseReceivalDetail.purchaseOrderDetail
		purchaseReceivalDetail.quantity == updatePurchaseReceivalDetail.quantity
		println "Update success"
	}
	
	void "test update validation purchaseOrderDetail not null"() {
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
		purchaseOrderDetail.item = item
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		purchaseOrder.addToPurchaseOrderDetails(purchaseOrderDetail)
		
		and: 'setting new PurchaseReceival'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
		
		and : 'setting new PurchaseReceivalDetail'
		def purchaseReceivalDetail = new PurchaseReceivalDetail()
		purchaseReceivalDetail.purchaseReceival = purchaseReceival
		purchaseReceivalDetail.code = "coded1"
		purchaseReceivalDetail.quantity = 1
		purchaseReceivalDetail.purchaseOrderDetail = purchaseOrderDetail
		purchaseReceivalDetail = purchaseReceivalDetailService.createObject(purchaseReceivalDetail)
		purchaseReceival.addToPurchaseReceivalDetails(purchaseReceivalDetail)
		
		and : 'setting new PurchaseReceivalDetail'
		def updatePurchaseReceivalDetail = new PurchaseReceivalDetail()
		updatePurchaseReceivalDetail.id = purchaseReceivalDetail.id
		updatePurchaseReceivalDetail.purchaseReceival = purchaseReceival
		updatePurchaseReceivalDetail.code = "coded1"
		updatePurchaseReceivalDetail.quantity = 2
		updatePurchaseReceivalDetail.purchaseOrderDetail = purchaseOrderDetail
		
		when: 'update is called'
		purchaseReceivalDetail = purchaseReceivalDetailService.updateObject(updatePurchaseReceivalDetail)
				
		then: 'check has no error'
		purchaseReceivalDetail.hasErrors() == true
		println  "Validation success with error message : " + purchaseReceivalDetail.errors.getFieldError().defaultMessage
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
		purchaseOrderDetail.item = item
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		purchaseOrder.addToPurchaseOrderDetails(purchaseOrderDetail)
		
		and: 'setting new PurchaseReceival'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
		
		and : 'setting new PurchaseReceivalDetail'
		def purchaseReceivalDetail = new PurchaseReceivalDetail()
		purchaseReceivalDetail.purchaseReceival = purchaseReceival
		purchaseReceivalDetail.code = "coded1"
		purchaseReceivalDetail.quantity = 1
		purchaseReceivalDetail.purchaseOrderDetail = purchaseOrderDetail
		purchaseReceivalDetail = purchaseReceivalDetailService.createObject(purchaseReceivalDetail)
		purchaseReceival.addToPurchaseReceivalDetails(purchaseReceivalDetail)
		
		and : 'setting new PurchaseReceivalDetail'
		def updatePurchaseReceivalDetail = new PurchaseReceivalDetail()
		updatePurchaseReceivalDetail.id = purchaseReceivalDetail.id
		updatePurchaseReceivalDetail.purchaseReceival = purchaseReceival
		updatePurchaseReceivalDetail.code = "coded1"
		updatePurchaseReceivalDetail.quantity = 0
		updatePurchaseReceivalDetail.purchaseOrderDetail = purchaseOrderDetail
		
		when: 'update is called'
		purchaseReceivalDetail = purchaseReceivalDetailService.updateObject(updatePurchaseReceivalDetail)
				
		then: 'check has no error'
		purchaseReceivalDetail.hasErrors() == true
		println  "Validation success with error message : " + purchaseReceivalDetail.errors.getFieldError().defaultMessage
	}
	
	//------------------------------------END UPDATE
	//-------------------------------------DELETE
	void "test softdelete PurchaseReceival"() {
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
		purchaseOrderDetail.item = item
		purchaseOrderDetail.quantity = 1
		purchaseOrderDetail = purchaseOrderDetailService.createObject(purchaseOrderDetail)
		purchaseOrder.addToPurchaseOrderDetails(purchaseOrderDetail)
		
		and: 'setting new PurchaseReceival'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
		
		and : 'setting new PurchaseReceivalDetail'
		def purchaseReceivalDetail = new PurchaseReceivalDetail()
		purchaseReceivalDetail.purchaseReceival = purchaseReceival
		purchaseReceivalDetail.code = "coded1"
		purchaseReceivalDetail.quantity = 1
		purchaseReceivalDetail.purchaseOrderDetail = purchaseOrderDetail
		purchaseReceivalDetail = purchaseReceivalDetailService.createObject(purchaseReceivalDetail)
		
		when: 'delete is called'
		purchaseReceivalDetail = purchaseReceivalDetailService.softDeleteObject(purchaseReceivalDetail)
				
		then: 'check has error'
		purchaseReceivalDetail.hasErrors() == false
		purchaseReceivalDetail.isDeleted == true
		println  "Delete Success"
	}
	//----------------------------------------END DELETE

}
