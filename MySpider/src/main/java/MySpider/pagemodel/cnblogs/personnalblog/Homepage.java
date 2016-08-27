package MySpider.pagemodel.cnblogs.personnalblog;

import java.util.List;

import org.apache.log4j.Logger;

import MySpider.pagemodel.TaggedPageUtil;
import MySpider.pagemodel.TaggedPage;
import MySpider.pagemodel.TaggedPageBase;

public class Homepage extends TaggedPageBase implements TaggedPage {
	private static Logger log = Logger.getLogger(Homepage.class);
	private static TaggedPageUtil tputil=TaggedPageUtil.getTPU();
	
	//私人blog的主页（第一个index页）
	public static String REGEX_IDENTIFY="//div[@id='nav_next_page']/a/text()";
	public static String IDENTIFY="下一页";
    //格式举例："http://www.cnblogs.com/taichu/p/5725417.html"
    private static final String REGEX_BLOG_PAGE="(http://www\\.cnblogs\\.com/taichu/\\w+/\\w+.html)";
    //私人blog的index（主页作为index1，这里指其他index页）
    private static final String REGEX_BLOG_INDEX_PAGE="//div[@class='pager']/text()";
	public static List<String> REGEX_SUCCESSOR_PAGES=null;
	static {
		REGEX_SUCCESSOR_PAGES.add(REGEX_BLOG_PAGE);
		REGEX_SUCCESSOR_PAGES.add(REGEX_BLOG_INDEX_PAGE);
	}

	
	public boolean identify() {
		return tputil.identifiedByCaptureTarget(getPage(), REGEX_IDENTIFY, IDENTIFY);
	}

	public void scanSuccessorPage() {
		tputil.scanSuccessorPageByRegexs(getPage(),
				REGEX_SUCCESSOR_PAGES);
	}

	public void captureData() {
		//this.setName("Blog-Homepage");
		this.setName(this.getPageType());
		this.setContent(tputil.getRawPage(getPage()));
		this.setCapturedTimeMs(System.currentTimeMillis());
		getPage().putField(TaggedPage.TAGGED_PAGE_FLAG,this);
	}

	public void handlePage() {
		tputil.savePageAsExtName(this, "html", getPath());
	}
	
}
