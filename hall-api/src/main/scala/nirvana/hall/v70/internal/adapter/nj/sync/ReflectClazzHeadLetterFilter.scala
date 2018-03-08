package nirvana.hall.v70.internal.adapter.nj.sync

import java.lang.reflect.Field

import nirvana.hall.api.HallApiConstants
import stark.activerecord.services.ActiveRecord

import scala.reflect.ClassTag

/**
  * Created by yuchen on 2018/3/5.
  * 在同步数据的过程中，如果案件caseid、现场cardid，开头带有A，那么必须将A去掉.
  */
  class ReflectClazzHeadLetterFilter[T <: ActiveRecord:ClassTag](t:T) {
    def reflectFilterHeadLetter(): T ={
      val obj = t.asInstanceOf[T]
      obj.getClass.getDeclaredFields.foreach(handler(_,obj))
      obj
    }

    private def isContains(property:String): Boolean ={
      (property.equals("caseId")
        || property.equals("cardId")
        || property.equals("fingerId")
        || property.equals("palmId"))
    }

    private def handler(f: Field,obj: AnyRef): Unit ={
      if(isContains(f.getName)){
        f.setAccessible(true)
        f.set(obj,if(f.get(obj).toString.toUpperCase.startsWith(HallApiConstants.LPCARDNO_HEAD_LETTER))
          f.get(obj).toString.drop(1))
      }
    }
  }

