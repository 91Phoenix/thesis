package backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EventuateAggregateStore;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEventHandlers
public class DescriptionModeratorConfiguration {

  @Bean
  public DescriptionModeratorWorkflow descriptionModeratorWorkflow(DescriptionModeratorService descriptionModeratorService) {
    return new DescriptionModeratorWorkflow(descriptionModeratorService);
  }

  @Bean
  public DescriptionModeratorService descriptionModeratorService(AggregateRepository<DescriptionModerator, DescriptionModeratorCommand> descriptionModeratorRepository) {
    return new DescriptionModeratorService(descriptionModeratorRepository);
  }

  @Bean
  public AggregateRepository<DescriptionModerator, DescriptionModeratorCommand> DescriptionModeratorRepository(EventuateAggregateStore eventStore) {
    return new AggregateRepository<DescriptionModerator, DescriptionModeratorCommand>(DescriptionModerator.class, eventStore);
  }
  


}


