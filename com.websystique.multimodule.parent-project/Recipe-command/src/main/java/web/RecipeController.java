package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import common.recipes.CreateRecipeResponse;
import common.recipes.GetRecipeResponse;
import io.eventuate.EntityWithIdAndVersion;
import backend.Recipe;
import backend.RecipeService;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

  private RecipeService recipeService;
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  
  @Autowired
  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public CompletableFuture<CreateRecipeResponse> createRecipe(
		  @RequestParam("titolo") String Titolo,
		  @RequestParam("descrizione") String descrizione,
		  @RequestParam("autore") String autore,
		  @RequestParam("CustomerEmail") String CustomerEmail,
		  @RequestParam("file") MultipartFile file) throws IOException
  {
		return recipeService.createRecipe(Titolo, 0, descrizione, autore,CustomerEmail, new Date(),file)
				  .thenApply(entityAndEventInfo -> new CreateRecipeResponse(entityAndEventInfo.getEntityId()));
  }
  
  @RequestMapping(method = RequestMethod.POST,value="/update")
  public CompletableFuture<GetRecipeResponse> updateRecipe(
		  @RequestParam("descrizione") Optional<String> descrizione ,
		  @RequestParam("id") String id,
		  @RequestParam("file") Optional<MultipartFile> file) throws IOException
  {
		return recipeService.updateRecipe(id, descrizione,file).
		thenApply(entityAndEventInfo -> createGetRecipeResponse(entityAndEventInfo));
  }
  
  
  @RequestMapping(method = RequestMethod.POST, value="/addLike")
  public CompletableFuture<GetRecipeResponse> addLikeController(
		  @RequestParam("id") String entityID){
	  
	  return recipeService.updateNumberOfLikes(entityID)
			  .thenApply(entityAndEventInfo -> createGetRecipeResponse(entityAndEventInfo));
  }

  @RequestMapping(value = "/deleteRecipe/{id}",method=RequestMethod.DELETE)
  public CompletableFuture<GetRecipeResponse> deleteRecipe(@PathVariable("id") String recipeId) {

	  logger.info("deleting recipe with id: "+ recipeId);
     return recipeService.deleteRecipe(recipeId)
            .thenApply(entityAndEventInfo -> null);

  }
  
  public GetRecipeResponse createGetRecipeResponse(EntityWithIdAndVersion<Recipe> entityAndEventInfo)
  {
	  return new GetRecipeResponse(entityAndEventInfo.getEntityId(),entityAndEventInfo.getAggregate().getTitle(),
			  entityAndEventInfo.getAggregate().getLikesNumber(),entityAndEventInfo.getAggregate().getDescription(),
			  entityAndEventInfo.getAggregate().getAuthor(),entityAndEventInfo.getAggregate().getState(),
			  entityAndEventInfo.getAggregate().getPhoto());
  }
}
