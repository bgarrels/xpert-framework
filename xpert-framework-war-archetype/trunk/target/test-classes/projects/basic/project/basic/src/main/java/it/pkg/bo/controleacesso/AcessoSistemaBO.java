package it.pkg.bo.controleacesso;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import it.pkg.dao.controleacesso.AcessoSistemaDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import it.pkg.modelo.controleacesso.AcessoSistema;
import it.pkg.modelo.controleacesso.Usuario;
import com.xpert.faces.utils.FacesUtils;
import java.util.Date;

/**
 *
 * @author ayslan
 */
@Stateless
public class AcessoSistemaBO extends AbstractBusinessObject<AcessoSistema> {

    @EJB
    private AcessoSistemaDAO acessoSistemaDAO;
    
    @Override
    public BaseDAO getDAO() {
        return acessoSistemaDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }
    
    public void save(Usuario usuario){
        AcessoSistema acessoSistema = new AcessoSistema();
        acessoSistema.setDataHora(new Date());
        acessoSistema.setIp(FacesUtils.getIP());
        acessoSistema.setUserAgent(FacesUtils.getBrowser());
        acessoSistema.setUsuario(usuario);
        acessoSistemaDAO.merge(acessoSistema, false);
    }

    @Override
    public void validate(AcessoSistema acessoSistema) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
