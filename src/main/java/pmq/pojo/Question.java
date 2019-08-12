package pmq.pojo;

public class Question {
    private Integer id;
    
    private String code;

    private String question;

    private Integer indexId;

    private String des;

    private String optionA;

    private Integer scoreA;

    private String optionB;

    private Integer scoreB;

    private String optionC;

    private Integer scoreC;

    private String optionD;

    private Integer scoreD;

    private String optionE;

    private Integer scoreE;

    private String optionF;

    private Integer scoreF;

    private Integer top;

    private Integer objsub;

    private Integer flag;//保留字符，用于判断是否是必考题
    
    private String post;//此属性并不存在于数据库表格中
    
    private String indexF;//此属性并不存在于数据库表格中
    
    private String indexS;//此属性并不存在于数据库表格中

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public Integer getScoreA() {
        return scoreA;
    }

    public void setScoreA(Integer scoreA) {
        this.scoreA = scoreA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public Integer getScoreB() {
        return scoreB;
    }

    public void setScoreB(Integer scoreB) {
        this.scoreB = scoreB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public Integer getScoreC() {
        return scoreC;
    }

    public void setScoreC(Integer scoreC) {
        this.scoreC = scoreC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public Integer getScoreD() {
        return scoreD;
    }

    public void setScoreD(Integer scoreD) {
        this.scoreD = scoreD;
    }

    public String getOptionE() {
        return optionE;
    }

    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    public Integer getScoreE() {
        return scoreE;
    }

    public void setScoreE(Integer scoreE) {
        this.scoreE = scoreE;
    }

    public String getOptionF() {
        return optionF;
    }

    public void setOptionF(String optionF) {
        this.optionF = optionF;
    }

    public Integer getScoreF() {
        return scoreF;
    }

    public void setScoreF(Integer scoreF) {
        this.scoreF = scoreF;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getObjsub() {
        return objsub;
    }

    public void setObjsub(Integer objsub) {
        this.objsub = objsub;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
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
    
}