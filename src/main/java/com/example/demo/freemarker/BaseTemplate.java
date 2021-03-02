package com.example.demo.freemarker;

import java.util.List;

/**
 * @author: administer
 * @description:
 * @date: 2021/2/8  16:07
 **/
public class BaseTemplate {
    /**
     * page
     */
    protected String pack;

    protected String user;

    protected String description;

    protected String datetime;

    protected String className;

    protected List<String> importList;

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getImportList() {
        return importList;
    }

    public void setImportList(List<String> importList) {
        this.importList = importList;
    }
}
