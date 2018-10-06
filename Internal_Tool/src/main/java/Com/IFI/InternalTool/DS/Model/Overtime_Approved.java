package Com.IFI.InternalTool.DS.Model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="overtime_approved")
public class Overtime_Approved {
	@Id
	@Column(name = "overtime_approved_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long overtime_approved_id;
	@Column(name = "manager_id")
	private long manager_id;
	@Column(name = "overtime_id")
	private long overtime_id;
	@Column(name = "priority")
	private int priority;
	public long getOvertime_approved_id() {
		return overtime_approved_id;
	}
	public void setOvertime_approved_id(long overtime_approved_id) {
		this.overtime_approved_id = overtime_approved_id;
	}
	public long getManager_id() {
		return manager_id;
	}
	public void setManager_id(long manager_id) {
		this.manager_id = manager_id;
	}
	public long getOvertime_id() {
		return overtime_id;
	}
	public void setOvertime_id(long overtime_id) {
		this.overtime_id = overtime_id;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Overtime_Approved(long overtime_approved_id, long manager_id, long overtime_id, int priority) {
		super();
		this.overtime_approved_id = overtime_approved_id;
		this.manager_id = manager_id;
		this.overtime_id = overtime_id;
		this.priority = priority;
	}
	public Overtime_Approved() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
