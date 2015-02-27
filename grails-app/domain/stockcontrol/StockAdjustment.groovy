package stockcontrol

import java.util.Date;

class StockAdjustment {
	
	String 	code
	boolean isDeleted
	boolean isConfirmed
	Date	adjusmentDate
	Date	confirmationDate
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically

	static hasMany = [stockAdjusmentDetails : StockAdjusmentDetail]
    static constraints = {
		confirmationDate (nullable : true)
    }
}
