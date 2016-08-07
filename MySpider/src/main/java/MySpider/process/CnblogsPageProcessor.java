/**
 * 
 */
package MySpider.process;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import MySpider.pipeline.SaveRawPageAsHtmlPipeline;
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
public class CnblogsPageProcessor  implements PageProcessor {

	private static Logger log = Logger.getLogger(CnblogsPageProcessor.class);

    private Site site = Site.me().setRetryTimes(1).setSleepTime(1000)
    		.setCharset("utf-8").setUserAgent("Spider");

    public void process(Page page) {
    	//page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        
    	//搜索主页（‘http://www.cnblogs.com/taichu/’）
        //添加文章的link规则
    	//links format=(href="http://www.cnblogs.com/taichu/p/5725417.html")
        page.addTargetRequests(page.getHtml().links().regex("(http://www\\.cnblogs\\.com/taichu/\\w+/\\w+.html)").all());
        //添加index page
        //links format=(href="http://www.cnblogs.com/taichu/default.html?page=1“）
        page.addTargetRequests(page.getHtml().links().regex("(http://www\\.cnblogs\\.com/taichu/default.html\\?page=\\d+)").all());
        
        page.putField("body",page.getRawText());
        log.info("Got body [xxx].length="+page.getResultItems().get("body").toString().length());
        
        page.putField("title", page.getHtml().xpath("//h1[@class='block_title']/a/text()").toString());
        log.info("Got title=["+page.getResultItems().get("title")+"]");
        
//        page.putField("type", page.getHtml().xpath("//a[@id='Header1_HeaderTitle']/@href").toString()
//        		.replaceAll("http://", "").replaceAll("/", "-"));
//        log.info("Got type=["+page.getResultItems().get("type")+"]");
        
        //判断是不是主页的几个page；
        //如果是第一页，则右下角有一个“下一页”
        String firstIndexPage = page.getHtml().xpath("//div[@id='nav_next_page']/a/text()").toString();
        if (firstIndexPage!=null){
        	page.putField("firstIndexPage", firstIndexPage.equals("下一页"));
        	log.info("["+firstIndexPage+"]");
        	log.info("firstIndexPage="+page.getResultItems().get("firstIndexPage"));
        	page.putField("title", "首页");
        }
        //如果是非第一页的indexpage，有“共X页”结构；
        String otherIndexPage = page.getHtml().xpath("//div[@class='pager']/text()").toString();
        if (otherIndexPage!=null&&otherIndexPage.length()>=2){
        	page.putField("otherIndexPage", otherIndexPage.trim().substring(0,1).equals("共"));
        	log.info("["+otherIndexPage.trim().substring(0,1)+"]");
        	log.info("otherIndexPage="+page.getResultItems().get("otherIndexPage"));
        	page.putField("title", "索引页-"+System.currentTimeMillis());
        }
        
        //skip this page
//        if (Boolean.parseBoolean(page.getResultItems().get("otherIndexPage").toString())!=true
//        		&&Boolean.parseBoolean(page.getResultItems().get("firstIndexPage").toString())!=true){
//        	page.setSkip(true);
//        	}

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
