package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.*;
import play.db.jpa.*;
import static play.libs.Json.toJson;

import views.html.applications.*;

import models.*;

/**
 * Manage a database of Applications
 */
public class Applications extends Controller {
    
    /**
     * This result directly redirect to application home.
     */
    public static Result goHome = redirect(
        routes.Applications.list(0, "name", "asc", "")
    );
    
    /**
     * Handle default path requests, redirect to Applications list
     */
    public static Result index() {
        return goHome;
    }

    /**
     * Display the paginated list of Applications.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on application names
     */
    @Transactional(readOnly=true)
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(
            list.render(
                models.Application.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
    
    /**
     * Display the 'edit form' of a existing Applications.
     *
     * @param id Id of the Applications to edit
     */
    @Transactional(readOnly=true)
    public static Result edit(Long id) {
        Form<models.Application> applicationForm = form(models.Application.class).fill(
            models.Application.findById(id)
        );
        return ok(
            editForm.render(id, applicationForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the application to edit
     */
    @Transactional
    public static Result update(Long id) {
        Form<models.Application> applicationForm = form(models.Application.class).bindFromRequest();
        if(applicationForm.hasErrors()) {
            return badRequest(editForm.render(id, applicationForm));
        }
        applicationForm.get().update(id);
        flash("success", "Application " + applicationForm.get().name + " has been updated");
        return goHome;
    }
    
    /**
     * Display the 'new application form'.
     */
    @Transactional(readOnly=true)
    public static Result create() {
        Form<models.Application> applicationForm = form(models.Application.class);
        return ok(
            createForm.render(applicationForm)
        );
    }
    
    /**
     * Handle the 'new application form' submission 
     */
    @Transactional
    public static Result save() {
        Form<models.Application> applicationForm = form(models.Application.class).bindFromRequest();
        if(applicationForm.hasErrors()) {
            return badRequest(createForm.render(applicationForm));
        }
        applicationForm.get().save();
        flash("success", "Application " + applicationForm.get().name + " has been created");
        return goHome;
    }
    
    /**
     * Handle application deletion
     */
    @Transactional
    public static Result delete(Long id) {
        models.Application.findById(id).delete();
        flash("success", "Application has been deleted");
        return goHome;
    }
    
    /**
     * Show a existing Applications.
     *
     * @param id Id of the Applications to show
     */
    @Transactional(readOnly=true)
    public static Result show(Long id) {
        models.Application application = models.Application.findById(id);
        return ok(show.render(application));
    }	

    /**
     * Show a existing Applications.
     *
     * @param id Id of the Applications to show
     */
    @Transactional(readOnly=true)
    public static Result showJson(Long id) {
        models.Application application = models.Application.findById(id);
		
        return ok(toJson(application));
    }		
	
}
            
