package com.xpert.maker;

import com.xpert.i18n.XpertResourceBundle;
import com.xpert.maker.model.ViewEntity;
import com.xpert.maker.model.ViewField;
import com.xpert.persistence.utils.EntityUtils;
import com.xpert.utils.HumaniseCamelCase;
import com.xpert.utils.StringUtils;
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
import java.text.SimpleDateFormat;
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
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Ayslan
 */
public class BeanCreator {

    private static final Logger logger = Logger.getLogger(BeanCreator.class.getName());
    private static final Configuration CONFIG = new Configuration();
    private static final String AUTHOR = "#Author";
    private static final String GET_PREFIX = "get";
    public static final String DEFAULT_RESOURCE_BUNDLE = "msg";
    public static final String DEFAULT_TEMPLATE = "/template/mainTemplate.xhtml";
    private static final int DEFAULT_SIZE = 70;
    private static final int DEFAULT_MAX_LENGTH = 255;
    private static final int SIZE_ANNOTATION_MAX_DEFAULT = 2147483647;
    private static final String[] LOCALES_MAKER = {"pt_BR", "en", "es"};
    public static final String SUFFIX_MANAGED_BEAN = "MB";
    public static final String SUFFIX_BUSINESS_OBJECT = "BO";
    public static final String SUFFIX_DAO = "DAO";
    public static final String SUFFIX_DAO_IMPL = "DAOImpl";
    public static final String PREFFIX_VIEW_CREATE = "create";
    public static final String PREFFIX_VIEW_FORM_CREATE = "formCreate";
    public static final String PREFFIX_VIEW_LIST = "list";
    public static final String PREFFIX_VIEW_MENU = "menu";
    public static final String PREFFIX_VIEW_DETAIL = "detail";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        try {
            CONFIG.setObjectWrapper(new DefaultObjectWrapper());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String createBean(Bean bean, BeanConfiguration configuration) throws IOException, TemplateException {

        ViewEntity viewEntity = createViewEntity(bean.getEntity(), configuration);
        String templatePath = bean.getBeanType().getTemplate();

        if (bean.getBeanType().isView()) {
            return getViewTemplate(viewEntity, configuration.getResourceBundle(), templatePath, configuration.getTemplate(), configuration);
        }

        Template template = getTemplate(templatePath);
        StringWriter writer = new StringWriter();
        bean.setConfiguration(configuration);
        if (configuration.getAuthor() == null || configuration.getAuthor().trim().isEmpty()) {
            configuration.setAuthor(AUTHOR);
        }
        bean.setAuthor(configuration.getAuthor());
        template.process(bean, writer);

        writer.flush();
        writer.close();

        return writer.toString();
    }

    public static String getViewTemplate(ViewEntity viewEntity, String resourceBundle,
            String templatePath, String xhtmlTemplate, BeanConfiguration configuration) throws TemplateException, IOException {
        Template template = getTemplate(templatePath);
        StringWriter writer = new StringWriter();
        Map attributes = getDefaultParameters();
        attributes.put("entity", viewEntity);
        attributes.put("resourceBundle", resourceBundle);
        attributes.put("configuration", configuration);
        attributes.put("template", xhtmlTemplate);
        template.process(attributes, writer);
        writer.flush();
        writer.close();
        return writer.toString();
    }

    public static ViewEntity createViewEntity(Class clazz, BeanConfiguration configuration) {
        ViewEntity entity = new ViewEntity();
        entity.setName(clazz.getSimpleName());
        entity.setPrimeFacesVersion(configuration.getPrimeFacesVersion());
        entity.setIdFieldName(EntityUtils.getIdFieldName(clazz));
        List<Field> fields = getFields(clazz);
        for (Field field : fields) {
            if (isAnnotationPresent(field, Transient.class)) {
                continue;
            }
            ViewField viewField = new ViewField();
            viewField.setName(field.getName());
            //@Id
            if (isAnnotationPresent(field, Id.class)) {
                viewField.setId(true);
                //@ManyToMany
            } else if (isAnnotationPresent(field, ManyToMany.class)) {
                viewField.setManyToMany(true);
                //@OneToMany
            } else if (isAnnotationPresent(field, OneToMany.class)) {
                viewField.setOneToMany(true);
                //@OneToOne
            } else if (isAnnotationPresent(field, OneToOne.class)) {
                viewField.setOneToOne(true);
                //@OneToOne
            } else if (isAnnotationPresent(field, ManyToOne.class)) {
                viewField.setManyToOne(true);
                //Enum
            } else if (field.getType().isEnum()) {
                viewField.setEnumeration(true);
                //Date
            } else if (field.getType().equals(Date.class) || field.getType().equals(Calendar.class)) {
                viewField.setDate(true);
                //Boolean
            } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                viewField.setYesNo(true);
                //Decimal Number(BigDecimal, Double)
            } else if (isDecimal(field)) {
                viewField.setDecimal(true);
                //Integer
            } else if (isInteger(field)) {
                viewField.setInteger(true);
                //String
            } else if (field.getType().equals(String.class)) {
                int maxlength = DEFAULT_MAX_LENGTH;
                Size size = field.getAnnotation(Size.class);
                if (size != null && size.max() != SIZE_ANNOTATION_MAX_DEFAULT) {
                    maxlength = size.max();
                }
                viewField.setMaxlength(maxlength + "");
                viewField.setString(true);
            }
            if (isRequired(field)) {
                viewField.setRequired(true);
            }
            viewField.setLazy(isLazy(field));
            if (field.getType().equals(Collection.class) || field.getType().equals(List.class) || field.getType().equals(Set.class)) {
                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                Class<?> listType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                viewField.setTypeName(listType.getSimpleName());
            } else {
                viewField.setTypeName(field.getType().getSimpleName());
            }
            entity.getFields().add(viewField);
        }

        return entity;
    }
    
