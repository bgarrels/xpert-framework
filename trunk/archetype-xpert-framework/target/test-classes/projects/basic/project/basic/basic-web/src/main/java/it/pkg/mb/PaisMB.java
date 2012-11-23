package it.pkg.mb;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import it.pkg.bo.PaisBO;
import it.pkg.model.Pais;

/**
 *
 * @author
 */
@ManagedBean
@ViewScoped
public class PaisMB extends AbstractBaseBean<Pais> {

    @EJB
    private PaisBO paisBO;

    @Override
    public AbstractBusinessObject getBO() {
        return paisBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
