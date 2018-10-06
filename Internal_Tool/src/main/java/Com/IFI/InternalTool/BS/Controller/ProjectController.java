//package Com.IFI.InternalTool.BS.Controller;
//
//import Com.IFI.InternalTool.BS.Service.Impl.ProjectServiceImpl;
//import Com.IFI.InternalTool.DS.Model.Project;
//import Com.IFI.InternalTool.Payloads.Payload;
//import Com.IFI.InternalTool.Security.CurrentUser;
//import Com.IFI.InternalTool.Security.UserPrincipal;
//import Com.IFI.InternalTool.Utils.AppConstants;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.sql.Date;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/projects")
//public class ProjectController {
//	@Autowired
//	private ProjectServiceImpl projectService;
//
//	@Autowired
//	AuthenticationManager authenticationManager;
//	Payload message = new Payload();
//	Object data = "";
//	boolean success = false;
//
//	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
//
//	// lay tat ca project
//	@GetMapping
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	public @ResponseBody Payload getAllProjects(
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
//		try {
//			message.resetPayload();
//			data = projectService.getAllProjects(page, pageSize, false);
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e);
//			message.setPayLoad("Failed", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Projects Successfull", true);
//		if (!"".equals(data) || data != null) {
//			message.setPages(projectService.NumerRecordsGetAllProjects(pageSize));
//		}
//		return message;
//	}
//
////	// tao project
////	@PostMapping(value = { "/create" })
////	@PreAuthorize("hasRole('ROLE_ADMIN')")
////	public @ResponseBody Payload createProject(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody Project projectRequest,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("Create Project ... ");
////
////		try {
////			message.resetPayload();
////			//sua kieu boolean cho update
////			success = projectService.saveProject(currentUser.getId(), projectRequest);
////			data = projectRequest;
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("Failed", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		// kiem tra viec thanh cong
////		if (!success) {
////			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE,	"Create Project Doesn't Successfull", false);
////		} else {
////			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Create Project Successfull",
////					true);
////		}
////		return message;
////	}
//
//	// tim kiem theo id
//	@GetMapping("/findProjectById")
//	public @ResponseBody Payload findProjectById(@RequestParam("project_id") long project_id) {
//		logger.info("Get Project By Id ... ");
//		try {
//			message.resetPayload();
//			data = projectService.getProjectById(project_id);
//		} catch (Exception e) {
//			logger.error("ERROR: Get connection error", e);
//			message.setPayLoad("Failed", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
//					false);
//			return message;
//		}
//		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Find Project By ID Successfull",
//				true);
//		return message;
//
//	}
//
////	// tim kiem theo ten
////	@PostMapping("/findProjectNameLike")
////	public @ResponseBody Payload findProjectNameLike(@RequestParam("projectName") String projectName,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("Find Project Like Name ... ");
////
////		try {
////			message.resetPayload();
////			data = projectService.findProjectNameLike(projectName, page, pageSize);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("Failed", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,	"Find Project Like Name Successfull", true);
////		if (!"".equals(data) || data != null) {
////			message.setPages(projectService.NumerRecordsProjectNameLike(projectName, pageSize));
////		}
////		return message;
////
////	}
//
////	// xoa project
////	@DeleteMapping("/deleteProject")
////	@PreAuthorize("hasRole('ROLE_ADMIN')")
////	public @ResponseBody Payload deleteProject(@RequestParam("project_id") long projectId,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("Delete Project ... ");
////
////		try {
////			message.resetPayload();
////			success = projectService.turnOffProject(projectId);
////			data = projectService.getAllProjects(page, pageSize, false);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("Failed", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		//kiem tra viec thanh cong
////		if (!success) {
////			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE, "Delete Project Doesn't Successfull", false);
////		}else {
////			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Delete Project Successfull", true);
////		}
////		
////		return message;
////	}
//
//	// lay danh sach project cua mot group
////	@GetMapping("/getProjectsOfGroup")
////	public @ResponseBody Payload getProjectsOfGroup(@RequestParam("group_id") String groupId,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("Get List Project Of Group... ");
////
////		try {
////			message.resetPayload();
////			data = projectService.getProjectsOfGroup(groupId, page, pageSize);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("Failed", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get List Project Of Group Successfull", true);
////		if (!"".equals(data) || data != null) {
////			message.setPages(projectService.NumerRecordsProjectsOfGroup(groupId, pageSize));
////		}
////		return message;
////	}
//
//	// lay quan ly cao nhat
////	@GetMapping("/getBigestManager")
////	public @ResponseBody Payload getBigestManager(@RequestParam("project_id") long project_id) {
////		logger.info("Get Bigest Manager... ");
////
////		try {
////			message.resetPayload();
////			data = projectService.getBigestManager(project_id);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("Failed", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Bigest Manager Successfull",
////				true);
////		return message;
////	}
////
////	// lya danh sach nhan vien trong project
////	@GetMapping("/getListEmployee")
////	public @ResponseBody Payload getListEmployee(@CurrentUser UserPrincipal currentUser, @RequestParam("project_id") long project_id,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("Get List Employee... ");
////
////		try {
////			message.resetPayload();
////			data = projectService.getListEmployee(currentUser.getId().longValue(), project_id, page, pageSize);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("Failed", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get List Employee Successfull", true);
////		if (!"".equals(data) || data != null) {
////			message.setPages(projectService.NumerRecordsListEmployee(project_id, pageSize));
////		}
////		return message;
////	}
//
//	// update project
////	@PutMapping("/updateProject")
////	@PreAuthorize("hasRole('ROLE_ADMIN')")
////	public @ResponseBody Payload updateProject(@Valid @RequestBody Project project,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("Update Project... ");
////
////		try {
////			message.resetPayload();
////			success = projectService.updateProject(project);
////			data = project;
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		
////		//kiem tra thanh cong
////		if (!success) {
////			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE, "Update Project Doesn't Successfull", false);
////		}else {
////			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Update Project Successfull", true);
////		}
////		return message;
////	}
//
////	// lay danh sach project off
////	@GetMapping("/getListProjectOutOfDate")
////	// @PreAuthorize("hasRole('ROLE_USER')")
////	public @ResponseBody Payload getListProjectOutOfDate(
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("Get List Project Out Of Date... ");
////
////		try {
////			message.resetPayload();
////			data = projectService.getListProjectOutOfDate(page, pageSize);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("Failed", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,	"Get List Project Out Of Date Successfull", true);
////		if (!"".equals(data) || data != null) {
////			message.setPages(projectService.NumerRecordsListProjectOutOfDate(pageSize));
////		}
////		return message;
////	}
////
////	// lay danh sach project theo nam thang
////	@GetMapping("/getProjectByMonthYear")
////	// @PreAuthorize("hasRole('ROLE_USER')")
////	public @ResponseBody Payload getProjectByMonthYear(@RequestParam("year") int year, @RequestParam("month") int month,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////		logger.info("Get Project By Month Year... ");
////
////		try {
////			message.resetPayload();
////			data = projectService.getProjectByMonthYear(month, year, page, pageSize);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("Failed", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE,	"Get Project By Month Year Successfull", true);
////		if (!"".equals(data) || data != null) {
////			message.setPages(projectService.NumerRecordsProjectByMonthYear(month, year,  pageSize));
////		}
////		return message;
////	}
//
////	// tim kiem project cua nhan vien tham gia vao
////	@GetMapping("/getProjectAllocatedIn")
////	public @ResponseBody Payload getProjectAllocatedIn(@CurrentUser UserPrincipal currentUser,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////
////		boolean hasUserRole = currentUser.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
////		logger.info(hasUserRole + " role");
////
////	//	hasUserRole = true;
////		try {
////			message.resetPayload();
////			data = projectService.getProjectAllocatedIn(currentUser.getId(), page, pageSize);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Allocations Successfull", true);
////		if (!"".equals(data) || data != null) {
////			message.setPages(projectService.NumerRecordsProjectAllocatedIn(currentUser.getId(), pageSize));
////		}
////		return message;
////	}
//
////	// lay cac project ma nhan vien do duoc phan cong
////	@GetMapping("/getProjectAllocateTo")
////	public @ResponseBody Payload getProjectAllocateTo(@CurrentUser UserPrincipal currentUser,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////
////		boolean hasUserRole = currentUser.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
////		logger.info(hasUserRole + " role");
////
////		try {
////			message.resetPayload();
////			data = projectService.getProjectAllocateTo(currentUser.getId(), page, pageSize);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Get Allocations Successfull", true);
////		if (!"".equals(data) || data != null) {
////			message.setPages(projectService.NumerRecordsProjectAllocateTo(currentUser.getId(), pageSize));
////		}
////		return message;
////	}
//
////	@PostMapping("/AddMemberToProject")
////	public @ResponseBody Payload AddMemberToProject(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ProjectMembers projectMember,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////
////		logger.info("Add Member To Project... ");
////		try {
////			message.resetPayload();
////			success = projectService.addMemberToProject(currentUser.getId(), projectMember);
////			data = projectService.getListEmployee(currentUser.getId().longValue(), projectMember.getProject_id(), page, pageSize);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		if (!success) {
////			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE, "Add Member To Project Doesn't Successfull", false);
////		}else {
////			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Add Member To Project Successfull", true);
////		}
////		return message;
////	}
//	
////	@DeleteMapping("/RemoveMemberOfProject")
////	public @ResponseBody Payload RemoveMemberOfProject(@CurrentUser UserPrincipal currentUser, @RequestParam long project_id, @RequestParam long projectMemberId,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////
////		logger.info("Remove Member in Project... ");
////		try {
////			message.resetPayload();
////			success = projectService.removeMemberOfProject(currentUser.getId(), projectMemberId);
////			data = projectService.getListEmployee(currentUser.getId().longValue(), project_id, page, pageSize);
////
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(), false);
////			return message;
////		}
////
////		if (!success) {
////			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE, "The Employee not exist or You don't have permitsion to delete", false);
////		}else {
////			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "Remove Member in Project Successfull", true);
////		}
////		return message;
////	}
//	
////	@PostMapping("/AddListMemberToProject")
////	public @ResponseBody Payload addListMemberToProject(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody List<Long> listEmployeeId, @RequestParam long project_id,
////			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize) {
////
////		logger.info("Add List Member To Project... ");
////		try {
////			message.resetPayload();
////			success = projectService.addListMemberToProject(currentUser.getId().longValue(), project_id, listEmployeeId);
////			data = projectService.getListEmployee(currentUser.getId().longValue(), project_id, page, pageSize);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("FAILED", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		if (!success) {
////			message.setPayLoad(data, AppConstants.STATUS_KO, AppConstants.SUCCESS_CODE, "Some Member Cann't Add To Project", false);
////		}else {
////			message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "All Member had been Add To Project", true);
////		}
////		return message;
////	}
//	
////	// tim kiem theo id
////	@GetMapping("/searchMultipleValue")
////	public @ResponseBody Payload searchMultipleValue(@RequestParam("group_id") String group_id, @RequestParam("name") String name,
////			@RequestParam("start_date") Date start_date, @RequestParam("end_date") Date end_date) {
////		logger.info("search Multiple Value ... ");
////		try {
////			message.resetPayload();
////			data = projectService.searchMultipleValue(group_id, name, start_date, end_date);
////		} catch (Exception e) {
////			logger.error("ERROR: Get connection error", e);
////			message.setPayLoad("Failed", AppConstants.STATUS_KO, AppConstants.FAILED_CODE, "ERROR: " + e.getMessage(),
////					false);
////			return message;
////		}
////		message.setPayLoad(data, AppConstants.STATUS_OK, AppConstants.SUCCESS_CODE, "search Multiple Value Successfull",
////				true);
////		return message;
////
////	}
//}