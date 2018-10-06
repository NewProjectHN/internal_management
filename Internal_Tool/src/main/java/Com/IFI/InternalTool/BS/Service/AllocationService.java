//package Com.IFI.InternalTool.BS.Service;
//
//import java.sql.Date;
//import java.util.List;
//
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.prepost.PreAuthorize;
//
//import Com.IFI.InternalTool.DS.Model.Allocation;
//import Com.IFI.InternalTool.DS.Model.AllocationDetail;
//import Com.IFI.InternalTool.Payloads.SupportAllocateResponse;
//import Com.IFI.InternalTool.Security.CurrentUser;
//import Com.IFI.InternalTool.Security.UserPrincipal;
//
//public interface AllocationService {
//
//	// find Allocation By Id
//	Allocation findById(final long allocation_id);
//
//	boolean createAllocation(final long currentUserID, final Allocation allocation);
//
//
//	public Boolean updateAllocation(final long currentUserID,Allocation allocation);
//
//	boolean deleteByID(final long allocation_id);
//
//	List<Allocation> getAllocations(final long employee_id, final int page, final int pageSize);
//
//	// get allocated of manager
//	List<Allocation> getAllocatedofManager(final long employee_id, final int page, final int pageSize);
//
//	Long NumRecordsAllocatedofManager(final long employee_id);
//
//	// search allocation with month or year
//	List<Allocation> SearchAllocationWithTime(final int year, final int month, final int page, final int pageSize);
//
//	// find AllocationByEmPloyeeID
//	List<Allocation> findAllocationByEmployeeID(final long employee_id, final int page, final int pageSize, final boolean isDESC);
//
//	Long NumRecordsAllocationByEmployeeID(final long employee_id);
//
//	// find AllocationByProjectID
//	List<Allocation> findAllocationByProjectID(final long project_id, final int page, final int pageSize, final boolean isDESC);
//
//	Long NumRecordsAllocationByProjectID(final long employee_id);
//
//	// save AllocationDetail
//	boolean saveAllocationDetail(final AllocationDetail allocationDetail);
//
//	// tim kiem allocation tu ngay den ngay
//	List<Allocation> findAllocationFromDateToDate(Date fromDate, Date toDate, int page, int pageSize);
//
//	//sao chep allocation theo thang
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	List<Allocation> duplicateAllocationByMonth(final long currentUserID, final int month, final int year, final int page, final int pageSize);
//
//	//sao chep allocation theo project
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	List<Allocation> duplicateAllocationByProject(final long currentUserID, final long projectId, final int month, final int year, final int page, final int pageSize);
//
//	//sao chep allocation theo employee
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	List<Allocation> duplicateAllocationByEmployee(final long currentUserID, final long employeeId, final int month, final int year, final int page, final int pageSize);
//
//	//tinh tong so allocation plan theo employee
//	//@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	Double getTotalAllocationPlanByEmployeeId(final long employeeId, final int month, final int year);
//
//	//tim kiem theo project id va employee id
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	List<Allocation> findAllocationByEmpIdProId(final long employeeId, final long projectId);
//
//	//lay thong tin phan cong cua mot nhan vien
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	SupportAllocateResponse getAllocatedInfo(final long employeeId);
//
//	public Long NumRecordsllocationFromDateToDate(Date fromDate, Date toDate);
//
//	public List<Allocation> searchAllocation(final int year, final int month,final long project_id,final long employee_id, final int page,
//			final int pageSize);
//	Long NumRecordssearchAllocation(final int year, final int month, final long project_id, final long employee_id);
//
//	public Date findMaxEndDate(final long employee_id, final int month, final int year);
//}