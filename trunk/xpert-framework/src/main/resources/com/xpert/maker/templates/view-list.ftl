<ui:composition  xmlns="http://www.w3.org/1999/xhtml"
                 xmlns:h="http://java.sun.com/jsf/html"
                 xmlns:f="http://java.sun.com/jsf/core"
                 xmlns:ui="http://java.sun.com/jsf/facelets"
                 xmlns:p="http://primefaces.org/ui"
                 template="${template}"
                 xmlns:x="http://xpert.com/faces"
                 xmlns:custom="http://java.sun.com/jsf/composite/components"
                 xmlns:xc="http://java.sun.com/jsf/composite/xpert/components">
    <ui:param name="title" value="${sharp}{msg['${entity.nameLower}.list']}" />
    <ui:define name="body">
        <ui:include src="menu${entity.name}.xhtml" />
        <h:form id="formList${entity.name}">
            <xc:modalMessages/>
            <p:dataTable paginator="true" rows="10" rowsPerPageTemplate="10,20,30" paginatorPosition="bottom" emptyMessage="${sharp}{xmsg['noRecordFound']}"
                         var="${entity.nameLower}" value="${sharp}{${entity.nameLower}MB.dataModel}" lazy="true" >
                <#list entity.fields as field>
                <#if field.collection == false && field.id == false>
                <p:column headerText="${sharp}{${resourceBundle}['${entity.nameLower}.${field.name}']}" sortBy="${sharp}{${entity.nameLower}.${field.name}}"
                          <#if field.string == true || field.integer == true || field.enumaration == true>filterBy="${sharp}{${entity.nameLower}.${field.name}}"</#if>
                          <#if field.enumaration == true>filterOptions="${sharp}{findAllBean.getSelect(classMB.${field.typeNameLower})}"</#if> <#if field.date == true || field.yesNo == true>style="text-align: center;"</#if><#if field.decimal == true>style="text-align: right;"</#if>>
                        <#if field.lazy == true>
                        <h:outputText value="${sharp}{${entity.nameLower}.${field.name}}">
                            <x:initializer/>
                        </h:outputText>
                        </#if>
                        <#if field.decimal == true>
                        <h:outputText value="${sharp}{${entity.nameLower}.${field.name}}">
                            <f:convertNumber />
                        </h:outputText>
                        </#if>
                        <#if field.date == true>
                        <h:outputText value="${sharp}{${entity.nameLower}.${field.name}}">
                            <f:convertDateTime />
                        </h:outputText>
                        </#if>
                        <#if field.yesNo == true>
                        <h:outputText value="${sharp}{${entity.nameLower}.${field.name}}" converter ="yesNoConverter" />
                        </#if>
                        <#if field.lazy == false && field.decimal == false && field.date == false && field.yesNo == false>
                        <h:outputText value="${sharp}{${entity.nameLower}.${field.name}}"/>
                        </#if>
                </p:column>
                </#if>
                </#list>
                <p:column style="text-align: center;">
                    <p:commandButton oncomplete="widget${entity.name}Detail.show();"  icon="ui-icon-zoomin" process="@form" update=":formDetail${entity.name}" >
                        <f:setPropertyActionListener value="${sharp}{${entity.nameLower}}" target="${sharp}{${entity.nameLower}MB.entity}" />
                    </p:commandButton>
                    <x:securityArea rolesAllowed="${entity.nameLower}.create">
                        <p:button icon="ui-icon-pencil" outcome="create${entity.name}" >
                            <f:param name="id" value="${sharp}{${entity.nameLower}.id}" />
                        </p:button>
                    </x:securityArea>
                    <x:securityArea rolesAllowed="${entity.nameLower}.delete">
                        <p:commandButton icon="ui-icon-trash" process="@form" update="@form" action="${sharp}{${entity.nameLower}MB.delete}" >
                            <f:setPropertyActionListener value="${sharp}{${entity.nameLower}.id}" target="${sharp}{${entity.nameLower}MB.id}" />
                            <x:confirmation message="${sharp}{xmsg['confirmDelete']} - ${sharp}{${entity.nameLower}}" />
                        </p:commandButton>
                    </x:securityArea>
                </p:column>
                <f:facet name="footer">
                    <x:securityArea rolesAllowed="${entity.nameLower}.audit">
                        <xc:auditDelete for="${sharp}{classMB.${entity.nameLower}}"/>
                    </x:securityArea>
                </f:facet>
            </p:dataTable>
        </h:form>

        <p:dialog widgetVar="widget${entity.name}Detail" header="${sharp}{msg['${entity.nameLower}.detail']}" appendToBody="true" modal="true" height="500" width="800">
            <ui:include src="detail${entity.name}.xhtml" />
        </p:dialog>
    </ui:define>
</ui:composition>