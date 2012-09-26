package ${configuration.managedBean};


import java.io.Serializable;
import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ${configuration.businessObject}.${name}BO;
import ${entity.name};

/**
 *
 * @author ${author}
 */
@ManagedBean
@ViewScoped
public class ${name}MB extends AbstractBaseBean<${name}> implements Serializable {

    @EJB
    private ${name}BO ${nameLower}BO;

    @Override
    public AbstractBusinessObject getBO() {
        return ${nameLower}BO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
