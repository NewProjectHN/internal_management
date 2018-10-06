//package Com.IFI.InternalTool.BS.Controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
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
//import Com.IFI.InternalTool.BS.Service.Impl.EmployeeServiceImpl;
//import Com.IFI.InternalTool.DS.Model.Employee;
//import Com.IFI.InternalTool.Payloads.ContentResponse;
//import Com.IFI.InternalTool.Payloads.PagedResponse;
//import Com.IFI.InternalTool.Payloads.Payload;
//import Com.IFI.InternalTool.Security.CurrentUser;
//import Com.IFI.InternalTool.Security.UserPrincipal;
//import Com.IFI.InternalTool.Utils.AppConstants;
//import Com.IFI.InternalTool.Utils.Business;
//
//@RestController
//@RequestMapping("/api/employees")
//public class EmployeeController {
//
//	@Autowired
//	EmployeeServiceImpl employeeService;
//	Payload message = new Payload();
//	Object data = new Object();
//
//	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
//
//	@GetMapping
//	public @ResponseBody Payload getEmployees(@CurrentUser UserPrincipal currentUser,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//		List<Employee> listEmployee = new ArrayList<Employee>();
//		try {
//			message.resetPayload();
//			listEmployee = employeeService.getAllEmployees(currentUser.getId(), page, pageSize);
//			data = listEmployee;
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e);
//			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		if (listEmployee.size() > 0) {
//			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Employees Successfull",
//					true);
//		} else {
//			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE,
//					"Get Employees Doesn't Successfull", false);
//		}
//		return message;
//	}
//
////	// Find Employee By Id
////	@GetMapping("/getEmployeeById")
////	public @ResponseBody Payload getEmployeeById(@RequestParam(value = "employee_id") long employeeId) {
////		logger.info("Get Employee By Id ... ");
////
////		try {
////			message.resetPayload();
////			data = employeeService.getEmployeeById(employeeId);
////		} catch (Exception e) {
////			logger.error("ERROR:", e.getMessage());
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
////					false);
////			return message;
////		}
////		if (data instanceof Employee) {
////			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
////					"Find Employee By ID Successfull", true);
////		} else {
////			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE,
////					"Find Employee By ID Doesn't Successfull", true);
////		}
////		return message;
////	}
//
//	// create Allocation
//	@PostMapping("/create")
//	public @ResponseBody Payload createEmployee(@CurrentUser UserPrincipal currentUser,
//			@Valid @RequestBody Employee employee,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//		logger.info("Create Employee ... ");
//		boolean success = true;
//		try {
//			message.resetPayload();
//			success = employeeService.createEmployee(currentUser.getId().longValue(), employee);
//			data = employee;
//
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e.getMessage());
//			message.setPayLoad("false", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
//					false);
//			return message;
//		}
//		if (success) {
//			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Create Employee Success",
//					true);
//		} else {
//			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE,
//					"Create Employee Doesn't Success", true);
//		}
//
//		return message;
//
//	}
//
//	@PostMapping("/update")
//	public @ResponseBody Payload updateEmployee(@CurrentUser UserPrincipal currentUser,
//			@Valid @RequestBody Employee emp,
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//		logger.info("Update Employee... ");
//		boolean success = false;
//		try {
//			message.resetPayload();
//			success = employeeService.EditEmployee(emp);
//			data = emp;
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e);
//			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		if (success) {
//			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Update Employee Successfull",
//					true);
//		} else {
//			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE,
//					"Update Employee Doesn't Successfull", false);
//		}
//		return message;
//	}
//
////	// find employees with name likes
////	@GetMapping("/findEmployeeNameLike")
////	public @ResponseBody Payload findEmployeeNameLike(@RequestParam("name") String nameLike,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("Find Employees Name Like ... ");
////		try {
////			message.resetPayload();
////			data = employeeService.findEmployeeNameLike(nameLike, page, pageSize);
////			Long count = employeeService.NumRecordsEmployeeNameLike(nameLike);
////			message.setPages(Business.getTotalsPages(count, pageSize));
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR:" + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Employees Name Like ... ",
////				true);
////		return message;
////	}
//
////	@GetMapping("/findEmployeeByGroupId")
////	public @ResponseBody Payload findEmployeeByGroupId(@RequestParam(value = "group_id") String groupId,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("Find Employees By Group ID ... ");
////		try {
////			message.resetPayload();
////			data = employeeService.findEmployeeByGroupId(groupId, page, pageSize);
////			Long count = employeeService.NumRecordsEmployeeInGroup(groupId);
////			message.setPages(Business.getTotalsPages(count, pageSize));
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Employees By Group ID ... ",
////				true);
////		return message;
////	}
//
////	@GetMapping("/getListEmployeeInProject")
////	public @ResponseBody Payload getListEmployeeInProject(@CurrentUser UserPrincipal currentUser, @RequestParam(value = "project_id") long project_id,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("Find Employees IN Project ... ... ");
////		try {
////			message.resetPayload();
////			data = employeeService.getListEmployeeInProject(currentUser.getId().longValue(), project_id, page, pageSize);
////			Long count = employeeService.NumRecordsEmployeeInProject(project_id);
////			message.setPages(Business.getTotalsPages(count, pageSize));
////
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
////				"Find Employees IN Project ... ... ", true);
////		return message;
////	}
////
////	@GetMapping("/getListEmployeeNotInProject")
////	public @ResponseBody Payload getListEmployeeNOTInProject(@CurrentUser UserPrincipal currentUser,
////			@RequestParam(value = "project_id") long project_id,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("Find Employees Not IN Project ... ");
////		try {
////			message.resetPayload();
////			data = employeeService.getListEmployeeNotInProject(currentUser.getId().longValue(), project_id, page, pageSize);
////			Long count = employeeService.NumRecordsEmployeeNotInProject(currentUser.getId(), project_id);
////			message.setPages(Business.getTotalsPages(count, pageSize));
////
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
////				"Find Employees Not IN Project ... ", true);
////		return message;
////	}
////
////	@GetMapping("/ManageMembersProject")
////	public @ResponseBody Payload ManageMembersProject(@CurrentUser UserPrincipal currentUser,
////			@RequestParam(value = "project_id") long project_id,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
////			@RequestParam(value = "page1", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page1,
////			@RequestParam(value = "pageSize1", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize1) {
////		logger.info("<manage Employees Project ... ");
////		data = " ";
////		//
////		try {
////			message.resetPayload();
////			List<Employee> list = employeeService.getListEmployeeInProject(currentUser.getId().longValue(), project_id, page, pageSize);
////			if (list != null) {
////				ContentResponse<Employee> ListEmployeeInProject = new ContentResponse<Employee>();
////				ListEmployeeInProject.setHead("ListEmployeeInProject");
////				ListEmployeeInProject.setPage(page);
////				ListEmployeeInProject.setSize(pageSize);
////				ListEmployeeInProject.setContent(list);
////				Long count = employeeService.NumRecordsEmployeeInProject(project_id);
////				ListEmployeeInProject.setTotalElements(count);
//////				message.setPages(Business.getTotalsPages(count, pageSize));
////				ListEmployeeInProject.setTotalPages(Business.getTotalsPages(count, pageSize));
////				data = ListEmployeeInProject;
////			}
////
////			List<Employee> list1 = employeeService.getListEmployeeNotInProject(currentUser.getId(), project_id, page1,
////					pageSize1);
////			if (list != null) {
////				ContentResponse<Employee> ListEmployeeNotInProject = new ContentResponse<Employee>();
////				ListEmployeeNotInProject.setHead("ListEmployeeNotInProject");
////				ListEmployeeNotInProject.setPage(page1);
////				ListEmployeeNotInProject.setSize(pageSize1);
////				ListEmployeeNotInProject.setContent(list1);
////				Long count1 = employeeService.NumRecordsEmployeeNotInProject(currentUser.getId(), project_id);
////				ListEmployeeNotInProject.setTotalElements(count1);
////				ListEmployeeNotInProject.setTotalPages(Business.getTotalsPages(count1, pageSize1));
////				Object data1 = "";
////				data1 = ListEmployeeNotInProject;
////				message.setData1(data1);
////			}
////
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Manage Employees Project ... ",
////				true);
////		return message;
////	}
//
////	@GetMapping("/getListSubEmployee")
////	public @ResponseBody Payload getListSubEmployee(@CurrentUser UserPrincipal currentUser,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("getListSubEmployee ... ");
////		try {
////			message.resetPayload();
////			data = employeeService.getListSubEmployee(currentUser.getId().longValue());
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "getListSubEmployee success ... ",
////				true);
////		return message;
////	}
//
////	@DeleteMapping("/deleteEmployee")
////	public @ResponseBody Payload deleteEmployee(@CurrentUser UserPrincipal currentUser,
////			@RequestParam(value = "employee_id") long employee_id,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("delete Employee ... ");
////		boolean success = false;
////		try {
////			message.resetPayload();
////			success = employeeService.deleteEmployee(employee_id);
////			data = employeeService.getAllEmployees(currentUser.getId(), page, pageSize);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		if (success) {
////			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,
////					"getListSubEmployee success ... ", true);
////		} else {
////			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE,
////					"getListSubEmployee doesn't success ... ", false);
////		}
////
////		return message;
////	}
////
////	@GetMapping("/getListEmployeeInProjectDoNotAllocated")
////	public @ResponseBody Payload getListEmployeeInProjectDoNotAllocated(@CurrentUser UserPrincipal currentUser,
////			@RequestParam(value = "project_id") long projectId) {
////		logger.info("Get List Employee In Project Do Not Allocated ... ");
////		List<Employee> result;
////		try {
////			message.resetPayload();
////			result = employeeService.getListEmployeeInProjectDoNotAllocated(currentUser.getId().longValue(), projectId);
////			data = result;
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		if (result.size() != 0) {
////			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,	"Get List Employee In Project Do Not Allocated Success ... ", true);
////		} else {
////			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE, "Get List Employee In Project Do Not Allocated Doesn't Success ... ", false);
////		}
////
////		return message;
////	}
////
////	@GetMapping("/getAllTypes")
////	public @ResponseBody Payload getAllTypes() {
////		logger.info("Get All Types ... ");
////		try {
////			message.resetPayload();
////			data = employeeService.getAllTypes();
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,	"get All Types... ", true);
////
////
////		return message;
////	}
//	
////	@GetMapping("/getListProjectByEmpId")
////	public @ResponseBody Payload getListProjectByEmpId(@RequestParam(value = "employee_id") long employeeId) {
////		logger.info("get List Project By Emp Id ... ");
////		try {
////			message.resetPayload();
////			data = employeeService.getListProjectByEmpId(employeeId);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,	"get List Project By Emp Id... ", true);
////		return message;
////	}
//
//}