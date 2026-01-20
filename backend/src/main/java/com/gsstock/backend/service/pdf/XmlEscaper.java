package com.gsstock.backend.service.pdf;

public final class XmlEscaper {

    private XmlEscaper() {}

    public static String esc(String s) {
        if (s == null) return "";
        return s
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
