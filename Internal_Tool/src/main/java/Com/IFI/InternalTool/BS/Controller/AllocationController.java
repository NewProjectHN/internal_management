///*
//package Com.IFI.InternalTool.BS.Controller;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import Com.IFI.InternalTool.BS.Service.Impl.AllocationServiceImpl;
//import Com.IFI.InternalTool.BS.Service.Impl.EmployeeServiceImpl;
//import Com.IFI.InternalTool.DS.Model.Allocation;
//import Com.IFI.InternalTool.DS.Model.Employee;
//import Com.IFI.InternalTool.Payloads.ContentResponse;
//import Com.IFI.InternalTool.Payloads.Payload;
//import Com.IFI.InternalTool.Security.CurrentUser;
//import Com.IFI.InternalTool.Security.UserPrincipal;
//import Com.IFI.InternalTool.Utils.AppConstants;
//import Com.IFI.InternalTool.Utils.Business;
//
//@RestController
//@RequestMapping("/api/allocations")
//public class AllocationController {
//
//	@Autowired
//	private AllocationServiceImpl allocationService;
//	@Autowired
//	EmployeeServiceImpl employeeService;
//
//	private static final Logger logger = LoggerFactory.getLogger(AllocationController.class);
//
//	Payload message = new Payload();
//	Object data = "";
//
//	// get Allocation With Employee ID
//	@GetMapping(("/allocations"))
//	public @ResponseBody Payload getAllocations(@CurrentUser UserPrincipal currentUser,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//
//		// boolean hasUserRole = currentUser.getAuthorities().stream()
//		// .anyMatch(r -> r.getAuthority().equals("ROLE_EMPLOYEE"));
//		// logger.info(hasUserRole + " role");
//
//		try {
//			message.resetPayload();
//			data = allocationService.getAllocations(currentUser.getId(), page, pageSize);
//			Long count = allocationService.NumRecordsAllocationByEmployeeID(currentUser.getId());
//			message.setPages(Business.getTotalsPages(count, pageSize));
//
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e);
//			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Allocations Successfull",
//				true);
//		return message;
//	}
//
//	// get Allocated OF Manager
//	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C') OR hasRole('ADMIN')")
//	@GetMapping("/allocated")
//	public @ResponseBody Payload getAllocatedofManager(@CurrentUser UserPrincipal currentUser,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//		try {
//			message.resetPayload();
//			data = allocationService.getAllocatedofManager(currentUser.getId(), page, pageSize);
//			Long count = allocationService.NumRecordsAllocatedofManager(currentUser.getId());
//			message.setPages(Business.getTotalsPages(count, pageSize));
//
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e);
//			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
//				"Get Allocated By Manager Successfull", true);
//		return message;
//	}
//
//	// create Allocation
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	@PostMapping("/create")
//	public @ResponseBody Payload createAllocation(@CurrentUser UserPrincipal currentUser,
//			@Valid @RequestBody Allocation allocation) {
//		logger.info("Create allocation ... ");
//
//		try {
//			message.resetPayload();
//			if (allocationService.createAllocation(currentUser.getId(), allocation)) {
//				data = allocation;
//				message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
//						"Create Allocation By ID Successfull", true);
//			} else {
//				message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
//						"ERROR: end_date < start_date or start_date is smaller than max end_date is history", false);
//			}
//
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e.getMessage());
//			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
//					false);
//			return message;
//		}
//
//		return message;
//
//	}
//
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	@PostMapping("/update")
//	public @ResponseBody Payload updateAllocation(@CurrentUser UserPrincipal currentUser,
//			@Valid @RequestBody Allocation allocation) {
//		logger.info("Update allocation ... ");
//
//		try {
//			message.resetPayload();
//			if (allocationService.updateAllocation(currentUser.getId().longValue(), allocation)) {
//				data = allocation;
//				message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
//						"Update Allocation By ID Successfull", true);
//			} else {
//				message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
//						"ERROR: Something wrong", false);
//			}
//
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e.getMessage());
//			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
//					false);
//			return message;
//		}
//
//		return message;
//
//	}
//
//
//	// get AllocationPlan
//	@GetMapping("/getAllocationPlan")
//	public @ResponseBody Payload getAllocationPlan(@RequestParam(value = "start_date") Date start_date,
//			@RequestParam("end_date") Date end_date) {
//		logger.info("get Allcation Plan ... ");
//		try {
//			message.resetPayload();
//			if (start_date.toLocalDate().isAfter(end_date.toLocalDate())) {
//				message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
//						"ERROR: Start_Date must be smaller End_Date ", false);
//			} else {
//				int month = start_date.toLocalDate().getMonthValue();
//				logger.info("Month: " + month);
//				int year = start_date.toLocalDate().getYear();
//				// get distance Time between start_date vs end_date not set Weekends;
//				int distanceTime = Business.getDistanceTime(start_date.toLocalDate(), end_date.toLocalDate());
//				logger.info("distance Time: " + distanceTime);
//				// get number days of month // get nums days weekend of month
//				int numDaysOfMonth = start_date.toLocalDate().getDayOfMonth();
//
//				logger.info("numDaysOfMonth: " + numDaysOfMonth);
//				int numDaysWeekOfMonth = Business.numberWeekendOfMonth(month, year);
//				logger.info("numDaysWeekOfMonth: " + numDaysWeekOfMonth);
//				double plan = Business.getAllocation_Plan(numDaysOfMonth, numDaysWeekOfMonth, distanceTime);
//				logger.info("Plan: " + plan);
//
//				data = plan;
//			}
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e.getMessage());
//			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "get Allcation Plan ", true);
//		return message;
//	}
//
//	@GetMapping("/getMaxEndDate")
//	public @ResponseBody Payload getMaxEndDate(@RequestParam(value = "employee_id") long employee_id,
//			@RequestParam(value = "year") int year,	@RequestParam("month") int month) {
//		logger.info("get Allocation Allcation Plan ... ");
//		try {
//			message.resetPayload();
//			data = allocationService.findMaxEndDate(employee_id, month, year);
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e.getMessage());
//			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "get Allocation Allcation Plan ",
//				true);
//		return message;
//	}
//
//	// Find Allocation By Id
//	@GetMapping("/{allocation_id}")
//	public @ResponseBody Payload findAllocationById(@PathVariable Long allocation_id) {
//		logger.info("Find Allocation By Id ... ");
//
//		try {
//			message.resetPayload();
//			data = allocationService.findById(allocation_id);
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e.getMessage());
//			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Allocation By ID Successfull",
//				true);
//		return message;
//
//	}
//
//	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C') OR hasRole('ADMIN')")
//	// delete Allocation - Only Leader or Admin has roles
//	@DeleteMapping(path = "deleteById/{allocation_id}")
//	public @ResponseBody Payload DeleteAllocationById(@PathVariable Long allocation_id) {
//		logger.info("Delete Allocation By Id ... ");
//
//		message.resetPayload();
//		if (allocationService.deleteByID(allocation_id)) {
//			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
//					"Delete Allocation By ID Successfull", true);
//			return message;
//		} else {
//			logger.error("ERROR: Get connection error");
//			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE,
//					"ERROR: Some things wrong error", false);
//			return message;
//		}
//	}
//
//	@GetMapping("/searchAllcationWithTime")
//	public @ResponseBody Payload searchAllocationWithMonth(@RequestParam(value = "year") int year,
//			@RequestParam("month") int month,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//		logger.info("search Allcation With Month ... ");
//		logger.info(year + " ");
//		try {
//			message.resetPayload();
//			data = allocationService.SearchAllocationWithTime(year, month, page, pageSize);
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e.getMessage());
//			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
//				"search Allcation With Month Successfull", true);
//		return message;
//	}
//
//	@GetMapping("/findAllocationByEmployeeID")
//	public @ResponseBody Payload findAllocationByEmployeeID(@RequestParam("employee_id") int employee_id,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//		logger.info("search Allcation With Employee ID ... ");
//		try {
//			message.resetPayload();
//			data = allocationService.findAllocationByEmployeeID(employee_id, page, pageSize, true);
//			Long count = allocationService.NumRecordsAllocationByEmployeeID(employee_id);
//			message.setPages(Business.getTotalsPages(count, pageSize));
//
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e.getMessage());
//			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
//				"search Allcation With Employee ID Successfull", true);
//		return message;
//	}
//
//	@GetMapping("/findAllocationByProjectID")
//	public @ResponseBody Payload findAllocationByProjectID(@RequestParam("project_id") int project_id,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//		logger.info("find Allcation By Project ID ... ");
//		try {
//			message.resetPayload();
//			data = allocationService.findAllocationByProjectID(project_id, page, pageSize, true);
//			Long count = allocationService.NumRecordsAllocationByProjectID(project_id);
//			message.setPages(Business.getTotalsPages(count, pageSize));
//
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e.getMessage());
//			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
//				"find Allcation By Project ID  ID Successfull", true);
//		return message;
//	}
//
//	@GetMapping("/findAllocationFromDateToDate")
//	public @ResponseBody Payload findAllocationFromDateToDate(@RequestParam("from_date") Date from_date,
//			@RequestParam("to_date") Date to_date,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//		logger.info("findAllocationFromDateToDate ... ");
//		try {
//			message.resetPayload();
//			data = allocationService.findAllocationFromDateToDate(from_date, to_date, page, pageSize);
//			Long count = allocationService.NumRecordsllocationFromDateToDate(from_date, to_date);
//			message.setPages(Business.getTotalsPages(count, pageSize));
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e.getMessage());
//			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
//				"find Allcation By Project ID  ID Successfull", true);
//		return message;
//	}
//
//	@GetMapping("/searchAllocation")
//	public @ResponseBody Payload searchAllocation(@RequestParam(value = "year", defaultValue = "0") int year,
//			@RequestParam(value = "month", defaultValue = "0") int month,
//			@RequestParam(value = "project_id", defaultValue = "0") long project_id,
//			@RequestParam(value = "employee_id", defaultValue = "0") long employee_id,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//		logger.info("Search Allocation ... ");
//		try {
//			message.resetPayload();
//			if (year <= 0) {
//				year = Calendar.getInstance().get(Calendar.YEAR);
//			}
//			if (month <= 0) {
//				month = Calendar.getInstance().get(Calendar.MONTH) + 1;
//				logger.info(month + " month now");
//			}
//			data = allocationService.searchAllocation(year, month, project_id, employee_id, page, pageSize);
//			Long count = allocationService.NumRecordssearchAllocation(year, month, project_id, employee_id);
//			message.setPages(Business.getTotalsPages(count, pageSize));
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e.getMessage());
//			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Search Allocation Successfull",
//				true);
//		return message;
//	}
//
//	// duplicateAllocationByMonth
//	@PostMapping(("/duplicateAllocationByMonth"))
//	public @ResponseBody Payload duplicateAllocationByMonth(@CurrentUser UserPrincipal currentUser,
//			@RequestParam(value = "year") int year,	@RequestParam("month") int month,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//
//		List<Allocation> result = new ArrayList<Allocation>();
//		try {
//			message.resetPayload();
//			result = allocationService.duplicateAllocationByMonth(currentUser.getId(), month, year, page, pageSize);
//			data = result;
//
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e);
//			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		if (result.size() == 0) {
//			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE, "Duplicate Allocation By Month Doesn't Successfull", false);
//		}else {
//			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Duplicate Allocation By Month Successfull", true);
//		}
//		return message;
//	}
//
//	// duplicatevAllocationByProject
//	@PostMapping(("/duplicateAllocationByProject"))
//	public @ResponseBody Payload duplicateAllocationByProject(@CurrentUser UserPrincipal currentUser, @RequestParam(value = "project_id") long projectId,
//			@RequestParam(value = "year") int year,	@RequestParam("month") int month,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//
//		List<Allocation> result = new ArrayList<Allocation>();
//		try {
//			message.resetPayload();
//			result = allocationService.duplicateAllocationByProject(currentUser.getId().longValue(), projectId, month, year, page, pageSize);
//			data = result;
//
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e);
//			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		if (result.size() == 0) {
//			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE, "Duplicate Allocation By Project Doesn't Successfull", false);
//		}else {
//			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Duplicate Allocation By Project Successfull", true);
//		}
//		return message;
//	}
//
//	// duplicateAllocationByEmployee
//	@PostMapping(("/duplicateAllocationByEmployee"))
//	public @ResponseBody Payload duplicateAllocationByEmployee(@CurrentUser UserPrincipal currentUser, @RequestParam("employee_id") int employeeId,
//			@RequestParam(value = "year") int year,	@RequestParam("month") int month,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//
//		List<Allocation> result = new ArrayList<Allocation>();
//		try {
//			message.resetPayload();
//			result = allocationService.duplicateAllocationByEmployee(currentUser.getId().longValue(), employeeId, month, year, page, pageSize);
//			data = result;
//
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e);
//			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		if (result.size() == 0) {
//			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE, "Duplicate Allocation By Employee Doesn't Successfull", false);
//		}else {
//			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Duplicate Allocation By Employee Successfull", true);
//		}
//		return message;
//	}
//
//	// getTotalAllocationPlanByEmployeeId
//	@GetMapping(("/getTotalAllocationPlanByEmployeeId"))
//	public @ResponseBody Payload getTotalAllocationPlanByEmployeeId(@CurrentUser UserPrincipal currentUser, @RequestParam("employee_id") int employeeId,
//			@RequestParam(value = "year") int year,	@RequestParam("month") int month) {
//
//		try {
//			message.resetPayload();
//			data = allocationService.getTotalAllocationPlanByEmployeeId(employeeId, month, year);
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e);
//			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Total Allocation Plan By EmployeeId Successfull", true);
//		return message;
//	}
//
//	// getAllocatedInfo
//	@GetMapping(("/getAllocatedInfo"))
//	public @ResponseBody Payload getAllocatedInfo(@CurrentUser UserPrincipal currentUser, @RequestParam("employee_id") int employeeId) {
//
//		try {
//			message.resetPayload();
//			data = allocationService.getAllocatedInfo(employeeId);
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e);
//			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Allocated Info Successfull", true);
//		return message;
//	}
//
//	@GetMapping("/ManageAllocation")
//	public @ResponseBody Payload ManageMembersProject(@CurrentUser UserPrincipal currentUser,
//			@RequestParam(value = "project_id") long project_id,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
//			@RequestParam(value = "page1", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page1,
//			@RequestParam(value = "pageSize1", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize1) {
//		logger.info("<manage Employees Project ... ");
//		data = " ";
//		//
//		try {
//			message.resetPayload();
//			List<Employee> list = employeeService.getListEmployeeInProject(currentUser.getId().longValue(), project_id, page, pageSize);
//			if (list != null) {
//				ContentResponse<Employee> ListEmployeeInProject = new ContentResponse<Employee>();
//				ListEmployeeInProject.setHead("ListEmployeeInProject");
//				ListEmployeeInProject.setPage(page);
//				ListEmployeeInProject.setSize(pageSize);
//				ListEmployeeInProject.setContent(list);
//				//Long count = employeeService.NumRecordsEmployeeInProject(project_id);
//				Long count = employeeService.NumRecordsEmployeeInProject(project_id);
//				ListEmployeeInProject.setTotalElements(count);
//				// message.setPages(Business.getTotalsPages(count, pageSize));
//				ListEmployeeInProject.setTotalPages(Business.getTotalsPages(count, pageSize));
//				data = ListEmployeeInProject;
//			}
//
//			List<Allocation> list1 = allocationService.findAllocationByProjectID(project_id, page1, pageSize1, true);
//			if (list != null) {
//				ContentResponse<Allocation> listAllocationInProject = new ContentResponse<Allocation>();
//				listAllocationInProject.setHead("listAllocationInProject");
//				listAllocationInProject.setPage(page1);
//				listAllocationInProject.setSize(pageSize1);
//				listAllocationInProject.setContent(list1);
//				//Long count1 = employeeService.NumRecordsEmployeeNotInProject(currentUser.getId(), project_id);
//				Long count1 = allocationService.NumRecordsAllocationByProjectID(project_id);
//				listAllocationInProject.setTotalElements(count1);
//				listAllocationInProject.setTotalPages(Business.getTotalsPages(count1, pageSize1));
//				Object data1 = "";
//				data1 = listAllocationInProject;
//				message.setData1(data1);
//			}
//
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e);
//			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Manage Allocation Project ... ",
//				true);
//		return message;
//	}
//
//}*/
