package common.recipes;


public class UpdateRecipePhotoEvent extends RecipeEvent {
	
	private String photo;
	
	public UpdateRecipePhotoEvent(){}
	
	public UpdateRecipePhotoEvent(String photo)
	{
		this.photo=photo;
	}
	
	public String getPhoto()
	{
		return photo;
	}

	public void setPhoto(String photo)
	{
		this.photo=photo;
	}
}
