package common.recipes;


public class UpdateRecipeDescriptionEvent extends RecipeEvent {
	
	private String description;

public UpdateRecipeDescriptionEvent(){}
	
	public UpdateRecipeDescriptionEvent(String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description=description;
	}

}
