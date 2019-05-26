package com.xyy.athena.core.exception;

import java.io.Serializable;

/**
 * Messageable
 *
 * @author: 熊亚运
 * @date: 2019-05-21
 */
public interface Messageable extends Serializable {
    String getDefaultMessage();

    boolean hasDefaultMessage();

    String getMessageKey();

    Object[] getArgs();
}
