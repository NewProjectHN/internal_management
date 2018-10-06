package Com.IFI.InternalTool.Payloads;

/**
 * Allocation
 */
public class PagedResponse<T> {
	private String name;
	private ContentResponse<T> content;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ContentResponse<T> getContent() {
		return content;
	}
	public void setContent(ContentResponse<T> content) {
		this.content = content;
	}

	

}