package service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import model.GetRecipeResponse;
import model.GetRecipesResponse;
import rx.Observable;

@Service
public class RecipeGetService {


	private String recipeQuerySideUrl ;
	@Autowired
	protected RestTemplate restTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public RecipeGetService(String service) {

		this.recipeQuerySideUrl= service.startsWith("http") ? service
				: "http://" + service;
	}


	@HystrixCommand(fallbackMethod="getDefaultRecipe")
	public Observable<GetRecipeResponse> getRecipeByTitle(String title)
	{
		Map<String, Object> parts = new HashMap<String, Object>();
		parts.put("title", title);
		logger.info(getRecipeQuerySideUrl() + "api/recipeByTitle");
		return Observable.create(aSubscriber-> {
			aSubscriber.onNext(restTemplate.
				getForObject(getRecipeQuerySideUrl() + "api/recipeByTitle/{title}",GetRecipeResponse.class,parts));
					aSubscriber.onCompleted();});
	}
	

	@HystrixCommand(fallbackMethod="getDefaultRecipe")
	public Observable<GetRecipeResponse> getRecipeById(String id) {

		Map<String, Object> parts = new HashMap<String, Object>();
		parts.put("id", id);
		logger.info(getRecipeQuerySideUrl() + "api/recipeById");
		return Observable.create(aSubscriber-> {aSubscriber.onNext(
				restTemplate.getForObject(getRecipeQuerySideUrl() + "api/recipeById/{id}",GetRecipeResponse.class,parts));
					aSubscriber.onCompleted();});
	}


	@HystrixCommand(fallbackMethod="getDefaultRecipes")
	public Observable<GetRecipesResponse> getRecipes() {

		logger.info(getRecipeQuerySideUrl() + "api/getRecipes");
		return Observable.create(aSubscriber-> {
			aSubscriber.onNext(restTemplate.
				getForObject(getRecipeQuerySideUrl() + "api/getRecipes/",GetRecipesResponse.class));
					aSubscriber.onCompleted();});
	}
	
	
	public GetRecipesResponse getDefaultRecipes(){
		logger.info("getRecipes Fallback");
		return new GetRecipesResponse();
	}

	public String getRecipeQuerySideUrl() {
		return recipeQuerySideUrl;
	}
	

	public GetRecipeResponse getDefaultRecipe(String param){
		logger.info("getRecipe Fallback.. not found recipe");
		return new GetRecipeResponse();
	}

}
