//package com.ozomall.process;
//
//import com.alibaba.fastjson.JSONObject;
//import com.aliyun.openservices.ons.api.Message;
//import com.aliyun.openservices.ons.api.Producer;
//import com.aliyun.openservices.ons.api.SendResult;
//import com.sgcc.self.common.utils.DateUtils;
//import com.sgcc.self.dal.jsy.entity.DailyFrozenElectricityInfoPO;
//import com.sgcc.self.dal.jsy.mapper.DailyFrozenElectricityInfoPOMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@EnableScheduling
//@Service
//public class DateSendMq {
//
//    @Autowired
//    private Producer producer;
//
//    @Value("${mq.producer.electric.topic}")
//    private String topic;
//    @Value("${mq.producer.electric.tag}")
//    private String tag;
//    @Value("${mq.push.batch.size}")
//    private Integer size;
//
//    @Resource
//    private DailyFrozenElectricityInfoPOMapper dailyFrozenElectricityInfoPOMapper;
//
//
//    private int nums = 1;
//
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void init() {
//        nums = 1;
//    }
//
//    //    @Scheduled(cron = "0/30 * * * * ? ")
//    @Scheduled(cron = "0/30 * 6,7 * * ?")
//    public void sendMq() {
//
//        log.info("第" + nums + "次查询开始时间----" + DateUtils.format(new Date(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
//        List<DailyFrozenElectricityInfoPO> list = dailyFrozenElectricityInfoPOMapper.querySendDate(size);
//        log.info("第" + nums + "次查询数据量----" + list.size());
//        if (!CollectionUtils.isEmpty(list)) {
//
//            String result = JSONObject.toJSONString(list);
//            Message msg = new Message(topic, tag, result.getBytes());
//
//            try {
//                SendResult sendResult = producer.send(msg);
//                // 同步发送消息，只要不抛异常就是成功
//                if (sendResult != null) {
//                    List<Integer> ids = list.stream().map(obj -> obj.getId()).collect(Collectors.toList());
//                    dailyFrozenElectricityInfoPOMapper.updateFlag(ids);
//                    log.info("第" + nums + "次推送MQ的msgId is:" + sendResult.getMessageId());
//                    log.info("第" + nums + "次查询推送结束时间----" + DateUtils.format(new Date(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
//                    nums++;
//                }
//            } catch (Exception e) {
//                log.error("第" + nums + "次推送MQ的异常信息:" + e.getMessage());
//            }
//        }
//    }
//
//
////    public static void main(String[] args) {
////        for (int i = 2001; i <= 5000; i++) {
////            System.out.println("INSERT INTO \"SCOTT\".\"SOURCE_ELECTRICITY_INFO\" VALUES ("+i+", '33101', '浙江', '33401', '国网浙江省电力公司杭州供电公司', '123456', '20200720', '123456', '100', TO_DATE('2020-07-20 16:39:00', 'YYYY-MM-DD HH24:MI:SS'));");
////        }
////    }
//}
