/**
 * 
 */
package MySpider.spider;

import org.apache.log4j.Logger;

import MySpider.pipeline.CnblogsPersonalBlogSaver;
import MySpider.process.CnblogsPersonalBlogProcessor;
import us.codecraft.webmagic.Spider;

/**
 * @author User
 *
 */
public class CnblogsPersonalBlogSpider {

	private static Logger log = Logger.getLogger(CnblogsPersonalBlogSpider.class);
	private static final String PARAM_URL="URL=";
	private static final String PARAM_LOCAL_BACKUP_FOLDER="LocalBackupFolder=";
	private static final String USAGE = "USAGE: java -jar xxxSpider.jar "+PARAM_URL+"http://www.cnblogs.com/taichu/ "
			+PARAM_LOCAL_BACKUP_FOLDER+"H:/博客备份/博客园-www.cnblogs.com/taichu/ \n"
			+ "arg["+PARAM_URL+"] is first url feed to spider,\n"
			+ "arg["+PARAM_LOCAL_BACKUP_FOLDER+"] is used as local backup folder name.";

	public static void main(String[] args) {
		
		// 如果args数组没有元素的话，说明一个参数都没有传递
		// 参数格式/举例/说明：见usage中说明
		// TODO：5线程，site重试1次，每次请求间隔1秒，这些都可配置，但先不做。
		if (args == null || args.length != 2) {
			log.error("Spider exist with wrong arguments!");
			showUsage();
			return;
		} else {
			//不校验URL，如果错误则spider抓取不到任何值；
			String url = args[0].trim().substring(PARAM_URL.length());
			log.info("args[0]=("+url+")");
			
			//不校验本地备份目录参数
			String localBackupFolder = args[1].trim().substring(PARAM_LOCAL_BACKUP_FOLDER.length());
			log.info("args[1]=("+localBackupFolder+")");
			
			//启动spider
			log.info("启动spider，抓取第一个page("+url+"),并保存到本地目录("+localBackupFolder+")中.");
			Spider.create(new CnblogsPersonalBlogProcessor()).addUrl(url)
					.addPipeline(new CnblogsPersonalBlogSaver(localBackupFolder)).thread(5).run();
			

		}
	}
	private static void showUsage(){
		log.warn(USAGE);
		System.out.println(USAGE);
	}
}
