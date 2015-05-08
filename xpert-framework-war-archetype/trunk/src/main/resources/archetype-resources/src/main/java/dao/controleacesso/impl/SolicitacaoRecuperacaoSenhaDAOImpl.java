#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.controleacesso.impl;

import ${package}.application.BaseDAOImpl;
import ${package}.dao.controleacesso.SolicitacaoRecuperacaoSenhaDAO;
import ${package}.modelo.controleacesso.SolicitacaoRecuperacaoSenha;
import javax.ejb.Stateless;

/**
 *
 * @author ayslan
 */
@Stateless
public class SolicitacaoRecuperacaoSenhaDAOImpl extends BaseDAOImpl<SolicitacaoRecuperacaoSenha> implements SolicitacaoRecuperacaoSenhaDAO {

    @Override
    public Class getEntityClass() {
        return SolicitacaoRecuperacaoSenha.class;
    }
}
