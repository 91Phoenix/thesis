package backend;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.customer.CustomerJustCreatedEvent;
import common.customer.CustomerDeletedEvent;
import common.recipes.DescriptionModeratedEvent;
import common.recipes.ModerationState;
import common.recipes.PhotoModeratedEvent;
import common.recipes.RecCreateEvent;
import common.recipes.RecipeChangeInfo;
import common.recipes.RecipeDeletedEvent;
import common.recipes.UpdateRecipeDescriptionEvent;
import common.recipes.UpdateRecipeLikeEvent;
import common.recipes.UpdateRecipePhotoEvent;


@EventSubscriber(id="CustomerqueryEventHandlers")
public class CustomerQueryWorkflow {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private CustomerInfoUpdateService customerInfoUpdateService;

	public CustomerQueryWorkflow(CustomerInfoUpdateService customerInfoUpdateService) {
		this.customerInfoUpdateService = customerInfoUpdateService;
	}


	@EventHandlerMethod
	public void createCustomer(DispatchedEvent<CustomerJustCreatedEvent> dec) {
		CustomerJustCreatedEvent event = dec.getEvent();

		logger.info("**************** customer version=" + dec.getEntityId() + ", " + dec.getEventId());

		customerInfoUpdateService.create(dec.getEntityId(),event.getEmail(),event.getPassword()
				,event.getFirstName(),event.getSecondName(),event.getState(),
				event.getUserProfiles(),event.getRecipes(),dec.getEventId());
	}

	@EventHandlerMethod
	public void recipeCreated(DispatchedEvent<RecCreateEvent> dec) {
		RecCreateEvent cmd = dec.getEvent();

		logger.info("**************** recipe version=" + dec.getEntityId() + ", " + dec.getEventId());


		RecipeInfo ri= new RecipeInfo(cmd.getTitle(), cmd.getLikesNumber(),
				cmd.getDescription(),cmd.getAuthor(),cmd.getCustomerEmail(),
				cmd.getSubmissionDate(),cmd.getPhoto(),ModerationState.NotModerated,ModerationState.NotModerated);
		ri.setId(dec.getEntityId());
		customerInfoUpdateService.addRecipe(ri,cmd.getCustomerEmail());
	}

	@EventHandlerMethod
	public void deleteCustomer(DispatchedEvent<CustomerDeletedEvent> de) {
		String id = de.getEntityId();
		customerInfoUpdateService.delete(id);
	}

	@EventHandlerMethod
	public void delete(DispatchedEvent<RecipeDeletedEvent> de) {
		String id = de.getEntityId();
		customerInfoUpdateService.deleteRecipe(id);
	}


	@EventHandlerMethod
	public void  photoModeration(DispatchedEvent<PhotoModeratedEvent> de) {

		logger.info("photoModerator result: "+de.getEvent().getState());
	customerInfoUpdateService.updatePhotoModerationState(de.getEvent().getRecipeEntityId(),de.getEvent().getState());
	}
	@EventHandlerMethod
	public void descriptionModeration(DispatchedEvent<DescriptionModeratedEvent> de) {

		logger.info("descriptionModerator result: "+de.getEvent().getState());
		customerInfoUpdateService.updateDescriptionModerationState(de.getEvent().getRecipeEntityId(),de.getEvent().getState());
	}

	@EventHandlerMethod
	public void updateNumberofLikes(DispatchedEvent<UpdateRecipeLikeEvent> de) {

	customerInfoUpdateService.updateLikes(de.getEntityId(), de.getEventId().asString());
	}

	@EventHandlerMethod
	public void saveDescriptionChange(DispatchedEvent<UpdateRecipeDescriptionEvent> de) {

		UpdateRecipeDescriptionEvent urde=de.getEvent();
		String changeId = de.getEventId().asString();

		RecipeChangeInfo ci = new RecipeChangeInfo(changeId, "Description", urde.getDescription(), null);
		String accountId = de.getEntityId();
		logger.info("**************** account version=" + accountId + ", " + de.getEventId().asString());

		customerInfoUpdateService.updateDescription(accountId, changeId, ci);
	}

	@EventHandlerMethod
	public void savePhotoChange(DispatchedEvent<UpdateRecipePhotoEvent> de) {

		UpdateRecipePhotoEvent urde=de.getEvent();
		String changeId = de.getEventId().asString();

		RecipeChangeInfo ci = new RecipeChangeInfo(changeId, "Photo", null,  urde.getPhoto());
		String accountId = de.getEntityId();
		logger.info("**************** account version=" + accountId + ", " + de.getEventId().asString());

		customerInfoUpdateService.updatePhoto(accountId, changeId, ci);
	}

}
