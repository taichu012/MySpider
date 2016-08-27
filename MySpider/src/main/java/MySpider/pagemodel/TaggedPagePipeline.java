package MySpider.pagemodel;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

public class TaggedPagePipeline extends FilePipeline  {
	private static Logger log = Logger.getLogger(TaggedPagePipeline.class);

	/**
	 * create a FilePipeline with default path"/data/webmagic/"
	 */
	public TaggedPagePipeline() {
		//设定默认绝对路径，如果初始化的时候没有设定（只支持win）
		setPath("c:/temp/myspider/cnblogs/");
	}

	public TaggedPagePipeline(String path) {
		setPath(path);
	}
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		TaggedPage tPage = resultItems.get(TaggedPage.TAGGED_PAGE_FLAG);
		
		tPage.setSavePath(path);
		tPage.handlePage();
	}
}

