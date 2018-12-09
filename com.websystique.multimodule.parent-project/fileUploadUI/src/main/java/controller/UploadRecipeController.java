package controller;

import java.io.IOException;
import java.time.LocalDateTime;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import model.CreateRecipeResponse;
import service.RecipeUploadService;

@Controller
public class UploadRecipeController {

	@Autowired
	protected RecipeUploadService RecipeUploadService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public UploadRecipeController(RecipeUploadService uploadeService) {
		this.RecipeUploadService= uploadeService;
	}


	//Allows the recipe upload
	@RequestMapping(value="/recipes")
	public String uploadRecipeController(  
			@RequestParam("file") MultipartFile file,
			@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("author") String author,
			@RequestParam("CustomerEmail") String CustomerEmail,
			Model model) throws IllegalStateException, InterruptedException, ExecutionException, IOException {

		logger.info("inside uploadRecipeController");
		logger.info("***********************secondi:"+LocalDateTime.now().getSecond()+" nano secondi:"+LocalDateTime.now().getNano());
		long start= System.nanoTime();
		//this one is the eventuate version
		DeferredResult<CreateRecipeResponse> deferredrRes= new  DeferredResult<CreateRecipeResponse>();
		 RecipeUploadService.uploadRecipe(file,title, description, author,CustomerEmail)
		 	.subscribe(m->deferredrRes.setResult(m),e->deferredrRes.setErrorResult(e));
		model.addAttribute("Recipe",deferredrRes.getResult());
	//	model.addAttribute("Recipe", RecipeUploadService.uploadRecipeASyncRest(file,title, description, author,CustomerEmail).get());
	//	model.addAttribute("Recipe", RecipeUploadService.uploadRecipeSyncRest(file,title, description, author,CustomerEmail));
		System.out.println("tempo impiegato: "+((System.nanoTime()-start)/10000000));
		return "greeting";
	}
}
