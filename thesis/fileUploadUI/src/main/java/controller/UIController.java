package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import service.RecipeUploadService;

@Controller
public class UIController {

	@Autowired
	protected RecipeUploadService RecipeUploadService;
	private Logger logger = LoggerFactory.getLogger(getClass());


	public UIController(RecipeUploadService uploadeService) {
		this.RecipeUploadService= uploadeService;
	}

	//sends to the home page
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String showHomeController() {

		logger.info("inside showHomeController");
		return "home";
	}
	
	//sends to the home page
		@RequestMapping( value = "/hystrix/index")
		public String hystrixDashboard() {

			logger.info("inside hystrixDashboard");
			return "hystrix/index";
		}

	//sends to the login page
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String loginControllerView() {

		logger.info("inside loginControllerView");
		return "login";
	}

	//sends to the upload recipe page, it uses the username as modelAttribute
	@RequestMapping(value="/uploadForm")
	public String uploadRequestController(Model model) {

		logger.info("inside uploadRequestController");
		model.addAttribute("CustomerEmail",SecurityContextHolder.getContext().getAuthentication().getName());
		return "uploadForm";

	}

	//sends to the sign up page for the customers
	@RequestMapping(value="/registerCustomerView")
	public String registerCustomerControllerView() {

		logger.info("inside registerCustomerControllerView");
		return "customer/RegisterCustomer";

	}

	//sends to a page useful when we want to recover the customer detail
	@RequestMapping(value="/getCustomerPage")
	public String getCustomerPageController()
	{
		logger.info("inside getCustomerPageController");
		return "customer/getCustomerPage";
	}


}
