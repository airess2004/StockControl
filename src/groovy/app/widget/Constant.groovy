package app.widget

public final class Constant {
	public enum MenuGroup {
		Master("Master"),
		Transaction("Transaction"),
		Setting("Setting");
		
		private final String name;
		
		private MenuGroup(String s) {
			name = s;
		}
	
		//@Override
		public boolean equalsName(String otherName){
			return (otherName == null)? false:name.equals(otherName);
		}
	
		@Override
		public String toString(){
		   return name;
		}
		
		//@Override
		public String plus(String s){
			return name + s;
		}
	}
	
	public enum MenuName {
		Item("Item"),
		Contact("Contact"),
		
		PurchaseOrder("PurchaseOrder"),
		PurchaseReceival("PurchaseReceival"),
		SalesOrder("SalesOrder"),
		DeliveryOrder("DeliveryOrder"),
		
		Role("Role"),
		User("User");
		
		private final String name;
		
		private MenuName(String s) {
			name = s;
		}
	
		//@Override
		public boolean equalsName(String otherName){
			return (otherName == null)? false:name.equals(otherName);
		}
	
		@Override
		public String toString(){
		   return name;
		}
		
		//@Override
		public String plus(String s){
			return name + s;
		}
	}
	
	public enum AccessType {
		Add("Add"),
		Edit("Edit"),
		Delete("Delete"),
		Confirm("Confirm"),
		Unconfirm("Unconfirm"),
		Print("Print");
		
		private final String name;
		
		private AccessType(String s) {
			name = s;
		}
	
		//@Override
		public boolean equalsName(String otherName){
			return (otherName == null)? false:name.equals(otherName);
		}
	
		@Override
		public String toString(){
		   return name;
		}
		
		//@Override
		public String plus(String s){
			return name + s;
		}
	}
}
