#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.impl;

import ${package}.application.BaseDAOImpl;
import ${package}.dao.PaisDAO;
import ${package}.model.Pais;
import javax.ejb.Stateless;

/**
 *
 * @author
 */
@Stateless
public class PaisDAOImpl extends BaseDAOImpl<Pais> implements PaisDAO {
}
