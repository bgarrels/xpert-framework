#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.exemplo;

import com.xpert.persistence.dao.BaseDAO;
import ${package}.modelo.exemplo.PessoaExemplo;
import javax.ejb.Local;

/**
 *
 * @author ayslan
 */
@Local
public interface PessoaExemploDAO extends BaseDAO<PessoaExemplo> {
    
}
