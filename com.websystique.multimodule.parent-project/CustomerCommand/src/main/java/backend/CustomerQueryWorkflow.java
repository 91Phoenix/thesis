package backend;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.customer.CustomerCreateEvent;
import common.customer.CustomerDeletedEvent;


@EventSubscriber(id="CustomerqueryEventHandlers")
public class CustomerQueryWorkflow {
  private Logger logger = LoggerFactory.getLogger(getClass());

  private CustomerInfoUpdateService customerInfoUpdateService;

  public CustomerQueryWorkflow(CustomerInfoUpdateService customerInfoUpdateService) {
    this.customerInfoUpdateService = customerInfoUpdateService;
  }


  @EventHandlerMethod
  public void createCustomer(DispatchedEvent<CustomerCreateEvent> dec) {
	  CustomerCreateEvent event = dec.getEvent();
 
    logger.info("**************** customer version=" + dec.getEntityId() + ", " + dec.getEventId());
    
    logger.info(dec.getEntityId(),event.getEmail(),event.getPassword()
    		,event.getFirstName(),event.getSecondName(),event.getState(),
    		event.getUserProfiles(),dec.getEventId());
    
    customerInfoUpdateService.create(dec.getEntityId(),event.getEmail(),event.getPassword()
    		,event.getFirstName(),event.getSecondName(),event.getState(),
    		event.getUserProfiles(),dec.getEventId());
  }
  

  @EventHandlerMethod
  public void deleteCustomer(DispatchedEvent<CustomerDeletedEvent> de) {
    String id = de.getEntityId();
    customerInfoUpdateService.delete(id);
  }
  
  /*
  @EventHandlerMethod
  public void updateNumberofLikes(DispatchedEvent<UpdateRecipeLikeEvent> de) {
    String id = de.getEntityId();
    customerInfoUpdateService.updateLikes(de.getEntityId(), de.getEventId().asString());
  }
 */
}
