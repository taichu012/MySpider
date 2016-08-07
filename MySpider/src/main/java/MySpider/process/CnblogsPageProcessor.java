/**
 * 
 */
package MySpider.process;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        String firstIndexPageFlag = page.getHtml().xpath("//div[@id='nav_next_page']/a/text()").toString();
        if (firstIndexPageFlag!=null&&firstIndexPageFlag.equals("下一页")){
        	log.info("Find Index Page[homepage] by find flag=["+firstIndexPageFlag+"]");
        	page.putField("title", "index");
        }
        //如果是非第一页的index page，有“共X页”结构；
        List<String> indexPageX = page.getHtml().xpath("//div[@class='pager']/text()").all();
        if (indexPageX!=null&&indexPageX.size()>=1){
        	String indexPageXFlag = getPageNbr(indexPageX.get(0));
        	log.info("Find Index Page["+indexPageXFlag+"] by find flag=["+indexPageX+"]");
        	page.putField("title", "index-"+indexPageXFlag);
        }
        
        //skip this page
//        if (Boolean.parseBoolean(page.getResultItems().get("otherIndexPage").toString())!=true
//        		&&Boolean.parseBoolean(page.getResultItems().get("firstIndexPage").toString())!=true){
//        	page.setSkip(true);
//        	}

    }
    
    /**
     * 函数目标：将诸如”共3页:    3 ”中的第3页的3取出；
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
