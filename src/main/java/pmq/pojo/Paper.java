package pmq.pojo;

import java.io.Serializable;

public class Paper implements Serializable{
	private static final long serialVersionUID = -9102017020286042305L;//克隆，所以需要序列化
    private Integer id;

    private Integer testerId;

    private String indexF;

    private Integer indexId;

    private String question;

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

    private Integer flag;

    private String answer;

    private Integer score;

	@Override
	public String toString() {
		return "Paper [id=" + id + ", testerId=" + testerId + ", indexF=" + indexF + ", indexId=" + indexId
				+ ", question=" + question + ", des=" + des + ", optionA=" + optionA + ", scoreA=" + scoreA
				+ ", optionB=" + optionB + ", scoreB=" + scoreB + ", optionC=" + optionC + ", scoreC=" + scoreC
				+ ", optionD=" + optionD + ", scoreD=" + scoreD + ", optionE=" + optionE + ", scoreE=" + scoreE
				+ ", optionF=" + optionF + ", scoreF=" + scoreF + ", top=" + top + ", objsub=" + objsub + ", flag="
				+ flag + ", answer=" + answer + ", score=" + score + "]";
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTesterId() {
        return testerId;
    }

    public void setTesterId(Integer testerId) {
        this.testerId = testerId;
    }

    public String getIndexF() {
        return indexF;
    }

    public void setIndexF(String indexF) {
        this.indexF = indexF;
    }

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}