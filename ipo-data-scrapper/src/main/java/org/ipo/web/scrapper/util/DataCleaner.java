package org.ipo.web.scrapper.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataCleaner {

    private static final Logger LOG = LoggerFactory.getLogger(DataCleaner.class);

    public String cleanData(int i, StringBuilder text) {
        try {
            return switch (i) {
                case 0 -> cleanIPOName(text);
                case 1 -> cleanPrice(text);
                case 2 -> cleanGMP(text);
                case 3 -> cleanEstListingPrice(text);
                case 5 -> cleanIPOSize(text);
                case 6 -> cleanLotSize(text);
                case 7 -> cleanOpenDate(text);
                case 8 -> cleanCloseDate(text);
                case 9 -> cleanBOADate(text);
                case 10 -> cleanListingDate(text);
                default -> {
                    LOG.info("Exceed the Range fix it");
                    yield trim(text);
                }
            };
        }
        catch (Exception ex) {
            LOG.error("Exception occurred with {}:{}", i, text, ex);
            return text.toString();
        }

    }

    private String cleanListingDate(StringBuilder text) {
        return removeNull(text);
    }

    private String cleanBOADate(StringBuilder text) {
        return removeNull(text);
    }

    private String cleanCloseDate(StringBuilder text) {
        return removeNull(text);
    }

    private String cleanOpenDate(StringBuilder text) {
        return removeNull(text);
    }

    private String cleanLotSize(StringBuilder text) {
        return removeNull(text);
    }

    private String removeNull(StringBuilder text) {
        replaceString(text, "null");

        return trim(text);
    }

    private String cleanIPOSize(StringBuilder text) {
        replaceString(text, "null");
        removeString(text, "Cr");
        replaceString(text, "₹");

        return trim(text);
    }

    private String cleanEstListingPrice(StringBuilder text) {
        replaceString(text, "null");
        removeString(text, "(");


        return trim(text);
    }

    private String cleanIPOName(StringBuilder text) {
        removeString(text, "Open");
        removeString(text, "Upcoming");
        removeString(text, "Closing");

        replaceString(text, "null");

        removeStringPart(text, "Close");
        removeStringPart(text,"[email");
        removeStringPart(text,"Listing Today");


        return trim(text);
    }


    private String cleanPrice(StringBuilder text) {
        replaceString(text, "null");

        return trim(text);
    }

    public String cleanGMP(StringBuilder text) {
        removeString(text, "--");

        return trim(text);
    }

    private void removeString(StringBuilder text, String textRemove) {
        int i = text.indexOf(textRemove);
        if (i >= 0) {
            text.delete(i, text.length());
        }
    }

    private void replaceString(StringBuilder text, String textReplace) {
        int i = text.indexOf(textReplace);
        if (i >= 0) {
            text.replace(i, textReplace.length(), "");
        }
    }

    private String trim(StringBuilder sb) {
        while (!sb.isEmpty() && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }

        // Trim trailing spaces
        while (!sb.isEmpty() && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public void removeStringPart(StringBuilder text, String removePart) {
        // Find the index of the part to remove
        int index = text.indexOf(removePart);

        // If the part is found, delete it and everything after it
        if (index != -1) {
            text.delete(index, text.length());
        }
    }
}
