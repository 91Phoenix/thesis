package common.customer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

import backend.RecipeInfo;

@JsonRootName("customerRecipes")
public class GetCustomerRecipesResponse {
	
	private List<RecipeInfo> recipes= new ArrayList<RecipeInfo>();
	
	public GetCustomerRecipesResponse()
	{}

	public GetCustomerRecipesResponse(List<RecipeInfo> recipes) {
		super();
		this.recipes = recipes;
	}

	public List<RecipeInfo> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<RecipeInfo> recipes) {
		this.recipes = recipes;
	}
}
