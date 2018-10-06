//package Com.IFI.InternalTool.DS.DAO;
//
//import java.util.List;
//
//import Com.IFI.InternalTool.DS.Model.Employee;
//import Com.IFI.InternalTool.DS.Model.Project;
//import Com.IFI.InternalTool.DS.Model.ProjectMembers;
//
//public interface ProjectMembersDAO {
//
//	Boolean isMembersOfProject(final long employee_id, final long project_id);
//
//	Boolean addMemberToProject(final ProjectMembers projectMember);
//
//	Boolean removeMemberOfProject(final long projectMemberId);
//
//	List<Long> listEmPloyeesIdInProject(final long project_id);
//
//	// xoa tat ca member cua mot project
//	boolean deleteAllMemberInProject(final long projectId);
//
//	// lay mot project member theo id
//	ProjectMembers getProjectMemberById(final long projectMemberId);
//
//	// xoa project member theo employee id
//	boolean deleteProjectMemberByEmployeeId(final long employeeId);
//
//	//tim kiem ProjectMember theo project_id va employee
//	ProjectMembers getProjectMemberByProIdAndEmpId(final long projectId, final long employeeId);
//
//	//update total allocation plan
//	boolean updateTotalAllocationPlan(final ProjectMembers projectMember);
//
//	// lay danh sach nhan vien trong mot project
//	List<Long> getListEmployeeIdInProject(long leaderId, long projectId, int page, int pageSize);
//
//	//lay danh sach project theo nhan vien
//	List<Long> getListProjectIdOfEmployee(final long employeeId, int page, int pageSize);
//}