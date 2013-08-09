package com.xpert.showcase.mb;

import com.xpert.faces.utils.FacesUtils;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class DownloadMB {
    
    public void download() throws IOException, InterruptedException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream));
        writer.write("xpert-framework");
        //wait 5s
        Thread.sleep(5000);
        writer.close();
        FacesUtils.download(byteArrayOutputStream.toByteArray(), "text/plain", "file.txt");
    }
}
