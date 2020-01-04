package com.ruoyi.project.pro.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.project.common.CommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.pro.domain.ProWorklog;
import com.ruoyi.project.pro.service.IProWorklogService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 日报Controller
 *
 * @author lizhe
 * @date 2020-01-03
 */
@RestController
@RequestMapping("/pro/worklog")
public class ProWorklogController extends BaseController {
    @Autowired
    private IProWorklogService proWorklogService;

    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    /**
     * 查询日报列表
     */
    @PreAuthorize("@ss.hasPermi('pro:worklog:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProWorklog proWorklog) {
        startPage();
        List<ProWorklog> list = proWorklogService.selectProWorklogList(proWorklog);
        return getDataTable(list);
    }

    /**
     * 导出日报列表
     */
    @PreAuthorize("@ss.hasPermi('pro:worklog:export')")
    @Log(title = "日报", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ProWorklog proWorklog) {
        List<ProWorklog> list = proWorklogService.selectProWorklogList(proWorklog);
        ExcelUtil<ProWorklog> util = new ExcelUtil<ProWorklog>(ProWorklog.class);
        return util.exportExcel(list, "worklog");
    }

    /**
     * 获取日报详细信息
     */
    @PreAuthorize("@ss.hasPermi('pro:worklog:query')")
    @GetMapping(value = "/{worklogId}")
    public AjaxResult getInfo(@PathVariable("worklogId") Long worklogId) {
        return AjaxResult.success(proWorklogService.selectProWorklogById(worklogId));
    }

    /**
     * 新增日报
     */
    @PreAuthorize("@ss.hasPermi('pro:worklog:add')")
    @Log(title = "日报", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProWorklog proWorklog) {
        // 設置创建时间还有创建人
        proWorklog.setCreateTime(DateUtils.getNowDate());
        proWorklog.setCreateBy(SecurityUtils.getUsername());
        return toAjax(proWorklogService.insertProWorklog(proWorklog));
    }

    /**
     * 修改日报
     */
    @PreAuthorize("@ss.hasPermi('pro:worklog:edit')")
    @Log(title = "日报", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProWorklog proWorklog) {
        return toAjax(proWorklogService.updateProWorklog(proWorklog));
    }

    /**
     * 删除日报
     */
    @PreAuthorize("@ss.hasPermi('pro:worklog:remove')")
    @Log(title = "日报", businessType = BusinessType.DELETE)
    @DeleteMapping("/{worklogIds}")
    public AjaxResult remove(@PathVariable Long[] worklogIds) {
        return toAjax(proWorklogService.deleteProWorklogByIds(worklogIds));
    }

    /**
     * 文件
     */
    //@Log(title = "上传日报", businessType = BusinessType.UPDATE)
    //@PostMapping("/workFile")
    //public AjaxResult avatar(MultipartFile file) throws IOException {
    //    if (!file.isEmpty()) {
    //        String url = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
    //        AjaxResult ajax = AjaxResult.success();
    //        ajax.put("fileUrl", url);
    //        return ajax;
    //    }
    //    return AjaxResult.error("上传图片异常，请联系管理员");
    //}
    @Log(title = "下载日报", businessType = BusinessType.EXPORT)
    @GetMapping("/downFile")
    public void download(HttpServletResponse response, String fileurl) {
        try {
            String profile = RuoYiConfig.getProfile();

            File file = new File(profile + fileurl.substring(8));
            // 如果文件存在
            if (file.exists()) {
                String fileName = fileurl.substring(fileurl.lastIndexOf("/") + 1);

                //设置相应类型让浏览器知道用什么打开  用application/octet-stream也可以，看是什么浏览器
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("utf-8");
                //设置头信息
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                InputStream inputStream = new FileInputStream(file);
                ServletOutputStream ouputStream = response.getOutputStream();
                byte b[] = new byte[1024];
                int n;
                while ((n = inputStream.read(b)) != -1) {
                    ouputStream.write(b, 0, n);
                }
                //关闭流
                ouputStream.close();
                inputStream.close();
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }


        //return AjaxResult.error("下载日报异常，请联系管理员");
    }
}
