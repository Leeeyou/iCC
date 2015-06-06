package com.ly.cc.event;

import org.json.JSONObject;

/**
 * Created by liuzhifang on 15/6/6.
 */
public class ShowAgeEvent {
    private JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public ShowAgeEvent(JSONObject jsonObject) {

        this.jsonObject = jsonObject;
    }
}
