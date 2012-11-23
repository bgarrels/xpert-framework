#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.mb;

import ${package}.model.Cidade;
import ${package}.model.Estado;
import ${package}.model.Pais;
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
