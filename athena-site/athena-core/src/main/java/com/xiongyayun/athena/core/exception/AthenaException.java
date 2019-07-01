package com.xiongyayun.athena.core.exception;

import com.xiongyayun.athena.core.ErrorConstant;
import com.xiongyayun.athena.core.i18n.I18nService;
import com.xiongyayun.athena.core.utils.SpringContextUtil;
import io.micrometer.core.instrument.util.StringEscapeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedCheckedException;
import org.springframework.lang.Nullable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * AthenaException
 *
 * @author: 熊亚运
 * @date: 2019-05-21
 */
@Slf4j
@SuppressWarnings("all")
public class AthenaException extends NestedCheckedException {
    private static final long serialVersionUID = -5547644358612306631L;
    @Setter
    @Getter
    private Object[] args = new Object[0];

    private ConcurrentMap<String, String> cache = new ConcurrentHashMap(1);

    public AthenaException() {
        super(ErrorConstant.SYSTEM_ERROR_UNDEFINED);
    }

    public AthenaException(Throwable throwable) {
        super(ErrorConstant.SYSTEM_ERROR_UNDEFINED, throwable);
    }

    public AthenaException(@Nullable String messageKey) {
        super(messageKey);
    }

    public AthenaException(@Nullable String messageKey, @Nullable Object[] args) {
        this(messageKey);
        this.args = args;
    }

    public AthenaException(@Nullable String messageKey, @Nullable Throwable cause) {
        super(messageKey, cause);
    }

    public AthenaException(@Nullable String messageKey, @Nullable Object[] args, @Nullable Throwable cause) {
        super(messageKey, cause);
        this.args = args;
    }

    public String getMessageKey() {
        return super.getMessage();
    }

    public String getDefaultMessage() {
        String message;
        if (this.cache != null && (message = this.cache.get(this.getMessageKey())) != null) {
            return message;
        }
        try {
            I18nService i18nService = SpringContextUtil.getBean(I18nService.class);
            if (i18nService != null) {
                message = i18nService.get(this.getMessageKey(), this.args, this.getMessageKey());
                this.cache.put(this.getMessageKey(), message);
                return message;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return super.getMessage();
    }

//	public String[] getVariable() {
//		if (this.variables == null) {
//			return null;
//		} else {
//			String[] escapedVariable = new String[this.variables.length];
//
//			for(int i = 0; i < this.variables.length; ++i) {
//				escapedVariable[i] = StringEscapeUtils.escapeHtml(this.variables[i]);
//			}
//
//			return escapedVariable;
//		}
//	}
}
