/**
 * 
 */
package MySpider.spider;

import MySpider.process.GithubRepoPageProcessor;
import MySpider.process.annotation.GithubRepo;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;

/**
 * @author User
 *
 */
public class GithubSpider {

	public static void main(String[] args) {
		// 运行github专门spider（传统spdier类型）
		runGithubSpider();

		// 运行github专门spider（新的annotation的spdier类型）
		//runGithubAnnotationSpider();
	}

	public static void runGithubSpider() {
		// 运行github专门spider（传统spdier类型）
		Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
	}

	public static void runGithubAnnotationSpider() {
		// 运行github专门spider（新的annotation的spdier类型）
		OOSpider.create(Site.me().setSleepTime(1000), new ConsolePageModelPipeline(), GithubRepo.class)
				.addUrl("https://github.com/code4craft").thread(5).run();

	}

}
