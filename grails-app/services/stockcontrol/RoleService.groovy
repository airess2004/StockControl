package stockcontrol

import grails.transaction.Transactional
import stockcontrol.ItemValidationService

@Transactional
class RoleService {
	RoleValidator roleValidator
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return ShiroRole.get(object)
	}
	
	def getList(){
		return ShiroRole.getAll()
	}
	
	def createObject(object){
			object.isDeleted = false
			object = roleValidator.createObjectValidation(object as ShiroRole)
			if (object.errors.getErrorCount() == 0)
			{
			object.save()
			} 
		return object
	}
	
	
	def updateObject(def object){
		def valObject = ShiroRole.read(object.id)
//		valObject.id = object.id
		valObject.name = object.name
		valObject = roleValidator.updateObjectValidation(valObject)
		if (valObject.errors.getErrorCount() == 0)
		{
//			def newObject2 = Item.get(object.id)
//			newObject2.sku = object.sku
//			newObject2.description = object.description
//			newObject2.errors = newObject.errors
			valObject.save()
//			newObject = newObject2
		}
		else
		{
			valObject.discard()
		}
		return valObject
	}
	
	def softDeleteObject(def object){
		def newObject = ShiroRole.get(object.id)
		newObject.isDeleted = true
		newObject.save()
	}
	
	
}
