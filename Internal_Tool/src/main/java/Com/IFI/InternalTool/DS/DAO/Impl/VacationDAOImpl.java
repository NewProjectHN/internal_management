package Com.IFI.InternalTool.DS.DAO.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Com.IFI.InternalTool.DS.DAO.VacationDAO;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.Vacation;
import Com.IFI.InternalTool.DS.Model.Vacation_Approved;
import Com.IFI.InternalTool.DS.Model.Vacation_Log;
import Com.IFI.InternalTool.DS.Model.Vacation_Type;
import Com.IFI.InternalTool.DS.Model.SearchModel.VacationSearch;

@Repository("VacationDAO")
@Transactional
public class VacationDAOImpl implements VacationDAO {
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	// employee page
	@Override
	public List<Vacation> getAllVacationByEmp(long employee_id, int page, int pageSize, String sortedColumn,
			Boolean desc, Boolean is_approved, List<Integer> status) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Transaction tx = null;
        List<Vacation> list=null;
		try{
			tx=session.beginTransaction();
		String hql = "Select v FROM Vacation v INNER JOIN Vacation_Type AS vt ON vt.vacation_type_id=v.vacation_type INNER JOIN Project AS p ON v.project_id=p.project_id where v.employee_id=:employee_id and ((:is_approved IS NULL and v.is_approved IS NULL) or v.is_approved=:is_approved) and v.status in (:status) ";
		if (sortedColumn != null && desc != null) {
			String order = "";
			if (desc) {
				order = "desc";
			}
			hql += "ORDER BY " + sortedColumn + " " + order;
		}else {
			
			hql += " ORDER BY vacation_id" +  " desc";
		}
		Query query = session.createQuery(hql);
		query.setReadOnly(true);
		query.setParameter("employee_id", employee_id);
		query.setParameter("is_approved", is_approved);
		query.setParameterList("status", status);
		query.setFirstResult((page - 1) * pageSize);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		list = query.list();
		tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return list;
	}

	// get all vacation to approve manage/leader page
	@Override
	public List<Vacation> getAllVacationByEmp2(long manager_id, int page, int pageSize, String sortedColumn,
			Boolean desc) {
		Transaction tx = null;
		List<Vacation> list=null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
		tx=session.beginTransaction();
		String hql = "Select v FROM Vacation v INNER JOIN Vacation_Type AS vt ON v.vacation_type=vt.vacation_type_id INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Project AS p ON v.project_id=p.project_id INNER JOIN ProjectManager AS pm ON (v.employee_id=pm.employee_id and v.project_id=pm.project_id  and v.status=pm.priority)  where  pm.manager_id=:manager_id ";
		if (sortedColumn != null && desc != null) {
			String order = "";
			if (desc) {
				order = "desc";
			}
			hql += "ORDER BY " + sortedColumn + " " + order;
		}
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setReadOnly(true);
		query.setFirstResult((page - 1) * pageSize);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		list = query.list();
		if (list.size() > pageSize) {
			return list = list.subList(0, pageSize);
		}
		tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return list;
	}
	
