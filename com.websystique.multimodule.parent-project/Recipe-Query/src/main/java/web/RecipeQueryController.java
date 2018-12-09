package web;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import common.recipes.GetRecipeResponse;
import common.recipes.GetRecipesResponse;
import common.recipes.ModerationState;
import backend.RecipeInfo;
import backend.RecipeInfoUpdateService;
import backend.RecipeQueryService;

@RestController
@RequestMapping("/api")
public class RecipeQueryController {

  private RecipeQueryService recipeQueryService;
  @Autowired
  public RecipeQueryController(RecipeQueryService recipeQueryService,
		  RecipeInfoUpdateService recipeInfoUpdateService  ) {
    this.recipeQueryService = recipeQueryService;
  }
  
  @RequestMapping(value = "/recipeByTitle/{title}", method=RequestMethod.GET)
  public ResponseEntity<GetRecipeResponse> getByTitolo(@PathVariable("title") String title) {
	
    RecipeInfo recipeInfo = recipeQueryService.findRecipeByTitle(title);
    return ResponseEntity.ok().body(createNewRecipeResponseObject(recipeInfo));
  }
  
  
  @RequestMapping(value = "/recipeById/{id}", method=RequestMethod.GET)
  public ResponseEntity<GetRecipeResponse> getById(@PathVariable("id") String id) {
	
    RecipeInfo recipeInfo = recipeQueryService.findRecipeById(id);
    return ResponseEntity.ok().body(createNewRecipeResponseObject(recipeInfo));
  }
  
  @RequestMapping(value = "/getRecipes/", method=RequestMethod.GET)
  public ResponseEntity<GetRecipesResponse> geRecipes() {
	
	GetRecipesResponse result= new GetRecipesResponse();
    recipeQueryService.findAll().stream().filter(rec-> 
    	rec.getDescriptionModeration()== ModerationState.Allowed && rec.getPhotoModeration()==ModerationState.Allowed).
    		collect(Collectors.toList()).
	    		forEach(recipeInfo -> result.addResponse(createNewRecipeResponseObject(recipeInfo)));
    
    return ResponseEntity.ok().body(result);
	 
  }
  
  private GetRecipeResponse createNewRecipeResponseObject(RecipeInfo recipeInfo) {
		return new GetRecipeResponse(recipeInfo.getId(),
	    		recipeInfo.getTitle(), recipeInfo.getLikesNumber(),
	    			recipeInfo.getDescription(), recipeInfo.getAuthor(),
	    				recipeInfo.getState(), recipeInfo.getPhoto(),
	    					recipeInfo.getPhotoModeration(),recipeInfo.getDescriptionModeration());
	}
  
}
