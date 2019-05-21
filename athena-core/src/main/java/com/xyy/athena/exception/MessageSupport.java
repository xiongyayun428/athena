package com.xyy.athena.exception;

/**
 * MessageSupport
 *
 * @author: 熊亚运
 * @date: 2019-05-21
 */
public class MessageSupport implements Messageable {
    private static final long serialVersionUID = -7914716472642453867L;
    private String defaultMessage = null;
    private String messageKey = null;
    private Object[] args = new Object[0];

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getDefaultMessage() {
        return this.defaultMessage;
    }

    @Override
    public boolean hasDefaultMessage() {
        return this.defaultMessage != null;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageKey() {
        return this.messageKey;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public Object[] getArgs() {
        return this.args;
    }
}

