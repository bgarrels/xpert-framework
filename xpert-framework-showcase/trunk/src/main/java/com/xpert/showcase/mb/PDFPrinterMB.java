package com.xpert.showcase.mb;

import com.xpert.showcase.model.Person;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.extensions.component.gchart.model.GChartModel;
import org.primefaces.extensions.component.gchart.model.GChartModelBuilder;
import org.primefaces.extensions.component.gchart.model.GChartType;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class PDFPrinterMB {

    private List<Person> people = new ArrayList<Person>();

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            Person person = new Person("PERSON " + (i + 1), "person." + (i + 1) + "@gmail.com");
            people.add(person);
        }
        generateModelGChart();
    }

    private GChartModel chartModel = null;

    public void generateModelGChart() {
        //from primefaces-extension 
        //http://www.primefaces.org/showcase-ext/sections/gchart/multiple.jsf
        chartModel = new GChartModelBuilder().setChartType(GChartType.COLUMN)
                .addColumns("Year", "Salves", "Expenses")
                .addRow("2010", 1000, 400)
                .addRow("2011", 1200, 800)
                .addRow("2012", 2000, 1800)
                .addRow("2013", 1500, 1800)
                .addRow("2014", 1300, 1000)
                .build();
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public GChartModel getChartModel() {
        return chartModel;
    }

    public void setChartModel(GChartModel chartModel) {
        this.chartModel = chartModel;
    }
    
    
}
