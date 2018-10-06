package Com.IFI.InternalTool.DS.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vacation_type")
public class Vacation_Type implements Serializable{
	@Id
	@Column(name = "vacation_type_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long vacation_type_id;
	@Column(name = "vacation_type_name")
	private String vacation_type_name;
	
	public long getVacation_type_id() {
		return vacation_type_id;
	}
	public void setVacation_type_id(long vacation_type_id) {
		this.vacation_type_id = vacation_type_id;
	}
	
	public String getVacation_type_name() {
		return vacation_type_name;
	}
	public void setVacation_type_name(String vacation_type_name) {
		this.vacation_type_name = vacation_type_name;
	}
	public Vacation_Type(String vacation_type_name) {
		super();
		this.vacation_type_name = vacation_type_name;
	}
	public Vacation_Type() {
		super();
	}
	
}
