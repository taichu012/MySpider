package MySpider.pagemodel.cnblogs.personnalblog;

import java.util.List;

import org.apache.log4j.Logger;

import MySpider.pagemodel.TaggedPage;
import MySpider.pagemodel.TaggedPageBase;
import MySpider.pagemodel.TaggedPageUtil;
import us.codecraft.webmagic.Page;

public class BlogIndex extends TaggedPageBase implements TaggedPage{
	private static Logger log = Logger.getLogger(BlogIndex.class);
	private static TaggedPageUtil tputil=TaggedPageUtil.getTPU();
	
	//私人blog的主页（第一个index页）
	public static String REGEX_IDENTIFY="//div[@class='pager']/text()";
	public static String IDENTIFY="";
    //格式举例："http://www.cnblogs.com/taichu/p/5725417.html"
    private static final String REGEX_BLOG_PAGE="(http://www\\.cnblogs\\.com/taichu/\\w+/\\w+.html)";
    //私人blog的index（主页作为index1，这里指其他index页）
    private static final String REGEX_BLOG_INDEX_PAGE="//div[@class='pager']/text()";
	public static List<String> REGEX_SUCCESSOR_PAGES=null;
	static {
		REGEX_SUCCESSOR_PAGES.add(REGEX_BLOG_PAGE);
		REGEX_SUCCESSOR_PAGES.add(REGEX_BLOG_INDEX_PAGE);
	}
	private String blogIndexNbr=""; 

	public boolean identify() {
		if (getPage()==null) return false;
		List<String> flag=getPage().getHtml().xpath(REGEX_IDENTIFY).all();
		if (flag!=null&&flag.size()>=1){
			blogIndexNbr = getPageNbr(flag.get(0));
        	log.info("Find Index Page["+blogIndexNbr+"].");
			return true;
		}else {
			return false;
		}
	}
	
	public void scanSuccessorPage() {
		tputil.scanSuccessorPageByRegexs(getPage(),
				REGEX_SUCCESSOR_PAGES);
	}

	public void captureData() {
		this.setName("index-"+blogIndexNbr);
		this.setContent(tputil.getRawPage(getPage()));
		this.setCapturedTimeMs(System.currentTimeMillis());
		getPage().putField(TaggedPage.TAGGED_PAGE_FLAG,this);
	}

	public void handlePage() {
		tputil.savePageAsExtName(this, "html", getPath());
	}
	
	
    /**
     * 函数目标：将诸如”共3页:    3 ”中的最右面的3取出；
     * 函数算法：用冒号分割，右面部分前后取除空格，留下3
     * @param str 输入类似“共3页:    3 ”
     * @return 3
     */
    public static String getPageNbr(String str){
    	String ret=null;
    	if (str==null){
    		return ret;
    	}else {
    		//算法：取出冒号右面部分，再去掉前后的空格，得到中间的数字；
    		//但trim和普通replace都只是英文半角空格，不是中文全角空格，于是引入正则算法如下：
    		//去除空格算法：将非数字替换为#，再将#全部删除，就留下了数字！
    		return str.substring(str.indexOf(':')+1).replaceAll("[^0-9]", "#").replaceAll("#","");
    	}    	
    }

}
