package common.recipes;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity="backend.DescriptionModerator")
public abstract class DescriptionModeratorEvent implements Event{
}
