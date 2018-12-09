package backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import common.recipes.ModerationState;

public class PhotoModeratorService  {

  private final AggregateRepository<PhotoModerator, PhotoModeratorCommand> recipeRepository;
  private static final Random random= new Random();
  
  public PhotoModeratorService(AggregateRepository<PhotoModerator, PhotoModeratorCommand> recipeRepository) {
    this.recipeRepository = recipeRepository;
 
  }

  public CompletableFuture<EntityWithIdAndVersion<PhotoModerator>> moderateRecipe(String recipeEntityId) throws InterruptedException{
	  
	  return recipeRepository.save(new PhotoModeratedCommand(photoModeation(),recipeEntityId));

  }
  

	public ModerationState photoModeation() throws InterruptedException {
		
		int delay=500+random.nextInt(2500);
		Thread.sleep(delay);
		System.out.println(delay);
		return delay>2500? ModerationState.NotAllowed :ModerationState.Allowed;
  
}
  
}
