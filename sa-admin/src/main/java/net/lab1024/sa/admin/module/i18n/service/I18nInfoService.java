package net.lab1024.sa.admin.module.i18n.service;


import cn.hutool.core.util.StrUtil;
import cn.idev.excel.FastExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.constant.I18nRedisKeyConst;
import net.lab1024.sa.admin.module.i18n.domain.entity.I18nInfoEntity;
import net.lab1024.sa.admin.module.i18n.domain.enums.I18nMsgTypeEnum;
import net.lab1024.sa.admin.module.i18n.dto.I18nImportDTO;
import net.lab1024.sa.admin.module.i18n.mapper.I18nInfoMapper;
import net.lab1024.sa.admin.module.i18n.req.I18nInfoPageReqVO;
import net.lab1024.sa.admin.module.i18n.req.I18nInfoReqVO;
import net.lab1024.sa.admin.module.i18n.resp.I18nInfoRespVO;
import net.lab1024.sa.base.common.code.ErrorCode;
import net.lab1024.sa.base.common.code.I18nErrorCode;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.exception.BusinessException;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.module.support.redis.RedisRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service("i18nInfoService")
public class I18nInfoService extends ServiceImpl<I18nInfoMapper, I18nInfoEntity> {

    @Resource
    private I18nInfoMapper i18nInfoMapper;
    @Resource
    private StaticMessageSource staticMessageSource;
    @Resource
    private RedisRepository redisRepository;



    /**
     * 国际化信息列表
     */
    public PageResult<I18nInfoRespVO> listByPage(I18nInfoPageReqVO queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<I18nInfoRespVO> list = baseMapper.listByPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 新增
     */
    public ResponseDTO<String> addI18nInfo(I18nInfoReqVO vo) {
        LambdaQueryWrapper<I18nInfoEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(I18nInfoEntity::getMsgKey, vo.getMsgKey())
                .eq(I18nInfoEntity::getMsgType, vo.getMsgType());
        I18nInfoEntity i18nInfoEntity = getOne(queryWrapper);

        //校验msgKey是否存在
        if (i18nInfoEntity != null) {
            return ResponseDTO.error(I18nErrorCode.I18N_INFO_KEY_EXIST);
        }

        redisRepository.delKeysByNamesPrefix(I18nRedisKeyConst.CACHE_NAME_I18N);

        //新增
        I18nInfoEntity i18nInfo = new I18nInfoEntity();
        SmartBeanUtil.copyProperties(vo, i18nInfo);
        i18nInfoMapper.insert(i18nInfo);
        return ResponseDTO.ok();
    }

    /**
     * 导入
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> addForImport(MultipartFile file){

        List<I18nImportDTO> dataList;
        try {
            dataList = FastExcel.read(file.getInputStream()).head(I18nImportDTO.class)
                    .sheet()
                    .doReadSync();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("数据格式存在问题，无法读取");
        }

        //校验excel数据
//        ExcelImportResult<I18nImportDTO> object = ExcelUtils.verifyExcel(file, I18nImportDTO.class);
        // 获取校验通过的数据
//        List<I18nImportDTO> i18nlist = object.getList();
//        // 如果校验通过数据为0直接返回提示
//        if (i18nlist.size() == 0) {
//            return getUploadMsg(object, "", 0);
//        }
//        UploadResultMsg uploadMsg = getUploadMsg(object, "", 0);
        //获取校验通过的数据
//        List<I18nImportDTO> dataList = object.getList();
        //获取失败数据
//        List<I18nImportDTO> errorVOS = object.getFailList();
        // 将失败数据转换为 I18nImportRespErrorVO，用于导出失败信息使用
//        List<I18nImportRespErrorVO> errorVOList = new ArrayList<>();
//        for (I18nImportDTO vo : errorVOS) {
//            I18nImportRespErrorVO i18nImportRespErrorVO = new I18nImportRespErrorVO();
//            BeanUtil.copyProperties(vo, i18nImportRespErrorVO);
//            i18nImportRespErrorVO.setErrorMsg(vo.getErrorMsg());
//            errorVOList.add(i18nImportRespErrorVO);
//        }
//        uploadMsg.setFailMsgList(errorVOList);

        //循环数据，做插入或更新处理
        List<I18nInfoEntity> updateList = new ArrayList<>();
        List<I18nInfoEntity> insertList = new ArrayList<>();
        for (I18nImportDTO vo : dataList) {
            I18nInfoEntity i18nInfoEntity = new I18nInfoEntity();

            // 查询I18N信息是否存在，存在则更新，不存在则插入
            LambdaQueryWrapper<I18nInfoEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(I18nInfoEntity::getMsgKey, vo.getMsgKey())
                    .eq(I18nInfoEntity::getMsgType, vo.getMsgType());
            I18nInfoEntity i18nInfo = getOne(queryWrapper);
            if (i18nInfo != null) {
                // 只更新 英文、韩文、日文
                i18nInfoEntity.setId(i18nInfo.getId());
                i18nInfoEntity.setMsgEn(vo.getMsgEn());
                i18nInfoEntity.setMsgJp(vo.getMsgJp());
                i18nInfoEntity.setMsgKr(vo.getMsgKr());
                updateList.add(i18nInfoEntity);
            } else {
                // 新增
                SmartBeanUtil.copyProperties(vo, i18nInfoEntity);
                insertList.add(i18nInfoEntity);
            }
        }
        // 批量更新
        updateBatchById(updateList);
        // 批量插入
        saveBatch(insertList);

        redisRepository.delKeysByNamesPrefix(I18nRedisKeyConst.CACHE_NAME_I18N);
        return ResponseDTO.ok();
    }


    @Cacheable(value = I18nRedisKeyConst.CACHE_NAME_I18N, key = "#msgType")
    public List<I18nInfoEntity> listByType(String msgType) {
        LambdaQueryWrapper<I18nInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(I18nInfoEntity::getMsgType, msgType);
        return i18nInfoMapper.selectList(queryWrapper);
    }

    /**
     * 初始化spring 国际化
     * 将国际化信息存入内存，方便后续使用
     *
     * @author FangCheng
     * @since 2024/12/27 16:50
     */
    @PostConstruct
    public void init() {
        // 初始化操作,同一个类调用，不会走缓存，不过也不处理了，这个加缓存也没意义，只有项目启动执行一次.后面刷新时，也需要实时取
        List<I18nInfoEntity> backI18n = listByType(I18nMsgTypeEnum.BACK.getCode());
        for (I18nInfoEntity i18n : backI18n) {
            String msgKey = i18n.getMsgKey();
            if (i18n.getErrorCode() != null) {
                // 如果有错误码，则将错误码写入
                msgKey = String.valueOf(i18n.getErrorCode());
            }
            staticMessageSource.addMessage(msgKey, Locale.CHINESE, i18n.getMsgCn());
            staticMessageSource.addMessage(msgKey, Locale.KOREAN, i18n.getMsgKr());
            if (StrUtil.isNotBlank(i18n.getMsgEn())) {
                // 如果有英文，则将英文写入，暂时不涉及
                staticMessageSource.addMessage(msgKey, Locale.ENGLISH, i18n.getMsgEn());
            }
        }
    }
}