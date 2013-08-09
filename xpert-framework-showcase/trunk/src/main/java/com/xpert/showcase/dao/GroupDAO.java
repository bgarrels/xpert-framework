/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.showcase.dao;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.model.Group;
import javax.ejb.Local;

/**
 *
 * @author Ayslan
 */
@Local
public interface GroupDAO extends BaseDAO<Group> {
    
}
