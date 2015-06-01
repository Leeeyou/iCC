package com.ly.cc.bean.custom_controls;

/**
 * Created by liuzhifang on 15/5/23.
 */
public class VerticalTimelineBean {
    private String time;
    private String content;

    public VerticalTimelineBean(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public VerticalTimelineBean() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
