#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import ${package}.dao.EstadoDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import ${package}.model.Estado;

/**
 *
 * @author
 */
@Stateless
public class EstadoBO extends AbstractBusinessObject<Estado> {

    @EJB
    private EstadoDAO estadoDAO;
    
    @Override
    public BaseDAO getDAO() {
        return estadoDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
         return new ArrayList<UniqueField>() {  
            {  
                add(new UniqueField("descricao", "pais"));  
                add(new UniqueField("sigla", "pais"));  
            }  
        }; 
    }

    @Override
    public void validate(Estado estado) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
