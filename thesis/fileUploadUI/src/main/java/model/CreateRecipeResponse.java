package model;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("recipe")
public class CreateRecipeResponse {
  
  private String recipeId;

  public CreateRecipeResponse() {
  }

  public CreateRecipeResponse(String recipeId) {
    this.setRecipeId(recipeId);
  }

public String getRecipeId() {
	return recipeId;
}

public void setRecipeId(String recipeId) {
	this.recipeId = recipeId;
}

 
}
