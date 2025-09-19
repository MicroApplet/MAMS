/*
 * Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.asialjim.microapplet.mams.user.domain.agg;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.user.infrastructure.config.JwtConfProperty;
import com.asialjim.microapplet.mams.user.infrastructure.repository.JwtTokenRepository;
import com.asialjim.microapplet.mams.user.pojo.UserMain;
import com.asialjim.microapplet.mams.user.res.UserResCode;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 会话用户
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Scope("request")
@RequiredArgsConstructor
@Component(SessionUser.sessionUser)
public class SessionUser implements Serializable {
    private static final long serialVersionUID = 1405110836520718924L;
    public static final String sessionUser = "innerCurrentSessionUser";
    public static final String SESSION_ID = "sessionId";
    public static final String APPID = "appid";
    public static final String USER_ID = "userid";
    public static final String CHL_TYPE = "chlType";
    public static final String CHL_APP_ID = "chlAppid";
    public static final String CHL_APP_TYPE = "chlAppType";
    public static final String NICKNAME = "nickname";
    public static final String USERNAME = "username";
    public static final String USER_CODE = "userCode";
    public static final String AUTHORIZATION = "authorization";

    private final JwtConfProperty jwtConfProperty;
    private final JwtTokenRepository jwtTokenCache;

    /**
     * 会话编号
     */
    private String id;

    /**
     * 用户认证令牌
     */
    private String authorization;

    /**
     * 登录应用编号:Application@getId
     */
    private String appid;

    /**
     * 用户编号
     */
    private String userid;

    /**
     * 登录渠道编号:H5/wechat/Telecom
     */
    private String chlType;

    /**
     * 登录渠道应用编号:H5/wechat-appid/
     */
    private String chlAppid;

    /**
     * 登录渠道应用类型:H5/wechat-app-type
     */
    private String chlAppType;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户编号：username, phone, email
     */
    private String username;

    /**
     * 用户令牌：password, smsToken, code
     */
    private String userCode;

    /**
     * 用户登录时间
     */
    private Date issueAt;

    @PostConstruct
    public void init() {
        String authorization = currentAuthorization();
        fromToken(authorization, null);
    }


    /**
     * 用户登录检查
     *
     * @since 2025/4/10
     */
    public void loginCheck() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = servletRequestAttributes.getRequest();
            verify(request,this.jwtConfProperty,this.jwtTokenCache);
        } catch (Throwable t){
            UserResCode.UserNotLogin.throwBiz();
        }
    }

    public UserMain user(Consumer<UserMain> consumer) {
        String userid = getUserid();
        if (StringUtils.isBlank(userid))
            throw UserResCode.UserNotLogin.bizException();

        UserAggRoot userAgg = App.beanAndThen(UserAggRoot.class, userAgg1 -> userAgg1.getSessionUser().setUserid(userid));
        UserMain user = userAgg.userMainOrThrow(null);
        if (Objects.nonNull(consumer) && Objects.nonNull(user))
            consumer.accept(user);

        return user;
    }


    public void logout() {
        this.jwtTokenCache.delete(this.authorization);
    }

    public String authorization() {
        authorization(null);
        return getAuthorization();
    }

    public void authorization(String authorization) {
        String jwtSecret = jwtConfProperty.getSecret();
        Duration timeout = jwtConfProperty.jwtTimeout();

        if (StringUtils.isBlank(authorization)) {
            authorization = UUID.randomUUID().toString().replaceAll("-", StringUtils.EMPTY);
            if (StringUtils.isBlank(getId()))
                setId(authorization);
            setAuthorization(authorization);
        }
        if (Objects.isNull(issueAt))
            setIssueAt(new Date(System.currentTimeMillis()));
        String jwtToken = JWT.create()
                .withClaim(APPID, appid)
                .withClaim(SESSION_ID, authorization)
                .withClaim(USER_ID, userid)
                .withClaim(CHL_TYPE, chlType)
                .withClaim(CHL_APP_ID, chlAppid)
                .withClaim(CHL_APP_TYPE, chlAppType)
                .withClaim(NICKNAME, nickname)
                .withClaim(USERNAME, username)
                .withClaim(USER_CODE, userCode)
                .withClaim(AUTHORIZATION, authorization)
                .withIssuedAt(issueAt)
                .withExpiresAt(new Date(System.currentTimeMillis() + timeout.toMillis()))
                .withIssuer("MicroBank")
                .sign(Algorithm.HMAC256(jwtSecret));

        jwtTokenCache.set(authorization, jwtToken, timeout);
    }

    private void fromToken(String authorization,
                           @SuppressWarnings("SameParameterValue") Supplier<RuntimeException> runtimeExceptionSupplier) {
        try {
            String token = jwtTokenCache.get(authorization);
            Algorithm algorithm = Algorithm.HMAC256(jwtConfProperty.getSecret());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            setId(body(SESSION_ID, jwt));
            setAppid(body(APPID, jwt));
            setUserid(body(USER_ID, jwt));
            setChlType(body(CHL_TYPE, jwt));
            setChlAppid(body(CHL_APP_ID, jwt));
            setChlAppType(body(CHL_APP_TYPE, jwt));
            setNickname(body(NICKNAME, jwt));
            setUsername(body(USERNAME, jwt));
            setUserCode(body(USER_CODE, jwt));
            setAuthorization(authorization);
            authorization(authorization);
            setIssueAt(jwt.getIssuedAt());
        } catch (Throwable t) {
            if (Objects.nonNull(runtimeExceptionSupplier))
                throw runtimeExceptionSupplier.get();
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public static DecodedJWT verify(HttpServletRequest request, JwtConfProperty jwtConfProperty, JwtTokenRepository jwtTokenRepository) {
        String authorization = AuthorizationHeader(request);
        String token = jwtTokenRepository.get(authorization);
        if (StringUtils.isBlank(token))
            UserResCode.UserNotLogin.throwBiz();
        Algorithm algorithm = Algorithm.HMAC256(jwtConfProperty.getSecret());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    private String currentAuthorization() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return AuthorizationHeader(request);
    }

    public static String AuthorizationHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            if (StringUtils.equalsIgnoreCase(name, HttpHeaders.AUTHORIZATION)) {
                String header = request.getHeader(name);
                return StringUtils.replace(header, "Bearer ", "");
            }
        }
        return StringUtils.EMPTY;
    }

    private String body(String key, DecodedJWT jwt) {
        if (StringUtils.isBlank(key))
            return StringUtils.EMPTY;
        return Optional.ofNullable(jwt).map(item -> item.getClaim(key)).map(Claim::asString).orElse(StringUtils.EMPTY);
    }
}
