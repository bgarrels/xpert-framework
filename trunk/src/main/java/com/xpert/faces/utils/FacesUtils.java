package com.xpert.faces.utils;

import java.io.IOException;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ayslan
 */
public class FacesUtils {

    public static Object getFromSession(String attributeName) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        return session.getAttribute(attributeName);
    }

    public static String getParameter(String parameterName) {
        HttpServletRequest request = getRequest();
        return request.getParameter(parameterName);
    }

    public static void addToSession(String attributeName, Object value) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        session.setAttribute(attributeName, value);
    }

    public static void removeFromSession(String attributeName) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        session.removeAttribute(attributeName);
    }

    public static void invalidateSession() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ((HttpSession) externalContext.getSession(true)).invalidate();
    }

    public static HttpServletRequest getRequest() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        return request;
    }

    public static HttpServletResponse getResponse() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        return response;
    }

    public static ServletContext getServletContext() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return (ServletContext) externalContext.getContext();
    }

    public static void redirect(String url) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + url);
            context.responseComplete();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Get a bean form EL context
     *
     * @param <T>
     * @param beanName
     * @param clazz
     * @return
     */
    public static <T> T getBeanByEl(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        return (T) context.getApplication().evaluateExpressionGet(context, beanName, Object.class);
    }

    public static void setValueEl(String expression, Object newValue) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
        Class bindClass = valueExp.getType(elContext);
        if (bindClass.isPrimitive() || bindClass.isInstance(newValue)) {
            valueExp.setValue(elContext, newValue);
        }
    }

    public static void setNewValueBean(String beanName, String attribute, Object newValue) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getApplication().getELResolver().setValue(context.getELContext(), beanName, attribute, newValue);
    }

    /**
     * Método responsável por limpar os valores de um determinado componente Ex:
     * ao passar um form o método vai limpar seus componentes "filhos"
     *
     * @param pComponent
     */
    public static void clearComponent(UIComponent pComponent) {
        if (pComponent instanceof EditableValueHolder) {
            EditableValueHolder editableValueHolder = (EditableValueHolder) pComponent;
            editableValueHolder.setSubmittedValue(null);
            editableValueHolder.setValue(null);
            editableValueHolder.setValid(true);
        }
        for (UIComponent child : pComponent.getChildren()) {
            clearComponent(child);
        }
    }

    public static String getRealPath(String caminho) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
        String realPath = scontext.getRealPath(caminho);

        return realPath;
    }

    /**
     * Add Cookie no HttpServletResponse
     *
     * @param nomeCookie
     * @return
     */
    public static void addCookie(String nome, String valor) {
        Cookie cookie = new Cookie(nome, valor);
        cookie.setPath("/");
        cookie.setMaxAge(2592000);
        // NOTE: If cookie version is set to 1, cookie values will be quoted.
        cookie.setVersion(0);
        HttpServletResponse response = getResponse();
        response.addCookie(cookie);
    }

    public static String getBrowser() {
        return getRequest().getHeader("User-Agent");
    }

    public static Cookie getCookie(String cookieName) {
        HttpServletRequest request = getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static String getCookieValue(String cookieName) {
        Cookie cookie = getCookie(cookieName);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * Removes Cookie
     *
     * @param nomeCookie
     * @return
     */
    public static void removeCookie(String nomeCookie) {

        Cookie cookie = getCookie(nomeCookie);
        if (cookie != null) {
            HttpServletResponse response = getResponse();
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

    }

    /**
     *
     * Method to find a component in ViewRoot
     *
     * @param id
     * @return
     */
    public static UIComponent findComponentInRoot(String id) {
        UIComponent component = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            UIComponent root = facesContext.getViewRoot();
            component = findComponent(root, id);
        }

        return component;
    }

    public static UIComponent findComponent(UIComponent base, String id) {
        if (id.equals(base.getId())) {
            return base;
        }

        UIComponent kid = null;
        UIComponent result = null;
        Iterator kids = base.getFacetsAndChildren();
        while (kids.hasNext() && (result == null)) {
            kid = (UIComponent) kids.next();
            if (id.equals(kid.getId())) {
                result = kid;
                break;
            }
            result = findComponent(kid, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }
    public static final String FILE_DOWNLOAD_TOKEN = "xpert.download";

    /**
     *
     * Generates a file to download.
     *
     * This method put a cookie named 'fileDownloadToken', this cookie can be
     * used to control the downloaded file by the "javax.faces.ViewState" (wich
     * is a param submited in JSF forms)
     *
     * @param bytes
     * @param contentType
     * @param nomeArquivo
     */
    public static void download(byte[] bytes, String contentType, String fileName) {

        FacesContext.getCurrentInstance().responseComplete();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            addCookie(FILE_DOWNLOAD_TOKEN, request.getParameter("javax.faces.ViewState"));
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentLength(bytes.length);
            response.getOutputStream().write(bytes);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }

    public static String getIP() {
        String ipAddress = getRequest().getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = getRequest().getRemoteAddr();
        }
        return ipAddress;
    }

    /**
     *
     * Create the FacesContext. This method is useful to Contexts where
     * FacesContext is not created, like a Filter.
     *
     * @param request
     * @param response
     * @return
     */
    public static FacesContext getFacesContext(HttpServletRequest request, HttpServletResponse response) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) {

            FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
            LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
            Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

            facesContext = contextFactory.getFacesContext(request.getSession().getServletContext(), request, response, lifecycle);
            // Set using our inner class
            InnerFacesContext.setFacesContextAsCurrentInstance(facesContext);

            // set a new viewRoot, otherwise context.getViewRoot returns null
            UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, "");
            facesContext.setViewRoot(view);
        }
        return facesContext;
    }

    private abstract static class InnerFacesContext extends FacesContext {

        protected static void setFacesContextAsCurrentInstance(FacesContext facesContext) {
            FacesContext.setCurrentInstance(facesContext);
        }
    }
}
