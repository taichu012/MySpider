package MySpider.pagemodel;

import us.codecraft.webmagic.Page;

public class TaggedPageBase implements TaggedPage {

	public boolean identify() {
		return false;
	}

	public void scanSuccessorPage() {}

	public void captureData() {}

	public void handlePage() {}

	public String getPageType() {
		return this.getClass().getSimpleName();
	}

	
	
	private Page page;
	private String path;
	private String name;
	private String content;
	private long capturedTimeMs;
	

	public void setSavePath(String path) {
		this.setPath(path);
	}
	
	//getter/setter
	
	public void setPage(Page page) {
		this.page = page;
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public String getPath() {
		return path;
	}

	private void setPath(String path) {
		this.path = path;
	}

	public Page getPage() {
		return page;
	}

	protected void setContent(String content) {
		this.content = content;
	}

	public long getCapturedTimeMs() {
		return capturedTimeMs;
	}

	protected void setCapturedTimeMs(long capturedTimeMs) {
		this.capturedTimeMs = capturedTimeMs;
	}



}
