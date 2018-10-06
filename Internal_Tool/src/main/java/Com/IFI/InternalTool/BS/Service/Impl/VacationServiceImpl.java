package Com.IFI.InternalTool.BS.Service.Impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import Com.IFI.InternalTool.Utils.MailThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.VacationService;
import Com.IFI.InternalTool.DS.DAO.EmployeeDAO;
import Com.IFI.InternalTool.DS.DAO.VacationDAO;
import Com.IFI.InternalTool.DS.Model.Vacation;
import Com.IFI.InternalTool.DS.Model.Vacation_Approved;
import Com.IFI.InternalTool.DS.Model.Vacation_Log;
import Com.IFI.InternalTool.DS.Model.Vacation_Type;
import Com.IFI.InternalTool.DS.Model.SearchModel.VacationSearch;
import Com.IFI.InternalTool.Payloads.EmailSent;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import static com.google.common.collect.Lists.newArrayList;
@Service("VacationService")
public class VacationServiceImpl implements VacationService{
	@Autowired
	VacationDAO vacationDAO;
	@Autowired
	EmployeeDAO employeeDAO;
	@Autowired
    private EmailService emailService;
	@Override
	public List<Vacation> getAllVacationByEmp(long employee_id,int page, int pageSize,String sortedColumn,Boolean desc,Boolean is_approved,List<Integer> status) {
		return vacationDAO.getAllVacationByEmp(employee_id, page, pageSize, sortedColumn, desc,is_approved,status);
	}
	@Override
	public boolean saveVacation(Vacation vacation) {
		return vacationDAO.saveVacation(vacation);
	}
	@Override
	public boolean deleteVacation(long vacation_id,long employee_id) {
		return vacationDAO.deleteVacation(vacation_id,employee_id);
	}
	@Override
	public Vacation getVacationById(long vacation_id) {
		return vacationDAO.getVacationById(vacation_id);
	}
	@Override
	public void saveVacationApproved(Vacation_Approved vacation_approved) {
		vacationDAO.saveVacationApproved(vacation_approved);
	}
	@Override
	public List<Vacation_Type> getAllVacationType() {
		return vacationDAO.getAllVacationType();
	}
	@Override
	public List<Vacation> searchVacationP2(Long employee_id,int page, int pageSize,String sortedColumn,Boolean desc,Boolean is_approved,List<Integer> status,VacationSearch vacationSearch) {
		return vacationDAO.searchVacationP2(employee_id,page, pageSize, sortedColumn, desc,is_approved,status,vacationSearch);
	}
	@Override
	public List<Vacation> searchVacationNeedApprove(Long manager_id,int page, int pageSize,String sortedColumn,Boolean desc,Boolean is_approved,VacationSearch vacationSearch) {
		return vacationDAO.searchVacationNeedApprove(manager_id,page, pageSize, sortedColumn, desc,is_approved,vacationSearch);
	}
	@Override
	public int getMaxPriority(long vacation_id) {
		return vacationDAO.getMaxPriority(vacation_id);
	}
	@Override
	public int getPriority(long manager_id, long vacation_id) {
		return vacationDAO.getPriority(manager_id, vacation_id);
	}
	@Override
	public List<Vacation> getAllVacationByEmp2(long manager_id,int page, int pageSize,String sortedColumn,Boolean desc) {
		return vacationDAO.getAllVacationByEmp2(manager_id,page,pageSize, sortedColumn, desc);
	
	}
	@Override
	public List<Long> getManagerByVacationId(long vacation_id) {
		return vacationDAO.getManagerByVacationId(vacation_id);
	}
	@Override
	public boolean saveVacationLog(Vacation_Log vacation_log) {
		return vacationDAO.saveVacationLog(vacation_log);
	}
	
	@Override
	public Vacation_Log getVacationLogByVacationIdAndNextApproveId(long vacation_id, long next_approve_id) {
		return vacationDAO.getVacationLogByVacationIdAndNextApproveId(vacation_id, next_approve_id);
	
	}

