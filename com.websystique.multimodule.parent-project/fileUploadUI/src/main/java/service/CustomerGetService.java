package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import model.CustomerInfos;
import model.GetCustomerRecipesResponse;
import model.RecipeInfo;
import rx.Observable;

@Service
public class CustomerGetService {


	private String customerQuerySideUrl ;
	@Autowired
	protected RestTemplate restTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());


	public CustomerGetService(String service) {

		this.customerQuerySideUrl= service.startsWith("http") ? service
				: "http://" + service;
	}

	//obtain the customer using the id
	@HystrixCommand(fallbackMethod="getDefaultCustomer")
	public Observable<CustomerInfos> getCustomerById(String id) {

		Map<String, Object> parts = new HashMap<String, Object>();
		parts.put("id", id);
		logger.info(customerQuerySideUrl + "CustomerQuery/ById/");
		return Observable.create(aSubscriber-> {
			aSubscriber.onNext(restTemplate.getForObject(customerQuerySideUrl + "CustomerQuery/ById/{id}",CustomerInfos.class,parts));
			aSubscriber.onCompleted();});
	}

	public CustomerInfos getDefaultCustomer(String id){
		logger.info("inside fallback method");
		return new CustomerInfos();
	}

	//below you can see the "nice version" of the method. It is not available because hystrix doesn't support java 8
	//therefore if we want to realaize an async and reliable service we need to switch to Observable.
	/*	//obtain the customer using the Email
	@HystrixCommand(fallbackMethod="getDefaultCustomer")
	@Async
	public CompletableFuture<CustomerInfos> getCustomerByName(String name) {
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("name", name);
		logger.info(customerQuerySideUrl + "CustomerQuery/ByName/");
		return CompletableFuture.completedFuture(restTemplate.postForObject(customerQuerySideUrl + "CustomerQuery/ByName/",parts,CustomerInfos.class));

	}*/

	//obtain the customer using the Email
	@HystrixCommand(fallbackMethod="getDefaultCustomer")
	public Observable<CustomerInfos> getCustomerByName(String name) {
		Map<String, Object> parts = new HashMap<String, Object>();
		parts.put("name", name);
		logger.info(customerQuerySideUrl + "CustomerQuery/ByName/");
		return Observable.create(aSubscriber-> {
			aSubscriber.onNext(restTemplate.getForObject(customerQuerySideUrl + "CustomerQuery/ByName/{name}",CustomerInfos.class,parts));
			aSubscriber.onCompleted();});
	}

	//retrieve the customer recipes
	@HystrixCommand(fallbackMethod="getEmptyRecipesList")
	public Observable<List<RecipeInfo>> lookYourRecipes(String email) {

	Map<String, Object> parts = new HashMap<String, Object>();
		parts.put("email", email);
		logger.info(customerQuerySideUrl + "CustomerQuery/lookYourRecipes/");
		return Observable.create(aSubscriber-> {
			aSubscriber.onNext(restTemplate.getForObject(customerQuerySideUrl + 
					"CustomerQuery/lookYourRecipes/{email}",GetCustomerRecipesResponse.class,parts).getRecipes());
			aSubscriber.onCompleted();});

	}

	public  List<RecipeInfo> getEmptyRecipesList(String email)
	{
		logger.info("unexpected error during the recover of the recipes for: "+ email);
		return new ArrayList<RecipeInfo>();
	}

}
