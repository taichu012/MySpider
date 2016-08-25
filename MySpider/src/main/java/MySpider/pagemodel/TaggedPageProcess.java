package MySpider.pagemodel;

import java.util.List;

import org.apache.log4j.Logger;

import MySpider.pagemodel.cnblogs.personnalblog.Homepage;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class TaggedPageProcess extends FilePipeline implements PageProcessor {
	private static Logger log = Logger.getLogger(TaggedPageProcess.class);

    private Site site = Site.me().setRetryTimes(1).setSleepTime(1000)
    		.setCharset("utf-8").setUserAgent("MSIE6.0");
    
    private static List<ITaggedPage> taggedPages;
    static {
    	//Step1-逐个按需定义tagged page（不同类型），组成page model
    	//Step2-初始化定义好的page model
    	taggedPages.add(new Homepage());
    	taggedPages.add(new BlogIndex());
    	taggedPages.add(new BlogPage());
    }
    
    public Site getSite() {
        return site;
    }

	public void process(Page page) {
		//TODO：处理所有已经定义的tagged page；
		//TODO：考虑用户定义好tagged page后，怎么开通实例，并让本process处理？
		
		
		
	}

}
