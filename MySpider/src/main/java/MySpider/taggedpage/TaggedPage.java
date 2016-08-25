package MySpider.taggedpage;

import java.util.ArrayList;

public class TaggedPage {
	public static final String TAGGED_PAGE_FLAG="taggedPage";
	private String name;
	private String content;
	private long generatedTimeMs;
	private ArrayList<Tag> tagBag;
	
	
	
	
	/**
	 * 定义page的名称name，内容content，逻辑标签集合tags
	 * @param name name
	 * @param content content
	 * @param tagBag tagBag
	 */
	public TaggedPage(String name, String content, ArrayList<Tag> tags) {
		super();
		this.name = name;
		this.content = content;
		this.tagBag = tags;
		this.generatedTimeMs=System.currentTimeMillis();
	}
	
	public TaggedPage(String name, String content, long generatedTimeMs, ArrayList<Tag> tags) {
		super();
		this.name = name;
		this.content = content;
		this.generatedTimeMs = generatedTimeMs;
		this.tagBag = tags;
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
		return tagBag;
	}
	public void setTags(ArrayList<Tag> tags) {
		this.tagBag = tags;
	}
}
