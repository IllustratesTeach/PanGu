package nirvana.hall.v70.internal.sync

/**
  * Created by yuchen on 2018/3/2.
  *   南京6、7同步的时候，当7.0存库的时，对案件类别需要进行6的编码转换为7的编码
  * 有一部分涉及以某些内容开头的编码，所以考虑用正则匹配
  */
object DictCodeCaseClass6to7Reg {
  private final val reg2010 = "(2010.*)".r
  private final val reg2020 = "(2020.*)".r
  private final val reg2030 = "(2030.*)".r
  private final val reg2040 = "(2040.*)".r
  private final val reg2050 = "(2050.*)".r
  private final val reg2060 = "(2060.*)".r
  private final val reg2070 = "(2070.*)".r
  private final val reg2080 = "(2080.*)".r
  private final val reg2090 = "(2090.*)".r
  private final val reg2099 = "(2099.*)".r
  private final val reg21 = "(21.*)".r
  private final val reg22 = "(22.*)".r
  private final val reg23 = "(23.*)".r
  private final val reg24 = "(24.*)".r
  private final val reg25 = "(25.*)".r
  private final val reg29 = "(29.*)".r
  private final val reg30 = "(30.*)".r
  private final val reg32 = "(32.*)".r
  private final val reg33 = "(33.*)".r
  private final val reg34 = "(34.*)".r
  private final val reg35 = "(35.*)".r
  private final val reg360 = "(360.*)".r
  private final val reg361 = "(361.*)".r
  private final val reg363 = "(363.*)".r
  private final val reg364 = "(364.*)".r
  private final val reg365 = "(365.*)".r
  private final val reg366 = "(366.*)".r
  private final val reg367 = "(367.*)".r
  private final val reg368 = "(368.*)".r
  private final val reg369 = "(369.*)".r
  private final val reg370 = "(370.*)".r
  private final val reg371 = "(371.*)".r
  private final val reg372 = "(372.*)".r
  private final val reg373 = "(373.*)".r
  private final val reg374 = "(374.*)".r
  private final val reg375 = "(375.*)".r
  private final val reg3A01 = "(3A01.*)".r
  private final val reg3A02 = "(3A02.*)".r
  private final val reg3A03 = "(3A03.*)".r
  private final val reg3A04 = "(3A04.*)".r
  private final val reg3A05 = "(3A05.*)".r
  private final val reg3A06 = "(3A06.*)".r
  private final val reg3A07 = "(3A07.*)".r
  private final val reg3A08 = "(3A08.*)".r
  private final val reg3A09 = "(3A09.*)".r
  private final val reg3A1 = "(3A1.*)".r
  private final val reg3A20 = "(3A20.*)".r
  private final val reg3A21 = "(3A21.*)".r
  private final val reg3A22 = "(3A22.*)".r
  private final val reg3A23 = "(3A23.*)".r
  private final val reg3A24 = "(3A24.*)".r
  private final val reg3A25 = "(3A25.*)".r
  private final val reg3A26 = "(3A26.*)".r
  private final val reg3A27 = "(3A27.*)".r
  private final val reg3A28 = "(3A28.*)".r
  private final val reg3A29 = "(3A29.*)".r
  private final val reg3A32 = "(3A32.*)".r
  private final val reg3A33 = "(3A33.*)".r
  private final val reg3A34 = "(3A34.*)".r
  private final val reg3A35 = "(3A35.*)".r
  private final val reg3A36 = "(3A36.*)".r
  private final val reg3A37 = "(3A37.*)".r
  private final val reg3A38 = "(3A38.*)".r
  private final val reg3A39 = "(3A39.*)".r
  private final val reg3A40 = "(3A40.*)".r
  private final val reg3A41 = "(3A41.*)".r
  private final val reg3C93 = "(3C93.*)".r
  private final val reg4010 = "(4010.*)".r
  private final val reg4030 = "(4030.*)".r
  private final val reg4040 = "(4040.*)".r
  private final val reg4090 = "(4090.*)".r
  private final val reg4100 = "(4100.*)".r
  private final val reg4110 = "(4110.*)".r
  private final val reg4120 = "(4120.*)".r
  private final val reg4150 = "(4150.*)".r
  private final val reg4160 = "(4160.*)".r
  private final val reg4170 = "(4170.*)".r
  private final val reg4190 = "(4190.*)".r
  private final val reg4200 = "(4200.*)".r
  private final val reg4210 = "(4210.*)".r
  private final val reg4220 = "(4220.*)".r
  private final val reg4230 = "(4230.*)".r
  private final val reg4240 = "(4240.*)".r
  private final val reg4250 = "(4250.*)".r
  private final val reg4260 = "(4260.*)".r
  private final val reg4270 = "(4270.*)".r
  private final val reg4280 = "(4280.*)".r
  private final val reg4290 = "(4290.*)".r
  private final val reg4300 = "(4300.*)".r
  private final val reg4310 = "(4310.*)".r
  private final val reg4320 = "(4320.*)".r
  private final val reg4330 = "(4330.*)".r
  private final val reg60 = "(60.*)".r
  private final val reg9 = "(9.*)".r


