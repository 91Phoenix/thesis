package common.recipes;

public class GetRecipeResponse {
	

	private String entityId;
	  private String title;
	  private int likesNumber;
	  private String description;
	  private String author;
	  private RecipeState state;
	  private String photo;
	  
	  public GetRecipeResponse()
	  {
		  
	  }	  
	  
	public GetRecipeResponse(String entityId, String title, int likesNumber,
			String description, String author,
			RecipeState state, String photo) {
		super();
		this.setEntityID(entityId);
		this.title = title;
		this.likesNumber = likesNumber;
		this.description = description;
		this.author = author;
		this.state = state;
		this.photo = photo;
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

	public String getEntityId() {
		return entityId;
	}

	public void setEntityID(String entityID) {
		this.entityId = entityID;
	}
	@Override
	public String toString() {
		return "GetRecipeResponse [entityId=" + entityId + ", title=" + title + ", likesNumber=" + likesNumber
				+ ", description=" + description + ", author=" + author + ", state=" + state + ", photo=" +
				photo + "]";
	}

 
}
