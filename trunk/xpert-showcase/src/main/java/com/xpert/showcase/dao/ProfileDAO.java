package com.xpert.showcase.dao;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.model.Profile;
import javax.ejb.Local;

/**
 *
 * @author #Author
 */
@Local
public interface ProfileDAO extends BaseDAO<Profile> {
    
}
