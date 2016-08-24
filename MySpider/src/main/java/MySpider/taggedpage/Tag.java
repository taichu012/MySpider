package MySpider.taggedpage;

public class Tag {
	
	public static final String HOMEPAGE="homePage"; 
	public static final String INDEXPAGE="indexPage";
	public static final String BLOGPAGE="blogPage";
	public static final String UNKNOWN_PAGE="unknownPage";
	public static final String DEFAULT=UNKNOWN_PAGE;


	private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Tag(String tag) {
		super();
		this.tag = tag;
	}
	
	public Tag() {
		super();
		this.tag = DEFAULT;
	}
}
