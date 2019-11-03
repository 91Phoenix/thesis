package backend;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.recipes.RecCreateEvent;
import io.eventuate.EventHandlerContext;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;


@EventSubscriber(id = "photoModeratorEventHandlers")
public class PhotoModeratorWorkflow {
	
	
	private PhotoModeratorService photoModeratorService;
	 private Logger logger = LoggerFactory.getLogger(getClass());
	

	public PhotoModeratorWorkflow(PhotoModeratorService photoModeratorService) {
		super();
		this.photoModeratorService = photoModeratorService;
	}

	@EventHandlerMethod
	public void photoModeration(EventHandlerContext<RecCreateEvent> de) throws InterruptedException {
		
		logger.info("**************** recipe version=" + de.getEntityId() + ", " + de.getEventId());
	
		photoModeratorService.moderateRecipe(de.getEntityId());
	
	}

}
