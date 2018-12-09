package backend;

public class RecipeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

public RecipeNotFoundException(String recipeId) {
    super("Recipe not found " + recipeId);
  }
}
