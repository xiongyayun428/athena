package com.xiongyayun.athena.system.vo;

import com.xiongyayun.athena.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DictItemVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictItemVO extends BaseVO {
	private String id;
	private String dictId;
	private String itemLabel;
	private String itemValue;
	private String description;
	private Integer sortOrder;
	private Boolean enabled;
	private String language;
	private Boolean allowUpdate;
	private Boolean allowDelete;
}
