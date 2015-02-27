package stockcontrol

import java.util.Date;

class PurchaseOrder {

	//Integer	contactId
	String	code
	boolean	isDeleted
	boolean isConfirmed
	Date	purchaseDate
	Date 	confirmationDate
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically
	
	Contact contact
	
	static hasMany = [purchaseOrderDetails : PurchaseOrderDetail]
	
    static constraints = {
		confirmationDate (nullable : true)
    }
}
