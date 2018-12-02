package com.example.springbootexample.Mail.Until;

import java.util.Locale;
import java.util.ResourceBundle;

public class PropertiesUtil {
    private ResourceBundle resource = null;
    private final String fileName;

    //获取文件资源对象
    public PropertiesUtil(String fileName) {

        this.fileName = fileName;
        Locale locale = new Locale("zh", "CN");
        this.resource = ResourceBundle.getBundle(this.fileName, locale);
    }

    //key  value
    public String getValue(String key) {
        String message = this.resource.getString(key);
        return message;
    }
}
