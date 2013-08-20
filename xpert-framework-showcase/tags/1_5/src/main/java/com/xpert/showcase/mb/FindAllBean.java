package com.xpert.showcase.mb;

import com.xpert.faces.bean.ClassModel;
import com.xpert.showcase.model.*;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Ayslan
 */
@ManagedBean
@ViewScoped
public class FindAllBean extends com.xpert.faces.bean.FindAllBean {

    private static final Map<Class, ClassModel> MODEL = new HashMap<Class, ClassModel>();

    static {
        MODEL.put(Group.class, new ClassModel("description"));
        MODEL.put(Status.class, new ClassModel("description"));
        MODEL.put(Country.class, new ClassModel("name"));
        MODEL.put(State.class, new ClassModel("name"));
        MODEL.put(City.class, new ClassModel("name"));
    }

    @Override
    public Map<Class, ClassModel> getClassModel() {
        return MODEL;
    }
}
