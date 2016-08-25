package MySpider.pagemodel;

public class Action {
	public static final int NO_ACTION = 1; // 默认action，等价于mute哑状态，意味着并不额外增加操作；
	public static final int LOG = 2; // 记录日志（需自定义如何记录日志）
	public static final int DUMP = 3; // 执行转储（需自定义如何转储）
	public static final int SAVE_AS_HTML = 4; // 保存为扩展名html（不支持htm）
	public static final int SAVE_AS_TXT = 5; // 保存为扩展名txt
	public static final int SAVE_AS_RAW = 6; // 保存为扩展名.raw

	private int action;

	public Action(int action) {
		super();
		this.action = action;
	}

	public Action() {
		super();
		this.action = NO_ACTION;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

}
