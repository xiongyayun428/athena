package com.xiongyayun.athena.core.exception;

import com.xiongyayun.athena.core.constant.ErrorConstant;
import com.xiongyayun.athena.core.exception.enums.AthenaExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedCheckedException;
import org.springframework.lang.Nullable;

/**
 * 一般异常
 *
 * @author 熊亚运
 * @date 2019-05-21
 */
public class AthenaException extends NestedCheckedException implements AthenaInnerException {
    private static final long serialVersionUID = -5547644358612306631L;
	protected static final Logger log = LoggerFactory.getLogger(AthenaException.class);

	private Object[] args;
	private String code;

	@Override
	public Object[] getArgs() {
		return args;
	}

	public String getCode() {
		return code;
	}

	public AthenaException() {
		super(ErrorConstant.SYSTEM_ERROR_UNDEFINED);
	}

	public AthenaException(Throwable throwable) {
		super(ErrorConstant.SYSTEM_ERROR_UNDEFINED, throwable);
	}

	public AthenaException(@Nullable String message) {
		super(message);
	}

	public AthenaException(@Nullable String message, @Nullable Throwable cause) {
		super(message, cause);
	}

	public AthenaException(@Nullable String message, @Nullable String code) {
		super(message);
		this.code = code;
	}

	public AthenaException(@Nullable String message, @Nullable Object[] args) {
		this(message);
		this.args = args;
	}

	public AthenaException(@Nullable String message, @Nullable Object[] args, @Nullable Throwable cause) {
		super(message, cause);
		this.args = args;
	}

	public AthenaException(@Nullable String message, @Nullable String code, @Nullable Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public AthenaException(@Nullable AthenaExceptionEnum athenaExceptionEnum) {
		super(athenaExceptionEnum.getMessage());
		this.code = athenaExceptionEnum.getCode();
	}

	public AthenaException(@Nullable AthenaExceptionEnum athenaExceptionEnum, @Nullable Object[] args) {
		super(athenaExceptionEnum.getMessage());
		this.code = athenaExceptionEnum.getCode();
		this.args = args;
	}

	public AthenaException(@Nullable AthenaExceptionEnum athenaExceptionEnum, @Nullable Throwable cause) {
		super(athenaExceptionEnum.getMessage(), cause);
		this.code = athenaExceptionEnum.getCode();
	}

	public AthenaException(@Nullable AthenaExceptionEnum athenaExceptionEnum, @Nullable Object[] args, @Nullable Throwable cause) {
		super(athenaExceptionEnum.getMessage(), cause);
		this.code = athenaExceptionEnum.getCode();
		this.args = args;
	}
}
