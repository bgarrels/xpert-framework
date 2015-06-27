Xpert-Framework is a component suite for JSF 2, EJB and JPA/Hibernate, integrated with Primefaces, the main idea behind Xpert is facilitate the development of Web applications.

### Showcase ###
http://showcase.xpertsistemas.com.br/

### Download and Documentation ###

https://drive.google.com/folderview?id=0B7aA_qvVNF77S2dIc082cWdBVzg&usp=sharing#list

### Xpert-Audit ###
  * Configurable Framework for auditing entities
  * Component integrated with primefaces for viewing the audit

### Xpert-Faces ###

Components are available under the following XML namespaces:

```
 xmlns:xc="http://java.sun.com/jsf/composite/xpert/components"
 xmlns:x="http://xpert.com/faces"
```

  * Hibernate Initializer Component
    * Initialize LAZY relationship, during RENDER\_RESPONSE
```
    <h:inputText value="#{bean.object.lazyObject}" >
        <x:initializer/>
    </h:inputText>    
```
    * Easy integration with any project
    * Do not use Filters or PhaseListeners
  * Generic entities Converter
    * Do not use the database query
```
     <h:selectOneMenu value="#{bean.entity}" converter="entityConverter">
        <f:selectItems value="#{bean.entityList}"/>
     </h:selectOneMenu>
```
    * Configurable for any type of bean, just annote with @ConverterId
  * UI components: inputNumber, Modal Messages, Confirmation
  * Internationalized components

### Xpert-Core ###
  * Internationalization with automatic integration with Bean Validation (JSR 303)
  * Managed Beans to simplify creation of CRUD

### Xpert-Maker ###
  * Code generation for CRUD following the MVC pattern
  * Integrated with JSF and EJB

### Xpert-Persistence ###
  * Generic DAOs, usable in a simple way
  * Retrieves attributes of the object on demand, reducing the need for VOs