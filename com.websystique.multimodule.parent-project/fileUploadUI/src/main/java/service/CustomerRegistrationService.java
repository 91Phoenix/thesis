package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import model.CustomerResponse;
import rx.Observable;


@Service
public class CustomerRegistrationService {


	@Autowired
	protected RestTemplate restTemplate;
	protected String serviceUrl;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public CustomerRegistrationService(String customercommandside) {
		this.serviceUrl = customercommandside.startsWith("http") ? customercommandside
				: "http://" + customercommandside;
	}

	//invoke the service responsible of the customer registration 
	@HystrixCommand(fallbackMethod="getDefaultResponse")
	public Observable<CustomerResponse> registerCustomer(String email, String password, String firstName, String secondName) {
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("email", email);
		parts.add("password", password);
		parts.add("firstName", firstName);
		parts.add("secondName",secondName );
		logger.info(serviceUrl + "customer");
		return Observable.create(aSubscriber-> {
			aSubscriber.onNext(restTemplate.postForObject(serviceUrl + "customer",parts,CustomerResponse.class));
				aSubscriber.onCompleted();});
	}
		
	public CustomerResponse getDefaultResponse(String email, String password, String firstName, String secondName)
	{
		return new CustomerResponse("please "+firstName+" "+secondName+" try later the servers are temporaly down");
	}

}
