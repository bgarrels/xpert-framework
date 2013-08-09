#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.mb;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ${package}.bo.CidadeBO;
import ${package}.model.Cidade;

/**
 *
 * @author
 */
@ManagedBean
@ViewScoped
public class CidadeMB extends AbstractBaseBean<Cidade> {

    @EJB
    private CidadeBO cidadeBO;

    @Override
    public AbstractBusinessObject getBO() {
        return cidadeBO;
    }

    @Override
    public String getDataModelOrder() {
        return "descricao";
    }
}
