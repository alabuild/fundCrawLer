package com.jxnu.fundCrawler.business.model.protocol.crontab.req;

import com.jxnu.fundCrawler.business.model.http.HttpPropers;

public class CrontabAnalyseListReq extends HttpPropers {
    private String fundCode;
    private String fundName;

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }


    @Override
    public String toString() {
        return "CrontabAnalyseListReq{" +
                "fundCode='" + fundCode + '\'' +
                ", fundName='" + fundName + '\'' +
                '}';
    }
}
