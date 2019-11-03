package controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import service.CustomerGetService;
import model.CustomerInfos;
import model.RecipeInfo;

@Controller
public class GetCustomerController {

	@Autowired
	protected CustomerGetService customerGetService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public GetCustomerController(CustomerGetService customerGetService) {
		this.customerGetService= customerGetService;
	}


	//recover Customer by his Email
	@RequestMapping(value="/customerByName")
	public String findCustomerByTitle(@RequestParam("name") String name, Model model) 
			throws InterruptedException, ExecutionException {

		logger.info("inside findCustomerByTitle Controller");
		DeferredResult<CustomerInfos> deferredrRes= new  DeferredResult<CustomerInfos>();
		customerGetService.getCustomerByName(name).subscribe(m->deferredrRes.setResult(m),e->deferredrRes.setErrorResult(e));
		model.addAttribute("Customer",deferredrRes.getResult());
		return "/customer/showCustomer";
	}

	//find the Recipes submitted by a specific customer
	@RequestMapping(value="/lookYourRecipes")
	public String lookYourRecipes(Model model) throws InterruptedException, ExecutionException {

		logger.info("inside lookYourRecipes Controller");
		DeferredResult<List<RecipeInfo>> deferredrRes= new  DeferredResult<List<RecipeInfo>>();
		customerGetService.lookYourRecipes(SecurityContextHolder.getContext().getAuthentication().getName()).
			subscribe(m->deferredrRes.setResult(m),e->deferredrRes.setErrorResult(e));
		model.addAttribute("Recipes",deferredrRes.getResult());

		return "/customer/showCustomerRecipes";
	}

}
