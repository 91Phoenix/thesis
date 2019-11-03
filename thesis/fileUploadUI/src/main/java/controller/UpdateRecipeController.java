package controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import model.GetRecipeResponse;
import model.ModerationState;
import model.RecipeInfo;
import model.RecipeState;
import service.RecipeUpdateService;
import service.CustomerGetService;
import service.RecipeGetService;


@Controller
public class UpdateRecipeController {


	@Autowired
	protected RecipeUpdateService RecipeUpdateService;
	@Autowired
	protected RecipeGetService RecipeGetService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected CustomerGetService customerGetService;

	public UpdateRecipeController(RecipeUpdateService RecipeUpdateService,RecipeGetService RecipeGetService) {
		this.RecipeUpdateService= RecipeUpdateService;
		this.RecipeGetService=RecipeGetService;
	}

	//Invokes the service which update a specific recipe
	@RequestMapping(value="/updateRecipe")
	public String updateRecipeController(  
			@RequestParam("file") MultipartFile file,
			@RequestParam("description") String description,
			@RequestParam("id") String id,
			Model model) throws IllegalStateException, IOException, InterruptedException, ExecutionException {

		logger.info("inside updateRecipeController");
		DeferredResult<GetRecipeResponse> deferredrRes= new  DeferredResult<GetRecipeResponse>();
		RecipeUpdateService.updateRecipe(file,id, description)
		 	.subscribe(m->deferredrRes.setResult(m),e->deferredrRes.setErrorResult(e));
		model.addAttribute("Recipe",deferredrRes.getResult());
		return "foundRecipe";
	}

	//shows the update performed
	@RequestMapping(value="/updateThisRecipe")
	public String showUploadRecipeController(  
			@RequestParam("id") String id,
			@RequestParam("photo") String photo,
			@RequestParam("title") String title,
			@RequestParam("likes") int likes,
			@RequestParam("description") String description,
			@RequestParam("author") String author,
			@RequestParam("state") RecipeState state,
			@RequestParam("PhotoModeration") String PhotoModeration,
			@RequestParam("descriptionModeration") String descriptionModeration,
			Model model) throws IllegalStateException, IOException {

		logger.info("inside showUploadRecipeController");
		model.addAttribute("Recipe", new GetRecipeResponse(id, title, likes,
					description, author, state, photo,ModerationState.valueOf(PhotoModeration),
					ModerationState.valueOf(descriptionModeration)));
		return "updateRecipe";
	}

	//invokes the service which updates the number of likes
	@RequestMapping(value="/like")
	public String giveAlikeToRecipeWithIdController(@RequestParam("id") String id,
			Model model) throws InterruptedException, ExecutionException {

		logger.info("inside giveAlikeToRecipeWithIdController");
		DeferredResult<GetRecipeResponse> deferredrRes= new  DeferredResult<GetRecipeResponse>();
		RecipeUpdateService.addLikeToRecipeWithId(id).subscribe(m->deferredrRes.setResult(m),e->deferredrRes.setErrorResult(e));
		model.addAttribute("Recipe",deferredrRes.getResult() );
		return "foundRecipe";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/deleteRecipe")
	public String deleteRecipeWithIdController(@RequestParam("id") String id,
			Model model) {

		logger.info("inside delete recipe");
		RecipeUpdateService.deleteRecipeWithId(id);
		DeferredResult<List<RecipeInfo>> deferredrRes= new  DeferredResult<List<RecipeInfo>>();
			customerGetService.lookYourRecipes(SecurityContextHolder.getContext().getAuthentication().getName()).
				subscribe(m->deferredrRes.setResult(m),e->deferredrRes.setErrorResult(e));
			model.addAttribute("Recipes",
					((List<RecipeInfo>)deferredrRes.getResult())
					.stream()
					.filter(rec->!rec.getId().equals(id))
					.collect(Collectors.toList()));
		return "/customer/showCustomerRecipes";
	}
}
