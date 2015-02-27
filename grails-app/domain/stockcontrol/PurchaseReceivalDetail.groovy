package stockcontrol

import java.util.Date;

class PurchaseReceivalDetail {

	//Integer	itemId
	//Integer	purchaseReceivalId
	//Integer	purchaseOrderDetailId
	Integer	quantity
	String	code
	boolean	isDeleted
	boolean	isConfirmed
	Date	confirmationDate
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically

	PurchaseReceival	purchaseReceival
	PurchaseOrderDetail	purchaseOrderDetail
	
	static constraints = {
		confirmationDate (nullable : true)
    }
}
