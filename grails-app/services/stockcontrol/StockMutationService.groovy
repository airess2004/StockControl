package stockcontrol

import grails.transaction.Transactional

@Transactional
class StockMutationService {

    def createObject(def object){
		object.save()
	}
	
	
}
