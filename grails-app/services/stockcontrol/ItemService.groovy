package stockcontrol

import grails.transaction.Transactional
import stockcontrol.ItemValidationService

@Transactional
class ItemService {
	ItemValidationService itemValidationService
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return Item.get(object)
	}
	
	def getList(){
		return Item.getAll()
	}
	
	def createObject(object){
			object.isDeleted = false
			object.pendingDelivery = 0
			object.pendingReceival = 0
			object.quantity = 0
			object = itemValidationService.createObjectValidation(object as Item)
			if (object.errors.getErrorCount() == 0)
			{
			object.save()
			} 
		return object
	}
	
	
	def updateObject(def object){
		def valObject = Item.read(object.id)
//		valObject.id = object.id
		valObject.sku = object.sku
		valObject.description = object.description
		valObject = itemValidationService.updateObjectValidation(valObject)
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
		def newObject = Item.get(object.id)
		newObject.isDeleted = true
		newObject.save()
	}
	
	
}
