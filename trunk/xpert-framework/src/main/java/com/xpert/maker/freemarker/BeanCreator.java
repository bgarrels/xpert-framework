package com.xpert.maker.freemarker;

import com.xpert.maker.bean.*;
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

    static {
        try {
            CONFIG.setObjectWrapper(new DefaultObjectWrapper());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String createBean(Bean bean, PackageInfo packageInfo, String author, String resourceBundle) throws IOException, TemplateException {

        if (bean.getBeanType().equals(BeanType.VIEW)) {
            return getView(bean.getEntity(), resourceBundle);
        }


        Template template = getTemplate(bean.getBeanType().getTemplate());
        StringWriter writer = new StringWriter();
        bean.setPackageInfo(packageInfo);
        if (author == null || author.trim().isEmpty()) {
            author = "#Author";
        }
        bean.setAuthor(author);
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

    public static String getHeader() {

        StringBuilder builder = new StringBuilder();
        builder.append("<ui:composition  xmlns=\"http://www.w3.org/1999/xhtml\"\n");
        builder.append("                 xmlns:h=\"http://java.sun.com/jsf/html\"\n");
        builder.append("                 xmlns:f=\"http://java.sun.com/jsf/core\"\n");
        builder.append("                 xmlns:ui=\"http://java.sun.com/jsf/facelets\"\n");
        builder.append("                 xmlns:p=\"http://primefaces.org/ui\"\n");
        builder.append("                 xmlns:x=\"http://xpert.com/faces\"\n");
        builder.append("                 xmlns:xc=\"http://java.sun.com/jsf/composite/xpert/components\">\n");

        return builder.toString();
    }

    public static String getView(Class clazz, String resourceBundle) {

        if (resourceBundle == null || resourceBundle.trim().isEmpty()) {
            resourceBundle = "msg";
        }

        Field[] fields = clazz.getDeclaredFields();
        StringBuilder view = new StringBuilder();
        String managedBean = getNameLower(clazz) + "MB";
        view.append(getHeader());
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
        view.append("       <p:commandButton process=\"@form\" update=\"@form\" action=\"#{");
        view.append(managedBean).append(".save}\" value=\"#{");
        view.append(resourceBundle).append("['save']}\" />\n");
        view.append("   <h:form>\n");
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
        String propertyPath = getNameLower(field.getDeclaringClass()) + "." + field.getName();
        view.append("\n");
        view.append("           <h:outputLabel value=\"").append(required ? "* " : "").append("#{").append(resourceBundle).append("['").append(propertyPath).append("']\" />\n");
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
                view.append("               <f:selectItem itemLabel=\"#{").append(resourceBundle).append("['select']}\" />");
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
            //form
            putEntry(out, "views/" + getNameLower(mappedBean.getEntityClass()) + "/form" + classSimpleName + ".xhtml", mappedBean.getView());
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
