package web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import backend.DescriptionModeratorConfiguration;

@Configuration
@Import({DescriptionModeratorConfiguration.class})
@ComponentScan
@EnableAutoConfiguration
public class DescriptionModeratoWebConfiguration {


}
