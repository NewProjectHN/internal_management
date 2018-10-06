package Com.IFI.InternalTool.BS.Controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import Com.IFI.InternalTool.DS.Model.ProjectManager;
import Com.IFI.InternalTool.DS.Model.Vacation;
import Com.IFI.InternalTool.DS.Model.Vacation_Approved;
import Com.IFI.InternalTool.DS.Model.Vacation_Log;
import Com.IFI.InternalTool.DS.Model.Vacation_Type;
import Com.IFI.InternalTool.DS.Model.SearchModel.VacationSearch;
import Com.IFI.InternalTool.Payloads.CountVacationResponse;
import Com.IFI.InternalTool.Payloads.CountVacationResponseMng;
import Com.IFI.InternalTool.Payloads.EmailSent;
import Com.IFI.InternalTool.Payloads.Payload;
import Com.IFI.InternalTool.Payloads.VacationCategory;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class VacationRestController {
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ProjectService projectService;
	@Autowired
	VacationService vacationService;
	@Autowired
	OvertimeService overtimeService;

	/*-----------Begin Vacation MainRestController--------*/
	// get vacation by id
	@GetMapping("/vacations/{vacation_id}")
	public Vacation getVacationById(@PathVariable("vacation_id") long vacation_id) {
		Vacation v = vacationService.getVacationById(vacation_id);
		return v;
	}

	// get all vacation (employee page)
	@GetMapping("/vacations/employee")
	public @ResponseBody Payload getVacationByEmp(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc, @RequestParam(required = false) Boolean is_approved,
			@RequestParam List<Integer> status) throws ParseException {
		Payload message = new Payload();

		Long employee_id = employeeService.getEmployeeIdAuthenticated();
		List<Vacation> list = null;
		list = vacationService.getAllVacationByEmp(employee_id, page, pageSize, sortedColumn, desc, is_approved,
				status);
		int size = list.size();
		if (size != 0) {
			try {
				for (Vacation v : list) {
					// List<String> approved_manager=new ArrayList<String>();
					// String disapproved_manager=null;
					String project_name = null;
					String vacation_type_name = null;
					String next_approve_manager = null;
					// List<Long> e=vacationService.getApprovedIdByVacationId(v.getVacation_id());
					// for(Long i:e) {
					// if(employeeService.getEmployeeById2(i)!=null)
					// approved_manager.add(employeeService.getEmployeeById2(i).getFullname());
					// }
					// get disapprove manager name
					// if(vacationService.getDisApproveIdByVacationId(v.getVacation_id())!=null) {
					// disapproved_manager=employeeService.getEmployeeById2(vacationService.getDisApproveIdByVacationId(v.getVacation_id())).getFullname();
					// }
					// get project name
					project_name = projectService.getProjectById(v.getProject_id()).getName();
					// get vacation type name
					vacation_type_name = vacationService.getVacationTypeById(v.getVacation_type())
							.getVacation_type_name();
					// get next approve manager name
					if (vacationService.getManagerIdByEmpProAndStatus(employee_id, v.getProject_id(),
							v.getStatus()) != null) {
						next_approve_manager = employeeService.getEmployeeById2(vacationService
								.getManagerIdByEmpProAndStatus(employee_id, v.getProject_id(), v.getStatus()))
								.getFullname();
					}
					v.setName(project_name);
					v.setVacation_type_name(vacation_type_name);
					// v.setApproved_manager(approved_manager);
					// v.setDisapproved_manager(disapproved_manager);
					v.setNext_approve_manager(next_approve_manager);
				}
				message.setMessage("Get vacations by employee successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(list);
				Long count = vacationService.countAllVacationByEmp(status, is_approved, employee_id);
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
			message.setMessage("Vacation by employee not found!");
			message.setCode("CODE OK!");
			message.setStatus("OK!");
		}

		return message;
	}

	// get vacation number by status (employee page)
	@GetMapping("/vacations/employee/count")
	public CountVacationResponse countVacationByStatus() {
		List<Long> count = vacationService.countVacationByStatusEmp(employeeService.getEmployeeIdAuthenticated());
		CountVacationResponse cvr = new CountVacationResponse();
		cvr.setLastest(count.get(0));
		cvr.setApproving(count.get(1));
		cvr.setApproved(count.get(2));
		cvr.setDisapproved(count.get(3));
		return cvr;

	}

	// get all vacation type
	@GetMapping("/vacationTypes")
	public List<Vacation_Type> getAllVacationType() {
		return vacationService.getAllVacationType();
	}

	// save vacation
	@PostMapping("/vacations")
	public @ResponseBody Payload saveVacation(@RequestBody Vacation vacation) throws UnsupportedEncodingException {
		Payload message = new Payload();
		Long employee_id = employeeService.getEmployeeIdAuthenticated();
		List<Long> check = projectService.getProjectByEmp(employee_id);
		boolean allow_send = false;
		if (check.size() > 0) {
			try {
				List<ProjectManager> pm = projectService.getProjectManagerByEmp(employee_id, vacation.getProject_id());
				if (pm.size() > 0) {
					for (ProjectManager u : pm) {

						Date date = new java.util.Date();
						vacation.setEmployee_id(employee_id);
						vacation.setCreated_at(date);
						vacation.setUpdated_at(date);
						vacation.setStatus(1);
						vacationService.saveVacation(vacation);

						// get more info
						String next_approve_manager = null;
						if (vacationService.getManagerIdByEmpProAndStatus(employee_id, vacation.getProject_id(),
								vacation.getStatus()) != null) {
							next_approve_manager = employeeService
									.getEmployeeById2(vacationService.getManagerIdByEmpProAndStatus(employee_id,
											vacation.getProject_id(), vacation.getStatus()))
									.getFullname();
						}
						vacation.setNext_approve_manager(next_approve_manager);// get next_approve manager
						vacation.setName(projectService.getProjectById(vacation.getProject_id()).getName());// get
																											// project
																											// name
						vacation.setVacation_type_name(vacationService.getVacationTypeById(vacation.getVacation_type())
								.getVacation_type_name());// vacation
															// type
															// name

						Vacation_Approved va = new Vacation_Approved();
						va.setVacation_id(vacation.getVacation_id());
						va.setManager_id(u.getManager_id());
						va.setPriority(u.getPriority());
						vacationService.saveVacationApproved(va);
						List<Long> listManagerId = vacationService.getManagerByVacationId(vacation.getVacation_id());
						Vacation_Log v = new Vacation_Log();
						for (Long a : listManagerId) {
							v.setVacation_id(vacation.getVacation_id());
							v.setNext_approve_id(a);
							vacationService.saveVacationLog(v);
						}
						allow_send = true;
						message.setMessage("Save vacation successfully");
						message.setCode("CODE OK!");
						message.setStatus("OK!");
						message.setData(vacation);

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
			emailSent1.setName(employeeService.getEmployeeById2(employee_id).getFullname());
			emailSent1.setBody("You just made a new leave request." + "<br/>Request ID:" + vacation.getVacation_id());
			emailSent1.setSubject("Request save successfully!");
			vacationService.sendEmail(emailSent1);
			if (employeeService.getEmployeeById2(vacationService.getManagerIdByEmpProAndStatus(employee_id,
					vacation.getProject_id(), vacation.getStatus())) != null) {
				EmailSent emailSent2 = new EmailSent();
				emailSent2.setSend_to(
						employeeService.getEmployeeById2(vacationService.getManagerIdByEmpProAndStatus(employee_id,
								vacation.getProject_id(), vacation.getStatus())).getEmail());
				emailSent2.setName(
						employeeService.getEmployeeById2(vacationService.getManagerIdByEmpProAndStatus(employee_id,
								vacation.getProject_id(), vacation.getStatus())).getFullname());
				emailSent2.setBody(
						"You need to review a new leave request." + "<br/>Request ID:" + vacation.getVacation_id());
				emailSent2.setSubject("Review new request!");
				vacationService.sendEmail(emailSent2);
			}
		}
		return message;
	}

	// edit vacation
	@PutMapping("/vacations")
	public @ResponseBody Payload editVacation(@RequestBody Vacation vacation) {
		Payload message = new Payload();
		Vacation v = vacationService.getVacationById(vacation.getVacation_id());
		if (v.getStatus() == 1) {
			try {
				List<ProjectManager> pm = projectService
						.getProjectManagerByEmp(employeeService.getEmployeeIdAuthenticated(), vacation.getProject_id());
				if (pm.size() > 0) {
					Date date = new java.util.Date();
					vacation.setCreated_at(v.getCreated_at());
					vacation.setStatus(v.getStatus());
					vacation.setUpdated_at(date);
					vacation.setEmployee_id(employeeService.getEmployeeIdAuthenticated());
					vacationService.saveVacation(vacation);
					// get more info
					String next_approve_manager = null;
					if (vacationService.getManagerIdByEmpProAndStatus(v.getEmployee_id(), v.getProject_id(),
							v.getStatus()) != null) {
						next_approve_manager = employeeService.getEmployeeById2(vacationService
								.getManagerIdByEmpProAndStatus(v.getEmployee_id(), v.getProject_id(), v.getStatus()))
								.getFullname();
					}
					vacation.setNext_approve_manager(next_approve_manager);// get next_approve manager
					vacation.setName(projectService.getProjectById(vacation.getProject_id()).getName());// get
																										// project
																										// name
					vacation.setVacation_type_name(
							vacationService.getVacationTypeById(vacation.getVacation_type()).getVacation_type_name());// vacation
																														// type
																														// name
					message.setMessage("Edit project successfully");
					message.setCode("CODE OK!");
					message.setStatus("OK!");
					message.setData(vacation);
				} else {
					message.setMessage("You dont belong to this project");
					message.setCode("Error!");
					message.setStatus("Error");
				}
			} catch (Exception e) {
				message.setMessage(e.getMessage());
			}

		}

		else {
			message.setStatus("Error!");
			message.setCode("Error!");
			message.setMessage("Vacation is processing, You can not update");
		}
		return message;
	}

	// delete vacation by id
	@DeleteMapping("/vacations/{vacation_id}")
	public @ResponseBody Payload deleteVacation(@PathVariable long vacation_id) {
		Payload message = new Payload();
		Vacation v = vacationService.getVacationById(vacation_id);
		long employee_id = employeeService.getEmployeeIdAuthenticated();

		if (v != null) {
			if (employee_id == v.getEmployee_id()) {
				if (v.getStatus() == 1) {
					if (vacationService.deleteVacation(vacation_id, employee_id)) {
						message.setMessage("Delete vacation successfully");
						message.setCode("CODE OK!");
						message.setStatus("OK!");
						message.setData("");
					}
				} else {
					message.setCode("CODE OK!");
					message.setStatus("OK!");
					message.setMessage("Vacation is processing, You can not delete");
				}
			} else {
				message.setCode("Error");
				message.setStatus("Error");
				message.setMessage("You dont have right to delete this vacation");
			}
		} else {
			message.setMessage("Can not delete, vacation not found");
			message.setCode("Error");
			message.setStatus("Error");
		}
		return message;
	}

	// get vacation to approve( manager/leader page)
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@GetMapping("/vacations/manager")
	public @ResponseBody Payload getEmployeeVacationByManager(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc) {
		Payload message = new Payload();
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		try {
			List<Vacation> listVacation = vacationService.getAllVacationByEmp2(manager_id, page, pageSize, sortedColumn,
					desc);
			if (listVacation.size() > 0) {
				for (Vacation v : listVacation) {
//					List<String> approved_manager=new ArrayList<String>();
//					String disapproved_manager=null;
					String project_name = null;
					String employee_name = null;
					String vacation_type_name = null;
					String next_approve_manager = null;
//					List<Long> e=vacationService.getApprovedIdByVacationId(v.getVacation_id());
//					for(Long i:e) {
//						if(employeeService.getEmployeeById2(i)!=null)
//						approved_manager.add(employeeService.getEmployeeById2(i).getFullname());
//					}
					// get employee name
					employee_name = employeeService.getEmployeeById2(v.getEmployee_id()).getFullname();
					// get project name
					project_name = projectService.getProjectById(v.getProject_id()).getName();
					// get vacation type name
					vacation_type_name = vacationService.getVacationTypeById(v.getVacation_type())
							.getVacation_type_name();
					v.setName(project_name);
					v.setFullname(employee_name);
					v.setVacation_type_name(vacation_type_name);
//					v.setApproved_manager(approved_manager);
//					v.setDisapproved_manager(disapproved_manager);
					v.setNext_approve_manager(next_approve_manager);
				}
				Long count = vacationService.countAllVacationByEmp2(manager_id);
				int pages = (int) (count / pageSize);
				if (count % pageSize > 0) {
					pages++;
				}
				message.setPages(pages);
				message.setMessage("Get vacation successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(listVacation);

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

	// get number of vacation manager need approve
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@GetMapping("/vacations/manager/count")
	public CountVacationResponseMng countVacationByStatus2() {
		List<Long> count1 = vacationService.countVacationByStatusMng(employeeService.getEmployeeIdAuthenticated());
		List<Long> count2 = overtimeService.countOvertimeByStatusManager(employeeService.getEmployeeIdAuthenticated());

		CountVacationResponseMng cvr = new CountVacationResponseMng();
		cvr.setApproved1(count1.get(1));
		cvr.setNeed_approve1(count1.get(0));
		cvr.setDisapproved1(count1.get(2));
		cvr.setApproved2(count2.get(1));
		cvr.setNeed_approve2(count2.get(0));
		cvr.setDisapproved2(count2.get(2));
		return cvr;
	}

	// get vacation approved by manager
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@GetMapping("/vacations/manager/approved")
	public @ResponseBody Payload getApprovedVacationLogByMng(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc) {
		Payload message = new Payload();
		List<Vacation> result = null;
		try {
			result = vacationService.getApprovedIdVacationLogByMng(employeeService.getEmployeeIdAuthenticated(), page,
					pageSize, sortedColumn, desc);
//				for(Long i:list) {
//					result.add(vacationService.getVacationById(i));
//				}
			for (Vacation v : result) {
				// List<String> approved_manager=new ArrayList<String>();
				// String disapproved_manager=null;
				String project_name = null;
				String employee_name = null;
				String vacation_type_name = null;
				String next_approve_manager = null;
				// List<Long> e=vacationService.getApprovedIdByVacationId(v.getVacation_id());
				// for(Long i:e) {
				// if(employeeService.getEmployeeById2(i)!=null)
				// approved_manager.add(employeeService.getEmployeeById2(i).getFullname());
				// }
				// get employee name
				employee_name = employeeService.getEmployeeById2(v.getEmployee_id()).getFullname();
				// get project name
				project_name = projectService.getProjectById(v.getProject_id()).getName();
				// get vacation type name
				vacation_type_name = vacationService.getVacationTypeById(v.getVacation_type()).getVacation_type_name();
				v.setName(project_name);
				v.setFullname(employee_name);
				v.setVacation_type_name(vacation_type_name);
				// v.setApproved_manager(approved_manager);
				// v.setDisapproved_manager(disapproved_manager);
				v.setNext_approve_manager(next_approve_manager);

			}
			Long count = vacationService.countApprovedVacationByMng(employeeService.getEmployeeIdAuthenticated());
			int pages = (int) (count / pageSize);
			if (count % pageSize > 0) {
				pages++;
			}
			message.setPages(pages);
			message.setCode("OK");
			message.setStatus("OK");
			message.setData(result);
			message.setMessage("Get Vacation successfully! ");
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	// get vacation disapproved by manager
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@GetMapping("/vacations/manager/disapproved")
	public @ResponseBody Payload getDisApprovedVacationLogByMng(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc) {
		Payload message = new Payload();
		List<Vacation> result = null;
		try {
			result = vacationService.getDisapproveIdVacationLogByMng(employeeService.getEmployeeIdAuthenticated(), page,
					pageSize, sortedColumn, desc);
//		for(Long i:list) {
//			result.add(vacationService.getVacationById(i));
//		}
			for (Vacation v : result) {
//			List<String> approved_manager=new ArrayList<String>();
//			String disapproved_manager=null;
				String project_name = null;
				String employee_name = null;
				String vacation_type_name = null;
				String next_approve_manager = null;
//			List<Long> e=vacationService.getApprovedIdByVacationId(v.getVacation_id());
//			for(Long i:e) {
//				if(employeeService.getEmployeeById2(i)!=null)
//				approved_manager.add(employeeService.getEmployeeById2(i).getFullname());
//			}
				// get employee name
				employee_name = employeeService.getEmployeeById2(v.getEmployee_id()).getFullname();
				// get project name
				project_name = projectService.getProjectById(v.getProject_id()).getName();
				// get vacation type name
				vacation_type_name = vacationService.getVacationTypeById(v.getVacation_type()).getVacation_type_name();
				v.setName(project_name);
				v.setFullname(employee_name);
				v.setVacation_type_name(vacation_type_name);
//			v.setApproved_manager(approved_manager);
//			v.setDisapproved_manager(disapproved_manager);
				v.setNext_approve_manager(next_approve_manager);
			}
			Long count = vacationService.countDisApprovedVacationByMng(employeeService.getEmployeeIdAuthenticated());
			int pages = (int) (count / pageSize);
			if (count % pageSize > 0) {
				pages++;
			}
			message.setPages(pages);
			message.setCode("OK");
			message.setStatus("OK");
			message.setData(result);
			message.setMessage("Get Vacation successfully! ");
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	// approve a request
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@GetMapping("/vacations/approve")
	public @ResponseBody Payload approveEmployeeRequest(@RequestParam("vacation_id") long vacation_id)
			throws UnsupportedEncodingException {
		Payload message = new Payload();
		boolean allow_send = false;
		boolean allow_send2 = false;
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		Vacation v = vacationService.getVacationById(vacation_id);
		try {
			List<Long> test = vacationService.getManagerByVacationId(vacation_id);
			for (Long a : test) {
				if (manager_id == a) {
					Vacation_Log v_log = vacationService.getVacationLogByVacationIdAndNextApproveId(vacation_id,
							manager_id);
					int max = vacationService.getMaxPriority(vacation_id);
					int my_prio = vacationService.getPriority(manager_id, vacation_id);
					if (my_prio < max) {
						v.setStatus(my_prio + 1);
						v.setIs_approved(false);
						v_log.setApproved_id(manager_id);
						vacationService.saveVacationLog(v_log);
						vacationService.saveVacation(v);
						allow_send2 = true;
					} else if (my_prio == max) {
						v.setStatus(max + 1);
						v.setIs_approved(true);
						vacationService.saveVacation(v);
						v_log.setApproved_id(manager_id);
						vacationService.saveVacationLog(v_log);
						allow_send = true;
					}
					message.setCode("OK");
					message.setStatus("OK");
					message.setData(v);
					message.setMessage("Approve Vacation successfully,email sent! ");
					break;
				} else {
					message.setCode("Error");
					message.setStatus("Error");
					message.setMessage("You don't have permission to approve this vacation!");
				}
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}

		if (allow_send == true) {
			EmailSent emailSent1 = new EmailSent();
			emailSent1.setSend_to(employeeService.getEmployeeById2(manager_id).getEmail());
			emailSent1.setName(employeeService.getEmployeeById2(manager_id).getFullname());
			emailSent1.setBody("You just approve a leave request.<br/>" + "<br/>Request ID: " + v.getVacation_id()
					+ "<br/>This email is intended to inform you about the activity of your account.");
			emailSent1.setSubject("Approve Request Notification!");
			EmailSent emailSent2 = new EmailSent();
			emailSent2.setSend_to(employeeService.getEmployeeById2(v.getEmployee_id()).getEmail());
			emailSent2.setName(employeeService.getEmployeeById2(v.getEmployee_id()).getFullname());
			emailSent2.setBody(
					"Your request is approved by all manager/leader." + "<br/>Request ID: " + v.getVacation_id());
			emailSent2.setSubject("Request Approved Notification!");
			vacationService.sendEmail(emailSent1);
			vacationService.sendEmail(emailSent2);
		}
		if (allow_send2 == true) {
			EmailSent emailSent1 = new EmailSent();
			emailSent1.setSend_to(employeeService
					.getEmployeeById2(vacationService.getNextManagerByPrio(v.getVacation_id(), v.getStatus()))
					.getEmail());
			emailSent1.setName(employeeService
					.getEmployeeById2(vacationService.getNextManagerByPrio(v.getVacation_id(), v.getStatus()))
					.getFullname());
			emailSent1
					.setBody("You need to review a new leave request.<br/>" + "<br/>Request ID: " + v.getVacation_id());
			emailSent1.setSubject("Review new request!");
			EmailSent emailSent2 = new EmailSent();
			emailSent2.setSend_to(employeeService.getEmployeeById2(v.getEmployee_id()).getEmail());
			emailSent2.setName(employeeService.getEmployeeById2(v.getEmployee_id()).getFullname());
			emailSent2.setBody("Your request is just approved by manager/leader: "
					+ employeeService.getEmployeeById2(manager_id).getFullname() + "<br/>Request ID: "
					+ v.getVacation_id());
			emailSent2.setSubject("Request Approved Notification!");
			EmailSent emailSent3 = new EmailSent();
			emailSent3.setSend_to(employeeService.getEmployeeById2(manager_id).getEmail());
			emailSent3.setName(employeeService.getEmployeeById2(manager_id).getFullname());
			emailSent3.setBody("You just approve a leave request." + "<br/>Request ID: " + v.getVacation_id()
					+ "<br/>This email is intended to inform you about the activity of your account.");
			emailSent3.setSubject("Approve Request Notification!");
			vacationService.sendEmail(emailSent1);
			vacationService.sendEmail(emailSent2);
			vacationService.sendEmail(emailSent3);
		}
		return message;
	}

	// disapprove a request
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@GetMapping("/vacations/disapprove")
	public @ResponseBody Payload disapproveEmployeeRequest(@RequestParam("vacation_id") long vacation_id)
			throws UnsupportedEncodingException {
		Payload message = new Payload();
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		Vacation v = vacationService.getVacationById(vacation_id);
		List<Long> test = vacationService.getManagerByVacationId(vacation_id);
		boolean allow_send = false;
		try {
			for (Long a : test) {
				if (manager_id == a) {
					Vacation_Log v_log = vacationService.getVacationLogByVacationIdAndNextApproveId(vacation_id,
							manager_id);
					v.setStatus(-1);
					v.setIs_approved(false);
					vacationService.saveVacation(v);
					v_log.setDisapproved_id(manager_id);
					vacationService.saveVacationLog(v_log);
					message.setCode("OK");
					message.setStatus("OK");
					message.setData(v);
					message.setMessage("Disapprove Vacation successfully,email sent! ");
					allow_send = true;
					break;
				} else {
					message.setCode("Error");
					message.setStatus("Error");
					message.setMessage("You don't have permission to disapprove this vacation!");
				}

			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		if (allow_send == true) {
			EmailSent emailSent1 = new EmailSent();
			emailSent1.setSend_to(employeeService.getEmployeeById2(manager_id).getEmail());
			emailSent1.setName(employeeService.getEmployeeById2(manager_id).getFullname());
			emailSent1.setBody("You just disapprove a leave request.<br/>" + "<br/>Request ID: " + v.getVacation_id()
					+ "<br/>This email is intended to inform you about the activity of your account.");
			emailSent1.setSubject("Disapprove Request Notification!");
			EmailSent emailSent2 = new EmailSent();
			emailSent2.setSend_to(employeeService.getEmployeeById2(v.getEmployee_id()).getEmail());
			emailSent2.setName(employeeService.getEmployeeById2(v.getEmployee_id()).getFullname());
			emailSent2.setBody("Your request is just disapproved by manager/leader: "
					+ employeeService.getEmployeeById2(manager_id).getFullname() + "<br/>Request ID: "
					+ v.getVacation_id());
			emailSent2.setSubject("Request Disapproved Notification!");
			vacationService.sendEmail(emailSent1);
			vacationService.sendEmail(emailSent2);
		}
		return message;

	}

	// search need approve manager/leader
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@PostMapping("/vacations/search/needapprove")
	public @ResponseBody Payload searchVacationNeedApprove(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc, @RequestParam Boolean is_approved,
			@RequestBody VacationSearch vacationSearch) {
		Payload message = new Payload();
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		try {
			List<Vacation> list = vacationService.searchVacationNeedApprove(manager_id, page, pageSize, sortedColumn,
					desc, is_approved, vacationSearch);
			if (list.size() > 0) {
				for (Vacation v : list) {
//				List<String> approved_manager=new ArrayList<String>();
//				String disapproved_manager=null;
					String project_name = null;
					String employee_name = null;
					String vacation_type_name = null;
					String next_approve_manager = null;
//				List<Long> e=vacationService.getApprovedIdByVacationId(v.getVacation_id());
//				for(Long i:e) {
//					if(employeeService.getEmployeeById2(i)!=null)
//					approved_manager.add(employeeService.getEmployeeById2(i).getFullname());
//				}
					// get employee name
					employee_name = employeeService.getEmployeeById2(v.getEmployee_id()).getFullname();
					// get project name
					project_name = projectService.getProjectById(v.getProject_id()).getName();
					// get vacation type name
					vacation_type_name = vacationService.getVacationTypeById(v.getVacation_type())
							.getVacation_type_name();
					v.setName(project_name);
					v.setFullname(employee_name);
					v.setVacation_type_name(vacation_type_name);
//				v.setApproved_manager(approved_manager);
//				v.setDisapproved_manager(disapproved_manager);
					v.setNext_approve_manager(next_approve_manager);
				}
				Long count = vacationService.CountSearchVacationNeedApprove(manager_id, is_approved, vacationSearch);
				int pages = (int) (count / pageSize);
				if (count % pageSize > 0) {
					pages++;
				}
				message.setPages(pages);
				message.setMessage("Search vacation successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(list);
			}

			else {
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setMessage("No result!");
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	// search approved manager/leader
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@PostMapping("/vacations/search/approved")
	public @ResponseBody Payload searchVacationApproved(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc, @RequestParam Boolean is_approved,
			@RequestBody VacationSearch vacationSearch) {
		Payload message = new Payload();
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		List<Vacation> list = vacationService.searchVacationApproved(manager_id, page, pageSize, sortedColumn, desc,
				is_approved, vacationSearch);
		try {
			if (list.size() > 0) {
				for (Vacation v : list) {
//					List<String> approved_manager=new ArrayList<String>();
//					String disapproved_manager=null;
					String project_name = null;
					String employee_name = null;
					String vacation_type_name = null;
					String next_approve_manager = null;
//					List<Long> e=vacationService.getApprovedIdByVacationId(v.getVacation_id());
//					for(Long i:e) {
//						if(employeeService.getEmployeeById2(i)!=null)
//						approved_manager.add(employeeService.getEmployeeById2(i).getFullname());
//					}
					// get employee name
					employee_name = employeeService.getEmployeeById2(v.getEmployee_id()).getFullname();
					// get project name
					project_name = projectService.getProjectById(v.getProject_id()).getName();
					// get vacation type name
					vacation_type_name = vacationService.getVacationTypeById(v.getVacation_type())
							.getVacation_type_name();
					v.setName(project_name);
					v.setFullname(employee_name);
					v.setVacation_type_name(vacation_type_name);
//					v.setApproved_manager(approved_manager);
//					v.setDisapproved_manager(disapproved_manager);
					v.setNext_approve_manager(next_approve_manager);
				}
				Long count = vacationService.CountSearchVacationApproved(manager_id, is_approved, vacationSearch);
				int pages = (int) (count / pageSize);
				if (count % pageSize > 0) {
					pages++;
				}
				message.setPages(pages);
				message.setMessage("Search vacation successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(list);
			}

			else {
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setMessage("No result!");
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	// search disapproved manager/leader
	@PreAuthorize("hasRole('LEADER_A') OR hasRole('LEADER_B') OR hasRole('LEADER_C')")
	@PostMapping("/vacations/search/disapproved")
	public @ResponseBody Payload searchVacationDisApproved(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc, @RequestParam Boolean is_approved,
			@RequestBody VacationSearch vacationSearch) {
		Payload message = new Payload();
		Long manager_id = employeeService.getEmployeeIdAuthenticated();
		List<Vacation> list = vacationService.searchVacationDisApproved(manager_id, page, pageSize, sortedColumn, desc,
				is_approved, vacationSearch);
		try {
			if (list.size() > 0) {
				for (Vacation v : list) {
//							List<String> approved_manager=new ArrayList<String>();
//							String disapproved_manager=null;
					String project_name = null;
					String employee_name = null;
					String vacation_type_name = null;
					String next_approve_manager = null;
//							List<Long> e=vacationService.getApprovedIdByVacationId(v.getVacation_id());
//							for(Long i:e) {
//								if(employeeService.getEmployeeById2(i)!=null)
//								approved_manager.add(employeeService.getEmployeeById2(i).getFullname());
//							}
					// get employee name
					employee_name = employeeService.getEmployeeById2(v.getEmployee_id()).getFullname();
					// get project name
					project_name = projectService.getProjectById(v.getProject_id()).getName();
					// get vacation type name
					vacation_type_name = vacationService.getVacationTypeById(v.getVacation_type())
							.getVacation_type_name();
					v.setName(project_name);
					v.setFullname(employee_name);
					v.setVacation_type_name(vacation_type_name);
//							v.setApproved_manager(approved_manager);
//							v.setDisapproved_manager(disapproved_manager);
					v.setNext_approve_manager(next_approve_manager);
				}
				Long count = vacationService.CountSearchVacationDisApproved(manager_id, is_approved, vacationSearch);
				int pages = (int) (count / pageSize);
				if (count % pageSize > 0) {
					pages++;
				}
				message.setPages(pages);
				message.setMessage("Search vacation successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(list);
			}

			else {
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
	@PostMapping("/vacations/searchv2")
	public @ResponseBody Payload searchVacationP2(@RequestParam("page") int page,
			@RequestParam("pageSize") int pageSize, @RequestParam(required = false) String sortedColumn,
			@RequestParam(required = false) Boolean desc, @RequestParam Boolean is_approved,
			@RequestParam List<Integer> status, @RequestBody VacationSearch vacationSearch) {
		Payload message = new Payload();
		Long employee_id = employeeService.getEmployeeIdAuthenticated();
		List<Vacation> list = vacationService.searchVacationP2(employee_id, page, pageSize, sortedColumn, desc,
				is_approved, status, vacationSearch);
		try {
			if (list.size() > 0) {
				for (Vacation v : list) {
//				List<String> approved_manager=new ArrayList<String>();
//				String disapproved_manager=null;
					String project_name = null;
					String vacation_type_name = null;
					String next_approve_manager = null;
//				List<Long> e=vacationService.getApprovedIdByVacationId(v.getVacation_id());
//				for(Long i:e) {
//					if(employeeService.getEmployeeById2(i)!=null)
//					approved_manager.add(employeeService.getEmployeeById2(i).getFullname());
//					else break;
//				}
					// get disapprove manager name
//				if(vacationService.getDisApproveIdByVacationId(v.getVacation_id())!=null) {
//					disapproved_manager=employeeService.getEmployeeById2(vacationService.getDisApproveIdByVacationId(v.getVacation_id())).getFullname();
//				}
					// get project name
					if (projectService.getProjectById(v.getProject_id()) != null) {
						project_name = projectService.getProjectById(v.getProject_id()).getName();
					}
					// get vacation type name
					if (vacationService.getVacationTypeById(v.getVacation_type()) != null) {
						vacation_type_name = vacationService.getVacationTypeById(v.getVacation_type())
								.getVacation_type_name();
					}
					// get next approve manager name
					if (vacationService.getManagerIdByEmpProAndStatus(employee_id, v.getProject_id(),
							v.getStatus()) != null) {
						next_approve_manager = employeeService.getEmployeeById2(vacationService
								.getManagerIdByEmpProAndStatus(employee_id, v.getProject_id(), v.getStatus()))
								.getFullname();
					}
					v.setName(project_name);
					v.setVacation_type_name(vacation_type_name);
//				v.setApproved_manager(approved_manager);
//				v.setDisapproved_manager(disapproved_manager);
					v.setNext_approve_manager(next_approve_manager);
				}
				Long count = vacationService.CountSearchVacationP2(employee_id, is_approved, status, vacationSearch);
				int pages = (int) (count / pageSize);
				if (count % pageSize > 0) {
					pages++;
				}
				message.setPages(pages);
				message.setMessage("Search vacation successfully");
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setData(list);
			}

			else {
				message.setCode("CODE OK!");
				message.setStatus("OK!");
				message.setMessage("No result!");
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}
	// get project by manager
//	@GetMapping("/projects/manager")
//	public @ResponseBody List<Project> getProjectByManager() {
//		List<Project> result=new ArrayList<Project>();
//		List<Long> list=vacationService.getProjectByManager(employeeService.getEmployeeIdAuthenticated());
//		for(Long i :list) {
//			result.add(projectService.getProjectById(i));
//		}
//		return result;
//	}
//			

	@GetMapping("/vacations/category")
	public ResponseEntity<VacationCategory> getVacationCategory() {
		VacationCategory category = new VacationCategory();
		try {
			category.setVacationTypeList(vacationService.getAllVacationType());
			category.setProjectListByEmployee(
					projectService.getProjectByEmp(employeeService.getEmployeeIdAuthenticated()));
			category.setProjectList(projectService.getAllProject());
			category.setProjectListByManager(
					vacationService.getProjectByManager(employeeService.getEmployeeIdAuthenticated()));
		} catch (Exception e) {
		}
		return new ResponseEntity<VacationCategory>(category, HttpStatus.OK);
	}

	/*-----------End Vacation MainRestController--------*/

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		try {
			Date d = sdf.parse("02/08/2018 18:00 pm");
			System.out.println(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}