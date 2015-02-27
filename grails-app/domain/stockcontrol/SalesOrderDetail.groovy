package stockcontrol

import java.util.Date;

class SalesOrderDetail {

	//Integer	itemId
	//Integer	salesOrderId
	Integer	quantity
	String	code
	boolean	isDeleted
	boolean isConfirmed
	Date	confirmationDate
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically
	Integer	pendingDelivery
	
	Item	item
	SalesOrder	salesOrder
	
    static constraints = {
		confirmationDate (nullable : true)
    }
	
}
