/**
 * 
 */
package MySpider.spider;

import org.apache.log4j.Logger;

import MySpider.pipeline.SaveRawPageAsHtmlPipeline;
import MySpider.process.CnblogsPageProcessor;
import MySpider.process.GithubRepoPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * @author User
 *
 */
public class CnblogsSpider {

	private static Logger log = Logger.getLogger(CnblogsSpider.class);
	private static final String USAGE = "USAGE: java -jar xxxSpider.jar URL=http://www.cnblogs.com/taichu/ LocalBackupFolder=H:/博客备份/博客园-www.cnblogs.com/taichu/ \n"
			+ "arg[URL] is first url feed to spider,\n"
			+ "arg[LocalBackupFolder] is used as local backup folder name.";

	public static void main(String[] args) {
		
		// 如果args数组没有元素的话，说明一个参数都没有传递
		// 参数格式：“url=http://www.cnblogs.com/taichu/ type=taichu”
		// 参数说明：第一个参数url代表爬虫开始的第一个url；第2个参数type代表一个分类，用户建立保存的目录；
		// 参数举例：见参数格式；
		if (args == null || args.length != 2) {
			log.error("Spider exist with wrong arguments!");
			showUsage();
			return;
		} else {
			//不校验URL，如果错误则spider抓取不到任何值；
			String url = args[0].trim().substring("URL=".length());
			log.info("args[0]=("+url+")");
			
			//不校验本地备份目录参数
			String localBackupFolder = args[1].trim().substring("LocalBackupFolder=".length());
			log.info("args[1]=("+localBackupFolder+")");
			
			//启动spider
			log.info("启动spider，抓取第一个page("+url+"),并保存到本地目录("+localBackupFolder+")中.");
			Spider.create(new CnblogsPageProcessor()).addUrl(url)
					.addPipeline(new SaveRawPageAsHtmlPipeline(localBackupFolder)).thread(5).run();
		}
	}
	private static void showUsage(){
		log.warn(USAGE);
		System.out.println(USAGE);
	}
}
