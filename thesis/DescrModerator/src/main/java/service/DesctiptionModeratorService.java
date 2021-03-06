package service;


import java.util.Random;

import org.springframework.stereotype.Service;

import common.recipes.ModerationState;


@Service
public class DesctiptionModeratorService {
	
private static final Random random= new Random();
	
	public ModerationState descriptionModeation(String descriprion) throws InterruptedException {
		
		int delay=500+random.nextInt(2500);
		Thread.sleep(delay);
		System.out.println(delay);
		return delay>1.5? ModerationState.NotAllowed :ModerationState.Allowed;
    
}
}