package stockcontrol

class DeliveryOrderDetail {

	//Integer	itemId
	//Integer	deliveryOrderId
	//Integer	salesOrderDetailId
	Integer	quantity
	String	code
	boolean	isDeleted
	boolean	isConfirmed
	Date	confirmationDate
	Date 	dateCreated // Predefined names by Grails will be filled automatically
	Date 	lastUpdated // Predefined names by Grails will be filled automatically
	
	DeliveryOrder	deliveryOrder
	SalesOrderDetail	salesOrderDetail
	
	
    static constraints = {
		confirmationDate (nullable : true)
    }
}
