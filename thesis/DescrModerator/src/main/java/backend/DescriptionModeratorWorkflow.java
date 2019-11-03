package backend;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.recipes.RecCreateEvent;
import io.eventuate.EventHandlerContext;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;


@EventSubscriber(id = "descriptionModeratorEventHandlers")
public class DescriptionModeratorWorkflow {
	
	
	private DescriptionModeratorService descriptionModeratorService;
	 private Logger logger = LoggerFactory.getLogger(getClass());
	

	public DescriptionModeratorWorkflow(DescriptionModeratorService photoModeratorService) {
		super();
		this.descriptionModeratorService = photoModeratorService;
	}

	@EventHandlerMethod
	public void photoModeration(EventHandlerContext<RecCreateEvent> de) throws InterruptedException {
		
		logger.info("**************** recipe version=" + de.getEntityId() + ", " + de.getEventId());
		descriptionModeratorService.moderateRecipe(de.getEntityId());
	
	}

}
