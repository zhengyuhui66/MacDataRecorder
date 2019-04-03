package com.hik.base.util
import java.text.SimpleDateFormat
import java.util.{Calendar, Date, GregorianCalendar}

import com.hiklife.utils.RedisUtil
object CETCProtocol {

  def Escape(src: String): String = {
    if (src != null && (!src.equals("")))
    src.replace("\n","").replace("\t","").replace("\r","")

    //src.replace("%09", "\t").replace("%0A", "\n").replace("%0D", "\r").replace("%7C", "|")
    else {
      src;
    }
  }

  def IntParse(src: String): Integer = {
    try {
      if (src != null && (!src.equals(""))) {
        src.toInt
      }
      else {
        0
      }
    } catch {
      case e: Exception => {
        println(e)
        0
      }
    }

  }

  def GetBaseDate(seconds: Integer): Date = {
    if (seconds != null){
      try {
        val gc = new GregorianCalendar
        gc.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1970-01-01 08:00:00"))
        gc.add(Calendar.SECOND, seconds)
        gc.getTime
      } catch {
        case e: Exception =>
          print(e)
          null
      }
    }else{
      null
    }
  }

/*  def getTempLeaveTime(datetime: String): String = {
    if (datetime == null) return ""
    try {
      val gc = new GregorianCalendar
      val sdf:SimpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      val tempDate:Date=sdf.parse(datetime);
      gc.setTime(tempDate)
      gc.add(Calendar.SECOND, 1)
      val rDate:Date=gc.getTime();
      val _rDate=sdf.format(rDate);
      _rDate;
    } catch {
      case e: Exception =>{
          println(e)
        null
        }
    }
  }*/

  def setOffset(redisUtil: RedisUtil, kafkaOffsetKey: String, fromOffsetKey: String, fromOffsetVal: String): Boolean = {
    val results = redisUtil.getObject(kafkaOffsetKey)
    if (results == null || results == None) {
      val map = Map(fromOffsetKey -> fromOffsetVal)
      redisUtil.watchSetObject(kafkaOffsetKey, map)
    } else {
      var map = results.asInstanceOf[Map[String, String]]
      map += (fromOffsetKey -> fromOffsetVal)
      redisUtil.watchSetObject(kafkaOffsetKey, map)
    }
  }

  def GetStrDate(seconds:Integer):String={
    var d = GetBaseDate(seconds);
    var sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    val rStr=sdf.format(d);
   rStr
  }

}
