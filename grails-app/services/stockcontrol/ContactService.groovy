package stockcontrol

import grails.transaction.Transactional
import stockcontrol.ContactValidationService

@Transactional
class ContactService {
	ContactValidationService contactValidationService
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return Contact.get(object)
	}
	
	def getList(){
		return Contact.getAll()
	}
	
	def createObject(object){
			Contact newObject = new Contact()
			newObject.name = object.name
			newObject.phoneBook = object.phoneBook
			newObject.address = object.address
			newObject.isDeleted = false
			object = contactValidationService.createObjectValidation(newObject)
			if (object.errors.getErrorCount() == 0)
			{
			newObject.save()
			object = newObject
			} 
		return newObject
	}
	
	def updateObject(def object){
		def newObject = Contact.read(object.id)
		newObject.name = object.name
		newObject.phoneBook = object.phoneBook	
		newObject.address = object.address
		newObject = contactValidationService.createObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.save()
		}
		else
		{
			newObject.discard()
		}
		return newObject
	}
	
	def softDeleteObject(def object){
		def newObject = Contact.get(object.id)
		newObject.isDeleted = true
		newObject.save()
	}
	
	
}
