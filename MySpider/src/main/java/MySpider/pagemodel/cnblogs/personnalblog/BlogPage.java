/**
 * 
 */
package MySpider.pagemodel.cnblogs.personnalblog;

import java.util.List;

import org.apache.log4j.Logger;

import MySpider.pagemodel.TaggedPage;
import us.codecraft.webmagic.Page;

/**
 * @author Administrator
 *
 */
public class BlogPage extends TaggedPage {
	private static Logger log = Logger.getLogger(BlogPage.class);
	
	//私人blog的主页（第一个index页）
	public static String REGEX_IDENTIFY="//h1[@class='block_title']/a/text()";
	public static String IDENTIFY="";
	public static List<String> REGEX_SUCCESSOR_TAGGED_PAGE=null;
	static {
		//将多个tagged page级联成为model
		//此类型page无需再级联scan其内部URLs
	}
	
	/**
	 * 默认无参数构造函数会将开发时定义的静态参数放入
	 * 如果需要runtime修改，可参考base class getter/setter.
	 */
	public BlogPage() {
		super(REGEX_IDENTIFY,IDENTIFY, REGEX_SUCCESSOR_TAGGED_PAGE);
	}
	
	public BlogPage(String regexOfIdentify, String identify, List<String> regexOfSuccessorTaggedPage) {
		super(regexOfIdentify, identify, regexOfSuccessorTaggedPage);
	}
	
	@Override
	public boolean isIdentified(Page page) {
		if (page==null) return false;
		String flag=page.getHtml().xpath(REGEX_IDENTIFY).toString();
		if (flag!=null&&flag.length()>=1){
        	log.info("Find Blog Page with Title["+flag+"].");
			return true;
		}else {
			return false;
		}
	}

}
