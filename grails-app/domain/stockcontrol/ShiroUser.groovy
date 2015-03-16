package stockcontrol

import java.util.Date;

class ShiroUser {
    String username
    String passwordHash
	
	boolean isDeleted
	Date dateCreated
	Date lastUpdated
    
    static hasMany = [ roles: ShiroRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique: true)
    }
}
