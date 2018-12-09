package backend;

import java.util.Date;

import common.recipes.RecipeState;

public class CreateRecipeCommand implements RecipeCommand {

 // private String recipeId;
  private String title;
  private int likesNumber;
  private String description;
  private String author;
  private Date submissionDate;
  private RecipeState state;
  private String photo;
  private String CustomerEmail;


 public CreateRecipeCommand( String title, int likesNumber, 
		String description,String author,String CustomerEmail,Date submissionDate,String photo){
    this.title = title;
    this.likesNumber = likesNumber;
    this.description = description;
    this.author=author;
    this.setCustomerEmail(CustomerEmail);
    this.submissionDate=submissionDate;
    this.photo=photo;
    state=RecipeState.New;
  }



public int getLikesNumber() {
    return likesNumber;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
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



	public String getCustomerEmail() {
		return CustomerEmail;
	}



	public void setCustomerEmail(String customerEmail) {
		CustomerEmail = customerEmail;
	}
}
