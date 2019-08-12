package pmq.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class Evaluation {
    private Integer id;
    private String code;
    private Integer indexId;
    @NotNull@Size(max=500)
    private String evaA;
    @NotNull
    private Integer miniA;
    @NotNull@Size(max=500)
    private String evaB;
    @NotNull
    private Integer miniB;
    @NotNull@Size(max=500)
    private String evaC;
    @NotNull
    private Integer miniC;
    @NotNull@Size(max=500)
    private String evaD;
    @NotNull
    private Integer miniD;
    @NotNull@Size(max=500)
    private String evaE;
    @NotNull
    private Integer miniE;
    @NotNull@Size(max=500)
    private String evaF;
    @NotNull
    private Integer miniF;
    @NotNull@Size(max=25)
    private String post;
    @Size(max=255)
    private String multiIndex;
    @NotEmpty@Size(max=25)
    private String title;
    private int flag;
    
   // private String message;//此字段数据库是不存在的//用来保存所有的二级指标名称
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public String getEvaA() {
        return evaA;
    }

    public void setEvaA(String evaA) {
        this.evaA = evaA;
    }

    public Integer getMiniA() {
        return miniA;
    }

    public void setMiniA(Integer miniA) {
        this.miniA = miniA;
    }

    public String getEvaB() {
        return evaB;
    }

    public void setEvaB(String evaB) {
        this.evaB = evaB;
    }

    public Integer getMiniB() {
        return miniB;
    }

    public void setMiniB(Integer miniB) {
        this.miniB = miniB;
    }

    public String getEvaC() {
        return evaC;
    }

    public void setEvaC(String evaC) {
        this.evaC = evaC;
    }

    public Integer getMiniC() {
        return miniC;
    }

    public void setMiniC(Integer miniC) {
        this.miniC = miniC;
    }

    public String getEvaD() {
        return evaD;
    }

    public void setEvaD(String evaD) {
        this.evaD = evaD;
    }

    public Integer getMiniD() {
        return miniD;
    }

    public void setMiniD(Integer miniD) {
        this.miniD = miniD;
    }

    public String getEvaE() {
        return evaE;
    }

    public void setEvaE(String evaE) {
        this.evaE = evaE;
    }

    public Integer getMiniE() {
        return miniE;
    }

    public void setMiniE(Integer miniE) {
        this.miniE = miniE;
    }

    public String getEvaF() {
        return evaF;
    }

    public void setEvaF(String evaF) {
        this.evaF = evaF;
    }

    public Integer getMiniF() {
        return miniF;
    }

    public void setMiniF(Integer miniF) {
        this.miniF = miniF;
    }

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getMultiIndex() {
		return multiIndex;
	}

	public void setMultiIndex(String multiIndex) {
		this.multiIndex = multiIndex;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/*public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		message= message;
	}*/

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
    
}