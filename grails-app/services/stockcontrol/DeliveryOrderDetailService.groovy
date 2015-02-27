package stockcontrol

import grails.transaction.Transactional

@Transactional
class DeliveryOrderDetailService {
	DeliveryOrderDetailValidationService deliveryOrderDetailValidationService
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return Item.get(object)
	}
	
	def getList(objectId){ 
		def a = objectId.toLong()
		return DeliveryOrderDetail.findAll("from DeliveryOrderDetail as b where b.deliveryOrder.id=? and b.isDeleted = false", [a])
		}
	
	def generateCode()
	{
		
	}
	
	def createObject(object){
			object.deliveryOrder = DeliveryOrder.get(object.deliveryOrderId)
			object.isConfirmed = false
			object.isDeleted = false
			object = deliveryOrderDetailValidationService.createObjectValidation(object as DeliveryOrderDetail)
			if (object.errors.getErrorCount() == 0)
			{
			object.save()
			} 
		return object
	}
	
	def updateObject(def object){
		def valObject = DeliveryOrderDetail.read(object.id)
		valObject.deliveryOrder = DeliveryOrder.get(object.deliveryOrderId)
		valObject.code = object.code
		valObject.quantity = object.quantity
		valObject.salesOrderDetail = object.salesOrderDetail
		valObject = deliveryOrderDetailValidationService.updateObjectValidation(valObject)
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
		def newObject = DeliveryOrderDetail.get(object.id)	
//		newObject.deliveryOrder =  DeliveryOrder.get(newObject.deliveryOrder.id)
		newObject = deliveryOrderDetailValidationService.softDeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject
	}
	
	
}
