package common.customer;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("userProfile")
public class UserProfile {
	
	private String type;
	
	public UserProfile()
	{}
	
	public UserProfile(String type)
	{
		this.type=type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
