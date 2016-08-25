package MySpider.pagemodel.cnblogs.personnalblog;

import java.util.List;

import org.apache.log4j.Logger;

import MySpider.pagemodel.TaggedPage;

public class Homepage extends TaggedPage {
	private static Logger log = Logger.getLogger(Homepage.class);
	
	//私人blog的主页（第一个index页）
	public static String REGEX_IDENTIFY="//div[@id='nav_next_page']/a/text()";
	public static String IDENTIFY="下一页";
    //格式举例："http://www.cnblogs.com/taichu/p/5725417.html"
    private static final String REGEX_BLOG_PAGE="(http://www\\.cnblogs\\.com/taichu/\\w+/\\w+.html)";
    //私人blog的index（主页作为index1，这里指其他index页）
    private static final String REGEX_BLOG_INDEX_PAGE="//div[@class='pager']/text()";
	public static List<String> REGEX_SUCCESSOR_TAGGED_PAGE=null;
	static {
		//将多个tagged page级联成为model
		REGEX_SUCCESSOR_TAGGED_PAGE.add(REGEX_BLOG_PAGE);
		REGEX_SUCCESSOR_TAGGED_PAGE.add(REGEX_BLOG_INDEX_PAGE);
	}
	
	/**
	 * 默认无参数构造函数会将开发时定义的静态参数放入
	 * 如果需要runtime修改，可参考base class getter/setter.
	 */
	public Homepage() {
		super(REGEX_IDENTIFY,IDENTIFY, REGEX_SUCCESSOR_TAGGED_PAGE);
	}
	
	public Homepage(String regexOfIdentify, String identify, List<String> regexOfSuccessorTaggedPage) {
		super(regexOfIdentify, identify, regexOfSuccessorTaggedPage);
	}
}
