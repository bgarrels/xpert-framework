package it.pkg.mb.padrao;

import it.pkg.modelo.controleacesso.Perfil;
import it.pkg.modelo.controleacesso.Permissao;
import it.pkg.modelo.controleacesso.SituacaoUsuario;
import it.pkg.modelo.controleacesso.Usuario;
import it.pkg.modelo.email.TipoAssuntoEmail;
import com.xpert.faces.bean.ClassModel;
import com.xpert.persistence.query.Restriction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author
 */
@ManagedBean
@ViewScoped
public class FindAllBean extends com.xpert.faces.bean.FindAllBean {

    private static final Map<Class, ClassModel> MODEL = new HashMap<Class, ClassModel>();

    static {
        put(Permissao.class, "descricao");
        put(Perfil.class, "descricao");
        put(Usuario.class, "nome");
        put(SituacaoUsuario.class, "descricao");
        put(TipoAssuntoEmail.class, "descricao");
    }

    public static void put(Class entity, String order) {
        MODEL.put(entity, new ClassModel(order));
    }

    public static void put(Class entity, Restriction restriction, String order) {
        MODEL.put(entity, new ClassModel(order, restriction, order));
    }

    public static void put(Class entity, List<Restriction> restrictions, String order) {
        MODEL.put(entity, new ClassModel(order, restrictions, order));
    }

    @Override
    public Map<Class, ClassModel> getClassModel() {
        return MODEL;
    }
}