  def caseClassDict6to7(code6:String): Option[String] ={
    val code7 = code6 match {
      case reg2010(code6) =>
        Some("060100")
      case reg2020(code6) =>
        Some("020000")
      case reg2030(code6) =>
        Some("040100")
      case reg2040(code6) =>
        Some("050000")
      case reg2050(code6) =>
        Some("060000")
      case reg2060(code6) =>
        Some("020715")
      case reg2070(code6) =>
        Some("020200")
      case reg2080(code6) =>
        Some("060100")
      case reg2090(code6) =>
        Some("060100")
      case reg2099(code6) =>
        Some("060100")
      case reg21(code6) =>
        Some("060300")
      case reg22(code6) =>
        Some("020200")
      case reg23(code6) =>
        Some("020715")
      case reg24(code6) =>
        Some("060100")
      case reg25(code6) =>
        Some("060100")
      case reg29(code6) =>
        Some("060000")
      case reg30(code6) =>
        Some("060000")
      case reg32(code6) =>
        Some("060000")
      case reg33(code6) =>
        Some("060000")
      case reg34(code6) =>
        Some("040000")
      case reg35(code6) =>
        Some("060000")
      case reg360(code6) =>
        Some("060200")
      case reg361(code6) =>
        Some("060300")
      case reg363(code6) =>
        Some("060400")
      case reg364(code6) =>
        Some("020200")
      case reg365(code6) =>
        Some("060000")
      case reg366(code6) =>
        Some("060800")
      case reg367(code6) =>
        Some("060800")
      case reg368(code6) =>
        Some("060900")
      case reg369(code6) =>
        Some("060900")
      case reg370(code6) =>
        Some("060197")
      case reg371(code6) =>
        Some("060700")
      case reg372(code6) =>
        Some("060700")
      case reg373(code6) =>
        Some("060700")
      case reg374(code6) =>
        Some("060700")
      case reg375(code6) =>
        Some("060000")
      case reg3A01(code6) =>
        Some("020600")
      case reg3A02(code6) =>
        Some("020600")
      case reg3A03(code6) =>
        Some("020600")
      case reg3A04(code6) =>
        Some("020600")
      case reg3A05(code6) =>
        Some("060000")
      case reg3A06(code6) =>
        Some("030000")
      case reg3A07(code6) =>
        Some("060000")
      case reg3A08(code6) =>
        Some("060000")
      case reg3A09(code6) =>
        Some("060000")
      case reg3A1(code6) =>
        Some("060300")
      case reg3A20(code6) =>
        Some("060000")
      case reg3A21(code6) =>
        Some("060000")
      case reg3A22(code6) =>
        Some("060000")
      case reg3A23(code6) =>
        Some("060000")
      case reg3A24(code6) =>
        Some("060000")
      case reg3A25(code6) =>
        Some("060000")
      case reg3A26(code6) =>
        Some("060000")
      case reg3A27(code6) =>
        Some("060000")
      case reg3A28(code6) =>
        Some("030000")
      case reg3A29(code6) =>
        Some("060100")
      case reg3A32(code6) =>
        Some("060000")
      case reg3A33(code6) =>
        Some("020600")
      case reg3A34(code6) =>
        Some("060000")
      case reg3A35(code6) =>
        Some("060000")
      case reg3A36(code6) =>
        Some("060100")
      case reg3A37(code6) =>
        Some("060100")
      case reg3A38(code6) =>
        Some("020500")
      case reg3A39(code6) =>
        Some("030000")
      case reg3A40(code6) =>
        Some("060100")
      case reg3A41(code6) =>
        Some("030000")
      case reg3C93(code6) =>
        Some("060000")
      case reg4010(code6) =>
        Some("060000")
      case reg4030(code6) =>
        Some("060100")
      case reg4040(code6) =>
        Some("060000")
      case reg4090(code6) =>
        Some("060000")
      case reg4100(code6) =>
        Some("020600")
      case reg4110(code6) =>
        Some("060100")
      case reg4120(code6) =>
        Some("060000")
      case reg4150(code6) =>
        Some("060800")
      case reg4160(code6) =>
        Some("030000")
      case reg4170(code6) =>
        Some("060000")
      case reg4190(code6) =>
        Some("060000")
      case reg4200(code6) =>
        Some("020000")
      case reg4210(code6) =>
        Some("060000")
      case reg4220(code6) =>
        Some("060000")
      case reg4230(code6) =>
        Some("060100")
      case reg4240(code6) =>
        Some("060000")
      case reg4250(code6) =>
        Some("060100")
      case reg4260(code6) =>
        Some("020600")
      case reg4270(code6) =>
        Some("060700")
      case reg4280(code6) =>
        Some("060000")
      case reg4290(code6) =>
        Some("060000")
      case reg4300(code6) =>
        Some("060000")
      case reg4310(code6) =>
        Some("060000")
      case reg4320(code6) =>
        Some("060000")
      case reg4330(code6) =>
        Some("030000")
      case reg60(code6) =>
        Some("060300")
      case reg9(code6) =>
        Some("069900")
      case _ =>
        None
    }
    code7
  }
}
