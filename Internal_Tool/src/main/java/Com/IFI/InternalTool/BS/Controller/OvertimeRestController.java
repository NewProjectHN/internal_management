package Com.IFI.InternalTool.BS.Controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Com.IFI.InternalTool.BS.Service.EmployeeService;
import Com.IFI.InternalTool.BS.Service.OvertimeService;
import Com.IFI.InternalTool.BS.Service.ProjectService;
import Com.IFI.InternalTool.BS.Service.VacationService;
import Com.IFI.InternalTool.DS.Model.Overtime;
import Com.IFI.InternalTool.DS.Model.Overtime_Approved;
import Com.IFI.InternalTool.DS.Model.Overtime_Log;
import Com.IFI.InternalTool.DS.Model.Overtime_Type;
import Com.IFI.InternalTool.DS.Model.ProjectManager;
import Com.IFI.InternalTool.DS.Model.SearchModel.OvertimeSearch;
import Com.IFI.InternalTool.Payloads.CountOvertimeResponse;
import Com.IFI.InternalTool.Payloads.CountOvertimeResponseManager;
import Com.IFI.InternalTool.Payloads.EmailSent;
import Com.IFI.InternalTool.Payloads.OvertimeCategory;
import Com.IFI.InternalTool.Payloads.Payload;

@RestController
@RequestMapping("/api")
public class OvertimeRestController {
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ProjectService projectService;
	@Autowired
	OvertimeService overtimeService;

	@Autowired
	VacationService vacationService;

	@GetMapping("/overtimes/{overtime_id}")
	public Overtime getOvertimeById(@PathVariable long overtime_id) {
		return overtimeService.getOvertimeById(overtime_id);
	}

