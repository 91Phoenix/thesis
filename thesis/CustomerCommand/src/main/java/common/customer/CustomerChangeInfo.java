package common.customer;

import backend.RecipeInfo;

public class CustomerChangeInfo {

  private String changeId;
  private RecipeInfo newRecipe;
  private RecipeInfo recipeToDelete;
  
  public CustomerChangeInfo() {
  }

  
public void setNewRecipe(RecipeInfo recipe)
{
	this.newRecipe=recipe;
}
  
public RecipeInfo getNewRecipe() {
	
	return newRecipe;
}

public String getRecipeId() {
	return recipeToDelete.getId();
}


public RecipeInfo getRecipeToDelete() {
	return recipeToDelete;
}


public void setRecipeToDelete(RecipeInfo recipeToDelete) {
	this.recipeToDelete = recipeToDelete;
}


public String getChangeId() {
	return changeId;
}


public void setChangeId(String changeId) {
	this.changeId = changeId;
}
}
