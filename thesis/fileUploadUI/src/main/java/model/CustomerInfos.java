package model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonRootName;


/**
 * Created by cer on 11/21/14.
 */
@JsonRootName("customer")
public class CustomerInfos {

	
	private String id;
	private String email;
	private String password;
	private String firstName;
	private String secondName;
	private String state;
	private Set<UserProfile> userProfiles= new HashSet<UserProfile>();
	private List<RecipeInfo> personalRecipes;

	public  CustomerInfos() {
	}

	public CustomerInfos( String email, String password, String firstName, String secondName,
			String state, Set<UserProfile> userProfiles,List<RecipeInfo> personalRecipes) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.secondName = secondName;
		this.state = state;
		this.userProfiles = userProfiles;
		this.personalRecipes=personalRecipes;
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

	public List<RecipeInfo> getPersonalRecipes() {
		return personalRecipes;
	}

	public void setPersonalRecipes(List<RecipeInfo> personalRecipes) {
		this.personalRecipes = personalRecipes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "CustomerInfo [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", secondName=" + secondName + ", state=" + state + ", userProfiles=" + userProfiles
				+ ", personalRecipes=" + personalRecipes + "]";
	}


}
