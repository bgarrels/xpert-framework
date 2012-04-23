package com.xpert.faces.utils;

import com.xpert.faces.utils.FacesUtils;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Ayslan
 */
public class FacesJasper {

    public static void createJasperReport(List dataSource, Map parameters, String path, String fileName) {

        try {
            String layout = FacesContext.getCurrentInstance().getExternalContext().getRealPath(path);
            JasperPrint jasperPrint;
            if (dataSource == null || dataSource.isEmpty()) {
                JREmptyDataSource jREmptyDataSource = new JREmptyDataSource();
                jasperPrint = JasperFillManager.fillReport(layout, parameters, jREmptyDataSource);
            } else {
                JRBeanCollectionDataSource jRBeanCollectionDataSource = new JRBeanCollectionDataSource(dataSource);
                jasperPrint = JasperFillManager.fillReport(layout, parameters, jRBeanCollectionDataSource);
            }
            FacesUtils.download(JasperExportManager.exportReportToPdf(jasperPrint), "application/pdf", fileName.endsWith(".pdf") ? fileName : fileName + ".pdf");
        } catch (JRException ex) {
            throw new RuntimeException(ex);
        }
    }
}
