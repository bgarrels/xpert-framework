package ${package_name}.modelo;

import br.com.seed.seedbean.annotations.SeedEntity;
import br.com.seed.seedbean.annotations.SeedId;
import br.com.seed.seedbean.annotations.SeedColumn;
import br.com.seed.seedbean.enumeration.SeedAssociationType;
import br.com.seed.seedbean.annotations.SeedAssociationEntity;
import java.math.BigDecimal;
import java.util.Date;

@SeedEntity(tableName="${nome_tabela}")
public class ${class_name} {
<#list atributos as atributo>
<#if atributo.nomeAtributo == "id" >
    @SeedId
    private long ${atributo.nomeAtributo};
<#else>
    <#if atributo.nomeColuna == atributo.nomeAtributo >
    private ${atributo.tipoAtributo} ${atributo.nomeAtributo};
    <#elseif atributo.tipoAssociacao == "nenhum">
    @SeedColumn(columnName="${atributo.nomeColuna}")
    private ${atributo.tipoAtributo} ${atributo.nomeAtributo};
    </#if>
    <#if atributo.tipoAssociacao == "OneToOne">
    @SeedAssociationEntity(mappedBy="${atributo.nomeColuna}", associationType=SeedAssociationType.OneToOne)
    private ${atributo.tipoAtributo} ${atributo.nomeAtributo} = new ${atributo.tipoAtributo}();
    </#if>
    <#if atributo.tipoAssociacao == "ManyToOne">
    @SeedAssociationEntity(mappedBy="${atributo.nomeColuna}", associationType=SeedAssociationType.ManyToOne)
    private ${atributo.tipoAtributo} ${atributo.nomeAtributo} = new ${atributo.tipoAtributo}();
    </#if>
</#if>
</#list>

<#list atributos as atributo>
    public ${atributo.tipoAtributo} get${atributo.nomeAtributo?cap_first}(){
        return ${atributo.nomeAtributo};
    }

    public void set${atributo.nomeAtributo?cap_first}(${atributo.tipoAtributo} ${atributo.nomeAtributo}){
        this.${atributo.nomeAtributo}=${atributo.nomeAtributo};
    }
</#list>
}