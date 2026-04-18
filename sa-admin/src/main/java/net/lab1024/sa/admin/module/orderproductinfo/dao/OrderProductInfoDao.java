package net.lab1024.sa.admin.module.orderproductinfo.dao;

import java.util.List;
import net.lab1024.sa.admin.module.orderproductinfo.domain.entity.OrderProductInfoEntity;
import net.lab1024.sa.admin.module.orderproductinfo.domain.form.OrderProductInfoQueryForm;
import net.lab1024.sa.admin.module.orderproductinfo.domain.vo.OrderProductInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 订单商品 Dao
 *
 * @Author Link
 * @Date 2026-04-10 08:42:51
 * @Copyright  
 */

@Mapper
public interface OrderProductInfoDao extends BaseMapper<OrderProductInfoEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<OrderProductInfoVO> queryPage(Page page, @Param("queryForm") OrderProductInfoQueryForm queryForm);

}
