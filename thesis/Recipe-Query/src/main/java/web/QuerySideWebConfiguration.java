package web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import backend.QuerySideRecipeConfiguration;

@Configuration

@Import({QuerySideRecipeConfiguration.class})
@ComponentScan
@EnableAutoConfiguration
public class QuerySideWebConfiguration  {


}
