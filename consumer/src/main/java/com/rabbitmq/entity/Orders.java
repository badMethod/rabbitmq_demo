package com.rabbitmq.entity;

import java.io.Serializable;

public class Orders implements Serializable {
    private static final long serialVersionUID = 7285476939923179018L;
    private String id;

    private String name;

    private String msgId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }
}