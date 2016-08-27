package MySpider.pagemodel;

import us.codecraft.webmagic.Page;

public interface TaggedPage {
	/**
	 * TAGGED_PAGE_FLAG 全局标记
	 */
	public static final String TAGGED_PAGE_FLAG="TAGGED_PAGE";
	
	/**
	 * 鉴定page是否为符合定义的目标page
	 * @return ture - 当前page为目标page
	 */
	public boolean identify();
	/**
	 * 搜索后继的page
	 */
	public void scanSuccessorPage();
	/**
	 * 保存当前页的必要数据
	 */
	public void captureData();
	/**
	 * 设入当前Page 
	 * @param page - 从webmagic的process来
	 */
	public void setPage(Page page);
	/**
	 * 设定子类的类型type名称，用于一些信息输出等方便使用
	 * @param name - sub class name of tagged page
	 */
	public String getPageType();
	/**
	 * 处理page（比如保存为html等）
	 */
	public void handlePage();
	/**
	 * 设定保存的物理路径（不包含文件名），一般用“/”;如果不保存文件可忽略此参数;
	 * @param path
	 */
	public void setSavePath(String path);
	/**
	 * 返回tagged page name;
	 * @return String 
	 */
	public String getName();
	/**
	 * 返回tagged page content;
	 * @return String 
	 */
	public String getContent();
	/**
	 * 返回tagged page name;
	 * @return long 
	 */
	public long getCapturedTimeMs();
	
}
