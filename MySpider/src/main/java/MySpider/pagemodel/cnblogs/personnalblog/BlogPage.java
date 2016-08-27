/**
 * 
 */
package MySpider.pagemodel.cnblogs.personnalblog;

import org.apache.log4j.Logger;

import MySpider.pagemodel.TaggedPage;
import MySpider.pagemodel.TaggedPageBase;
import MySpider.pagemodel.TaggedPageUtil;

/**
 * @author Administrator
 *
 */
public class BlogPage  extends TaggedPageBase implements TaggedPage {
	private static Logger log = Logger.getLogger(BlogPage.class);
	private static TaggedPageUtil tputil=TaggedPageUtil.getTPU();
	
	//私人blog的主页（第一个index页）
	public static String REGEX_IDENTIFY="//h1[@class='block_title']/a/text()";
	public static String IDENTIFY="";
	
	public boolean identify() {
		return tputil.identifiedByCaptureNotNull(getPage(), REGEX_IDENTIFY);
	}
	

	public void scanSuccessorPage() {
		//blog page只需保存，无需继续挖掘其内部urls
	}

	public void captureData() {
		this.setName(tputil.getStringField(getPage(), REGEX_IDENTIFY));
		this.setContent(tputil.getRawPage(getPage()));
		this.setCapturedTimeMs(System.currentTimeMillis());
		getPage().putField(TaggedPage.TAGGED_PAGE_FLAG,this);
	}

	public void handlePage() {
		tputil.savePageAsExtName(this, "html", getPath());
	}

}
