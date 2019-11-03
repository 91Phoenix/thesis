package service;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import io.eventuate.local.java.jdbckafkastore.EventuateJdbcEventStoreConfiguration;
import web.PhotoModeratoWebConfiguration;

@Configuration
@Import({PhotoModeratoWebConfiguration.class, EventuateJdbcEventStoreConfiguration.class})
@EnableAutoConfiguration
@ComponentScan
public class PhotoModeratorServiceConfiguration {


	@Bean
	public HttpMessageConverters customConverters() {
		HttpMessageConverter<?> additional = new MappingJackson2HttpMessageConverter();
		return new HttpMessageConverters(additional);
	}

}
