package backend;


public class UpdatePhotoRecipeCommand implements RecipeCommand {
private String photo;
	
	public UpdatePhotoRecipeCommand(String photo)
	{
		this.photo=photo;
	}
	
	public String getPhoto()
	{
		return photo;
	}
}
