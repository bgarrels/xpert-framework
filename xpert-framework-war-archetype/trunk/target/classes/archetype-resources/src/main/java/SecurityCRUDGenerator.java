#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${package}.modelo.exemplo.PessoaExemplo;
import com.xpert.utils.HumaniseCamelCase;
import com.xpert.utils.StringUtils;
import java.util.logging.Logger;

/**
 * Classe para facilitar a geracao das permissoes da classe GeracaoPermissao
 *  
 * @see GeracaoPermissao
 * @author Ayslan
 */
public class SecurityCRUDGenerator {

    private static final Logger logger = Logger.getLogger(SecurityCRUDGenerator.class.getName());
    private static String VIEW_PREFFIX;

    public static void main(String[] args) {
        /**
         * informar padráo da view
         */
        VIEW_PREFFIX = "/view/controleAcesso/";
        /**
         * adicionar aqui as classes que serão geradas
         */
        generate(PessoaExemplo.class);
    }

    public static void generate(Class clazz) {

        String className = clazz.getSimpleName();
        String classLower = StringUtils.getLowerFirstLetter(clazz.getSimpleName());
        String classHuman = new HumaniseCamelCase().humanise(className);

        StringBuilder builder = new StringBuilder();
        builder.append("//").append(classHuman).append("${symbol_escape}n");
        builder.append("create(new Permissao(${symbol_escape}"").append(classLower).append("${symbol_escape}", ${symbol_escape}"").append(classHuman).append("${symbol_escape}", true), null);").append("${symbol_escape}n");
        builder.append("create(new Permissao(${symbol_escape}"").append(classLower).append(".create${symbol_escape}", ${symbol_escape}"Cadastro de ").append(classHuman).append("${symbol_escape}", ${symbol_escape}"").append(VIEW_PREFFIX).append(classLower).append("/create").append(className).append(".jsf${symbol_escape}", true), ${symbol_escape}"").append(classLower).append("${symbol_escape}");").append("${symbol_escape}n");
        builder.append("create(new Permissao(${symbol_escape}"").append(classLower).append(".list${symbol_escape}", ${symbol_escape}"Consulta de ").append(classHuman).append("${symbol_escape}", ${symbol_escape}"").append(VIEW_PREFFIX).append(classLower).append("/list").append(className).append(".jsf${symbol_escape}", true), ${symbol_escape}"").append(classLower).append("${symbol_escape}");").append("${symbol_escape}n");
        builder.append("create(new Permissao(${symbol_escape}"").append(classLower).append(".audit${symbol_escape}", ${symbol_escape}"Auditoria de ").append(classHuman).append("${symbol_escape}"").append("), ${symbol_escape}"").append(classLower).append("${symbol_escape}");").append("${symbol_escape}n");
        builder.append("create(new Permissao(${symbol_escape}"").append(classLower).append(".delete${symbol_escape}", ${symbol_escape}"Exclusão de ").append(classHuman).append("${symbol_escape}"").append("), ${symbol_escape}"").append(classLower).append("${symbol_escape}");").append("${symbol_escape}n");
        builder.append("${symbol_escape}n");
        System.out.println(builder.toString());

    }
}
