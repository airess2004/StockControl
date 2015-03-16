package stockcontrol

import grails.test.spock.IntegrationSpec

class PurchaseReceivalIntegrationSpec extends IntegrationSpec {

    def purchaseReceivalService
	def purchaseReceivalDetailService
	def contactService
	def itemService
	def purchaseOrderService
	
    def setup() {
    }

    def cleanup() {
    }
	//----------------------------------CREATE
    void "test create PurchaseReceival"() {
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
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and: 'setting new PurchaseReceival'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		
		when: 'create is called'
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
				
		then: 'check has no error'
		purchaseReceival.hasErrors() == false
		purchaseReceival.isDeleted == false
		purchaseReceival.isConfirmed == false
		println "Create success"
	}
	
	void "test create validation purchaseOrder not Null"() {
		setup: 'setting new PurchaseReceival'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		
		when: 'create is called'
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
				
		then: 'check has error'
		purchaseReceival.hasErrors() == true
		println  "Validation success with error message : " + purchaseReceival.errors.getFieldError().defaultMessage
	}
	//-----------------------------END CREATE
	
	//-----------------------------UPDATE
	void "test update PurchaseReceival"() {
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
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and: 'setting new Purchase Order2'
		def purchaseOrder2 = new PurchaseOrder()
		purchaseOrder2.code = "code2"
		purchaseOrder2.contact = contact
		purchaseOrder2.purchaseDate = new Date(2015, 3, 17)
		purchaseOrder2 = purchaseOrderService.createObject(purchaseOrder2)
		
		
		and: 'setting new Purchase Order'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
		
		and: 'setting Update Purchase Order'
		def updatePurchaseReceival = new PurchaseReceival()
		updatePurchaseReceival.id = purchaseReceival.id
		updatePurchaseReceival.code = "code2"
		updatePurchaseReceival.purchaseOrder = purchaseOrder2
		updatePurchaseReceival.receivalDate = new Date(2015, 4, 16)
		
		when: 'update is called'
		purchaseReceival = purchaseReceivalService.updateObject(updatePurchaseReceival)
				
		then: 'check has no error'
		purchaseReceival.hasErrors() == false
		purchaseReceival.isDeleted == false
		purchaseReceival.isConfirmed == false
		purchaseReceival.code == updatePurchaseReceival.code
		purchaseReceival.purchaseOrder == updatePurchaseReceival.purchaseOrder
		purchaseReceival.receivalDate == updatePurchaseReceival.receivalDate
		println "Update success"
	}
	
	void "test update validation PurchaseOrder not null"() {
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
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and: 'setting new PurchaseReceival'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
		
		and: 'setting Update PurchaseReceival'
		def updatePurchaseReceival = new PurchaseReceival()
		updatePurchaseReceival.id = purchaseReceival.id
		updatePurchaseReceival.code = "code2"
		updatePurchaseReceival.receivalDate = new Date(2015, 4, 16)
		
		when: 'update is called'
		purchaseReceival = purchaseReceivalService.updateObject(updatePurchaseReceival)
				
		then: 'check has error'
		purchaseReceival.hasErrors() == true
		println  "Validation success with error message : " + purchaseReceival.errors.getFieldError().defaultMessage
	}
	
	//-----------------------------END UPDATE
	
	//-----------------------------DELETE
	void "test softdelete PurchaseReceival"() {
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
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and: 'setting new PurchaseReceival'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
		
		when: 'delete is called'
		purchaseReceival = purchaseReceivalService.softDeleteObject(purchaseReceival)
				
		then: 'check has error'
		purchaseReceival.hasErrors() == false
		purchaseReceival.isDeleted == true
		println  "Delete Success"
	}
	
