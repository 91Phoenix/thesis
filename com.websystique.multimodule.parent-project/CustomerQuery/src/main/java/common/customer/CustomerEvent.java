package common.customer;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity="backend.Customer")
public abstract class CustomerEvent implements Event{
}
