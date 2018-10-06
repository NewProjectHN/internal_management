package Com.IFI.InternalTool.BS.Service;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.DS.Model.Project;
import Com.IFI.InternalTool.DS.Model.ProjectManager;

public interface ProjectService {
	// lay tat ca project
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	List<Project> getAllProjects(int page, int pageSize, final boolean isDESC);
	
	int NumerRecordsGetAllProjects(final int pageSize);
	
	List<Project> getAllProject();

//	// luu project
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	public boolean saveProject(long managerId, Project project);

//	// xoa project
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	boolean turnOffProject(long project_id);

	// lay theo id
	Project getProjectById(Long project_id);

//	// lay danh sach project theo group
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	List<Project> getProjectsOfGroup(String group_id, int page, int pageSize);
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	int NumerRecordsProjectsOfGroup(String group_id, int pageSize);
//	
//	// tim kiem theo ten
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	List<Project> findProjectNameLike(String projectName, int page, int pageSize);
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	int NumerRecordsProjectNameLike(String projectName, int pageSize);

	// tim kiem id manager cao nhat
	Employee getBigestManager(long project_id);

//	// tim kiem danh sach nhan vien trong project
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	List<Employee> getListEmployee(final long leaderId, long projectId, int page, int pageSize);
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	int NumerRecordsListEmployee(long project_id, int pageSize);
	
	// update project
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	boolean updateProject(final Project project);
	
//	//them nhan vien vao project
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	Boolean addMemberToProject(final long currentEmployeeId, final ProjectMembers projectMember);
//	// them nhieu nhan vien vao project
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	Boolean addListMemberToProject(final long currentEmployeeId, final long projectId, final List<Long> listEmployeeId);
//
//	//xoa nhan vien khoi project
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	Boolean removeMemberOfProject(final long currentEmployeeId, final long projectMemberId);

	// tim kiem cac project da off
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	List<Project> getListProjectOutOfDate(int page, int pageSize);
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	int NumerRecordsListProjectOutOfDate(int pageSize);

	// tim kiem theo nam thang
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	List<Project> getProjectByMonthYear(int month, int year, int page, int pageSize);
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	int NumerRecordsProjectByMonthYear(int month, int year, int pageSize);

	// lay project nhan vien duoc phan cong vao
//	List<Project> getProjectAllocatedIn(long employee_id, int page, int pageSize);
//	int NumerRecordsProjectAllocatedIn(long employee_id, int pageSize);

	// lay cac project ma nhan vien do quan ly
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	List<Project> getProjectAllocateTo(long employee_id, int page, int pageSize);
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	int NumerRecordsProjectAllocateTo(long employee_id, int pageSize);
	
	//tim kiem nhieu tieu chi
//	@PreAuthorize("hasRole('ROLE_LEADER_A') OR hasRole('ROLE_LEADER_B') OR hasRole('ROLE_LEADER_C') OR hasRole('ROLE_ADMIN')")
//	List<Project> searchMultipleValue(final String groupId, final String projectName, final Date startDate, final Date endDate);
		

	List<ProjectManager> getProjectManagerByEmp(long employee_id, long project_id);

	List<Long> getProjectByEmp(long employee_id);
}