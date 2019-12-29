package com.ruoyi.project.pro.controller;

import java.util.List;

import com.ruoyi.project.pro.domain.ProInfo;
import com.ruoyi.project.pro.service.IProProinfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 项目列Controller
 *
 * @author lizhe
 * @date 2019-12-28
 */
@RestController
@RequestMapping("/pro/proinfo")
public class ProProInfoController extends BaseController {

    @Autowired
    private IProProinfoService proProinfoService;

    /**
     * 查询项目列列表
     */
    @PreAuthorize("@ss.hasPermi('pro:proinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProInfo proInfo) {
        startPage();
        List<ProInfo> list = proProinfoService.selectProProinfoList(proInfo);
        return getDataTable(list);
    }

    /**
     * 导出项目列列表
     */
    @PreAuthorize("@ss.hasPermi('pro:proinfo:export')")
    @Log(title = "项目列", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ProInfo proInfo) {
        List<ProInfo> list = proProinfoService.selectProProinfoList(proInfo);
        ExcelUtil<ProInfo> util = new ExcelUtil<ProInfo>(ProInfo.class);
        return util.exportExcel(list, "proinfo");
    }

    /**
     * 获取项目列详细信息
     */
    @PreAuthorize("@ss.hasPermi('pro:proinfo:query')")
    @GetMapping(value = "/{proId}")
    public AjaxResult getInfo(@PathVariable("proId") Long proId) {
        return AjaxResult.success(proProinfoService.selectProProinfoById(proId));
    }

    /**
     * 新增项目列
     */
    @PreAuthorize("@ss.hasPermi('pro:proinfo:add')")
    @Log(title = "项目列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProInfo proInfo) {
        return toAjax(proProinfoService.insertProProinfo(proInfo));
    }

    /**
     * 修改项目列
     */
    @PreAuthorize("@ss.hasPermi('pro:proinfo:edit')")
    @Log(title = "项目列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProInfo proInfo) {
        return toAjax(proProinfoService.updateProProinfo(proInfo));
    }

    /**
     * 删除项目列
     */
    @PreAuthorize("@ss.hasPermi('pro:proinfo:remove')")
    @Log(title = "项目列", businessType = BusinessType.DELETE)
    @DeleteMapping("/{proIds}")
    public AjaxResult remove(@PathVariable Long[] proIds) {
        return toAjax(proProinfoService.deleteProProinfoByIds(proIds));
    }
}
