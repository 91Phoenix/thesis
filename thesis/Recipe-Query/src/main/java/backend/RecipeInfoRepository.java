package backend;
import org.springframework.data.mongodb.repository.MongoRepository;

interface RecipeInfoRepository extends MongoRepository<RecipeInfo, String> {

	RecipeInfo findByTitle(String title);
}
