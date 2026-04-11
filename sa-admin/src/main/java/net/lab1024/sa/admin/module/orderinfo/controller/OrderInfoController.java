package net.lab1024.sa.admin.module.orderinfo.controller;

import net.lab1024.sa.admin.module.orderinfo.domain.form.OrderInfoAddForm;
import net.lab1024.sa.admin.module.orderinfo.domain.form.OrderInfoQueryForm;
import net.lab1024.sa.admin.module.orderinfo.domain.form.OrderInfoUpdateForm;
import net.lab1024.sa.admin.module.orderinfo.domain.vo.OrderInfoVO;
import net.lab1024.sa.admin.module.orderinfo.service.OrderInfoService;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.PageResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 * 测试用订单表 Controller
 *
 * @Author Link
 * @Date 2026-04-09 14:30:50
 * @Copyright  
 */

@RestController
@Tag(name = "测试用订单表")
public class OrderInfoController {

    @Resource
    private OrderInfoService orderInfoService;

    @Operation(summary = "分页查询 @author Link")
    @PostMapping("/orderInfo/queryPage")
    @SaCheckPermission("orderInfo:query")
    public ResponseDTO<PageResult<OrderInfoVO>> queryPage(@RequestBody @Valid OrderInfoQueryForm queryForm) {
        return ResponseDTO.ok(orderInfoService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author Link")
    @PostMapping("/orderInfo/add")
    @SaCheckPermission("orderInfo:add")
    public ResponseDTO<String> add(@RequestBody @Valid OrderInfoAddForm addForm) {
        return orderInfoService.add(addForm);
    }

    @Operation(summary = "更新 @author Link")
    @PostMapping("/orderInfo/update")
    @SaCheckPermission("orderInfo:update")
    public ResponseDTO<String> update(@RequestBody @Valid OrderInfoUpdateForm updateForm) {
        return orderInfoService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author Link")
    @PostMapping("/orderInfo/batchDelete")
    @SaCheckPermission("orderInfo:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Integer> idList) {
        return orderInfoService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author Link")
    @GetMapping("/orderInfo/delete/{id}")
    @SaCheckPermission("orderInfo:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Integer id) {
        return orderInfoService.delete(id);
    }
}
