package net.lab1024.sa.admin.module.i18n.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.i18n.domain.entity.I18nInfoEntity;
import net.lab1024.sa.admin.module.i18n.req.I18nInfoPageReqVO;
import net.lab1024.sa.admin.module.i18n.resp.I18nInfoRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 国际化表
 *
 * @author Link
 * @since 2025-07-16 16:57:43
 */
@Mapper
public interface I18nInfoMapper extends BaseMapper<I18nInfoEntity> {

    /**
     * 国际化列表
     *
     * @author Link
     * @since 2025/7/17 8:40
     * @param page page
     * @param vo vo
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.nssoftware.warehouse.vo.req.I18nInfoReqVO>
     */
    List<I18nInfoRespVO> listByPage(Page page, @Param("i18n") I18nInfoPageReqVO vo);
}
