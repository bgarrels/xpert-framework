package com.xpert.showcase.mb;

import com.xpert.faces.utils.FacesMessageUtils;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class MessagesMB {

    public void warn() {
        FacesMessageUtils.warning("Warning message. Warning message. This is a long message, for size test. Very long long message.");
    }

    public void info() {
        FacesMessageUtils.info("Info message. Info message.");
    }

    public void fatal() {

        FacesMessageUtils.fatal("Fatal message. Fatal message.");
    }

    public void error() {
        FacesMessageUtils.error("Error message. Error message.");
    }

    public void all() {
        info();
        info();
        warn();
        warn();
        error();
        error();
        fatal();
        fatal();
    }
}
