package hello;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service("customUserDetailService")
public class CustomUserDetailService implements UserDetailsService {


	private String customerQuerySideUrl ;//;userDetailsService

	@Autowired
	protected RestTemplate restTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public CustomUserDetailService(String service) {
		this.customerQuerySideUrl= service.startsWith("http") ? service
				: "http://" + service;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

		Map<String, Object> parts = new HashMap<String, Object>();
		parts.put("name", name);
		logger.info(customerQuerySideUrl + "CustomerQuery/ByName/");
		CustomerInfo   result= restTemplate.getForObject(customerQuerySideUrl + "CustomerQuery/ByName/{name}",CustomerInfo.class,parts);

		if(result==null){
			System.out.println("customer not found");
			throw new UsernameNotFoundException(name);
		}
		return new org.springframework.security.core.userdetails.User(result.getEmail(), result.getPassword(),
				getGantedAuthorities(result));
	}

	private Collection<? extends GrantedAuthority> getGantedAuthorities(CustomerInfo result) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		result.getUserProfiles().forEach(profile -> authorities.
				add(new SimpleGrantedAuthority("ROLE_"+profile.getType())));
		return authorities;
	}

}
