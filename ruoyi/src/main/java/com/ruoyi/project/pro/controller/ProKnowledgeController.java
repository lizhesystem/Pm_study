package com.ruoyi.project.pro.controller;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.project.pro.domain.ProKnowledge;
import com.ruoyi.project.pro.service.IProKnowledgeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 知识库Controller
 *
 * @author lizhe
 * @date 2020-01-05
 */
@RestController
@RequestMapping("/pro/knowledge")
public class ProKnowledgeController extends BaseController {
    @Autowired
    private IProKnowledgeService proKnowledgeService;

    /**
     * 查询知识库列表
     */
    @PreAuthorize("@ss.hasPermi('pro:knowledge:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProKnowledge proKnowledge) {
        startPage();
        List<ProKnowledge> list = proKnowledgeService.selectProKnowledgeList(proKnowledge);
        return getDataTable(list);
    }

    /**
     * 导出知识库列表
     */
    @PreAuthorize("@ss.hasPermi('pro:knowledge:export')")
    @Log(title = "知识库", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ProKnowledge proKnowledge) {
        List<ProKnowledge> list = proKnowledgeService.selectProKnowledgeList(proKnowledge);
        ExcelUtil<ProKnowledge> util = new ExcelUtil<ProKnowledge>(ProKnowledge.class);
        return util.exportExcel(list, "knowledge");
    }

    /**
     * 获取知识库详细信息
     */
    @PreAuthorize("@ss.hasPermi('pro:knowledge:query')")
    @GetMapping(value = "/{knowledgeId}")
    public AjaxResult getInfo(@PathVariable("knowledgeId") Long knowledgeId) {
        return AjaxResult.success(proKnowledgeService.selectProKnowledgeById(knowledgeId));
    }

    /**
     * 新增知识库
     */
    @PreAuthorize("@ss.hasPermi('pro:knowledge:add')")
    @Log(title = "知识库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProKnowledge proKnowledge) {
        proKnowledge.setCreateTime(DateUtils.getNowDate());
        proKnowledge.setCreateBy(SecurityUtils.getUsername());
        return toAjax(proKnowledgeService.insertProKnowledge(proKnowledge));
    }

    /**
     * 修改知识库
     */
    @PreAuthorize("@ss.hasPermi('pro:knowledge:edit')")
    @Log(title = "知识库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProKnowledge proKnowledge) {
        // 设置更新时间还有更新人
        proKnowledge.setUpdateTime(DateUtils.getNowDate());
        proKnowledge.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(proKnowledgeService.updateProKnowledge(proKnowledge));
    }

    /**
     * 删除知识库
     */
    @PreAuthorize("@ss.hasPermi('pro:knowledge:remove')")
    @Log(title = "知识库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{knowledgeIds}")
    public AjaxResult remove(@PathVariable Long[] knowledgeIds) {
        return toAjax(proKnowledgeService.deleteProKnowledgeByIds(knowledgeIds));
    }
}
