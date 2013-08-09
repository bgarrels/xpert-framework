package it.pkg.mb;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import it.pkg.bo.CidadeBO;
import it.pkg.model.Cidade;

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
