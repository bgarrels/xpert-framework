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
        String fileContent = "This is xpert-framework";
        //wait 5s
        Thread.sleep(5000);
        FacesUtils.download(fileContent.getBytes(), "text/plain", "file.txt");
    }
}
