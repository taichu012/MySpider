/**
 * 
 */
package MySpider.process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import MySpider.taggedpage.Tag;
import MySpider.taggedpage.TaggedPage;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author User
 * 爬取（http://www.cnblogs.com/taichu/）博客园上自己的博客
 *
 */
public class CnblogsPersonalBlogProcessor  implements PageProcessor {

	private static Logger log = Logger.getLogger(CnblogsPersonalBlogProcessor.class);

    private Site site = Site.me().setRetryTimes(1).setSleepTime(1000)
    		.setCharset("utf-8").setUserAgent("Spider");
    
    //格式举例："http://www.cnblogs.com/taichu/p/5725417.html"
    private static final String REG_STR_BLOGPAGE="(http://www\\.cnblogs\\.com/taichu/\\w+/\\w+.html)";
    //格式举例："href="http://www.cnblogs.com/taichu/default.html?page=1"
    private static final String REG_STR_BLOGINDEX="(http://www\\.cnblogs\\.com/taichu/default.html\\?page=\\d+)";
    
    private static final String REG_STR_TITLEFIELD="//h1[@class='block_title']/a/text()";
    //私人blog的主页（第一个index页）
    private static final String REG_STR_BLOGHOMEPAGE="//div[@id='nav_next_page']/a/text()";
    //私人blog的index（主页作为index1，这里指其他index页）
    private static final String REG_STR_BLOGINDEXPAGE="//div[@class='pager']/text()";

    //查找普通blog的link并添加到挖掘列表
    private void scanBlogPageUrl(Page page){
    	page.addTargetRequests(page.getHtml().links().regex(REG_STR_BLOGPAGE).all());
    }
    //homepage作为特殊的index page，拥有类似的url结构和查找方法；
    private void scanBlogHomepageUrl(Page page){
        this.scanBlogIndexpageUrl(page);
    }
    //查找索引页（index page）的url并添加到挖掘列表
    private void scanBlogIndexpageUrl(Page page){
    	page.addTargetRequests(page.getHtml().links().regex(REG_STR_BLOGINDEX).all());
    }
    
    private ArrayList<Tag> clearAndGetTagBag(Tag tag){
    	ArrayList<Tag> tagBags = new ArrayList<Tag>();
    	tagBags.add(tag);
    	return tagBags;
    }
    
    public void process(Page page) {
     	//搜索主页（格式举例：‘http://www.cnblogs.com/taichu/’）
        //打印page长度
        log.info("Got page, length="+page.getRawText().length());
    	
        //探测homepage标记：如果是第一页，则右下角有一个“下一页”
        String firstIndexPageFlag = page.getHtml().xpath(REG_STR_BLOGHOMEPAGE).toString();
        //探测非第一页homepage的其他index page，有“共X页”结构；
        List<String> indexPageX = page.getHtml().xpath(REG_STR_BLOGINDEXPAGE).all();
        //探测普通blog page应该包含的TITLE字段
        String pageTitle = page.getHtml().xpath(REG_STR_TITLEFIELD).toString();
        //log.info("Got page title=["+pageTitle+"]");

        
        if (firstIndexPageFlag!=null&&firstIndexPageFlag.equals("下一页")){
        	log.info("Find Index Page[homepage] by find flag=["+firstIndexPageFlag+"]");
        	page.putField(TaggedPage.TAGGED_PAGE_FLAG, new TaggedPage(Tag.HOMEPAGE, page.getRawText(),
        			clearAndGetTagBag(new Tag(Tag.HOMEPAGE))));
        	//page判断为是homepage，进一步检索其他URL；
        	this.scanBlogPageUrl(page);
        	this.scanBlogIndexpageUrl(page);
        }else if (indexPageX!=null&&indexPageX.size()>=1){
        	String indexPageXFlag = getPageNbr(indexPageX.get(0));
        	log.info("Find Index Page["+indexPageXFlag+"] by find flag=["+indexPageX+"]");
        	page.putField(TaggedPage.TAGGED_PAGE_FLAG, new TaggedPage("index-"+indexPageXFlag,page.getRawText(),
        			clearAndGetTagBag(new Tag(Tag.INDEXPAGE))));
        	//page判断为是index page，进一步检索其他URL；
        	this.scanBlogPageUrl(page);
        	this.scanBlogIndexpageUrl(page);
        }else if (pageTitle!=null&&pageTitle.length()>0) {
        	//page既不是第一个index的homepage，也不是某个index page，所以是普通的blogpage
        	page.putField(TaggedPage.TAGGED_PAGE_FLAG, new TaggedPage(pageTitle, page.getRawText(),
        			clearAndGetTagBag(new Tag(Tag.BLOGPAGE))));
        	//page判断为是blog page，无需进一步检索其他URL；
        }else {
        	//非homepage，非index page，非有title的blogpage，无需做级联的url scan
        	page.setSkip(true);
        	//非homepage，非index page，非有title的blogpage，设定tag为默认；
//        	page.putField(TaggedPage.TAGGED_PAGE_FLAG, new TaggedPage(pageTitle, page.getRawText(), 
//        			clearAndGetTagBag(new Tag(Tag.DEFAULT))));
        }
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

    public Site getSite() {
        return site;
    }
    
	public static String getDateTimeNow() {
		return getDateTimeNow(System.currentTimeMillis());
	}

	public static String getDateTimeNow(long ms) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
		Date date = new Date(ms);
		return (formatter.format(date));
	}
}
