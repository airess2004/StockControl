package stockcontrol

import grails.transaction.Transactional

@Transactional
class SalesOrderDetailValidationService {

    def serviceMethod() {

    }
	
	def hasConfirmed(def object)
	{
		if (object.salesOrder.isConfirmed == true)
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
	
	def itemNotNull(def object){
		
			if (object.item == null)
			{
				object.errors.rejectValue('item','null','Item tidak boleh kosong')
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
			def uniq = SalesOrderDetail.findByCodeAndSalesOrderAndIsDeleted(object.code,object.salesOrder,false)
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
		object = hasConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeMustUnique(object)
		if (object.errors.hasErrors()) return object
		object = itemNotNull(object)
		if (object.errors.hasErrors()) return object
		object = quantityNotNull(object)
		return object
	}
	
	def updateObjectValidation(def object){
		object = hasConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeMustUnique(object)
		if (object.errors.hasErrors()) return object
		object = itemNotNull(object)
		if (object.errors.hasErrors()) return object
		object = quantityNotNull(object)
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
