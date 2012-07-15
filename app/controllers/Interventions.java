package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.*;
import play.db.jpa.*;

import views.html.interventions.*;

import models.*;

/**
 * Manage a database of Interventions
 */
public class Interventions extends Controller {
    
    /**
     * This result directly redirect to application home.
     */
    public static Result goHome = redirect(
        routes.Interventions.list(0, "name", "asc", "")
    );
    
    /**
     * Handle default path requests, redirect to Interventions list
     */
    public static Result index() {
        return goHome;
    }

    /**
     * Display the paginated list of Interventions.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on intervention names
     */
    @Transactional(readOnly=true)
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(
            list.render(
                Intervention.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
    
    /**
     * Display the 'edit form' of a existing Interventions.
     *
     * @param id Id of the Interventions to edit
     */
    @Transactional(readOnly=true)
    public static Result edit(Long id) {
        Form<Intervention> interventionForm = form(Intervention.class).fill(
            Intervention.findById(id)
        );
        return ok(
            editForm.render(id, interventionForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the intervention to edit
     */
    @Transactional
    public static Result update(Long id) {
        Form<Intervention> interventionForm = form(Intervention.class).bindFromRequest();
        if(interventionForm.hasErrors()) {
            return badRequest(editForm.render(id, interventionForm));
        }
        interventionForm.get().update(id);
        flash("success", "Intervention " + interventionForm.get().name + " has been updated");
        return goHome;
    }
    
    /**
     * Display the 'new intervention form'.
     */
    @Transactional(readOnly=true)
    public static Result create() {
        Form<Intervention> interventionForm = form(Intervention.class);
        return ok(
            createForm.render(interventionForm)
        );
    }
    
    /**
     * Handle the 'new intervention form' submission 
     */
    @Transactional
    public static Result save() {
        Form<Intervention> interventionForm = form(Intervention.class).bindFromRequest();
        if(interventionForm.hasErrors()) {
            return badRequest(createForm.render(interventionForm));
        }
        interventionForm.get().save();
        flash("success", "Intervention " + interventionForm.get().name + " has been created");
        return goHome;
    }
    
    /**
     * Handle intervention deletion
     */
    @Transactional
    public static Result delete(Long id) {
        Intervention.findById(id).delete();
        flash("success", "Intervention has been deleted");
        return goHome;
    }
    

}
            
