package Com.IFI.InternalTool.DS.DAO;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.Vacation;
import Com.IFI.InternalTool.DS.Model.Vacation_Approved;
import Com.IFI.InternalTool.DS.Model.Vacation_Log;
import Com.IFI.InternalTool.DS.Model.Vacation_Type;
import Com.IFI.InternalTool.DS.Model.SearchModel.VacationSearch;

public interface VacationDAO {
		List<Vacation> getAllVacationByEmp(long employee_id,int page, int pageSize,String sortedColumn,Boolean desc,Boolean is_approved,List<Integer> status);
		Long countAllVacationById(List<Integer> status,Boolean is_approved,Long emp_id);
		boolean saveVacation(Vacation vacation);
		boolean deleteVacation(long vacation_id,long employee_id);
		Vacation getVacationById(long vacation_id);
		void saveVacationApproved(Vacation_Approved vacation_approved);
		List<Vacation_Type> getAllVacationType();
		Vacation_Type getVacationTypeById(long vacation_type_id);
		List<Vacation> searchVacationNeedApprove(Long manager_id,int page, int pageSize,String sortedColumn,Boolean desc,Boolean is_approved,VacationSearch vacationSearch);
		Long CountSearchVacationNeedApprove(Long manager_id,Boolean is_approved,VacationSearch vacationSearch);
		List<Vacation> searchVacationApproved(Long manager_id,int page, int pageSize,String sortedColumn,Boolean desc,Boolean is_approved,VacationSearch vacationSearch);
		Long CountSearchVacationApproved(Long manager_id,Boolean is_approved,VacationSearch vacationSearch);
		List<Vacation> searchVacationDisApproved(Long manager_id,int page, int pageSize,String sortedColumn,Boolean desc,Boolean is_approved,VacationSearch vacationSearch);
		Long CountSearchVacationDisApproved(Long manager_id,Boolean is_approved,VacationSearch vacationSearch);
		List<Vacation> searchVacationP2(Long employee_id,int page, int pageSize,String sortedColumn,Boolean desc,Boolean is_approved,List<Integer> status,VacationSearch vacationSearch);
		Long CountSearchVacationP2(Long employee_id,Boolean is_approved,List<Integer> status,VacationSearch vacationSearch);
		int getMaxPriority(long vacation_id);
		int getPriority(long manager_id,long vacation_id);
		Long getNextManagerByPrio(long vacation_id,int prio);
		List<Vacation> getAllVacationByEmp2(long manager_id,int page, int pageSize,String sortedColumn,Boolean desc);
		Long countAllVacationByEmp2(long manager_id);
		List<Long> getManagerByVacationId(long vacation_id);
		boolean saveVacationLog(Vacation_Log vacation_log);
		Vacation_Log getVacationLogByVacationIdAndNextApproveId(long vacation_id,long next_approve_id);
		List<Long> getApprovedIdByVacationId(Long vacation_id);
		Long getDisApproveIdByVacationId(Long vacation_id);
		List<Long> countVacationByStatus(Long employee_id);
		List<Long> countVacationByStatusMng(Long manager_id);
		List<Vacation> getApprovedIdVacationLogByMng(long manager_id,int page, int pageSize,String sortedColumn,Boolean desc);
		Long countApprovedVacationByMng(long manager_id);
		List<Vacation> getDisapproveIdVacationLogByMng(long manager_id,int page, int pageSize,String sortedColumn,Boolean desc);
		Long countDisApprovedVacationByMng(long manager_id);
		Long getManagerIdByEmpProAndStatus(long employee_id,long project_id,int status);
		List<Long> getProjectByManager(long manager_id);
}