    public static String getClassBean( List<Class> classes, BeanConfiguration configuration) {
        try {
            Template template = BeanCreator.getTemplate("class-bean.ftl");
            StringWriter writer = new StringWriter();
            Map attributes = new HashMap();
            attributes.put("classes", classes);
            attributes.put("configuration", configuration);
            attributes.put("package", configuration.getManagedBean() == null ? "" : configuration.getManagedBean());
            template.process(attributes, writer);

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

    public static boolean isSerialVersionUID(Field field) {
        return field.getName().equals("serialVersionUID");
    }

    public static String getI18N(Class clazz) {

        List<Field> fields = getFields(clazz);
        StringBuilder builder = new StringBuilder();
        String className = StringUtils.getLowerFirstLetter(clazz.getSimpleName());
        String humanName = new HumaniseCamelCase().humanise(clazz.getSimpleName());
        builder.append("\n\n#").append(clazz.getSimpleName()).append("\n");
        builder.append(className).append("=").append(humanName).append("\n");

        //create CRUD i18n, like: person.create, person.list, person.detail
        Locale locale = Locale.getDefault();
        builder.append(className).append(".create").append("=").append(XpertResourceBundle.get("makerCreate", locale)).append(" ").append(humanName).append("\n");
        builder.append(className).append(".list").append("=").append(XpertResourceBundle.get("makerList", locale)).append(" ").append(humanName).append("\n");
        builder.append(className).append(".detail").append("=").append(humanName).append(" - ").append(XpertResourceBundle.get("makerDetail", locale)).append("\n");
        boolean first = false;
        for (Field field : fields) {
            if (isSerialVersionUID(field)) {
                continue;
            }
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

    public static String getDialogWidget(Class clazz) {
        return "widget" + clazz.getSimpleName() + "Detail";
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

    public static String getI18N(List<MappedBean> mappedBeans) {
        if (mappedBeans == null) {
            return null;
        }
        StringBuilder i18n = new StringBuilder();
        for (MappedBean mappedBean : mappedBeans) {
            i18n.append(mappedBean.getI18n());
        }
        return i18n.toString();
    }

    public static void writeFile(String location, String fileName, String content, StringBuilder logBuilder) {
        if (location == null || location.isEmpty()) {
            return;
        }
        //create dir
        File dir = new File(location);
        dir.mkdirs();
        //create file
        File file = new File(location + File.separator + fileName);
        if (!file.exists()) {
            FileOutputStream outputStream;
            try {
                log(logBuilder, "Writing file: " + fileName + " on dir: " + file.getAbsolutePath());
                outputStream = new FileOutputStream(file);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                writer.write(content);
                writer.close();
                outputStream.close();
            } catch (IOException ex) {
                log(logBuilder, "IOException: " + ex.getMessage() + ". More details see java log.");
                logger.log(Level.SEVERE, null, ex);
            }
        } else {
            log(logBuilder, "File: " + fileName + " already exists");
        }
    }

    public static void log(StringBuilder logBuilder, String message) {
        if (logBuilder != null) {
            logBuilder.append(SIMPLE_DATE_FORMAT.format(new Date())).append(" ").append(message).append("\n");
        }
    }

    public static String getManagedBeanSuffix(BeanConfiguration configuration) {
        if (configuration != null && configuration.getManagedBeanSuffix() != null && !configuration.getManagedBeanSuffix().isEmpty()) {
            return configuration.getManagedBeanSuffix();
        }
        return SUFFIX_MANAGED_BEAN;
    }

    public static String getBusinessObjectSuffix(BeanConfiguration configuration) {
        if (configuration != null && configuration.getBusinessObjectSuffix() != null && !configuration.getBusinessObjectSuffix().isEmpty()) {
            return configuration.getBusinessObjectSuffix();
        }
        return SUFFIX_BUSINESS_OBJECT;
    }

    public static void writeBean(List<MappedBean> mappedBeans, BeanConfiguration configuration, StringBuilder logBuilder) {
        for (MappedBean mappedBean : mappedBeans) {
            log(logBuilder, "Mapping class " + mappedBean.getClassName());
            String classSimpleName = mappedBean.getEntityClass().getSimpleName();
            String nameLower = getNameLower(mappedBean.getEntityClass());

            //ManagedBean
            if (configuration.getManagedBeanLocation() != null) {
                writeFile(configuration.getManagedBeanLocation(), classSimpleName + getManagedBeanSuffix(configuration) + ".java", mappedBean.getManagedBean(), logBuilder);
            }
            //BusinessObject
            if (configuration.getBusinessObjectLocation() != null) {
                writeFile(configuration.getBusinessObjectLocation(), classSimpleName + getBusinessObjectSuffix(configuration) + ".java", mappedBean.getBusinnesObject(), logBuilder);

            }
            //DAO
            if (configuration.getDaoLocation() != null) {
                writeFile(configuration.getDaoLocation(), classSimpleName + SUFFIX_DAO + ".java", mappedBean.getDao(), logBuilder);

            }
            //DAOImpl
            if (configuration.getDaoImplLocation() != null) {
                writeFile(configuration.getDaoImplLocation(), classSimpleName + SUFFIX_DAO_IMPL + ".java", mappedBean.getDaoImpl(), logBuilder);
            }

            //Views
            if (configuration.getViewLocation() != null && !configuration.getViewLocation().trim().isEmpty()) {
                //View Create
                writeFile(configuration.getViewLocation() + File.separator + nameLower, PREFFIX_VIEW_CREATE + classSimpleName + ".xhtml", mappedBean.getCreateView(), logBuilder);
                //View Form Create
                writeFile(configuration.getViewLocation() + File.separator + nameLower, PREFFIX_VIEW_FORM_CREATE + classSimpleName + ".xhtml", mappedBean.getFormCreateView(), logBuilder);
                //View Menu
                writeFile(configuration.getViewLocation() + File.separator + nameLower, PREFFIX_VIEW_MENU + classSimpleName + ".xhtml", mappedBean.getMenu(), logBuilder);

                //View List
                writeFile(configuration.getViewLocation() + File.separator + nameLower, PREFFIX_VIEW_LIST + classSimpleName + ".xhtml", mappedBean.getListView(), logBuilder);

                //View Detail
                writeFile(configuration.getViewLocation() + File.separator + nameLower, PREFFIX_VIEW_DETAIL + classSimpleName + ".xhtml", mappedBean.getDetail(), logBuilder);
            }
        }

        log(logBuilder, "Finished!");

    }

    public static byte[] createBeanZipFile(List<MappedBean> mappedBeans, String classBean, String viewTemplate, BeanConfiguration configuration) throws IOException, TemplateException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(baos);
        out.setLevel(Deflater.DEFAULT_COMPRESSION);
        String i18n = getI18N(mappedBeans);
        String viewPath = getViewPath(configuration);
        for (MappedBean mappedBean : mappedBeans) {
            String classSimpleName = mappedBean.getEntityClass().getSimpleName();
            String nameLower = getNameLower(mappedBean.getEntityClass());
            //Managed bean
            putEntry(out, "mb/" + classSimpleName + getManagedBeanSuffix(configuration) + ".java", mappedBean.getManagedBean());
            //BO
            putEntry(out, "bo/" + classSimpleName + getBusinessObjectSuffix(configuration) + ".java", mappedBean.getBusinnesObject());
            //DAO
            putEntry(out, "dao/" + classSimpleName + SUFFIX_DAO + ".java", mappedBean.getDao());
            //DAO Impl
            putEntry(out, "dao/impl/" + classSimpleName + SUFFIX_DAO_IMPL + ".java", mappedBean.getDaoImpl());
            //create
            putEntry(out, viewPath + nameLower + "/" + PREFFIX_VIEW_CREATE + classSimpleName + ".xhtml", mappedBean.getCreateView());
            //form
            putEntry(out, viewPath + nameLower + "/" + PREFFIX_VIEW_FORM_CREATE + classSimpleName + ".xhtml", mappedBean.getFormCreateView());
            //list
            putEntry(out, getUrlForList(nameLower, classSimpleName, configuration) + ".xhtml", mappedBean.getListView());
            //detail
            putEntry(out, viewPath + nameLower + "/" + PREFFIX_VIEW_DETAIL + classSimpleName + ".xhtml", mappedBean.getDetail());
            //menu
            putEntry(out, viewPath + nameLower + "/" + PREFFIX_VIEW_MENU + classSimpleName + ".xhtml", mappedBean.getMenu());

        }
        //template
        putEntry(out, getViewTemplatePath(configuration), viewTemplate);
        //menubar
        putEntry(out, "menu.xhtml", getMenubar(mappedBeans, getResourceBundle(configuration.getResourceBundle()), configuration));
        //class bean
        putEntry(out, "mb/Class" + configuration.getManagedBeanSuffix() + ".java", classBean);
        //i18n
        for (String locale : LOCALES_MAKER) {
            putEntry(out, "messages_" + locale + ".properties", i18n);
        }

        out.finish();
        out.flush();
        out.close();

        return baos.toByteArray();
    }

    public static String getMenuI18N(List<MappedBean> mappedBeans) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n#").append("menu").append("\n");
        for (MappedBean mappedBean : mappedBeans) {
            builder.append("menu.").append(StringUtils.getLowerFirstLetter(mappedBean.getEntityClass().getSimpleName()));
            builder.append("=");
            builder.append(mappedBean.getHumanClassName());
            builder.append("\n");
        }

        return builder.toString();
    }

    private static String getUrlForList(String nameLower, String classSimpleName, BeanConfiguration configuration) {
        return getViewPath(configuration) + nameLower + "/" + PREFFIX_VIEW_LIST + classSimpleName;
    }

    private static String getResourceBundle(String resourceBundle) {
        if (resourceBundle == null || resourceBundle.trim().isEmpty()) {
            return DEFAULT_RESOURCE_BUNDLE;
        }
        return resourceBundle;
    }

    public static String getMenubar(List<MappedBean> mappedBeans, String resourceBundle, BeanConfiguration configuration) {
        if (mappedBeans == null || mappedBeans.isEmpty()) {
            return null;
        }
        try {

            Template template = BeanCreator.getTemplate("menubar.ftl");
            StringWriter writer = new StringWriter();
            Map attributes = new HashMap();
            List<MenuModel> menus = new ArrayList<MenuModel>();
            for (MappedBean mappedBean : mappedBeans) {
                String nameLower = StringUtils.getLowerFirstLetter(mappedBean.getEntityClass().getSimpleName());
                menus.add(new MenuModel("#{" + resourceBundle + "['menu." + nameLower + "']}",
                        "/" + getUrlForList(nameLower, mappedBean.getEntityClass().getSimpleName(), configuration) + ".jsf"));
            }
            attributes.put("menus", menus);
            template.process(attributes, writer);
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

    public static String getViewPath(BeanConfiguration configuration) {
        if (configuration != null && configuration.getViewPath() != null && !configuration.getViewPath().isEmpty()) {
            StringBuilder builder = new StringBuilder();
            if (configuration.getViewPath().startsWith("/")) {
                builder.append(configuration.getViewPath().substring(1, configuration.getViewPath().length()));
            } else {
                builder.append(configuration.getViewPath());
            }
            if (!configuration.getViewPath().endsWith("/")) {
                builder.append("/");
            }
            return builder.toString();
        }
        return "view/";
    }

    public static String getViewTemplatePath(BeanConfiguration configuration) {
        if (configuration.getTemplate() != null && !configuration.getTemplate().isEmpty()) {
            return configuration.getTemplate().startsWith("/") ? configuration.getTemplate().substring(1, configuration.getTemplate().length()) : configuration.getTemplate();
        } else {
            return DEFAULT_TEMPLATE;
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
        for (Field field : entity.getDeclaredFields()) {
            if (isSerialVersionUID(field)) {
                continue;
            }
            fields.add(field);
        }
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
            return field.getDeclaringClass().getMethod(GET_PREFIX + StringUtils.getUpperFirstLetter(field.getName()));
        } catch (Exception ex) {
            //nothing
            return null;
        }
    }

    public static String getNameLower(Class clazz) {
        return StringUtils.getLowerFirstLetter(clazz.getSimpleName());
    }

    public static boolean isDecimal(Field field) {
        return field.getType().equals(BigDecimal.class) || field.getType().equals(Double.class) || field.getType().equals(double.class);
    }

    public static boolean isInteger(Field field) {
        return field.getType().equals(Integer.class) || field.getType().equals(int.class)
                || field.getType().equals(Long.class) || field.getType().equals(long.class);
    }

    public static boolean isRequired(Field field) {
        return isAnnotationPresent(field, NotNull.class) || isAnnotationPresent(field, NotBlank.class) || isAnnotationPresent(field, NotEmpty.class);
    }
}
