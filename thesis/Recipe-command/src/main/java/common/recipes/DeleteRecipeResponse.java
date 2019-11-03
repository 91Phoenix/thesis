package common.recipes;


public class DeleteRecipeResponse {

  private String recipeId;

  public DeleteRecipeResponse() {
  }

  public DeleteRecipeResponse(String recipeId) {
    this.recipeId = recipeId;
  }

  public String getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(String recipeId) {
    this.recipeId = recipeId;
  }
}
