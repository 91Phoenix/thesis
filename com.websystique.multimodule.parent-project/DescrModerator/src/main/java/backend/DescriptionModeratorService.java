package backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import common.recipes.ModerationState;

public class DescriptionModeratorService  {

  private final AggregateRepository<DescriptionModerator, DescriptionModeratorCommand> descriptionModeratorRepository;
  private static final Random random= new Random();
  
  public DescriptionModeratorService(AggregateRepository<DescriptionModerator, DescriptionModeratorCommand> descriptionModeratorRepository) {
    this.descriptionModeratorRepository = descriptionModeratorRepository;
 
  }

  public CompletableFuture<EntityWithIdAndVersion<DescriptionModerator>> moderateRecipe(String entityId) throws InterruptedException{
	  
	  return descriptionModeratorRepository.save(new DescriptionModeratedCommand(descriptionModeation(),entityId));

  }
  

	public ModerationState descriptionModeation() throws InterruptedException {
		
		int delay=500+random.nextInt(2500);
		Thread.sleep(delay);
		System.out.println(delay);
		return delay>2500? ModerationState.NotAllowed :ModerationState.Allowed;
  
}
  
}
