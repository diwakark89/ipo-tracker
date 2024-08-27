package org.ipo.web.scrapper.util;

import org.ipo.web.scrapper.model.IPOTableData;

public class FilterData {
    public boolean shouldItBeAdded(IPOTableData data){
        return isIPONameOK(data.getIpoName());
    }

    private boolean isIPONameOK(String str){
        return !(str==null || str.isEmpty() || str.contains("Zerodha Trade@20"));
    }
}
