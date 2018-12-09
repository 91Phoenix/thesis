package common.recipes;

public class PhotoModeratedEvent extends PhotoModeratorEvent {


	private ModerationState state;
	private String recipeEntityId;


	public PhotoModeratedEvent(){}

	public PhotoModeratedEvent(ModerationState state,String recipeEntityId){
		this.setState(state);
		this.setRecipeEntityId(recipeEntityId);
	}

	public ModerationState getState() {
		return state;
	}

	public void setState(ModerationState state) {
		this.state = state;
	}

	public String getRecipeEntityId() {
		return recipeEntityId;
	}

	public void setRecipeEntityId(String recipeEntityId) {
		this.recipeEntityId = recipeEntityId;
	}


}
