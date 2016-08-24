package MySpider.pipeline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import MySpider.taggedpage.Action;
import MySpider.taggedpage.Tag;
import MySpider.taggedpage.TaggedPageProcess;
import MySpider.taggedpage.TaggedPage;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

public class CnblogsPersonalBlogSaver extends FilePipeline implements TaggedPageProcess {
	private static Logger log = Logger.getLogger(CnblogsPersonalBlogSaver.class);
	
	//初始化规则：通过设定<tag,action> pair对应关系来绑定tag驱动的action；
	private HashMap<String, Action> tagActionMappingRules = new HashMap<String, Action>();
	
	/**
	 * 定义tag和action的对应规则
	 */
	public void initRules() {
		tagActionMappingRules.put(Tag.UNKNOWN_PAGE, new Action(Action.NO_ACTION));
		tagActionMappingRules.put(Tag.HOMEPAGE, new Action(Action.SAVE_AS_HTML));
		tagActionMappingRules.put(Tag.INDEXPAGE, new Action(Action.SAVE_AS_HTML));
		tagActionMappingRules.put(Tag.BLOGPAGE, new Action(Action.SAVE_AS_HTML));
	}
	
	/**
	 * create a FilePipeline with default path"/data/webmagic/"
	 */
	public CnblogsPersonalBlogSaver() {
		//设定默认绝对路径，如果初始化的时候没有设定（只支持win）
		setPath("c:/data/myspider/");
		initRules();
	}

	public CnblogsPersonalBlogSaver(String path) {
		setPath(path);
		initRules();
	}
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		TaggedPage tdPage = resultItems.get(TaggedPage.TAGGED_PAGE_FLAG);
		taggedPageProcess(tdPage);

	}

	public void taggedPageProcess(TaggedPage tdPage){
		ArrayList<Tag> tags=tdPage.getTags();
		if (tags==null) return;
		for(Iterator<Tag> it = tags.iterator(); it.hasNext();) {
			Tag currentTag=it.next();
			Action action=getAction(currentTag);
			if (action==null) {return;}
			it.remove();//删除已经找到了对于aciton的tag；
			
			switch (action.getAction()){
			case Action.NO_ACTION:break;//do nothing
			case Action.DUMP:dump(tdPage);break;
			case Action.LOG:log(tdPage);break;
			case Action.SAVE_AS_HTML:savePageAsExtName(tdPage,"html");break;
			case Action.SAVE_AS_TXT:savePageAsExtName(tdPage,"txt");break;
			case Action.SAVE_AS_RAW:savePageAsExtName(tdPage,"raw");break;
			default:break;//do nothing
			}
		}
	}

	private Action getAction(Tag tag){
		return tagActionMappingRules.get(tag.getTag());
	}
	
	private void log(TaggedPage tdPage) {
		log.warn("Method 'log(TaggedPage tdPage)' has not implemented yet!");
	}

	private void dump(TaggedPage tdPage) {
		log.warn("Method 'dump(TaggedPage tdPage)' has not implemented yet!");
	}

	private void savePageAsExtName(TaggedPage tdPage, String extensionName) {
		String filename = tdPage.getName();
		if (filename==null||filename.length()==0){
			log.warn("Page title is empty and cannot be saved!");
			return;
		};
		String filebody = tdPage.getContent();
		long grabPageTimeMs = tdPage.getGeneratedTimeMs();
		String pathAndFileName = null;

		//String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
		try {
			pathAndFileName = path + filename + '.'+extensionName;
			PrintWriter printWriter = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream(getFile(pathAndFileName)), "UTF-8"));
			printWriter.println(filebody);
			printWriter.close();
			log.debug("("+pathAndFileName+") is saved.");
		} catch (IOException e) {
			log.warn("write file("+pathAndFileName+") error!", e);
		}
	}
	
}
