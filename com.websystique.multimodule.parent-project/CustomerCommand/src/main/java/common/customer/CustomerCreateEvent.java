package common.customer;

import java.util.HashSet;
import java.util.Set;

public class CustomerCreateEvent extends CustomerEvent {

	private String email;
	private String password;
	private String firstName;
	private String secondName;
	private String state;
	private Set<UserProfile> userProfiles= new HashSet<UserProfile>();

	public CustomerCreateEvent(){}


	public CustomerCreateEvent(String email, String password, String firstName, String secondName, String state,
			Set<UserProfile> userProfiles) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.secondName = secondName;
		this.state = state;
		this.userProfiles = userProfiles;
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


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getSecondName() {
		return secondName;
	}


	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public Set<UserProfile> getUserProfiles() {
		return userProfiles;
	}


	public void setUserProfiles(Set<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

}
