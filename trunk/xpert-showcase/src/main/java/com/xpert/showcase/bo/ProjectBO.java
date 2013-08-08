package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.ProjectDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.Project;

/**
 *
 * @author ayslan
 */
@Stateless
public class ProjectBO extends AbstractBusinessObject<Project> {

    @EJB
    private ProjectDAO projectDAO;
    
    @Override
    public ProjectDAO getDAO() {
        return projectDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public void validate(Project project) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
