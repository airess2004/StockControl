package stockcontrol

import java.util.Date;

class StockAdjusmentDetail {
	
	//Integer	itemId
	//Integer stockAdjustmentId
	Integer	quantity
	String	code
	boolean	isDeleted
	boolean	isConfirmed
	Date	confirmationDate
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically

	Item item
	StockAdjustment stockadjusment
	
    static constraints = {
		confirmationDate (nullable : true)
    }
}
