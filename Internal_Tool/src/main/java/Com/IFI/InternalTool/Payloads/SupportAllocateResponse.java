package Com.IFI.InternalTool.Payloads;

import java.sql.Date;

/**
 * Allocation
 */
public class SupportAllocateResponse {
	private long employeeId;
	private String employeeName;
	private Date lastAllocatedDay;
	private double totalAllocationPlan;
	
	public SupportAllocateResponse(long employeeId, String employeeName, Date lastAllocatedDay,
			double totalAllocationPlan) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.lastAllocatedDay = lastAllocatedDay;
		this.totalAllocationPlan = totalAllocationPlan;
	}

	public SupportAllocateResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getLastAllocatedDay() {
		return lastAllocatedDay;
	}

	public void setLastAllocatedDay(Date lastAllocatedDay) {
		this.lastAllocatedDay = lastAllocatedDay;
	}

	public double getTotalAllocationPlan() {
		return totalAllocationPlan;
	}

	public void setTotalAllocationPlan(double totalAllocationPlan) {
		this.totalAllocationPlan = totalAllocationPlan;
	}
	
	
	
	
}