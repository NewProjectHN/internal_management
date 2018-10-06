package Com.IFI.InternalTool.BS.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.ProjectService;
import Com.IFI.InternalTool.DS.DAO.ProjectDAO;
import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;
import Com.IFI.InternalTool.Utils.Business;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectDAO projectDAO;
	@Autowired
	EmployeeServiceImpl employeeServiceImpl;

	@Override
	public int NumerRecordsGetAllProjects(int pageSize) {
		return Business.getTotalPage(projectDAO.NumerRecordsGetAllProjects(), pageSize);
	}
	
	@Override
	public List<Project> getAllProject() {
		return projectDAO.getAllProject();
	}

	@Override
	public Project getProjectById(Long project_id) {
		Project project = projectDAO.getProjectById(project_id);
		// check cac truong hop khong tim thay
		if (project != null) {
			Employee employee = employeeServiceImpl.getEmployeeById(project.getManager_id());
			if (employee != null) {
				project.setManager_Name(employee.getFullname());
			}
		}
		
		return project;
		
	}

	@Override
	public List<ProjectManager> getProjectManagerByEmp(long employee_id, long project_id) {
		return projectDAO.getProjectManagerByEmp(employee_id, project_id);
	}

	@Override
	public List<Long> getProjectByEmp(long employee_id) {
		return projectDAO.getProjectByEmp(employee_id);
	}

	@Override
	public Employee getBigestManager(long project_id) {
		return employeeServiceImpl.getEmployeeById(projectDAO.getBigestManagerId(project_id));
	}

}