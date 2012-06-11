package com.xpert.showcase.mb;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class SpreadMB {

    public List<String> getItems() {
        List<String> itens = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            itens.add("Item " + (i + 1));
        }
        return itens;
    }
}
