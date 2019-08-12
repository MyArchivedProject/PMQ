package pmq.pojo;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class Tester {
	private Integer id;

	@Size(min=1,max=25,message="{tester.name}")
	private String name;
	@Email(message="{tester.email}")@Size(max=50)
	private String email;
	@Size(min=11,max=11,message="{tester.tele}")
	private String tele;
	@NotEmpty(message="{tester.post}")
	private String post;
	
    private Integer numObj;

    private Integer numSub;

    private Date time;

    private String teacher;

    private Integer totalObj;

    private Integer totalSub;

    private Integer total;

    private Integer knowScore;

    private Integer experienceScore;

    private Integer skillScore;

    private Integer styleScore;

    private Integer totalPaper;

    private Integer valueScore;

    private Integer subPaper;

    private Integer objPaper;

    private Integer knowPaper;

    private Integer experiencePaper;

    private Integer skillPaper;

    private Integer stylePaper;

    private Integer valuePaper;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getTotalObj() {
        return totalObj;
    }

    public void setTotalObj(Integer totalObj) {
        this.totalObj = totalObj;
    }

    public Integer getTotalSub() {
        return totalSub;
    }

    public void setTotalSub(Integer totalSub) {
        this.totalSub = totalSub;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getKnowScore() {
        return knowScore;
    }

    public void setKnowScore(Integer knowScore) {
        this.knowScore = knowScore;
    }

    public Integer getExperienceScore() {
        return experienceScore;
    }

    public void setExperienceScore(Integer experienceScore) {
        this.experienceScore = experienceScore;
    }

    public Integer getSkillScore() {
        return skillScore;
    }

    public void setSkillScore(Integer skillScore) {
        this.skillScore = skillScore;
    }

    public Integer getStyleScore() {
        return styleScore;
    }

    public void setStyleScore(Integer styleScore) {
        this.styleScore = styleScore;
    }

    public Integer getTotalPaper() {
        return totalPaper;
    }

    public void setTotalPaper(Integer totalPaper) {
        this.totalPaper = totalPaper;
    }

    public Integer getValueScore() {
        return valueScore;
    }

    public void setValueScore(Integer valueScore) {
        this.valueScore = valueScore;
    }

    public Integer getSubPaper() {
        return subPaper;
    }

    public void setSubPaper(Integer subPaper) {
        this.subPaper = subPaper;
    }

    public Integer getObjPaper() {
        return objPaper;
    }

    public void setObjPaper(Integer objPaper) {
        this.objPaper = objPaper;
    }

    public Integer getKnowPaper() {
        return knowPaper;
    }

    public void setKnowPaper(Integer knowPaper) {
        this.knowPaper = knowPaper;
    }

    public Integer getExperiencePaper() {
        return experiencePaper;
    }

    public void setExperiencePaper(Integer experiencePaper) {
        this.experiencePaper = experiencePaper;
    }

    public Integer getSkillPaper() {
        return skillPaper;
    }

    public void setSkillPaper(Integer skillPaper) {
        this.skillPaper = skillPaper;
    }

    public Integer getStylePaper() {
        return stylePaper;
    }

    public void setStylePaper(Integer stylePaper) {
        this.stylePaper = stylePaper;
    }

    public Integer getValuePaper() {
        return valuePaper;
    }

    public void setValuePaper(Integer valuePaper) {
        this.valuePaper = valuePaper;
    }
}