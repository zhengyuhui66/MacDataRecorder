package com.hik.base

import java.text.SimpleDateFormat
import java.util.Calendar

import com.hik.base.util.CommFunUtils
import com.hiklife.utils.ByteUtil

object test {
  def main(args: Array[String]): Unit = {
    for(i<-0 to 23){
      val c:Calendar=Calendar.getInstance()
      c.set(Calendar.HOUR,i)
      c.set(Calendar.MINUTE,0)
      c.set(Calendar.SECOND,0)
      val formatStr:SimpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      println("==>:"+formatStr.format(c.getTime))
    }
  }
  def getPrexRowkey(datetime:String):String ={
    val dateTimes=datetime.substring(0,10).replace("-","")
    var keyrow = CommFunUtils.byte2HexStr(CommFunUtils.GetHashCodeWithLimit(dateTimes, 0xFF).toByte)
    val bb = new Array[Byte](4)
    ByteUtil.putInt(bb, ("2524608000".toLong - CommFunUtils.Str2Date(datetime).getTime / 1000).asInstanceOf[Int], 0)
    keyrow += CommFunUtils.byte2HexStr(bb)
    keyrow
  }
}
