package com.xyy.athena.exception;

import org.springframework.core.NestedCheckedException;

/**
 * AthenaException
 *
 * @author: 熊亚运
 * @date: 2019-05-21
 */
public class AthenaException extends NestedCheckedException {
    private static final long serialVersionUID = -5547644358612306631L;
    private MessageSupport messageSupport = new MessageSupport();
    private static final String DEFAULTMESSAGE = "xyy.error.undefined";

    public AthenaException() {
        super("");
        this.messageSupport.setDefaultMessage(DEFAULTMESSAGE);
    }

    public AthenaException(String msg) {
        super(msg);
        if ((msg == null) || (msg.trim().length() == 0)) {
            this.messageSupport.setMessageKey(DEFAULTMESSAGE);
        } else {
            this.messageSupport.setMessageKey(msg);
        }
    }

    public AthenaException(String msg, Object[] args) {
        this(msg);
        this.messageSupport.setArgs(args);
    }

    public AthenaException(String msg, Throwable throwable) {
        super(msg, throwable);
        if ((msg == null) || (msg.trim().length() == 0)) {
            this.messageSupport.setMessageKey(DEFAULTMESSAGE);
        } else {
            this.messageSupport.setMessageKey(msg);
        }
    }

    public AthenaException(String msg, Throwable throwable, Object[] args) {
        super(msg, throwable);
        if ((msg == null) || (msg.trim().length() == 0)) {
            this.messageSupport.setMessageKey(DEFAULTMESSAGE);
        } else {
            this.messageSupport.setMessageKey(msg);
        }
        this.messageSupport.setArgs(args);
    }

    public AthenaException(Throwable throwable) {
        super("", throwable);
        this.messageSupport.setMessageKey(DEFAULTMESSAGE);
    }

    public void setDefaultMessage(String messageSupport) {
        this.messageSupport.setDefaultMessage(messageSupport);
    }

    public String getMessageKey() {
        return this.messageSupport.getMessageKey();
    }

    public Object[] getArgs() {
        return this.messageSupport.getArgs();
    }

    public String getDefaultMessage() {
        String defaultMessage = this.messageSupport.getDefaultMessage();
        if (defaultMessage == null) {
            StringBuffer sb = new StringBuffer(super.getClass().getName())
                    .append(" MessageCode: ").append(getMessageKey());
            if (this.messageSupport.getArgs() != null) {
                Object[] args = this.messageSupport.getArgs();
                if (args.length > 0) {
                    sb.append(" Args:");
                }
                for (int i = 0; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
            }
            if (getCause() != null) {
                sb.append(" nested exception is: ").append(getCause());
            }
            return sb.toString();
        }
        return defaultMessage;
    }

    public boolean hasDefaultMessage() {
        return this.messageSupport.hasDefaultMessage();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        if (this.messageSupport.getArgs() != null) {
            Object[] args = this.messageSupport.getArgs();
            if (args.length > 0) {
                sb.append(" Args:");
            }
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
        }
        return sb.toString();
    }
}
