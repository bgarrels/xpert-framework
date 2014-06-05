package com.xpert.showcase.mb;

import com.xpert.Constants;
import javax.faces.bean.ManagedBean;

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
        return "4.0";
    }
    
}
