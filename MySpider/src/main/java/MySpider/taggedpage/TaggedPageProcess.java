package MySpider.taggedpage;

public interface TaggedPageProcess {
	
	/**
	 * 实现类需至少实现针对TaggedPage的独立解析，而不需要依赖其他内容；
	 * @param tdPage
	 */
	void taggedPageProcess(TaggedPage tdPage);

}
