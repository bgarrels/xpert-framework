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
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
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

    private static final Configuration CONFIG = new Configuration();
    private static final String AUTHOR = "#Author";

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

    public static String getLowerFirstLetter(String string) {
        if (string.length() == 1) {
            return string.toLowerCase();
        }
        if (string.length() > 1) {
            return string.substring(0, 1).toLowerCase() + "" + string.substring(1, string.length());
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
        view.append("           <p:button icon=\"ui-icon-search\" value=\"#{xmsg['create']}\" outcome=\"create").append(clazz.getSimpleName()).append("\" />\n");
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
        Field[] fields = clazz.getDeclaredFields();
        String managedBean = getNameManagedBean(clazz);
        String varName = getLowerFirstLetter(clazz.getSimpleName());
        String idExpression = "#{" + varName + "." + EntityUtils.getIdFieldName(clazz) + "}";
        String dialogWidget = "widget" + clazz.getSimpleName() + "Detail";
        StringBuilder view = new StringBuilder();
        view.append(getHeader(template));
        view.append("   <ui:param name=\"title\" value=\"#{").append(resourceBundle).append("['").append(getNameLower(clazz)).append(".list']}\" />\n");
        view.append("   <ui:define name=\"body\">\n");
        view.append("       <ui:include src=\"menu").append(clazz.getSimpleName()).append(".xhtml\" />\n");
        view.append("       <h:form id=\"formList").append(clazz.getSimpleName()).append("\">\n");
        view.append("           <xc:modalMessages/>\n");
        view.append("           <p:dataTable paginator=\"true\" rows=\"10\" rowsPerPageTemplate=\"10,20,30\"\n");
        view.append("                   var=\"").append(varName).append("\" value=\"#{").append(managedBean).append(".dataModel}\">\n");
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(ManyToMany.class) || field.isAnnotationPresent(OneToMany.class)) {
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
        view.append("                   <p:commandButton oncomplete=\"").append(dialogWidget).append(".show();\"  icon=\"ui-icon-zoomin\" process=\"@this\" update=\":formDetail").append(clazz.getSimpleName()).append("\" >\n");
        view.append("                       <f:setPropertyActionListener value=\"#{").append(varName).append("}\" target=\"#{").append(managedBean).append(".entity}\" />\n");
        view.append("                   </p:commandButton>\n");
        view.append("                   <p:button icon=\"ui-icon-pencil\" outcome=\"create").append(clazz.getSimpleName()).append("\" >\n");
        view.append("                       <f:param name=\"id\" value=\"").append(idExpression).append("\" />\n");
        view.append("                   </p:button>\n");
        view.append("                   <p:commandButton icon=\"ui-icon-trash\" process=\"@this\" update=\"@form\" action=\"#{").append(managedBean).append(".delete}\" >\n");
        view.append("                       <f:setPropertyActionListener value=\"").append(idExpression).append("\" target=\"#{").append(managedBean).append(".id}\" />\n");
        view.append("                       <x:confirmation />\n");
        view.append("                   </p:commandButton>\n");
        view.append("               </p:column>\n");
        view.append("           </p:dataTable>\n");
        view.append("       </h:form>\n\n");
        view.append("       <p:dialog widgetVar=\"").append(dialogWidget).append("\" header=\"#{").append(resourceBundle).append("['").append(varName).append(".detail']}\" appendToBody=\"true\" modal=\"true\" height=\"500\" width=\"800\">\n");
        view.append("           <h:form id=\"formDetail").append(clazz.getSimpleName()).append("\">\n");
        view.append("               <h:panelGrid columns=\"4\">\n");
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(ManyToMany.class) || field.isAnnotationPresent(OneToMany.class)) {
                continue;
            }
            String varExpression = "#{" + managedBean + ".entity." + field.getName() + "}";
            view.append("\n");
            view.append("                   ").append(getLabel(field, false, resourceBundle));
            view.append(getText(field, resourceBundle, varExpression, "                   "));
        }
        view.append("               </h:panelGrid>\n");
        view.append("               <p:separator/>\n");
        view.append("               <div style=\"text-align: center;\">\n");
        view.append("                   <p:commandButton type=\"button\" value=\"#{xmsg['close']}\" onclick=\"").append(dialogWidget).append(".hide()\" />\n");
        view.append("                   <xc:audit for=\"#{").append(managedBean).append(".entity}\"/>\n");
        view.append("               </div>\n");
        view.append("           </h:form>\n");
        view.append("       </p:dialog>\n");
        view.append("   </ui:define>\n");
        view.append("</ui:composition>");

        return view.toString();
    }

    public static String getNameManagedBean(Class clazz) {
        return getNameLower(clazz) + "MB";
    }

    public static String getCreateForm(Class clazz, String resourceBundle) {

        if (resourceBundle == null || resourceBundle.trim().isEmpty()) {
            resourceBundle = "msg";
        }

        Field[] fields = clazz.getDeclaredFields();
        StringBuilder view = new StringBuilder();
        String managedBean = getNameManagedBean(clazz);
        view.append(getHeader(null));
        view.append("   <h:form>\n");
        view.append("       <xc:modalMessages/>\n");
        view.append("       <h:panelGrid columns=\"2\">\n");
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
            if (field.isAnnotationPresent(NotNull.class) || field.isAnnotationPresent(NotBlank.class)) {
                required = true;
            }
            if (field.getType().equals(Date.class)) {
                tag = "p:calendar";
            } else if (field.getType().equals(BigDecimal.class)) {
                tag = "xc:inputNumber";
            } else if (field.isAnnotationPresent(ManyToOne.class) || field.getType().isEnum()) {
                tag = "h:selectOneMenu";
                if (!field.getType().isEnum()) {
                    converter = "entityConverter";
                }
                hasSelectItem = true;
                hasEmptySelect = true;
            } else if (field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToMany.class)) {
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
        view.append(managedBean).append(".save}\" value=\"#{");
        view.append(resourceBundle).append("['save']}\" />\n");
        view.append("            <xc:audit for=\"#{").append(managedBean).append(".entity}\"/>\n");
        view.append("        </div>\n");
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
        if (annotation != null && ((ManyToOne) annotation).fetch() != null && ((ManyToOne) annotation).fetch().equals(FetchType.LAZY)) {
            return true;
        }
        annotation = field.getAnnotation(ManyToMany.class);
        if (annotation != null && ((((ManyToMany) annotation).fetch() == null) || ((ManyToMany) annotation).fetch().equals(FetchType.LAZY))) {
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

    public static byte[] createBeanZipFile(List<MappedBean> mappedBeans, String classBean) throws IOException, TemplateException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(baos);
        out.setLevel(Deflater.DEFAULT_COMPRESSION);
        for (MappedBean mappedBean : mappedBeans) {
            String classSimpleName = mappedBean.getEntityClass().getSimpleName();
            //Managed bean
            putEntry(out, "mb/" + classSimpleName + "MB.java", mappedBean.getManagedBean());
            //BO
            putEntry(out, "bo/" + classSimpleName + "BO.java", mappedBean.getBusinnesObject());
            //DAO
            putEntry(out, "dao/" + classSimpleName + "DAO.java", mappedBean.getDao());
            //DAO Impl
            putEntry(out, "dao/impl/" + classSimpleName + "DAOImpl.java", mappedBean.getDaoImpl());
            //create
            putEntry(out, "views/" + getNameLower(mappedBean.getEntityClass()) + "/create" + classSimpleName + ".xhtml", mappedBean.getCreateView());
            //form
            putEntry(out, "views/" + getNameLower(mappedBean.getEntityClass()) + "/formCreate" + classSimpleName + ".xhtml", mappedBean.getFormCreateView());
            //list
            putEntry(out, "views/" + getNameLower(mappedBean.getEntityClass()) + "/list" + classSimpleName + ".xhtml", mappedBean.getListView());
            //menu
            putEntry(out, "views/" + getNameLower(mappedBean.getEntityClass()) + "/menu" + classSimpleName + ".xhtml", mappedBean.getMenu());
        }
        //class bean
        putEntry(out, "mb/ClassMB.java", classBean);

        out.finish();
        out.flush();
        out.close();

        return baos.toByteArray();
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

    public static void main(String[] args) {
        //  System.out.println(getView(Person.class));
    }
    /*
     * public static void main(String[] args) throws IOException {
     *
     *
     * PackageInfo packageInfo = new PackageInfo();
     * packageInfo.setManagedBean("com.mb");
     * packageInfo.setBusinessObject("com.bo"); packageInfo.setDao("com.dao");
     * packageInfo.setDaoImpl("com.dao.impl");
     * packageInfo.setBaseDAO("com.dao.BaseDAOImpl");
     *
     * Bean bean = new Bean(Bean.class, BeanType.DAO_IMPL);
     * bean.setPackageInfo(packageInfo); try {
     * System.out.println(createBean(bean, packageInfo, "Ayslan")); } catch
     * (IOException ex) {
     * Logger.getLogger(BeanCreator.class.getName()).log(Level.SEVERE, null,
     * ex); } catch (TemplateException ex) {
     * Logger.getLogger(BeanCreator.class.getName()).log(Level.SEVERE, null,
     * ex); } }
     *
     */
}