	// get all overtime (employee page)
	@GetMapping("/overtimes/employee")
	public @ResponseBody Payload getOvertimeByEmp(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc, @RequestParam(required = false) Boolean is_approved,
			@RequestParam(required = false) List<Integer> status) throws ParseException {
		Payload message = new Payload();
		Long employee_id = employeeService.getEmployeeIdAuthenticated();
		List<Overtime> list = overtimeService.getAllOvertimeByEmp(employee_id, page, pageSize, sortedColumn, desc,
				is_approved, status);
		int size = list.size();
		if (size != 0) {
			try {
				for (Overtime o : list) {
					// List<String> approved_manager = new ArrayList<String>();
					// String disapproved_manager = null;
					String project_name = null;
					String overtime_type_name = null;
					String next_approved_manager = null;
					// List<Long> e = overtimeService.getApprovedIdByOvertimeId(o.getOvertime_id());
					// for(Long i:e) {
					// if(employeeService.getEmployeeById(i)!=null)
					// approved_manager.add(employeeService.getEmployeeById(i).getFullname());
					// }
					// if(overtimeService.getDisApproveIdByOvertimeId(o.getOvertime_id())!=null) {
					// disapproved_manager =
					// employeeService.getEmployeeById(overtimeService.getDisApproveIdByOvertimeId(o.getOvertime_id())).getFullname();
					// }
					project_name = projectService.getProjectById(o.getProject_id()).getName();
					overtime_type_name = overtimeService.getOvertimeTypeByID(o.getOvertime_type())
							.getOvertime_type_name();
					if (overtimeService.getManagerIdByEmpProAndStatus(employee_id, o.getProject_id(),
							o.getStatus()) != null) {
						next_approved_manager = employeeService.getEmployeeById(overtimeService
								.getManagerIdByEmpProAndStatus(employee_id, o.getProject_id(), o.getStatus()))
								.getFullname();
					}
					o.setName(project_name);
					o.setOvertime_type_name(overtime_type_name);
					// o.setApproved_manager(approved_manager);
					// o.setDisapproved_manager(disapproved_manager);
					o.setNext_approve_manager(next_approved_manager);
				}
				message.setMessage("Get overtime by employee successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(list);
				Long count = overtimeService.countAllOvertimeByEmp(status, is_approved, employee_id);
				int pages = (int) (count / pageSize);
				if (count % pageSize > 0) {
					pages++;
				}
				message.setPages(pages);
			} catch (Exception e) {
				message.setMessage(e.getMessage());
				message.setData(null);
			}

		} else {
			message.setMessage("Overtime by employee not found!");
			message.setCode("CODE OK!");
			message.setStatus("OK!");
		}

		return message;
	}

	// get overtime number by status (employee page)
	@GetMapping("/overtimes/employee/count")
	public CountOvertimeResponse countOvertimeByStatus() {
		Long employee_id = employeeService.getEmployeeIdAuthenticated();
		List<Long> count = overtimeService.countOvertimeByStatus(employee_id);
		CountOvertimeResponse countOvertimeResponse = new CountOvertimeResponse();
		countOvertimeResponse.setLastest(count.get(0));
		countOvertimeResponse.setApproving(count.get(1));
		countOvertimeResponse.setApproved(count.get(2));
		countOvertimeResponse.setDisapproved(count.get(3));
		return countOvertimeResponse;

	}

	// get all overtime type
	@GetMapping("/overtimesType")
	public List<Overtime_Type> getOvertimeType() {
		return overtimeService.getAllOvertimeType();
	}

	// add overtime
	@PostMapping("/overtimes")
	public @ResponseBody Payload saveOvertime(@RequestBody Overtime overtime) throws UnsupportedEncodingException {
		Payload message = new Payload();
		Long employee_id = employeeService.getEmployeeIdAuthenticated();
		List<Long> check = projectService.getProjectByEmp(employee_id);
		boolean allow_send = false;
		if (check.size() > 0) {
			try {
				List<ProjectManager> pm = projectService.getProjectManagerByEmp(employee_id, overtime.getProject_id());
				if (pm.size() > 0) {
					for (ProjectManager u : pm) {
						Date date2 = new Date();
						overtime.setEmployee_id(employee_id);
						overtime.setCreated_at(date2);
						overtime.setUpdated_at(date2);
						overtime.setStatus(1);
						overtime.setIs_approved(null);
						overtimeService.saveOvertime(overtime);
						String next_approve_manager = null;
						if (overtimeService.getManagerIdByEmpProAndStatus(overtime.getEmployee_id(),
								overtime.getProject_id(), overtime.getStatus()) != null) {
							next_approve_manager = employeeService
									.getEmployeeById(overtimeService.getManagerIdByEmpProAndStatus(
											overtime.getEmployee_id(), overtime.getProject_id(), overtime.getStatus()))
									.getFullname();
						}
						overtime.setNext_approve_manager(next_approve_manager);
						overtime.setName(projectService.getProjectById(overtime.getProject_id()).getName());
						overtime.setOvertime_type_name(overtimeService.getOvertimeTypeByID(overtime.getOvertime_type())
								.getOvertime_type_name());
						Overtime_Approved overtime_Approved = new Overtime_Approved();
						overtime_Approved.setOvertime_id(overtime.getOvertime_id());
						overtime_Approved.setManager_id(u.getManager_id());
						overtime_Approved.setPriority(u.getPriority());
						overtimeService.saveOvertimeApproved(overtime_Approved);
						List<Long> listManagerId = overtimeService.getManagerByOvertimeId(overtime.getOvertime_id());
						Overtime_Log ol = new Overtime_Log();
						for (Long a : listManagerId) {
							ol.setOvertime_id(overtime.getOvertime_id());
							ol.setNext_approve_id(a);
							overtimeService.saveOvertimeLog(ol);
						}
						allow_send = true;
						message.setMessage("Add overtime successfully");
						message.setCode("CODE OK!");
						message.setStatus("OK!");
						message.setData(overtime);
					}
				} else {
					message.setMessage("You dont belong to this project");
					message.setCode("Error");
					message.setStatus("Error");
				}
			} catch (Exception e) {
				message.setMessage(e.getMessage());
				message.setData(null);
			}
		} else {
			message.setMessage("You are the highest level management or You dont belong to any project");
			message.setCode("Error");
			message.setStatus("Error");
		}
		if (allow_send == true) {
			EmailSent emailSent1 = new EmailSent();
			emailSent1.setSend_to(employeeService.getEmployeeById2(employee_id).getEmail());
			System.out.println(employeeService.getEmployeeById2(employee_id));
			emailSent1.setName(employeeService.getEmployeeById2(employee_id).getFullname());
			emailSent1.setBody("You just made a new overtime request." + "\nRequest ID:" + overtime.getOvertime_id());
			emailSent1.setSubject("Request save successfully!");
			EmailSent emailSent2 = new EmailSent();
			emailSent2.setSend_to(
					employeeService.getEmployeeById2(overtimeService.getManagerIdByEmpProAndStatus(employee_id,
							overtime.getProject_id(), overtime.getStatus())).getEmail());
			emailSent2
					.setName(employeeService.getEmployeeById2(overtimeService.getManagerIdByEmpProAndStatus(employee_id,
							overtime.getProject_id(), overtime.getStatus())).getFullname());
			emailSent2.setBody(
					"You need to review a new overtime request." + "\nRequest ID:" + overtime.getOvertime_id());
			emailSent2.setSubject("Review new request!");
			overtimeService.sendEmail(emailSent1);
			overtimeService.sendEmail(emailSent2);

		}
		return message;
	}

	// edit overtime
	@PutMapping("/overtimes")
	public @ResponseBody Payload editOvertime(@RequestBody Overtime overtime) {
		Payload message = new Payload();
		Overtime o = overtimeService.getOvertimeById(overtime.getOvertime_id());
		if (o.getStatus() == 1) {
			try {
				List<ProjectManager> pm = projectService
						.getProjectManagerByEmp(employeeService.getEmployeeIdAuthenticated(), overtime.getProject_id());
				if (pm.size() > 0) {
					Date date2 = new java.util.Date();
					overtime.setCreated_at(o.getCreated_at());
					overtime.setStatus(o.getStatus());
					overtime.setUpdated_at(date2);
					overtime.setCreated_at(o.getCreated_at());
					overtime.setEmployee_id(employeeService.getEmployeeIdAuthenticated());
					overtimeService.saveOvertime(overtime);
					// get more info
					String next_approve_manager = null;
					if (overtimeService.getManagerIdByEmpProAndStatus(overtime.getEmployee_id(),
							overtime.getProject_id(), overtime.getStatus()) != null) {
						next_approve_manager = employeeService
								.getEmployeeById(overtimeService.getManagerIdByEmpProAndStatus(
										overtime.getEmployee_id(), overtime.getProject_id(), overtime.getStatus()))
								.getFullname();
					}
					overtime.setNext_approve_manager(next_approve_manager);// get next_approve manager
					overtime.setName(projectService.getProjectById(overtime.getProject_id()).getName());// get
																										// project
																										// name
					overtime.setOvertime_type_name(
							overtimeService.getOvertimeTypeByID(overtime.getOvertime_type()).getOvertime_type_name());
					message.setMessage("Edit project successfully");
					message.setCode("CODE OK!");
					message.setStatus("OK!");
					message.setData(overtime);
				} else {
					message.setMessage("You dont belong to this project");
					message.setCode("Error!");
					message.setStatus("Error");
				}

			} catch (Exception e) {
				message.setMessage(e.getMessage());
			}
		} else {
			message.setStatus("Error!");
			message.setCode("Error!");
			message.setMessage("Overtime is processing, You can not update");
		}
		return message;
	}

	// delete overtime by id
	@DeleteMapping("/overtimes/{overtime_id}")
	public @ResponseBody Payload deleteOvertime(@PathVariable long overtime_id) {
		Payload message = new Payload();
		Overtime overtime = overtimeService.getOvertimeById(overtime_id);
		if (overtime != null) {
			if (overtime.getStatus() == 1) {
				if (overtimeService.deleteOvertime(overtime_id)) {
					message.setMessage("Delete overtime successfully");
					message.setCode("CODE OK!");
					message.setStatus("OK!");
					message.setData("");
				}
			} else {
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setMessage("Overtime is processing, You can not delete");
			}
		} else {
			message.setMessage("Can not delete, overtime not found");
			message.setCode("CODE OK!");
			message.setStatus("OK!");
		}
		return message;
	}

	// get overtime ( manager/leader page)
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@GetMapping("/overtimes/manager")
	public @ResponseBody Payload getEmployeeOvertimeByManager(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc) {
		Payload message = new Payload();
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		try {
			List<Overtime> listOvertime = overtimeService.getAllOvertimeByEmp2(manager_id, page, pageSize, sortedColumn,
					desc);
			if (listOvertime.size() > 0) {
				for (Overtime overtime : listOvertime) {
//						List<String> approved_manager=new ArrayList<String>();
//						String disapproved_manager=null;
					String project_name = null;
					String employee_name = null;
					String overtime_type_name = null;
					String next_approve_manager = null;
//						List<Long> e=overtimeService.getApprovedIdByOvertimeId(overtime.getOvertime_id());
//						for(Long i:e) {
//							if(employeeService.getEmployeeById(i)!=null)
//							approved_manager.add(employeeService.getEmployeeById(i).getFullname());
//						}
					// get employee name
					employee_name = employeeService.getEmployeeById(overtime.getEmployee_id()).getFullname();
					// get project name
					project_name = projectService.getProjectById(overtime.getProject_id()).getName();
					// get overtime type name
					overtime_type_name = overtimeService.getOvertimeTypeByID(overtime.getOvertime_type())
							.getOvertime_type_name();
					overtime.setName(project_name);
					overtime.setFullname(employee_name);
					overtime.setOvertime_type_name(overtime_type_name);
//							overtime.setApproved_manager(approved_manager);
//							overtime.setDisapproved_manager(disapproved_manager);
					overtime.setNext_approve_manager(next_approve_manager);
				}
				Long count = overtimeService.countAllOvertimeByEmp2(manager_id);
				int pages = (int) (count / pageSize);
				if (count % pageSize > 0) {
					pages++;
				}
				message.setPages(pages);
				message.setMessage("Get overtime successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(listOvertime);
			} else {
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setMessage("No result!");
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;

	}

	// get number of overtime manager need approve
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@GetMapping("/overtimes/manager/count")
	public CountOvertimeResponseManager countOvertimeByStatus2() {
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		List<Long> count = overtimeService.countOvertimeByStatusManager(manager_id);
		CountOvertimeResponseManager countOvertimeResponseManager = new CountOvertimeResponseManager();
		countOvertimeResponseManager.setApproved(count.get(1));
		countOvertimeResponseManager.setNeed_approve(count.get(0));
		countOvertimeResponseManager.setDisapproved(count.get(2));
		return countOvertimeResponseManager;
	}

	// get all approved overtime by manager
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@GetMapping("/overtimes/manager/approved")
	public @ResponseBody Payload getApprovedOvertimeLogByMng(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc) {
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		List<Overtime> result = null;
		Payload message = new Payload();
		try {
			result = overtimeService.getApprovedIdOvertimeLogByManager(manager_id, page, pageSize, sortedColumn, desc);
			/*
			 * for(Long i:list) { result.add(overtimeService.getOvertimeById(i)); }
			 */
			for (Overtime o : result) {
//					List<String> approved_manager=new ArrayList<String>();
//					String disapproved_manager=null;
				String project_name = null;
				String employee_name = null;
				String overtime_type_name = null;
				String next_approve_manager = null;

//					List<Long> e=overtimeService.getApprovedIdByOvertimeId(o.getOvertime_id());
//					for(Long i:e) {
//					if(employeeService.getEmployeeById(i)!=null)
//					approved_manager.add(employeeService.getEmployeeById(i).getFullname());
//						}
				// get employee name
				employee_name = employeeService.getEmployeeById(o.getEmployee_id()).getFullname();
				// get project name
				project_name = projectService.getProjectById(o.getProject_id()).getName();
				// get overtime type name
				overtime_type_name = overtimeService.getOvertimeTypeByID(o.getOvertime_type()).getOvertime_type_name();

				o.setName(project_name);
				o.setFullname(employee_name);
				o.setOvertime_type_name(overtime_type_name);
//					o.setApproved_manager(approved_manager);
//					o.setDisapproved_manager(disapproved_manager);
				o.setNext_approve_manager(next_approve_manager);
			}
			Long count = overtimeService.countApprovedOvertimeByManager(manager_id);
			int pages = (int) (count / pageSize);
			if (count % pageSize > 0) {
				pages++;
			}
			message.setPages(pages);
			message.setCode("OK");
			message.setStatus("OK");
			message.setData(result);
			message.setMessage("Get Overtime successfully! ");
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	// get all overtime disapproved by manager
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@GetMapping("/overtimes/manager/disapproved")
	public @ResponseBody Payload getDisApprovedOvertimeLogByManager(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc) {
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		List<Overtime> result = null;
		Payload message = new Payload();
		try {
			result = overtimeService.getDisapproveIdOvertimeLogByManager(manager_id, page, pageSize, sortedColumn,
					desc);
			/*
			 * result.add(overtimeService.getOvertimeById(i)); }
			 */
			for (Overtime o : result) {
//						List<String> approved_manager=new ArrayList<String>();
//						String disapproved_manager=null;
				String project_name = null;
				String employee_name = null;
				String overtime_type_name = null;
				String next_approve_manager = null;
//						List<Long> e=overtimeService.getApprovedIdByOvertimeId(o.getOvertime_id());
//						for(Long i:e) {
//							if(employeeService.getEmployeeById(i)!=null)
//							approved_manager.add(employeeService.getEmployeeById(i).getFullname());
//						}
				// get employee name
				employee_name = employeeService.getEmployeeById(o.getEmployee_id()).getFullname();
				// get project name
				project_name = projectService.getProjectById(o.getProject_id()).getName();
				// get overtime type name
				overtime_type_name = overtimeService.getOvertimeTypeByID(o.getOvertime_type()).getOvertime_type_name();
				o.setName(project_name);
				o.setFullname(employee_name);
				o.setOvertime_type_name(overtime_type_name);
//						o.setApproved_manager(approved_manager);
//						o.setDisapproved_manager(disapproved_manager);
				o.setNext_approve_manager(next_approve_manager);
			}
			Long count = overtimeService.countDisapprovedOvertimeByManager(manager_id);
			int pages = (int) (count / pageSize);
			if (count % pageSize > 0) {
				pages++;
			}
			message.setPages(pages);
			message.setCode("OK");
			message.setStatus("OK");
			message.setData(result);
			message.setMessage("Get Overtime successfully! ");
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	// approve request
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@GetMapping("/overtimes/approve")
	public @ResponseBody Payload approveEmployeeRequest(@RequestParam("overtime_id") long overtime_id)
			throws UnsupportedEncodingException {
		Payload message = new Payload();
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		Overtime overtime = overtimeService.getOvertimeById(overtime_id);
		boolean allow_send = false;
		boolean allow_send2 = false;
		try {
			List<Long> test = overtimeService.getManagerByOvertimeId(overtime_id);

			for (Long a : test) {
				if (manager_id == a) {
					Overtime_Log overtime_Log = overtimeService.getOverTimeLogByOvertimeIdAndNextApprovedId(overtime_id,
							manager_id);
					int max = overtimeService.getMaxPriority(overtime_id);
					int my_prio = overtimeService.getPriority(manager_id, overtime_id);
					if (my_prio < max) {
						overtime.setStatus(my_prio + 1);
						overtime.setIs_approved(false);
						overtime_Log.setApproved_id(manager_id);
						overtimeService.saveOvertimeLog(overtime_Log);
						overtimeService.saveOvertime(overtime);
						allow_send2 = true;
					} else if (my_prio == max) {
						overtime.setStatus(max + 1);
						overtime.setIs_approved(true);
						overtimeService.saveOvertime(overtime);
						overtime_Log.setApproved_id(manager_id);
						overtimeService.saveOvertimeLog(overtime_Log);
						allow_send = true;
					}
					message.setCode("OK");
					message.setStatus("OK");
					message.setData(overtime);
					message.setMessage("Approve Overtime successfully! ");
					break;
				} else {
					message.setCode("Error");
					message.setStatus("Error");
					message.setMessage("You don't have permission to approve this overtime!");
				}
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}

		if (allow_send == true) {
			EmailSent emailSent1 = new EmailSent();
			emailSent1.setSend_to(employeeService.getEmployeeById2(manager_id).getEmail());
			System.out.println(employeeService.getEmployeeById2(manager_id));
			emailSent1.setName(employeeService.getEmployeeById2(manager_id).getFullname());
			emailSent1.setBody("You just approve a overtime request.\n" + "\nRequest ID: " + overtime.getOvertime_id()
					+ "\nThis email is intended to inform you about the activity of your account.");
			emailSent1.setSubject("Approve Request Notification!");
			EmailSent emailSent2 = new EmailSent();
			emailSent2.setSend_to(employeeService.getEmployeeById2(overtime.getEmployee_id()).getEmail());
			System.out.println(employeeService.getEmployeeById2(overtime.getEmployee_id()));
			emailSent2.setName(employeeService.getEmployeeById2(overtime.getEmployee_id()).getFullname());
			emailSent2.setBody(
					"Your request is approved by all manager/leader." + "\nRequest ID: " + overtime.getOvertime_id());
			emailSent2.setSubject("Request Approved Notification!");
			overtimeService.sendEmail(emailSent1);
			overtimeService.sendEmail(emailSent2);

		}

		if (allow_send2 == true) {
			EmailSent emailSent1 = new EmailSent();
			emailSent1.setSend_to(employeeService
					.getEmployeeById2(
							overtimeService.getNextManagerByPrio(overtime.getOvertime_id(), overtime.getStatus()))
					.getEmail());
			emailSent1.setName(employeeService
					.getEmployeeById2(
							overtimeService.getNextManagerByPrio(overtime.getOvertime_id(), overtime.getStatus()))
					.getFullname());
			emailSent1.setBody("You need to review a new overtime request.<br/>" + "<br/>Request ID: "
					+ overtime.getOvertime_id());
			emailSent1.setSubject("Review new request!");
			EmailSent emailSent2 = new EmailSent();
			emailSent2.setSend_to(employeeService.getEmployeeById2(overtime.getEmployee_id()).getEmail());
			emailSent2.setName(employeeService.getEmployeeById2(overtime.getEmployee_id()).getFullname());
			emailSent2.setBody("Your request is just approved by manager/leader: "
					+ employeeService.getEmployeeById2(manager_id).getFullname() + "<br/>Request ID: "
					+ overtime.getOvertime_id());
			emailSent2.setSubject("Request Approved Notification!");
			EmailSent emailSent3 = new EmailSent();
			emailSent3.setSend_to(employeeService.getEmployeeById2(manager_id).getEmail());
			emailSent3.setName(employeeService.getEmployeeById2(manager_id).getFullname());
			emailSent3.setBody("You just approve a overtime request." + "<br/>Request ID: " + overtime.getOvertime_id()
					+ "<br/>This email is intended to inform you about the activity of your account.");
			emailSent3.setSubject("Approve Request Notification!");
			vacationService.sendEmail(emailSent1);
			vacationService.sendEmail(emailSent2);
			vacationService.sendEmail(emailSent3);
		}
		return message;

	}

	// disapprove request
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@GetMapping("/overtimes/disapprove")
	public @ResponseBody Payload disapproveEmployeeRequest(@RequestParam("overtime_id") long overtime_id)
			throws UnsupportedEncodingException {
		Payload message = new Payload();
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		Overtime overtime = overtimeService.getOvertimeById(overtime_id);
		List<Long> test = overtimeService.getManagerByOvertimeId(overtime_id);
		boolean allow_send = false;
		try {
			for (Long a : test) {
				if (manager_id == a) {
					Overtime_Log overtime_Log = overtimeService.getOverTimeLogByOvertimeIdAndNextApprovedId(overtime_id,
							manager_id);
					overtime.setStatus(-1);
					overtime.setIs_approved(false);
					overtimeService.saveOvertime(overtime);
					overtime_Log.setDisapproved_id(manager_id);
					overtimeService.saveOvertimeLog(overtime_Log);
					message.setCode("OK");
					message.setStatus("OK");
					message.setData(overtime);
					message.setMessage("Disapprove Overtime successfully! ");
					allow_send = true;
					break;
				} else {
					message.setCode("Error");
					message.setStatus("Error");
					message.setMessage("You don't have permission to disapprove this overtime!");
				}
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		if (allow_send == true) {
			EmailSent emailSent1 = new EmailSent();
			emailSent1.setSend_to(employeeService.getEmployeeById2(manager_id).getEmail());
			emailSent1.setName(employeeService.getEmployeeById2(manager_id).getFullname());
			emailSent1
					.setBody("You just disapprove a overtime request.\n" + "\nRequest ID: " + overtime.getOvertime_id()
							+ "\nThis email is intended to inform you about the activity of your account.");
			emailSent1.setSubject("Disapprove Request Notification!");
			EmailSent emailSent2 = new EmailSent();
			emailSent2.setSend_to(employeeService.getEmployeeById2(overtime.getEmployee_id()).getEmail());
			emailSent2.setName(employeeService.getEmployeeById2(overtime.getEmployee_id()).getFullname());
			emailSent2.setBody("Your request is just disapproved by manager/leader: "
					+ employeeService.getEmployeeById2(manager_id).getFullname() + "\nRequest ID: "
					+ overtime.getOvertime_id());
			emailSent2.setSubject("Request Disapproved Notification!");
			overtimeService.sendEmail(emailSent1);
			overtimeService.sendEmail(emailSent2);
		}
		return message;

	}

	// search page manager/leader
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@PostMapping("/overtimes/manager/search/needapprove")
	public @ResponseBody Payload searchOvertimeManagerNeedApprove(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc, @RequestParam Boolean is_approved,
			@RequestBody OvertimeSearch overtimeSearch) {
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		Payload message = new Payload();
		try {
			List<Overtime> list = overtimeService.searchOvertimeManagerNeedApprove(manager_id, page, pageSize,
					sortedColumn, desc, is_approved, overtimeSearch);
			System.out.println(list);
			if (list.size() > 0) {
				for (Overtime o : list) {
//							List<String> approved_manager=new ArrayList<String>();
//							String disapproved_manager=null;
					String project_name = null;
					String employee_name = null;
					String overtime_type_name = null;
					String next_approve_manager = null;
//							List<Long> e=overtimeService.getApprovedIdByOvertimeId(o.getOvertime_id());
//							System.out.println(list);
//							for(Long i:e) {
//								if(employeeService.getEmployeeById(i)!=null)
//								approved_manager.add(employeeService.getEmployeeById(i).getFullname());
//							}
					// get employee name
					employee_name = employeeService.getEmployeeById(o.getEmployee_id()).getFullname();
					// get project name
					project_name = projectService.getProjectById(o.getProject_id()).getName();
					// get overtime type name
					overtime_type_name = overtimeService.getOvertimeTypeByID(o.getOvertime_type())
							.getOvertime_type_name();
					o.setName(project_name);
					o.setFullname(employee_name);
					o.setOvertime_type_name(overtime_type_name);
//							o.setApproved_manager(approved_manager);
//							o.setDisapproved_manager(disapproved_manager);
					o.setNext_approve_manager(next_approve_manager);
				}
				Long count = overtimeService.CountSearchOvertimeManagerNeedApprove(manager_id, is_approved,
						overtimeSearch);
				int pages = (int) (count / pageSize);
				if (count % pageSize > 0) {
					pages++;
				}
				message.setPages(pages);
				message.setMessage("Search overtime successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(list);
			} else {
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setMessage("No result!");
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	// search page manager/leader
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@PostMapping("/overtimes/manager/search/approved")
	public @ResponseBody Payload searchOvertimeManagerApproved(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc, @RequestParam Boolean is_approved,
			@RequestBody OvertimeSearch overtimeSearch) {
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		Payload message = new Payload();
		List<Overtime> list = overtimeService.searchOvertimeManagerApproved(manager_id, page, pageSize, sortedColumn,
				desc, is_approved, overtimeSearch);
		System.out.println(list);
		try {
			if (list.size() > 0) {
				for (Overtime o : list) {
//							List<String> approved_manager=new ArrayList<String>();
//							String disapproved_manager=null;
					String project_name = null;
					String employee_name = null;
					String overtime_type_name = null;
					String next_approve_manager = null;
//							List<Long> e=overtimeService.getApprovedIdByOvertimeId(o.getOvertime_id());
//							System.out.println(list);
//							for(Long i:e) {
//								if(employeeService.getEmployeeById(i)!=null)
//								approved_manager.add(employeeService.getEmployeeById(i).getFullname());
//							}
					// get employee name
					employee_name = employeeService.getEmployeeById(o.getEmployee_id()).getFullname();
					// get project name
					project_name = projectService.getProjectById(o.getProject_id()).getName();
					// get overtime type name
					overtime_type_name = overtimeService.getOvertimeTypeByID(o.getOvertime_type())
							.getOvertime_type_name();
					o.setName(project_name);
					o.setFullname(employee_name);
					o.setOvertime_type_name(overtime_type_name);
//							o.setApproved_manager(approved_manager);
//							o.setDisapproved_manager(disapproved_manager);
					o.setNext_approve_manager(next_approve_manager);
				}
				Long count = overtimeService.CountSearchOvertimeManagerApproved(manager_id, is_approved,
						overtimeSearch);
				int pages = (int) (count / pageSize);
				if (count % pageSize > 0) {
					pages++;
				}
				message.setPages(pages);
				message.setMessage("Search overtime successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(list);
			} else {
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setMessage("No result!");
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	// search page manager/leader
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@PostMapping("/overtimes/manager/search/disapproved")
	public @ResponseBody Payload searchOvertimeManagerDisApproved(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc, @RequestParam Boolean is_approved,
			@RequestBody OvertimeSearch overtimeSearch) {
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		Payload message = new Payload();
		List<Overtime> list = overtimeService.searchOvertimeManagerDisApproved(manager_id, page, pageSize, sortedColumn,
				desc, is_approved, overtimeSearch);
		System.out.println(list);
		try {
			if (list.size() > 0) {
				for (Overtime o : list) {
//							List<String> approved_manager=new ArrayList<String>();
//							String disapproved_manager=null;
					String project_name = null;
					String employee_name = null;
					String overtime_type_name = null;
					String next_approve_manager = null;
//							List<Long> e=overtimeService.getApprovedIdByOvertimeId(o.getOvertime_id());
//							System.out.println(list);
//							for(Long i:e) {
//								if(employeeService.getEmployeeById(i)!=null)
//								approved_manager.add(employeeService.getEmployeeById(i).getFullname());
//							}
					// get employee name
					employee_name = employeeService.getEmployeeById(o.getEmployee_id()).getFullname();
					// get project name
					project_name = projectService.getProjectById(o.getProject_id()).getName();
					// get overtime type name
					overtime_type_name = overtimeService.getOvertimeTypeByID(o.getOvertime_type())
							.getOvertime_type_name();
					o.setName(project_name);
					o.setFullname(employee_name);
					o.setOvertime_type_name(overtime_type_name);
//							o.setApproved_manager(approved_manager);
//							o.setDisapproved_manager(disapproved_manager);
					o.setNext_approve_manager(next_approve_manager);
				}
				Long count = overtimeService.CountSearchOvertimeManagerDisApproved(manager_id, is_approved,
						overtimeSearch);
				int pages = (int) (count / pageSize);
				if (count % pageSize > 0) {
					pages++;
				}
				message.setPages(pages);
				message.setMessage("Search overtime successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(list);
			} else {
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setMessage("No result!");
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	// search page employee
	@PostMapping("/overtimes/employee/search")
	public @ResponseBody Payload searchOvertimeEmployee(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc, @RequestParam Boolean is_approved,
			@RequestParam List<Integer> status, @RequestBody OvertimeSearch overtimeSearch) {
		Long employee_id = employeeService.getEmployeeIdAuthenticated();
		Payload message = new Payload();
		List<Overtime> list = overtimeService.searchOvertimeEmployee(employee_id, page, pageSize, sortedColumn, desc,
				is_approved, status, overtimeSearch);
		System.out.println(list);
		try {
			if (list.size() > 0) {
				for (Overtime o : list) {
//							List<String> approved_manager=new ArrayList<String>();
//							String disapproved_manager=null;
					String project_name = null;
					String overtime_type_name = null;
					String next_approve_manager = null;
//							List<Long> e=overtimeService.getApprovedIdByOvertimeId(o.getOvertime_id());
//							for(Long i:e) {
//								if(employeeService.getEmployeeById(i)!=null)
//								approved_manager.add(employeeService.getEmployeeById(i).getFullname());
//								
//							}
					// get disapprove manager name
//							if(overtimeService.getDisApproveIdByOvertimeId(o.getOvertime_id())!=null) {
//								disapproved_manager=employeeService.getEmployeeById(overtimeService.getDisApproveIdByOvertimeId(o.getOvertime_id())).getFullname();
//							}
					// get project name
					if (projectService.getProjectById(o.getProject_id()) != null) {
						project_name = projectService.getProjectById(o.getProject_id()).getName();
					}
					// get overtime type name
					if (overtimeService.getOvertimeTypeByID(o.getOvertime_type()) != null) {
						overtime_type_name = overtimeService.getOvertimeTypeByID(o.getOvertime_type())
								.getOvertime_type_name();
					}
					// get next approve manager name
					if (overtimeService.getManagerIdByEmpProAndStatus(employee_id, o.getProject_id(),
							o.getStatus()) != null) {
						next_approve_manager = employeeService.getEmployeeById(overtimeService
								.getManagerIdByEmpProAndStatus(employee_id, o.getProject_id(), o.getStatus()))
								.getFullname();
					}
					o.setName(project_name);
					o.setOvertime_type_name(overtime_type_name);
//							o.setApproved_manager(approved_manager);
//							o.setDisapproved_manager(disapproved_manager);
					o.setNext_approve_manager(next_approve_manager);
				}
				Long count = overtimeService.CountSearchEmployee(employee_id, is_approved, status, overtimeSearch);
				int pages = (int) (count / pageSize);
				if (count % pageSize > 0) {
					pages++;
				}
				message.setPages(pages);
				message.setMessage("Search overtime successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(list);
			} else {
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setMessage("No result!");
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@GetMapping("/overtimes/category")
	public ResponseEntity<OvertimeCategory> getOvertimeCategory() {
		OvertimeCategory category = new OvertimeCategory();
		try {
			category.setOvertimeTypeList((overtimeService.getAllOvertimeType()));
			category.setProjectListByEmployee(
					projectService.getProjectByEmp(employeeService.getEmployeeIdAuthenticated()));
			category.setProjectList(projectService.getAllProject());
			category.setProjectListByManager(
					vacationService.getProjectByManager(employeeService.getEmployeeIdAuthenticated()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<OvertimeCategory>(category, HttpStatus.OK);

	}

	@GetMapping("/time")
	public void setTime() {
		Date date = new Date();
		System.out.println(LocalDateTime.now().getHour());
	}

}