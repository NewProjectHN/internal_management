//package Com.IFI.InternalTool.DS.DAO.Impl;
//
//import java.sql.Date;
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.List;
//
//import javax.persistence.EntityManagerFactory;
//import javax.transaction.Transactional;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import Com.IFI.InternalTool.DS.DAO.AllocationDetailDAO;
//import Com.IFI.InternalTool.DS.Model.Allocation;
//import Com.IFI.InternalTool.DS.Model.AllocationDetail;
//
//@Repository("AllocationDetailDAO")
//@Transactional
//public class AllocationDetailDAOImpl implements AllocationDetailDAO {
//	@Autowired
//	private EntityManagerFactory entityManagerFactory;
//
//	private static final Logger logger = LoggerFactory.getLogger(AllocationDetailDAOImpl.class);
//
//	private boolean success = false;
//
//	@Override
//	public List<AllocationDetail> getAllocationDetails(final int page, final int pageSize) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		Transaction tx = null;
//		List<AllocationDetail> list =null;
//		try {
//			tx = session.beginTransaction();
//		String hql = "FROM AllocationDetail ";
//		Query query = session.createQuery(hql);
//		query.setFirstResult((page - 1) * pageSize);
//		query.setMaxResults(pageSize);
//		 list = query.getResultList();
//		tx.commit();}
//		catch (Exception e) {
//			if (tx!=null) tx.rollback();
//            throw e;
//		}
//		finally {
//            session.close();
//        }
//		return list;
//	}
//
//	@Override
//	public boolean saveAllocationDetail(final AllocationDetail allocationDetail) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		try {
//			session.saveOrUpdate(allocationDetail);
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
//	public boolean deleteById(final long allocation_detail_id) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		String hql = "Delete from AllocationDetail where allocation_detail_id =:allocation_detail_id";
//		Query query = session.createQuery(hql);
//		query.setParameter("allocation_detail_id", allocation_detail_id);
//		int row = query.executeUpdate();
//		session.close();
//		if (row > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	@Override
//	public AllocationDetail findById(long allocation_detail_id) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		Transaction tx = null;
//		AllocationDetail allocationDetail =null;
//		try {
//			tx = session.beginTransaction();
//		String hql = "FROM AllocationDetail where allocation_detail_id =:allocation_detail_id ";
//		Query query = session.createQuery(hql);
//		query.setParameter("allocation_detail_id", allocation_detail_id);
//		 allocationDetail = (AllocationDetail) query.uniqueResult();
//		 tx.commit();}
//		catch (Exception e) {
//			if (tx!=null) tx.rollback();
//            throw e;
//		}
//		finally {
//            session.close();
//        }
//		return allocationDetail;
//	}
//
//	@Override
//	public List<AllocationDetail> findAllocationDetailFromToDate(Date from_date, Date to_date) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean deleteAllocationDetailByAllocationId(long allocationId) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		Transaction tx = null;
//		tx = session.beginTransaction();
//		String hql = "Delete from AllocationDetail where allocation_id =:allocation_id";
//		Query query = session.createQuery(hql);
//		query.setParameter("allocation_id", allocationId);
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
//	@Override
//	public boolean autoCreateAllocationDetail(Allocation allocation) {
//		boolean success = false;
//		// // generic allocationDetail
//		LocalDate start_date = allocation.getStart_date().toLocalDate();
//		LocalDate end_date = allocation.getEnd_date().toLocalDate();
//		while (start_date.isBefore(end_date) || start_date.equals(end_date)) {
//			if ((start_date.getDayOfWeek() != DayOfWeek.SATURDAY
//					&& start_date.getDayOfWeek() != DayOfWeek.SUNDAY)) {
//				AllocationDetail a = new AllocationDetail();
//				a.setAllocation_id(allocation.getAllocation_id());
//				a.setEmployee_id(allocation.getEmployee_id());
//
//				if (allocation.isHalf_day()) {
//					a.setTime(4);
//					if (allocation.isPm()) {
//						LocalDateTime dateTime = start_date.atTime(13, 30, 00);
//						a.setDate(java.util.Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
//					}else {
//						LocalDateTime dateTime = start_date.atTime(8, 00, 00);
//						a.setDate(java.util.Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
//					}
//
//				}else {
//					a.setTime(8);
//					LocalDateTime dateTime = start_date.atTime(8, 00, 00);
//					a.setDate(java.util.Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
//				}
//				success = saveAllocationDetail(a);
//				if (!success) {
//					break;
//				}
//			}
//			start_date = start_date.plusDays(1);
//		}
//		return success;
//	}
//
//}