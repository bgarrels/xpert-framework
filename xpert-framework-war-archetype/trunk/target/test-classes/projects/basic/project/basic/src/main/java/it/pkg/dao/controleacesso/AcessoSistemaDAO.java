package it.pkg.dao.controleacesso;

import com.xpert.persistence.dao.BaseDAO;
import it.pkg.modelo.controleacesso.AcessoSistema;
import javax.ejb.Local;

/**
 *
 * @author ayslan
 */
@Local
public interface AcessoSistemaDAO extends BaseDAO<AcessoSistema> {
    
}
