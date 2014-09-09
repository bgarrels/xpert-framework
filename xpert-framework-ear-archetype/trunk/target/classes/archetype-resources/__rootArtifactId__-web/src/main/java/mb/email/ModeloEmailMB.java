#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.mb.email;


import java.io.Serializable;
import com.xpert.core.crud.AbstractBaseBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ${package}.bo.email.ModeloEmailBO;
import ${package}.modelo.email.ModeloEmail;

/**
 *
 * @author ayslan
 */
@ManagedBean
@ViewScoped
public class ModeloEmailMB extends AbstractBaseBean<ModeloEmail> implements Serializable {

    @EJB
    private ModeloEmailBO modeloEmailBO;

    @Override
    public ModeloEmailBO getBO() {
        return modeloEmailBO;
    }

    @Override
    public String getDataModelOrder() {
        return "assunto";
    }
}
