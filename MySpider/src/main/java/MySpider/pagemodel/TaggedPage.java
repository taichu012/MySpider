package MySpider.pagemodel;

import java.util.List;

import us.codecraft.webmagic.Page;

public interface TaggedPage {
	public static final String TAGGED_PAGE_FLAG="taggedPage";
	public boolean isIdentified(Page page, String regexOfIdentify, String identify);
	public String getAString(Page page, String regex);
	public List<String> getStrings(Page page, String regex);
	public void scanTaggedPageSuccessor(Page page, String ... regexs);
}
