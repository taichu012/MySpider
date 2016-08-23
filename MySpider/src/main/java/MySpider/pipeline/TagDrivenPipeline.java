/**
 * 
 */
package MySpider.pipeline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.apache.log4j.Logger;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

/**
 * @author User
 *
 */
public class TagDrivenPipeline extends FilePipeline {
	private static Logger log = Logger.getLogger(TagDrivenPipeline.class);
	
	//TODO:这里设定<tag,action> pair对，然后按照规则进行pipeline处理；
	

	/**
	 * create a FilePipeline with default path"/data/webmagic/"
	 */
	public TagDrivenPipeline() {
		//设定默认绝对路径，如果初始化的时候没有设定（只支持win）
		setPath("c:/data/myspider/");
	}

	public TagDrivenPipeline(String path) {
		setPath(path);
	}

	/**
	 * MUST input pair(title,body) and both having value, else do NOTHING!
	 */
	@Override
	public void process(ResultItems resultItems, Task task) {
		//title作为文件名；
		String rawPageTitle = resultItems.get("title");
		//如果没有title，就退出；
		if (rawPageTitle==null||rawPageTitle.length()==0){return;};
		
		//body作为html文件内容
		String rawPageBody = resultItems.get("body");		
		//如果没有body，就退出；
		if (rawPageBody==null||rawPageBody.length()==0){return;};

		//		//type作为跟目录区分，一种type所生成的page应该放在对应的type目录下；
//		String rawPageType = resultItems.get("type");
//		//如果没有type，就默认default=other；
//		if (rawPageTitle==null||rawPageTitle.length()==0){rawPageType="other";};

		//set full path
		//String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
		//String path = this.path + PATH_SEPERATOR + rawPageType + PATH_SEPERATOR;
		log.info("task.getUUID()="+task.getUUID());
		
		try {
			PrintWriter printWriter = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream(getFile(path + rawPageTitle + ".html")), "UTF-8"));
			printWriter.println(rawPageBody);
			printWriter.close();
		} catch (IOException e) {
			log.warn("write file error", e);
		}
	}

}
