package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import play.db.jpa.*;

/**
 * Intervention entity managed by JPA
 */
@Entity 
@SequenceGenerator(name = "intervention_seq", sequenceName = "intervention_seq")
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "intervention_seq")
    public Long id;
    
    @Constraints.Required
    public String name;
    
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date introduced;
    
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date scheduled;
	
    @ManyToOne(cascade = CascadeType.MERGE)
    public Application application;	
    
    /**
     * Find an Intervention by id.
     */
    public static Intervention findById(Long id) {
        return JPA.em().find(Intervention.class, id);
    }
    
    /**
     * Update this intervention.
     */
    public void update(Long id) {
        if(this.application.id == null) {
            this.application = null;
        } else {
            this.application = Application.findById(application.id);
        }	
        this.id = id;
        JPA.em().merge(this);
    }
    
    /**
     * Insert this new intervention.
     */
    public void save() {
        if(this.application.id == null) {
            this.application = null;
        } else {
            this.application = Application.findById(application.id);
        }		
        this.id = id;
        JPA.em().persist(this);
    }
    
    /**
     * Delete this intervention.
     */
    public void delete() {
        JPA.em().remove(this);
    }
     
    /**
     * Return a page of intervention
     *
     * @param page Page to display
     * @param pageSize Number of interventions per page
     * @param sortBy Intervention property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page page(int page, int pageSize, String sortBy, String order, String filter) {
        if(page < 1) page = 1;
        Long total = (Long)JPA.em()
            .createQuery("select count(c) from Intervention c where lower(c.name) like ?")
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .getSingleResult();
        List<Intervention> data = JPA.em()
            .createQuery("from Intervention c where lower(c.name) like ? order by c." + sortBy + " " + order)
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
        return new Page(data, total, page, pageSize);
    }
    
    /**
     * Used to represent a interventions page.
     */
    public static class Page {
        
        private final int pageSize;
        private final long totalRowCount;
        private final int pageIndex;
        private final List<Intervention> list;
        
        public Page(List<Intervention> data, long total, int page, int pageSize) {
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
        
        public List<Intervention> getList() {
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
    
}

