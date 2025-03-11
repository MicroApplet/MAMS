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

package io.github.microapplet.mams.user.session;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.microapplet.commons.security.*;
import io.github.microapplet.mams.user.config.JwtConfProperty;
import io.github.microapplet.mams.user.jwt.JwtTokenCache;
import io.github.microapplet.mams.user.model.User;
import io.github.microapplet.mams.user.repository.UserRepository;
import io.github.microapplet.mams.user.res.UserResCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.Duration;
import java.util.*;

/**
 * 当前会话用户
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Scope("request")
@Component(SessionUser.sessionUser)
public class SessionUser implements CurrentRoles, CurrentPermission, Serializable {
    private static final long serialVersionUID = 1405110836520718924L;
    public static final String sessionUser = "innerCurrentSessionUser";
    public static final String SESSION_ID = "sessionId";
    public static final String APPID = "appid";
    public static final String USER_ID = "userid";
    public static final String CHANNEL_CODE = "chlCode";
    public static final String CHANNEL_APP_ID = "chlAppid";
    public static final String CHANNEL_APP_TYPE = "chlAppType";
    public static final String NICKNAME = "nickname";
    public static final String USERNAME = "username";
    public static final String USER_CODE = "userCode";
    public static final String AUTHORIZATION = "authorization";

    @Resource
    @JsonIgnore
    private transient PermissionService permissionService;

    @Resource
    @JsonIgnore
    private transient CurrentUserBean currentUserBean;

    @Resource
    @JsonIgnore
    private transient JwtConfProperty jwtConfProperty;

    @Resource
    @JsonIgnore
    private transient UserRepository userRepository;

    @Resource
    @JsonIgnore
    private transient JwtTokenCache jwtTokenCache;

    @Resource
    @JsonIgnore
    private transient RoleService roleService;

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
    private String chlCode;

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
        fromToken(authorization, jwtConfProperty, jwtTokenCache);
    }

    public User user() {
        String userid = getUserid();
        if (StringUtils.isBlank(userid))
            throw UserResCode.UserNotLogin.bizException();

        return this.userRepository.queryByUserid(userid);
    }

    @Override
    public List<Permission> hasPermission() {
        return this.permissionService.queryPermissionByUserid(this.getUserid());
    }

    @Override
    public List<Role> hasRole() {
        return this.roleService.queryRoleByUserid(this.getUserid());
    }

    public void authorization(String authorization, JwtConfProperty jwtConfProperty, JwtTokenCache jwtTokenCache) {
        String jwtSecret = jwtConfProperty.getSecret();
        Duration timeout = jwtConfProperty.jwtTimeout();

        if (StringUtils.isBlank(authorization)) {
            authorization = UUID.randomUUID().toString().replaceAll("-", StringUtils.EMPTY);
            setAuthorization(authorization);
        }
        String jwtToken = JWT.create()
                .withClaim(APPID, appid)
                .withClaim(SESSION_ID, authorization)
                .withClaim(USER_ID, userid)
                .withClaim(CHANNEL_CODE, chlCode)
                .withClaim(CHANNEL_APP_ID, chlAppid)
                .withClaim(CHANNEL_APP_TYPE, chlAppType)
                .withClaim(NICKNAME, nickname)
                .withClaim(USERNAME, username)
                .withClaim(USER_CODE, userCode)
                .withClaim(AUTHORIZATION, authorization)
                .withIssuedAt(Optional.ofNullable(issueAt).orElseGet(() -> new Date(System.currentTimeMillis())))
                .withExpiresAt(new Date(System.currentTimeMillis() + timeout.toMillis()))
                .withIssuer("MicroBank")
                .sign(Algorithm.HMAC256(jwtSecret));

        jwtTokenCache.set(authorization, jwtToken, timeout);
    }

    private void fromToken(String authorization, JwtConfProperty jwtConfProperty, JwtTokenCache jwtTokenCache) {
        try {
            String token = jwtTokenCache.get(authorization);
            Algorithm algorithm = Algorithm.HMAC256(jwtConfProperty.getSecret());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            setId(body(SESSION_ID, jwt));
            setAppid(body(APPID, jwt));
            setUserid(body(USER_ID, jwt));
            setChlCode(body(CHANNEL_CODE, jwt));
            setChlAppid(body(CHANNEL_APP_ID, jwt));
            setChlAppType(body(CHANNEL_APP_TYPE, jwt));
            setNickname(body(NICKNAME, jwt));
            setUsername(body(USERNAME, jwt));
            setUserCode(body(USER_CODE, jwt));
            authorization(authorization, jwtConfProperty, jwtTokenCache);
            setIssueAt(jwt.getIssuedAt());
        } catch (Throwable t) {
            throw UserResCode.UserNotLogin.bizException();
        }
    }

    private String currentAuthorization() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            if (StringUtils.equalsIgnoreCase(name, HttpHeaders.AUTHORIZATION)) {
                return request.getHeader(name);
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