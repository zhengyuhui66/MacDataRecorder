package com.hik.base

import java.util.concurrent.{Callable, Executors}
import com.hik.base.main.MacRunable
import com.hik.base.util.{CETCProtocol, CommFunUtils, ConfigUtil}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext, TaskContext}

/**
  * 说明：
  * 此项目为Mac采集记录
  * 数据分别存储于Redis和Hbase中
  * 在Hbase中存放Mac采集记录表名分别为
  * MacRecoder:原始记录，以采集设备ID为key
  * MacRecoder_dateInx：以时间为key
  * MacRecoder_macInx：以Mac为key
  * MTMACInfo：采集到的Mac列表
  * 在Redis中存放 (注意过期时间)
  * key：Total_MACRecoder  value:mac采集总记录数
  * key：DayTotal_MACRecoderYYYY(年份) value:每年采集总记录数
  * key：DayTotal_MACRecoderYYYYMM(月份) value:每月采集总记录数
  * key：DayTotal_MACRecoderYYYYMMDD(日) value:每日采集总记录数
  * key：devmin_2019_mac_14306073X00037F23F8E3 value:单设备每年采集总记录数
  * key：devmin_201902_mac_14306073X00037F23F8E3 value:单设备每月采集总记录数
  * key：devmin_20190219_mac_14306073X00037F23F8E3 value:单设备每日采集总记录数
  * key：devmin_2019021913_mac_14306073X00037F23F8E3 value:单设备每时采集总记录数
  * key：devmin_201902191324_mac_14306073X00037F23F8E3 value:单设备每分采集总记录数
  */
object App {

  val MACRECORDER_CONF:String="dataAnalysis/macrecoder.xml"
  val HBASE_SITE_CONF:String="hbase/hbase-site.xml"
  val APP_NAME="MacDataRecorder"
  def main(args:Array[String]):Unit= {
    //val path="E:\\BaseDataRecoder\\conf\\"
    val path = args(0)
    val conf = new SparkConf().setAppName(APP_NAME)
    //conf.setMaster("local")
    val sc = new SparkContext(conf)
    val executors = Executors.newCachedThreadPool()
    val ssc = new StreamingContext(sc, Seconds(3))
    val configUtil = new ConfigUtil(path + MACRECORDER_CONF)
    configUtil.setConfPath(path + HBASE_SITE_CONF);
    val g2016Mac:MacRunable= new MacRunable(ssc,configUtil,sc);
    executors.submit(g2016Mac)
  }

}
