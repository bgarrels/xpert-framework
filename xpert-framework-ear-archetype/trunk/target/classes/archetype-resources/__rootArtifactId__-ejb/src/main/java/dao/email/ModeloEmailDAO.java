#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.email;

import com.xpert.persistence.dao.BaseDAO;
import ${package}.modelo.email.ModeloEmail;
import javax.ejb.Local;

/**
 *
 * @author ayslan
 */
@Local
public interface ModeloEmailDAO extends BaseDAO<ModeloEmail> {
    
}
