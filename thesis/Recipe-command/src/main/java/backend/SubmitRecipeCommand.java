package backend;

import common.recipes.RecipeState;

public class SubmitRecipeCommand implements RecipeCommand {
	
	private RecipeState state;
	public SubmitRecipeCommand() {
		super();
		this.state=RecipeState.ToConfirm;
	}

	public RecipeState getState() {
		return state;
	}

}
