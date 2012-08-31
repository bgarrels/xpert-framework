package com.xpert.maker;

import com.xpert.persistence.utils.EntityUtils;
import com.xpert.utils.HumaniseCamelCase;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.persistence.*;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.io.IOUtils;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Ayslan
 */
public class BeanCreator {

    private static final Logger logger = Logger.getLogger(BeanCreator.class.getName());
    private static final Configuration CONFIG = new Configuration();
    private static final String AUTHOR = "#Author";
    private static final String GET_PREFIX = "get";
    private static final String[] LOCALES_MAKER = {"pt_BR", "en", "es"};

    static {
        try {
            CONFIG.setObjectWrapper(new DefaultObjectWrapper());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String createBean(Bean bean, BeanConfiguration configuration) throws IOException, TemplateException {

        if (bean.getBeanType().equals(BeanType.FORM)) {
            return getCreateForm(bean.getEntity(), configuration.getResourceBundle());
        }
        if (bean.getBeanType().equals(BeanType.CREATE)) {
            return getCreate(bean.getEntity(), configuration.getTemplate(), configuration.getResourceBundle());
        }
        if (bean.getBeanType().equals(BeanType.LIST)) {
            return getList(bean.getEntity(), configuration.getTemplate(), configuration.getResourceBundle());
        }
        if (bean.getBeanType().equals(BeanType.MENU)) {
            return getMenu(bean.getEntity());
        }
        if (bean.getBeanType().equals(BeanType.DETAIL)) {
            return getDetail(bean.getEntity(), configuration.getResourceBundle());
        }

        Template template = getTemplate(bean.getBeanType().getTemplate());
        StringWriter writer = new StringWriter();
        bean.setConfiguration(configuration);
        if (configuration.getAuthor() == null || configuration.getAuthor().trim().isEmpty()) {
            configuration.setAuthor(AUTHOR);
        }
        bean.setAuthor(AUTHOR);
        template.process(bean, writer);

        writer.flush();
        writer.close();

        return writer.toString();
    }

    public static Template getTemplate(String template) throws IOException {

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/xpert/maker/templates/" + template);
        String templateString = IOUtils.toString(inputStream, "UTF-8");

        return new Template(template, new StringReader(templateString), CONFIG);
    }

    public static Map<String, Object> getDefaultParameters() {
        Map parameters = new HashMap();
        parameters.put("sharp", "#");
        return parameters;
    }

    public static String getViewTemplate() {

        try {
            Template template = BeanCreator.getTemplate("view-template.ftl");
            StringWriter writer = new StringWriter();
            template.process(getDefaultParameters(), writer);
            writer.flush();
            writer.close();
            return writer.toString();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (TemplateException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return "";

    }

    public static String getI18N(Class clazz) {

        Field[] fields = clazz.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        String className = getLowerFirstLetter(clazz.getSimpleName());
        String humanName = new HumaniseCamelCase().humanise(clazz.getSimpleName());
        builder.append("\n\n#").append(clazz.getSimpleName()).append("\n");
        builder.append(className).append("=").append(humanName).append("\n");

        //create CRUD i18n, like: person.create, person.list, person.detail
        builder.append(className).append(".create").append("=").append("Create ").append(humanName).append("\n");
        builder.append(className).append(".list").append("=").append("List ").append(humanName).append("\n");
        builder.append(className).append(".detail").append("=").append(humanName).append(" Detail").append("\n");
        boolean first = false;
        for (Field field : fields) {
            if (!first) {
                builder.append("\n");
            } else {
                first = true;
            }
            builder.append(className);
            builder.append(".");
            builder.append(field.getName());
            builder.append("=");
            builder.append(new HumaniseCamelCase().humanise(field.getName()));
        }


        return builder.toString();

    }

    public static String getHeader(String template) {

        StringBuilder builder = new StringBuilder();
        builder.append("<ui:composition  xmlns=\"http://www.w3.org/1999/xhtml\"\n");
        builder.append("                 xmlns:h=\"http://java.sun.com/jsf/html\"\n");
        builder.append("                 xmlns:f=\"http://java.sun.com/jsf/core\"\n");
        builder.append("                 xmlns:ui=\"http://java.sun.com/jsf/facelets\"\n");
        builder.append("                 xmlns:p=\"http://primefaces.org/ui\"\n");
        if (template != null && !template.isEmpty()) {
            builder.append("                 template=\"").append(template).append("\"\n");
        }
        builder.append("                 xmlns:x=\"http://xpert.com/faces\"\n");
        builder.append("                 xmlns:xc=\"http://java.sun.com/jsf/composite/xpert/components\">\n");

        return builder.toString();
    }

    public static String getMenu(Class clazz) {
        StringBuilder view = new StringBuilder();
        view.append(getHeader(null));
        view.append("   <p:toolbar>\n");
        view.append("       <p:toolbarGroup align=\"left\">  \n");
        view.append("           <p:button icon=\"ui-icon-search\" value=\"#{xmsg['list']}\" outcome=\"list").append(clazz.getSimpleName()).append("\" />\n");
        view.append("           <p:button icon=\"ui-icon-plus\" value=\"#{xmsg['create']}\" outcome=\"create").append(clazz.getSimpleName()).append("\" />\n");
        view.append("       </p:toolbarGroup>\n");
        view.append("   </p:toolbar>\n");
        view.append("</ui:composition>");

        return view.toString();
    }

    public static String getCreate(Class clazz, String template, String resourceBundle) {
        StringBuilder view = new StringBuilder();
        view.append(getHeader(template));


        view.append("   <ui:param name=\"title\" value=\"#{").append(resourceBundle).append("['").append(getNameLower(clazz)).append(".create']}\" />\n");
        view.append("   <ui:define name=\"body\">\n");
        view.append("       <ui:include src=\"menu").append(clazz.getSimpleName()).append(".xhtml\" />\n");
        view.append("       <ui:include src=\"formCreate").append(clazz.getSimpleName()).append(".xhtml\" />\n");
        view.append("   </ui:define>\n");
        view.append("</ui:composition>");

        return view.toString();
    }

    public static String getList(Class clazz, String template, String resourceBundle) {
        List<Field> fields = getFields(clazz);
        String managedBean = getNameManagedBean(clazz);
        String varName = getLowerFirstLetter(clazz.getSimpleName());
        String idExpression = "#{" + varName + "." + EntityUtils.getIdFieldName(clazz) + "}";
        String dialogWidget = getDialogWidget(clazz);
        StringBuilder view = new StringBuilder();
        view.append(getHeader(template));
        view.append("   <ui:param name=\"title\" value=\"#{").append(resourceBundle).append("['").append(getNameLower(clazz)).append(".list']}\" />\n");
        view.append("   <ui:define name=\"body\">\n");
        view.append("       <ui:include src=\"menu").append(clazz.getSimpleName()).append(".xhtml\" />\n");
        view.append("       <h:form id=\"formList").append(clazz.getSimpleName()).append("\">\n");
        view.append("           <xc:modalMessages/>\n");
        view.append("           <p:dataTable paginator=\"true\" rows=\"10\" rowsPerPageTemplate=\"10,20,30\"  emptyMessage=\"#{xmsg['noRecordFound']}\"\n");
        view.append("                   var=\"").append(varName).append("\" value=\"#{").append(managedBean).append(".dataModel}\" lazy=\"true\" >\n");
        for (Field field : fields) {
            if (isAnnotationPresent(field, Id.class) || isAnnotationPresent(field, ManyToMany.class) || isAnnotationPresent(field, OneToMany.class)) {
                continue;
            }
            String fieldName = varName + "." + field.getName();
            String varExpression = "#{" + fieldName + "}";
            view.append("               <p:column headerText=\"#{").append(resourceBundle).append("['").append(fieldName).append("']}\" sortBy=\"").append(varExpression).append("\"");
            if (field.getType().equals(String.class) || field.getType().equals(Integer.class) || field.getType().equals(Long.class) || field.getType().isEnum()) {
                view.append("\n").append("                      filterBy=\"").append(varExpression).append("\"");
            }
            if (field.getType().isEnum()) {
                view.append("\n").append("                      filterOptions=\"#{findAllBean.getSelect(classMB.").append(getLowerFirstLetter(field.getType().getSimpleName())).append(")}\"");
            }
            //align Date on center
            if (field.getType().equals(Date.class)) {
                view.append("\n").append("                                   style=\"text-align: center;\"");
                //align Number on right
            } else if (field.getType().equals(BigDecimal.class)) {
                view.append("\n").append("                                   style=\"text-align: right;\"");
            }
            view.append(">\n");
            view.append(getText(field, resourceBundle, varExpression, "                   "));
            view.append("               </p:column>\n");
        }
        //actions column
        view.append("               <p:column style=\"text-align: center;\">\n");
        view.append("                   <p:commandButton oncomplete=\"").append(dialogWidget).append(".show();\"  icon=\"ui-icon-zoomin\" process=\"@form\" update=\":formDetail").append(clazz.getSimpleName()).append("\" >\n");
        view.append("                       <f:setPropertyActionListener value=\"#{").append(varName).append("}\" target=\"#{").append(managedBean).append(".entity}\" />\n");
        view.append("                   </p:commandButton>\n");
        view.append("                   <p:button icon=\"ui-icon-pencil\" outcome=\"create").append(clazz.getSimpleName()).append("\" >\n");
        view.append("                       <f:param name=\"id\" value=\"").append(idExpression).append("\" />\n");
        view.append("                   </p:button>\n");
        view.append("                   <p:commandButton icon=\"ui-icon-trash\" process=\"@form\" update=\"@form\" action=\"#{").append(managedBean).append(".delete}\" >\n");
        view.append("                       <f:setPropertyActionListener value=\"").append(idExpression).append("\" target=\"#{").append(managedBean).append(".id}\" />\n");
        view.append("                       <x:confirmation ").append("message=\"#{xmsg['").append("confirmDelete").append("']} #{").append(varName).append("}\" />\n");
        view.append("                   </p:commandButton>\n");
        view.append("               </p:column>\n");
        view.append("               <f:facet name=\"footer\">\n");
        view.append("                   <xc:auditDelete for=\"#{classMB.").append(varName).append("}\"/>\n");
        view.append("               </f:facet>\n");
        view.append("           </p:dataTable>\n");
        view.append("       </h:form>\n\n");
        view.append("       <p:dialog widgetVar=\"").append(dialogWidget).append("\" header=\"#{").append(resourceBundle).append("['").append(varName).append(".detail']}\" appendToBody=\"true\" modal=\"true\" height=\"500\" width=\"800\">\n");
        view.append("           <ui:include src=\"detail").append(clazz.getSimpleName()).append(".xhtml\" />\n");
        view.append("       </p:dialog>\n");
        view.append("   </ui:define>\n");
        view.append("</ui:composition>");

        return view.toString();
    }

    public static String getDetail(Class clazz, String resourceBundle) {
        List<Field> fields = getFields(clazz);
        String managedBean = getNameManagedBean(clazz);
        String dialogWidget = getDialogWidget(clazz);
        StringBuilder view = new StringBuilder();
        view.append(getHeader(null));
        view.append("   <h:form id=\"formDetail").append(clazz.getSimpleName()).append("\">\n");
        view.append("       <h:panelGrid columns=\"4\" styleClass=\"grid-detail\">\n");
        for (Field field : fields) {
            if (isAnnotationPresent(field, Id.class) || isAnnotationPresent(field, ManyToMany.class) || isAnnotationPresent(field, OneToMany.class)) {
                continue;
            }
            String varExpression = "#{" + managedBean + ".entity." + field.getName() + "}";
            view.append("\n");
            view.append("           ").append(getLabel(field, false, resourceBundle));
            view.append(getText(field, resourceBundle, varExpression, "           "));
        }
        view.append("\n").append("       </h:panelGrid>\n");
        view.append("       <p:separator/>\n");
        view.append("       <div style=\"text-align: center;\">\n");
        view.append("           <p:commandButton type=\"button\" value=\"#{xmsg['close']}\" onclick=\"").append(dialogWidget).append(".hide()\" />\n");
        view.append("           <xc:audit for=\"#{").append(managedBean).append(".entity}\"/>\n");
        view.append("       </div>\n");
        view.append("   </h:form>\n");
        view.append("</ui:composition>");

        return view.toString();
    }

    public static String getDialogWidget(Class clazz) {
        return "widget" + clazz.getSimpleName() + "Detail";
    }

    public static String getNameManagedBean(Class clazz) {
        return getNameLower(clazz) + "MB";
    }

    public static String getCreateForm(Class clazz, String resourceBundle) {

        if (resourceBundle == null || resourceBundle.trim().isEmpty()) {
            resourceBundle = "msg";
        }

        List<Field> fields = getFields(clazz);
        StringBuilder view = new StringBuilder();
        String managedBean = getNameManagedBean(clazz);
        view.append(getHeader(null));
        view.append("   <h:form>\n");
        view.append("       <xc:modalMessages/>\n");
        view.append("       <h:panelGrid columns=\"2\" styleClass=\"grid-form\">\n");
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                continue;
            }
            String tag = "";
            String converter = "";
            boolean hasSelectItem = false;
            boolean hasEmptySelect = false;
            Integer maxlength = null;
            boolean required = false;
            if (isAnnotationPresent(field, NotNull.class) || isAnnotationPresent(field, NotBlank.class)) {
                required = true;
            }
            if (field.getType().equals(Date.class)) {
                tag = "p:calendar";
            } else if (field.getType().equals(BigDecimal.class)) {
                tag = "xc:inputNumber";
            } else if (isAnnotationPresent(field, ManyToOne.class) || field.getType().isEnum()) {
                tag = "h:selectOneMenu";
                if (!field.getType().isEnum()) {
                    converter = "entityConverter";
                }
                hasSelectItem = true;
                hasEmptySelect = true;
            } else if (isAnnotationPresent(field, OneToMany.class) || isAnnotationPresent(field, ManyToMany.class)) {
                tag = "h:selectManyCheckbox";
                converter = "entityConverter";
                hasSelectItem = true;
            } else {
                if (field.getType().equals(String.class)) {
                    maxlength = 255;
                    Size size = field.getAnnotation(Size.class);
                    if (size != null) {
                        maxlength = size.max();
                    }
                }
                tag = "p:inputText";
            }
            appendTagValue(view, tag, field, managedBean, converter, resourceBundle, hasSelectItem, hasEmptySelect, maxlength, required);
        }
        view.append("       </h:panelGrid>\n");
        view.append("       <p:separator/>\n");
        view.append("       <h:outputText value=\"#{xmsg['requiredFieldsForm']}\"/>\n");
        view.append("       <div style=\"text-align: center;\">\n");
        view.append("           <p:commandButton process=\"@form\" update=\"@form\" action=\"#{");
        view.append(managedBean).append(".save}\" value=\"#{xmsg['save']}\" />\n");
        view.append("           <xc:audit for=\"#{").append(managedBean).append(".entity}\"/>\n");
        view.append("       </div>\n");
        view.append("   </h:form>\n");
        view.append("</ui:composition>");
        return view.toString();
    }

    public static String getNameLower(Class clazz) {
        String name = clazz.getSimpleName();
        if (name != null && name.length() > 2) {
            return name.substring(0, 2).toLowerCase() + "" + name.substring(2, name.length());
        }
        return "";
    }

    public static boolean isLazy(Field field) {

        Annotation annotation = field.getAnnotation(ManyToOne.class);
        //ManyToOne
        if (annotation == null) {
            Method method = getMethod(field);
            if (method != null) {
                annotation = method.getAnnotation(ManyToOne.class);
            }
        }
        if (annotation != null && ((ManyToOne) annotation).fetch() != null && ((ManyToOne) annotation).fetch().equals(FetchType.LAZY)) {
            return true;
        }
        annotation = field.getAnnotation(ManyToMany.class);
        //ManyToMany
        if (annotation == null) {
            Method method = getMethod(field);
            if (method != null) {
                annotation = method.getAnnotation(ManyToMany.class);
            }
        }
        if (annotation != null && ((((ManyToMany) annotation).fetch() == null) || ((ManyToMany) annotation).fetch().equals(FetchType.LAZY))) {
            return true;
        }
        //OneToMany
        annotation = field.getAnnotation(OneToMany.class);
        if (annotation == null) {
            Method method = getMethod(field);
            if (method != null) {
                annotation = method.getAnnotation(OneToMany.class);
            }
        }
        if (annotation != null && ((((OneToMany) annotation).fetch() == null) || ((OneToMany) annotation).fetch().equals(FetchType.LAZY))) {
            return true;
        }
        //OneToOne
        annotation = field.getAnnotation(OneToOne.class);
        if (annotation == null) {
            Method method = getMethod(field);
            if (method != null) {
                annotation = method.getAnnotation(OneToOne.class);
            }
        }
        if (annotation != null && ((OneToOne) annotation).fetch() != null && ((OneToOne) annotation).fetch().equals(FetchType.LAZY)) {
            return true;
        }

        return false;
    }

    public static String getText(Field field, String resourceBundle, String varExpression, String ident) {

        StringBuilder text = new StringBuilder();
        boolean hasContent = false;
        text.append(ident).append("<h:outputText value=\"").append(varExpression).append("\"");
        if (field.getType().equals(Date.class)) {
            text.append(">\n").append(ident).append("   <f:convertDateTime />\n");
            hasContent = true;
        } else if (field.getType().equals(BigDecimal.class)) {
            text.append(">\n").append(ident).append("   <f:convertNumber />\n");
            hasContent = true;
        } else if (isLazy(field)) {
            text.append(">\n").append(ident).append("   <x:initializer/>\n");
            hasContent = true;
        }
        if (hasContent) {
            text.append(ident).append("</h:outputText>\n");
        } else {
            text.append("/>\n");
        }

        return text.toString();
    }

    public static String getLabel(Field field, boolean required, String resourceBundle) {

        StringBuilder label = new StringBuilder();
        label.append("<h:outputLabel value=\"").append(required ? "* " : "").append("#{").append(resourceBundle);
        label.append("['").append(getNameLower(field.getDeclaringClass())).append(".").append(field.getName()).append("']}:\" />\n");

        return label.toString();
    }

    public static void appendTagValue(StringBuilder view, String tag, Field field, String managedBean, String converter, String resourceBundle,
            boolean hasSelectItem, boolean hasEmptySelect, Integer maxlength, boolean required) {
        String type;
        if (field.getType().equals(Collection.class) || field.getType().equals(List.class) || field.getType().equals(Set.class)) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            Class<?> clazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            type = getNameLower(clazz);
        } else {
            type = getNameLower(field.getType());
        }
        view.append("\n");
        view.append("           ").append(getLabel(field, required, resourceBundle));
        view.append("           <").append(tag).append(" value=\"#{").append(managedBean).append(".entity.").append(field.getName()).append("}\"");
        if (converter != null && !converter.trim().isEmpty()) {
            view.append(" converter=\"").append(converter).append("\" ");
        }
        if (maxlength != null && maxlength > 0) {
            view.append(" maxlength=\"").append(maxlength).append("\" ");
        }
        if (hasSelectItem) {
            view.append(">");
            view.append("\n");
            if (isLazy(field)) {
                view.append("               <x:initializer/>");
                view.append("\n");
            }
            if (hasEmptySelect) {
                view.append("               <f:selectItem itemLabel=\"#{xmsg['select']}\" />");
                view.append("\n");

            }
            view.append("               <f:selectItems value=\"#{findAllBean.get(").append("classMB.").append(type).append(")}\"");
            view.append(" var=\"").append(type).append("\"");
            view.append(" itemLabel=\"#{").append(type).append("}\"");
            view.append(" />");
            view.append("\n");
            view.append("           </").append(tag).append(">");
        } else {
            view.append(" />");
        }
        view.append("\n");
    }

    public static byte[] createBeanZipFile(List<MappedBean> mappedBeans, String classBean, String viewTemplate, BeanConfiguration configuration) throws IOException, TemplateException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(baos);
        out.setLevel(Deflater.DEFAULT_COMPRESSION);
        StringBuilder i18n = new StringBuilder();
        for (MappedBean mappedBean : mappedBeans) {
            String classSimpleName = mappedBean.getEntityClass().getSimpleName();
            String nameLower = getNameLower(mappedBean.getEntityClass());
            //Managed bean
            putEntry(out, "mb/" + classSimpleName + "MB.java", mappedBean.getManagedBean());
            //BO
            putEntry(out, "bo/" + classSimpleName + "BO.java", mappedBean.getBusinnesObject());
            //DAO
            putEntry(out, "dao/" + classSimpleName + "DAO.java", mappedBean.getDao());
            //DAO Impl
            putEntry(out, "dao/impl/" + classSimpleName + "DAOImpl.java", mappedBean.getDaoImpl());
            //create
            putEntry(out, "views/" + nameLower + "/create" + classSimpleName + ".xhtml", mappedBean.getCreateView());
            //form
            putEntry(out, "views/" + nameLower + "/formCreate" + classSimpleName + ".xhtml", mappedBean.getFormCreateView());
            //list
            putEntry(out, "views/" + nameLower + "/list" + classSimpleName + ".xhtml", mappedBean.getListView());
            //detail
            putEntry(out, "views/" + nameLower + "/detail" + classSimpleName + ".xhtml", mappedBean.getDetail());
            //menu
            putEntry(out, "views/" + nameLower + "/menu" + classSimpleName + ".xhtml", mappedBean.getMenu());

            i18n.append(mappedBean.getI18n());
        }
        //template
        putEntry(out, getViewTemplatePath(configuration), viewTemplate);
        //class bean
        putEntry(out, "mb/ClassMB.java", classBean);
        //i18n
        for (String locale : LOCALES_MAKER) {
            putEntry(out, "messages_" + locale, i18n.toString());
        }

        out.finish();
        out.flush();
        out.close();

        return baos.toByteArray();
    }

