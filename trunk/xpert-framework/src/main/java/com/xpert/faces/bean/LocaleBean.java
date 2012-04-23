package com.xpert.faces.bean;

import com.xpert.faces.utils.FacesUtils;
import java.io.Serializable;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ayslan
 */
@ManagedBean
@SessionScoped
public class LocaleBean implements Serializable{

    private Locale locale;
    private List<Locale> locales = new ArrayList<Locale>();

    public LocaleBean() {
        locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
        locales = new ArrayList<Locale>();
        Iterator<Locale> it = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
        while (it.hasNext()) {
            locales.add(it.next());
        }
    }

    public String changeLocale() {
        String view = FacesUtils.getRequest().getServletPath();
        return view + "?faces-redirect=true;";
    }

    public DecimalFormatSymbols getDecimalFormatSymbols() {
        return new DecimalFormatSymbols(locale);
    }

    public char getDecimalSeparator() {
        return getDecimalFormatSymbols().getDecimalSeparator();
    }

    public char getGroupingSeparator() {
        return getDecimalFormatSymbols().getGroupingSeparator();
    }

    public String getCurrencySymbol() {
        return getDecimalFormatSymbols().getCurrencySymbol();
    }

    public List<Locale> getLocales() {
        return locales;
    }

    public void setLocales(List<Locale> locales) {
        this.locales = locales;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
