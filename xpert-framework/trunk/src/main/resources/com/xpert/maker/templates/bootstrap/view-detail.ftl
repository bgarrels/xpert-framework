<ui:composition  xmlns="http://www.w3.org/1999/xhtml"
                 xmlns:h="http://java.sun.com/jsf/html"
                 xmlns:f="http://java.sun.com/jsf/core"
                 xmlns:ui="http://java.sun.com/jsf/facelets"
                 xmlns:p="http://primefaces.org/ui"
                 xmlns:x="http://xpert.com/faces">
    <h:form id="formDetail${entity.name}" styleClass="uix-form-detail">
        <div class="container-fluid">
            <div class="row">
            <#list entity.fields as field>
                <#if field.collection == false && field.id == false>
                <div class="${configuration.bootstrapVersion.defaultColumns}">
                    <h:outputLabel value="${sharp}{${resourceBundle}['${entity.nameLower}.${field.name}']}:" styleClass="control-label" />
                    <p class="form-control-static">
                        <#if field.lazy == true>
                        <h:outputText value="${sharp}{${entity.nameLower}${configuration.managedBeanSuffix}.entity.${field.name}}">
                            <x:initializer/>
                        </h:outputText>
                        </#if>
                        <#if field.decimal == true>
                        <h:outputText value="${sharp}{${entity.nameLower}${configuration.managedBeanSuffix}.entity.${field.name}}">
                            <f:convertNumber minFractionDigits="2" maxFractionDigits="2" />
                        </h:outputText>
                        </#if>
                        <#if field.date == true>
                        <h:outputText value="${sharp}{${entity.nameLower}${configuration.managedBeanSuffix}.entity.${field.name}}">
                            <f:convertDateTime />
                        </h:outputText>
                        </#if>
                        <#if field.yesNo == true>
                        <h:outputText value="${sharp}{${entity.nameLower}${configuration.managedBeanSuffix}.entity.${field.name}}" converter ="yesNoConverter" />
                        </#if>
                        <#if field.lazy == false && field.decimal == false && field.date == false && field.yesNo == false>
                        <h:outputText value="${sharp}{${entity.nameLower}${configuration.managedBeanSuffix}.entity.${field.name}}"/>
                        </#if>
                    </p>
                </div>
                </#if>
            </#list>
           </div>
         </div>
        <p:separator/>
        <div style="text-align: center;">
            <p:commandButton type="button" value="${sharp}{xmsg['close']}" onclick="${entity.widgetVarDetail}.hide()" />
            <#if configuration.generatesSecurityArea == true >
            <x:securityArea rolesAllowed="${entity.nameLower}.audit">
                <x:audit for="${sharp}{${entity.nameLower}${configuration.managedBeanSuffix}.entity}"/>
            </x:securityArea>
            </#if>
            <#if configuration.generatesSecurityArea == false >
            <x:audit for="${sharp}{${entity.nameLower}${configuration.managedBeanSuffix}.entity}"/>
            </#if>
        </div>
    </h:form>
</ui:composition>