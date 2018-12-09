package backend;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

public CustomerNotFoundException(String recipeId) {
    super("Customer not found " + recipeId);
  }
}
