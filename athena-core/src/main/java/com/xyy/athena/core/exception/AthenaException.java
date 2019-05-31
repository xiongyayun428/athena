package com.xyy.athena.core.exception;

import com.xyy.athena.core.ErrorConstant;
import com.xyy.athena.core.i18n.I18nService;
import com.xyy.athena.core.utils.SpringContextUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.NestedCheckedException;
import org.springframework.lang.Nullable;

/**
 * AthenaException
 *
 * @author: 熊亚运
 * @date: 2019-05-21
 */
public class AthenaException extends NestedCheckedException {
    private static final long serialVersionUID = -5547644358612306631L;
    @Setter
    @Getter
    private String messageKey;
    @Setter
    @Getter
    private Object[] args = new Object[0];
    
    public AthenaException() {
        super("");
        this.messageKey = ErrorConstant.SYSTEM_ERROR_UNDEFINED;
    }

    public AthenaException(Throwable throwable) {
        super("", throwable);
        this.messageKey = ErrorConstant.SYSTEM_ERROR_UNDEFINED;
    }

    public AthenaException(@Nullable String messageKey) {
        super(messageKey);
        this.messageKey = messageKey;
    }

    public AthenaException(@Nullable String messageKey, @Nullable Object[] args) {
        this(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

    public AthenaException(@Nullable String messageKey, @Nullable Throwable throwable) {
        super(messageKey, throwable);
        this.messageKey = messageKey;
    }

    public AthenaException(@Nullable String messageKey, @Nullable Object[] args, @Nullable Throwable throwable) {
        super(messageKey, throwable);
        this.messageKey = messageKey;
        this.args = args;
    }


    @Override
    public String getMessage() {
        try {
            I18nService i18nService = SpringContextUtil.getBean(I18nService.class);
            if (i18nService != null) {
                return i18nService.get(this.messageKey, this.args, ErrorConstant.SYSTEM_ERROR_UNDEFINED);
            }
        } catch (Exception e) {}
        return super.getMessage();
    }

//    @Override
//    public String toString() {
//        StringBuffer sb = new StringBuffer();
//        try {
//            I18nService i18nService = SpringContextUtil.getBean(I18nService.class);
//            if (i18nService != null) {
//                sb.append("{messageKey: ");
//                sb.append(this.messageKey);
//                sb.append(", message: ");
//                sb.append(i18nService.get(this.messageKey, this.args, ErrorConstant.SYSTEM_ERROR_UNDEFINED));
//                sb.append("}; ");
//            }
//        } catch (Exception e) {}
//        sb.append(super.toString());
//        return sb.toString();
//    }
}
