package backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EventuateAggregateStore;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEventHandlers
public class PhotoModeratorConfiguration {

  @Bean
  public PhotoModeratorWorkflow recipeWorkflow(PhotoModeratorService photoModeratorService) {
    return new PhotoModeratorWorkflow(photoModeratorService);
  }

  @Bean
  public PhotoModeratorService recipeService(AggregateRepository<PhotoModerator, PhotoModeratorCommand> recipeRepository) {
    return new PhotoModeratorService(recipeRepository);
  }

  @Bean
  public AggregateRepository<PhotoModerator, PhotoModeratorCommand> recipeRepository(EventuateAggregateStore eventStore) {
    return new AggregateRepository<PhotoModerator, PhotoModeratorCommand>(PhotoModerator.class, eventStore);
  }
  


}


