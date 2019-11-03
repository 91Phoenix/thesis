package common.customer;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("customer")
public class CustomerResponse {
  
  private String customerId;

  public CustomerResponse() {
  }

  public CustomerResponse(String customerId) {
    this.setCustomerId(customerId);
  }

public String getCustomerId() {
	return customerId;
}

public void setCustomerId(String customerId) {
	this.customerId = customerId;
}



 
}
