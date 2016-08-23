package MySpider.pagetag;

import java.util.ArrayList;

public class TaggedPage {
	private String name;
	private String content;
	private long generatedTimeMs;
	private ArrayList<Tag> tags;
	
	
	
	
	/**
	 * 定义page的名称name，内容content，逻辑标签集合tags
	 * @param name name
	 * @param content content
	 * @param tags tags
	 */
	public TaggedPage(String name, String content, ArrayList<Tag> tags) {
		super();
		this.name = name;
		this.content = content;
		this.tags = tags;
		this.generatedTimeMs=System.currentTimeMillis();
	}
	
	public TaggedPage(String name, String content, long generatedTimeMs, ArrayList<Tag> tags) {
		super();
		this.name = name;
		this.content = content;
		this.generatedTimeMs = generatedTimeMs;
		this.tags = tags;
	}

	public long getGeneratedTimeMs() {
		return generatedTimeMs;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<Tag> getTags() {
		return tags;
	}
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
}
