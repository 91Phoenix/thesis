package backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import common.recipes.ModerationState;
import common.recipes.RecipeState;

import java.util.*;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by cer on 11/21/14.
 */
@Document
public class RecipeInfo {
	
	  private String id;
	  private String title;
	  private int likesNumber;
	  private String description;
	  private String author;
	  private String customerEmail;
	  @JsonProperty("date")
	  private Date submissionDate;
	  private RecipeState state;
	  private String photo;
	  private ModerationState photoModeration;
	  private ModerationState descriptionModeration;


private RecipeInfo() {
  }

  public RecipeInfo(String title, int likesNumber, 
			String description,String author,String customerEmail,
			String photo,ModerationState PhotoModeration,ModerationState descriptionModeration) {
    this( title, likesNumber, description, author,customerEmail,
    		new Date(), photo,PhotoModeration,descriptionModeration);
  }

  
  public RecipeInfo( String title, int likesNumber, 
			String description,String author,String customerEmail,Date submissionDate, String photo
			,ModerationState PhotoModeration,ModerationState descriptionModeration) {
	    this.title = title;
	    this.likesNumber = likesNumber;
	    this.description = description;
	    this.author=author;
	    this.submissionDate=submissionDate;
	    this.photo=photo;
	    this.customerEmail=customerEmail;
	    state=RecipeState.New;
	    this.photoModeration=PhotoModeration;
	    this.descriptionModeration=descriptionModeration;
	  }


public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public int getLikesNumber() {
	return likesNumber;
}

public void setLikesNumber(int likesNumber) {
	this.likesNumber = likesNumber;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getAuthor() {
	return author;
}

public void setAuthor(String author) {
	this.author = author;
}

public Date getSubmissionDate() {
	return submissionDate;
}

public void setSubmissionDate(Date submissionDate) {
	this.submissionDate = submissionDate;
}

public RecipeState getState() {
	return state;
}

public void setState(RecipeState state) {
	this.state = state;
}

public String getPhoto() {
	return photo;
}

public void setPhoto(String photo) {
	this.photo = photo;
}


public String getId() {
		return id;
	}

	public void setId(String eventID) {
		this.id = eventID;
	}


@Override
	public String toString() {
		return "RecipeInfo [id=" + id + ", title=" + title + ", likesNumber=" + likesNumber + ", description="
				+ description + ", author=" + author + ", customerEmail=" + customerEmail + ", submissionDate="
				+ submissionDate + ", state=" + state + ", photo=" + photo + ", photoModeration=" + photoModeration
				+ ", descriptionModeration=" + descriptionModeration + "]";
	}

public String getCustomerEmail() {
	return customerEmail;
}

public void setCustomerEmail(String customerEmail) {
	this.customerEmail = customerEmail;
}

public ModerationState getPhotoModeration() {
	return photoModeration;
}

public void setPhotoModeration(ModerationState photoModeration) {
	this.photoModeration = photoModeration;
}

public ModerationState getDescriptionModeration() {
	return descriptionModeration;
}

public void setDescriptionModeration(ModerationState descriptionModeration) {
	this.descriptionModeration = descriptionModeration;
}
  

}
