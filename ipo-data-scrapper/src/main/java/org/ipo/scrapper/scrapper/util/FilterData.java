package org.ipo.scrapper.scrapper.util;

import org.ipo.scrapper.scrapper.model.IPOTableData;

public class FilterData {
    public boolean shouldItBeAdded(IPOTableData data){
        return isIPONameOK(data.getIpoName());
    }

    private boolean isIPONameOK(String str){
        return !(str==null || str.isEmpty() || str.contains("Zerodha Trade@20"));
    }
}