	@Override
	public Long countAllVacationByEmp2(long manager_id) {
		Transaction tx = null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Long result=null;
		try {
		tx=session.beginTransaction();
		String hql = "Select count(v) FROM Vacation v INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Project AS p ON v.project_id=p.project_id INNER JOIN ProjectManager AS pm ON (v.employee_id=pm.employee_id and v.project_id=pm.project_id  and v.status=pm.priority)  where  pm.manager_id=:manager_id ";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setReadOnly(true);
		result=(Long) query.uniqueResult();
		tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return result;
	}
	
	@Override
	public boolean saveVacation(Vacation vacation) {
		
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		try {
		tx = session.beginTransaction();
		session.saveOrUpdate(vacation);
		tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return true;
	}

	@Override
	public boolean deleteVacation(long vacation_id,long employee_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		try {
		tx = session.beginTransaction();
		String hql = "Delete from Vacation where vacation_id=:vacation_id and employee_id=:employee_id";
		String hql2= "Delete from Vacation_Log where vacation_id=:vacation_id";
		Query query = session.createQuery(hql);
		Query query2 = session.createQuery(hql2);
		query.setParameter("vacation_id", vacation_id);
		query.setParameter("employee_id", employee_id);
		query2.setParameter("vacation_id", vacation_id);
		query.executeUpdate();
		query2.executeUpdate();
		tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }		return true;
	}

	@Override
	public Vacation getVacationById(long vacation_id) {
		Transaction tx = null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Vacation v =null;
		try {
		tx = session.beginTransaction();
		String hql = "FROM Vacation where vacation_id=:vacation_id";
		Query query = session.createQuery(hql);
		query.setParameter("vacation_id", vacation_id);
		query.setReadOnly(true);
		v = (Vacation) query.uniqueResult();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
		return v;
	}

	@Override
	public void saveVacationApproved(Vacation_Approved vacation_approved) {
		
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		try {
		tx = session.beginTransaction();
		session.saveOrUpdate(vacation_approved);
		tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
	}

	@Override
	public List<Vacation_Type> getAllVacationType() {
		
		Transaction tx = null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		List<Vacation_Type> list=null;
		try {
		tx = session.beginTransaction();
		String hql = "from Vacation_Type";
		Query query = session.createQuery(hql);
		list = query.list();
		query.setReadOnly(true);
		tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
		return list;
	}

	// search manager/leader page
	@Override
	public List<Vacation> searchVacationNeedApprove(Long manager_id, int page, int pageSize, String sortedColumn, Boolean desc,Boolean is_approved,
			VacationSearch vacationSearch) {
		Transaction tx = null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		List<Vacation> list=null;
			try { 
			tx = session.beginTransaction();
			String hql = "Select v from Vacation v INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Vacation_Type AS vt ON v.vacation_type=vt.vacation_type_id INNER JOIN Project AS p ON v.project_id=p.project_id INNER JOIN ProjectManager pm ON (pm.employee_id=v.employee_id and pm.project_id=v.project_id and pm.priority=v.status ) ";
			hql += "WHERE (:emp_name IS NULL OR e.fullname LIKE CONCAT('%', :emp_name, '%')) ";
			hql += "AND (:pro_name IS NULL OR p.name = :pro_name )";
			hql += "AND (:type_name IS NULL OR vt.vacation_type_name = :type_name ) ";
			hql += "AND ((:from_date IS NULL and ( :to_date IS NOT NULL and (:to_date>= v.to_date) or (:to_date < v.to_date and :to_date>=v.from_date))) ";
			hql += "or (:to_date IS NULL and (:from_date IS NOT NULL and :from_date <= v.to_date)) ";
			hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
			hql += "or (:from_date <= v.from_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
			hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.to_date and :from_date <= :to_date) ";
			hql += "or (:from_date <= v.from_date and :to_date >= v.to_date and :from_date <= :to_date) ";
			hql += "or (:from_date IS NULL and :to_date IS NULL)) ";
			hql += "AND ((:is_approved IS NULL and v.is_approved IS NULL) or v.is_approved=false) "; 
			hql += "AND (pm.manager_id=:manager_id) ";
			if (sortedColumn != null && desc != null) {
				String order = "";
				if (desc) {
					order = "desc";
				}
				hql += "ORDER BY " + sortedColumn + " " + order;
			}
			Query query = session.createQuery(hql);
			query.setParameter("manager_id", manager_id);
			query.setParameter("emp_name", vacationSearch.getEmp_name());
			query.setParameter("pro_name", vacationSearch.getPro_name());
			query.setParameter("from_date", vacationSearch.getFrom_date());
			query.setParameter("to_date", vacationSearch.getTo_date());
			query.setParameter("is_approved", is_approved);
			query.setParameter("type_name", vacationSearch.getType_name());
			query.setReadOnly(true);
			query.setFirstResult((page - 1) * pageSize);
			query.setFetchSize(pageSize);
			query.setMaxResults(pageSize);
		    list = query.list();
			tx.commit();
			}
			
			catch (Exception e) {
				if (tx!=null) tx.rollback();
	            throw e;
			}
			finally {
	            session.close();
	        }			
			
		return list;
		}
	@Override
	public Long CountSearchVacationNeedApprove(Long manager_id,Boolean is_approved,VacationSearch vacationSearch) {
		Transaction tx = null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Long result=null;
		try {
			tx = session.beginTransaction();
			String hql = "Select count(v) from Vacation v INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Project AS p ON v.project_id=p.project_id INNER JOIN ProjectManager pm ON (pm.employee_id=v.employee_id and pm.project_id=v.project_id and pm.priority=v.status) INNER JOIN Vacation_Type AS vt ON v.vacation_type=vt.vacation_type_id ";
			hql += "WHERE (:emp_name IS NULL OR e.fullname LIKE CONCAT('%', :emp_name, '%')) ";
			hql += "AND (:pro_name IS NULL OR p.name = :pro_name )";
			hql += "AND (:type_name IS NULL OR vt.vacation_type_name = :type_name ) ";
			hql += "AND ((:from_date IS NULL and ( :to_date IS NOT NULL and (:to_date>= v.to_date) or (:to_date < v.to_date and :to_date>=v.from_date))) ";
			hql += "or (:to_date IS NULL and (:from_date IS NOT NULL and :from_date <= v.to_date)) ";
			hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
			hql += "or (:from_date <= v.from_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
			hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.to_date and :from_date <= :to_date) "; 
			hql += "or (:from_date <= v.from_date and :to_date >= v.to_date and :from_date <= :to_date) ";
			hql += "or (:from_date IS NULL and :to_date IS NULL)) ";
			hql += "AND ((:is_approved IS NULL and v.is_approved IS NULL) or v.is_approved=false) "; 
			hql += "AND (pm.manager_id=:manager_id)";
			Query query = session.createQuery(hql);
			query.setParameter("manager_id", manager_id);
			query.setParameter("emp_name", vacationSearch.getEmp_name());
			query.setParameter("pro_name", vacationSearch.getPro_name());
			query.setParameter("from_date", vacationSearch.getFrom_date());
			query.setParameter("to_date", vacationSearch.getTo_date());
			query.setParameter("is_approved", is_approved);
			query.setParameter("type_name", vacationSearch.getType_name());
			query.setReadOnly(true);
			result=(Long) query.uniqueResult();
			tx.commit();
			}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
			return result;
		}
		
	@Override
	public List<Vacation> searchVacationApproved(Long manager_id, int page, int pageSize, String sortedColumn,
			Boolean desc, Boolean is_approved, VacationSearch vacationSearch) {
		Transaction tx = null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		List<Vacation> list =null;
		try {
			tx=session.beginTransaction();
		String hql = "Select v from Vacation v INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Vacation_Type AS vt ON v.vacation_type=vt.vacation_type_id INNER JOIN Project AS p ON v.project_id=p.project_id INNER JOIN ProjectManager pm ON (pm.employee_id=v.employee_id and pm.project_id=v.project_id) ";
		hql += "INNER JOIN Vacation_Log AS vl ON (vl.vacation_id=v.vacation_id) ";
		hql += "WHERE (:emp_name IS NULL OR e.fullname LIKE CONCAT('%', :emp_name, '%')) ";
		hql += "AND (:pro_name IS NULL OR p.name = :pro_name )";
		hql += "AND (:type_name IS NULL OR vt.vacation_type_name = :type_name ) ";
		hql += "AND ((:from_date IS NULL and ( :to_date IS NOT NULL and (:to_date>= v.to_date) or (:to_date < v.to_date and :to_date>=v.from_date))) ";
		hql += "or (:to_date IS NULL and (:from_date IS NOT NULL and :from_date <= v.to_date)) ";
		hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date <= v.from_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date <= v.from_date and :to_date >= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date IS NULL and :to_date IS NULL)) ";
		hql += "AND (v.is_approved=:is_approved or v.is_approved=false) "; 
		hql += "AND (vl.approved_id=:manager_id) and (vl.approved_id=pm.manager_id) ";
		if (sortedColumn != null && desc != null) {
			String order = "";
			if (desc) {
				order = "desc";
			}
			hql += "ORDER BY " + sortedColumn + " " + order;
		}
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setParameter("emp_name", vacationSearch.getEmp_name());
		query.setParameter("pro_name", vacationSearch.getPro_name());
		query.setParameter("from_date", vacationSearch.getFrom_date());
		query.setParameter("to_date", vacationSearch.getTo_date());
		query.setParameter("is_approved", is_approved);
		query.setParameter("type_name", vacationSearch.getType_name());
		query.setReadOnly(true);
		query.setFirstResult((page - 1) * pageSize);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		list = query.list();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
		return list;
	}

	@Override
	public Long CountSearchVacationApproved(Long manager_id, Boolean is_approved,
			VacationSearch vacationSearch) {
		Transaction tx = null;
		Long result=null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
		tx=session.beginTransaction();	
		String hql = "Select count(v) from Vacation v INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Vacation_Type AS vt ON v.vacation_type=vt.vacation_type_id INNER JOIN Project AS p ON v.project_id=p.project_id INNER JOIN ProjectManager pm ON (pm.employee_id=v.employee_id and pm.project_id=v.project_id) ";
		hql += "INNER JOIN Vacation_Log AS vl ON (vl.vacation_id=v.vacation_id) ";
		hql += "WHERE (:emp_name IS NULL OR e.fullname LIKE CONCAT('%', :emp_name, '%')) ";
		hql += "AND (:pro_name IS NULL OR p.name = :pro_name )";
		hql += "AND (:type_name IS NULL OR vt.vacation_type_name = :type_name ) ";
		hql += "AND ((:from_date IS NULL and ( :to_date IS NOT NULL and (:to_date>= v.to_date) or (:to_date < v.to_date and :to_date>=v.from_date))) ";
		hql += "or (:to_date IS NULL and (:from_date IS NOT NULL and :from_date <= v.to_date)) ";
		hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date <= v.from_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date <= v.from_date and :to_date >= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date IS NULL and :to_date IS NULL)) ";
		hql += "AND (v.is_approved=:is_approved or v.is_approved=false) "; 
		hql += "AND (vl.approved_id=:manager_id) and (vl.approved_id=pm.manager_id)";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setParameter("emp_name", vacationSearch.getEmp_name());
		query.setParameter("pro_name", vacationSearch.getPro_name());
		query.setParameter("from_date", vacationSearch.getFrom_date());
		query.setParameter("to_date", vacationSearch.getTo_date());
		query.setParameter("is_approved", is_approved);
		query.setParameter("type_name", vacationSearch.getType_name());
		query.setReadOnly(true);
	 result=(Long) query.uniqueResult();
		tx.commit();}
		
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
		return result;
		
	}

	@Override
	public List<Vacation> searchVacationDisApproved(Long manager_id, int page, int pageSize, String sortedColumn,
			Boolean desc, Boolean is_approved, VacationSearch vacationSearch) {
		Transaction tx = null;
		List<Vacation> list=null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx=session.beginTransaction();	
		String hql = "Select v from Vacation v INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Vacation_Type AS vt ON v.vacation_type=vt.vacation_type_id INNER JOIN Project AS p ON v.project_id=p.project_id INNER JOIN ProjectManager pm ON (pm.employee_id=v.employee_id and pm.project_id=v.project_id) ";
		hql += "INNER JOIN Vacation_Log AS vl ON (vl.vacation_id=v.vacation_id) ";
		hql += "WHERE (:emp_name IS NULL OR e.fullname LIKE CONCAT('%', :emp_name, '%')) ";
		hql += "AND (:pro_name IS NULL OR p.name = :pro_name )";
		hql += "AND (:type_name IS NULL OR vt.vacation_type_name = :type_name ) ";
		hql += "AND ((:from_date IS NULL and ( :to_date IS NOT NULL and (:to_date>= v.to_date) or (:to_date < v.to_date and :to_date>=v.from_date))) ";
		hql += "or (:to_date IS NULL and (:from_date IS NOT NULL and :from_date <= v.to_date)) ";
		hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date <= v.from_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date <= v.from_date and :to_date >= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date IS NULL and :to_date IS NULL)) ";
		hql += "AND (v.is_approved=:is_approved) "; 
		hql += "AND (vl.disapproved_id=:manager_id) and (vl.disapproved_id=pm.manager_id) ";
		if (sortedColumn != null && desc != null) {
			String order = "";
			if (desc) {
				order = "desc";
			}
			hql += "ORDER BY " + sortedColumn + " " + order;
		}
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setParameter("emp_name", vacationSearch.getEmp_name());
		query.setParameter("pro_name", vacationSearch.getPro_name());
		query.setParameter("from_date", vacationSearch.getFrom_date());
		query.setParameter("to_date", vacationSearch.getTo_date());
		query.setParameter("is_approved", is_approved);
		query.setParameter("type_name", vacationSearch.getType_name());
		query.setReadOnly(true);
		query.setFirstResult((page - 1) * pageSize);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		list = query.list();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
		return list;
	}

	@Override
	public Long CountSearchVacationDisApproved(Long manager_id, Boolean is_approved,
			VacationSearch vacationSearch) {
		Transaction tx = null;
		Long result=null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx=session.beginTransaction();
		
		String hql = "Select count(v) from Vacation v INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Vacation_Type AS vt ON v.vacation_type=vt.vacation_type_id INNER JOIN Project AS p ON v.project_id=p.project_id INNER JOIN ProjectManager pm ON (pm.employee_id=v.employee_id and pm.project_id=v.project_id) ";
		hql += "INNER JOIN Vacation_Log AS vl ON (vl.vacation_id=v.vacation_id) ";
		hql += "WHERE (:emp_name IS NULL OR e.fullname LIKE CONCAT('%', :emp_name, '%')) ";
		hql += "AND (:pro_name IS NULL OR p.name = :pro_name )";
		hql += "AND (:type_name IS NULL OR vt.vacation_type_name = :type_name ) ";
		hql += "AND ((:from_date IS NULL and ( :to_date IS NOT NULL and (:to_date>= v.to_date) or (:to_date < v.to_date and :to_date>=v.from_date))) ";
		hql += "or (:to_date IS NULL and (:from_date IS NOT NULL and :from_date <= v.to_date)) ";
		hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date <= v.from_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date <= v.from_date and :to_date >= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date IS NULL and :to_date IS NULL)) ";
		hql += "AND (v.is_approved=:is_approved ) "; 
		hql += "AND (vl.disapproved_id=:manager_id) and (vl.disapproved_id=pm.manager_id)";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setParameter("emp_name", vacationSearch.getEmp_name());
		query.setParameter("pro_name", vacationSearch.getPro_name());
		query.setParameter("from_date", vacationSearch.getFrom_date());
		query.setParameter("to_date", vacationSearch.getTo_date());
		query.setParameter("is_approved", is_approved);
		query.setParameter("type_name", vacationSearch.getType_name());
		query.setReadOnly(true);
	 result=(Long) query.uniqueResult();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
		return result;
		
	}
	
	
	
	
	// search employee page
	@Override
	public List<Vacation> searchVacationP2(Long employee_id,int page, int pageSize,String sortedColumn,Boolean desc,Boolean is_approved,List<Integer> status,VacationSearch vacationSearch) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		List<Vacation> list=null;
		try {
			tx=session.beginTransaction();
		String hql = "Select v from Vacation v INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Project AS p ON v.project_id=p.project_id INNER JOIN Vacation_Type AS vt ON v.vacation_type=vt.vacation_type_id ";
		hql += "AND (:pro_name IS NULL OR p.name = :pro_name )";
		hql += "AND (:type_name IS NULL OR vt.vacation_type_name = :type_name ) ";
		hql += "AND ((:from_date IS NULL and ( :to_date IS NOT NULL and (:to_date >= v.to_date) or (:to_date<v.to_date and :to_date>=v.from_date))) ";
		hql += "or (:to_date IS NULL and (:from_date IS NOT NULL and :from_date <= v.to_date)) ";
		hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date <= v.from_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date <= v.from_date and :to_date >= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date IS NULL and :to_date IS NULL)) ";
		hql += "AND ((:is_approved IS NULL and v.is_approved IS NULL) or v.is_approved=:is_approved) and v.status in (:status) "; 
		hql += "AND (v.employee_id=:employee_id) ";
		if (sortedColumn != null && desc != null) {
			String order = "";
			if (desc) {
				order = "desc";
			}
			hql += "ORDER BY " + sortedColumn + " " + order;
		}
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setParameter("pro_name", vacationSearch.getPro_name());
		query.setParameter("from_date", vacationSearch.getFrom_date());
		query.setParameter("to_date", vacationSearch.getTo_date());
		query.setParameter("is_approved", is_approved);
		query.setParameterList("status",status);
		query.setParameter("type_name", vacationSearch.getType_name());
		query.setFirstResult((page - 1) * pageSize);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		query.setReadOnly(true);
		list = query.list();
		if (list.size() > pageSize) {
			return list = list.subList(0, pageSize);
		}
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
		return list;

	}
	
	@Override
	public Long CountSearchVacationP2(Long employee_id,Boolean is_approved,List<Integer> status,VacationSearch vacationSearch) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Long result=null;
		Transaction tx = null;
		try {
		
		tx=session.beginTransaction();
		String hql = "Select count(v) from Vacation v INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Project AS p ON v.project_id=p.project_id INNER JOIN Vacation_Type AS vt ON v.vacation_type=vt.vacation_type_id ";
		hql += "AND (:pro_name IS NULL OR p.name = :pro_name )";
		hql += "AND (:type_name IS NULL OR vt.vacation_type_name = :type_name ) ";
		hql += "AND ((:from_date IS NULL and ( :to_date IS NOT NULL and (:to_date >= v.to_date) or (:to_date<v.to_date and :to_date>=v.from_date))) ";
		hql += "or (:to_date IS NULL and (:from_date IS NOT NULL and :from_date <= v.to_date)) ";
		hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date <= v.from_date and :to_date >= v.from_date and :to_date <= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date >= v.from_date and :from_date <= v.to_date and :to_date >= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date <= v.from_date and :to_date >= v.to_date and :from_date <= :to_date) ";
		hql += "or (:from_date IS NULL and :to_date IS NULL)) ";
		hql += "AND ((:is_approved IS NULL and v.is_approved IS NULL) or v.is_approved=:is_approved) and v.status in (:status)  "; 
		hql += "AND (v.employee_id=:employee_id) ";	
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setParameter("pro_name", vacationSearch.getPro_name());
		query.setParameter("from_date", vacationSearch.getFrom_date());
		query.setParameter("to_date", vacationSearch.getTo_date());
		query.setParameter("is_approved", is_approved);
		query.setParameterList("status",status);
		query.setParameter("type_name", vacationSearch.getType_name());
		query.setReadOnly(true);
		 result=(Long) query.uniqueResult();
		tx.commit();}
		
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }			return result;
	
	}
	// get vacation's max priority
	@Override
	public int getMaxPriority(long vacation_id) {
		Transaction tx = null;
		int max=0;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx=session.beginTransaction();
		
		String hql = "Select MAX(t.priority) from Vacation_Approved t where vacation_id=:vacation_id";
		Query query = session.createQuery(hql);
		query.setParameter("vacation_id", vacation_id);
		 max = (int) query.uniqueResult();
		 tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
		return max;
	}

	// get manager's priority
	@Override
	public int getPriority(long manager_id, long vacation_id) {
		Transaction tx = null;
		int p=0;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			
		tx=session.beginTransaction();
		String hql = "Select t.priority from Vacation_Approved t where manager_id=:manager_id and vacation_id=:vacation_id";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		query.setParameter("vacation_id", vacation_id);
		 p = (int) query.uniqueResult();
		 tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }			return p;
	}
	// get next manager by priority
	@Override
	public Long getNextManagerByPrio(long vacation_id, int prio) {
		long result=0;
		Transaction tx=null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx=session.beginTransaction();
		String hql = "Select t.manager_id from Vacation_Approved t where t.vacation_id=:vacation_id and t.priority=:prio";
		Query query = session.createQuery(hql);
		query.setParameter("vacation_id", vacation_id);
		query.setParameter("prio", prio);
		result= (long) query.uniqueResult();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
		return result;
	}
	@Override
	public List<Long> getManagerByVacationId(long vacation_id) {
		List<Long> list=null;
		Transaction tx=null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx=session.beginTransaction();
		String hql = "Select t.manager_id From Vacation_Approved t INNER JOIN Vacation AS v ON (t.vacation_id=v.vacation_id) where  t.vacation_id=:vacation_id ";
		Query query = session.createQuery(hql);
		query.setParameter("vacation_id", vacation_id);
		list = query.list();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
		return list;
	}

	@Override
	public boolean saveVacationLog(Vacation_Log vacation_log) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		try {
		tx = session.beginTransaction();
		session.saveOrUpdate(vacation_log);
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
		return true;
	}

	@Override
	public Vacation_Log getVacationLogByVacationIdAndNextApproveId(long vacation_id, long next_approve_id) {
		Transaction tx = null;
		Vacation_Log vl =null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx = session.beginTransaction();
		String hql = "From Vacation_Log where vacation_id=:vacation_id and next_approve_id=:next_approve_id";
		Query query = session.createQuery(hql);
		query.setParameter("next_approve_id", next_approve_id);
		query.setParameter("vacation_id", vacation_id);
		 vl = (Vacation_Log) query.uniqueResult();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }			return vl;
	}

	@Override
	public List<Long> getApprovedIdByVacationId(Long vacation_id) {
		Transaction tx = null;
		List<Long> list=null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx = session.beginTransaction();
		String hql = "Select v.approved_id From Vacation_Log v where vacation_id=:vacation_id and v.approved_id>0";
		Query query = session.createQuery(hql);
		query.setParameter("vacation_id", vacation_id);
		 list = query.list();
		 tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }	
		return list;
	}

	@Override
	public Long getDisApproveIdByVacationId(Long vacation_id) {
		Transaction tx = null;
		Long a=null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx = session.beginTransaction();
		String hql = "Select v.disapproved_id From Vacation_Log v where vacation_id=:vacation_id and v.disapproved_id>0";
		Query query = session.createQuery(hql);
		query.setParameter("vacation_id", vacation_id);
		a = (Long) query.uniqueResult();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return a;
	}

	@Override
	public Long countAllVacationById(List<Integer> status, Boolean is_approved, Long emp_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Long total = 0L;
		Transaction tx = null;
		Long count=null;
		try {
			tx = session.beginTransaction();
		for (Integer mystatus : status) {
			String hql = "SELECT count(e.vacation_id) FROM Vacation e WHERE e.employee_id = :emp_id and ((:is_approved IS NULL and e.is_approved IS NULL) or e.is_approved=:is_approved) and e.status=:mystatus";
			Query query = session.createQuery(hql);
			query.setParameter("emp_id", emp_id);
			query.setParameter("is_approved", is_approved);
			query.setParameter("mystatus", mystatus);
		    count = (Long) query.uniqueResult();
			total = total + count;
		}
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return total;
	}
//emp page
	@Override
	public List<Long> countVacationByStatus(Long employee_id) {
		Transaction tx = null;
		List<Long> result = new ArrayList<Long>();
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx = session.beginTransaction();
		// new
		String hql1 = "Select count(*) From Vacation v where v.employee_id=:employee_id and status=1 and is_approved IS NULL";
		Query query1 = session.createQuery(hql1);
		query1.setParameter("employee_id", employee_id);
		result.add((Long) query1.uniqueResult());
		// approving
		String hql2 = "Select count(*) From Vacation v where v.employee_id=:employee_id and status >= 1 and is_approved=false";
		Query query2 = session.createQuery(hql2);
		query2.setParameter("employee_id", employee_id);
		result.add((Long) query2.uniqueResult());
		// approved
		String hql3 = "Select count(*) From Vacation v where v.employee_id=:employee_id and status >= 1 and is_approved=true";
		Query query3 = session.createQuery(hql3);
		query3.setParameter("employee_id", employee_id);
		result.add((Long) query3.uniqueResult());
		// disapproved
		String hql4 = "Select count(*) From Vacation v where v.employee_id=:employee_id and status = -1 and is_approved=false";
		Query query4 = session.createQuery(hql4);
		query4.setParameter("employee_id", employee_id);
		result.add((Long) query4.uniqueResult());
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		
		return result;
	}
//manager page
	@Override
	public List<Long> countVacationByStatusMng(Long manager_id) {
		Transaction tx = null;
		List<Long> result = new ArrayList<Long>();
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx = session.beginTransaction();
		// need approve
		String hql1 = "Select count(va.vacation_id) From Vacation_Approved va INNER JOIN Vacation AS v ON va.vacation_id=v.vacation_id where va.priority=v.status and va.manager_id=:manager_id";
		Query query1 = session.createQuery(hql1);
		query1.setParameter("manager_id", manager_id);
		result.add((Long) query1.uniqueResult());
		// approved
		String hql2 = "Select count(v.approved_id) From Vacation_Log v where v.approved_id=:manager_id";
		Query query2 = session.createQuery(hql2);
		query2.setParameter("manager_id", manager_id);
		result.add((Long) query2.uniqueResult());
		// disapproved
		String hql3 = "Select count(v.disapproved_id) From Vacation_Log v where v.disapproved_id=:manager_id";
		Query query3 = session.createQuery(hql3);
		query3.setParameter("manager_id", manager_id);
		result.add((Long) query3.uniqueResult());
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return result;
	}
//manager page
	@Override
	public List<Vacation> getApprovedIdVacationLogByMng(long manager_id,int page, int pageSize,String sortedColumn,Boolean desc) {
		Transaction tx = null;
		List<Vacation> list=null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx = session.beginTransaction();		
				String hql = "Select v From Vacation v INNER JOIN Vacation_Log AS vl ON v.vacation_id=vl.vacation_id INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Project AS p ON v.project_id=p.project_id INNER JOIN Vacation_Type AS vt ON v.vacation_type=vt.vacation_type_id where vl.approved_id=:manager_id ";
				if (sortedColumn != null && desc != null) {
					String order = "";
					if (desc) {
						order = "desc";
					}
					hql += "ORDER BY " + sortedColumn + " " + order;
				}
				Query query = session.createQuery(hql);
				query.setParameter("manager_id", manager_id);
				query.setFirstResult((page - 1) * pageSize);
				query.setFetchSize(pageSize);
				query.setMaxResults(pageSize);
				list = query.list();
				
				tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
				return list;
	}

	@Override
	public List<Vacation> getDisapproveIdVacationLogByMng(long manager_id,int page, int pageSize,String sortedColumn,Boolean desc) {
		Transaction tx = null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		List<Vacation> list=null;
		try {
			tx = session.beginTransaction();		
				String hql = "Select v From Vacation v INNER JOIN Vacation_Log AS vl ON v.vacation_id=vl.vacation_id INNER JOIN Employee AS e ON v.employee_id= e.employee_id INNER JOIN Project AS p ON v.project_id=p.project_id INNER JOIN Vacation_Type AS vt ON v.vacation_type=vt.vacation_type_id where vl.disapproved_id=:manager_id ";
				if (sortedColumn != null && desc != null) {
					String order = "";
					if (desc) {
						order = "desc";
					}
					hql += "ORDER BY " + sortedColumn + " " + order;
				}
				Query query = session.createQuery(hql);
				query.setParameter("manager_id", manager_id);
				query.setFirstResult((page - 1) * pageSize);
				query.setFetchSize(pageSize);
				query.setMaxResults(pageSize);
				list = query.list();
				
				tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return list;
	}

	@Override
	public Vacation_Type getVacationTypeById(long vacation_type_id) {
		Vacation_Type a=null;
		Transaction tx = null;

		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx = session.beginTransaction();
		String hql = "From Vacation_Type vt where vt.vacation_type_id=:vacation_type_id";
		Query query = session.createQuery(hql);
		query.setParameter("vacation_type_id", vacation_type_id);
		 a=(Vacation_Type) query.uniqueResult();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return a;
	}

	@Override
	public Long getManagerIdByEmpProAndStatus(long employee_id, long project_id, int status) {
		Transaction tx = null;
		Long result= null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx = session.beginTransaction();
		String hql = "Select pm.manager_id from ProjectManager pm where pm.employee_id=:employee_id and pm.project_id=:project_id and pm.priority=:status";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		query.setParameter("project_id", project_id);
		query.setParameter("status", status);
		 result=(Long) query.uniqueResult();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return result;
	}

	@Override
	public Long countApprovedVacationByMng(long manager_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		Long result=null;
		try {
			tx = session.beginTransaction();
		String hql = "Select count(v.vacation_log_id) From Vacation_Log v where v.approved_id=:manager_id";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		 result=(Long) query.uniqueResult();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return result;
	}

	@Override
	public Long countDisApprovedVacationByMng(long manager_id) {
		Transaction tx = null;
		Long result=null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx = session.beginTransaction();
		String hql = "Select count(v.vacation_log_id) From Vacation_Log v where v.disapproved_id=:manager_id";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		result=(Long) query.uniqueResult();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return result;
	}

	@Override
	public List<Long> getProjectByManager(long manager_id) {
		Transaction tx = null;
		List<Long> result=null;
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		try {
			tx = session.beginTransaction();
		String hql = "Select distinct pm.project_id  From ProjectManager pm where pm.manager_id=:manager_id";
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		 result=query.list();
		tx.commit();}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }		
		return result;
	}

	

	
}