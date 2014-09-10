#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.controleacesso;

import com.xpert.persistence.dao.BaseDAO;
import ${package}.modelo.controleacesso.HistoricoSituacaoUsuario;
import javax.ejb.Local;

/**
 *
 * @author ayslan
 */
@Local
public interface HistoricoSituacaoUsuarioDAO extends BaseDAO<HistoricoSituacaoUsuario> {
    
}
