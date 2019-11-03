package backend;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.recipes.RecipeChangeInfo;
import common.recipes.DescriptionModeratedEvent;
import common.recipes.PhotoModeratedEvent;
import common.recipes.RecCreateEvent;
import common.recipes.RecipeDeletedEvent;
import common.recipes.UpdateRecipeDescriptionEvent;
import common.recipes.UpdateRecipeLikeEvent;
import common.recipes.UpdateRecipePhotoEvent;


@EventSubscriber(id="querySideEventHandlers")
public class RecipeQueryWorkflow {
  private Logger logger = LoggerFactory.getLogger(getClass());

  private RecipeInfoUpdateService recipeInfoUpdateService;

  public RecipeQueryWorkflow(RecipeInfoUpdateService recipeInfoUpdateService) {
    this.recipeInfoUpdateService = recipeInfoUpdateService;
  }
 
  
  @EventHandlerMethod
  public void create(DispatchedEvent<RecCreateEvent> de) {
	  
	RecCreateEvent event = de.getEvent();
    logger.info("**************** recipe version=" + de.getEntityId() + ", " + de.getEventId());

    recipeInfoUpdateService.create(de.getEntityId(), event.getTitle(),
    		event.getLikesNumber(), event.getDescription(), event.getAuthor(),event.getCustomerEmail(),
    		event.getSubmissionDate(), event.getPhoto(), de.getEventId());
  }
  
  @EventHandlerMethod
  public void  photoModeration(DispatchedEvent<PhotoModeratedEvent> de) {
	  
   logger.info("photoModerator result: "+de.getEvent().getState());
    recipeInfoUpdateService.updatePhotoModerationState(de.getEvent().getRecipeEntityId(),de.getEvent().getState());
  }
  @EventHandlerMethod
  public void descriptionModeration(DispatchedEvent<DescriptionModeratedEvent> de) {
	  
	 logger.info("descriptionModerator result: "+de.getEvent().getState());
    recipeInfoUpdateService.updateDescriptionModerationState(de.getEvent().getRecipeEntityId(),de.getEvent().getState());
  }

  @EventHandlerMethod
  public void delete(DispatchedEvent<RecipeDeletedEvent> de) {
    String id = de.getEntityId();
    recipeInfoUpdateService.delete(id);
  }
  
  @EventHandlerMethod
  public void updateNumberofLikes(DispatchedEvent<UpdateRecipeLikeEvent> de) {
    recipeInfoUpdateService.updateLikes(de.getEntityId(), de.getEventId().asString());
  }
  
  
  @EventHandlerMethod
  public void saveDescriptionChange(DispatchedEvent<UpdateRecipeDescriptionEvent> de) {
	  
	UpdateRecipeDescriptionEvent urde=de.getEvent();
    String changeId = de.getEventId().asString();
    RecipeChangeInfo ci = new RecipeChangeInfo(changeId, "Description", urde.getDescription(), null);
    String accountId = de.getEntityId();
    logger.info("**************** account version=" + accountId + ", " + de.getEventId().asString());

    recipeInfoUpdateService.updateDescription(accountId, changeId, ci);
  }
  
  @EventHandlerMethod
  public void savePhotoChange(DispatchedEvent<UpdateRecipePhotoEvent> de) {
	  
    UpdateRecipePhotoEvent urde=de.getEvent();
    String changeId = de.getEventId().asString();
    RecipeChangeInfo ci = new RecipeChangeInfo(changeId, "Photo", null,  urde.getPhoto());
    String accountId = de.getEntityId();
   // logger.info("**************** recipe version=" + recipeId + ", " + de.getEventId().asString());

    recipeInfoUpdateService.updatePhoto(accountId, changeId, ci);
  }

}
