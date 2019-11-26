package com.rabbitmq.dao;


import com.rabbitmq.entity.BrokerMsgLog;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BrokerMsgLogMapper {
    int deleteByPrimaryKey(String msgId);

    int insert(BrokerMsgLog record);

    int insertSelective(BrokerMsgLog record);

    BrokerMsgLog selectByPrimaryKey(String msgId);

    int updateByPrimaryKeySelective(BrokerMsgLog record);

    int updateByPrimaryKey(BrokerMsgLog record);

    void updateOrderStatus(String msgId, String status, Date updateTime);

    List<BrokerMsgLog> getAllByStatusAndOut();

    void retryAddOne(String msgId);
}