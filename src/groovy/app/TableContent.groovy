package app

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Table
import com.vaadin.ui.VerticalLayout

class TableContent extends VerticalLayout{

	public  TableContent()
	{
		Table table = new Table()
		table.addContainerProperty("Column 1", String.class,"(default 1)")
		table.addContainerProperty("Column 2", String.class,"(default 2)")
		table.addContainerProperty("Column 3", String.class,"(default 3)")
		def values = new Object[2]
		Object[] tviscolumnless = ["projectionType","a"];
		tviscolumnless = ["projectionType","b","c"];
		
		
//		values[0] = "Hi"
//		values[1] = "There"
		
//		table.addItem([new Expando(map:"projectionType"),new Expando(map:"projectionType")], 1)
		table.addItem(tviscolumnless, 2)
		addComponent(table) 
	}	
	

}
