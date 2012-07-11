package com.xpert.showcase.dao.impl;

import com.xpert.showcase.application.BaseDAOImpl;
import com.xpert.showcase.dao.ProfileDAO;
import com.xpert.showcase.model.Profile;
import javax.ejb.Stateless;

/**
 *
 * @author #Author
 */
@Stateless
public class ProfileDAOImpl extends BaseDAOImpl<Profile> implements ProfileDAO {
}
