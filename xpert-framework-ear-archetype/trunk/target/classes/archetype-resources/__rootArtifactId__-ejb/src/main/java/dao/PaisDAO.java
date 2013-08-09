#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao;

import com.xpert.persistence.dao.BaseDAO;
import ${package}.model.Pais;
import javax.ejb.Local;

/**
 *
 * @author
 */
@Local
public interface PaisDAO extends BaseDAO<Pais> {
    
}
