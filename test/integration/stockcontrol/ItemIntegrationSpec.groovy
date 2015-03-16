package stockcontrol

import grails.test.spock.IntegrationSpec

class ItemIntegrationSpec extends IntegrationSpec {
	def itemService
	
	
	
	
    def setup() {
    }

    def cleanup() {
    }

	//------------------------------------ CREATE VALIDATION
    void "test create new Item"() {
		setup: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		
		when: 'create is called'
		item = itemService.createObject(item)
				
		then: 'check has no error'
		item.hasErrors() == false
		item.isDeleted == false
		item.quantity == 0
		item.pendingReceival == 0
		item.pendingDelivery == 0
		println "Create success"
    }
	
	void "test create validation sku not null"() {
		setup: 'setting new Item'
		def item = new Item()
		item.description = "ItemName"
		
		when: 'create is called'
		item = itemService.createObject(item)
				
		then: 'check has error'
		item.hasErrors() == true
		println "Validation success with error message : " + item.errors.getFieldError().defaultMessage
	}
	
	void "test create validation description not null"() {
		setup: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		
		when: 'create is called'
		item = itemService.createObject(item)
				
		then: 'check has error'
		item.hasErrors() == true
		println "Validation success with error message : " + item.errors.getFieldError().defaultMessage
	}
	
	void "test create validation sku must unique"() {
		setup: 'setting new Item'
		def item = new Item()
		item.sku = "sku5"
		item.description = "ItemName2"
		
		and: 'setting new Item with same sku'
		def item2 = new Item()
		item2.sku = "sku5"
		item2.description = "ItemName3"
		
		when: 'create 2 object item() and item2() is called'
		item = itemService.createObject(item)
		item2 = itemService.createObject(item2)
				
		then: 'check has error'
		item2.hasErrors() == true
		println "Validation success with error message : " + item2.errors.getFieldError().defaultMessage
	}
	
	//--------------------------------END CREATE
	
	//--------------------------------UPDATE
	void "test update Item"() {
		setup: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		item = itemService.createObject(item)
		
		and : 'setting update Item'
		def itemUpdate = new Item()
		itemUpdate.id = item.id
		itemUpdate.sku = "newsku2"
		itemUpdate.description = "updateItemName"

		when: 'update is called'
		item = itemService.updateObject(itemUpdate)
				
		then: 'check has no error'
		item.hasErrors() == false
		item.sku == itemUpdate.sku
		item.description == itemUpdate.description
		println "Update success"
	}
	
	void "test validation update Item sku not null"() {
		setup: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		item = itemService.createObject(item)
		
		and : 'setting update Item'
		def itemUpdate = new Item()
		itemUpdate.id = item.id
		itemUpdate.description = "updateItemName"

		when: 'update is called'
		item = itemService.updateObject(itemUpdate)
				
		then: 'check has error'
		item.hasErrors() == true
		println  "Validation success with error message : " + item.errors.getFieldError().defaultMessage
	}
	
	void "test validation update Item sku must unique"() {
		setup: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		item = itemService.createObject(item)
		
		and : 'setting new Item2'
		def item2 = new Item()
		item2.sku = "newsku2"
		item2.description = "ItemName"
		item2 = itemService.createObject(item2)
		
		and : 'setting update Item'
		def itemUpdate = new Item()
		itemUpdate.id = item.id
		itemUpdate.sku = "newsku2"
		itemUpdate.description = "updateItemName"

		when: 'update is called'
		item = itemService.updateObject(itemUpdate)
				
		then: 'check has error'
		item.hasErrors() == true
		println  "Validation success with error message : " + item.errors.getFieldError().defaultMessage
	}
	
	void "test validation update description not null"() {
		setup: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		item = itemService.createObject(item)
		
		and : 'setting update Item'
		def itemUpdate = new Item()
		itemUpdate.id = item.id
		itemUpdate.sku = "newsku2"
		itemUpdate.description = ""

		when: 'create is called'
		item = itemService.updateObject(itemUpdate)
				
		then: 'check has error'
		item.hasErrors() == true
		println  "Validation success with error message : " + item.errors.getFieldError().defaultMessage
	}
	
	//---------------------------------END UPDATE
	
	//---------------------------------DELETE
	void "test softdelete"() {
		setup: 'setting new Item'
		def item = new Item()
		item.sku = "newsku"
		item.description = "ItemName"
		item = itemService.createObject(item)
		
		when: 'delete is called'
		item = itemService.softDeleteObject(item)
				
		then: 'check has error'
		item.hasErrors() == false
		item.isDeleted == true
		println  "Delete Success"
	}
	//----------------------------------END DELETE
	
	
}
