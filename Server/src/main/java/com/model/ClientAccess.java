package com.model;


public class ClientAccess {

    private Long id;
    private String ipAddress;
    private java.sql.Timestamp accessTime;
    private String parameters;

    @Override
    public String toString() {
        return "ClientAccess{" +
                "id=" + id +
                ", ipAddress='" + ipAddress + '\'' +
                ", accessTime=" + accessTime +
                ", parameters='" + parameters + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }


    public java.sql.Timestamp getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(java.sql.Timestamp accessTime) {
        this.accessTime = accessTime;
    }


    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

}
