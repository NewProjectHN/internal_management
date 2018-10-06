package Com.IFI.InternalTool.BS.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.DS.DAO.OvertimeDao;
import Com.IFI.InternalTool.DS.Model.Overtime;
import Com.IFI.InternalTool.DS.Model.Overtime_Approved;
import Com.IFI.InternalTool.DS.Model.Overtime_Log;
import Com.IFI.InternalTool.DS.Model.Overtime_Type;
import Com.IFI.InternalTool.DS.Model.SearchModel.OvertimeSearch;
import Com.IFI.InternalTool.Payloads.EmailSent;




@Service("OvertimeService")
public class OvertimeService {
	
	@Autowired
	OvertimeDao overtimeDao;
	
	public List<Overtime_Type> getAllOvertimeType(){
		return overtimeDao.getAllOvertimeType();
	}
	
	public Overtime_Type getOvertimeTypeByID(long overtime_type_id) {
		return overtimeDao.getOvertimeTypeById(overtime_type_id);
	}
	
	public List<Overtime> getAllOvertimeByEmp(long employee_id,int page, int pageSize,String sortedColumn,Boolean desc,Boolean is_approved,List<Integer> status) {
		return overtimeDao.getAllOvertimeByEmp(employee_id, page, pageSize, sortedColumn, desc, is_approved, status);
	}
	
	public Overtime getOvertimeById(long overtime_id) {
		return overtimeDao.getOvertimeById(overtime_id);
	}
	public boolean saveOvertime(Overtime overtime) {
		return overtimeDao.saveOvertime(overtime);
	}
	
	public List<Long> getManagerByOvertimeId(long overtime_id){
		return overtimeDao.getManagerByOvertimeId(overtime_id);
	}
	
	public boolean saveOvertimeLog(Overtime_Log overtime_Log) {
		return overtimeDao.saveOvertimeLog(overtime_Log);
	}
	
	public boolean saveOvertimeApproved(Overtime_Approved overtime_Approved) {
		return overtimeDao.saveOvertimeApproved(overtime_Approved);
	}
	
	
	
	public List<Long> getApprovedIdByOvertimeId(long overtime_id){
		return overtimeDao.getApprovedIdByOvertimeId(overtime_id);
		
	}
	
	public Long getDisApproveIdByOvertimeId(long overtime_id){
		return overtimeDao.getDisApproveIdByOvertimeId(overtime_id);
	}
	
	public boolean deleteOvertime(long overtime_id) {
		return overtimeDao.deleteOvertime(overtime_id);
	}
	public Overtime_Log getOverTimeLogByOvertimeIdAndNextApprovedId(long overtime_id, long next_approved_id){
		return overtimeDao.getOverTimeLogByOvertimeIdAndNextApprovedId(overtime_id, next_approved_id);
	}
	
	public int getPriority(long manager_id, long overtime_id) {
		return overtimeDao.getPriority(manager_id, overtime_id);
	}
	
	public int getMaxPriority(long overtime_id) {
		return overtimeDao.getMaxPriority(overtime_id);
	}
	
	public List<Overtime> searchOvertimeManagerNeedApprove(Long manager_id, int page, int pageSize, String sortedColumn, Boolean desc,Boolean is_approved,
			OvertimeSearch overtimeSearch){
		return overtimeDao.searchOvertimeManagerNeedApprove(manager_id, page, pageSize, sortedColumn, desc, is_approved, overtimeSearch);
	}
	public Long CountSearchOvertimeManagerNeedApprove(Long manager_id,Boolean is_approved,OvertimeSearch overtimeSearch) {
		return overtimeDao.CountSearchOvertimeManagerNeedApprove(manager_id, is_approved, overtimeSearch);
	}
	
