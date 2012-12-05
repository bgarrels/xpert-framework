package it.pkg.mb;

import it.pkg.model.Pais;
import it.pkg.model.Cidade;
import it.pkg.model.Estado;
import com.xpert.faces.bean.ClassModel;
import java.util.HashMap;
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
        put(Pais.class, "descricao");
        put(Estado.class, "descricao");
        put(Cidade.class, "descricao");
    }

    public static void put(Class entity, String order) {
        MODEL.put(entity, new ClassModel(order));
    }

    @Override
    public Map<Class, ClassModel> getClassModel() {
        return MODEL;
    }
}