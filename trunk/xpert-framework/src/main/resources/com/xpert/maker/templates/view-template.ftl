<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      dir="ltr" lang="en-US" xml:lang="en"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:p="http://primefaces.org/ui"
      xmlns:x="http://xpert.com/faces"
      xmlns:xc="http://java.sun.com/jsf/composite/xpert/components">

    <f:view locale="#{localeBean.locale}">
        <h:head>
             <title>New Project<h:outputText rendered="#{not empty title}" value=" - #{title}"/></title>
        </h:head>
        <h:body>
             <ui:insert name="body" />
        </h:body>
    </f:view>

</html>