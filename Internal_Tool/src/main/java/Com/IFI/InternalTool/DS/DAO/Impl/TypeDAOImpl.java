//package Com.IFI.InternalTool.DS.DAO.Impl;
//
//import java.util.List;
//
//import javax.persistence.EntityManagerFactory;
//import javax.transaction.Transactional;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import Com.IFI.InternalTool.DS.DAO.TypeDAO;
//import Com.IFI.InternalTool.DS.Model.TypeName;
//import Com.IFI.InternalTool.DS.Model.Types;
//@Repository("TypesDAO")
//@Transactional
//public class TypeDAOImpl implements TypeDAO {
//	@Autowired
//	private EntityManagerFactory entityManagerFactory;
//
//	@Override
//	public Types getTypeByID(int type_id) {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		String hql = "FROM Types where type_id=:type_id";
//		Query query = session.createQuery(hql);
//		query.setParameter("type_id", type_id);
//		Types type = (Types) query.uniqueResult();
//		session.close();
//		return type;
//	}
//
//	@Override
//	public List<Types> getAllTypes() {
//		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
//		String hql = "FROM Types";
//		Query query = session.createQuery(hql);
//		List<Types> list = (List<Types>) query.list();
//		session.close();
//		return list;
//	}
//
//}