package com.jxnu.fundCrawler.business.store;

import com.jxnu.fundCrawler.business.model.FundRank;
import com.jxnu.fundCrawler.business.model.strategy.StrategyCrontabAnalyze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shoumiao_yao
 * @date 2016-07-01
 */
@Component
public class ApiStore extends BaseStore {
    private Logger logger = LoggerFactory.getLogger(ApiStore.class);

    /**
     * 获取基金排名
     *
     * @return
     */
    public List<FundRank> queryRankList() {
        return template.selectList("api.queryFundRank");
    }

    /**
     * 获取定投分析
     *
     * @return
     */
    public List<StrategyCrontabAnalyze> queryCrontabAnalyzeList() {
        return template.selectList("api.queryCrontabAnalyzeList");
    }
}
