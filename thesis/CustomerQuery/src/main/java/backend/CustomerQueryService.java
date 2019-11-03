package backend;

import java.util.List;

public class CustomerQueryService {

	private CustomerInfoRepository customerInfoRepository;

	public CustomerQueryService(CustomerInfoRepository customerInfoRepository) {
		this.customerInfoRepository = customerInfoRepository;
	}

	public CustomerInfos findCustomerById(String id)
	{ 
		CustomerInfos customer=customerInfoRepository.findOne(id);

		if(customer == null)  throw new CustomerNotFoundException(id);
		else 
			return customer;
	}

	public List<CustomerInfos> findAll() {
		return customerInfoRepository.findAll();
	}

	public CustomerInfos findByEmail(String name) {

		return customerInfoRepository.findByEmail(name);

	}

	public void deleteAllTheCustomers()
	{
		List<CustomerInfos> customers= customerInfoRepository.findAll();
		customers.forEach(cus-> customerInfoRepository.delete(cus));
	}

}
