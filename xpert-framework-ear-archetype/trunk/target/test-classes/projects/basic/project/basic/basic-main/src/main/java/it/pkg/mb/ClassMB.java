package it.pkg.mb;

import it.pkg.model.Cidade;
import it.pkg.model.Estado;
import it.pkg.model.Pais;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class ClassMB {

    public Class getCidade() {
        return Cidade.class;
    }
    public Class getEstado() {
        return Estado.class;
    }
    public Class getPais() {
        return Pais.class;
    }

}
