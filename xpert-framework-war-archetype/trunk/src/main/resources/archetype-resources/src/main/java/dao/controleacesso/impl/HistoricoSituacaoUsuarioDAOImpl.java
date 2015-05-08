#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.controleacesso.impl;

import ${package}.application.BaseDAOImpl;
import ${package}.dao.controleacesso.HistoricoSituacaoUsuarioDAO;
import ${package}.modelo.controleacesso.HistoricoSituacaoUsuario;
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
