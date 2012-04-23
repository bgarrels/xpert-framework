package ${packageInfo.businessObject};

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import ${packageInfo.dao}.${name}DAO;
import com.xpert.core.validation.UniqueField;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ${author}
 */
@Stateless
public class ${name}BO extends AbstractBusinessObject {

    @EJB
    private ${name}DAO ${nameLower}DAO;
    
    @Override
    public BaseDAO getDAO() {
        return ${nameLower}DAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

}