    public static String getViewTemplatePath(BeanConfiguration configuration) {
        if (configuration.getTemplate() != null && !configuration.getTemplate().isEmpty()) {
            return configuration.getTemplate().startsWith("/") ? configuration.getTemplate().substring(1, configuration.getTemplate().length()) : configuration.getTemplate();
        } else {
            return "template/mainTemplate.xhtml";
        }
    }

    public static void putEntry(ZipOutputStream out, String fileName, String content) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream));
        writer.write(content);
        writer.close();
        ZipEntry entry = new ZipEntry(fileName);
        entry.setSize(byteArrayOutputStream.size());
        out.putNextEntry(entry);
        out.write(byteArrayOutputStream.toByteArray());
        out.closeEntry();
    }

    public static List<Field> getFields(Class entity) {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(entity.getDeclaredFields()));
        if (entity.getSuperclass() != null && !entity.getSuperclass().equals(Object.class)) {
            fields.addAll(getFields(entity.getSuperclass()));
        }
        return fields;
    }

    /**
     * Verify annotation in field and in method get equivalent to field
     *
     * @param field
     * @param annotation
     * @return
     */
    public static boolean isAnnotationPresent(Field field, Class annotation) {
        if (field.isAnnotationPresent(annotation)) {
            return true;
        }
        //annotation can be found in Method Get
        Method method = getMethod(field);
        if (method != null && method.isAnnotationPresent(annotation)) {
            return true;
        }
        return false;
    }

    /**
     * Return equivalent method Get to a field. Example: field: name, try to
     * find method getName()
     *
     * @param field
     * @return
     */
    public static Method getMethod(Field field) {
        try {
            return field.getDeclaringClass().getMethod(GET_PREFIX + getUpperFirstLetter(field.getName()));
        } catch (Exception ex) {
            //nothing
            return null;
        }
    }

    public static String getLowerFirstLetter(String string) {
        if (string.length() == 1) {
            return string.toLowerCase();
        }
        if (string.length() > 1) {
            return string.substring(0, 1).toLowerCase() + "" + string.substring(1, string.length());
        }
        return "";
    }

    public static String getUpperFirstLetter(String string) {
        if (string.length() == 1) {
            return string.toUpperCase();
        }
        if (string.length() > 1) {
            return string.substring(0, 1).toUpperCase() + "" + string.substring(1, string.length());
        }
        return "";
    }
}
