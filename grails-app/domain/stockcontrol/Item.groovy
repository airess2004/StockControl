
package stockcontrol
import java.util.Date;

class Item {

	String sku
	String description
	Integer quantity
	Integer pendingReceival
	Integer pendingDelivery
	boolean isDeleted
	Date dateCreated // Predefined names by Grails will be filled automatically
	Date lastUpdated // Predefined names by Grails will be filled automatically
	
		
    static constraints = {
    }
	
}
