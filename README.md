# data-report
数据报表实践作业
 springbatch：
  1.作业调度为每10秒一次，所以在调度时间上用了固定值6月10为例，每10秒汇总一次，如果在生产环境下每天调度则可以将时间固定值改为当前日期了。
  2，在读取数据库中，分为第一次读取：当前日期的所有交易信息：包括最大交易金额，总交易金额
                        第二次读取：当前日期的所有还款信息：还款总金额，还款笔数
                        第三次读取：当前日期的所有还款信息：消费笔数
  3，三次读取数据库互不干扰，所以使用了CompletableFuture异步编排，异步读取数据库，交易明细表中有20万条测试数据。
     测试结果：常规读取平均200ms，异步读取平均70ms。
 springboot：
 1.包含客户信息与交易明细的CRUD。
 2.封装了查询条件对象以及返回数据
 3.更新，保存接受数据使用的RequestBody
 log4j2
 1.配置了log4j2.xml文件
 2.控制台打印warn以上级别日志，当天日志信息汇总到指定文件中，第二天会将前一天日志文件汇总到所属日期文件夹下。
 
