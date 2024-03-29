package com.xiongyayun.athena.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.Claim;
import com.xiongyayun.athena.components.common.exception.AthenaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * JWT帮助类
 *
 * @author 熊亚运
 * @date 2019-06-17
 */
public class JwtUtil {
	private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
	/**
	 * 秘钥
	 * csbt34.ydhl12s（池上碧苔三四点，叶底黄鹂一两声）
	 * for_$n(@RenSheng)_$n+="die"（人生自古谁无死）
	 * ppnn13%dkstFeb.1st。中文解析：娉娉袅袅十三余，豆蔻梢头二月初。
	 */
    private static final String SECRET = "csbt34.ydhl12s for_$n(@RenSheng)_$n+=\"die\" ppnn13%dkstFeb.1st。>?N<:{LWPW";
    private static final long EXP = 60 * 60 * 1000;
    private static final String ISSUER = "athena";

    /**
     * 生产指定时间token
     *
     * @param data 数据
     * @param exp   过期时间 单位毫秒
     * @return
     */
    public static String signature(Map<String, Object> data, long exp) {
        try {
            Map<String, Object> headerClaims = new HashMap<>(2);
            headerClaims.put(PublicClaims.ALGORITHM, "HS256");
            headerClaims.put(PublicClaims.TYPE, "JWT");
            JWTCreator.Builder builder = JWT
                    .create()
                    .withHeader(headerClaims)
                    // 该JWT的签发者
                    .withIssuer(ISSUER)
                    // 在什么时候签发的(UNIX时间)
                    .withIssuedAt(new Date())
                    // 什么时候过期，这里是一个Unix时间戳
                    .withExpiresAt(new Date(exp))
                    ;
            if (data != null) {
                Set<String> keys = data.keySet();
                for (String key : keys) {
                    Object value = data.get(key);
                    if (value == null) {
                    	continue;
					} else if (value instanceof String) {
                        builder.withClaim(key, value.toString());
                    } else if (value instanceof Long) {
                        builder.withClaim(key, Long.parseLong(value.toString()) + "");
                    } else if (value instanceof Integer) {
                        builder.withClaim(key, (Integer) value);
                    } else if (value instanceof Double) {
                        builder.withClaim(key, (Double) value);
                    } else if (value instanceof Date) {
                        builder.withClaim(key, (Date) value);
                    } else if (value instanceof Boolean) {
                        builder.withClaim(key, (Boolean) value);
                    } else if (value instanceof String[]) {
                        builder.withArrayClaim(key, (String[]) value);
                    } else if (value instanceof Integer[]) {
                        builder.withArrayClaim(key, (Integer[]) value);
                    } else {
                        throw new RuntimeException("nonsupport: " + value + " > " + value.getClass());
                    }
                }
            }
            return builder.sign(HS256());
        } catch (JWTCreationException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String signature(Map<String, Object> data) {
        return signature(data, System.currentTimeMillis() + EXP);
    }

    public static String signature(Map<String, Object> data, String token) {
        return signature(data, System.currentTimeMillis() + EXP);
    }

    private static Algorithm HS256() {
        Algorithm algorithm = null;
        try {// UTF-8 encoding not supported
            algorithm = Algorithm.HMAC256(generateSecret());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return algorithm;
    }

//    private static Algorithm RS256() {
//        // Get the key instance
//        RSAPublicKey publicKey = null;
//        // Get the key instance
//        RSAPrivateKey privateKey = null;
//        Algorithm algorithm = null;
//        try {// UTF-8 encoding not supported
//            algorithm = Algorithm.RSA256(privateKey);
//            // algorithm = Algorithm.RSA256(publicKey, privateKey);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        }
//        return algorithm;
//    }

    /**
     * 获取有效数据
     * @param token
     * @return
     * @throws AthenaException
     */
    public static Map<String, Claim> verify(String token) throws AthenaException {
        try {
            if (token == null || "".equals(token.trim())) {
				throw new AthenaException("令牌不能为空");
            }
            JWTVerifier verifier = JWT.require(HS256()).withIssuer(ISSUER).build();
            return verifier.verify(token).getClaims();
        } catch (Exception e) {
            log.error("令牌验证失败！ -->" + token, e);
            if (e instanceof InvalidClaimException) {
                throw new AthenaException("令牌数据异常", e);
            } else if (e instanceof JWTVerificationException) {
                throw new AthenaException("令牌验证未通过", e);
            } else if (e instanceof TokenExpiredException) {
                throw new AthenaException("令牌已过期", e);
            }
            throw new AthenaException(e.getMessage(), e);
        }
    }

    /**
     * 是否过期
     * @param token
     * @return
     */
    public static boolean isExpiresAt(String token) {
        try {
            if (token == null || "".equals(token.trim())) {
                return true;
            }
            JWTVerifier verifier = JWT.require(HS256()).withIssuer(ISSUER).build();
            Date date = verifier.verify(token).getExpiresAt();
			return date.after(new Date());
		} catch (Exception e) {
            return true;
        }
    }

    /**
     * 是否即将过期（5分钟后过期）
     * @param token
     * @return
     * @throws AthenaException
     */
    public static boolean willExpire(String token) throws AthenaException {
        try {
            if (token == null || "".equals(token.trim())) {
                return true;
            }
            JWTVerifier verifier = JWT.require(HS256()).withIssuer(ISSUER).build();
            Date date = verifier.verify(token).getExpiresAt();
			return date.getTime() > SafeDate.rollTime(new Date(), 0, 5, 0).getTime();
		} catch (Exception e) {
            return true;
        }
    }

    private static String generateSecret() {
        return SECRET;
    }


    public static void main(String[] args) throws Throwable {
//		Map<String, Object> datas = new HashMap<String, Object>();
//		datas.put("mobilePhone", "1288880029");//手机号码
//		datas.put("userName", "熊亚运");//用户名
//		datas.put("inviteCode", "22dt");//推荐码
//		datas.put("idCertificFlag", "N");//实名认证标志
//		datas.put("financialCertificFlag", "N");//理财师认证标志
//		String token = signature(datas, null);
//		System.out.println(token);
//		System.out.println(JWT.decode(token).getExpiresAt());
//		Map<String, Claim> tokenMap = JwtUtil.verify(token);
//		for(String key : tokenMap.keySet()) {
//			System.out.println("key:"+key +",value:"+tokenMap.get(key).asString());
//		}
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcGVuaWQiOiJvWUxHOTR2NndDblJCeXNFQ3UtMlViam9yNG5BIiwiaXNzIjoicHJvTGluayIsImV4cCI6NDMyMDAsImlhdCI6MTU1NjI0OTI2OX0.9MaGzyq5rN9dP8KF6oq6R-qsdjktUln1ln-HV5OGTeE";
//        verify(token);
        System.out.println(willExpire(token));
    }
}
