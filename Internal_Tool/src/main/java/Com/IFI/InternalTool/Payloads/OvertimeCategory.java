package Com.IFI.InternalTool.Payloads;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Overtime_Type;
import Com.IFI.InternalTool.DS.Model.Project;

public class OvertimeCategory {
	private List<Overtime_Type> overtimeTypeList;
	private List<Long> projectListByEmployee;
	private List<Project> projectList;
	private List<Long> projectListByManager;
	public List<Long> getProjectListByManager() {
		return projectListByManager;
	}
	public void setProjectListByManager(List<Long> projectListByManager) {
		this.projectListByManager = projectListByManager;
	}
	public List<Overtime_Type> getOvertimeTypeList() {
		return overtimeTypeList;
	}
	public void setOvertimeTypeList(List<Overtime_Type> overtimeTypeList) {
		this.overtimeTypeList = overtimeTypeList;
	}
	public List<Long> getProjectListByEmployee() {
		return projectListByEmployee;
	}
	public void setProjectListByEmployee(List<Long> projectListByEmployee) {
		this.projectListByEmployee = projectListByEmployee;
	}
	public List<Project> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}
	
}
