#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.impl;

import ${package}.application.BaseDAOImpl;
import ${package}.dao.EstadoDAO;
import ${package}.model.Estado;
import javax.ejb.Stateless;

/**
 *
 * @author
 */
@Stateless
public class EstadoDAOImpl extends BaseDAOImpl<Estado> implements EstadoDAO {
}
