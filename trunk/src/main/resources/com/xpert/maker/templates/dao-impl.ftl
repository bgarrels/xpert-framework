package ${packageInfo.daoImpl};

import ${packageInfo.baseDAO};
import ${packageInfo.dao}.${name}DAO;
import ${entity.name};
import javax.ejb.Stateless;

/**
 *
 * @author ${author}
 */
@Stateless
public class ${name}DAOImpl extends ${packageInfo.baseDAOSimpleName}<${name}> implements ${name}DAO {
}
