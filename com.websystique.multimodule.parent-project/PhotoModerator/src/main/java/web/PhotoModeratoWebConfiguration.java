package web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import backend.PhotoModeratorConfiguration;

@Configuration
@Import({PhotoModeratorConfiguration.class})
@ComponentScan
@EnableAutoConfiguration
public class PhotoModeratoWebConfiguration {


}
