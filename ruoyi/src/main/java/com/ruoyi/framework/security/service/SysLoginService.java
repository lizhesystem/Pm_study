package com.ruoyi.framework.security.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.redis.RedisCache;
import com.ruoyi.framework.security.LoginUser;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param captcha  验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        // 接收客户端传过来之前返回的的验证码的uuid
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        // 从redis里拿到4位的验证码
        String captcha = redisCache.getCacheObject(verifyKey);
        // 获取后直接清除掉redis
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            // 如果获取验证码失败,使用单例线程池方式，使用aop封装登录错误日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            // 验证码错误也记录日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaException();
        }
        // 用户验证
            Authentication authentication = null;
        try {
            // 该方法会去调用security/service/UserDetailsServiceImpl.loadUserByUsername 在security里,依然记录日志
            // Spring Security将获取到的用户名和密码封装成一个Authentication接口的实现类，比如常用的UsernamePasswordAuthenticationToken。
            /**
             *用户名和密码被过滤器获取到，封装成Authentication，通常情况下是UsernamePasswordAuthenticationToken这个实现类。
             * AuthenticationManager 身份管理器负责验证这个Authentication。
             * 认证成功后，AuthenticationManager身份管理器返回一个被填充满了信息的（包括上面提到的权限信息，身份信息，细节信息，但密码通常会被移除）Authentication实例。
             * SecurityContextHolder安全上下文容器将第3步填充了信息的Authentication，通过SecurityContextHolder.getContext().setAuthentication(…)方法，设置到其中。
             */
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        //  得到登录用户身份权限的类,这个时候Authentication对象里已经填满了用户信息。
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token令牌，调用一些方法,设置代理获取ip,刷新token的失效时间,覆盖redis,封装到了LoginUser对象。
        return tokenService.createToken(loginUser);
    }
}
