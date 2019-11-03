package common.recipes;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import java.util.Date;

public class CreateRecipeRequest {

  @NotNull
  private String recipeId;
  
  @NotNull
  private String title;
  
  @NotNull
  @DecimalMin("0")
  private int likesNumber;

@NotNull
  private String description;
  
  @NotNull
  private String author;
  
  @NotNull
  private Date submissionDate;
  
  @NotNull
  private RecipeState state;
  
 // @NotNull
  private String photo;

  public CreateRecipeRequest(String recipeId, String title, int likesNumber,
			String description, String author, Date submissionDate,
			RecipeState state, String photo) {
		super();
		this.recipeId = recipeId;
		this.title = title;
		this.likesNumber = likesNumber;
		this.description = description;
		this.author = author;
		this.submissionDate = submissionDate;
		this.state = state;
		this.photo = photo;
	}

public String getRecipeId() {
	return recipeId;
}

public void setRecipeId(String recipeId) {
	this.recipeId = recipeId;
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
  
}
