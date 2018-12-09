package controller;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import model.CustomerInfos;
import model.GetRecipeResponse;
import model.GetRecipesResponse;
import service.RecipeGetService;

@Controller
public class GetRecipeController {


	@Autowired
	protected RecipeGetService RecipeGetService;
	private Logger logger = LoggerFactory.getLogger(getClass());


	public GetRecipeController(RecipeGetService uploadeService) {
		this.RecipeGetService= uploadeService;
	}

	//recover all the recipes available in the recipe-query-side microservice
	@RequestMapping(method = RequestMethod.GET, value = "/vista")
	public String showRecipesController(Model model) throws InterruptedException, ExecutionException {

		logger.info("inside showRecipes Controller");
		DeferredResult<GetRecipesResponse> deferredrRes= new  DeferredResult<GetRecipesResponse>();
		RecipeGetService.getRecipes().subscribe(m->deferredrRes.setResult(m),e->deferredrRes.setErrorResult(e));
		model.addAttribute("Recipes",deferredrRes.getResult());
		return "vista";
	}

	//recover the recipe with a specific title in the recipe-query-side microservice
	@RequestMapping(value="/recipeByTitle")
	public String findRecipeByTitle(@RequestParam("title") String title, Model model) 
			throws InterruptedException, ExecutionException {

		logger.info("inside findRecipeByTitle Controller");
		DeferredResult<GetRecipeResponse> deferredrRes= new  DeferredResult<GetRecipeResponse>();
		RecipeGetService.getRecipeByTitle(title).subscribe(m->deferredrRes.setResult(m),e->deferredrRes.setErrorResult(e));
		model.addAttribute("Recipe",deferredrRes.getResult() );
		return "/customer/showCustomerRecipes";
	}
}
