#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bo.email;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import ${package}.dao.email.ModeloEmailDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import ${package}.modelo.email.ModeloEmail;
import ${package}.modelo.email.TipoAssuntoEmail;
import com.xpert.core.validation.UniqueFields;

/**
 *
 * @author ayslan
 */
@Stateless
public class ModeloEmailBO extends AbstractBusinessObject<ModeloEmail> {

    @EJB
    private ModeloEmailDAO modeloEmailDAO;
    
    @Override
    public ModeloEmailDAO getDAO() {
        return modeloEmailDAO;
    }
    
    public ModeloEmail getModeloEmail(TipoAssuntoEmail tipoAssuntoEmail) throws BusinessException{
        ModeloEmail modeloEmail = modeloEmailDAO.unique("tipoAssuntoEmail", tipoAssuntoEmail);
        if(modeloEmail == null){
            throw new BusinessException("business.modeloEmailNaoCadastrado", tipoAssuntoEmail.getDescricao());
        }
        return modeloEmail;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return new UniqueFields().add("tipoAssuntoEmail");
    }

    @Override
    public void validate(ModeloEmail modeloEmail) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
