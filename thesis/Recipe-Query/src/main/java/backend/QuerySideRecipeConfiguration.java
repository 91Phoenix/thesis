package backend;


import io.eventuate.javaclient.spring.EnableEventHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@EnableEventHandlers
public class QuerySideRecipeConfiguration {

  @Bean
  public RecipeQueryWorkflow recipeQueryWorkflow(RecipeInfoUpdateService recipeInfoUpdateService) {
    return new RecipeQueryWorkflow(recipeInfoUpdateService);
  }

  @Bean
  public RecipeInfoUpdateService recipeInfoUpdateService(RecipeInfoRepository recipeInfoRepository, MongoTemplate mongoTemplate) {
    return new RecipeInfoUpdateService(recipeInfoRepository, mongoTemplate);
  }

  @Bean
  public RecipeQueryService recipeQueryService(RecipeInfoRepository recipeInfoRepository) {
    return new RecipeQueryService(recipeInfoRepository);
  }

  @Bean
  public QuerySideDependencyChecker querysideDependencyChecker(MongoTemplate mongoTemplate) {
    return new QuerySideDependencyChecker(mongoTemplate);
  }



}
