package com.xpert.showcase.mb.crud;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.CompanyTypeBO;
import com.xpert.showcase.model.CompanyType;

/**
 *
 * @author #Author
 */
@ManagedBean
@ViewScoped
public class CompanyTypeMB extends AbstractBaseBean<CompanyType> {

    @EJB
    private CompanyTypeBO companyTypeBO;

    @Override
    public AbstractBusinessObject getBO() {
        return companyTypeBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
