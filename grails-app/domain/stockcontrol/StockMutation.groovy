package stockcontrol

import java.util.Date;

class StockMutation {

	//Integer itemId
	Integer	quantity
	String	itemCase
	String 	status
	Integer	sourceDocumentId
	String	sourceDocumentType
	Integer	sourceDocumentDetailId
	Integer	sourceDocumentDetailType
	boolean	isDeleted
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically
	
	Item item
	
    static constraints = {
    }
}
