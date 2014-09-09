#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.controleacesso;

import ${package}.modelo.controleacesso.Usuario;
import com.xpert.persistence.dao.BaseDAO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ${symbol_pound}Author
 */
@Local
public interface UsuarioDAO extends BaseDAO<Usuario> {
    
    public List<Usuario> getUsuariosAtivos();
    
}
