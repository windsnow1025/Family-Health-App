package com.project.Pojo;

/**
 * 取名为History 但实际上Report和History的都是用这个类封装数据
 * 区别在于remind
 * */
public class History{
    private Integer ID;
    private Integer No;
    private String content;
    private String remind;

    public Integer getID() {
        return ID;
    }

    public Integer getNo() {
        return No;
    }

    public String getContent() {
        return content;
    }

    public String getRemind() {
        return remind;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setNo(Integer no) {
        No = no;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }
}
