package app.widget
import com.vaadin.server.UserError
import com.vaadin.ui.Notification
import com.vaadin.server.Page
class GeneralFunction {
	public setErrorUI(def tv,def object){
		if (object.errors.hasGlobalErrors()){
//			Notification.show("Error",
//				object.errors.getGlobalError().defaultMessage,
//				
//				Notification.Type.ERROR_MESSAGE,Page.getCurrent())
			
			new Notification("Error",
				object.errors.getGlobalError().defaultMessage,
				Notification.Type.ERROR_MESSAGE, true)
				.show(Page.getCurrent());
		}
		else
		{
			for (i in tv){
				i.setComponentError(null)
				}
			
			for ( i in tv ) {
				if(i.getData() == object.errors.getFieldError().getField())
				{
					i.setComponentError(new UserError(object.errors.getFieldError().defaultMessage))
				}
			}
		}
	}
}
