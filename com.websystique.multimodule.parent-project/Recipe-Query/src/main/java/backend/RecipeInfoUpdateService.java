package backend;

import common.recipes.ModerationState;
import common.recipes.RecipeChangeInfo;
import common.recipes.RecipeState;
import io.eventuate.Int128;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class RecipeInfoUpdateService {
  private Logger logger = LoggerFactory.getLogger(getClass());

  private RecipeInfoRepository recipeInfoRepository;
  private MongoTemplate mongoTemplate;

  public RecipeInfoUpdateService(RecipeInfoRepository recipeInfoRepository, MongoTemplate mongoTemplate) {
	  this.recipeInfoRepository = recipeInfoRepository;
	  this.mongoTemplate = mongoTemplate;
  }


  public void create(String eventId, String title, int likesNumber, 
		  String description,String author,String customerEmail,Date submissionDate, String photo, Int128 version) {
	  RecipeInfo test= recipeInfoRepository.findByTitle(title);
	  //ga gestire l'else per mandare una risposta negativa alla creazione
	  if(test== null){
		  try {
			  RecipeChangeInfo ci = new RecipeChangeInfo();
			  //mongoTemplate.
			  mongoTemplate.upsert(new Query(where("id").is(eventId).and("version").exists(false)),
					  new Update()

					  .set("title",title)
					  .set("author",author)
					  .set("customerEmail", customerEmail)
					  .set("description", description)
					  .set("photo", photo)
					  .set("descriptionModeration",ModerationState.NotModerated)
					  .set("photoModeration", ModerationState.NotModerated)
					  .set("likesNumber", likesNumber)
					  .push("changes", ci)
					  .set("creationDate", submissionDate)
					  .set("state",RecipeState.New)
					  .set("version", version.asString()),
					  RecipeInfo.class);
			  logger.info("Saved in mongo");

		  } catch (DuplicateKeyException t) {
			  logger.warn("When saving ", t);
		  } catch (Throwable t) {
			  logger.error("Error during saving: ", t);
			  //throw new RuntimeException(t);
		  }
	  }
  }

  public void delete(String recipeId) {
	  logger.info("deleting recipe with id:"+ recipeId);
	  recipeInfoRepository.delete(recipeId);
  }


  public void updateDescription(String recipeId, String changeId, RecipeChangeInfo ci) {
	  mongoTemplate.updateMulti(new Query(where("id").is(recipeId).and("version").lt(changeId)),
			  new Update().
			  set("description", ci.getNewDescription()).
			  push("changes", ci).
			  set("version", changeId),
			  RecipeInfo.class);
  }

  public void updateLikes(String recipeId, String changeId) {
	  mongoTemplate.updateMulti(new Query(where("id").is(recipeId).and("version").lt(changeId)),
			  new Update().
			  inc("likesNumber",1).
			  set("version", changeId),
			  RecipeInfo.class);
  }


  public void updatePhoto(String recipeId, String changeId, RecipeChangeInfo ci) {
	  mongoTemplate.updateMulti(new Query(where("id").is(recipeId).and("version").lt(changeId)),
			  new Update().
			  set("photo", ci.getNewPhoto()).
			  push("changes", ci).
			  set("version", changeId),
			  RecipeInfo.class);
  }


  public void updatePhotoModerationState(String recipeEntityId, ModerationState state) {
	  mongoTemplate.updateMulti(new Query(where("id").is(recipeEntityId)),
			  new Update().
			  set("photoModeration", state),
			  RecipeInfo.class);
	  logger.info("****BENCHMARK**** photo secondi:"+LocalDateTime.now().getSecond()+" nano secondi:"+LocalDateTime.now().getNano());
  }


  public void updateDescriptionModerationState(String recipeEntityId, ModerationState state) {
	  // TODO Auto-generated method stub
	  mongoTemplate.updateMulti(new Query(where("id").is(recipeEntityId)),
			  new Update().
			  set("descriptionModeration",state),
			  RecipeInfo.class);
	  logger.info("****BENCHMARK****desc seconds:"+LocalDateTime.now().getSecond()+" nano seconds:"+LocalDateTime.now().getNano());
  }
}
