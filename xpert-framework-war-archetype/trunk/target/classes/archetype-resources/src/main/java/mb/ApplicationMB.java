#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.mb;

import ${package}.GeracaoDadosSistema;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author ayslan
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class ApplicationMB {

    @EJB
    private GeracaoDadosSistema geracaoDadosSistema;

    @PostConstruct
    public void init() {
        //gerar permissoes ao iniciar aplicacao
        geracaoDadosSistema.generate();
    }
}
