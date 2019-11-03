package backend;

import io.eventuate.Int128;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BasicDBObject;

import common.customer.CustomerChangeInfo;
import common.customer.UserProfile;

import java.util.ArrayList;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class CustomerInfoUpdateService {
  private Logger logger = LoggerFactory.getLogger(getClass());

  private CustomerInfoRepository customerInfoRepository;
  private MongoTemplate mongoTemplate;

  public CustomerInfoUpdateService(CustomerInfoRepository customerInfoRepository, MongoTemplate mongoTemplate) {
    this.customerInfoRepository = customerInfoRepository;
    this.mongoTemplate = mongoTemplate;
  }


  public void create(String eventId, String email, String password, String firstName, String secondName,
			 String state, Set<UserProfile> userProfiles, Int128 version) {
    try {
      CustomerChangeInfo ci = new CustomerChangeInfo();
      //mongoTemplate.
      mongoTemplate.upsert(new Query(where("id").is(eventId).and("version").exists(false)),
              new Update()
                      .set("email", email)
                      .set("password",password)
                      .set("firstName", firstName)
                      .set("secondName", secondName)
                      .set("state", state)
                      .push("changes", ci)
                      .push("recipes", new ArrayList<RecipeInfo>())
                      .set("userProfiles", userProfiles)
                      .set("version", version.asString()),
              CustomerInfo.class);
      logger.info("Saved in mongo");

    } catch (DuplicateKeyException t) {
      logger.warn("When saving ", t);
    } catch (Throwable t) {
      logger.error("Error during saving: ", t);
      throw new RuntimeException(t);
    }
  }

  public void delete(String customerId) {
    customerInfoRepository.delete(customerId);
  }


  public void addRecipe(String recipeId, String changeId, CustomerChangeInfo ci) {
	  mongoTemplate.findAndModify(new Query(where("id").is(recipeId).and("version").lt(changeId))
			 , new Update().
			 
             addToSet("recipes", ci.getNewRecipe()).
             push("changes", ci).
             set("version", changeId)
             , CustomerInfo.class);
  }
  
  public void deleteRecipe(String recipeId, String changeId, CustomerChangeInfo ci) {
	  mongoTemplate.updateFirst(new Query(where("id").is(recipeId).
			  and("recipes.recipeId").is(ci.getRecipeId()).and("version").lt(changeId))
				 , new Update().
	             pull("recipes",new BasicDBObject("recipeId", ci.getRecipeId())).
	             push("changes", ci).
	             set("version", changeId)
	             , CustomerInfo.class);
	  }
  
}
