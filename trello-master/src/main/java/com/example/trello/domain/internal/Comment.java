package com.example.trello.domain.internal;

import com.example.trello.domain.Action;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
    private String id;
    private String text;
    private String idMemberCreator;
    private Date date;
    private Action.MemberShort memberCreator;
    private Action.MemberShort member;

    public Comment() {
    }

    public Comment(String text) {
        super();
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIdMemberCreator() {
        return idMemberCreator;
    }

    public void setIdMemberCreator(String idMemberCreator) {
        this.idMemberCreator = idMemberCreator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Action.MemberShort getMemberCreator() {
        return memberCreator;
    }

    public void setMemberCreator(Action.MemberShort memberCreator) {
        this.memberCreator = memberCreator;
    }

    public Action.MemberShort getMember() {
        return member;
    }

    public void setMember(Action.MemberShort member) {
        this.member = member;
    }

}
