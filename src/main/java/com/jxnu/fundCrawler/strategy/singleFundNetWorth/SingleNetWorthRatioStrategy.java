package com.jxnu.fundCrawler.strategy.singleFundNetWorth;


import com.jxnu.fundCrawler.business.model.FundNetWorth;
import com.jxnu.fundCrawler.business.model.Mail;
import com.jxnu.fundCrawler.business.model.MailFundStatus;
import com.jxnu.fundCrawler.business.store.FundNetWorthStore;
import com.jxnu.fundCrawler.utils.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yaphyao
 * @version 2017/11/10
 * @see com.jxnu.fundCrawler.strategy
 */
@Deprecated
@Component(value = "fundNetWorthMailStrategy")
public class SingleNetWorthRatioStrategy extends BaseSingleNetWorthStrategy {
    @Autowired
    private FundNetWorthStore fundNetWorthStore;
    @Resource(name = "standardDeviationStrategy")
    private BaseSingleNetWorthStrategy singleNetWorthStrategy;

    @PostConstruct
    public void init() {
        super.next = singleNetWorthStrategy;
    }

    @Override
    public void handler(List<FundNetWorth> fundNetWorthList) {
        if (fundNetWorthList.isEmpty()) return;
        String code = fundNetWorthList.get(0).getFundCode();
        //两个月最大净值
        Float maxNetWorth = fundNetWorthStore.queryPeriodMax(code);
        //两个月最小净值
        Float minNetWorth = fundNetWorthStore.queryPeriodMin(code);
        List<Mail> mailList = new ArrayList<Mail>();
        for (FundNetWorth fundNetWorth : fundNetWorthList) {
            Float netWorth = fundNetWorth.getNetWorth();
            if (NumberUtil.maxRatio(netWorth, maxNetWorth)) {
                int counts = fundNetWorthStore.queryMail(code, MailFundStatus.DOWN.getIndex());
                if (counts == 0) {
                    Mail mail = new Mail();
                    mail.setTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    mail.setCode(code);
                    mail.setType(MailFundStatus.DOWN.getIndex());
                    mailList.add(mail);
                }
            }
            if (NumberUtil.minRatio(netWorth, minNetWorth)) {
                int counts = fundNetWorthStore.queryMail(code, MailFundStatus.UP.getIndex());
                if (counts == 0) {
                    Mail mail = new Mail();
                    mail.setTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    mail.setType(MailFundStatus.UP.getIndex());
                    mail.setCode(code);
                    mailList.add(mail);
                }
            }
        }
        if (!mailList.isEmpty()) {
            fundNetWorthStore.insertMail(mailList);
            fundNetWorthStore.insertDayFundAnalyze(mailList);
        }

        if (super.next != null) {
            super.next.handler(fundNetWorthList);
        }
    }

}
