package com.xiongyayun.athena.core.response;

import com.fasterxml.jackson.annotation.*;
import com.xiongyayun.athena.core.i18n.I18nService;
import com.xiongyayun.athena.core.utils.SpringContextUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回体
 *
 * @author 熊亚运
 * @date 2019-05-21
 */
@ApiModel("返回数据")
@Accessors(chain = true)
@JsonPropertyOrder({"rtnCode", "rtnMsg", "rtnData"})
public class ResBody<T> implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private I18nService i18nService;
    private static final String SUCCESS_CODE = "000000";
    private static final String SUCCESS_MSG = "SUCCESS";

    /**
     * 返回码
     */
	@ApiModelProperty("返回码")
    @Setter
    @Getter
    private String rtnCode;

    /**
     * 返回信息
     */
	@ApiModelProperty("返回信息")
    @Setter
    private String rtnMsg;

	@ApiModelProperty("返回数据")
    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T rtnData;

    @JsonIgnore
    private Object[] args;

	@Setter
	@JsonIgnore
    private long timestamp;

    public ResBody() {
		this.i18nService = SpringContextUtil.getBean("i18nService");
        this.rtnCode = SUCCESS_CODE;
        this.rtnMsg = SUCCESS_MSG;
    }

    public ResBody(String rtnCode) {
		this.i18nService = SpringContextUtil.getBean(I18nService.class);
        this.rtnCode = rtnCode;
    }

    public ResBody(String rtnCode, String rtnMsg) {
		this.i18nService = SpringContextUtil.getBean(I18nService.class);
        this.rtnCode = rtnCode;
        this.rtnMsg = rtnMsg;
    }

    public ResBody(String rtnCode, Object[] args) {
		this.i18nService = SpringContextUtil.getBean(I18nService.class);
        this.rtnCode = rtnCode;
        this.args = args;
    }

    public String getRtnMsg() {
//        if (this.rtnMsg == null && i18nService != null) {
//            this.rtnMsg = i18nService.get(rtnCode, args);
//        }
        return this.rtnMsg;
    }

    public long getTimestamp() {
    	return System.currentTimeMillis();
	}

    public ResBody<T> withCode(String rtnCode) {
        this.rtnCode = rtnCode;
        return this;
    }

    public ResBody<T> withMsg(String msg) {
        this.rtnMsg = msg;
        return this;
    }

    public ResBody<T> withArgs(Object[] args) {
        this.args = args;
        return this;
    }

	public ResBody<T> withData(T data) {
		this.rtnData = data;
		return this;
	}

}
