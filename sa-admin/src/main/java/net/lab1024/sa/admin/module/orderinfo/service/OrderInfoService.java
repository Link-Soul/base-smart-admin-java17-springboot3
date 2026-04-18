package net.lab1024.sa.admin.module.orderinfo.service;

import java.util.List;
import net.lab1024.sa.admin.module.orderinfo.dao.OrderInfoDao;
import net.lab1024.sa.admin.module.orderinfo.domain.entity.OrderInfoEntity;
import net.lab1024.sa.admin.module.orderinfo.domain.form.OrderInfoAddForm;
import net.lab1024.sa.admin.module.orderinfo.domain.form.OrderInfoQueryForm;
import net.lab1024.sa.admin.module.orderinfo.domain.form.OrderInfoUpdateForm;
import net.lab1024.sa.admin.module.orderinfo.domain.vo.OrderInfoVO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 测试用订单表 Service
 *
 * @Author Link
 * @Date 2026-04-09 14:30:50
 * @Copyright  
 */

@Service
public class OrderInfoService {

    @Resource
    private OrderInfoDao orderInfoDao;

    /**
     * 分页查询
     */
    public PageResult<OrderInfoVO> queryPage(OrderInfoQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<OrderInfoVO> list = orderInfoDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(OrderInfoAddForm addForm) {
        OrderInfoEntity orderInfoEntity = SmartBeanUtil.copy(addForm, OrderInfoEntity.class);
        orderInfoDao.insert(orderInfoEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(OrderInfoUpdateForm updateForm) {
        OrderInfoEntity orderInfoEntity = SmartBeanUtil.copy(updateForm, OrderInfoEntity.class);
        orderInfoDao.updateById(orderInfoEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }

        orderInfoDao.deleteBatchIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Integer id) {
        if (null == id){
            return ResponseDTO.ok();
        }

        orderInfoDao.deleteById(id);
        return ResponseDTO.ok();
    }
}
