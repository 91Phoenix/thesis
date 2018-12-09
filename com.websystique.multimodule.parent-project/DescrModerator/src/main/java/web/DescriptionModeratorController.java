package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import common.recipes.ModerationState;
import service.DesctiptionModeratorService;

@RestController
public class DescriptionModeratorController {
    
    @Autowired
    DesctiptionModeratorService descriptionModeratorService;
    
    @RequestMapping(value = "/DescriptionModeration")
    public ModerationState performModeration( @RequestParam(value="descrizione") String description) 
    		throws InterruptedException {	 
    	
        return descriptionModeratorService.descriptionModeation(description);
    }
	
}
