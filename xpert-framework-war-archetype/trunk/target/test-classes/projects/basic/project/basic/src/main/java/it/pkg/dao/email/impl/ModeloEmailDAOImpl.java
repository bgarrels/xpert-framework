package it.pkg.dao.email.impl;

import it.pkg.application.BaseDAOImpl;
import it.pkg.dao.email.ModeloEmailDAO;
import it.pkg.modelo.email.ModeloEmail;
import javax.ejb.Stateless;

/**
 *
 * @author ayslan
 */
@Stateless
public class ModeloEmailDAOImpl extends BaseDAOImpl<ModeloEmail> implements ModeloEmailDAO {

    @Override
    public Class getEntityClass() {
        return ModeloEmail.class;
    }

}
