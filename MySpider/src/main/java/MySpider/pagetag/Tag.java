package MySpider.pagetag;

public class Tag {
	
	public static final String HOMEPAGE="homepage"; 
	public static final String INDEXPAGE="indexpage";
	public static final String BLOGPAGE="blogpage";
	public static final String DEFAULT=BLOGPAGE;


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
