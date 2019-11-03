package backend;
import org.springframework.data.mongodb.repository.MongoRepository;

interface CustomerInfoRepository extends MongoRepository<CustomerInfo, String> {

}
