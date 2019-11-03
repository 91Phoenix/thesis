package backend;
import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;

import java.util.List;

import common.customer.CustomerCreateEvent;
import common.customer.CustomerDeletedEvent;

public class Customer extends ReflectiveMutableCommandProcessingAggregate<Customer, CustomerCommand> {

	private boolean deleted;

	//************Create and Delete Commands********************//
	public List<Event> process(CreateCustomerCommand cmd) {
		
		return EventUtil.events(new CustomerCreateEvent(cmd.getEmail(),cmd.getPassword(),
				cmd.getFirstName(),cmd.getSecondName(),cmd.getState(),cmd.getUserProfiles()));
	}

	public List<Event> process(DeleteCustomerCommand cmd) {
		return EventUtil.events(new CustomerDeletedEvent());
	}

	//**************end Create and Delete Commands****************//

	//************** START OF THE EVENT SECTION**********************//


	//create a new Recipe aggregate
	public void apply(CustomerCreateEvent event) {
		setDeleted(false);
	}

	//manage the delete of a new aggregate
	public void apply(CustomerDeletedEvent event) {
		setDeleted(true);
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


}


