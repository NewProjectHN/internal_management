package Com.IFI.InternalTool.DS.Model;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="overtime")
public class Overtime {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="overtime_id")
	public long overtime_id;
	
	@Column(name="employee_id")
	public  long employee_id;
	
	@Column(name="project_id")
	public long project_id;
	@JsonFormat(pattern="dd/MM/yyyy hh:mm a")
	@Column(name="from_hour")
	public Date from_hour;
	@JsonFormat(pattern="dd/MM/yyyy hh:mm a")
	@Column(name="to_hour")
	public Date to_hour;
	
	@Column(name="overtime_type")
	public int overtime_type;
	
	@Column(name="description")
	public String description;
	
	@Column(name="status")
	public int status;
	@JsonFormat(pattern="dd/MM/yyyy hh:mm a")
	@Column(name="created_at")
	public Date created_at;
	@JsonFormat(pattern="dd/MM/yyyy hh:mm a")
	@Column(name="updated_at")
	public Date updated_at;
	
	@Column(name="is_approved")
	public Boolean is_approved;

//	@Transient
//	private List<String> approved_manager;
	
	@Transient
	private String next_approve_manager;
//	@Transient
//	private String disapproved_manager;
	@Transient
	private String name;
	@Transient
	private String fullname;
	
	@Transient
	private String overtime_type_name;
	
	
	public long getOvertime_id() {
		return overtime_id;
	}

	public void setOvertime_id(long overtime_id) {
		this.overtime_id = overtime_id;
	}

	public long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(long employee_id) {
		this.employee_id = employee_id;
	}

	public long getProject_id() {
		return project_id;
	}

	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}

	

	
	public Date getFrom_hour() {
		return from_hour;
	}

	public void setFrom_hour(Date from_hour) {
		this.from_hour = from_hour;
	}

	public Date getTo_hour() {
		return to_hour;
	}

	public void setTo_hour(Date to_hour) {
		this.to_hour = to_hour;
	}

	public int getOvertime_type() {
		return overtime_type;
	}

	public void setOvertime_type(int overtime_type) {
		this.overtime_type = overtime_type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public Boolean getIs_approved() {
		return is_approved;
	}

	public void setIs_approved(Boolean is_approved) {
		this.is_approved = is_approved;
	}
	
	
	public String getOvertime_type_name() {
		return overtime_type_name;
	}

	public void setOvertime_type_name(String overtime_type_name) {
		this.overtime_type_name = overtime_type_name;
	}

	public Overtime( long employee_id, long project_id, Date from_hour, Date to_hour,
			int overtime_type, String description, int status, Date created_at, Date updated_at, Boolean is_approved) {
		super();

		this.employee_id = employee_id;
		this.project_id = project_id;
		this.from_hour = from_hour;
		this.to_hour = to_hour;
		this.overtime_type = overtime_type;
		this.description = description;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.is_approved = is_approved;
	}

	public Overtime() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public List<String> getApproved_manager() {
//		return approved_manager;
//	}
//
//	public void setApproved_manager(List<String> approved_manager) {
//		this.approved_manager = approved_manager;
//	}

	public String getNext_approve_manager() {
		return next_approve_manager;
	}

	public void setNext_approve_manager(String next_approve_manager) {
		this.next_approve_manager = next_approve_manager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

//	public String getDisapproved_manager() {
//		return disapproved_manager;
//	}
//
//	public void setDisapproved_manager(String disapproved_manager) {
//		this.disapproved_manager = disapproved_manager;
//	}

	
	
	

}
