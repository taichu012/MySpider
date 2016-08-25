package MySpider.pagemodel;

import java.util.List;
import java.util.function.Function;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;

public class TaggedPage implements ITaggedPage{
	private static Logger log = Logger.getLogger(TaggedPage.class);
	
	private String regexOfIdentify=".*";
	private String identify="";
	private List<String> regexOfSuccessorTaggedPage = null;
	
	private String name;
	private String content;
	private long capturedTimeMs;

	public TaggedPage(String regexOfIdentify, String identify
			, List<String> regexOfSuccessorTaggedPage
			//, Isme func
			) {
		this.regexOfIdentify=regexOfIdentify;
		this.identify=identify;
		this.regexOfSuccessorTaggedPage=regexOfSuccessorTaggedPage;
		//this.isme=func;
	}
	
	/*
	public interface Isme {
		boolean isIdentified(Page page);
		boolean a(Page page);
	}
	
	private Isme isme=null;
	
	public boolean Isme(Page page, Isme func){
		func.isIdentified(page);
		func.a(page);
		
		return false;
	}
	
	//private IdentifyPage<Page,boolean> check = (x) -> {return true;};  


	public static Function<Page,Boolean> func = (x) -> {return true;};  
	
	
	static {
		System.out.println(func.apply(new Page()));  
	}
	*/

	
	
	
	

	@Override
	public boolean isIdentified(Page page) {
		if (page==null||regexOfIdentify==null||regexOfIdentify.length()<=0
				||identify==null||identify.length()<=0) return false;
		String flag=page.getHtml().xpath(regexOfIdentify).toString();
		if (flag!=null&&flag.equals(identify)){
			log.info("Find TARGET Page with match identify ["+identify+"].");
			return true;
		}else {
			return false;
		}
	}

	public String getStringField(Page page, String regex) {
		if (page==null||regex==null||regex.length()<=0) return null;
		return page.getHtml().xpath(regex).toString();
	}

	public List<String> getStringFields(Page page, String regex) {
		if (page==null||regex==null||regex.length()<=0) return null;
		 return page.getHtml().xpath(regex).all();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getCapturedTimeMs() {
		return capturedTimeMs;
	}

	public void setCapturedTimeMs(long capturedTimeMs) {
		this.capturedTimeMs = capturedTimeMs;
	}

	@Override
	public void setSuccessorTaggedPage(Page page) {
		if (page==null||regexOfSuccessorTaggedPage==null||regexOfSuccessorTaggedPage.size()<=0) return;
		for (int i=0; i<regexOfSuccessorTaggedPage.size(); i++){
			page.addTargetRequests(page.getHtml().links().regex(regexOfSuccessorTaggedPage.get(i)).all());
		}
	}

	public List<String>getAllLinks(Page page, String regex) {
		if (page==null||regex==null||regex.length()<=0) return null;
		return page.getHtml().xpath(regex).links().all();
	}

}
