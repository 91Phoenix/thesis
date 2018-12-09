package common.recipes;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class RecipeChangeInfo {

  private String changeId;
  private String changeType;
  private String newDescription;
  private String newPhoto;

  public RecipeChangeInfo() {
  }

  public RecipeChangeInfo(String changeId, String changeType, String newDescription,
		  String newPhoto) {
	super();
	this.changeId = changeId;
	this.changeType = changeType;
	this.newDescription = newDescription;
	this.newPhoto = newPhoto;
}



public String getChangeId() {
	return changeId;
}



public void setChangeId(String changeId) {
	this.changeId = changeId;
}



public String getChangeType() {
	return changeType;
}



public void setChangeType(String changeType) {
	this.changeType = changeType;
}




public String getNewDescription() {
	return newDescription;
}



public void setNewDescription(String newDescription) {
	this.newDescription = newDescription;
}



public String getNewPhoto() {
	return newPhoto;
}



public void setNewPhoto(String newPhoto) {
	this.newPhoto = newPhoto;
}


@Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
}
