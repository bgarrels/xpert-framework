package com.xpert.showcase.mb;

import com.xpert.Constants;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class ConstantMB {
    
    public String getXpertFrameworkVersion(){
        return Constants.VERSION;
    }
    public String getPrimefacesVersion(){
        return RequestContext.getCurrentInstance().getApplicationContext().getConfig().getBuildVersion();
    }
    
}
