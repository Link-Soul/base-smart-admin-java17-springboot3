package net.lab1024.sa.admin.module.orderproductinfo.controller;

import net.lab1024.sa.admin.module.orderproductinfo.domain.form.OrderProductInfoAddForm;
import net.lab1024.sa.admin.module.orderproductinfo.domain.form.OrderProductInfoQueryForm;
import net.lab1024.sa.admin.module.orderproductinfo.domain.form.OrderProductInfoUpdateForm;
import net.lab1024.sa.admin.module.orderproductinfo.domain.vo.OrderProductInfoVO;
import net.lab1024.sa.admin.module.orderproductinfo.service.OrderProductInfoService;
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
 * 订单商品 Controller
 *
 * @Author Link
 * @Date 2026-04-10 08:42:51
 * @Copyright  
 */

@RestController
@Tag(name = "订单商品")
public class OrderProductInfoController {

    @Resource
    private OrderProductInfoService orderProductInfoService;

    @Operation(summary = "分页查询 @author Link")
    @PostMapping("/orderProductInfo/queryPage")
    @SaCheckPermission("orderProductInfo:query")
    public ResponseDTO<PageResult<OrderProductInfoVO>> queryPage(@RequestBody @Valid OrderProductInfoQueryForm queryForm) {
        return ResponseDTO.ok(orderProductInfoService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author Link")
    @PostMapping("/orderProductInfo/add")
    @SaCheckPermission("orderProductInfo:add")
    public ResponseDTO<String> add(@RequestBody @Valid OrderProductInfoAddForm addForm) {
        return orderProductInfoService.add(addForm);
    }

    @Operation(summary = "更新 @author Link")
    @PostMapping("/orderProductInfo/update")
    @SaCheckPermission("orderProductInfo:update")
    public ResponseDTO<String> update(@RequestBody @Valid OrderProductInfoUpdateForm updateForm) {
        return orderProductInfoService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author Link")
    @PostMapping("/orderProductInfo/batchDelete")
    @SaCheckPermission("orderProductInfo:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return orderProductInfoService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author Link")
    @GetMapping("/orderProductInfo/delete/{id}")
    @SaCheckPermission("orderProductInfo:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long id) {
        return orderProductInfoService.delete(id);
    }
}
