package stockcontrol

import grails.transaction.Transactional
import stockcontrol.ItemValidationService

@Transactional
class DeliveryOrderService {
	DeliveryOrderValidationService deliveryOrderValidationService
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return Item.get(object)
	}
	
	def getList(){
		return DeliveryOrder.getAll()
	}
	
	def generateCode()
	{
		
	}
	
	def createObject(object){
			object.isConfirmed = false
			object.isDeleted = false
			object = deliveryOrderValidationService.createObjectValidation(object as DeliveryOrder)
			if (object.errors.getErrorCount() == 0)
			{
			object.save()
			} 
		return object
	}
	
	def updateObject(def object){
		def valObject = DeliveryOrder.read(object.id)
		valObject.code = object.code
		valObject.salesOrder = object.salesOrder
		valObject.receivalDate = object.receivalDate
		valObject = deliveryOrderValidationService.updateObjectValidation(valObject)
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
		def newObject = DeliveryOrder.get(object.id)
		newObject = deliveryOrderValidationService.softDeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
		}
		return newObject
	}
	
	def confirmObject(def object){
		def newObject = DeliveryOrder.get(object.id)
		newObject = deliveryOrderValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			for (a in newObject.deliveryOrderDetails.findAll{ it.isDeleted == false })
			{
				Item item = Item.find("from Item as b where b.id=?", [a.salesOrderDetail.item.id])
				item.pendingDelivery -= a.quantity
				item.quantity -= a.quantity
				a.isConfirmed = true
				a.confirmationDate = new Date()
				StockMutation sm = new StockMutation()
				sm.quantity = a.quantity
				sm.itemCase = "ready"
				sm.status = "add"
				sm.sourceDocumentId = newObject.id
				sm.sourceDocumentType = "deliveryOrder"
				sm.sourceDocumentDetailId = a.id
				sm.sourceDocumentDetailType = a.id
				sm.isDeleted = false
				sm.item = item
				sm.save()
			}
			newObject.save()
		}
		return newObject
	}
	
	def unconfirmObject(def object){
		def newObject = DeliveryOrder.get(object.id)
		newObject = deliveryOrderValidationService.unconfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			for (a in newObject.deliveryOrderDetails.findAll{ it.isDeleted == false })
			{
				Item item = Item.find("from Item as b where b.id=?", [a.salesOrderDetail.item.id])
				item.pendingDelivery += a.quantity
				item.quantity += a.quantity
				a.isConfirmed = false
				a.confirmationDate = null
				StockMutation sm = new StockMutation()
				sm.quantity = a.quantity
				sm.itemCase = "pending"
				sm.status = "dec"
				sm.sourceDocumentId = newObject.id
				sm.sourceDocumentType = "deliveryOrder"
				sm.sourceDocumentDetailId = a.id
				sm.sourceDocumentDetailType = a.id
				sm.isDeleted = false
				sm.item = item
				sm.save()
			}
			newObject.save()
		}
		return newObject
	}
	
	
}
