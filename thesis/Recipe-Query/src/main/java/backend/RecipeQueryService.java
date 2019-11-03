package backend;

import java.util.List;

public class RecipeQueryService {

	private RecipeInfoRepository recipeInfoRepository;

	public RecipeQueryService(RecipeInfoRepository recipeInfoRepository) {
		this.recipeInfoRepository = recipeInfoRepository;
	}

	public RecipeInfo findRecipeByTitle(String recipeTitle) {
		RecipeInfo recipe = recipeInfoRepository.findByTitle(recipeTitle);

		if (recipe == null)
			throw new RecipeNotFoundException(recipeTitle);
		else
			return recipe;
	}


	public RecipeInfo findRecipeById(String id) {
		RecipeInfo recipe = recipeInfoRepository.findOne(id);

		if (recipe == null)
			throw new RecipeNotFoundException(id);
		else
			return recipe;
	}

	public List<RecipeInfo> findAll() {
		return recipeInfoRepository.findAll();
	}

	public void deleteEverything()
	{
		List<RecipeInfo> recipes= recipeInfoRepository.findAll();
		recipes.forEach(rec-> recipeInfoRepository.delete(recipes));
	}

}
