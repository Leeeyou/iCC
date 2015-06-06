package com.ly.cc.event;

import com.facepp.error.FaceppParseException;

/**
 * Created by liuzhifang on 15/6/6.
 */
public class FaceppParseExceptionEvent {
    private FaceppParseException e;

    public FaceppParseExceptionEvent(FaceppParseException e) {
        this.e = e;
    }

    public FaceppParseException getE() {
        return e;
    }
}
