package Com.IFI.InternalTool.DS.DAO.Impl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Com.IFI.InternalTool.DS.DAO.ProjectDAO;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;

@Repository("ProjectDAO")
@Transactional
public class ProjectDAOImpl implements ProjectDAO {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
//	@Autowired
//	private AllocationDAOImpl allocationDaoImpl;
//	@Autowired
//	private ProjectMembersDAOImpl projectMemberDAOImpl;

    @Override
    public List<Project> getAllProject() {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;
        List<Project> list = null;
        try {
            tx = session.beginTransaction();
            String hql = "FROM Project";
            Query query = session.createQuery(hql);
            list = query.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return list;
    }


    @Override
    public List<Project> getAllProjects(int page, int pageSize, boolean isDESC) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        List<Project> list = null;
        try {
            String hql = "FROM Project where status = 1 ORDER BY project_id ";
            if (isDESC) {
                hql += "DESC";
            } else {
                hql += "ASC";
            }
            Query query = session.createQuery(hql);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            list = query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Long NumerRecordsGetAllProjects() {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        String hql = "select count(*) FROM Project where status = 1";
        Query query = session.createQuery(hql);
        Long count = (Long) query.uniqueResult();
        session.close();
        return count;
    }


    @Override
    public Project getProjectById(long project_id) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        String hql = "FROM Project where status = 1 and project_id=:project_id";
        Query query = session.createQuery(hql);
        query.setParameter("project_id", project_id);
        Project pro = (Project) query.uniqueResult();
        session.close();
        return pro;
    }

    @Override
    public List<ProjectManager> getProjectManagerByEmp(long employee_id, long project_id) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;
        List<ProjectManager> list = null;
        try {
            tx = session.beginTransaction();
            String hql = "Select distinct p FROM ProjectManager p where p.employee_id=:employee_id and p.project_id=:project_id ";
            Query query = session.createQuery(hql);
            query.setParameter("employee_id", employee_id);
            query.setParameter("project_id", project_id);
            list = query.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public List<Long> getProjectByEmp(long employee_id) {
        Transaction tx = null;
        List<Long> list = null;
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        try {
            tx = session.beginTransaction();
            String hql = "Select distinct p.project_id FROM ProjectManager p LEFT JOIN Vacation AS v ON p.employee_id=v.employee_id where p.employee_id=:employee_id";
            Query query = session.createQuery(hql);
            query.setParameter("employee_id", employee_id);
            list = query.list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return list;
    }


    @Override
    public Long getBigestManagerId(long project_id) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        String hql = "select manager_id FROM Project where status = 1 and project_id = :project_id";
        Query query = session.createQuery(hql);
        query.setParameter("project_id", project_id);
        Long result = (Long) query.uniqueResult();
        session.close();
        return result;
    }

}