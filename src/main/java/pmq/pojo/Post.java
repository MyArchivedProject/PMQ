package pmq.pojo;
//自定义的实体类，数据库中不存在这个表格
public class Post {
	String post;
	Integer indexSNum;//保存某个职位的所有客观题题目数量
	Integer objNum;//保存某个职位的所有客观题题目数量
	Integer subNum;//保存某个职位的所有主观题题目数量
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public Integer getIndexSNum() {
		return indexSNum;
	}
	public void setIndexSNum(Integer indexSNum) {
		this.indexSNum = indexSNum;
	}
	public Integer getObjNum() {
		return objNum;
	}
	public void setObjNum(Integer objNum) {
		this.objNum = objNum;
	}
	public Integer getSubNum() {
		return subNum;
	}
	public void setSubNum(Integer subNum) {
		this.subNum = subNum;
	}
	
}
