package backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EventuateAggregateStore;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEventHandlers
public class RecipeConfiguration {

  @Bean
  public RecipeService recipeService(AggregateRepository<Recipe, RecipeCommand> recipeRepository) {
    return new RecipeService(recipeRepository);
  }

  @Bean
  public AggregateRepository<Recipe, RecipeCommand> recipeRepository(EventuateAggregateStore eventStore) {
    return new AggregateRepository<Recipe, RecipeCommand>(Recipe.class, eventStore);
  }
  


}


