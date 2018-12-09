package backend;

import io.eventuate.Int128;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import common.customer.CustomerChangeInfo;
import common.customer.UserProfile;
import common.recipes.ModerationState;
import common.recipes.RecipeChangeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
			String state, Set<UserProfile> userProfiles,List<RecipeInfo> recipes, Int128 version) {

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
					.set("personalRecipes",recipes)
					.set("userProfiles", userProfiles)
					.set("version", version.asString()),
					CustomerInfos.class);
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

	public void addRecipe( RecipeInfo ri,String email) {

		mongoTemplate.findAndModify(new Query(where("email").is(email))
				, new Update().	 
				addToSet("personalRecipes",ri)
				, CustomerInfos.class);
	}


	public void deleteRecipe(String recipeEntityId) {

		Map<String,RecipeInfo>	res=findRecipeWithId(recipeEntityId);
		String key=res.keySet().iterator().next();
		ArrayList<RecipeInfo> recipes=(ArrayList<RecipeInfo>) customerInfoRepository.findOne(key).getPersonalRecipes();
		recipes.remove(recipes.stream().filter(rec->rec.getId().equals(recipeEntityId)).findFirst().get());

		mongoTemplate.findAndModify(new Query(where("id").is(key))
				, new Update().
				set("personalRecipes", recipes)
				, CustomerInfos.class);
	}


	public void updatePhotoModerationState(String recipeEntityId, ModerationState state) {

		Map<String,RecipeInfo>	res=findRecipeWithId(recipeEntityId);
		String key=res.keySet().iterator().next();
		ArrayList<RecipeInfo> recipes=(ArrayList<RecipeInfo>) customerInfoRepository.findOne(key).getPersonalRecipes();
		RecipeInfo recIn=res.get(key);
		recIn.setPhotoModeration(state);
		recipes.remove(recipes.stream().filter(rec->rec.getId().equals(recipeEntityId)).findFirst().get());
		recipes.add(recIn);

		mongoTemplate.findAndModify(new Query(where("id").is(key)),
				new Update().
				set("personalRecipes", recipes),
				CustomerInfos.class);
	}


	public void updateDescriptionModerationState(String recipeEntityId, ModerationState state) {
		Map<String,RecipeInfo>	res=findRecipeWithId(recipeEntityId);

		String key=res.keySet().iterator().next();
		ArrayList<RecipeInfo> recipes=(ArrayList<RecipeInfo>) customerInfoRepository.findOne(key).getPersonalRecipes();
		RecipeInfo recIn=res.get(key);
		recIn.setDescriptionModeration(state);
		recipes.remove(recipes.stream().filter(rec->rec.getId().equals(recipeEntityId)).findFirst().get());
		recipes.add(recIn);

		mongoTemplate.findAndModify(new Query(where("id").is(key)),
				new Update().

				set("personalRecipes", recipes),
				CustomerInfos.class);
	}


	public void updateLikes(String recipeEntityId, String asString) {
		Map<String,RecipeInfo>	res=findRecipeWithId(recipeEntityId);

		String key=res.keySet().iterator().next();
		ArrayList<RecipeInfo> recipes=(ArrayList<RecipeInfo>) customerInfoRepository.findOne(key).getPersonalRecipes();
		RecipeInfo recIn=res.get(key);
		recIn.setLikesNumber(recIn.getLikesNumber()+1);
		recipes.remove(recipes.stream().filter(rec->rec.getId().equals(recipeEntityId)).findFirst().get());
		recipes.add(recIn);

		mongoTemplate.findAndModify(new Query(where("id").is(key)),
				new Update().
				set("personalRecipes", recipes),
				CustomerInfos.class);
	}


	public void updateDescription(String recipeEntityId, String changeId, RecipeChangeInfo ci) {

		Map<String,RecipeInfo>	res=findRecipeWithId(recipeEntityId);
		String key=res.keySet().iterator().next();
		ArrayList<RecipeInfo> recipes=(ArrayList<RecipeInfo>) customerInfoRepository.findOne(key).getPersonalRecipes();
		RecipeInfo recIn=res.get(key);
		recIn.setDescription(ci.getNewDescription());
		recipes.remove(recipes.stream().filter(rec->rec.getId().equals(recipeEntityId)).findFirst().get());
		recipes.add(recIn);

		mongoTemplate.findAndModify(new Query(where("id").is(key)),
				new Update().
				set("personalRecipes", recipes),
				CustomerInfos.class);

	}


	public void updatePhoto(String recipeEntityId, String changeId, RecipeChangeInfo ci) {

		Map<String,RecipeInfo>	res=findRecipeWithId(recipeEntityId);
		String key=res.keySet().iterator().next();
		ArrayList<RecipeInfo> recipes=(ArrayList<RecipeInfo>) customerInfoRepository.findOne(key).getPersonalRecipes();
		RecipeInfo recIn=res.get(key);
		recIn.setPhoto(ci.getNewPhoto());
		recipes.remove(recipes.stream().filter(rec->rec.getId().equals(recipeEntityId)).findFirst().get());
		recipes.add(recIn);

		mongoTemplate.findAndModify(new Query(where("id").is(key)),
				new Update().
				set("personalRecipes", recipes),
				CustomerInfos.class);

	}

	public Map<String,RecipeInfo> findRecipeWithId(String recipeEntityId)
	{ 
		Map<String,RecipeInfo> resul= new HashMap<>();
		List<CustomerInfos> customers=  customerInfoRepository.findAll();
		for(CustomerInfos c: customers)
		{
			RecipeInfo r=c.getPersonalRecipes().stream().filter(rec-> rec.getId().equals(recipeEntityId)).findFirst().orElse(null);
			if(r!= null)
			{
				resul.put(c.getId(), r);

			}

		}
		return resul;

	}

}
