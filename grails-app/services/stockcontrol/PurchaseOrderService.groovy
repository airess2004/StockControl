package stockcontrol

import grails.transaction.Transactional
import stockcontrol.ItemValidationService

@Transactional
class PurchaseOrderService {
	PurchaseOrderValidationService purchaseOrderValidationService
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return Item.get(object)
	}
	
	def getList(){
		return PurchaseOrder.getAll()
	}
	
	def getListForCombo(){
		return PurchaseOrderDetail.findAll("from PurchaseOrder as b where b.isDeleted = false and b.isConfirmed = true")
	}
	
	def generateCode()
	{
		
	}
	
	def createObject(object){
			object.isConfirmed = false
			object.isDeleted = false
			object = purchaseOrderValidationService.createObjectValidation(object as PurchaseOrder)
			print object.errors.getErrorCount()
			if (object.errors.getErrorCount() == 0)
			{
			object.save()
			} 
		return object
	}

	
	
	def updateObject(def object){
		def valObject = new PurchaseOrder()
		
		valObject.id = object.id
		valObject.code = object.code
		valObject.contact = object.contact
		valObject.purchaseDate = object.purchaseDate
		def newObject = purchaseOrderValidationService.updateObjectValidation(valObject as PurchaseOrder)
//		print newObject.errors.getAllErrors()
		if (newObject.errors.getErrorCount() == 0)
		{
			def newObject2 = PurchaseOrder.get(object.id)
			newObject2.code = object.code
			newObject2.contact = object.contact
			newObject2.purchaseDate = object.purchaseDate
			newObject2.errors = newObject.errors
			newObject2.save()
			newObject = newObject2
		}
		else
		{
			newObject.discard()
		}
		return newObject
	}
	
	def softDeleteObject(def object){
		def newObject = PurchaseOrder.get(object.id)
		newObject = purchaseOrderValidationService.softDeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
		}
		return newObject
	}
	
	def confirmObject(def object){
		def newObject = PurchaseOrder.get(object.id)
		newObject = purchaseOrderValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			for (a in newObject.purchaseOrderDetails.findAll{ it.isDeleted == false })
			{
				Item item = Item.find("from Item as b where b.id=?", [a.item.id])
				item.pendingReceival += a.quantity
				a.pendingReceival = a.quantity
				a.isConfirmed = true
				a.confirmationDate = new Date()
			}
			newObject.save()
		}
		return newObject
	}
	
	def unconfirmObject(def object){
		def newObject = PurchaseOrder.get(object.id)
		newObject = purchaseOrderValidationService.unconfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			for (a in newObject.purchaseOrderDetails.findAll{ it.isDeleted == false })
			{
				Item item = Item.find("from Item as b where b.id=?", [a.item.id])
				item.pendingReceival -= a.quantity
				a.pendingReceival = 0
				a.isConfirmed = false
				a.confirmationDate = null
			}
			newObject.save()
		}
		return newObject
	}
	
	
}
