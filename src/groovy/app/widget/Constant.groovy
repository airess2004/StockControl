package app.widget

//import org.springframework.stereotype.Component;
//import org.springframework.beans.factory.annotation.Configurable;

//@Singleton
//@Component
//@Configurable(preConstruction = true)
public class Constant {
	//public static final INSTANCE = new Constant()
	MenuGroup MenuGroup;
	MenuName MenuName;
	AccessType AccessType;
}

//public enum MenuGroup extends Enum<MenuGroup>;

public enum MenuGroup {
	Master("Master"),
	Transaction("Transaction"),
	Setting("Setting")
	
	private final String value
	
	private MenuGroup(String value) {
		//this.value = value
	 }
  
	 int value() { value }
	 
//	public static main(args) {
//		//println MenuGroup.values()
//	}
}

public enum MenuName {
	Item("Item"),
	Contact("Contact"),
	
	PurchaseOrder("PurchaseOrder"),
	PurchaseReceival("PurchaseReceival"),
	SalesOrder("SalesOrder"),
	DeliveryOrder("DeliveryOrder"),
	
	Role("Role"),
	User("User"),
}

public enum AccessType {
	Add("Add"),
	Edit("Edit"),
	Delete("Delete"),
	Confirm("Confirm"),
	Unconfirm("Unconfirm"),
	Print("Print"),
}