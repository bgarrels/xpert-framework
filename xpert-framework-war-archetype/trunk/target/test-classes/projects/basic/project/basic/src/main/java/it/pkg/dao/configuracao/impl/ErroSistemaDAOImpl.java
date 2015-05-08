package it.pkg.dao.configuracao.impl;

import it.pkg.application.BaseDAOImpl;
import it.pkg.dao.configuracao.ErroSistemaDAO;
import it.pkg.modelo.configuracao.ErroSistema;
import javax.ejb.Stateless;

/**
 *
 * @author
 */
@Stateless
public class ErroSistemaDAOImpl extends BaseDAOImpl<ErroSistema> implements ErroSistemaDAO {

    @Override
    public Class getEntityClass() {
        return ErroSistema.class;
    }
    
    
    
}
