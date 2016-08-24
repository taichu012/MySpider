/**
 * 
 */
package MySpider.process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import MySpider.pipeline.SaveRawPageAsHtmlPipeline;
import MySpider.taggedpage.Tag;
import MySpider.taggedpage.TaggedPage;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
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
    private static final String REG_STR_BLOCKINDEXPAGE="//div[@class='pager']/text()";

    public void process(Page page) {
        
    	//搜索主页（格式举例：‘http://www.cnblogs.com/taichu/’）
    	
        //查找普通blog的link并添加到挖掘列表
        //page.addTargetRequests(page.getHtml().links().regex("(http://www\\.cnblogs\\.com/taichu/\\w+/\\w+.html)").all());
    	page.addTargetRequests(page.getHtml().links().regex(REG_STR_BLOGPAGE).all());
        
        //查找索引页（index page）的url并添加到挖掘列表
    	//page.addTargetRequests(page.getHtml().links().regex("(http://www\\.cnblogs\\.com/taichu/default.html\\?page=\\d+)").all());
        page.addTargetRequests(page.getHtml().links().regex(REG_STR_BLOGINDEX).all());
        
        //打印page长度
        log.info("Got page, length="+page.getRawText().length());
        
        //查找TITLE字段并保存
        //page.putField("title", page.getHtml().xpath("//h1[@class='block_title']/a/text()").toString());
        String pageTitle = page.getHtml().xpath(REG_STR_TITLEFIELD).toString();
        log.info("Got page title=["+pageTitle+"]");
        
        
    	//默认设定taggedpage
    	ArrayList<Tag> tags = new ArrayList<Tag>();
    	if (pageTitle!=null&&pageTitle.length()>0) {
    		tags.add(new Tag(Tag.BLOGPAGE));
    	}else {
    		tags.add(new Tag(Tag.DEFAULT));
    	}
    	page.putField(TaggedPage.TAGGED_PAGE_FLAG, new TaggedPage(pageTitle, page.getRawText(), tags));
        
        //判断是不是主页的几个page；
        //如果是第一页，则右下角有一个“下一页”
        //String firstIndexPageFlag = page.getHtml().xpath("//div[@id='nav_next_page']/a/text()").toString();
        String firstIndexPageFlag = page.getHtml().xpath(REG_STR_BLOGHOMEPAGE).toString();
        if (firstIndexPageFlag!=null&&firstIndexPageFlag.equals("下一页")){
        	log.info("Find Index Page[homepage] by find flag=["+firstIndexPageFlag+"]");
        	//设定taggedpage为homepage
        	tags.clear();
        	tags.add(new Tag(Tag.HOMEPAGE));
        	page.putField(TaggedPage.TAGGED_PAGE_FLAG, new TaggedPage(Tag.HOMEPAGE, page.getRawText(), tags));
        }
        //如果是非第一页的index page，有“共X页”结构；
        //List<String> indexPageX = page.getHtml().xpath("//div[@class='pager']/text()").all();
        List<String> indexPageX = page.getHtml().xpath(REG_STR_BLOCKINDEXPAGE).all();
        if (indexPageX!=null&&indexPageX.size()>=1){
        	String indexPageXFlag = getPageNbr(indexPageX.get(0));
        	log.info("Find Index Page["+indexPageXFlag+"] by find flag=["+indexPageX+"]");
        	//设定taggedpage为普通index page
        	tags.clear();
        	tags.add(new Tag(Tag.INDEXPAGE));
        	page.putField(TaggedPage.TAGGED_PAGE_FLAG, new TaggedPage("index-"+indexPageXFlag,page.getRawText(), tags));
        }
        
        //skip this page
//        if (Boolean.parseBoolean(page.getResultItems().get("otherIndexPage").toString())!=true
//        		&&Boolean.parseBoolean(page.getResultItems().get("firstIndexPage").toString())!=true){
//        	page.setSkip(true);
//        	}

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
