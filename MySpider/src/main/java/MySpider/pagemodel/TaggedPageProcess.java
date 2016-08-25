package MySpider.pagemodel;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class TaggedPageProcess implements PageProcessor {
	private static Logger log = Logger.getLogger(TaggedPageProcess.class);

    private Site site = Site.me().setRetryTimes(1).setSleepTime(1000)
    		.setCharset("utf-8").setUserAgent("MSIE6.0");
    
    public Site getSite() {
        return site;
    }

	public void process(Page page) {
		//TODO：处理所有已经定义的tagged page；
		//TODO：考虑用户定义好tagged page后，怎么开通实例，并让本process处理？
		
	}

}
