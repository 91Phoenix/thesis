package backend;
import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;

import java.util.List;

import common.recipes.DescriptionModeratedEvent;


public class DescriptionModerator extends ReflectiveMutableCommandProcessingAggregate<DescriptionModerator, DescriptionModeratorCommand> {

	//************Create and Delete Commands********************//
	public List<Event> process(DescriptionModeratedCommand cmd) {
		
		return EventUtil.events(new DescriptionModeratedEvent(cmd.getState(),cmd.getRecipeEntityId()));
	}

	public void apply(DescriptionModeratedEvent event) {
		
	}


}


