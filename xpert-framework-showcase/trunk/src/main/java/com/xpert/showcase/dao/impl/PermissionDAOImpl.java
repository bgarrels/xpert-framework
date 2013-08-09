package com.xpert.showcase.dao.impl;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.application.BaseDAOImpl;
import com.xpert.showcase.dao.PermissionDAO;
import com.xpert.showcase.model.Permission;
import javax.ejb.Stateless;

/**
 *
 * @author #Insert Author here
 */
@Stateless
public class PermissionDAOImpl extends BaseDAOImpl<Permission> implements PermissionDAO {
}
