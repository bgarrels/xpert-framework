#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.configuracao;

import ${package}.modelo.configuracao.ErroSistema;
import com.xpert.persistence.dao.BaseDAO;
import javax.ejb.Local;

/**
 *
 * @author
 */
@Local
public interface ErroSistemaDAO extends BaseDAO<ErroSistema> {
    
}
