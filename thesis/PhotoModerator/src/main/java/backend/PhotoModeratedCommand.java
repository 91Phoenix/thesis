package backend;

import common.recipes.ModerationState;

public class PhotoModeratedCommand implements PhotoModeratorCommand {

 // private String recipeId;
  private ModerationState state;
  private String recipeEntityId;


 public PhotoModeratedCommand( ModerationState state,String recipeEntityId){
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
