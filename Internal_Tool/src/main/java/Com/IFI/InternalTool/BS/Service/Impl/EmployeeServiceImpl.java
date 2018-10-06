package Com.IFI.InternalTool.BS.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.EmployeeService;
import Com.IFI.InternalTool.DS.DAO.EmployeeDAO;
import Com.IFI.InternalTool.DS.DAO.UserDAO;
import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.Security.UserPrincipal;

@Service("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public Employee getEmployeeById(long employee_id) {
		Employee emp = employeeDAO.getEmployeeById(employee_id);
		if (emp != null) {
			emp.setRole_name(emp.getRole().getName().toString());
		}

		return emp;
	}

	@Override
	public Long getEmployeeIdAuthenticated() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		Long id = user.getId();
		return id;
	}



	@Override
	public Employee getEmployeeByName(String username) {
		return userDAO.findByUsername(username);
	}

	@Override
	public Employee getEmployeeById2(long employee_id) {
		return employeeDAO.getEmployeeById(employee_id);
	}

}