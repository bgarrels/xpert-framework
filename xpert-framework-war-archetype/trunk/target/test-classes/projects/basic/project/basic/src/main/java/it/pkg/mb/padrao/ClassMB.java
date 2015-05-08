package it.pkg.mb.padrao;

import it.pkg.modelo.exemplo.PessoaExemplo;
import it.pkg.modelo.controleacesso.Permissao;
import it.pkg.modelo.controleacesso.Usuario;
import it.pkg.modelo.configuracao.ErroSistema;
import it.pkg.modelo.controleacesso.SituacaoUsuario;
import it.pkg.modelo.controleacesso.AcessoSistema;
import it.pkg.modelo.email.ConfiguracaoEmail;
import it.pkg.modelo.controleacesso.Perfil;
import it.pkg.modelo.controleacesso.HistoricoSituacaoUsuario;
import it.pkg.modelo.email.ModeloEmail;
import it.pkg.modelo.controleacesso.SolicitacaoRecuperacaoSenha;
import it.pkg.modelo.email.TipoAssuntoEmail;
import it.pkg.modelo.controleacesso.TipoRecuperacaoSenha;
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