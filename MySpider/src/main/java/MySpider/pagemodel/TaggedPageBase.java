package MySpider.pagemodel;

import java.util.List;

import us.codecraft.webmagic.Page;

public class TaggedPageBase implements TaggedPage{
	
	public static String regexOfIdentify=".*";
	public static String identify="";
	
	private String name;
	private String content;
	private long generatedTimeMs;

	public boolean isIdentified(Page page, String regexOfIdentify, String identify) {
		String flag=page.getHtml().xpath(regexOfIdentify).toString();
		if (flag!=null&&flag.equals(identify)){
			return true;
		}else {
			return false;
		}
	}

	public String getAString(Page page, String regex) {
		return page.getHtml().xpath(regex).toString();
	}

	public List<String> getStrings(Page page, String regex) {
		 return page.getHtml().xpath(regex).all();
	}

	public void scanTaggedPageSuccessor(Page page, String... regexs) {
		for (int i=0; i<regexs.length; i++){
			page.addTargetRequests(page.getHtml().links().regex(regexs[i]).all());
		}
		
	}
}
