package Com.IFI.InternalTool.Payloads;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import Com.IFI.InternalTool.Utils.AppConstants;
public class Payload {
	private String code;
	private String status;
	private String message;
	private Object data;
	private Object data1;
	private Boolean success;
	private Integer pages;

	public Payload() {
		code = AppConstants.FAILED_CODE;
		status = AppConstants.STATUS_KO;
		message = "";
		data = "";
		data1 = "";
		success = false;
		pages = 0;
	}
	
	public void resetPayload() {
		code = AppConstants.FAILED_CODE;
		status = AppConstants.STATUS_KO;
		message = "";
		data = "";
		data1 = "";
		success = false;
		pages = 0;
	}

	public Payload(final Object data, final String status, final String code, final String message,
			final Boolean success) {
		this.data = data;
		this.status = status;
		this.code = code;
		this.message = message;
		this.success = success;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Payload(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setPayLoad(final Object data, final String status, final String code, final String message,
			final Boolean success) {
		this.data = data;
		this.status = status;
		this.code = code;
		this.message = message;
		this.success = success;
	}

	public Object getData1() {
		return data1;
	}

	public void setData1(Object data1) {
		this.data1 = data1;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}