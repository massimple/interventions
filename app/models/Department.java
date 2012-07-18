package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import play.db.jpa.*;

/**
 * Department entity managed by JPA
 */
@Entity 
@SequenceGenerator(name = "department_seq", sequenceName = "department_seq")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq")
    public Long id;
    
    @Constraints.Required
    public String name;
    
    @ManyToMany(targetEntity = Intervention.class, fetch = FetchType.LAZY)
    @JoinTable(name = "ldeptinterv", joinColumns = @JoinColumn(name = "department_id"), inverseJoinColumns = @JoinColumn(name = "idintervention_id"))
    //@ForeignKey(name = "fk_termsourceacceptoffice_source_id", inverseName = "fk_termsourceacceptoffice_office_id")
    private List<Intervention> interventions = new ArrayList<Intervention>();
	
    /**
     * Find an Department by id.
     */
    public static Department findById(Long id) {
        return JPA.em().find(Department.class, id);
    }
    
    /**
     * Update this department.
     */
    public void update(Long id) {
        this.id = id;
        JPA.em().merge(this);
    }
    
    /**
     * Insert this new department.
     */
    public void save() {
        this.id = id;
        JPA.em().persist(this);
    }
    
    /**
     * Delete this department.
     */
    public void delete() {
        JPA.em().remove(this);
    }
     
    /**
     * Return a page of department
     *
     * @param page Page to display
     * @param pageSize Number of departments per page
     * @param sortBy Department property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page page(int page, int pageSize, String sortBy, String order, String filter) {
        if(page < 1) page = 1;
        Long total = (Long)JPA.em()
            .createQuery("select count(c) from Department c where lower(c.name) like ?")
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .getSingleResult();
        List<Department> data = JPA.em()
            .createQuery("from Department c where lower(c.name) like ? order by c." + sortBy + " " + order)
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
        return new Page(data, total, page, pageSize);
    }
    
    /**
     * Used to represent a departments page.
     */
    public static class Page {
        
        private final int pageSize;
        private final long totalRowCount;
        private final int pageIndex;
        private final List<Department> list;
        
        public Page(List<Department> data, long total, int page, int pageSize) {
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
        
        public List<Department> getList() {
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

