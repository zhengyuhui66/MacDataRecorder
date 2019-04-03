package com.hik.base.util

import org.apache.commons.configuration.XMLConfiguration

class ConfigUtil(path: String) extends Serializable {
  val conf = new XMLConfiguration(path)

  def getConfigSetting(key: String, default: String): String ={
    if(conf != null)
      conf.getString(key)
    else
      default
  }

  var confPath:String=""
  def setConfPath(path:String): Unit ={
    confPath=path;
  }

  def getConfPath():String={
    confPath
  }

  /*
  spark应用名称
   */
  val appName: String = getConfigSetting("appName", "BaseMacRecoder")

  /*
  每隔多少时间从kafka获取数据，单位秒
   */
  val duration: Long = getConfigSetting("duration", "10000").toLong

  /*
  kafka集群地址
   */
  val brokers: String = getConfigSetting("brokers", "")

  /*
  kafka消费团体
   */
  val group: String = getConfigSetting("group", "basemacrecoder_group")

  /*
  kafka订阅主题
   */
  val topic: String = getConfigSetting("topic", "test123")

  /**
    *kafka消费偏移量方式
   */
  val offset: String = getConfigSetting("offset", "latest")

  /**
    *mac原始记录表
   */
  val recoderTable: String = getConfigSetting("recoderTable", "")
  /**
    *
    */
    val recoderMacInx:String=getConfigSetting("recoderMacInxTable","")
  /**
    *
    */
    val recoderDateInx:String=getConfigSetting("recoderDateInxTable","")
  /**
    * 去重表，信息表
    */
  val recoderDuplicate:String=getConfigSetting("recoderDuplicate","")
  /**
  *redis host
   */
  val redisHost: String = getConfigSetting("redisHost", "")

  /*
  redis port
   */
  val redisPort: Int = getConfigSetting("redisPort", "").toInt

  /*
  redis timeout
   */
  val redisTimeout: Int = getConfigSetting("redisTimeout", "").toInt

  /*
  kafka redis key
   */
  val kafkaOffsetKey: String = getConfigSetting("kafkaOffsetKey", "")


}
