package com.fm.mq.domain;

public class SubMsg extends Msg {

    private String sub;


    public SubMsg() {
    }

    public SubMsg(String str, String type, String sub) {
        super(str, type);
        this.sub = sub;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }


    @Override
    public String toString() {
        return "SubMsg{" +
                "sub='" + sub + '\'' +
                '}';
    }
}
