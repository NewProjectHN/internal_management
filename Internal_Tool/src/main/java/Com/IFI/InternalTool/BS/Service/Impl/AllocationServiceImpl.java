//package Com.IFI.InternalTool.BS.Service.Impl;
//
//import java.sql.Date;
//import java.text.DecimalFormat;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import Com.IFI.InternalTool.BS.Service.AllocationService;
//import Com.IFI.InternalTool.DS.DAO.AllocationDAO;
//import Com.IFI.InternalTool.DS.DAO.ProjectManagerDAO;
//import Com.IFI.InternalTool.DS.DAO.Impl.AllocationDAOImpl;
//import Com.IFI.InternalTool.DS.DAO.Impl.AllocationDetailDAOImpl;
//import Com.IFI.InternalTool.DS.DAO.Impl.EmployeeDAOImpl;
//import Com.IFI.InternalTool.DS.DAO.Impl.ProjectDAOImpl;
//import Com.IFI.InternalTool.DS.DAO.Impl.ProjectManagerDAOImpl;
//import Com.IFI.InternalTool.DS.DAO.Impl.ProjectMembersDAOImpl;
//import Com.IFI.InternalTool.DS.Model.Allocation;
//import Com.IFI.InternalTool.DS.Model.AllocationDetail;
//import Com.IFI.InternalTool.DS.Model.Employee;
//import Com.IFI.InternalTool.DS.Model.ProjectMembers;
//import Com.IFI.InternalTool.Payloads.SupportAllocateResponse;
//import Com.IFI.InternalTool.Security.UserPrincipal;
//import Com.IFI.InternalTool.Utils.Business;
//
//@Service
//public class AllocationServiceImpl implements AllocationService {
//
//	@Autowired
//	private AllocationDAOImpl allocationDAO;
//
//	@Autowired
//	private AllocationDetailDAOImpl allocationDetailDAO;
//
//	@Autowired
//	private EmployeeDAOImpl employeeDAO;
//
//	@Autowired
//	private ProjectDAOImpl projectDAO;
//
//	@Autowired
//	private ProjectMembersDAOImpl projectMembersDAOImpl;
//
//	private static final Logger logger = LoggerFactory.getLogger(AllocationServiceImpl.class);
//
//	@Override
//	public boolean createAllocation(final long currentUserID, Allocation allocation) {
//		LocalDate start_date = allocation.getStart_date().toLocalDate();
//		LocalDate end_date = allocation.getEnd_date().toLocalDate();
//
//		// end_date must be > start_date
//		if (start_date.isAfter(end_date)) {
//			return false;
//		}
//
//		int roleCurrentUser = employeeDAO.getEmployeeById(currentUserID).getRole_id();
//		int roleEmployeeInAllocated = employeeDAO.getEmployeeById(allocation.getEmployee_id()).getRole_id();
//		if (roleCurrentUser >= roleEmployeeInAllocated) {
//			return false;
//		}
//
//		// get month , get year
//		int month = start_date.getMonthValue();
//		int year = start_date.getYear();
//
//		// get maxEndDate Allocation in History
//		Date maxEndDate = allocationDAO.findMaxEndDate(allocation.getEmployee_id(), month, year);
//		logger.info(maxEndDate + " max end_date in history");
//
//		// check start_date with maxEndDate
//		if (maxEndDate != null) {
//			if (start_date.isBefore(maxEndDate.toLocalDate()) || start_date.isEqual(maxEndDate.toLocalDate())) {
//				return false;
//			}
//		}
//
//		// get distance Time between start_date vs end_date not set Weekends;
//		int distanceTime = Business.getDistanceTime(start_date, end_date);
//		logger.info("distance Time: " + distanceTime);
//
//		// get number days of month // get nums days weekend of month
//		int numDaysOfMonth = start_date.lengthOfMonth();
//		int numDaysWeekOfMonth = Business.numberWeekendOfMonth(month, year);
//
//		// set allocation_plan
//		double allocation_plan = Business.getAllocation_Plan(numDaysOfMonth, numDaysWeekOfMonth, distanceTime);
//		//tinh allocation_plan theo nua ngay
//		if (allocation.isHalf_day()) {
//			allocation_plan = allocation_plan/2.0;
//		}
//		logger.info("Allocation_Plan: " + allocation_plan);
//		allocation.setAllocation_plan(allocation_plan);
//
//		allocation.setMonth(start_date.getMonthValue());
//		allocation.setYear(start_date.getYear());
//		if (allocationDAO.saveAllocation(allocation)) {
//			//neu save thanh cong thi tinh lai total allocation plan
//			ProjectMembers projectMember = projectMembersDAOImpl.getProjectMemberByProIdAndEmpId(allocation.getProject_id(), allocation.getEmployee_id());
//			double currentTotalAllocationPlan = projectMember.getTotal_allocation_plan();
//			DecimalFormat df = new DecimalFormat("#.##");
//			projectMember.setTotal_allocation_plan(Double.valueOf(df.format(currentTotalAllocationPlan + allocation_plan)));
//			projectMembersDAOImpl.updateTotalAllocationPlan(projectMember);
//			//set project name, employee name
//			allocation.setProject_Name(projectDAO.getProjectById(allocation.getProject_id()).getName());
//			allocation.setEmployee_Name(employeeDAO.getEmployeeById(allocation.getEmployee_id()).getFullname());
//			return true;
//		} else {
//
//			return false;
//		}
//
//	}
//
//	@Override
//	public Boolean updateAllocation(long currentUserID, Allocation allocation) {
//		int roleCurrentUser = employeeDAO.getEmployeeById(currentUserID).getRole_id();
//		int roleEmployeeInAllocated = employeeDAO.getEmployeeById(allocation.getEmployee_id()).getRole_id();
//		if (roleCurrentUser >= roleEmployeeInAllocated) {
//			return false;
//		}
//		double allocationPlanBeforeUpdate = allocationDAO.findById(allocation.getAllocation_id()).getAllocation_plan();
//		boolean successUpdate = allocationDAO.updateAllocation(allocation);
//		// neu update thanh cong thi tinh lai total allocation plan trong project
//		if (successUpdate) {
//			double allocationPlanAfterUpdate = allocationDAO.findById(allocation.getAllocation_id()).getAllocation_plan();
//			ProjectMembers projectMember = projectMembersDAOImpl.getProjectMemberByProIdAndEmpId(allocation.getProject_id(), allocation.getEmployee_id());
//			double currentTotalAllocationPlan = projectMember.getTotal_allocation_plan();
//			double newTotalAllocationPlan = currentTotalAllocationPlan + allocationPlanAfterUpdate - allocationPlanBeforeUpdate;
//			DecimalFormat df = new DecimalFormat("#.##");
//			projectMember.setTotal_allocation_plan(Double.valueOf(df.format(newTotalAllocationPlan)));
//			projectMembersDAOImpl.updateTotalAllocationPlan(projectMember);
//		}
//		return successUpdate;
//	}
//
//	@Override
//	public List<Allocation> getAllocations(final long employee_id, int page, int pageSize) {
//		return convertAllocation(allocationDAO.getAllocations(employee_id, page, pageSize));
//	}
//
//	@Override
//	public List<Allocation> getAllocatedofManager(final long employee_id, int page, int pageSize) {
//		return convertAllocation(allocationDAO.getAllocatedOfManager(employee_id, page, pageSize));
//
//	}
//
//	@Override
//	public Long NumRecordsAllocatedofManager(long employee_id) {
//		return allocationDAO.NumRecordsAllocatedOfManager(employee_id);
//	}
//
//	@Override
//	public Allocation findById(long allocation_id) {
//		Allocation a = allocationDAO.findById(allocation_id);
//		a.setEmployee_Name(employeeDAO.getEmployeeById(a.getEmployee_id()).getFullname());
//		a.setProject_Name(projectDAO.getProjectById(a.getProject_id()).getName());
//		return a;
//	}
//
//	@Override
//	public boolean deleteByID(long allocation_id) {
//		//lay allocation plan truoc khi delete
//		Allocation allocation = allocationDAO.findById(allocation_id);
//		double allocationPlanDelete = allocation.getAllocation_plan();
//		boolean successDelete = allocationDAO.deleteById(allocation_id);
//		if (successDelete) {
//			ProjectMembers projectMember = projectMembersDAOImpl.getProjectMemberByProIdAndEmpId(allocation.getProject_id(), allocation.getEmployee_id());
//			double currentTotalAllocationPlan = projectMember.getTotal_allocation_plan();
//			double newTotalAllocationPlan = currentTotalAllocationPlan - allocationPlanDelete;
//			DecimalFormat df = new DecimalFormat("#.##");
//			projectMember.setTotal_allocation_plan(Double.valueOf(df.format(newTotalAllocationPlan)));
//			projectMembersDAOImpl.updateTotalAllocationPlan(projectMember);
//		}
//
//		return successDelete;
//	}
//
//	@Override
//	public List<Allocation> SearchAllocationWithTime(int year, int month, int page, int pageSize) {
//		if (year <= 0) {
//			year = Calendar.getInstance().get(Calendar.YEAR);
//		}
//		return convertAllocation(allocationDAO.searchAllocationWithTime(year, month, page, pageSize));
//	}
//
//	@Override
//	public List<Allocation> findAllocationByEmployeeID(long employee_id, int page, int pageSize, boolean isDESC) {
//
//		return convertAllocation(allocationDAO.findAllocationByEmployeeID(employee_id, page, pageSize, isDESC));
//	}
//
//	@Override
//	public Long NumRecordsAllocationByEmployeeID(long employee_id) {
//
//		return allocationDAO.NumRecordsAllocationByEmployeeID(employee_id);
//	}
//
//	@Override
//	public List<Allocation> findAllocationByProjectID(long project_id, int page, int pageSize, boolean isDESC) {
//		List<Allocation> resultList = allocationDAO.findAllocationByProjectID(project_id, page, pageSize, isDESC);
//		for (Allocation allocation : resultList) {
//			allocation.setEmployee_Name(employeeDAO.getEmployeeById(allocation.getEmployee_id()).getFullname());
//			allocation.setProject_Name(projectDAO.getProjectById(allocation.getProject_id()).getName());
//		}
//		return resultList;
//	}
//
//	@Override
//	public Long NumRecordsAllocationByProjectID(long project_id) {
//		return allocationDAO.NumRecordsAllocationByProjectID(project_id);
//	}
//
//	@Override
//	public boolean saveAllocationDetail(AllocationDetail allocationDetail) {
//
//		return allocationDetailDAO.saveAllocationDetail(allocationDetail);
//
//	}
//
//	@Override
//	public List<Allocation> findAllocationFromDateToDate(Date fromDate, Date toDate, int page, int pageSize) {
//
//		return convertAllocation(allocationDAO.findAllocationFromDateToDate(fromDate, toDate, page, pageSize));
//	}
//
//	public List<Allocation> convertAllocation(final List<Allocation> list) {
//		for (Allocation item : list) {
//			item.setEmployee_Name(employeeDAO.getEmployeeById(item.getEmployee_id()).getFullname());
//			item.setProject_Name(projectDAO.getProjectById(item.getProject_id()).getName());
//		}
//		return list;
//	}
//
//	@Override
//	public Long NumRecordsllocationFromDateToDate(Date fromDate, Date toDate) {
//		return allocationDAO.NumRecordsllocationFromDateToDate(fromDate, toDate);
//	}
//
//	@Override
//	public List<Allocation> searchAllocation(int year, int month, long project_id, long employee_id, int page,
//			int pageSize) {
//
//		return allocationDAO.searchAllocation(year, month, project_id, employee_id, page, pageSize);
//	}
//
//	@Override
//	public Long NumRecordssearchAllocation(int year, int month, long project_id, long employee_id) {
//
//		return allocationDAO.NumRecordssearchAllocation(year, month, project_id, employee_id);
//	}
//
//	@Override
//	public Date findMaxEndDate(long employee_id, int month, int year) {
//		return allocationDAO.findMaxEndDate(employee_id, month, year);
//	}
//
//	@Override
//	public List<Allocation> duplicateAllocationByMonth(long currentUserID, int month, int year, int page, int pageSize) {
//		List<Allocation> listDuplicateAllocation = SearchAllocationWithTime(year, month, page, pageSize);
//		for (Allocation allocation : listDuplicateAllocation) {
//			allocation.setMonth(month + 1);
//			allocation.setStart_date(Business.increaseMonthByOne(allocation.getStart_date()));
//			allocation.setEnd_date(Business.increaseMonthByOne(allocation.getEnd_date()));
//			createAllocation(currentUserID, allocation);
//		}
//		return listDuplicateAllocation;
//	}
//
//	@Override
//	public List<Allocation> duplicateAllocationByProject(long currentUserID, long projectId, int month, int year, int page, int pageSize) {
//		// tim kiem list allocation theo project
//		List<Allocation> listDuplicateAllocation = findAllocationByProjectID(projectId, page, pageSize, false);
//		for (Allocation allocation : listDuplicateAllocation) {
//			if (allocation.getMonth() == month && allocation.getYear() == year) {
//				allocation.setMonth(month + 1);
//				allocation.setStart_date(Business.increaseMonthByOne(allocation.getStart_date()));
//				allocation.setEnd_date(Business.increaseMonthByOne(allocation.getEnd_date()));
//				createAllocation(currentUserID, allocation);
//			}
//		}
//		return listDuplicateAllocation;
//	}
//
//	@Override
//	public List<Allocation> duplicateAllocationByEmployee(long currentUserID, long employeeId, int month, int year,	int page, int pageSize) {
//		// tim kiem list allocation theo employee
//		List<Allocation> listDuplicateAllocation = findAllocationByEmployeeID(employeeId, page, pageSize, false);
//		for (Allocation allocation : listDuplicateAllocation) {
//			if (allocation.getMonth() == month && allocation.getYear() == year) {
//				allocation.setMonth(month + 1);
//				allocation.setStart_date(Business.increaseMonthByOne(allocation.getStart_date()));
//				allocation.setEnd_date(Business.increaseMonthByOne(allocation.getEnd_date()));
//				createAllocation(currentUserID, allocation);
//			}
//		}
//		return listDuplicateAllocation;
//	}
//
//	@Override
//	public Double getTotalAllocationPlanByEmployeeId(long employeeId, int month, int year) {
//		Double total = 0.0;
//		// tim kiem list allocation theo employee
//		List<Allocation> listAllocation = findAllocationByEmployeeID(employeeId, 1, Integer.MAX_VALUE, false);
//		for (Allocation allocation : listAllocation) {
//			//tinh toan bo allocaion
//			if (month <= 0 && year <= 0) {
//				total += allocation.getAllocation_plan();
//				continue;
//			}else if (month <= 0 && year > 0) {		//tinh theo nam
//				if (allocation.getYear() == year) {
//					total += allocation.getAllocation_plan();
//				}
//				continue;
//			}else if (month > 0 && year > 0) {		//tinh theo ca nam va thang
//				if (allocation.getMonth() == month && allocation.getYear() == year) {
//					total += allocation.getAllocation_plan();
//				}
//				continue;
//			}
//		}
//		return total;
//	}
//
//	@Override
//	public List<Allocation> findAllocationByEmpIdProId(long employeeId, long projectId) {
//		return allocationDAO.findAllocationByEmpIdProId(employeeId, projectId);
//	}
//
//	@Override
//	public SupportAllocateResponse getAllocatedInfo(long employeeId) {
//		SupportAllocateResponse supportAllocateResponse = new SupportAllocateResponse();
//		Employee employee = employeeDAO.getEmployeeById(employeeId);
//		supportAllocateResponse.setEmployeeId(employeeId);
//		supportAllocateResponse.setEmployeeName(employee.getFullname());
//		int month = Calendar.getInstance().get(Calendar.MONTH);
//		int year = Calendar.getInstance().get(Calendar.YEAR);
//		supportAllocateResponse.setLastAllocatedDay(allocationDAO.findMaxEndDate(employeeId, month, year));
//		//lay theo thang nam hien tai
//		supportAllocateResponse.setTotalAllocationPlan(getTotalAllocationPlanByEmployeeId(employeeId, month, year));
//		return supportAllocateResponse;
//	}
//
//
//
//}