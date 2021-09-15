package com.xiongyayun.athena.components.common.exception;

import com.xiongyayun.athena.components.common.constant.ErrorConstant;
import com.xiongyayun.athena.components.common.exception.enums.AthenaExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedCheckedException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.Nullable;

/**
 * 运行时异常
 *
 * @author 熊亚运
 * @date 2019-05-21
 */
public class AthenaRuntimeException extends RuntimeException implements AthenaInnerException {
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

	public AthenaRuntimeException(@Nullable AthenaExceptionEnum athenaExceptionEnum) {
		super(athenaExceptionEnum.getMessage());
		this.code = athenaExceptionEnum.getCode();
	}

	public AthenaRuntimeException(@Nullable AthenaExceptionEnum athenaExceptionEnum, @Nullable Object[] args) {
		super(athenaExceptionEnum.getMessage());
		this.code = athenaExceptionEnum.getCode();
		this.args = args;
	}

	public AthenaRuntimeException(@Nullable AthenaExceptionEnum athenaExceptionEnum, @Nullable Throwable cause) {
		super(athenaExceptionEnum.getMessage(), cause);
		this.code = athenaExceptionEnum.getCode();
	}

	public AthenaRuntimeException(@Nullable AthenaExceptionEnum athenaExceptionEnum, @Nullable Object[] args, @Nullable Throwable cause) {
		super(athenaExceptionEnum.getMessage(), cause);
		this.code = athenaExceptionEnum.getCode();
		this.args = args;
	}

	@Nullable
	public String getDefaultMessage() {
		return NestedExceptionUtils.buildMessage(super.getMessage(), getCause());
	}

	@Nullable
	public Throwable getRootCause() {
		return NestedExceptionUtils.getRootCause(this);
	}

	/**
	 * 获取最具体的错误信息
	 * @return
	 */
	public Throwable getMostSpecificCause() {
		Throwable rootCause = getRootCause();
		return (rootCause != null ? rootCause : this);
	}

	public boolean contains(@Nullable Class<?> exType) {
		if (exType == null) {
			return false;
		}
		if (exType.isInstance(this)) {
			return true;
		}
		Throwable cause = getCause();
		if (cause == this) {
			return false;
		}
		if (cause instanceof NestedCheckedException) {
			return ((NestedCheckedException) cause).contains(exType);
		}
		else {
			while (cause != null) {
				if (exType.isInstance(cause)) {
					return true;
				}
				if (cause.getCause() == cause) {
					break;
				}
				cause = cause.getCause();
			}
			return false;
		}
	}

}
