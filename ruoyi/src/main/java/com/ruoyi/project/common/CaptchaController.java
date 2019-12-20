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
public class CaptchaController
{
    @Autowired
    private RedisCache redisCache;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException
    {
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 生成唯一标识uuid
        String uuid = IdUtils.simpleUUID();
        // captcha_codes: + uuid
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        // 调用工具类放到Redis  默认2分钟
        redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try
        {
            AjaxResult ajax = AjaxResult.success();
            ajax.put("uuid", uuid);
            ajax.put("img", Base64.encode(stream.toByteArray()));
            // 返回生成的uuid和验证码图片
            return ajax;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        finally
        {
            stream.close();
        }
    }
}
