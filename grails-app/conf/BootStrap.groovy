import stockcontrol.Contact
import stockcontrol.Item
import stockcontrol.ItemService

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
		
		
    }
	
    def destroy = {
    }
}
