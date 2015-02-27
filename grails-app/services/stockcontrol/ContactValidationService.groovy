package stockcontrol

import grails.transaction.Transactional

@Transactional
class ContactValidationService {

    def serviceMethod() {

    }
	
	def vName(){
			
	}
	
	def nameNotNull(def object){
	
		if (object.name == "")
		{
			object.errors.rejectValue('name','null','Nama tidak boleh kosong')
		}
		return object
	}
	
	
	def createObjectValidation(def object){
		object = nameNotNull(object)
		if (object.errors.hasErrors()) return object
		return object
	}
}
