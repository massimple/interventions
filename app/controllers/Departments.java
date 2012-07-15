package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.*;
import play.db.jpa.*;
import static play.libs.Json.toJson;

import views.html.departments.*;

import models.*;

/**
 * Manage a database of Departments
 */
public class Departments extends Controller {
    
    /**
     * This result directly redirect to application home.
     */
    public static Result goHome = redirect(
        routes.Departments.list(0, "name", "asc", "")
    );
    
    /**
     * Handle default path requests, redirect to Departments list
     */
    public static Result index() {
        return goHome;
    }

    /**
     * Display the paginated list of Departments.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on department names
     */
    @Transactional(readOnly=true)
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(
            list.render(
                Department.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
    
    /**
     * Display the 'edit form' of a existing Departments.
     *
     * @param id Id of the Departments to edit
     */
    @Transactional(readOnly=true)
    public static Result edit(Long id) {
        Form<Department> departmentForm = form(Department.class).fill(
            Department.findById(id)
        );
        return ok(
            editForm.render(id, departmentForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the department to edit
     */
    @Transactional
    public static Result update(Long id) {
        Form<Department> departmentForm = form(Department.class).bindFromRequest();
        if(departmentForm.hasErrors()) {
            return badRequest(editForm.render(id, departmentForm));
        }
        departmentForm.get().update(id);
        flash("success", "Department " + departmentForm.get().name + " has been updated");
        return goHome;
    }
    
    /**
     * Display the 'new department form'.
     */
    @Transactional(readOnly=true)
    public static Result create() {
        Form<Department> departmentForm = form(Department.class);
        return ok(
            createForm.render(departmentForm)
        );
    }
    
    /**
     * Handle the 'new department form' submission 
     */
    @Transactional
    public static Result save() {
        Form<Department> departmentForm = form(Department.class).bindFromRequest();
        if(departmentForm.hasErrors()) {
            return badRequest(createForm.render(departmentForm));
        }
        departmentForm.get().save();
        flash("success", "Department " + departmentForm.get().name + " has been created");
        return goHome;
    }
    
    /**
     * Handle department deletion
     */
    @Transactional
    public static Result delete(Long id) {
        Department.findById(id).delete();
        flash("success", "Department has been deleted");
        return goHome;
    }
    
    /**
     * Show a existing Departments.
     *
     * @param id Id of the Departments to show
     */
    @Transactional(readOnly=true)
    public static Result show(Long id) {
        Department department = Department.findById(id);
        return ok(show.render(department));
    }	

    /**
     * Show a existing Departments.
     *
     * @param id Id of the Departments to show
     */
    @Transactional(readOnly=true)
    public static Result showJson(Long id) {
        Department department = Department.findById(id);
		
        return ok(toJson(department));
    }		
	
}
            
