package stockcontrol

import grails.transaction.Transactional
import stockcontrol.ItemValidationService

@Transactional
class SalesOrderService {
	SalesOrderValidationService salesOrderValidationService
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return Item.get(object)
	}
	
	def getList(){
		return SalesOrder.getAll()
	}
	
	def getListForCombo(){
		return SalesOrderDetail.findAll("from SalesOrder as b where b.isDeleted = false and b.isConfirmed = true")
	}
	
	def generateCode()
	{
		
	}
	
	def createObject(object){
			object.isConfirmed = false
			object.isDeleted = false
			object = salesOrderValidationService.createObjectValidation(object as SalesOrder)
			print object.errors.getErrorCount()
			if (object.errors.getErrorCount() == 0)
			{
			object.save()
			} 
		return object
	}
	
	def updateObject(def object){
		def valObject = new SalesOrder()
		valObject.id = object.id
		valObject.code = object.code
		valObject.contact = object.contact
		valObject.salesDate = object.salesDate
		def newObject = salesOrderValidationService.updateObjectValidation(valObject as SalesOrder)
//		print newObject.errors.getAllErrors()
		if (newObject.errors.getErrorCount() == 0)
		{
			def newObject2 = SalesOrder.get(object.id)
			newObject2.code = object.code
			newObject2.contact = object.contact
			newObject2.salesDate = object.salesDate
			newObject2.errors = newObject.errors
			newObject2.save()
			newObject = newObject2
		}
		return newObject
	}
	
	def softDeleteObject(def object){
		def newObject = SalesOrder.get(object.id)
		newObject = salesOrderValidationService.softDeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
		}
		return newObject
	}
	
	def confirmObject(def object){
		def newObject = SalesOrder.get(object.id)
		newObject = salesOrderValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			for (a in newObject.salesOrderDetails.findAll{ it.isDeleted == false })
			{
				Item item = Item.find("from Item as b where b.id=?", [a.item.id])
				item.pendingDelivery += a.quantity
				a.isConfirmed = true
				a.pendingDelivery = a.quantity
				a.confirmationDate = new Date()
			}
			newObject.save()
		}
		return newObject
	}
	
	def unconfirmObject(def object){
		def newObject = SalesOrder.get(object.id)
		newObject = salesOrderValidationService.unconfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			for (a in newObject.salesOrderDetails.findAll{ it.isDeleted == false })
			{
				Item item = Item.find("from Item as b where b.id=?", [a.item.id])
				item.pendingDelivery -= a.quantity
				a.isConfirmed = false
				a.pendingDelivery = 0
				a.confirmationDate = null
			}
			newObject.save()
		}
		return newObject
	}
	
	
}
