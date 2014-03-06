package com.xpert.showcase.dao;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.model.UserRole;
import javax.ejb.Local;

/**
 *
 * @author Ayslan
 */
@Local
public interface UserRoleDAO extends BaseDAO<UserRole> {
    
}
