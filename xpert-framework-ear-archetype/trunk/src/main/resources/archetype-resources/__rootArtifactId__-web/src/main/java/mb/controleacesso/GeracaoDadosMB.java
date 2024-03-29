#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.mb.controleacesso;

import ${package}.GeracaoDadosSistema;
import com.xpert.faces.utils.FacesMessageUtils;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ayslan
 *
 */
@ManagedBean
@SessionScoped
public class GeracaoDadosMB implements Serializable {

    @EJB
    private GeracaoDadosSistema geracaoDadosSistema;

    public void gerarDados() {
        try {
            geracaoDadosSistema.generate();
            FacesMessageUtils.sucess();
        } catch (Exception ex) {
            FacesMessageUtils.fatal("Um erro aconteceu na geração. Erro: " + ex.getMessage() + ". Mais detalhes no log.");
        }
    }
}
