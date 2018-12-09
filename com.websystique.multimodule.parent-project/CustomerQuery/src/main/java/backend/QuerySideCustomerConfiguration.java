package backend;


import io.eventuate.javaclient.spring.EnableEventHandlers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories
@EnableEventHandlers
public class QuerySideCustomerConfiguration {

  @Bean
  public CustomerQueryWorkflow customerQueryWorkflow(CustomerInfoUpdateService customerInfoUpdateService) {
    return new CustomerQueryWorkflow(customerInfoUpdateService);
  }

  @Bean
  public CustomerInfoUpdateService customerInfoUpdateService(CustomerInfoRepository customerInfoRepository,
		  MongoTemplate mongoTemplate) {
    return new CustomerInfoUpdateService(customerInfoRepository, mongoTemplate);
  }

  @Bean
  public CustomerQueryService customerQueryService(CustomerInfoRepository customerInfoRepository) {
    return new CustomerQueryService(customerInfoRepository);
  }

  @Bean
  public QuerySideDependencyChecker querysideDependencyChecker(MongoTemplate mongoTemplate) {
    return new QuerySideDependencyChecker(mongoTemplate);
  }

}