	void "test softdelete validation  purchaseReceival is confirmed"() {
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
		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
		
		and: 'setting new Purchase Order'
		def purchaseReceival = new PurchaseReceival()
		purchaseReceival.code = "code1"
		purchaseReceival.purchaseOrder = purchaseOrder
		purchaseReceival.receivalDate = new Date(2015, 3, 16)
		purchaseReceival.isConfirmed = true
		purchaseReceival = purchaseReceival.save()
		 
		when: 'delete is called'
		purchaseReceival = purchaseReceivalService.softDeleteObject(purchaseReceival)
				
		then: 'check has error'
		purchaseReceival.hasErrors() == true
		println  "Validation success with error message : " + purchaseReceival.errors.getGlobalError().defaultMessage
	}
	//-----------------------------END DELETE
	
//	//-----------------------------CONFIRM
//	void "test confirm PurchaseReceival"() {
//		setup: 'setting new Contact'
//		def contact = new Contact()
//		contact.name = "contactName";
//		contact.phoneBook = "88723";
//		contact.address = "Jln. Kopi";
//		contact = contactService.createObject(contact)
//		
//		and: 'setting new PurchaseOrder'
//		def purchaseOrder = new PurchaseOrder()
//		purchaseOrder.code = "code1"
//		purchaseOrder.contact = contact
//		purchaseOrder.purchaseDate = new Date(2015, 3, 16)
//		purchaseOrder = purchaseOrderService.createObject(purchaseOrder)
//		
//		and: 'setting new PurchaseReceival'
//		def purchaseReceival = new PurchaseReceival()
//		purchaseReceival.code = "code1"
//		purchaseReceival.purchaseOrder = purchaseOrder
//		purchaseReceival.receivalDate = new Date(2015, 3, 16)
//		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
//		
//		and : 'setting new PurchaseReceivalDetail'
//		def purchaseReceivalDetail = new PurchaseReceivalDetail()
//		purchaseReceivalDetail.purchaseReceival = purchaseReceival
//		purchaseReceivalDetail.code = "coded1"
//		purchaseReceivalDetail.purchaseOrderDetail = purchaseOrderDetail
//		purchaseReceivalDetail.quantity = 1
//		purchaseReceivalDetail = purchaseReceivalDetailService.createObject(purchaseReceivalDetail)
//		purchaseReceival.addToPurchaseReceivalDetails(purchaseReceivalDetail)
//		
//		when: 'confirm is called'
//		purchaseReceival = purchaseReceivalService.confirmObject(purchaseReceival)
//				
//		then: 'check has error'
//		purchaseReceival.hasErrors() == false
//		purchaseReceival.isConfirmed == true
//		println  "Confirm success"
//	}
//	
//	void "test confirm validation PurchaseReceival must has PurchaseReceivalDetails"() {
//		setup : 'setting new Contact'
//		def contact = new Contact()
//		contact.name = "contactName"
//		contact.phoneBook = "88723"
//		contact.address = "Jln. Kopi"
//		contact = contactService.createObject(contact)
//		
//		and: 'setting new Purchase Order'
//		def purchaseReceival = new PurchaseReceival()
//		purchaseReceival.code = "code1"
//		purchaseReceival.contact = contact
//		purchaseReceival.purchaseDate = new Date(2015, 3, 16)
//		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
//		
//		when: 'delete is called'
//		purchaseReceival = purchaseReceivalService.confirmObject(purchaseReceival)
//				
//		then: 'check has error'
//		purchaseReceival.hasErrors() == true
//		println  "Validation success with error message : " + purchaseReceival.errors.getGlobalError().defaultMessage
//	}
//	
//	//-----------------------------END CONFIRM
//	
//	//-----------------------------UNCONFIRM
//	void "test unconfirm PurchaseReceival"() {
//		setup : 'setting new Contact'
//		def contact = new Contact()
//		contact.name = "contactName"
//		contact.phoneBook = "88723"
//		contact.address = "Jln. Kopi"
//		contact = contactService.createObject(contact)
//		
//		and: 'setting new Item'
//		def item = new Item()
//		item.sku = "newsku"
//		item.description = "ItemName"
//		item = itemService.createObject(item)
//		
//		and: 'setting new Purchase Order'
//		def purchaseReceival = new PurchaseReceival()
//		purchaseReceival.code = "code1"
//		purchaseReceival.contact = contact
//		purchaseReceival.purchaseDate = new Date(2015, 3, 16)
//		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
//		
//		and : 'setting new PurchaseReceivalDetail'
//		def purchaseReceivalDetail = new PurchaseReceivalDetail()
//		purchaseReceivalDetail.purchaseReceival = purchaseReceival
//		purchaseReceivalDetail.code = "coded1"
//		purchaseReceivalDetail.item = item
//		purchaseReceivalDetail.quantity = 1
//		purchaseReceivalDetail = purchaseReceivalDetailService.createObject(purchaseReceivalDetail)
//		purchaseReceival.addToPurchaseReceivalDetails(purchaseReceivalDetail)
//		
//		when: 'confirm is called'
//		purchaseReceival = purchaseReceivalService.confirmObject(purchaseReceival)
//		purchaseReceival = purchaseReceivalService.unconfirmObject(purchaseReceival)
//				
//		then: 'check has error'
//		purchaseReceival.hasErrors() == false
//		purchaseReceival.isConfirmed == false
//		purchaseReceival.confirmationDate == null
//		println  "Confirm success"
//	}
//	
//	void "test unconfirm validation PurchaseReceival isConfirmed must true"() {
//		setup : 'setting new Contact'
//		def contact = new Contact()
//		contact.name = "contactName"
//		contact.phoneBook = "88723"
//		contact.address = "Jln. Kopi"
//		contact = contactService.createObject(contact)
//		
//		and: 'setting new Item'
//		def item = new Item()
//		item.sku = "newsku"
//		item.description = "ItemName"
//		item = itemService.createObject(item)
//		
//		
//		and: 'setting new Purchase Order'
//		def purchaseReceival = new PurchaseReceival()
//		purchaseReceival.code = "code1"
//		purchaseReceival.contact = contact
//		purchaseReceival.purchaseDate = new Date(2015, 3, 16)
//		purchaseReceival = purchaseReceivalService.createObject(purchaseReceival)
//		
//		and : 'setting new PurchaseReceivalDetail'
//		def purchaseReceivalDetail = new PurchaseReceivalDetail()
//		purchaseReceivalDetail.purchaseReceival = purchaseReceival
//		purchaseReceivalDetail.code = "coded1"
//		purchaseReceivalDetail.item = item
//		purchaseReceivalDetail.quantity = 1
//		purchaseReceivalDetail = purchaseReceivalDetailService.createObject(purchaseReceivalDetail)
//		purchaseReceival.addToPurchaseReceivalDetails(purchaseReceivalDetail)
//		
//		when: 'unconfirm is called'
//		purchaseReceival = purchaseReceivalService.unconfirmObject(purchaseReceival)
//				
//		then: 'check has error'
//		purchaseReceival.hasErrors() == true
//		println  "Validation success with error message : " + purchaseReceival.errors.getGlobalError().defaultMessage
//	}
//	//-----------------------------END UNCONFIRM
}
