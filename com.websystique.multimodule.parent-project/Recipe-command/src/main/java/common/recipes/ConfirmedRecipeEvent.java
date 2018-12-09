package common.recipes;


public class ConfirmedRecipeEvent extends RecipeEvent {
	
	  private RecipeState state;

	public ConfirmedRecipeEvent(RecipeState state) {
		super();
		this.state=state;
	}

	public RecipeState getState() {
		return state;
	}

}
