#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.configuracao.impl;

import ${package}.application.BaseDAOImpl;
import ${package}.dao.configuracao.ErroSistemaDAO;
import ${package}.modelo.configuracao.ErroSistema;
import javax.ejb.Stateless;

/**
 *
 * @author
 */
@Stateless
public class ErroSistemaDAOImpl extends BaseDAOImpl<ErroSistema> implements ErroSistemaDAO {
}
