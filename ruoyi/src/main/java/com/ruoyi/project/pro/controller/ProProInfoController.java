package com.ruoyi.project.pro.controller;

import java.util.List;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.pro.domain.ProDict;
import com.ruoyi.project.pro.domain.ProInfo;
import com.ruoyi.project.pro.service.IProProinfoService;
import com.ruoyi.project.system.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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

    @Autowired
    private ISysUserService sysUserService;

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
    @Log(title = "项目列表", businessType = BusinessType.EXPORT)
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
    @Log(title = "项目列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ProInfo proInfo) {
        if (UserConstants.NOT_UNIQUE.equals(proProinfoService.selectProProInfoByNum(proInfo.getProNum()))) {
            return AjaxResult.error("项目编号'" + proInfo.getProNum() + "'已存在,请重新录入。");
        }
        if (!UserConstants.NOT_UNIQUE.equals(sysUserService.checkNickNameUnique(proInfo.getWorkName()))) {
            return AjaxResult.error("所属运维人员'" + proInfo.getWorkName() + "'不存在,请重新录入。");
        }
        if (!UserConstants.NOT_UNIQUE.equals(sysUserService.checkNickNameUnique(proInfo.getInsetallName()))) {
            return AjaxResult.error("所属部署人员'" + proInfo.getWorkName() + "'不存在,请重新录入。");
        }
        if (!UserConstants.NOT_UNIQUE.equals(sysUserService.checkNickNameUnique(proInfo.getTrainName()))) {
            return AjaxResult.error("所属培训人员'" + proInfo.getWorkName() + "'不存在,请重新录入。");
        }
        // 設置创建时间还有创建人
        proInfo.setCreateTime(DateUtils.getNowDate());
        proInfo.setCreateBy(SecurityUtils.getUsername());
        return toAjax(proProinfoService.insertProProinfo(proInfo));
    }

    /**
     * 修改项目列
     */
    @PreAuthorize("@ss.hasPermi('pro:proinfo:edit')")
    @Log(title = "项目列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ProInfo proInfo) {
        if (!UserConstants.NOT_UNIQUE.equals(sysUserService.checkNickNameUnique(proInfo.getWorkName()))) {
            return AjaxResult.error("所属运维人员'" + proInfo.getWorkName() + "'不存在,请重新录入。");
        }
        if (!UserConstants.NOT_UNIQUE.equals(sysUserService.checkNickNameUnique(proInfo.getInsetallName()))) {
            return AjaxResult.error("所属部署人员'" + proInfo.getWorkName() + "'不存在,请重新录入。");
        }
        if (!UserConstants.NOT_UNIQUE.equals(sysUserService.checkNickNameUnique(proInfo.getTrainName()))) {
            return AjaxResult.error("所属培训人员'" + proInfo.getWorkName() + "'不存在,请重新录入。");
        }
        // 设置更新时间还有更新人
        proInfo.setUpdateTime(DateUtils.getNowDate());
        proInfo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(proProinfoService.updateProProinfo(proInfo));
    }

    /**
     * 删除项目列
     */
    @PreAuthorize("@ss.hasPermi('pro:proinfo:remove')")
    @Log(title = "项目列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{proIds}")
    public AjaxResult remove(@PathVariable Long[] proIds) {
        return toAjax(proProinfoService.deleteProProinfoByIds(proIds));
    }


    /**
     * 获取所有项目编号名称
     *
     * @return 结果:所有的项目编号和名称
     */
    @GetMapping("/allList")
    public AjaxResult getAllProList() {
        List<ProDict> proDicts = proProinfoService.selectProNameList();
        return AjaxResult.success(proDicts);
    }


}
