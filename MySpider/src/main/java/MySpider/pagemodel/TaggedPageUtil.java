package MySpider.pagemodel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import org.apache.log4j.Logger;

import MySpider.pagemodel.TaggedPage;
import us.codecraft.webmagic.Page;

public class TaggedPageUtil {
	private static Logger log = Logger.getLogger(TaggedPageUtil.class);	
	private static TaggedPageUtil instance=null;
	public static TaggedPageUtil getTPU() {
		return instance==null? new TaggedPageUtil():instance;
	}
	public static final String PATH_SEPERATOR = "/";	


	/**
	 * 找到符合regex正则表达式的target目标，则为ture
	 * @param page
	 * @param regex
	 * @param target
	 * @return
	 */
	public boolean identifiedByCaptureTarget(Page page, String regex, String target) {
		if (page==null||regex==null||regex.length()<=0
				||target==null||target.length()<=0) return false;
		String flag=page.getHtml().xpath(regex).toString();
		if (flag!=null&&flag.equals(target)){
			log.debug("Find TARGET Page with match identify ["+target+"].");
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 找到符合regex正则表达式的任何非空值，则为ture
	 * @param page
	 * @param regex
	 * @return
	 */
	public boolean identifiedByCaptureNotNull(Page page, String regex) {
		if (page==null) return false;
		String flag=page.getHtml().xpath(regex).toString();
		if (flag!=null&&flag.length()>=1){
        	log.info("Find Blog Page with Title["+flag+"].");
			return true;
		}else {
			return false;
		}
	}
	
	public String getRawPage(Page page) {
		if (page==null) return null;
		return page.getRawText();
	}

	public String getStringField(Page page, String regex) {
		if (page==null||regex==null||regex.length()<=0) return null;
		return page.getHtml().xpath(regex).toString();
	}

	public List<String> getStringFields(Page page, String regex) {
		if (page==null||regex==null||regex.length()<=0) return null;
		 return page.getHtml().xpath(regex).all();
	}


	/**
	 * 设定后继多个需要继续搜索的urls的正则表达式list
	 * @param page
	 * @param regex
	 * @return
	 */
	public void scanSuccessorPageByRegexs(Page page, List<String> regexs) {
		if (page==null||regexs==null||regexs.size()<=0) return;
		for (int i=0; i<regexs.size(); i++){
			page.addTargetRequests(page.getHtml().links().regex(regexs.get(i)).all());
		}
	}

	public List<String>getAllLinks(Page page, String regex) {
		if (page==null||regex==null||regex.length()<=0) return null;
		return page.getHtml().xpath(regex).links().all();
	}
	
	public void log(TaggedPage tdPage) {
		log.warn("Method 'log(TaggedPage tdPage)' has not implemented yet!");
	}

	public void dump(TaggedPage tdPage) {
		log.warn("Method 'dump(TaggedPage tdPage)' has not implemented yet!");
	}

	public void savePageAsExtName(TaggedPage tdPage, String extensionName, String pathNoFilename) {
		String filename = tdPage.getName();
		if (filename==null||filename.length()==0){
			log.warn("Page title is empty and cannot be saved!");
			return;
		};
		String filebody = tdPage.getContent();
		long grabPageTimeMs = tdPage.getCapturedTimeMs();
		String pathAndFileName = null;

		//String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
		try {
			pathAndFileName = pathNoFilename + filename + '.'+extensionName;
			PrintWriter printWriter = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream(getFile(pathAndFileName)), "UTF-8"));
			printWriter.println(filebody);
			printWriter.close();
			log.debug("("+pathAndFileName+") is saved.");
		} catch (IOException e) {
			log.warn("write file("+pathAndFileName+") error!", e);
		}
	}
	
    public File getFile(String fullName) { 
         checkAndMakeParentDirecotry(fullName); 
         return new File(fullName); 
     } 
    
    public void checkAndMakeParentDirecotry(String fullName) { 
         int index = fullName.lastIndexOf(PATH_SEPERATOR); 
         if (index > 0) { 
             String path = fullName.substring(0, index); 
             File file = new File(path); 
             if (!file.exists()) { 
                file.mkdirs(); 
            } 
         } 
     } 


}
