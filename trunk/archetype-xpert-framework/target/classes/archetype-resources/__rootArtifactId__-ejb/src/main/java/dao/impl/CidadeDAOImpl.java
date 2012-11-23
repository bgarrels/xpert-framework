#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.impl;

import ${package}.application.BaseDAOImpl;
import ${package}.model.Cidade;
import ${package}.dao.CidadeDAO;
import javax.ejb.Stateless;

/**
 *
 * @author
 */
@Stateless
public class CidadeDAOImpl extends BaseDAOImpl<Cidade> implements CidadeDAO {
}
