package common.recipes;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity="backend.Recipe")
public abstract class RecipeEvent implements Event{
}
