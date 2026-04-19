package net.lab1024.sa.admin.module.i18n.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import net.lab1024.sa.admin.constant.I18nRedisKeyConst;
import net.lab1024.sa.admin.module.i18n.domain.entity.I18nInfoEntity;
import net.lab1024.sa.admin.module.i18n.domain.enums.I18nMsgTypeEnum;
import net.lab1024.sa.admin.module.i18n.dto.I18nImportDTO;
import net.lab1024.sa.admin.module.i18n.req.I18nInfoPageReqVO;
import net.lab1024.sa.admin.module.i18n.req.I18nInfoReqVO;
import net.lab1024.sa.admin.module.i18n.req.I18nInfoUpdateReqVO;
import net.lab1024.sa.admin.module.i18n.resp.I18nInfoRespVO;
import net.lab1024.sa.admin.module.i18n.service.I18nInfoService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartExcelUtil;
import net.lab1024.sa.base.module.support.redis.RedisRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 国际化表
 *
 * @author Link
 * @since 2025-09-10 13:33:27
 */
@RestController
@RequestMapping("/i18nInfo")
@Tag(name = "国际化表")
public class I18nInfoController {
    @Resource
    private I18nInfoService i18nInfoService;
    @Resource
    private RedisRepository redisRepository;



    @Operation(summary = "分页查询 @author Link")
    @PostMapping("/listByPage")
    @SaCheckPermission("i18nInfo:list")
    public ResponseDTO<PageResult<I18nInfoRespVO>> findListByPage(@RequestBody I18nInfoPageReqVO vo) {
        return ResponseDTO.ok(i18nInfoService.listByPage(vo));
    }


    @Operation(summary = "新增 @author Link")
    @PostMapping("/add")
    @SaCheckPermission("i18nInfo:add")
    public ResponseDTO<String> add(@RequestBody @Validated I18nInfoReqVO vo) {
        return i18nInfoService.addI18nInfo(vo);
    }


    @Operation(summary = "删除 @author Link")
    @DeleteMapping("/delete")
    @SaCheckPermission("i18nInfo:delete")
    public ResponseDTO<String> delete(@RequestBody List<String> ids) {
        i18nInfoService.removeByIds(ids);
        redisRepository.delKeysByNamesPrefix(I18nRedisKeyConst.CACHE_NAME_I18N);
        return ResponseDTO.ok();
    }


    @Operation(summary = "更新 @author Link")
    @PutMapping("/{id}")
    @SaCheckPermission("i18nInfo:update")
    public ResponseDTO<String> update(@PathVariable("id") String id, @RequestBody @Validated I18nInfoUpdateReqVO vo) {
        I18nInfoEntity i18nInfo = new I18nInfoEntity();
        BeanUtil.copyProperties(vo, i18nInfo);
        i18nInfo.setId(id);
        i18nInfoService.updateById(i18nInfo);
        redisRepository.delKeysByNamesPrefix(I18nRedisKeyConst.CACHE_NAME_I18N);
        return ResponseDTO.ok();
    }


    @Operation(summary = "i18n信息导入模板下载 @author Link")
    @GetMapping("/downloadI18n")
    @SaCheckPermission("i18nInfo:downloadI18n")
    public void productTemp(HttpServletResponse response) throws IOException {
        SmartExcelUtil.exportExcel(response, "i18n信息导入模板", "国际化信息",I18nImportDTO.class, new ArrayList<>());
    }


    @Operation(summary = "批量导入i18n信息 @author Link")
    @PostMapping("/i18nInfoForImport")
//    @SaCheckPermission("i18nInfo:i18nInfoForImport")
    public ResponseDTO<String> i18nInfoForImport(@RequestParam("file") MultipartFile file) throws Exception {
        return i18nInfoService.addForImport(file);
    }


    @Operation(summary = "查询前端国际化数据 @author Link")
    @PostMapping("list")
    @SaIgnore
    public ResponseDTO<List<I18nInfoEntity>> findList() {
        return ResponseDTO.ok(i18nInfoService.listByType(I18nMsgTypeEnum.FRONT.getCode()));
    }


    @GetMapping("init")
    @Operation(summary = "初始化后端国际化数据 @author Link")
    @SaIgnore
    public ResponseDTO<String> init() {
        i18nInfoService.init();
        return ResponseDTO.ok();
    }

}
