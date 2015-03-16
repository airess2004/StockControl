package stockcontrol

import grails.test.spock.IntegrationSpec

class ContactIntegrationSpec extends IntegrationSpec {
	def contactService;
	
	
	
	
	def setup() {
	}
	
    def cleanup() {
    }
	
	//-------------------------------CREATE
    void "Test create new Contact"() {
		setup: 'setting new Contact'
		def contact = new Contact();
		contact.name = "contactName";
		contact.phoneBook = "88723";
		contact.address = "Jln. Kopi";
		
		when: 'create is called'
		contact = contactService.createObject(contact)
				
		then: 'check has no error'
		contact.hasErrors() == false
		contact.isDeleted == false
		println "Create success"
    }
	
	
	void "Test create validation Contact cannot null"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = ""
		
		when: 'create is called'
		contact = contactService.createObject(contact)
				
		then: 'check has error'
		contact.hasErrors() == true
		println "Validation success with error message : " + contact.errors.getFieldError().defaultMessage
	}
	
	//------------------------------END CREATE
	
	//------------------------------UPDATE
	void "Test update Contact"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName"
		contact.phoneBook = "88723"
		contact.address = "Jln. Kopi"
		contact = contactService.createObject(contact)
		
		and : 'setting update Contact'
		def contactUpdate = new Contact()
		contactUpdate.id = contact.id
		contactUpdate.name = "updateContactName"
		contactUpdate.phoneBook = "887231"
		contactUpdate.address = "Jln. Kopi2"
		
		when: 'create and update is called'
		contact = contactService.updateObject(contactUpdate)
				
		then: 'check has no error'
		contact.hasErrors() == false
		contact.isDeleted == false
		contact.name == contactUpdate.name
		contact.phoneBook == contactUpdate.phoneBook
		contact.address == contactUpdate.address
		println "Update success"
	}
	
	void "Test update validation Contact cannot null"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName"
		contact.phoneBook = "88723"
		contact.address = "Jln. Kopi"
		contact = contactService.createObject(contact)
		
		and :
		def contactUpdate = new Contact()
		contactUpdate.id = contact.id
		contactUpdate.name = ""
		
		when: 'update is called'
		contact = contactService.updateObject(contactUpdate)
				
		then: 'check has error'
		contact.hasErrors() == true
		println  "Validation success with error message : " + contact.errors.getFieldError().defaultMessage
	}
	
	//-------------------------------END UPDATE
	
	//-------------------------------DELETE
	void "Test softdelete Contact"() {
		setup: 'setting new Contact'
		def contact = new Contact()
		contact.name = "contactName"
		contact.phoneBook = "88723"
		contact.address = "Jln. Kopi"
		contact = contactService.createObject(contact)
		
		when: 'delete is called'
		contact = contactService.softDeleteObject(contact)
				
		then: 'check has error'
		contact.hasErrors() == false
		contact.isDeleted == true
		println  "Delete Success"
	}
	//--------------------------------END DELETE
	
}
