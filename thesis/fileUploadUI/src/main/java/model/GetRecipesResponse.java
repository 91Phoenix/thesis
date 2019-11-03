package model;

import java.util.ArrayList;
import java.util.List;

public class GetRecipesResponse {
	
	private List<GetRecipeResponse> responses;
	
	public GetRecipesResponse(){
		this.responses= new ArrayList<GetRecipeResponse>();
	}
	public GetRecipesResponse(List<GetRecipeResponse> responses){
		this.setResponses(responses);
	}
	public List<GetRecipeResponse> getResponses() {
		return responses;
	}
	public void setResponses(List<GetRecipeResponse> responses) {
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
