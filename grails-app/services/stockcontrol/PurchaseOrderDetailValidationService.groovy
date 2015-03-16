package stockcontrol

import grails.transaction.Transactional

@Transactional
class PurchaseOrderDetailValidationService {

    def serviceMethod() {

    }
	
	def hasConfirmed(def object)
	{
		if (object.purchaseOrder.isConfirmed == true)
		{
			object.errors.rejectValue(null,'null','Sudah di confirm')
		}
		return object
	}
	
	def hasDeleted(def object)
	{
		if (object.isDeleted == true)
		{
			object.errors.rejectValue(null,'null','Sudah di delete')
		}
		return object
	}
	
	def quantityNotNull(def object)
	{
		if (object.quantity == null)
		{
			object.errors.rejectValue('quantity','null','Quantity tidak boleh kosong')
		}
		return object
	}
	
	def purchaseOrderNotNull(def object)
	{
		if (object.purchaseOrder == null)
		{
			object.errors.rejectValue('purchaseOrder','null','PurchaseOrder tidak boleh kosong')
		}
		return object
	}
	
	def itemNotNull(def object){
		
			if (object.item == null)
			{
				object.errors.rejectValue('item','null','Item tidak boleh kosong')
			}
			return object
		}
	
	def quantityIsZero(def object){
		
			if (object.quantity <= 0)
			{
				object.errors.rejectValue('quantity','null','Quantity harus lebih besar dari nol')
			}
			return object
		}
	
	
	def codeNotNull(def object){
		
			if (object.code == null)
			{
				object.errors.rejectValue('code','null','Code tidak boleh kosong')
			}
			return object
		}
	
	def codeMustUnique(def object){
			def uniq = PurchaseOrderDetail.findByCodeAndPurchaseOrderAndIsDeleted(object.code,object.purchaseOrder,false)
			if (uniq != null)
			{
				if (uniq.id.toString() != object.id)
				{	
				object.errors.rejectValue('code','null','Code harus unik')
				}
			}
			return object
		}
	
	def createObjectValidation(def object){
		object = purchaseOrderNotNull(object)
		if (object.errors.hasErrors()) return object
		object = hasConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeMustUnique(object)
		if (object.errors.hasErrors()) return object
		object = itemNotNull(object)
		if (object.errors.hasErrors()) return object
		object = quantityNotNull(object)
		if (object.errors.hasErrors()) return object
		object = quantityIsZero(object)
		return object
	}
	
	def updateObjectValidation(def object){
		object = purchaseOrderNotNull(object)
		if (object.errors.hasErrors()) return object
		object = hasConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeMustUnique(object)
		if (object.errors.hasErrors()) return object
		object = itemNotNull(object)
		if (object.errors.hasErrors()) return object
		object = quantityNotNull(object)
		if (object.errors.hasErrors()) return object
		object = quantityIsZero(object)
		return object
	}
	
	def softDeleteObjectValidation(def object){
		object = hasConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = hasDeleted(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	
}
