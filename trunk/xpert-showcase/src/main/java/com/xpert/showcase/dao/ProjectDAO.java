package com.xpert.showcase.dao;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.model.Project;
import javax.ejb.Local;

/**
 *
 * @author Ayslan
 */
@Local
public interface ProjectDAO extends BaseDAO<Project> {
    
}
