package com.xpert.maker;

import com.xpert.maker.model.ViewEntity;
import com.xpert.maker.model.ViewField;
import com.xpert.utils.StringUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Ayslan
 */
public class BeanCreatorTemplate {

    private static final String GET_PREFIX = "get";
    private static final int DEFAULT_SIZE = 70;
    private static final int DEFAULT_MAX_LENGTH = 255;

    public static ViewEntity createViewEntity(Class clazz) {
        ViewEntity entity = new ViewEntity();
        List<Field> fields = getFields(clazz);
        for (Field field : fields) {
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
                viewField.setEnumaration(true);
                //Date
            } else if (field.getType().equals(Date.class)) {
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
                if (size != null) {
                    maxlength = size.max();
                }
                viewField.setMaxlength(maxlength);
                viewField.setString(true);
            }
            if (isRequired(field)) {
                viewField.setRequired(true);
            }
            viewField.setLazy(isLazy(field));
            viewField.setTypeName(field.getType().getName());
            entity.getFields().add(viewField);
        }


        return entity;
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
}
