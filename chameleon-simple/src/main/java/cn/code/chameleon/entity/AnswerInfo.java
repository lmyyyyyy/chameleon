package cn.code.chameleon.entity;

import java.io.Serializable;

/**
 * @author liumingyu
 * @create 2018-04-24 下午6:01
 */
public class AnswerInfo implements Serializable {

    private static final long serialVersionUID = 1521933667517532658L;

    private String answerAuthor;

    private String answerVote;

    private String answerComment;

    private String answerDate;

    private String answerContent;

    public String getAnswerAuthor() {
        return answerAuthor;
    }

    public void setAnswerAuthor(String answerAuthor) {
        this.answerAuthor = answerAuthor;
    }

    public String getAnswerVote() {
        return answerVote;
    }

    public void setAnswerVote(String answerVote) {
        this.answerVote = answerVote;
    }

    public String getAnswerComment() {
        return answerComment;
    }

    public void setAnswerComment(String answerComment) {
        this.answerComment = answerComment;
    }

    public String getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(String answerDate) {
        this.answerDate = answerDate;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    @Override
    public String toString() {
        return "AnswerInfo{" +
                "answerAuthor='" + answerAuthor + '\'' +
                ", answerVote='" + answerVote + '\'' +
                ", answerComment='" + answerComment + '\'' +
                ", answerDate='" + answerDate + '\'' +
                ", answerContent='" + answerContent + '\'' +
                '}';
    }
}
