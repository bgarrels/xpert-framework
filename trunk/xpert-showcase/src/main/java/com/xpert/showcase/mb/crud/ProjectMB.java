package com.xpert.showcase.mb.crud;


import java.io.Serializable;
import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.ProjectBO;
import com.xpert.showcase.model.Project;

/**
 *
 * @author Ayslan
 */
@ManagedBean
@ViewScoped
public class ProjectMB extends AbstractBaseBean<Project> implements Serializable {

    @EJB
    private ProjectBO projectBO;

    @Override
    public AbstractBusinessObject getBO() {
        return projectBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
