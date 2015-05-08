#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.mb.padrao;

import ${package}.modelo.exemplo.PessoaExemplo;
import ${package}.modelo.controleacesso.Permissao;
import ${package}.modelo.controleacesso.Usuario;
import ${package}.modelo.configuracao.ErroSistema;
import ${package}.modelo.controleacesso.SituacaoUsuario;
import ${package}.modelo.controleacesso.AcessoSistema;
import ${package}.modelo.email.ConfiguracaoEmail;
import ${package}.modelo.controleacesso.Perfil;
import ${package}.modelo.controleacesso.HistoricoSituacaoUsuario;
import ${package}.modelo.email.ModeloEmail;
import ${package}.modelo.controleacesso.SolicitacaoRecuperacaoSenha;
import ${package}.modelo.email.TipoAssuntoEmail;
import ${package}.modelo.controleacesso.TipoRecuperacaoSenha;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class ClassMB {

    public Class getAcessoSistema() {
        return AcessoSistema.class;
    }

    public Class getErroSistema() {
        return ErroSistema.class;
    }

    public Class getHistoricoSituacaoUsuario() {
        return HistoricoSituacaoUsuario.class;
    }

    public Class getPerfil() {
        return Perfil.class;
    }

    public Class getPermissao() {
        return Permissao.class;
    }

    public Class getSituacaoUsuario() {
        return SituacaoUsuario.class;
    }

    public Class getUsuario() {
        return Usuario.class;
    }

    public Class getTipoAssuntoEmail() {
        return TipoAssuntoEmail.class;
    }

    public Class getModeloEmail() {
        return ModeloEmail.class;
    }

    public Class getSolicitacaoRecuperacaoSenha() {
        return SolicitacaoRecuperacaoSenha.class;
    }

    public Class getTipoRecuperacaoSenha() {
        return TipoRecuperacaoSenha.class;
    }

    public Class getConfiguracaoEmail() {
        return ConfiguracaoEmail.class;
    }
    public Class getPessoaExemplo() {
        return PessoaExemplo.class;
    }

}