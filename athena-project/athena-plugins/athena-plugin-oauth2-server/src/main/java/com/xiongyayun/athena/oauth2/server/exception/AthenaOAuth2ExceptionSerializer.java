package com.xiongyayun.athena.oauth2.server.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * AthenaOAuth2ExceptionSerializer
 *
 * @author: Yayun.Xiong
 * @date: 2019-07-05
 */
public class AthenaOAuth2ExceptionSerializer extends StdSerializer<AthenaOAuth2Exception> {

	protected AthenaOAuth2ExceptionSerializer() {
		super(AthenaOAuth2Exception.class);
	}

	@Override
	public void serialize(AthenaOAuth2Exception e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("error", String.valueOf(e.getHttpErrorCode()));
		jsonGenerator.writeStringField("message", e.getMessage());
        jsonGenerator.writeStringField("message1", "用户名或密码错误");
		jsonGenerator.writeStringField("path", request.getServletPath());
		jsonGenerator.writeStringField("timestamp", String.valueOf(System.currentTimeMillis()));
		if (e.getAdditionalInformation()!=null) {
			for (Map.Entry<String, String> entry : e.getAdditionalInformation().entrySet()) {
				String key = entry.getKey();
				String add = entry.getValue();
				jsonGenerator.writeStringField(key, add);
			}
		}
		jsonGenerator.writeEndObject();
	}
}
