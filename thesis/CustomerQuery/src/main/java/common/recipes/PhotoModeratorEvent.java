package common.recipes;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity="backend.PhotoModerator")
public abstract class PhotoModeratorEvent implements Event{
}
