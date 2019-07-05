package com.xiongyayun.athena.oauth2.server.configuration;

import com.xiongyayun.athena.oauth2.server.properties.OAuth2Properties;
import com.xiongyayun.athena.oauth2.server.security.OAuthUser;
import com.xiongyayun.athena.oauth2.server.security.jwt.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;

/**
 * TODO 这里有问题，没有加载
 *
 * @author: Yayun.Xiong
 * @date: 2019-07-05
 */
@Configuration
public class TokenStoreConfiguration {

	@Configuration
	@ConditionalOnProperty(prefix = "athena.security.oauth2", name = "storeType", havingValue = "redis")
	public static class RedisTokenStoreConfiguration {
		@Autowired
		private RedisConnectionFactory redisConnectionFactory;

		@Bean
		public TokenStore tokenStore() {
			return new RedisTokenStore(redisConnectionFactory);
		}
	}

	/**
	 * 默认使用redis
	 */
	@Configuration
	@ConditionalOnProperty(prefix = "athena.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
	public static class JwtTokenStoreConfiguration {
		@Autowired
		private OAuth2Properties oAuth2Properties;

		/**
		 * 使用jwtTokenStore存储token
		 *
		 * @return
		 */
		@Bean
		public TokenStore tokenStore() {
			return new JwtTokenStore(jwtAccessTokenConverter());
		}

		/**
		 * 用于生成jwt
		 * token 转换器，加入对称秘钥，使用自定tokenEnhancer
		 *
		 * @return
		 */
		@Bean
		@ConditionalOnMissingBean(name = "jwtAccessTokenConverter")
		public JwtAccessTokenConverter jwtAccessTokenConverter() {
			JwtAccessTokenConverter accessTokenConverter = new CustomJwtAccessTokenConverter();
			accessTokenConverter.setSigningKey(oAuth2Properties.getJwtSigningKey());//生成签名的key
			return accessTokenConverter;
		}

		/**
		 * 令牌信息拓展
		 * 用于扩展JWT
		 *
		 * @return
		 */
		@Bean
		@ConditionalOnMissingBean(name = "jwtTokenEnhancer")
		public TokenEnhancer jwtTokenEnhancer() {
			return new JwtTokenEnhancer();
		}

		/**
		 * 对JwtAccessTokenConverter 的 enhance进行重写，加入自定义的信息
		 *
		 * @author: Yayun.Xiong
		 * @date: 2019-07-05
		 */
		class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {
			private static final String BEARER_PRIFIX = "bearer ";

			//这个是token增强器，想让jwt token携带额外的信息在这里处理
			@Override
			public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
				if (accessToken instanceof DefaultOAuth2AccessToken) {
					Object principal = authentication.getPrincipal();

					//这个principal是当时登录后存到securiy的东东，一般是用户实体，自己debug一下就知道了
					if (principal instanceof OAuthUser) {
						OAuthUser user = (OAuthUser) principal;
						HashMap<String, Object> map = new HashMap<>();

						//jwt默认已经自带用户名，无需再次加入
						map.put("nick_name", user.getNickName());
						((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
					}
				}
				return super.enhance(accessToken, authentication);
			}


			//主要是资源服务器解析时一定要有bearer这个头才认为是一个oauth请求，但不知道为啥指定jwt后这个头就不见了，特意加上去
			@Override
			protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
				return BEARER_PRIFIX + super.encode(accessToken, authentication);
			}
		}
	}
}
