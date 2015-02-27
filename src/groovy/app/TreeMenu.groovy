package app

import com.vaadin.event.ItemClickEvent
import com.vaadin.event.ItemClickEvent.ItemClickListener
import com.vaadin.navigator.Navigator
import com.vaadin.ui.Tree

class TreeMenu extends Tree{
	
   public TreeMenu(){
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
			  
   }
   
   
}
