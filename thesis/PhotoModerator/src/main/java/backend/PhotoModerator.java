package backend;
import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;

import java.util.List;

import common.recipes.PhotoModeratedEvent;


public class PhotoModerator extends ReflectiveMutableCommandProcessingAggregate<PhotoModerator, PhotoModeratorCommand> {

	//************Create and Delete Commands********************//
	public List<Event> process(PhotoModeratedCommand cmd) {
		
		return EventUtil.events(new PhotoModeratedEvent(cmd.getState(),cmd.getRecipeEntityId()));
	}

	public void apply(PhotoModeratedEvent event) {
		
	}


}


