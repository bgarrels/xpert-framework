package it.pkg.dao.controleacesso.impl;

import it.pkg.application.BaseDAOImpl;
import it.pkg.dao.controleacesso.AcessoSistemaDAO;
import it.pkg.modelo.controleacesso.AcessoSistema;
import javax.ejb.Stateless;

/**
 *
 * @author ayslan
 */
@Stateless
public class AcessoSistemaDAOImpl extends BaseDAOImpl<AcessoSistema> implements AcessoSistemaDAO {

    @Override
    public Class getEntityClass() {
        return AcessoSistema.class;
    }
    
    
}
