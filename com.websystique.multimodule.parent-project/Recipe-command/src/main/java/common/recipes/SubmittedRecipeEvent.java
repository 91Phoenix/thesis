package common.recipes;

public class SubmittedRecipeEvent extends RecipeEvent {
	
	
	private RecipeState state;
	public SubmittedRecipeEvent(RecipeState state) {
		super();
		this.state=state;
	}

	public RecipeState getState() {
		return state;
	}
}
