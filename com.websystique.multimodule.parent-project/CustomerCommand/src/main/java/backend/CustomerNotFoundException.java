package backend;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

<<<<<<< HEAD
public CustomerNotFoundException(String customerID) {
    super("Customer not found " + customerID);
=======
public CustomerNotFoundException(String recipeId) {
    super("Customer not found " + recipeId);
>>>>>>> branch 'master' of https://github.com/91Phoenix/CustomerQuery.git
  }
}
