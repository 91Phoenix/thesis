package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import common.recipes.ModerationState;
import service.PhotoModeratorService;

@RestController
public class PhotoModeratorController {
    
    @Autowired
    PhotoModeratorService PhotoModeratorService;
   
    
    //two versions of controller used for experimental scopes
    @RequestMapping(value = "/PhotoModerationSync")
    public ModerationState performModeration(@RequestParam(value="file") MultipartFile photo,
    		@RequestParam(value="descrizione") String desc) 
    		throws InterruptedException {	 
    	
        return PhotoModeratorService.photoModeation();
    }
    
    
    @RequestMapping(value = "/PhotoModeration")
    public ModerationState performModeration(@RequestParam(value="file") MultipartFile photo) 
    		throws InterruptedException {	 
    	
        return PhotoModeratorService.photoModeation();
    }
	
}
