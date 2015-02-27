package stockcontrol

import java.util.Date;

class SalesOrder {

	//Integer	contactId
	String	code
	boolean	isDeleted
	boolean	isConfirmed
	Date	salesDate
	Date	confirmationDate
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically
	
	Contact	contact
	static hasMany = [salesOrderDetails : SalesOrderDetail]
    static constraints = {
		confirmationDate (nullable : true)
    }
}
