package com.xpert.showcase.maker;

import com.xpert.faces.primefaces.PrimeFacesVersion;
import com.xpert.maker.BeanCreator;
import com.xpert.maker.MakerSwingFrame;

/**
 *
 * @author Ayslan
 */
public class Maker extends MakerSwingFrame {

    @Override
    public String getDefaultPackage() {
        return "com.xpert.showcase.model";
    }

    @Override
    public String getDefaultTemplatePath() {
        return "/template/mainTemplate.xhtml";
    }

    @Override
    public String getDefaultResourceBundle() {
        return "msg";
    }

    @Override
    public String getDefaultBaseDAOImpl() {
        return "com.xpert.showcase.application.BaseDAOImpl";
    }

    @Override
    public String getManagedBeanSuffix() {
        return "MB";
    }

    @Override
    public String getBusinessObjectSuffix() {
        return "BO";
    }

    @Override
    public PrimeFacesVersion getPrimeFacesVersion() {
        return PrimeFacesVersion.VERSION_4;
    }

    public static void main(String args[]) {
        run(new Maker());
    }

}
