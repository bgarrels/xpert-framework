#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bo.controleacesso;

import ${package}.dao.controleacesso.SolicitacaoRecuperacaoSenhaDAO;
import ${package}.dao.controleacesso.UsuarioDAO;
import ${package}.modelo.controleacesso.SolicitacaoRecuperacaoSenha;
import ${package}.modelo.controleacesso.TipoRecuperacaoSenha;
import ${package}.modelo.controleacesso.Usuario;
import com.xpert.core.exception.BusinessException;
import com.xpert.core.validation.Validation;
import com.xpert.utils.Encryption;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ayslan
 */
@Stateless
public class AlterPasswordBO {

    @EJB
    private UsuarioDAO usuarioDAO;
    @EJB
    private SolicitacaoRecuperacaoSenhaDAO solicitacaoRecuperacaoSenhaDAO;

    /**
     * Validar a alteracao de senha do usuario
     *
     * 1 - a senha nao pode esta vazia 
     * 2 - a confirmacao de senha deve ser igual a senha 
     * 3 - senha atual deve esta correta
     *
     * @param usuario
     * @param password
     * @param confirmPassword
     * @param transactionPassword Indica se a senha a ser alterada eh a de
     * transacao
     * @throws BusinessException
     */
    public void validate(Usuario usuario, String password, String confirmPassword, String currentPassword, SolicitacaoRecuperacaoSenha solicitacaoRecuperacaoSenha) throws BusinessException {
        BusinessException exception = new BusinessException();

        //verificar se as senhas nao estao vazias
        if (Validation.isBlank(password)) {
            exception.add("required.senha");
        }

        if (Validation.isBlank(confirmPassword)) {
            exception.add("required.confirmacaoSenha");
        }

        if (solicitacaoRecuperacaoSenha == null) {
            if (Validation.isBlank(currentPassword)) {
                exception.add("required.senhaAtual");
            }
        }

        exception.check();

        if (!password.equals(confirmPassword)) {
            exception.add("business.senhaNaoConfere");
        }

        exception.check();
        //verificar se a senha confere com a confirmacao. Caso uma solicitacao seja enviada, nao e necessario informar senha atual
        if (solicitacaoRecuperacaoSenha == null) {
            try {

                //verificar se a senha atual está correta
                if (!usuario.getUserPassword().equals(Encryption.getSHA256(currentPassword))) {
                    exception.add("business.senhaAtualNaoConfere");
                }

                exception.check();
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        }

        exception.check();
    }

    public void save(Usuario usuario, String password, String confirmPassword, String currentPassword, SolicitacaoRecuperacaoSenha solicitacaoRecuperacaoSenha) throws BusinessException {

        validate(usuario, password, confirmPassword, currentPassword, solicitacaoRecuperacaoSenha);

        try {
            usuario.setUserPassword(Encryption.getSHA256(password));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }

        if (solicitacaoRecuperacaoSenha != null) {
            //novo usuario alterando a senha
            if (solicitacaoRecuperacaoSenha.getTipoRecuperacaoSenha() != null
                    && solicitacaoRecuperacaoSenha.getTipoRecuperacaoSenha().equals(TipoRecuperacaoSenha.NOVO_USUARIO)) {
                usuario.setSenhaCadastrada(true);
            }
            solicitacaoRecuperacaoSenha.setAtivo(false);
            solicitacaoRecuperacaoSenha.setDataAlteracaoSenha(new Date());
            solicitacaoRecuperacaoSenhaDAO.merge(solicitacaoRecuperacaoSenha);
        }
        
        usuarioDAO.merge(usuario);

    }
}
