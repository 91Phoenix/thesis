package service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

import model.CreateRecipeResponse;
import model.ModerationState;
import rx.Observable;

@Service
public class RecipeUploadService {

	@Autowired
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	public static final String ROOT = "temp-dir";
	private Logger logger = LoggerFactory.getLogger(getClass());

	//construct the service URL in this case not useful,but using a discovery server it is useful
	public RecipeUploadService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}

	@HystrixCommand(fallbackMethod="uploadRecipeFallback")
	public Observable<CreateRecipeResponse> uploadRecipe(MultipartFile file,String Titolo, String descrizione,
			String autore,String CustomerEmail) throws IllegalStateException, IOException {

		return  Observable.create(aSubscriber-> {
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		String fileName=(System.getProperty("user.dir")+"/"+ROOT+"/"+file.getOriginalFilename().replace("\\","/"));
		File tempFile= new File(fileName);
		try {file.transferTo(tempFile);} catch (IllegalStateException | IOException e) {e.printStackTrace();}
		FileSystemResource FSR = new FileSystemResource(fileName);
		parts.add("file",FSR );
		parts.add("descrizione", descrizione);
		parts.add("titolo", Titolo);
		parts.add("autore", autore);
		parts.add("CustomerEmail",CustomerEmail);
		logger.info(serviceUrl + "recipes");
		
			aSubscriber.onNext(  restTemplate.postForObject(serviceUrl + "recipes",parts,CreateRecipeResponse.class));
			tempFile.delete();
			aSubscriber.onCompleted();
		});	}

	public CreateRecipeResponse uploadRecipeFallback(MultipartFile file,String Titolo, String descrizione,
			String autore,String CustomerEmail){
		logger.info("unable to upload the recipe "+ Titolo);
		return new CreateRecipeResponse();
	}


	public CreateRecipeResponse uploadRecipeSyncRest(MultipartFile file,String Titolo, String descrizione,
			String autore,String CustomerEmail) throws IllegalStateException, IOException {

		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("descrizione", descrizione);
		descriptionModeratorController(parts);
		String fileName=(System.getProperty("user.dir")+"/"+ROOT+"/"+file.getOriginalFilename().replace("\\","/"));
		File tempFile= new File(fileName);
		file.transferTo(tempFile);
		FileSystemResource FSR = new FileSystemResource(fileName);
		parts.add("file",FSR );
		photoModeratorController(parts);
		parts.add("titolo", Titolo);
		parts.add("autore", autore);
		parts.add("CustomerEmail",CustomerEmail);

		logger.info(serviceUrl + "recipes");
		CreateRecipeResponse result= restTemplate.postForObject(serviceUrl + "recipes",parts,CreateRecipeResponse.class);
		tempFile.delete();
		return result;
	}

	@Async
	public  CompletableFuture<CreateRecipeResponse> uploadRecipeASyncRest
			(MultipartFile file,String Titolo, String descrizione,String autore,String CustomerEmail)
					throws IllegalStateException, IOException, InterruptedException, ExecutionException {

		MultiValueMap<String, Object> partsForDescriptionModerator = new LinkedMultiValueMap<String, Object>();
		MultiValueMap<String, Object> partsForPhotoModerator = new LinkedMultiValueMap<String, Object>();
		MultiValueMap<String, Object> partsForUpload = new LinkedMultiValueMap<String, Object>();
		String fileName=(System.getProperty("user.dir")+"/"+ROOT+"/"+file.getOriginalFilename());
		File tempFile= new File(fileName.replace("\\","/"));
		file.transferTo(tempFile);
		FileSystemResource FSR = new FileSystemResource(fileName.replace("\\","/"));
		partsForUpload.add("descrizione", descrizione);
		partsForDescriptionModerator.add("descrizione", descrizione);
		partsForUpload.add("file",FSR );
		partsForPhotoModerator.add("file",FSR );
		partsForUpload.add("titolo", Titolo);
		partsForUpload.add("autore", autore);
		partsForUpload.add("CustomerEmail",CustomerEmail);

		CompletableFuture<ModerationState> future1=
				CompletableFuture.supplyAsync(()->	
				restTemplate.postForObject(
						"http://description-Moderator-Service/"+"DescriptionModeration",partsForDescriptionModerator,ModerationState.class));


CompletableFuture<ModerationState> future2=
	CompletableFuture.supplyAsync(()->restTemplate.postForObject
		("http://photo-Moderator-Service/"+ "PhotoModeration",partsForPhotoModerator,ModerationState.class));

		//CompletableFuture<CreateRecipeResponse> future3=
		//	CompletableFuture.supplyAsync(()->
		CreateRecipeResponse res=	restTemplate.postForObject(serviceUrl + "recipes",partsForUpload,CreateRecipeResponse.class);

		CompletableFuture.allOf(future1,future2);
		tempFile.delete();
		System.out.println(future1.get()+" "+future2.get());

		return CompletableFuture.completedFuture(res);
	}

	public ModerationState descriptionModeratorController(MultiValueMap<String, Object> description) {

		logger.info("http://description-Moderator-Service/"+ "DescriptionModeration");
		return restTemplate.postForObject("http://description-Moderator-Service/"+"DescriptionModeration",description,ModerationState.class);

	}

	public ModerationState photoModeratorController(MultiValueMap<String, Object> file) {

		logger.info("http://photo-Moderator-Service/"+ "PhotoModerationSync");
		return restTemplate.postForObject("http://photo-Moderator-Service/"+ "PhotoModeration",file,ModerationState.class);

	}


}
