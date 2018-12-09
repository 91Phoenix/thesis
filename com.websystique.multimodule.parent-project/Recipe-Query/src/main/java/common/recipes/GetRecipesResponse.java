package common.recipes;

import java.util.ArrayList;

public class GetRecipesResponse {
	
	private ArrayList<GetRecipeResponse> responses;
	
	public GetRecipesResponse(){
		this.responses= new ArrayList<GetRecipeResponse>();
	}
	public GetRecipesResponse(ArrayList<GetRecipeResponse> responses){
		this.setResponses(responses);
	}
	public ArrayList<GetRecipeResponse> getResponses() {
		return responses;
	}
	public void setResponses(ArrayList<GetRecipeResponse> responses) {
		this.responses = responses;
	}
	
	public void addResponse(GetRecipeResponse resp)
	{
		resp.toString();
		responses.add(resp);
	}
	
	public int size()
	{
		return responses.size();
	}

}
