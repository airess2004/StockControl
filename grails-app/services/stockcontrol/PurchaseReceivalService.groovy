package stockcontrol

import grails.transaction.Transactional
import stockcontrol.ItemValidationService

@Transactional
class PurchaseReceivalService {
	PurchaseReceivalValidationService purchaseReceivalValidationService
	StockMutationService stockMutationService
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return Item.get(object)
	}
	
	def getList(){
		return PurchaseReceival.getAll()
	}
	
	def generateCode()
	{
		
	}
	
	def createObject(object){
			object.isConfirmed = false
			object.isDeleted = false
			object = purchaseReceivalValidationService.createObjectValidation(object as PurchaseReceival)
			if (object.errors.getErrorCount() == 0)
			{
			object.save()
			} 
		return object
	}
	
	def updateObject(def object){
		def valObject = PurchaseReceival.read(object.id)
		valObject.code = object.code
		valObject.purchaseOrder = object.purchaseOrder
		valObject.receivalDate = object.receivalDate
		valObject = purchaseReceivalValidationService.updateObjectValidation(valObject)
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
		def newObject = PurchaseReceival.get(object.id)
		newObject = purchaseReceivalValidationService.softDeleteObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isDeleted = true
		}
		return newObject
	}
	
	def confirmObject(def object){
		def newObject = PurchaseReceival.get(object.id)
		newObject = purchaseReceivalValidationService.confirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = true
			newObject.confirmationDate = new Date()
			for (a in newObject.purchaseReceivalDetails.findAll{ it.isDeleted == false })
			{
				Item item = Item.find("from Item as b where b.id=?", [a.purchaseOrderDetail.item.id])
				item.pendingReceival -= a.quantity
				item.quantity += a.quantity
				a.isConfirmed = true
				a.confirmationDate = new Date()
				a.purchaseOrderDetail.pendingReceival -= a.quantity
				StockMutation sm = new StockMutation()
				sm.quantity = a.quantity
				sm.itemCase = "ready"
				sm.status = "add"
				sm.sourceDocumentId = newObject.id
				sm.sourceDocumentType = "purchaseReceival"
				sm.sourceDocumentDetailId = a.id
				sm.sourceDocumentDetailType = a.id
				sm.isDeleted = false
				sm.item = item
				stockMutationService.createObject(sm)
			}
			newObject.save()
		}
		return newObject
	}
	
	def unconfirmObject(def object){
		def newObject = PurchaseReceival.get(object.id)
		newObject = purchaseReceivalValidationService.unconfirmObjectValidation(newObject)
		if (newObject.errors.getErrorCount() == 0)
		{
			newObject.isConfirmed = false
			newObject.confirmationDate = null
			for (a in newObject.purchaseReceivalDetails.findAll{ it.isDeleted == false })
			{
				Item item = Item.find("from Item as b where b.id=?", [a.purchaseOrderDetail.item.id])
				item.pendingReceival += a.quantity
				item.quantity -= a.quantity
				a.isConfirmed = false
				a.confirmationDate = null
				a.purchaseOrderDetail.pendingReceival += a.quantity
				StockMutation sm = new StockMutation()
				sm.quantity = a.quantity
				sm.itemCase = "pending"
				sm.status = "dec"
				sm.sourceDocumentId = newObject.id
				sm.sourceDocumentType = "purchaseReceival"
				sm.sourceDocumentDetailId = a.id
				sm.sourceDocumentDetailType = a.id
				sm.isDeleted = false
				sm.item = item
				stockMutationService.createObject(sm)
			}
			newObject.save()
		}
		return newObject
	}
	
	
}
