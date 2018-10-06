package Com.IFI.InternalTool.Payloads;

public class EmailSent {
	private String send_to;
	private String name;
	private String subject;
	private String body;
	public EmailSent(String send_to, String name, String subject, String body) {
		super();
		this.send_to = send_to;
		this.name = name;
		this.subject = subject;
		this.body = body;
	}
	public EmailSent() {
		super();
	}
	public String getSend_to() {
		return send_to;
	}
	public void setSend_to(String send_to) {
		this.send_to = send_to;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
