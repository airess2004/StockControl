package stockcontrol

import grails.transaction.Transactional

@Transactional
class PurchaseOrderDetailService {
	PurchaseOrderDetailValidationService purchaseOrderDetailValidationService
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return Item.get(object)
	}
	
	def getList(objectId){ 
		def a = objectId.toLong()
		return PurchaseOrderDetail.findAll("from PurchaseOrderDetail as b where b.purchaseOrder.id=? and b.isDeleted = false", [a])
	}
	
	def getListForCombo(objectId){
		def a = objectId.toLong()
		return PurchaseOrderDetail.findAll("from PurchaseOrderDetail as b where b.purchaseOrder.id=? and b.isDeleted = false", [a])
	}
	
	def generateCode()
	{
		
	}
	
	def createObject(object){
			object.purchaseOrder = PurchaseOrder.get(object.purchaseOrderId)
			object.isConfirmed = false
			object.isDeleted = false
			object.pendingReceival = 0
			object = purchaseOrderDetailValidationService.createObjectValidation(object as PurchaseOrderDetail)
			if (object.errors.getErrorCount() == 0)
			{
			object.save()
			} 
		return object
	}
	
	def updateObject(def object){
		def valObject = PurchaseOrderDetail.read(object.id)
		valObject.purchaseOrder = PurchaseOrder.get(object.purchaseOrderId)
		valObject.code = object.code
		valObject.item = object.item
		valObject.quantity = object.quantity
		valObject.pendingReceival = 0
		valObject = purchaseOrderDetailValidationService.updateObjectValidation(valObject)
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
		def newObject = PurchaseOrderDetail.get(object.id)	
//		newObject.purchaseOrder =  PurchaseOrder.get(newObject.purchaseOrder.id)
		newObject = purchaseOrderDetailValidationService.softDeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject
	}
	
	
}
