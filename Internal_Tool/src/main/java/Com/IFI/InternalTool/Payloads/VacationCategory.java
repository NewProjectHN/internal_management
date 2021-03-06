package Com.IFI.InternalTool.Payloads;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.Vacation_Type;

public class VacationCategory {
	private List<Vacation_Type> vacationTypeList;
	private List<Long> projectListByEmployee;
	private List<Project> projectList;
	private List<Long> projectListByManager;
	public List<Long> getProjectListByManager() {
		return projectListByManager;
	}
	public void setProjectListByManager(List<Long> projectListByManager) {
		this.projectListByManager = projectListByManager;
	}
	public List<Project> getProjectList() {
		return projectList;
	}
	public VacationCategory() {
		super();
	}
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}
	public List<Vacation_Type> getVacationTypeList() {
		return vacationTypeList;
	}
	public void setVacationTypeList(List<Vacation_Type> vacationTypeList) {
		this.vacationTypeList = vacationTypeList;
	}
	public List<Long> getProjectListByEmployee() {
		return projectListByEmployee;
	}
	public void setProjectListByEmployee(List<Long> projectList) {
		this.projectListByEmployee = projectList;
	}
	
	
}
