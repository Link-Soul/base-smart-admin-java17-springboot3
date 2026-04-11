package net.lab1024.sa.admin.module.orderproductinfo.service;

import java.util.List;
import net.lab1024.sa.admin.module.orderproductinfo.dao.OrderProductInfoDao;
import net.lab1024.sa.admin.module.orderproductinfo.domain.entity.OrderProductInfoEntity;
import net.lab1024.sa.admin.module.orderproductinfo.domain.form.OrderProductInfoAddForm;
import net.lab1024.sa.admin.module.orderproductinfo.domain.form.OrderProductInfoQueryForm;
import net.lab1024.sa.admin.module.orderproductinfo.domain.form.OrderProductInfoUpdateForm;
import net.lab1024.sa.admin.module.orderproductinfo.domain.vo.OrderProductInfoVO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 订单商品 Service
 *
 * @Author Link
 * @Date 2026-04-10 08:42:51
 * @Copyright  
 */

@Service
public class OrderProductInfoService {

    @Resource
    private OrderProductInfoDao orderProductInfoDao;

    /**
     * 分页查询
     */
    public PageResult<OrderProductInfoVO> queryPage(OrderProductInfoQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<OrderProductInfoVO> list = orderProductInfoDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(OrderProductInfoAddForm addForm) {
        OrderProductInfoEntity orderProductInfoEntity = SmartBeanUtil.copy(addForm, OrderProductInfoEntity.class);
        orderProductInfoDao.insert(orderProductInfoEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(OrderProductInfoUpdateForm updateForm) {
        OrderProductInfoEntity orderProductInfoEntity = SmartBeanUtil.copy(updateForm, OrderProductInfoEntity.class);
        orderProductInfoDao.updateById(orderProductInfoEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }

        orderProductInfoDao.deleteBatchIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long id) {
        if (null == id){
            return ResponseDTO.ok();
        }

        orderProductInfoDao.deleteById(id);
        return ResponseDTO.ok();
    }
}
