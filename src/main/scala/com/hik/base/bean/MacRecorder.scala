package com.hik.base.bean

import java.text.SimpleDateFormat

import com.hik.base.util.{CETCProtocol, CommFunUtils}

class MacRecorder extends java.io.Serializable{
  /**
    * 终端mac
    */
  private var mac = ""
  def setMac_= ( _mac : String ){
    mac =  _mac;
  }

  def getMac:String=mac
  /**
    * 终端品牌
    */
  private var bd = ""

  /**
    * 历史ssid
    */
  private var cssid = ""

  /**
    * 进入时间（采集时间）
    */
  private var ct = "";
  def setCt(time:String): Unit ={
    ct=time;
  }
  def getCt:String =ct

  /**
    * 离开时间（消失时间）
    */
  private var lt = "";
  def setLt(ltime:String): Unit ={
    lt=ltime;
  }
  def getLt:String=lt
  /**
    * 驻留时长，单位秒
    */
  private var rt = 0L
  def setRt(rtime:Long): Unit ={
    rt=rtime;
  }
  def getRt:Long = rt

  /**
    * 终端场强
    */
  private var rssi = ""

  /**
    * 当前连接ssid
    */
  private var ssid = ""

  /**
    * 热点mac
    */
  private var apmac = ""

  /**
    * 热点通道
    */
  private var apc = ""

  /**
    * 热点加密类型
    */
  private var ape = ""

  /**
    * x坐标
    */
  private var x = ""

  /**
    * y坐标
    */
  private var y = ""

  /**
    * 场所编号
    */
  private var nw = ""
  def setNw_= ( _nw : String ){
    nw =  _nw;
  }
  /**
    * 设备编号
    */
  private var devID = ""
  def setDevID_= ( _devID : String ){
    devID =  _devID;
  }
  def getDevId:String=devID

  /**
    * 经度
    */
  private var lg = ""
  def setLg_= ( _lg : String ){
    lg =  _lg;
  }
  /**
    * 维度
    */
  private var la = ""

  /**
    * 是否进入
    */
  private var isenter = ""
  def setIsenter_= ( _isenter : String ){
    isenter =  _isenter;
  }
  def getIsenter:String=isenter

  /**
    *虚拟身份类型
    */
  private var identitype = ""
  def setIdentitype_= ( _identitype : String ){
    identitype =  _identitype;
  }
  /**
    * 虚拟身份内容
    */
  private var certicode = ""
  def setCerticode_= ( _certicode : String ){
    certicode =  _certicode;
  }
  /**
    * 连接类型 是否连接wifi
    */
  private var conntype=""

  def ToMacRecoder(array: Array[String]): Unit = {
    mac = CETCProtocol.Escape(array(0)) //38-29-5A-0D-9B-29
    bd = CETCProtocol.Escape(array(1)) //GUANGDONG OPPO MOBILE TELECOMMUNICATIONS CORP.,LTD
    cssid = CETCProtocol.Escape(array(2))
    ct = CETCProtocol.GetStrDate(CETCProtocol.IntParse(CETCProtocol.Escape(array(3)))) //1548384668
    rssi = CETCProtocol.Escape(array(4)) //-63
    identitype = CETCProtocol.Escape(array(5)) //-1
    certicode = CETCProtocol.Escape(array(6))
    ssid = CETCProtocol.Escape(array(7)) //CMCC-NuxA
    apmac = CETCProtocol.Escape(array(8)) //D0-EF-C1-1A-00-98
    apc = CETCProtocol.Escape(array(9)) //5
    ape = CETCProtocol.Escape(array(10)) //2
    x = CETCProtocol.Escape(array(11)) //0
    y = CETCProtocol.Escape(array(12)) //0
    nw = CETCProtocol.Escape(array(13)) //42082139000003
    devID = CETCProtocol.Escape(array(14)) //14306073X00037F8364FB
    lg = CETCProtocol.Escape(array(15)) //113.12968
    la = CETCProtocol.Escape(array(16)) //31.02796
    isenter = CETCProtocol.Escape(array(17)) //1
    //isenter为0则为离开，那么当前的进入时间为采集时间，当前的采集时间作为离开时间
    if (isenter != null && (isenter.equals(CommFunUtils.EXIT))){
      lt = ct
      ct = CETCProtocol.GetStrDate(CETCProtocol.IntParse(CETCProtocol.Escape(array(18))))
    }else{
      //如果为1则说明还在内部，则采集时间作为进入时间，采集时间加1秒作为离开时间
      lt=CETCProtocol.GetStrDate(CETCProtocol.IntParse(CETCProtocol.Escape(array(3)))+1)
    }
    conntype= CETCProtocol.Escape(array(19))
    //离开时间-进入时间作为存留时间，如果在内部（isenter==1）则停留时间设置为1秒
    val teformat:SimpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    rt=(teformat.parse(lt).getTime-teformat.parse(ct).getTime)/1000;
  }
}
