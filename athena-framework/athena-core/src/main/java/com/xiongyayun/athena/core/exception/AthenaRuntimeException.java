package com.xiongyayun.athena.core.exception;

import com.xiongyayun.athena.core.ErrorConstant;
import com.xiongyayun.athena.core.i18n.I18nService;
import com.xiongyayun.athena.core.utils.SpringContextUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * AthenaRuntimeException
 *
 * @author: 熊亚运
 * @date: 2019-05-21
 */
@Slf4j
@SuppressWarnings("all")
public class AthenaRuntimeException extends NestedRuntimeException {
    private static final long serialVersionUID = 672694596335429564L;
    @Setter
    @Getter
    private Object[] args = new Object[0];
	@Setter
	@Getter
	private String code;

    private ConcurrentMap<String, String> cache = new ConcurrentHashMap(1);

    public AthenaRuntimeException() {
        super(ErrorConstant.SYSTEM_ERROR_UNDEFINED);
    }

    public AthenaRuntimeException(Throwable throwable) {
        super(ErrorConstant.SYSTEM_ERROR_UNDEFINED, throwable);
    }

    public AthenaRuntimeException(@Nullable String messageKey) {
        super(messageKey);
    }

	public AthenaRuntimeException(@Nullable String code, @Nullable String msg) {
		super(msg);
		this.code = code;
	}

    public AthenaRuntimeException(@Nullable String messageKey, @Nullable Object[] args) {
        this(messageKey);
        this.args = args;
    }

    public AthenaRuntimeException(@Nullable String messageKey, @Nullable Throwable cause) {
        super(messageKey, cause);
    }

    public AthenaRuntimeException(@Nullable String messageKey, @Nullable Object[] args, @Nullable Throwable cause) {
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
}
