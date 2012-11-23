package it.pkg.mb;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import it.pkg.bo.EstadoBO;
import it.pkg.model.Estado;

/**
 *
 * @author
 */
@ManagedBean
@ViewScoped
public class EstadoMB extends AbstractBaseBean<Estado> {

    @EJB
    private EstadoBO estadoBO;

    @Override
    public AbstractBusinessObject getBO() {
        return estadoBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
