package MySpider.pagetag;

import java.util.HashMap;

public class TagDrivenAction{
	HashMap<Tag, Action> hashMap = new HashMap<Tag, Action>();

	public HashMap<Tag, Action> getHashMap() {
		return hashMap;
	}

	public void put(Tag tag, Action action) {
		this.hashMap.put(tag, action);
	}

	public TagDrivenAction(int action, HashMap<Tag, Action> hashMap) {
		this.hashMap = hashMap;
	}


}
