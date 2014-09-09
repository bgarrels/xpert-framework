#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao;

import com.xpert.persistence.dao.BaseDAO;
import javax.ejb.Local;

/**
 *
 * @author Ayslan
 */
@Local
public interface DAO extends BaseDAO {

    public <T> BaseDAO<T> getDAO(Class<T> entity);
}