	public List<Overtime> searchOvertimeManagerApproved(Long manager_id, int page, int pageSize, String sortedColumn, Boolean desc,Boolean is_approved,
			OvertimeSearch overtimeSearch){
		return overtimeDao.searchOvertimeManagerApproved(manager_id, page, pageSize, sortedColumn, desc, is_approved, overtimeSearch);
	}
	public Long CountSearchOvertimeManagerApproved(Long manager_id,Boolean is_approved,OvertimeSearch overtimeSearch) {
		return overtimeDao.CountSearchOvertimeManagerApproved(manager_id, is_approved, overtimeSearch);
	}
	
	
	public List<Overtime> searchOvertimeManagerDisApproved(Long manager_id, int page, int pageSize, String sortedColumn, Boolean desc,Boolean is_approved,
			OvertimeSearch overtimeSearch){
		return overtimeDao.searchOvertimeManagerDisApproved(manager_id, page, pageSize, sortedColumn, desc, is_approved, overtimeSearch);
	}
	public Long CountSearchOvertimeManagerDisApproved(Long manager_id,Boolean is_approved,OvertimeSearch overtimeSearch) {
		return overtimeDao.CountSearchOvertimeManagerDisApproved(manager_id, is_approved, overtimeSearch);
	}
	
	
	public List<Overtime> searchOvertimeEmployee(Long employee_id,int page, int pageSize,String sortedColumn,Boolean desc,Boolean is_approved,List<Integer> status,OvertimeSearch overtimeSearch){
		return overtimeDao.searchOvertimeEmployee(employee_id, page, pageSize, sortedColumn, desc, is_approved, status, overtimeSearch);
	}
	public Long CountSearchEmployee(Long employee_id,Boolean is_approved,List<Integer> status,OvertimeSearch overtimeSearch) {
		return overtimeDao.CountSearchEmployee(employee_id, is_approved, status, overtimeSearch);
	}
	
	public Long getManagerIdByEmpProAndStatus(long employee_id, long project_id, int status) {
		return overtimeDao.getManagerIDByEmpProAndStatus(employee_id, project_id, status);
	}
	public Long countAllOvertimeByEmp(List<Integer> status, Boolean is_approved, Long emp_id) {
		return overtimeDao.countAllOvertimeByEmp(status, is_approved, emp_id);
	}
	public List<Long> countOvertimeByStatus(Long employee){
		return overtimeDao.countOvertimeByStatus(employee);
	}
	
	public List<Overtime> getAllOvertimeByEmp2(long manager_id , int page, int pageSize, String sortedColumn,
			Boolean desc){
		return overtimeDao.getAllOvertimeByEmp2(manager_id, page, pageSize, sortedColumn, desc);
	}
	
	public Long countAllOvertimeByEmp2(long manager_id) {
		return overtimeDao.countAllOvertimeByEmp2(manager_id);
	}
	public List<Long> countOvertimeByStatusManager(long manager_id){
		return overtimeDao.countOvertimeByStatusManager(manager_id);
	}
	public List<Overtime> getApprovedIdOvertimeLogByManager(long manager_id, int page, int pageSize, String sortedColumn, Boolean desc){
		return overtimeDao.getApprovedIdOvertimeLogByManager(manager_id, page, pageSize, sortedColumn, desc);
	}
	
	public List<Overtime> getDisapproveIdOvertimeLogByManager(long manager_id, int page, int pageSize, String sortedColumn, Boolean desc){
		return overtimeDao.getDisapproveIdOvertimeLogByManager(manager_id, page, pageSize, sortedColumn, desc);
	}
	public Long countApprovedOvertimeByManager(long manager_id) {
		return overtimeDao.countApprovedOvertimeByManager(manager_id);
	}
	public Long countDisapprovedOvertimeByManager(long manager_id) {
		return overtimeDao.countDisApprovedOvertimeByMng(manager_id);
	}
	public void sendEmail(EmailSent emailSent) throws UnsupportedEncodingException {
		  overtimeDao.sendEmail(emailSent);
	}
	public Long getNextManagerByPrio(long overtime_id,int prio) {
		return overtimeDao.getNextManagerByPrio(overtime_id, prio);
	}
}