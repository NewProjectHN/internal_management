package Com.IFI.InternalTool.DS.DAO;

import org.springframework.stereotype.Repository;
import java.util.List;

import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.DS.Model.Roles;

@Repository
public interface EmployeeDAO {
	// lay danh sach tat ca nhan vien
	List<Employee> getAllEmployees(final int page, final int pageSize);

	// save employees
	Boolean saveEmployee(final Employee employee);

	Boolean EditEmployee(final Employee employee);
//	// xoa nhan vien
//	public Boolean deleteEmployee(final long employee_id);

	// get employeess by ID
	Employee getEmployeeById(final long employee_id);

	//
	List<Long> getEmployeeByManager(final long manager_id);

	// find Employess with Name Like
	List<Employee> findEmployeeNameLike(final String name, int page, int pageSize);

	Long NumRecordsEmployeeNameLike(final String name);

//	// find employess by groups id
//	public List<Employee> findEmployeeByGroupId(final String group_id, final int page, final int pageSize);

	Long NumRecordsEmployeeInGroup(final String group_id);

//	List<Employee> getListSubEmployees(long employee_id);

//	// tim kiem danh sanh nhan vien duoc phan cong vao 1 project
//	List<Employee> getListEmployeeInProject(long project_id, int page, int pageSize);

//	Long NumRecordsEmployeeInProject(long project_id);

//	List<Employee> getListEmployeeNotInProject(final long employee_id, final long project_id, int page, int pageSize);

//	Long NumRecordsEmployeeNotInProject(final long employee_id, final long project_id);

	Roles getRolesByID(final int role_id);
	

}