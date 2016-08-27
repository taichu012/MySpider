package MySpider.pagemodel;

import java.util.List;

import org.apache.log4j.Logger;

import MySpider.pagemodel.cnblogs.personnalblog.BlogIndex;
import MySpider.pagemodel.cnblogs.personnalblog.BlogPage;
import MySpider.pagemodel.cnblogs.personnalblog.Homepage;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class TaggedPageProcess extends FilePipeline implements PageProcessor {
	private static Logger log = Logger.getLogger(TaggedPageProcess.class);

    private Site site = Site.me().setRetryTimes(1).setSleepTime(1000)
    		.setCharset("utf-8").setUserAgent("MSIE6.0");
    
    private List<TaggedPage> taggedPageList=null;
    
    public List<TaggedPage> getTaggedPageList() {
		return taggedPageList;
	}
    

	public TaggedPageProcess() {
		super();
	}
    

	public TaggedPageProcess(List<TaggedPage> taggedPageList) {
		super();
		this.taggedPageList = taggedPageList;
	}

	public TaggedPageProcess setTaggedPage(TaggedPage tPage) {
		this.taggedPageList.add(tPage);
		return this;
	}

    public Site getSite() {
        return site;
    }

	public void process(Page page) {
		for (int i=0; i<taggedPageList.size();i++){
			processOneTaggedPage(taggedPageList.get(i),page);
		}
	}

	private void processOneTaggedPage(TaggedPage tpage, Page page) {
		//TODO:这里或其他地方放入对整个page model的DAG有向无环图的依赖关系处理就最佳了！
		tpage.setPage(page);
		if (tpage.identify()==true){
			//确认找到目标tagged page，则继续操作；
			tpage.captureData();
			tpage.scanSuccessorPage();
		}else {
			log.debug("("+page.getUrl()+") is not instance of ("+tpage.getPageType()+").");
		}
	}
	
	


    public void main(String[] args){
    	//初始化方法1：
    	List<TaggedPage> tpList = null;
    	tpList.add(new Homepage());
    	tpList.add(new BlogIndex());
    	tpList.add(new BlogPage());
    	
    	TaggedPageProcess tpp=new TaggedPageProcess(tpList);
    	
    	//初始化方法2 （curry/查理化链式初始化）
    	TaggedPageProcess tpp2=new TaggedPageProcess()
    			.setTaggedPage(new Homepage())
    			.setTaggedPage(new BlogIndex())
    			.setTaggedPage(new BlogPage());
    	
    	//重要说明：因为目前tagged page的鉴定是平面一维，没有page之间关系的，
    	//所以在撰写某个tagged page的鉴定identity的regex正则表达式时，要确定能体现出这个page的type来，而最好不重复！
    	
    }
    

}
