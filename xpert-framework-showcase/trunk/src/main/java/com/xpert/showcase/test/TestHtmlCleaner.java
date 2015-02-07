/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.showcase.test;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.SimpleXmlSerializer;
import org.htmlcleaner.TagNode;

/**
 *
 * @author Ayslan
 */
public class TestHtmlCleaner {

    private static String content = "<span><div id=\"j_idt94:j_idt95:panelTab1\">\n"
            + "\n"
            + "\n"
            + "                        <h1>Title</h1>\n"
            + "                        <h2>Subtitle</h2>\n"
            + "                        <p>\n"
            + "                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras at accumsan mauris.\n"
            + "                            Nulla a accumsan nisl. Nam dapibus a velit quis consequat. Mauris a volutpat sem. Suspendisse semper enim in enim gravida, quis maximus ante \n"
            + "                            lobortis. Fusce pharetra, dui in dignissim rutrum, nunc nisl tempor ante, eu pellentesque quam metus in metus. Aliquam malesuada urna non urna molestie, sed convallis dolor condimentum. Vestibulum imperdiet tristique lacus at fringilla. Nunc tempus metus a arcu vehicula, in scelerisque ante commodo. \n"
            + "                        </p>\n"
            + "                        <hr>\n"
            + "                        <p>\n"
            + "                            Lorem ipsum dolor sit amet, <span style=\"color: red;\">consectetur adipiscing elit</span>. Cras at accumsan mauris. Nulla a accumsan nisl. Nam dapibus a velit quis consequat. \n"
            + "                            Mauris a volutpat sem. Suspendisse semper enim in enim gravida, quis maximus ante lobortis. Fusce pharetra, dui in dignissim rutrum, nunc nisl tempor ante, eu pellentesque quam metus in metus. Aliquam malesuada urna non urna molestie, sed convallis dolor condimentum. \n"
            + "                            Vestibulum imperdiet tristique lacus at fringilla. Nunc tempus metus a arcu vehicula, in scelerisque ante commodo. \n"
            + "                        </p>\n"
            + "\n"
            + "                        <h2>Content with image</h2>\n"
            + "\n"
            + "                        Xpert-Framework logo:\n"
            + "                        <img alt=\"Logo\" src=\"/xpert-showcase-war/images/framework-logo.png\">\n"
            + "                        <hr>\n"
            + "\n"
            + "                        <h2>Content with link</h2>\n"
            + "                        Link example: <a href=\"http://google.com\" target=\"_blank\">Google.com</a> \n"
            + "                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras at accumsan mauris.\n"
            + "\n"
            + "                        <br>\n"
            + "                        <br></div></span>";

    public static void main(String[] args) {

        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = cleaner.getProperties();
        props.setAllowHtmlInsideAttributes(true);
        props.setAllowMultiWordAttributes(true);
        props.setRecognizeUnicodeChars(true);
        props.setOmitComments(true);

        TagNode node = cleaner.clean(content);
        SimpleXmlSerializer serializer = new SimpleXmlSerializer(cleaner.getProperties());

        System.out.println(serializer.getAsString(node));

    }
}
