package stockcontrol

import java.util.Date;

class PurchaseOrderDetail {

	//Integer	itemId
	//Integer purchaseOrderId
	Integer	quantity
	String	code
	boolean	isDeleted
	boolean	isConfirmed
	Date	confirmationDate
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically
	Integer	pendingReceival
	
	Item	item
	PurchaseOrder	purchaseOrder
	
    static constraints = {
		confirmationDate (nullable : true)
    }
}
