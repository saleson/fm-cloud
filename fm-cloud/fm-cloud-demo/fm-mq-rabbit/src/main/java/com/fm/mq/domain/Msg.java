package com.fm.mq.domain;

public class Msg {

    private String str;
    private String type;

    public Msg() {

    }

    public Msg(String str, String type) {
        this.str = str;
        this.type = type;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Msg{" +
                "str='" + str + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
