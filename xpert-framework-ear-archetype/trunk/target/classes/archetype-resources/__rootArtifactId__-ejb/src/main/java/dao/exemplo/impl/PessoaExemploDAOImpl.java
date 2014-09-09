#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.exemplo.impl;

import ${package}.application.BaseDAOImpl;
import ${package}.dao.exemplo.PessoaExemploDAO;
import ${package}.modelo.exemplo.PessoaExemplo;
import javax.ejb.Stateless;

/**
 *
 * @author ayslan
 */
@Stateless
public class PessoaExemploDAOImpl extends BaseDAOImpl<PessoaExemplo> implements PessoaExemploDAO {
}
