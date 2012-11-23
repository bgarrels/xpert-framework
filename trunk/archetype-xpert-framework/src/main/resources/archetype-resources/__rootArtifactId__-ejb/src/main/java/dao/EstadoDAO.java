#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao;

import com.xpert.persistence.dao.BaseDAO;
import ${package}.model.Estado;
import javax.ejb.Local;

/**
 *
 * @author
 */
@Local
public interface EstadoDAO extends BaseDAO<Estado> {
    
}
