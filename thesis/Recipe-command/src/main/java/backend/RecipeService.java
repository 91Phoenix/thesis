package backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.resize.ResizeProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class RecipeService  {

  private final AggregateRepository<Recipe, RecipeCommand> recipeRepository;
  //the best way would be insert this parameters in an external file
  private String bucketName="raffaelerotellatest";
  private String accessName="";
  private String secretKey="";
  public static final String ROOT = "temp-dir";
  private Logger logger = LoggerFactory.getLogger(getClass());
  
  public RecipeService(AggregateRepository<Recipe, RecipeCommand> recipeRepository) {
    this.recipeRepository = recipeRepository;
 
  }

  public CompletableFuture<EntityWithIdAndVersion<Recipe>> createRecipe(String title, int likesNumber, 
		  String description,String author,String CustomerEmail,Date submissionDate, MultipartFile file) throws IllegalStateException, IOException{

	  String photo = amazoS3FileUpload(file); 
	  return recipeRepository.save(new CreateRecipeCommand(title,
			  likesNumber, description,author,CustomerEmail,submissionDate,photo));

  }

private String amazoS3FileUpload(MultipartFile file) throws IOException, MalformedURLException {
	
	  AmazonS3 s3client= new AmazonS3Client(new BasicAWSCredentials(accessName, secretKey));
	  String fileName=(System.getProperty("user.dir")+"/"+ROOT+"/"+file.getOriginalFilename()).replace("\\","/");
	  File f= new File(fileName);
	  file.transferTo(f);
	  MBFImage image = ImageUtilities.readMBF(f);
	  ResizeProcessor rp= new ResizeProcessor(380,250,true);
	  image.process(rp);
	  ImageUtilities.write(image,f);
	  s3client.putObject(new PutObjectRequest(bucketName, fileName, f).
			  withCannedAcl(CannedAccessControlList.PublicRead));
	  logger.info("file "+fileName+" published on Amazon S3");
	  String photo = "https://s3-eu-west-1.amazonaws.com/" + bucketName + "/" + fileName;
	  if(f.delete()) logger.info("temp file deleted");
	return photo;
}
  
 

  public CompletableFuture<EntityWithIdAndVersion<Recipe>> deleteRecipe(String recipeId) {
    return recipeRepository.update(recipeId, new DeleteRecipeCommand());
  }
  
  
  public CompletableFuture<EntityWithIdAndVersion<Recipe>> updateNumberOfLikes(String entityId)
  {
	  return recipeRepository.update(entityId, new UpdateRecipeLikeCommand());
  }

  public CompletableFuture<EntityWithIdAndVersion<Recipe>> updateRecipe(String id, Optional<String> descrizione, Optional<MultipartFile> file) 
		  throws MalformedURLException, IOException{
	 
	  CompletableFuture<EntityWithIdAndVersion<Recipe>> result=null;
	  if(descrizione.isPresent())
		  result=recipeRepository.update(id, new UpdateDescriptionRecipeCommand(descrizione.get()));
	 
	 if(file.isPresent())
		 result=recipeRepository.update(id, new UpdatePhotoRecipeCommand(amazoS3FileUpload(file.get())));
	
	  return result;
  }
  
}
