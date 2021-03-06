/*
package Com.IFI.InternalTool.DS.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Date;

*/
/**
 * Allocation
 *//*

@Entity
@Table(name = "allocation")
@JsonIgnoreProperties(value={"month","year"})
public class Allocation  {
	@Id
	@Column(name = "allocation_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long allocation_id;
	
	@Column(name = "project_id")
	private long project_id;
	
	@Column(name = "employee_id")
	private long employee_id;
	
	@Column(name = "month")
	private int month;
	
	@Column(name = "year")
	private int year;
	@Column(name = "allocation_plan")
	private double allocation_plan;
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "start_date")
  
	private Date start_date;
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "end_date")
	private Date end_date;
	
	@Transient
	private String Employee_Name;
	
	@Transient
	private String Project_Name;
	
	@Column(name = "half_day")
	@ColumnDefault("false")
	private boolean half_day;
	
	@Column(name = "pm")
	@ColumnDefault("false")
	private boolean pm;

	public Allocation(long project_id, long employee_id, int month, int year, float allocation_plan,
			Date start_date, Date end_date, boolean hafl_day, boolean pm) {
		super();
		this.project_id = project_id;
		this.employee_id = employee_id;
		this.month = month;
		this.year = year;
		this.allocation_plan = allocation_plan;
		this.start_date = start_date;
		this.end_date = end_date;
		this.half_day = hafl_day;
		this.pm = pm;
	}

	public Allocation() {
		super();
	}

	
	public String getEmployee_Name() {
		return Employee_Name;
	}

	public void setEmployee_Name(String employee_Name) {
		Employee_Name = employee_Name;
	}

	public String getProject_Name() {
		return Project_Name;
	}

	public void setProject_Name(String project_Name) {
		Project_Name = project_Name;
	}

	public long getAllocation_id() {
		return allocation_id;
	}

	public void setAllocation_id(long allocation_id) {
		this.allocation_id = allocation_id;
	}

	public long getProject_id() {
		return project_id;
	}

	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}

	public long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(long employee_id) {
		this.employee_id = employee_id;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getAllocation_plan() {
		return allocation_plan;
	}

	public void setAllocation_plan(double allocation_plan) {
		this.allocation_plan = allocation_plan;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}


	public boolean isHalf_day() {
		return half_day;
	}

	public void setHalf_day(boolean half_day) {
		this.half_day = half_day;
	}

	public boolean isPm() {
		return pm;
	}

	public void setPm(boolean pm) {
		this.pm = pm;
	}

	
	
	
}
*/
