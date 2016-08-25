package MySpider.pagemodel;

import java.util.List;

import us.codecraft.webmagic.Page;

public interface ITaggedPage {
	/**
	 * TAGGED_PAGE_FLAG 全局标记
	 */
	public static final String TAGGED_PAGE_FLAG="taggedPage";
	
	/**
	 * 鉴定page是否为符合定义的目标page
	 * @param page 待鉴定page
	 * @return
	 */
	public boolean isIdentified(Page page);
	/**
	 * page中需要继续
	 * @param page
	 * @param regexs
	 */
	public void setSuccessorTaggedPage(Page page);
}
