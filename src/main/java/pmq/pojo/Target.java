package pmq.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class Target {
    private Integer id;

    @NotEmpty@Size(min=1,max=25,message="{index.post}")
    private String post;
    @NotEmpty@Size(min=1,max=25,message="{index.indexF}")
    private String indexF;
    @NotEmpty@Size(min=1,max=25,message="{index.indexS}")
    private String indexS;
    @Min(value=0,message="{index.numObj}")
    private Integer numObj;
    @Min(value=0,message="{index.numSub}")
    private Integer numSub;

    private Integer total;

    private Integer totalSub;//记录题库里主观题数目的总量//数据库里没有此字段
    private Integer totalObj;//记录题库里客观题数目的总量//数据库里没有此字段
    
    
    private Integer allnumObj;//记录题库里主观题数目的总量//优化版
    private Integer allnumSub;
    public Target(){
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getIndexF() {
        return indexF;
    }

    public void setIndexF(String indexF) {
        this.indexF = indexF;
    }

    public String getIndexS() {
        return indexS;
    }

    public void setIndexS(String indexS) {
        this.indexS = indexS;
    }

    public Integer getNumObj() {
        return numObj;
    }

    public void setNumObj(Integer numObj) {
        this.numObj = numObj;
    }

    public Integer getNumSub() {
        return numSub;
    }

    public void setNumSub(Integer numSub) {
        this.numSub = numSub;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
	public Integer getTotalSub() {
		return totalSub;
	}
	public void setTotalSub(Integer totalSub) {
		this.totalSub = totalSub;
	}
	public Integer getTotalObj() {
		return totalObj;
	}
	public void setTotalObj(Integer totalObj) {
		this.totalObj = totalObj;
	}
	public Integer getAllnumObj() {
		return allnumObj;
	}
	public void setAllnumObj(Integer allnumObj) {
		this.allnumObj = allnumObj;
	}
	public Integer getAllnumSub() {
		return allnumSub;
	}
	public void setAllnumSub(Integer allnumSub) {
		this.allnumSub = allnumSub;
	}

}