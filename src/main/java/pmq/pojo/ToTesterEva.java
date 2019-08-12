package pmq.pojo;
//此实体用于封装返回给测试者的等级评价内容//未了方便使用json进行数据传输
public class ToTesterEva {
	String evaName;
	String evaContent;
	public String getEvaName() {
		return evaName;
	}
	public void setEvaName(String evaName) {
		this.evaName = evaName;
	}
	public String getEvaContent() {
		return evaContent;
	}
	public void setEvaContent(String evaContent) {
		this.evaContent = evaContent;
	}
	
}
