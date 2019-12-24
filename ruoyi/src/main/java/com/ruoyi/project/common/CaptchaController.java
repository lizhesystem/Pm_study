package com.ruoyi.project.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.VerifyCodeUtils;
import com.ruoyi.common.utils.sign.Base64;
import com.ruoyi.framework.redis.RedisCache;
import com.ruoyi.framework.web.domain.AjaxResult;

/**
 * 验证码操作处理
 *
 * @author ruoyi
 */
@RestController
public class CaptchaController {
    @Autowired
    private RedisCache redisCache;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException {
        // 生成随机字串(使用common下的utils下的工具类生成随机的4位验证码)
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 生成唯一标识uuid,和上面的工具类都在utils下
        String uuid = IdUtils.simpleUUID();
        // 加上标识，CAPTCHA_CODE_KEY 是自定义的通用常量类里的属性  captcha_codes: + uuid
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        // 调用工具类放到Redis存入redis里,K是verifyKey：v是随机串  默认2分钟 （在frameWork里，属于springRedis框架的工具类）
        redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 生成验证码图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try {
            // AjaxResult是封装的消息提醒的工具类,如果什么参数都不传返回值200提示信息为"操作成功"
            AjaxResult ajax = AjaxResult.success();
            ajax.put("uuid", uuid);
            ajax.put("img", Base64.encode(stream.toByteArray()));
            // 因为是这个工具类继承了HasMap可以在里面任意添加k,v的数据返回
            return ajax;
        } catch (Exception e) {
            e.printStackTrace();
            // 如果生成验证错误直接返回
            return AjaxResult.error(e.getMessage());
        } finally {
            // 无论如何都关闭
            stream.close();
        }
    }
}
