package stockcontrol

import grails.transaction.Transactional

@Transactional
class PurchaseOrderValidationService {

    def serviceMethod() {

    }
	
	def notDeleted(def object)
	{
		if (object.isDeleted == false)
		{
			object.errors.rejectValue(null,'null','Belum di delete')
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
	
	def notConfirmed(def object)
	{
		if (object.isConfirmed == false)
		{
			object.errors.rejectValue(null,'null','Belum di confirm')
		}
		return object
	}
	
	def hasConfirmed(def object)
	{
		if (object.isConfirmed == true)
		{
			object.errors.rejectValue(null,'null','Sudah di confirm')
		}
		return object
	}
	
	def notHasDetail(def object)
	{
		if (object.purchaseOrderDetails.findAll().size() == 0)
		{
			object.errors.rejectValue(null,'null','Tidak memiliki detail')
		}
		return object
	}
	
	
	def purchaseDateNotNull(def object)
	{
		if (object.purchaseDate == null)
		{
			object.errors.rejectValue('purchaseDate','null','PurchaseDate tidak boleh kosong')
		}
		return object
	}
	
	def contactNotNull(def object){
	
		if (object.contact == null)
		{
			object.errors.rejectValue('contact','null','Contact tidak boleh kosong')
		}
		return object
	}
	
	def codeNotNull(def object){
		
			if (object.code == null || object.code == "")
			{
				object.errors.rejectValue('code','null','Code tidak boleh kosong')
			}
			return object
		}
	
	def codeMustUnique(def object){
			def uniq = PurchaseOrder.findByCodeAndIsDeleted(object.code,false)
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
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeMustUnique(object)
		if (object.errors.hasErrors()) return object
		object = contactNotNull(object)
		if (object.errors.hasErrors()) return object
		object = purchaseDateNotNull(object)
		return object
	}
	
	def updateObjectValidation(def object){
		object = codeNotNull(object)
		if (object.errors.hasErrors()) return object
		object = codeMustUnique(object)
		if (object.errors.hasErrors()) return object
		object = contactNotNull(object)
		if (object.errors.hasErrors()) return object
		object = purchaseDateNotNull(object)
		return object
	}
	
	def softDeleteObjectValidation(def object){
		object = hasDeleted(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	
	def confirmObjectValidation(def object){
		object = notHasDetail(object)
		if (object.errors.hasErrors()) return object
		object = hasConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = hasDeleted(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	
	def unconfirmObjectValidation(def object){
		object = notHasDetail(object)
		if (object.errors.hasErrors()) return object
		object = notConfirmed(object)
		if (object.errors.hasErrors()) return object
		object = hasDeleted(object)
		if (object.errors.hasErrors()) return object
		return object
	}
	
}
