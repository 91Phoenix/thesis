package common.customer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.RecipeInfo;

public class CustomerJustCreatedEvent extends CustomerEvent {

	private String email;
	private String password;
	private String firstName;
	private String secondName;
	private String state;
	private Set<UserProfile> userProfiles= new HashSet<UserProfile>();
	 private List<RecipeInfo> recipes= new ArrayList<RecipeInfo>();

	public CustomerJustCreatedEvent(){}


	public CustomerJustCreatedEvent(String email, String password, String firstName, String secondName, String state,
			Set<UserProfile> userProfiles,List<RecipeInfo> recipes) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.secondName = secondName;
		this.state = state;
		this.userProfiles = userProfiles;
		this.setRecipes(recipes);
		
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


	public List<RecipeInfo> getRecipes() {
		return recipes;
	}


	public void setRecipes(List<RecipeInfo> recipes) {
		this.recipes = recipes;
	}

}
