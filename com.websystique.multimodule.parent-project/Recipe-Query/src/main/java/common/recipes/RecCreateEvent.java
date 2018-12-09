package common.recipes;

import java.util.Date;

public class RecCreateEvent extends RecipeEvent {


	//private String recipeId;
	  private String title;
	  private int likesNumber;
	  private String description;
	  private String author;
	  private Date submissionDate;
	  private RecipeState state;
	  private String photo;
	  private String customerEmail; 
	  
  public RecCreateEvent(){}
	  
  public RecCreateEvent(String title, int likesNumber, 
			String description,String author,String customerEmail,Date submissionDate,String photo){
	    this.title = title;
	    this.likesNumber = likesNumber;
	    this.description = description;
	    this.author=author;
	    this.setCustomerEmail(customerEmail);
	    this.submissionDate=submissionDate;
	    this.photo=photo;
	    state=RecipeState.New;
  }


	public String getTitle() {
		return title;
	}

	public int getLikesNumber() {
		return likesNumber;
	}

	public String getDescription() {
		return description;
	}

	public String getAuthor() {
		return author;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public RecipeState getState() {
		return state;
	}

	public String getPhoto() {
		return photo;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
 
}
