package Com.IFI.InternalTool.BS.Service;

import Com.IFI.InternalTool.DS.Model.Employee;

public interface EmployeeService {

	Employee getEmployeeById(final long employee_id);
	Employee getEmployeeById2(final long employee_id);
	Long getEmployeeIdAuthenticated();

	Employee getEmployeeByName(String username);
}