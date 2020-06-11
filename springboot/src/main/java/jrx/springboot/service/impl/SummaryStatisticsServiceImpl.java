package jrx.springboot.service.impl;

import jrx.springboot.entity.SummaryStatistics;
import jrx.springboot.mapper.SummaryStatisticsMapper;
import jrx.springboot.service.SummaryStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日汇总表 服务实现类
 * </p>
 *
 * @author galigigi
 * @since 2020-06-03
 */
@Service
public class SummaryStatisticsServiceImpl extends ServiceImpl<SummaryStatisticsMapper, SummaryStatistics> implements SummaryStatisticsService {

}
