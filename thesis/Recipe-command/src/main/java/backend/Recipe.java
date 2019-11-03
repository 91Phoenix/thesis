package backend;
import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;

import java.util.ArrayList;
import java.util.List;


import common.recipes.ConfirmedRecipeEvent;
import common.recipes.RecCreateEvent;
import common.recipes.RecipeDeletedEvent;
import common.recipes.RecipeState;
import common.recipes.SubmittedRecipeEvent;
import common.recipes.UpdateRecipeDescriptionEvent;
import common.recipes.UpdateRecipePhotoEvent;
import common.recipes.UpdateRecipeLikeEvent;

public class Recipe extends ReflectiveMutableCommandProcessingAggregate<Recipe, RecipeCommand> {

	private boolean deleted;
	private String title;
	private int likesNumber;
	private String description;
	private String photo;
	private String author;
	private RecipeState state;
	private String customerEmail;

	//************Create and Delete Commands********************//
	public List<Event> process(CreateRecipeCommand cmd) {
		
		return EventUtil.events(new RecCreateEvent(cmd.getTitle(), cmd.getLikesNumber(),
				cmd.getDescription(),cmd.getAuthor(),cmd.getCustomerEmail(),cmd.getSubmissionDate(),cmd.getPhoto()));
	}

	public List<Event> process(DeleteRecipeCommand cmd) {
		return EventUtil.events(new RecipeDeletedEvent());
	}

	//**************end Create and Delete Commands****************//



	//************commands to submit the Recipe************//

	public List<Event> process(ConfirmRecipeCommand cmd) {

		if(deleted) return new ArrayList<>();

		return EventUtil.events(new ConfirmedRecipeEvent(cmd.getState()));
	}

	public List<Event> process(SubmitRecipeCommand cmd) {

		if(deleted) return new ArrayList<>();

		return EventUtil.events(new SubmittedRecipeEvent(cmd.getState()));
	}

	//************end commands to submit the Recipe************//





	//*************commands to trigger the update on the Recipes**********//
	public List<Event> process(UpdateRecipeLikeCommand cmd) {

		if(deleted) return new ArrayList<>();

		return EventUtil.events(new UpdateRecipeLikeEvent());
	}

	//***********REMINDER, HERE IS A GOOD PLACE WHERE TO TRY TO IMPLEMENT THE EVENTUAL CONSISTENCY
	public List<Event> process(UpdatePhotoRecipeCommand cmd) {

		if(deleted) return new ArrayList<>();

		return EventUtil.events(new UpdateRecipePhotoEvent(cmd.getPhoto()));
	}

	public List<Event> process(UpdateDescriptionRecipeCommand cmd) {

		if(deleted) return new ArrayList<>();

		return EventUtil.events(new UpdateRecipeDescriptionEvent(cmd.getDescription()));
	}
	//*************end commands to trigger the update on the Recipes**********//



	//************** START OF THE EVENT SECTION**********************//


	//create a new Recipe aggregate
	public void apply(RecCreateEvent event) {
		deleted=false;
		likesNumber=getLikesNumber();
		description=event.getDescription();
		state=event.getState();
		photo=event.getPhoto();
		title=event.getTitle();
		author=event.getAuthor();
		setCustomerEmail(event.getCustomerEmail());
	}

	//manage the delete of a new aggregate
	public void apply(RecipeDeletedEvent event) {
		deleted = true;
	}

	//change the state of the aggregate
	public void apply(ConfirmedRecipeEvent event) {
		state=event.getState();
	}
	//change the state of the aggregate
	public void apply(SubmittedRecipeEvent event) {
		state=event.getState();
	}

	public void apply(UpdateRecipeLikeEvent event) {
		likesNumber++;
	}

	public void apply(UpdateRecipePhotoEvent event) {
		photo=event.getPhoto();
	}

	public void apply(UpdateRecipeDescriptionEvent event) {
		description=event.getDescription();
	}

	public int getLikesNumber() {
		return likesNumber;
	}

	public String getDescription() {
		return description;
	}

	public String getPhoto() {
		return photo;
	}

	public RecipeState getState() {
		return state;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

}


