package it.pkg.dao.controleacesso;

import it.pkg.modelo.controleacesso.Usuario;
import com.xpert.persistence.dao.BaseDAO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author #Author
 */
@Local
public interface UsuarioDAO extends BaseDAO<Usuario> {
    
    public List<Usuario> getUsuariosAtivos();
    
}
