package stockcontrol

import java.util.Date;

class DeliveryOrder {

	//Integer	salesOrderId
	String code
	boolean isDeleted
	boolean	isConfirmed
	Date	deliveryDate
	Date	confirmationDate
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically
	
	SalesOrder	salesOrder
	
	static hasMany = [deliveryOrderDetails : DeliveryOrderDetail]
	
    static constraints = {
		confirmationDate (nullable : true)
    }
}
