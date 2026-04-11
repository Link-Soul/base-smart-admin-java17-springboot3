package net.lab1024.sa.admin.module.orderinfo.dao;

import java.util.List;
import net.lab1024.sa.admin.module.orderinfo.domain.entity.OrderInfoEntity;
import net.lab1024.sa.admin.module.orderinfo.domain.form.OrderInfoQueryForm;
import net.lab1024.sa.admin.module.orderinfo.domain.vo.OrderInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 测试用订单表 Dao
 *
 * @Author Link
 * @Date 2026-04-09 14:30:50
 * @Copyright  
 */

@Mapper
public interface OrderInfoDao extends BaseMapper<OrderInfoEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<OrderInfoVO> queryPage(Page page, @Param("queryForm") OrderInfoQueryForm queryForm);

}
