package stockcontrol

class Contact {
	String name
	String phoneBook
	String address
	boolean	isDeleted
	Date dateCreated // Predefined names by Grails will be filled automatically
	Date lastUpdated // Predefined names by Grails will be filled automatically
	
    static constraints = {
    }
}
