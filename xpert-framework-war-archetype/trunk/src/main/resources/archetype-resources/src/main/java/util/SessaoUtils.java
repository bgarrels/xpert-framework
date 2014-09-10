#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util;

import ${package}.modelo.controleacesso.Usuario;
import com.xpert.faces.utils.FacesUtils;
import javax.faces.context.FacesContext;

/**
 * Classe utilitaria para acesso de dados da sessao
 * 
 * @author Ayslan
 */
public class SessaoUtils {

    /**
     * 
     * @return o Usuario logado na aplicacao, ou seja o usuario setado no ${symbol_pound}{sessaoUsuarioMB.user}
     */
    public static Usuario getUser() {
        if (FacesContext.getCurrentInstance() != null) {
            return (Usuario) FacesUtils.getBeanByEl("${symbol_pound}{sessaoUsuarioMB.user}");
        }
        return null;
    }

}
