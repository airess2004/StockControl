package app

import com.vaadin.data.validator.AbstractValidator
import com.vaadin.data.validator.EmailValidator
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox
import com.vaadin.ui.CustomComponent
import com.vaadin.ui.PasswordField
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Alignment
import com.vaadin.ui.Notification
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.crypto.hash.Sha256Hash
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.subject.SimplePrincipalCollection
import org.apache.shiro.subject.Subject
import org.apache.shiro.util.ThreadContext
//import org.apache.shiro.web.util.SavedRequest

import com.vaadin.annotations.Theme;

//@Theme("Reindeer")
class LoginForm extends CustomComponent implements View, Button.ClickListener {
	
	public static final String NAME = "login";
	
	private final TextField user;
	
	private final PasswordField password;
	
	private final CheckBox remember;
	
	private final Button loginButton;
	
	def shiroSecurityManager
		
	public  LoginForm()
	{
		setSizeFull();
		
		// Create the user input field
		user = new TextField("User:");
		user.setWidth("300px");
		user.setRequired(true);
		user.setInputPrompt("Your username (eg. joe@email.com)");
		//user.setValue("");
		user.setNullRepresentation("");
		user.addValidator(new UsernameValidator());
//		user.addValidator(new EmailValidator(
//			"Username must be an email address"));
		//user.setInvalidAllowed(false);
		
		// Create the password input field
		password = new PasswordField("Password:");
		password.setWidth("300px");
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");
		
		// Create login button
		remember = new CheckBox("Remember Me", false);
		
		// Create login button
		loginButton = new Button("Login", this);
		loginButton.setClickShortcut(KeyCode.ENTER);
		loginButton.addStyleName("primary"); //Reindeer.BUTTON_DEFAULT
		
		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(user, password, remember, loginButton);
		fields.setCaption("Please login to access the application.");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();
	
		// The view root layout
		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		//viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		setCompositionRoot(viewLayout);
	}
	
	// Validator for validating the passwords
	private static final class UsernameValidator extends
			AbstractValidator<String> {

		public UsernameValidator() {
			super("Username must not be empty."); //super("The password provided is not valid");
		}

		@Override
		protected boolean isValidValue(String value) {
			//
			// Password must be at least 1 characters long and contain at least
			// one number
			//
			if (value == null
					|| (value.length() < 1 /*|| !value.matches(".*\\d.*")*/)) {
				return false;
			}
			return true;
		}

		@Override
		public Class<String> getType() {
			return String.class;
		}
	}
	
	// Validator for validating the passwords
	private static final class PasswordValidator extends
			AbstractValidator<String> {

		public PasswordValidator() {
			super("Password must be at least 4 characters long"); //super("The password provided is not valid");
		}

		@Override
		protected boolean isValidValue(String value) {
			//
			// Password must be at least 4 characters long and contain at least
			// one number
			//
			if (value == null
					|| (value.length() < 4 /*|| !value.matches(".*\\d.*")*/)) {
				return false;
			}
			return true;
		}

		@Override
		public Class<String> getType() {
			return String.class;
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// focus the username field when user arrives to the login view
        user.focus();
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		//
        // Validate the fields using the navigator. By using validors for the
        // fields we reduce the amount of queries we have to use to the database
        // for wrongly entered passwords
        //
        if (!user.isValid() || !password.isValid()) {
            return;
        }

        String username = user.getValue();
        String password = this.password.getValue();

        //
        // Validate username and password with database here. For examples sake
        // I use a dummy username and password.
        //
        boolean isValid = SignIn(username, password, remember.getValue() as boolean) //username.equals("test@test.com") && password.equals("passw0rd");

        if (isValid) {

            // Store the current user in the service session
            getSession().setAttribute("user", String.valueOf(username).toUpperCase());

            // Navigate to main view
            getUI().getNavigator().navigateTo("MAINVIEW"/*LoginFormMainView.NAME*/);//

        } else {

            // Wrong password clear the password field and refocuses it
            this.password.setValue(null);
            this.password.focus();

        }
		
	}
	
	public boolean SignIn(String username, String password, boolean rememberMe = false)
	{
		//Object userIdentity = username
//		String realmName = "ShiroDbRealm";
//		PrincipalCollection principals = new SimplePrincipalCollection(username, realmName);
//		Subject subject = new Subject.Builder().principals(principals).buildSubject();
//		ThreadContext.bind(subject)
		
		Subject currentUser = SecurityUtils.getSubject();
		if ( !currentUser.isAuthenticated() ) {
			def authToken = new UsernamePasswordToken(String.valueOf(username).toUpperCase(), password) //new Sha256Hash(password).toHex()
		
			// Support for "remember me"
			authToken.rememberMe = rememberMe
//			if (rememberMe) {
//				authToken.rememberMe = true
//			}
			
			// If a controller redirected to this page, redirect back
			// to it. Otherwise redirect to the root URI.
			//def targetUri = params.targetUri ?: "/"
				
			// Handle requests saved by Shiro filters.
//			SavedRequest savedRequest = WebUtils.getSavedRequest(request)
//			if (savedRequest) {
//				targetUri = savedRequest.requestURI - request.contextPath
//				if (savedRequest.queryString) targetUri = targetUri + '?' + savedRequest.queryString
//			}
				
			try{
				// Perform the actual login. An AuthenticationException
				// will be thrown if the username is unrecognised or the
				// password is incorrect.
				
				currentUser.login(authToken) //SecurityUtils.subject.login(authToken)
				//SecurityUtils.subject.getSession().setTimeout(300000)
				log.info "Welcome" //"Redirecting to '${targetUri}'."
//				redirect(uri: targetUri)
				getUI().getPage().getCurrent().getJavaScript().execute("window.location.reload();");
				}
			catch (AuthenticationException ex){
				// Authentication failed, so display the appropriate message
				// on the login page.
				log.info "Authentication failure for user '${username}'."
				Notification.show("Login Failed\n",
					ex.message,
					Notification.Type.ERROR_MESSAGE)
				return false
			}
			catch (Exception ex){
				// Authentication failed, so display the appropriate message
				// on the login page.
				log.info "Authentication Exception for user '${username}'."
				Notification.show("Login Failed\n",
					ex.message,
					Notification.Type.ERROR_MESSAGE)
				return false
			}
		}
		return true
	}
	
}
