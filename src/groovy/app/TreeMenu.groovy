package app

import com.vaadin.event.ItemClickEvent
import com.vaadin.event.ItemClickEvent.ItemClickListener
import com.vaadin.navigator.Navigator
import com.vaadin.ui.Tree

import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils

class TreeMenu extends Tree{
	
   public TreeMenu(){
	   //get the current Subject
	   Subject currentUser = SecurityUtils.getSubject();
	   
	   //Book
	   //--Parent
	   addItem( "Master" )
	   //--Child
	   addItem("Item")
	   setParent("Item", "Master")
	   setChildrenAllowed( "Item", false)
	   addItem("Customer")
	   setParent("Customer", "Master")
	   setChildrenAllowed( "Customer", false)
	   //Master Date
	   //Parent
	   addItem( "Transaction" )
	   //Child
	   addItem("Purchase Order")
	   setParent("Purchase Order", "Transaction")
	   setChildrenAllowed( "Purchase Order", false)
	   addItem("Purchase Receival")
	   setParent("Purchase Receival", "Transaction")
	   setChildrenAllowed( "Purchase Receival", false)
	   addItem("Sales Order")
	   setParent("Sales Order", "Transaction")
	   setChildrenAllowed( "Sales Order", false)
	   addItem("Delivery Order")
	   setParent("Delivery Order", "Transaction")
	   setChildrenAllowed( "Delivery Order", false)
	   //Report
	   //Parent
	   addItem( "Adjustment" )
	   //Child
	   addItem("StockAdjusment")
	   setParent("StockAdjusment", "Adjustment")
	   setChildrenAllowed( "StockAdjusment", false)
	   
	   if (currentUser.hasRole("Administrator"))
	   {
		   //Parent
		   addItem( "Settings" )
		   setChildrenAllowed( "Settings", true)
		   //Child
		   addItem("Role")
		   setParent("Role", "Settings")
		   setChildrenAllowed( "Role", false)
		   addItem("User")
		   setParent("User", "Settings")
		   setChildrenAllowed( "User", false)
	   }
			  
   }
   
   
}
