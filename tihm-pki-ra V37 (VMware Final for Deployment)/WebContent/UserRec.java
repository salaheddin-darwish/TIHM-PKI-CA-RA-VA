package org.tihm.pki.ra;

public class UserRec {
	
	public int id;
	public String firstname;
	public String lastname;
	public String email;
	public String organization;
	public String password;
	public String reconfirmpassword;
	public String role;
	public int 	  failAttmptCount = 0;
	public boolean authenticated = false;
	

	
	public UserRec(String firstname, String lastname, String organization, String email, String password, String role,int id) {
//	public User(String firstname, String lastname, String email,String inputpassword, String saltval) {
		this.id = id ;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.organization = organization;
		this.role = role;
		}
	
	public UserRec(String firstname, String lastname,String organization, String email, String password, String confirmpassword, String role,int id) {
//		public User(String firstname, String lastname, String email,String inputpassword, String saltval) {
			this.firstname = firstname;
			this.lastname = lastname;
			this.email = email;
			this.password = password;
			this.reconfirmpassword= confirmpassword;
			this.organization = organization;
			this.role = "user";
			this.id=id;			
			}
		
	
	
	
	public int getFailAttmptCount() {
		return failAttmptCount;
	}

	public void setFailAttmptCount(int failAttmptCount) {
		this.failAttmptCount = failAttmptCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPassword() {
		return password;
	}
	

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	
	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public Boolean validate(Model model) throws Exception {
		return
			   model.validateName(this.firstname, "First Name")
			&& model.validateName(this.lastname, "Last Name")
			&& model.validateName(this.organization, "Organizatio Name")
			&& model.validateName(this.email, "Email Address or Username")			
			//&& model.validateEmail(email)
		    && model.validatePassword(this.password, this.reconfirmpassword);
	}
}
