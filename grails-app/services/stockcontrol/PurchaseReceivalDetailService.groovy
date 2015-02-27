package stockcontrol

import grails.transaction.Transactional

@Transactional
class PurchaseReceivalDetailService {
	PurchaseReceivalDetailValidationService purchaseReceivalDetailValidationService
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return Item.get(object)
	}
	
	def getList(objectId){ 
		def a = objectId.toLong()
		return PurchaseReceivalDetail.findAll("from PurchaseReceivalDetail as b where b.purchaseReceival.id=? and b.isDeleted = false", [a])
		}
	
	def generateCode()
	{
		
	}
	
	def createObject(object){
			object.purchaseReceival = PurchaseReceival.get(object.purchaseReceivalId)
			object.isConfirmed = false
			object.isDeleted = false
			object = purchaseReceivalDetailValidationService.createObjectValidation(object as PurchaseReceivalDetail)
			if (object.errors.getErrorCount() == 0)
			{
			object.save()
			} 
		return object
	}
	
	def updateObject(def object){
		def valObject = PurchaseReceivalDetail.read(object.id)
		valObject.purchaseReceival = PurchaseReceival.get(object.purchaseReceivalId)
		valObject.code = object.code
		valObject.quantity = object.quantity
		valObject.purchaseOrderDetail = object.purchaseOrderDetail
		valObject = purchaseReceivalDetailValidationService.updateObjectValidation(valObject)
		if (valObject.errors.getErrorCount() == 0)
		{
			valObject.save()
		}
		else
		{
			valObject.discard()
		}
		return valObject
		
	}
	
	def softDeleteObject(def object){
		def newObject = PurchaseReceivalDetail.get(object.id)	
//		newObject.purchaseReceival =  PurchaseReceival.get(newObject.purchaseReceival.id)
		newObject = purchaseReceivalDetailValidationService.softDeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject
	}
	
	
}
