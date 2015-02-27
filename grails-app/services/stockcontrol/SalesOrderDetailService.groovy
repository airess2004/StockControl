package stockcontrol

import grails.transaction.Transactional

@Transactional
class SalesOrderDetailService {
	SalesOrderDetailValidationService salesOrderDetailValidationService
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return Item.get(object)
	}
	
	def getList(objectId){ 
		def a = objectId.toLong()
		return SalesOrderDetail.findAll("from SalesOrderDetail as b where b.salesOrder.id=? and b.isDeleted = false", [a])
		}
	
	def getListForCombo(objectId){
		def a = objectId.toLong()
		return SalesOrderDetail.findAll("from SalesOrderDetail as b where b.salesOrder.id=? and b.isDeleted = false", [a])
	}
	
	def generateCode()
	{
		
	}
	
	def createObject(object){
			object.salesOrder = SalesOrder.get(object.salesOrderId)
			object.isConfirmed = false
			object.isDeleted = false
			object.pendingDelivery = 0
			object = salesOrderDetailValidationService.createObjectValidation(object as SalesOrderDetail)
			if (object.errors.getErrorCount() == 0)
			{
			object.save()
			} 
		return object
	}
	
	def updateObject(def object){
		def valObject = new SalesOrderDetail()
		valObject.id = object.id
		valObject.salesOrder = SalesOrder.get(object.salesOrderId)
		valObject.code = object.code
		valObject.item = object.item
		valObject.quantity = 0
		def newObject = salesOrderDetailValidationService.updateObjectValidation(valObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			def newObject2 = SalesOrderDetail.get(object.id)
			newObject2.code = object.code
			newObject2.item = object.item
			newObject2.quantity = object.quantity
			newObject2.errors = newObject.errors
			newObject2.save()
			newObject = newObject2
		}
		return newObject
		
	}
	
	def softDeleteObject(def object){
		def newObject = SalesOrderDetail.get(object.id)	
//		newObject.salesOrder =  SalesOrder.get(newObject.salesOrder.id)
		newObject = salesOrderDetailValidationService.softDeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
			newObject.save()
		}
		return newObject
	}
	
	
}
