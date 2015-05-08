package it.pkg.dao.controleacesso.impl;

import it.pkg.application.BaseDAOImpl;
import it.pkg.dao.controleacesso.HistoricoSituacaoUsuarioDAO;
import it.pkg.modelo.controleacesso.HistoricoSituacaoUsuario;
import javax.ejb.Stateless;

/**
 *
 * @author ayslan
 */
@Stateless
public class HistoricoSituacaoUsuarioDAOImpl extends BaseDAOImpl<HistoricoSituacaoUsuario> implements HistoricoSituacaoUsuarioDAO {

    @Override
    public Class getEntityClass() {
        return HistoricoSituacaoUsuario.class;
    }
}
