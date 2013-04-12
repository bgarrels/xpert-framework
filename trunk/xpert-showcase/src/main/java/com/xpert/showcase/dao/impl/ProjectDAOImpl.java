package com.xpert.showcase.dao.impl;

import com.xpert.showcase.application.BaseDAOImpl;
import com.xpert.showcase.dao.ProjectDAO;
import com.xpert.showcase.model.Project;
import javax.ejb.Stateless;

/**
 *
 * @author Ayslan
 */
@Stateless
public class ProjectDAOImpl extends BaseDAOImpl<Project> implements ProjectDAO {
}
