#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.controleacesso;

import ${package}.modelo.controleacesso.Perfil;
import ${package}.modelo.controleacesso.Permissao;
import ${package}.modelo.controleacesso.Usuario;
import com.xpert.persistence.dao.BaseDAO;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.Query;

/**
 *
 * @author ${symbol_pound}Author
 */
@Local
public interface PermissaoDAO extends BaseDAO<Permissao> {

    public List<Permissao> getTodasPermissoes();

    public List<Permissao> getPermissoes(Perfil perfil);

    public List<Permissao> getPermissoes(Usuario usuario);

    public List<Permissao> getPermissoesMenu(Perfil perfil);

    public List<Permissao> getPermissoesAtalhos(Usuario usuario);
}
