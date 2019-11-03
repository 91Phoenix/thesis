package backend;
import org.springframework.data.mongodb.repository.MongoRepository;

interface CustomerInfoRepository extends MongoRepository<CustomerInfos, String> {
	
	public CustomerInfos findByEmail(String email);

}
