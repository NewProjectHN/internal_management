package Com.IFI.InternalTool.DS.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="overtime_type")
public class Overtime_Type implements Serializable{
	@Id
	@Column(name = "overtime_type_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long overtime_type_id;
	@Column(name = "overtime_type_name")
	private String overtime_type_name;
	public long getOvertime_type_id() {
		return overtime_type_id;
	}
	public void setOvertime_type_id(long overtime_type_id) {
		this.overtime_type_id = overtime_type_id;
	}
	
	public String getOvertime_type_name() {
		return overtime_type_name;
	}
	public void setOvertime_type_name(String overtime_type_name) {
		this.overtime_type_name = overtime_type_name;
	}
	public Overtime_Type(String overtime_type_name) {
		super();
		this.overtime_type_name = overtime_type_name;
	}
	public Overtime_Type() {
		super();
	}
	
	
}
