package com.jehko.jpa.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jehko.jpa.user.entity.User;
import com.jehko.jpa.user.model.UserLoginToken;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@UtilityClass
public class JWTUtils {
	private static final String JWT_KEY = "jehkojpa_jwt_secret_key";
	private static final String CLAIM_USER_ID = "user_id";

	public static UserLoginToken createToken(User user) {
		if(user == null) return null;

		// 토큰 만료 일자 계산 - 현재 시간에서 1개월 뒤
		Date expiredDate = Timestamp.valueOf(LocalDateTime.now().plusMonths(1));

		String token = JWT.create()
				.withExpiresAt(expiredDate)
				.withClaim(CLAIM_USER_ID, user.getId())
				.withSubject(user.getUserName())
				.withIssuer(user.getEmail())
				.sign(Algorithm.HMAC512(JWT_KEY.getBytes()));

		return UserLoginToken.builder()
				.token(token)
				.build();
	}

	public static String getIssuer(String token) {
		String issuer =  JWT.require(Algorithm.HMAC512(JWT_KEY.getBytes()))
				.build()
				.verify(token)
				.getIssuer();

		return issuer;
	}
}
