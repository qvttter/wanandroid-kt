package com.li.mykotlinapp.bean;

/************************************************************************
 *@Project: android
 *@Package_Name: com.easyway.pdp.mqtt.bean
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/5/19 
 *@Copyright:(C)2022 . All rights reserved. 
 *************************************************************************/
public class AttributesResponseBean {
    private String sw_title;
    private String sw_version;
    private String sw_tag;
    private String sw_url;

    public AttributesResponseBean(String sw_title, String sw_version, String sw_tag, String sw_url) {
        this.sw_title = sw_title;
        this.sw_version = sw_version;
        this.sw_tag = sw_tag;
        this.sw_url = sw_url;
    }

    public String getSw_title() {
        return sw_title;
    }

    public void setSw_title(String sw_title) {
        this.sw_title = sw_title;
    }

    public String getSw_version() {
        return sw_version;
    }

    public void setSw_version(String sw_version) {
        this.sw_version = sw_version;
    }

    public String getSw_tag() {
        return sw_tag;
    }

    public void setSw_tag(String sw_tag) {
        this.sw_tag = sw_tag;
    }

    public String getSw_url() {
        return sw_url;
    }

    public void setSw_url(String sw_url) {
        this.sw_url = sw_url;
    }
}
