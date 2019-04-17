package com.enema.enemaapp.models;

public class SupportData {

    SupportData(){}

    String support_question, support_answer;

    public SupportData(String support_question, String support_answer) {
        this.support_question = support_question;
        this.support_answer = support_answer;
    }

    public String getSupport_question() {
        return support_question;
    }

    public String getSupport_answer() {
        return support_answer;
    }
}
