package service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import model.GetRecipeResponse;
import rx.Observable;

@Service
public class RecipeUpdateService {

	@Autowired
	protected RestTemplate restTemplate;

	public static final String ROOT = "temp-dir";

	protected String serviceUrl;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public RecipeUpdateService(String service) {

		this.serviceUrl= service.startsWith("http") ? service
				: "http://" + service;
	}

	@HystrixCommand(fallbackMethod="updateRecipeFallback")
	public Observable<GetRecipeResponse> updateRecipe(MultipartFile file, String id, String description) throws IllegalStateException, IOException {

		return	Observable.create(aSubscriber-> {
			MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
			if(description!= null && !description.equals("")) parts.add("descrizione", description);
			parts.add("id", id);
			File tempFile=null;
			if(file!=null && file.getSize()>0)	{
				String fileName=(System.getProperty("user.dir")+"/"+ROOT+"/"+file.getOriginalFilename());
				tempFile= new File(fileName.replace("\\","/"));
				try {file.transferTo(tempFile);} catch (Exception e) {} 
				FileSystemResource FSR = new FileSystemResource(fileName.replace("\\","/"));
				parts.add("file",FSR );}
			logger.info(serviceUrl + "recipes/update");
			aSubscriber.onNext( restTemplate.postForObject(serviceUrl + "recipes/update",parts,GetRecipeResponse.class));
			if(tempFile!=null) tempFile.delete();
			aSubscriber.onCompleted();				
		});}

	public GetRecipeResponse updateRecipeFallback(MultipartFile file, String id, String description)
	{

		logger.info("updateRecipeFallback");
		return new GetRecipeResponse();	
	}

	@HystrixCommand(fallbackMethod="addLikeFallback")
	public Observable<GetRecipeResponse> addLikeToRecipeWithId(String title) {
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("id", title);
		logger.info(serviceUrl+ "recipes/addLike"+"/?id="+title);
		return Observable.create(aSubscriber-> {
			aSubscriber.onNext(restTemplate.
					postForObject(serviceUrl + "recipes/addLike/",parts,GetRecipeResponse.class));
			aSubscriber.onCompleted();});

	}

	public GetRecipeResponse addLikeFallback(String title)
	{
		logger.info("addLikeFallback");
		return new GetRecipeResponse();
	}

	@HystrixCommand(fallbackMethod="deleteFallback")
	@Async
	public void deleteRecipeWithId(String id) {
		Map<String, Object> parts = new HashMap<String, Object>();
		parts.put("id", id);
		logger.info(serviceUrl+ "recipes/deleteRecipe"+"/?id="+id);
		restTemplate.delete(serviceUrl + "recipes/deleteRecipe/{id}",parts);
	}

	public void deleteFallback(String id)
	{
		logger.info("delete fallback: at the moment is impossible to delete the recipe with id "+ id);
	}

}