	@Override
	public List<Long> getApprovedIdByVacationId(Long vacation_id) {
		return vacationDAO.getApprovedIdByVacationId(vacation_id);
	}
	@Override
	public Long getDisApproveIdByVacationId(Long vacation_id) {
		return vacationDAO.getDisApproveIdByVacationId(vacation_id);
	}
	@Override
	public Long countAllVacationByEmp(List<Integer> status,Boolean is_approved,Long emp_id) {
		return vacationDAO.countAllVacationById(status,is_approved,emp_id);
	}
	@Override
	public List<Long> countVacationByStatusEmp(Long employee_id) {
		return vacationDAO.countVacationByStatus(employee_id);
	}
	@Override
	public List<Long> countVacationByStatusMng(Long manager_id) {
		return vacationDAO.countVacationByStatusMng(manager_id);
	}
	@Override
	public List<Vacation> getApprovedIdVacationLogByMng(long manager_id,int page, int pageSize,String sortedColumn,Boolean desc) {
		return vacationDAO.getApprovedIdVacationLogByMng(manager_id,page,pageSize,sortedColumn,desc);
	}
	@Override
	public List<Vacation> getDisapproveIdVacationLogByMng(long manager_id,int page, int pageSize,String sortedColumn,Boolean desc) {
		return vacationDAO.getDisapproveIdVacationLogByMng(manager_id,page,pageSize,sortedColumn,desc);
	}
	@Override
	public Vacation_Type getVacationTypeById(long vacation_type_id) {
		return vacationDAO.getVacationTypeById(vacation_type_id);
	}
	@Override
	public Long getManagerIdByEmpProAndStatus(long employee_id, long project_id, int status) {
			return vacationDAO.getManagerIdByEmpProAndStatus(employee_id, project_id, status);
		
	}
	@Override
	public Long CountSearchVacationNeedApprove(Long manager_id,Boolean is_approved, VacationSearch vacationSearch) {
		return vacationDAO.CountSearchVacationNeedApprove(manager_id,is_approved,vacationSearch);
	}
	@Override
	public Long CountSearchVacationP2(Long employee_id,Boolean is_approved,List<Integer> status, VacationSearch vacationSearch) {
		return vacationDAO.CountSearchVacationP2(employee_id,is_approved,status, vacationSearch);
	}
	@Override
	public Long countAllVacationByEmp2(long manager_id) {
		return vacationDAO.countAllVacationByEmp2(manager_id);
	}
	@Override
	public Long countApprovedVacationByMng(long manager_id) {
		return vacationDAO.countApprovedVacationByMng(manager_id);
	}
	@Override
	public Long countDisApprovedVacationByMng(long manager_id) {
		return vacationDAO.countDisApprovedVacationByMng(manager_id);
	}
	@Override
	public List<Vacation> searchVacationApproved(Long manager_id, int page, int pageSize, String sortedColumn,
			Boolean desc, Boolean is_approved, VacationSearch vacationSearch) {
		return vacationDAO.searchVacationApproved(manager_id, page, pageSize, sortedColumn, desc, is_approved, vacationSearch);
	}
	@Override
	public Long CountSearchVacationApproved(Long manager_id, Boolean is_approved, 
			VacationSearch vacationSearch) {
		return vacationDAO.CountSearchVacationApproved(manager_id, is_approved, vacationSearch);
	}
	@Override
	public List<Vacation> searchVacationDisApproved(Long manager_id, int page, int pageSize, String sortedColumn,
			Boolean desc, Boolean is_approved, VacationSearch vacationSearch) {
		return vacationDAO.searchVacationDisApproved(manager_id, page, pageSize, sortedColumn, desc, is_approved, vacationSearch);
	}
	@Override
	public Long CountSearchVacationDisApproved(Long manager_id, Boolean is_approved,
			VacationSearch vacationSearch) {
		return vacationDAO.CountSearchVacationDisApproved(manager_id, is_approved, vacationSearch);
	}
	@Override
	public List<Long> getProjectByManager(long manager_id) {
		return vacationDAO.getProjectByManager(manager_id);
	}

	@Override
	public void sendEmail(EmailSent emailSent) throws UnsupportedEncodingException{
			String not_reply="<br/><br/>-------------------DO NOT REPLY THIS EMAIL---------------------";
//		final Email email = DefaultEmail.builder()
//				.from(new InternetAddress("ifisolution",
//						"Internal-Tool"))
//				.to(newArrayList(
//						new InternetAddress(emailSent.getSend_to(),
//								emailSent.getName())))
//				.subject(emailSent.getSubject())
//				.body(emailSent.getBody() + not_reply)
//				.encoding("UTF-8").build();
//
//		emailService.send(email);

		MimeMultipart content = new MimeMultipart();
		MimeBodyPart textPart = new MimeBodyPart();

		try {
			//String text = "TEST TEXT";
			textPart.setText(emailSent.getBody() + not_reply,"UTF-8","html");
			content.addBodyPart(textPart);
			String subject = emailSent.getSubject();
			new MailThread(emailSent.getSend_to(), subject, content).run();
			System.out.println("SEND!!!!!!");
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	@Override
	public Long getNextManagerByPrio(long vacation_id, int prio) {
		return vacationDAO.getNextManagerByPrio(vacation_id, prio);
	}

}