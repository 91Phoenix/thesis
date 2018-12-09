package backend;

public class UpdateDescriptionRecipeCommand implements RecipeCommand {
	
	private String description;


	public UpdateDescriptionRecipeCommand(String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}


}
