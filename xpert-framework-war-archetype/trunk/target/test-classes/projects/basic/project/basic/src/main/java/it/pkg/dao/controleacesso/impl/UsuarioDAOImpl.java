package it.pkg.dao.controleacesso.impl;

import it.pkg.application.BaseDAOImpl;
import it.pkg.dao.controleacesso.UsuarioDAO;
import it.pkg.modelo.controleacesso.SituacaoUsuario;
import it.pkg.modelo.controleacesso.Usuario;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author #Author
 */
@Stateless
public class UsuarioDAOImpl extends BaseDAOImpl<Usuario> implements UsuarioDAO {

    @Override
    public Class getEntityClass() {
        return Usuario.class;
    }

    @Override
    public List<Usuario> getUsuariosAtivos() {
        return list("situacaoUsuario", SituacaoUsuario.ATIVO, "nome");
    }
    
    
}
