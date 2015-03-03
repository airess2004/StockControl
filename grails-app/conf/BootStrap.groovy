import org.apache.shiro.crypto.hash.Sha256Hash
import stockcontrol.Contact
import stockcontrol.Item
import stockcontrol.ItemService
import stockcontrol.ShiroRole
import stockcontrol.ShiroUser

class BootStrap {
	def itemService
    def init = { servletContext ->
		Item newitem = new Item()
		newitem.sku = "sku1"
		newitem.description = "item1"
		newitem.pendingDelivery = 0
		newitem.pendingReceival = 0
		newitem.quantity = 0
		newitem.save()
		
		Item newitem2 = new Item()
		newitem2.sku = "sku2"
		newitem2.description = "item2"
		newitem2.pendingDelivery = 0
		newitem2.pendingReceival = 0
		newitem2.quantity = 0
		newitem2.save()
		
		Item newitem3 = new Item()
		newitem3.sku = "sku3"
		newitem3.description = "item3"
		newitem3.pendingDelivery = 0
		newitem3.pendingReceival = 0
		newitem3.quantity = 0
		newitem3.save()
		
		Contact newContact1 = new Contact()
		newContact1.name = "Jostar"
		newContact1.phoneBook = "098273"
		newContact1.address = "Jln. Address 123"
		newContact1.save()
		
		Contact newContact2 = new Contact()
		newContact2.name = "Adam"
		newContact2.phoneBook = "987263"
		newContact2.address = "Jln. Alamat 765"
		newContact2.save()
		
		// Spring Security
		//def adminRole = new Authority(authority: 'ROLE_ADMIN').save(flush: true)
		//def userRole = new Authority(authority: 'ROLE_USER').save(flush: true)
		//def testUser = new Person(username: 'me', enabled: true, password: 'password') testUser.save(flush: true)
		//UserRole.create testUser, adminRole, true
		//assert User.count() == 1
		//assert Role.count() == 2
		//assert UserRole.count() == 1
		
		//Shiro
		def adminRole = ShiroRole.findByNameAndIsDeleted("Administrator",false)
		if(adminRole==null){
			adminRole = new ShiroRole(name: "Administrator")
			adminRole.addToPermissions("*:*")
			//adminRole.addToPermissions("admin")
			adminRole.save(flush:true, failOnError:true)
		}
		if (ShiroUser.findAllByUsernameAndIsDeleted("ADMIN",false).isEmpty()) {
			def user = new ShiroUser(username: "ADMIN", passwordHash: new Sha256Hash("sysadmin").toHex())
			user.addToRoles(adminRole)
			//		user.addToPermissions("*:*")
			//		user.addToPermissions("admin")
			user.save(flush:true, failOnError:true)
		}
		if (ShiroUser.findAllByUsernameAndIsDeleted("GUEST",false).isEmpty()) {
			def user = new ShiroUser(username: "GUEST", passwordHash: new Sha256Hash("guest").toHex())
			//user.addToPermissions("Master:Item:Add")
			user.save(flush:true, failOnError:true)
		}
    }
	
    def destroy = {
    }
}
