//package Com.IFI.InternalTool.DS.DAO.Impl;
//
//import java.util.List;
//
//import javax.persistence.EntityManagerFactory;
//import javax.transaction.Transactional;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import Com.IFI.InternalTool.DS.DAO.ProjectMembersDAO;
//import Com.IFI.InternalTool.DS.Model.Allocation;
//import Com.IFI.InternalTool.DS.Model.Employee;
//import Com.IFI.InternalTool.DS.Model.Project;
//import Com.IFI.InternalTool.DS.Model.ProjectMembers;
//
//@Repository("ProjectMembersDAO")
//@Transactional
//public class ProjectMembersDAOImpl implements ProjectMembersDAO {
//	private boolean success = false;
//
//	@Autowired
//	private EntityManagerFactory entityManagerFactory;
//	@Autowired
//	private AllocationDAOImpl allocationDAO;
//
//	@Override
//	public Boolean isMembersOfProject(long employee_id, final long project_id) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		String hql = "From ProjectMembers where employee_id=:employee_id or leader_id = :leader_id and project_id=:project_id";
//		Query query = session.createQuery(hql);
//		query.setParameter("employee_id", employee_id);
//		query.setParameter("project_id", project_id);
//		query.setParameter("leader_id", employee_id);
//		List<ProjectMembers> result = query.getResultList();
//		session.close();
//		if (result.size() > 0){
//			return true;
//		}else {
//			return false;
//		}
//	}
//
//	@Override
//	public Boolean addMemberToProject(ProjectMembers projectMember) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
////		if (isMembersOfProject(projectMember.getEmployee_id(), projectMember.getProject_id())) {
////			return false;
////		}
//
//		// projectMember.setPriority(employeeDAO.getEmployeeById(projectMember.getEmployee_id()).getType_id());
//		try {
//			session.saveOrUpdate(projectMember);
//			success = true;
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			session.close();
//		}
//		return success;
//	}
//
//	@Override
//	public Boolean removeMemberOfProject(long projectMemberId) {
//
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		boolean success = false;
//		Transaction tx = null;
//		tx = session.beginTransaction();
//		String hql = "Delete from ProjectMembers where project_members_id = :project_members_id and ";
//		Query query = session.createQuery(hql);
//		query.setParameter("project_members_id", projectMemberId);
//		int row = query.executeUpdate();
//		success = true;
//		tx.commit();
//		session.close();
//		if (row > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	@Override
//	public List<Long> listEmPloyeesIdInProject(long project_id) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		String hql = "Select distinct pm.employee_id From ProjectMembers pm where pm.project_id =: project_id";
//		Query query = session.createQuery(hql);
//		query.setParameter("project_id", project_id);
//		return query.getResultList();
//	}
//
//	@Override
//	public boolean deleteAllMemberInProject(long projectId) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		Transaction tx = null;
//		tx = session.beginTransaction();
//		// xoa tat ca allocation cua project do
//		for (Allocation allocation : allocationDAO.findAllocationByProjectID(projectId, 1, Integer.MAX_VALUE, false)) {
//			allocationDAO.deleteById(allocation.getAllocation_id());
//		}
//		//xoa tat ca nhan vien trong project
//		String hql = "Delete from ProjectMembers where project_id = :project_id";
//		Query query = session.createQuery(hql);
//		query.setParameter("project_id", projectId);
//		int row = query.executeUpdate();
//		tx.commit();
//		session.close();
//		if (row > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//
//	@Override
//	public ProjectMembers getProjectMemberById(final long projectMemberId) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		String hql = "from ProjectMembers where project_members_id = :projectMemberId";
//		Query query = session.createQuery(hql);
//		query.setParameter("projectMemberId", projectMemberId);
//		ProjectMembers result = (ProjectMembers) query.uniqueResult();
//		session.close();
//		return result;
//	}
//
//	@Override
//	public boolean deleteProjectMemberByEmployeeId(long employeeId) {
//
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		boolean success = false;
//		Transaction tx = null;
//		tx = session.beginTransaction();
//		String hql = "Delete from ProjectMembers where employee_id = :employeeId ";
//		Query query = session.createQuery(hql);
//		query.setParameter("employeeId", employeeId);
//		int row = query.executeUpdate();
//		success = true;
//		tx.commit();
//		session.close();
//		if (row > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	@Override
//	public ProjectMembers getProjectMemberByProIdAndEmpId(long projectId, long employeeId) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		String hql = "from ProjectMembers where project_id = :project_id and employee_id = :employee_id";
//		Query query = session.createQuery(hql);
//		query.setParameter("project_id", projectId);
//		query.setParameter("employee_id", employeeId);
//		ProjectMembers result = (ProjectMembers) query.uniqueResult();
//		session.close();
//		return result;
//	}
//
//	@Override
//	public boolean updateTotalAllocationPlan(ProjectMembers projectMember) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		boolean success = false;
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
//			ProjectMembers currentProjectMembers = session.get(ProjectMembers.class, projectMember.getProject_members_id());
//			currentProjectMembers.setTotal_allocation_plan(projectMember.getTotal_allocation_plan());
//			tx.commit();
//			success = true;
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			session.close();
//		}
//		return success;
//	}
//
//
//
//	@Override
//	public List<Long> getListEmployeeIdInProject(long leaderId, long projectId, int page, int pageSize) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		String hql = "select distinct employee_id FROM ProjectMembers where project_id = :project_id and leader_id = :leader_id";
//		Query query = session.createQuery(hql);
//		query.setParameter("project_id", projectId);
//		query.setParameter("leader_id", leaderId);
//		query.setFirstResult((page - 1) * pageSize);
//		query.setMaxResults(pageSize);
//		List<Long> list = query.getResultList();
//		session.close();
//		return list;
//	}
//
//	@Override
//	public List<Long> getListProjectIdOfEmployee(long employeeId, int page, int pageSize) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		String hql = "select distinct project_id FROM ProjectMembers where employee_id = :employee_id";
//		Query query = session.createQuery(hql);
//		query.setParameter("employee_id", employeeId);
//		query.setFirstResult((page - 1) * pageSize);
//		query.setMaxResults(pageSize);
//		List<Long> list = query.getResultList();
//		session.close();
//		return list;
//	}
//
//
//}