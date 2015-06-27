# FAQ (Perguntas frequentes) #
## Qual versão do primefaces usar? ##
Essa é uma decisão do próprio desenvolvedor, o xpert-framework suporta as versões 3, 4 e 5 do primefaces.
## A dependência do xpert-framework do primefaces é diferente da que eu quero usar, como fazer? ##
O xpert-framework importa o primefaces apenas para compilação do código, a versão considerada no seu projeto é que você importar, assim no xpert-framework pode ser a 4.0 e no seu projeto ser a 3.5, não tem problema, a versão final será a 3.5.
## Não consigo compilar o meu projeto maven, pois ele não consegue baixar o xpert-framework, o que há de errado? ##
Até a presente versão, o framework ainda não está no maven central, assim talvez seja necessário instalar manualmente o xpert-framework.jar no seu repositório.
## Encontrei um bug no framework, como corrigir? ##
Cadastre o bug no google codes através do link https://code.google.com/p/xpert-framework/issues/list, é interessante que seja explicado o que aconteceu, e se possível, com  trechos de código, assim facilita as correções.
## MySQL, SQL Sever, Oracle, PostgreSQL, qual é suportado pelo xpert-framework? ##
Qualquer banco é suportado, desde que ele possua um driver JDBC, e seja compatível a especificação do JTA. O projeto criado do arquétipo é voltado para o postgres, mas nada impede que sea configurado para outro banco.
## É possível utilizar o eclipse link no lugar do hibernate? ##
Até a versão atual, apenas o hibernate é suportado.
## Já tenho um banco criado, porém não tenho o mapeamento JPA do modelo, o xpert-framework vai gerar? ##
Não. Essa geração deve ser feita através de ferramentas de engenharia reversa, como as próprias IDEs (O Netbeans por exemplo, possui uma ferramenta de engenharia reversa).
## No primefaces 5.x o dataTable está com um layout estranho, é por causa do xpert-framework? ##
Não. Na versão 5.0 do primefaces o CSS da tabela foi mudado para “table-layout: fixed”, isso pode causar algumas quebras na tabela, principalmente se utilizar o bootstrap. A solução é sobrescrever essa propriedade via CSS. Exemplo:
```
.ui-datatable table{
	table-layout: auto;
}
```
## Como entrar em contato com a Xpert Sistemas? ##
Entre em contato através do site http://www.xpertsistemas.com.br/