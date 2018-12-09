package controller;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import model.CustomerResponse;
import service.CustomerRegistrationService;

@Controller
public class RegisterCustomerController {

	@Autowired
	protected CustomerRegistrationService registrationservice;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public RegisterCustomerController(CustomerRegistrationService customerUploadService) {
		this.registrationservice=customerUploadService;
	}

	//responsible to register a customer through the customer-Command- microservice 
	@RequestMapping(value="/registerCustomer")
	public String registerCustomer(  
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("firstName") String firstName,
			@RequestParam("secondName") String secondName,
			@RequestParam("confirm")String confirm,
			Model model) throws InterruptedException, ExecutionException
	{
		logger.info("inside registerCustomer Controller");
		if(password.equals(confirm))
		{
			DeferredResult<CustomerResponse> deferredrRes= new  DeferredResult<CustomerResponse>();
			registrationservice.registerCustomer(email,password, firstName, secondName)
				.subscribe(m->deferredrRes.setResult(m),e->deferredrRes.setErrorResult(e));
			model.addAttribute("Customer",deferredrRes.getResult());
		}
		else model.addAttribute("Customer",new CustomerResponse("failure"));
		return "customer/CustomerRegistered";
	}	

}
