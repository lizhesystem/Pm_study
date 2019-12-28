package com.ruoyi.project.pro.controller;

import java.util.List;

import com.ruoyi.project.pro.domain.ProProinfo;
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
 * @author ruoyi
 * @date 2019-12-28
 */
@RestController
@RequestMapping("/system/proinfo")
public class ProProinfoController extends BaseController {
    @Autowired
    private IProProinfoService proProinfoService;

    /**
     * 查询项目列列表
     */
    @PreAuthorize("@ss.hasPermi('system:proinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProProinfo proProinfo) {
        startPage();
        List<ProProinfo> list = proProinfoService.selectProProinfoList(proProinfo);
        return getDataTable(list);
    }

    /**
     * 导出项目列列表
     */
    @PreAuthorize("@ss.hasPermi('system:proinfo:export')")
    @Log(title = "项目列", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ProProinfo proProinfo) {
        List<ProProinfo> list = proProinfoService.selectProProinfoList(proProinfo);
        ExcelUtil<ProProinfo> util = new ExcelUtil<ProProinfo>(ProProinfo.class);
        return util.exportExcel(list, "proinfo");
    }

    /**
     * 获取项目列详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:proinfo:query')")
    @GetMapping(value = "/{proId}")
    public AjaxResult getInfo(@PathVariable("proId") Long proId) {
        return AjaxResult.success(proProinfoService.selectProProinfoById(proId));
    }

    /**
     * 新增项目列
     */
    @PreAuthorize("@ss.hasPermi('system:proinfo:add')")
    @Log(title = "项目列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProProinfo proProinfo) {
        return toAjax(proProinfoService.insertProProinfo(proProinfo));
    }

    /**
     * 修改项目列
     */
    @PreAuthorize("@ss.hasPermi('system:proinfo:edit')")
    @Log(title = "项目列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProProinfo proProinfo) {
        return toAjax(proProinfoService.updateProProinfo(proProinfo));
    }

    /**
     * 删除项目列
     */
    @PreAuthorize("@ss.hasPermi('system:proinfo:remove')")
    @Log(title = "项目列", businessType = BusinessType.DELETE)
    @DeleteMapping("/{proIds}")
    public AjaxResult remove(@PathVariable Long[] proIds) {
        return toAjax(proProinfoService.deleteProProinfoByIds(proIds));
    }
}
