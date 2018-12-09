package backend;

import common.recipes.RecipeState;

public class ConfirmRecipeCommand implements RecipeCommand {
	
	private RecipeState state;
	
	public ConfirmRecipeCommand() {
		super();
		this.state=RecipeState.Confirmed;
	}

	public RecipeState getState() {
		return state;
	}
}

