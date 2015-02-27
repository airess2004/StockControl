package stockcontrol

import java.util.Date;

class PurchaseReceival {

	//Integer	purchaseOrderId
	String	code
	boolean	isDeleted
	boolean	isConfirmed
	Date	receivalDate
	Date	confirmationDate
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically
	PurchaseOrder	purchaseOrder
	
	static hasMany = [purchaseReceivalDetails : PurchaseReceivalDetail]
    static constraints = {
		confirmationDate (nullable : true)
    }
}
