package it.pkg.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import it.pkg.dao.PaisDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import it.pkg.model.Pais;

/**
 *
 * @author
 */
@Stateless
public class PaisBO extends AbstractBusinessObject<Pais> {

    @EJB
    private PaisDAO paisDAO;
    
    @Override
    public BaseDAO getDAO() {
        return paisDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
       return new ArrayList<UniqueField>() {  
            {  
                add(new UniqueField("descricao"));  
            }  
        }; 
    }

    @Override
    public void validate(Pais pais) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
