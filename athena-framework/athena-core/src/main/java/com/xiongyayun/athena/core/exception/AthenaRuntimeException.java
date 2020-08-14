package com.xiongyayun.athena.core.exception;

import com.xiongyayun.athena.core.ErrorConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;

/**
 * AthenaRuntimeException
 *
 * @author: 熊亚运
 * @date: 2019-05-21
 */
public class AthenaRuntimeException extends NestedRuntimeException implements AthenaInnerException {
	private static final long serialVersionUID = 672694596335429564L;
	protected static final Logger log = LoggerFactory.getLogger(AthenaRuntimeException.class);

    private Object[] args;
	private String code;

	@Override
	public Object[] getArgs() {
		return args;
	}

	public String getCode() {
		return code;
	}

	public AthenaRuntimeException() {
        super(ErrorConstant.SYSTEM_ERROR_UNDEFINED);
    }

    public AthenaRuntimeException(Throwable throwable) {
        super(ErrorConstant.SYSTEM_ERROR_UNDEFINED, throwable);
    }

    public AthenaRuntimeException(@Nullable String message) {
        super(message);
    }

	public AthenaRuntimeException(@Nullable String message, @Nullable Throwable cause) {
		super(message, cause);
	}

	public AthenaRuntimeException(@Nullable String message, @Nullable String code) {
		super(message);
		this.code = code;
	}

    public AthenaRuntimeException(@Nullable String message, @Nullable Object[] args) {
        this(message);
        this.args = args;
    }

    public AthenaRuntimeException(@Nullable String message, @Nullable Object[] args, @Nullable Throwable cause) {
        super(message, cause);
        this.args = args;
    }

	public AthenaRuntimeException(@Nullable String message, @Nullable String code, @Nullable Throwable cause) {
		super(message, cause);
		this.code = code;
	}

}
