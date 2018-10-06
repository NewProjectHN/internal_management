package Com.IFI.InternalTool.DS.DAO.Impl;

import Com.IFI.InternalTool.DS.DAO.EmployeeDAO;
import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.DS.Model.Roles;
import Com.IFI.InternalTool.Utils.AppConstants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

@Repository("EmployeeDAO")
@Transactional
public class EmployeeDAOImpl implements EmployeeDAO {

	@Autowired
	private EntityManagerFactory entityManagerFactory;
//	@Autowired
//	private ProjectMembersDAOImpl projectMembersDAO;

	@Override
	public Roles getRolesByID(int role_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		Roles role=null;
		try{
			tx=session.beginTransaction();
		String hql = "FROM Roles where role_id=:role_id";
		Query query = session.createQuery(hql);
		query.setParameter("role_id", role_id);
		role = (Roles) query.uniqueResult();
		tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return role;
	}

	@Override
	public List<Employee> getAllEmployees(int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		List<Employee> list=null;
		try{
			tx=session.beginTransaction();
		String hql = "FROM Employee";
		Query query = session.createQuery(hql);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		list = query.getResultList();
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

	// save or update
	@Override
	public Boolean saveEmployee(Employee employee) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		session.saveOrUpdate(employee);
		tx.commit();
		session.close();
		return true;
	}

	@Override
	public Boolean EditEmployee(Employee employee) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		boolean success = false;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee currentEmp = session.get(Employee.class, employee.getEmployee_id());
			currentEmp.setFullname(employee.getFullname());
			currentEmp.setAddress(employee.getAddress());
			currentEmp.setAge(employee.getAge());
			currentEmp.setEmail(employee.getEmail());
			currentEmp.setGroup_id(employee.getGroup_id());
			currentEmp.setIs_active(employee.isIs_active());
			currentEmp.setPhone(employee.getPhone());
//			currentEmp.setType_id(employee.getType_id());
			currentEmp.setPassword(employee.getPassword());
			currentEmp.setRole_id(employee.getRole_id());
//			currentEmp.setType_id(employee.getType_id());
			currentEmp.setUsername(employee.getUsername());
			tx.commit();
			success = true;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return success;
	}

	@Override
	public Employee getEmployeeById(long employee_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		Employee emp=null;
		try{
			tx=session.beginTransaction();
		String hql = "FROM Employee where employee_id=:employee_id";
		Query query = session.createQuery(hql);
		query.setParameter("employee_id", employee_id);
		 emp = (Employee) query.uniqueResult();
		tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return emp;
	}

	@Override
	public List<Long> getEmployeeByManager(long manager_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		String hql = "Select distinct employee_id from Project_Manager where manager_id=:manager_id";
		Transaction tx = null;
		List<Long> list =null;
		try {
		
		tx=session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setParameter("manager_id", manager_id);
		list = query.list();
		tx.commit();}
		
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();}
		return list;
	}

	@Override

	public List<Employee> findEmployeeNameLike(String name, int page, int pageSize) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		List<Employee> list =null;
		try{
			tx=session.beginTransaction();
		String hql = "SELECT emp FROM Employee emp where emp.fullname LIKE CONCAT('%', :name, '%') ";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		query.setFirstResult((page - 1) * pageSize);
		query.setFetchSize(pageSize);
		query.setMaxResults(pageSize);
		list = query.getResultList();
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

//	@Override
//	public List<Employee> findEmployeeByGroupId(final String group_id, final int page, final int pageSize) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		Transaction tx = null;
//		List<Employee> emp =null;
//		try{
//			tx=session.beginTransaction();
//		String hql = "FROM Employee where group_id=:group_id";
//		Query query = session.createQuery(hql);
//		query.setParameter("group_id", group_id);
//		emp = query.getResultList();
//		tx.commit();
//		}
//		catch (Exception e) {
//			if (tx!=null) tx.rollback();
//            throw e;
//		}
//		finally {
//            session.close();
//        }
//		return emp;
//	}

//	@Override
//	public List<Employee> getListEmployeeInProject(long project_id, int page, int pageSize) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		Transaction tx = null;
//		List<Employee> emp =null;
//		try{
//			tx=session.beginTransaction();
//		List<Long> listEmployeesID = projectMembersDAO.listEmPloyeesIdInProject(project_id);
//		if (listEmployeesID.size() == 0) {
//			return Collections.emptyList();
//		}
//		String hql = "SELECT emp FROM  Employee emp where emp.employee_id in (:listEmployeesID) ORDER BY role_id";
//		Query query = session.createQuery(hql);
//		query.setParameter("listEmployeesID", listEmployeesID);
//		query.setFirstResult((page - 1) * pageSize);
//		query.setMaxResults(pageSize);
//		 emp = query.getResultList();
//		tx.commit();
//		}
//		catch (Exception e) {
//			if (tx!=null) tx.rollback();
//            throw e;
//		}
//		finally {
//            session.close();
//        }
//		return emp;
//	}

//	@Override
//	public List<Employee> getListEmployeeNotInProject(final long currentUserId, long project_id, int page,
//			int pageSize) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		Transaction tx = null;
//		List<Employee> list =null;
//		try{
//			tx=session.beginTransaction();
//		List<Long> listEmployeesID = projectMembersDAO.listEmPloyeesIdInProject(project_id);
//		String hql = "FROM Employee";
//		if (listEmployeesID.size() == 0) {
//			hql += " where group_id = :group_id and type_id = :type_id " + "and role_id >= :role_id order by role_id";
//		} else {
//			hql += " where employee_id NOT IN (:listEmployeesID) " + "and group_id = :group_id and type_id = :type_id "
//					+ "and role_id >= :role_id order by role_id";
//		}
//		Employee currentUser = getEmployeeById(currentUserId);
//		Query query = session.createQuery(hql);
//		if (listEmployeesID.size() != 0) {
//			query.setParameter("listEmployeesID", listEmployeesID);
//		}
//		query.setParameter("group_id", currentUser.getGroup_id());
//		query.setParameter("type_id", currentUser.getTypes().getType_id());
//		query.setParameter("role_id", currentUser.getRole().getRole_id());
//		query.setFirstResult((page - 1) * pageSize);
//		query.setMaxResults(pageSize);
//		list = query.getResultList();
//		tx.commit();
//		}
//		catch (Exception e) {
//			if (tx!=null) tx.rollback();
//            throw e;
//		}
//		finally {
//            session.close();
//        }
//		return list;
//	}

//	@Override
//	public List<Employee> getListSubEmployees(long employee_id) {
//
//		Employee emp = getEmployeeById(employee_id);
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		Transaction tx = null;
//		List<Employee> list =null;
//		try{
//			tx=session.beginTransaction();
//		String hql = "SELECT emp FROM  Employee emp where emp.role_id =:role_id and group_id = :group_id and type_id = ";
//		if (AppConstants.FULL_STACK.equals(emp.getRole().getName().name())) {
//			hql += AppConstants.BACK_END + " or type_id = " + AppConstants.FRONT_END;
//		} else {
//			hql += ":type_id";
//		}
//		Query query = session.createQuery(hql);
//		query.setParameter("role_id", emp.getRole_id() + 1);
//		query.setParameter("group_id", emp.getGroup_id());
//		if (!AppConstants.FULL_STACK.equals(emp.getRole().getName())) {
//			query.setParameter("type_id", emp.getType_id());
//		}
//		list = query.getResultList();
//		tx.commit();
//		}
//		catch (Exception e) {
//			if (tx!=null) tx.rollback();
//            throw e;
//		}
//		finally {
//            session.close();
//        }
//		return list;
//	}

//	@Override
//	public Long NumRecordsEmployeeInProject(long project_id) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		Transaction tx = null;
//		Long count =null;
//		try{
//			tx=session.beginTransaction();
//		List<Long> listEmployeesID = projectMembersDAO.listEmPloyeesIdInProject(project_id);
//		if (listEmployeesID.size() == 0) {
//			return (long) 0;
//		}
//		String hql = "SELECT count(*) FROM  Employee emp where emp.employee_id in (:listEmployeesID) ";
//		Query query = session.createQuery(hql);
//		query.setParameter("listEmployeesID", listEmployeesID);
//		count = (Long) query.uniqueResult();
//		tx.commit();
//		}
//		catch (Exception e) {
//			if (tx!=null) tx.rollback();
//            throw e;
//		}
//		finally {
//            session.close();
//        }
//		return count;
//	}

//	@Override
//	public Long NumRecordsEmployeeNotInProject(final long employee_id, final long project_id) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		Transaction tx = null;
//		Long count =null;
//		try{
//			tx=session.beginTransaction();
//		List<Long> listEmployeesID = projectMembersDAO.listEmPloyeesIdInProject(project_id);
//		String hql = " SELECT count(*) FROM Employee where  " + " group_id = :group_id and type_id = :type_id "
//				+ "and role_id >= :role_id ";
//		Employee emp = getEmployeeById(employee_id);
//		if (listEmployeesID.size() != 0) {
//			hql += " and employee_id not in (:listEmployeesID)";
//		}
//		Query query = session.createQuery(hql);
//		query.setParameter("group_id", emp.getGroup_id());
//		query.setParameter("type_id", emp.getTypes().getType_id());
//		query.setParameter("role_id", emp.getRole().getRole_id());
//		if (listEmployeesID.size() != 0) {
//			query.setParameter("listEmployeesID", listEmployeesID);
//		}
//		 count = (Long) query.uniqueResult();
//		tx.commit();
//		}
//		catch (Exception e) {
//			if (tx!=null) tx.rollback();
//            throw e;
//		}
//		finally {
//            session.close();
//        }
//		return count;
//	}

	@Override
	public Long NumRecordsEmployeeNameLike(String name) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		Long count =null;
		try{
			tx=session.beginTransaction();
		String hql = "SELECT count(*) FROM Employee emp where emp.fullname LIKE CONCAT('%', :name, '%') ";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		count = (Long) query.uniqueResult();
		tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return count;
	}

	@Override
	public Long NumRecordsEmployeeInGroup(String group_id) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = null;
		Long count =null;
		try{
			tx=session.beginTransaction();
		String hql = "Select count(*) FROM Employee where group_id=:group_id";
		Query query = session.createQuery(hql);
		query.setParameter("group_id", group_id);
		count = (Long) query.uniqueResult();
		tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
            throw e;
		}
		finally {
            session.close();
        }
		return count;
	}

	

}