package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import play.db.jpa.*;

/**
 * Application entity managed by JPA
 */
@Entity 
@SequenceGenerator(name = "application_seq", sequenceName = "application_seq")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_seq")
    public Long id;
    
    @Constraints.Required
    public String name;
    
    /**
     * Find an Application by id.
     */
    public static Application findById(Long id) {
        return JPA.em().find(Application.class, id);
    }
    
    /**
     * Update this application.
     */
    public void update(Long id) {
        this.id = id;
        JPA.em().merge(this);
    }
    
    /**
     * Insert this new application.
     */
    public void save() {
        this.id = id;
        JPA.em().persist(this);
    }
    
    /**
     * Delete this application.
     */
    public void delete() {
        JPA.em().remove(this);
    }
     
    /**
     * Return a page of application
     *
     * @param page Page to display
     * @param pageSize Number of applications per page
     * @param sortBy Application property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page page(int page, int pageSize, String sortBy, String order, String filter) {
        if(page < 1) page = 1;
        Long total = (Long)JPA.em()
            .createQuery("select count(c) from Application c where lower(c.name) like ?")
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .getSingleResult();
        List<Application> data = JPA.em()
            .createQuery("from Application c where lower(c.name) like ? order by c." + sortBy + " " + order)
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
        return new Page(data, total, page, pageSize);
    }
    
    /**
     * Used to represent a applications page.
     */
    public static class Page {
        
        private final int pageSize;
        private final long totalRowCount;
        private final int pageIndex;
        private final List<Application> list;
        
        public Page(List<Application> data, long total, int page, int pageSize) {
            this.list = data;
            this.totalRowCount = total;
            this.pageIndex = page;
            this.pageSize = pageSize;
        }
        
        public long getTotalRowCount() {
            return totalRowCount;
        }
        
        public int getPageIndex() {
            return pageIndex;
        }
        
        public List<Application> getList() {
            return list;
        }
        
        public boolean hasPrev() {
            return pageIndex > 1;
        }
        
        public boolean hasNext() {
            return (totalRowCount/pageSize) >= pageIndex;
        }
        
        public String getDisplayXtoYofZ() {
            int start = ((pageIndex - 1) * pageSize + 1);
            int end = start + Math.min(pageSize, list.size()) - 1;
            return start + " to " + end + " of " + totalRowCount;
        }
        
    }
    
    public static Map<String,String> options() {
        List<Application> apps = JPA.em().createQuery("from Application order by name").getResultList();
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Application app: apps) {
            options.put(app.id.toString(), app.name);
        }
        return options;
    }	
	
}

