package com.liferay.faces.demos.bean;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "testError")
@SessionScoped
public class TestErrorController {
    protected boolean throwRenderException;

    public void causeActionError() {
    	System.err.println("causeActionError: ...");
        throw new IllegalArgumentException("causeActionError: Deliberate action exception");
    }

    public void causeRenderError() {
    	System.err.println("causeRenderError: ...");
        throwRenderException = true;
    }

    public String getStatus() {
    	System.err.println("getStatus: ...");
        if (throwRenderException) {
            throwRenderException = false;
            throw new IllegalArgumentException("getStatus: Deliberate render exception");
        } else {
            return "getStatus: Updated at " + new Date();
        }
    }
}
