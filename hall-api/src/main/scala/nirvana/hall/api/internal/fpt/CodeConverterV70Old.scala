package nirvana.hall.api.internal.fpt

/**
  * Created by zqLuo
  */
object CodeConverterV70Old {
  /**
    * 采集信息原因代码转换
    */
  def converCollectionReason(code: String): String = {
    var collectionReason = ""
    if (!strIsEmpty(code)){
      code match {
        case "01" =>
          collectionReason = "01"
        //经人民法院审判定罪的罪犯

        case "02" =>
          collectionReason = "02"
        //依法被收容教养的人员

        case "03" =>
          collectionReason = "03"
        //依法被行政拘留或者因实施违反治安管理或者出入境管理行为被依法予以其他行政处罚的人员，但是被当场作出治安管理处罚的除外

        case "04" =>
          collectionReason = "04"
        //依法被强制戒毒的人员

        case "05" =>
          collectionReason = "05"
        //依法被收容教育的人员

        case "06" =>
          collectionReason = "06"
        //依法被拘传、取保候审、监视居住、拘留或者逮捕的犯罪嫌疑人

        case "07" =>
          collectionReason = "07"
        //依法被继续盘问的人员

        case "08" =>
          collectionReason = "08"
        //公安机关因办理案（事）件需要，经县级以上公安机关负责人批准采集指掌纹信息的人员

        case "21" =>
          collectionReason = "21"
        //保安员申请人

        case "99" =>
          collectionReason = "99"
        //其他

        case _ =>
          collectionReason = "99"
        //若匹配不到，返回其他

      }
    }
    collectionReason
  }

  /**
    * 性别转换
    */
  def converGender(code: String): String = {
    var gender = ""
    if (!strIsEmpty(code)){
      code match {
        case "2" =>
          gender = "2"
        //女

        case "0" =>
          gender = "0"
        //未知的性别

        case "1" =>
          gender = "1"
        //男

        case "9" =>
          gender = "9"
        //未说明的性别

        case _ =>
          gender = "0"
        //若匹配不到，返回未知的性别

      }
    }
    gender
  }

  /**
    * 国籍转换
    */
  def converNativeplace(code: String): String = {
    var nativeplace = ""
    if (!strIsEmpty(code)){
      code match {
        case "004" =>
          nativeplace = "004"
        //阿富汗

        case "008" =>
          nativeplace = "008"
        //阿尔巴尼亚

        case "010" =>
          nativeplace = "010"
        //南极洲

        case "012" =>
          nativeplace = "012"
        //阿尔及利亚

        case "016" =>
          nativeplace = "016"
        //美属萨摩亚

        case "020" =>
          nativeplace = "020"
        //安道尔

        case "024" =>
          nativeplace = "024"
        //安哥拉

        case "028" =>
          nativeplace = "028"
        //安提瓜和巴布达

        case "031" =>
          nativeplace = "031"
        //阿塞拜疆

        case "032" =>
          nativeplace = "032"
        //阿根廷

        case "036" =>
          nativeplace = "036"
        //澳大利亚

        case "040" =>
          nativeplace = "040"
        //奥地利

        case "044" =>
          nativeplace = "044"
        //巴哈马

        case "048" =>
          nativeplace = "048"
        //巴林

        case "050" =>
          nativeplace = "050"
        //孟加拉

        case "051" =>
          nativeplace = "051"
        //亚美尼亚

        case "052" =>
          nativeplace = "052"
        //巴巴多斯

        case "056" =>
          nativeplace = "056"
        //比利时

        case "060" =>
          nativeplace = "060"
        //百慕大

        case "064" =>
          nativeplace = "064"
        //不丹

        case "068" =>
          nativeplace = "068"
        //玻利维亚

        case "070" =>
          nativeplace = "070"
        //波斯尼和黑塞哥维那

        case "072" =>
          nativeplace = "072"
        //博茨瓦纳

        case "074" =>
          nativeplace = "074"
        //布维岛

        case "076" =>
          nativeplace = "076"
        //巴西

        case "084" =>
          nativeplace = "084"
        //伯利兹

        case "086" =>
          nativeplace = "086"
        //英属印度洋领土

        case "090" =>
          nativeplace = "090"
        //所罗门群岛

        case "092" =>
          nativeplace = "092"
        //英属维尔京群岛

        case "096" =>
          nativeplace = "096"
        //文莱

        case "100" =>
          nativeplace = "100"
        //保加利亚

        case "104" =>
          nativeplace = "104"
        //缅甸

        case "108" =>
          nativeplace = "108"
        //布隆迪

        case "112" =>
          nativeplace = "112"
        //白俄罗斯

        case "116" =>
          nativeplace = "116"
        //柬埔寨

        case "120" =>
          nativeplace = "120"
        //喀麦隆

        case "124" =>
          nativeplace = "124"
        //加拿大

        case "128" =>
          nativeplace = "128"
        //坎顿和恩德贝里群岛

        case "132" =>
          nativeplace = "132"
        //佛得角

        case "136" =>
          nativeplace = "136"
        //开曼群岛

        case "140" =>
          nativeplace = "140"
        //中非

        case "144" =>
          nativeplace = "144"
        //斯里兰卡

        case "148" =>
          nativeplace = "148"
        //乍得

        case "152" =>
          nativeplace = "152"
        //智利

        case "156" =>
          nativeplace = "156"
        //中国

        case "158" =>
          nativeplace = "158"
        //中国台湾

        case "162" =>
          nativeplace = "162"
        //圣诞岛

        case "166" =>
          nativeplace = "166"
        //可可(基林)群岛

        case "170" =>
          nativeplace = "170"
        //哥伦比亚

        case "174" =>
          nativeplace = "174"
        //科摩罗

        case "175" =>
          nativeplace = "175"
        //马约特

        case "178" =>
          nativeplace = "178"
        //刚果

        case "180" =>
          nativeplace = "180"
        //扎伊尔

        case "184" =>
          nativeplace = "184"
        //库克群岛

        case "188" =>
          nativeplace = "188"
        //哥斯达黎加

        case "191" =>
          nativeplace = "191"
        //克罗地亚

        case "192" =>
          nativeplace = "192"
        //古巴

        case "196" =>
          nativeplace = "196"
        //塞浦路斯

        case "201" =>
          nativeplace = "200"
        //捷克斯洛伐克

        case "200" =>
          nativeplace = "203"
        //捷克

        case "204" =>
          nativeplace = "204"
        //贝宁

        case "208" =>
          nativeplace = "208"
        //丹麦

        case "212" =>
          nativeplace = "212"
        //多米尼加

        case "214" =>
          nativeplace = "214"
        //多米尼加共和国

        case "216" =>
          nativeplace = "216"
        //毛德地

        case "218" =>
          nativeplace = "218"
        //厄瓜多尔

        case "222" =>
          nativeplace = "222"
        //萨尔瓦多

        case "226" =>
          nativeplace = "226"
        //赤道几内亚

        case "230" =>
          nativeplace = "230"
        //埃塞俄比亚

        case "232" =>
          nativeplace = "232"
        //厄立特里亚

        case "233" =>
          nativeplace = "233"
        //爱沙尼亚

        case "234" =>
          nativeplace = "234"
        //法罗

        case "238" =>
          nativeplace = "238"
        //福克兰群岛(马尔维纳斯)

        case "239" =>
          nativeplace = "239"
        //南乔治亚岛和南桑德韦奇岛

        case "242" =>
          nativeplace = "242"
        //斐济

        case "246" =>
          nativeplace = "246"
        //芬兰

        case "250" =>
          nativeplace = "250"
        //法国

        case "254" =>
          nativeplace = "254"
        //法属圭亚那

        case "258" =>
          nativeplace = "258"
        //法属波利尼西亚

        case "260" =>
          nativeplace = "260"
        //法属南部领土

        case "262" =>
          nativeplace = "262"
        //吉布提

        case "266" =>
          nativeplace = "266"
        //加蓬

        case "270" =>
          nativeplace = "270"
        //冈比亚

        case "280" =>
          nativeplace = "276"
        //德国

        case "288" =>
          nativeplace = "288"
        //加纳

        case "292" =>
          nativeplace = "292"
        //直布罗陀

        case "296" =>
          nativeplace = "296"
        //基里巴斯

        case "300" =>
          nativeplace = "300"
        //希腊

        case "304" =>
          nativeplace = "304"
        //格陵兰

        case "308" =>
          nativeplace = "308"
        //格林纳达

        case "312" =>
          nativeplace = "312"
        //瓜德罗普

        case "316" =>
          nativeplace = "316"
        //关岛

        case "320" =>
          nativeplace = "320"
        //危地马拉

        case "324" =>
          nativeplace = "324"
        //几内亚

        case "328" =>
          nativeplace = "328"
        //圭亚那

        case "332" =>
          nativeplace = "332"
        //海地

        case "334" =>
          nativeplace = "334"
        //赫德岛和麦克唐纳岛

        case "336" =>
          nativeplace = "336"
        //梵蒂冈

        case "340" =>
          nativeplace = "340"
        //洪都拉斯

        case "348" =>
          nativeplace = "348"
        //匈牙利

        case "352" =>
          nativeplace = "352"
        //冰岛

        case "356" =>
          nativeplace = "356"
        //印度

        case "360" =>
          nativeplace = "360"
        //印度尼西亚

        case "364" =>
          nativeplace = "364"
        //伊朗

        case "368" =>
          nativeplace = "368"
        //伊拉克

        case "372" =>
          nativeplace = "372"
        //爱尔兰

        case "374" =>
          nativeplace = "374"
        //巴基斯坦

        case "378" =>
          nativeplace = "378"
        //以色列

        case "380" =>
          nativeplace = "380"
        //意大利

        case "384" =>
          nativeplace = "384"
        //象牙海岸

        case "388" =>
          nativeplace = "388"
        //牙买加

        case "392" =>
          nativeplace = "392"
        //日本

        case "396" =>
          nativeplace = "396"
        //约斡斯顿岛

        case "400" =>
          nativeplace = "400"
        //约旦

        case "404" =>
          nativeplace = "404"
        //肯尼亚

        case "408" =>
          nativeplace = "408"
        //朝鲜

        case "410" =>
          nativeplace = "410"
        //韩国

        case "414" =>
          nativeplace = "414"
        //科威特

        case "417" =>
          nativeplace = "417"
        //吉尔吉斯斯坦

        case "418" =>
          nativeplace = "418"
        //老挝

        case "422" =>
          nativeplace = "422"
        //黎巴嫩

        case "426" =>
          nativeplace = "426"
        //莱索托

        case "428" =>
          nativeplace = "428"
        //拉脱维亚

        case "430" =>
          nativeplace = "430"
        //利比里亚

        case "438" =>
          nativeplace = "438"
        //列支敦士登

        case "440" =>
          nativeplace = "440"
        //立陶宛

        case "442" =>
          nativeplace = "442"
        //卢森堡

        case "446" =>
          nativeplace = "446"
        //澳门

        case "450" =>
          nativeplace = "450"
        //马达加斯加

        case "454" =>
          nativeplace = "454"
        //马拉维

        case "458" =>
          nativeplace = "458"
        //马来西亚

        case "462" =>
          nativeplace = "462"
        //马尔代夫

        case "466" =>
          nativeplace = "466"
        //马里

        case "470" =>
          nativeplace = "470"
        //马耳他

        case "474" =>
          nativeplace = "474"
        //马提尼克

        case "478" =>
          nativeplace = "478"
        //毛里塔尼亚

        case "480" =>
          nativeplace = "480"
        //毛里求斯

        case "484" =>
          nativeplace = "484"
        //墨西哥

        case "492" =>
          nativeplace = "492"
        //摩纳哥

        case "496" =>
          nativeplace = "496"
        //蒙古

        case "498" =>
          nativeplace = "498"
        //摩尔多瓦

        case "500" =>
          nativeplace = "500"
        //蒙特塞拉特

        case "504" =>
          nativeplace = "504"
        //摩洛哥

        case "508" =>
          nativeplace = "508"
        //莫桑比克

        case "512" =>
          nativeplace = "512"
        //阿曼

        case "516" =>
          nativeplace = "516"
        //纳米比亚

        case "520" =>
          nativeplace = "520"
        //瑙鲁

        case "524" =>
          nativeplace = "524"
        //尼泊尔

        case "528" =>
          nativeplace = "528"
        //荷兰

        case "532" =>
          nativeplace = "532"
        //荷属安的列斯

        case "533" =>
          nativeplace = "533"
        //阿鲁巴

        case "540" =>
          nativeplace = "540"
        //新喀里多尼亚

        case "548" =>
          nativeplace = "548"
        //瓦努阿图

        case "550" =>
          nativeplace = "550"
        //新西兰

        case "558" =>
          nativeplace = "558"
        //尼加拉瓜

        case "562" =>
          nativeplace = "562"
        //尼日尔

        case "566" =>
          nativeplace = "566"
        //尼日利亚

        case "570" =>
          nativeplace = "570"
        //纽埃岛

        case "574" =>
          nativeplace = "574"
        //诺福克岛

        case "578" =>
          nativeplace = "578"
        //挪威

        case "580" =>
          nativeplace = "580"
        //北马里亚纳

        case "581" =>
          nativeplace = "581"
        //美属太平洋各群岛

        case "583" =>
          nativeplace = "583"
        //密克罗尼西亚

        case "584" =>
          nativeplace = "584"
        //马绍尔群岛

        case "585" =>
          nativeplace = "585"
        //贝劳

        case "586" =>
          nativeplace = "586"

        case "590" =>
          nativeplace = "590"
        //巴拿马

        case "598" =>
          nativeplace = "598"
        //巴布亚新几内亚

        case "600" =>
          nativeplace = "600"
        //巴拉圭

        case "604" =>
          nativeplace = "604"
        //秘鲁

        case "608" =>
          nativeplace = "608"
        //菲律宾

        case "612" =>
          nativeplace = "612"
        //皮特凯恩岛

        case "616" =>
          nativeplace = "616"
        //波兰

        case "620" =>
          nativeplace = "620"
        //葡萄牙

        case "624" =>
          nativeplace = "624"
        //几内亚比绍

        case "626" =>
          nativeplace = "626"
        //东帝汶

        case "630" =>
          nativeplace = "630"
        //波多黎各

        case "634" =>
          nativeplace = "634"
        //卡诺尔

        case "638" =>
          nativeplace = "638"
        //留尼汪

        case "642" =>
          nativeplace = "642"
        //罗马尼亚

        case "643" =>
          nativeplace = "643"
        //俄罗斯

        case "646" =>
          nativeplace = "646"
        //卢旺达

        case "654" =>
          nativeplace = "654"
        //圣赫勒拿

        case "658" =>
          nativeplace = "659"
        //圣基茨和尼维斯

        case "660" =>
          nativeplace = "660"
        //安圭拉

        case "662" =>
          nativeplace = "662"
        //圣卢西亚

        case "666" =>
          nativeplace = "666"
        //圣皮埃尔和密克隆

        case "670" =>
          nativeplace = "670"
        //圣文森特和格林纳丁斯

        case "674" =>
          nativeplace = "674"
        //圣马力诺

        case "678" =>
          nativeplace = "678"
        //圣多美和普林西比

        case "682" =>
          nativeplace = "682"
        //沙特阿拉伯

        case "686" =>
          nativeplace = "686"
        //塞内加尔

        case "690" =>
          nativeplace = "690"
        //塞舌尔

        case "694" =>
          nativeplace = "694"
        //塞拉利昂

        case "702" =>
          nativeplace = "702"
        //新加坡

        case "703" =>
          nativeplace = "703"
        //斯洛伐克

        case "704" =>
          nativeplace = "704"
        //越南

        case "705" =>
          nativeplace = "705"
        //斯洛文尼亚

        case "706" =>
          nativeplace = "706"
        //索马里

        case "710" =>
          nativeplace = "710"
        //南非

        case "716" =>
          nativeplace = "716"
        //津巴布韦

        case "724" =>
          nativeplace = "724"
        //西班牙

        case "732" =>
          nativeplace = "732"
        //西撤哈拉

        case "736" =>
          nativeplace = "736"
        //苏丹

        case "740" =>
          nativeplace = "740"
        //苏里南

        case "744" =>
          nativeplace = "744"
        //斯瓦巴德群岛

        case "748" =>
          nativeplace = "748"
        //斯威士兰

        case "752" =>
          nativeplace = "752"
        //瑞典

        case "756" =>
          nativeplace = "756"
        //瑞士

        case "760" =>
          nativeplace = "760"
        //叙利亚

        case "762" =>
          nativeplace = "762"
        //塔吉克斯坦

        case "764" =>
          nativeplace = "764"
        //泰国

        case "768" =>
          nativeplace = "768"
        //多哥

        case "772" =>
          nativeplace = "772"
        //托克劳

        case "776" =>
          nativeplace = "776"
        //汤加

        case "780" =>
          nativeplace = "780"
        //特立尼达和多哥巴

        case "784" =>
          nativeplace = "784"
        //阿拉伯联合酋长国

        case "788" =>
          nativeplace = "788"
        //突尼斯

        case "792" =>
          nativeplace = "792"
        //土耳其

        case "795" =>
          nativeplace = "795"
        //土库曼斯坦

        case "796" =>
          nativeplace = "796"
        //特克斯和凯科斯群岛

        case "798" =>
          nativeplace = "798"
        //图瓦卢

        case "800" =>
          nativeplace = "800"
        //乌干达

        case "804" =>
          nativeplace = "804"
        //乌克兰

        case "807" =>
          nativeplace = "807"
        //马其顿

        case "818" =>
          nativeplace = "818"
        //埃及

        case "826" =>
          nativeplace = "826"
        //英国

        case "834" =>
          nativeplace = "834"
        //坦桑尼亚

        case "840" =>
          nativeplace = "840"
        //美国

        case "850" =>
          nativeplace = "850"
        //美属维尔京群岛

        case "854" =>
          nativeplace = "854"
        //布基纳法索

        case "858" =>
          nativeplace = "858"
        //乌拉圭

        case "860" =>
          nativeplace = "860"
        //乌兹别克斯坦

        case "862" =>
          nativeplace = "862"
        //委内瑞拉

        case "876" =>
          nativeplace = "876"
        //瓦利斯和富图纳群岛

        case "882" =>
          nativeplace = "882"
        //西萨摩亚

        case "886" =>
          nativeplace = "886"
        //也门

        case "890" =>
          nativeplace = "890"
        //南斯拉夫

        case "894" =>
          nativeplace = "894"
        //赞比亚

        case _ =>
          nativeplace = code
        //若匹配不到，返回原值

      }
    }
    nativeplace
  }

  /**
    * 民族转换
    */
  def converNation(code: String): String = {
    var nation = ""
    if (!strIsEmpty(code)){
      code match {
        case "01" =>
          nation = "01"
        //汉族

        case "02" =>
          nation = "02"
        //蒙古族

        case "03" =>
          nation = "03"
        //回族

        case "04" =>
          nation = "04"
        //藏族

        case "05" =>
          nation = "05"
        //维吾尔族

        case "06" =>
          nation = "06"
        //苗族

        case "07" =>
          nation = "07"
        //彝族

        case "08" =>
          nation = "08"
        //壮族

        case "09" =>
          nation = "09"
        //布依族

        case "10" =>
          nation = "10"
        //朝鲜族

        case "11" =>
          nation = "11"
        //满族

        case "12" =>
          nation = "12"
        //侗族

        case "13" =>
          nation = "13"
        //瑶族

        case "14" =>
          nation = "14"
        //白族

        case "15" =>
          nation = "15"
        //土家族

        case "16" =>
          nation = "16"
        //哈尼族

        case "17" =>
          nation = "17"
        //哈萨克族

        case "18" =>
          nation = "18"
        //傣族

        case "19" =>
          nation = "19"
        //黎族

        case "20" =>
          nation = "20"
        //僳僳族

        case "21" =>
          nation = "21"
        //佤族

        case "22" =>
          nation = "22"
        //畲族

        case "23" =>
          nation = "23"
        //高山族

        case "24" =>
          nation = "24"
        //拉祜族

        case "25" =>
          nation = "25"
        //水族

        case "26" =>
          nation = "26"
        //东乡族

        case "27" =>
          nation = "27"
        //纳西族

        case "28" =>
          nation = "28"
        //景颇族

        case "29" =>
          nation = "29"
        //柯尔克孜族

        case "30" =>
          nation = "30"
        //土族

        case "31" =>
          nation = "31"
        //达斡尔族

        case "32" =>
          nation = "32"
        //仫佬族

        case "33" =>
          nation = "33"
        //羌族

        case "34" =>
          nation = "34"
        //布朗族

        case "35" =>
          nation = "35"
        //撒拉族

        case "36" =>
          nation = "36"
        //毛难族

        case "37" =>
          nation = "37"
        //仡佬族

        case "38" =>
          nation = "38"
        //锡伯族

        case "39" =>
          nation = "39"
        //阿昌族

        case "40" =>
          nation = "40"
        //普米族

        case "41" =>
          nation = "41"
        //塔吉克族

        case "42" =>
          nation = "42"
        //怒族

        case "43" =>
          nation = "43"
        //乌孜别克族

        case "44" =>
          nation = "44"
        //俄罗斯族

        case "45" =>
          nation = "45"
        //鄂温克族

        case "46" =>
          nation = "46"
        //崩龙族

        case "47" =>
          nation = "47"
        //保安族

        case "48" =>
          nation = "48"
        //裕固族

        case "49" =>
          nation = "49"
        //京族

        case "50" =>
          nation = "50"
        //塔塔尔族

        case "51" =>
          nation = "51"
        //独龙族

        case "52" =>
          nation = "52"
        //鄂伦春族

        case "53" =>
          nation = "53"
        //赫哲族

        case "54" =>
          nation = "54"
        //门巴族

        case "55" =>
          nation = "55"
        //珞巴族

        case "56" =>
          nation = "56"
        //基诺族

        case "98" =>
          nation = "98"
        //外国血统(中国籍人士)

        case "99" =>
          nation = "99"
        //未确认

        case _ =>
          nation = "99"
        //若匹配不到，返回未确认

      }
    }
    nation
  }

  /**
    * 证件类型转换
    */
  def converCertificateType(code: String): String = {
    var certificateType = ""
    if (!strIsEmpty(code)){
      code match {
        case "113" =>
          certificateType = "13"
        //户口簿

        case "129" =>
          certificateType = "15"
        //记者证

        case "131" =>
          certificateType = "12"
        //工作证

        case "133" =>
          certificateType = "16"
        //学生证

        case "151" =>
          certificateType = "40"
        //入出境通行证

        case "153" =>
          certificateType = "65"
        //临时出入证

        case "155" =>
          certificateType = "64"
        //住宿证

        case "157" =>
          certificateType = "25"
        //医疗证

        case "159" =>
          certificateType = "26"
        //劳保证

        case "191" =>
          certificateType = "14"
        //会员证

        case "217" =>
          certificateType = "23"
        //残废证

        case "233" =>
          certificateType = "22"
        //军人通行证

        case "291" =>
          certificateType = "21"
        //证明信

        case "311" =>
          certificateType = "24"
        //持枪证

        case "323" =>
          certificateType = "60"
        //粮油证

        case "325" =>
          certificateType = "62"
        //购煤证

        case "327" =>
          certificateType = "63"
        //购煤气证

        case "333" =>
          certificateType = "71"
        //车辆通行证

        case "335" =>
          certificateType = "72"
        //机动车驾驶执照

        case "351" =>
          certificateType = "73"
        //自行车执照

        case "353" =>
          certificateType = "81"
        //汽车牌照

        case "355" =>
          certificateType = "82"
        //拖拉机牌照

        case "357" =>
          certificateType = "83"
        //摩托车牌照

        case "359" =>
          certificateType = "84"
        //船舶牌照

        case "368" =>
          certificateType = "85"
        //三轮车牌照

        case "363" =>
          certificateType = "86"
        //自行车牌照

        case "411" =>
          certificateType = "31"
        //外交护照

        case "412" =>
          certificateType = "32"
        //公务护照

        case "413" =>
          certificateType = "33"
        //因公普通护照

        case "414" =>
          certificateType = "34"
        //普通护照

        case "415" =>
          certificateType = "48"
        //旅行证

        case "417" =>
          certificateType = "52"
        //外国人出入境通行证

        case "418" =>
          certificateType = "46"
        //外国人旅行证

        case "419" =>
          certificateType = "35"
        //海员证

        case "513" =>
          certificateType = "42"
        //因私往来港澳通行证

        case "515" =>
          certificateType = "43"
        //前往港澳通行证

        case "516" =>
          certificateType = "44"
        //港澳通胞回乡证

        case "518" =>
          certificateType = "41"
        //因公往来港澳通行证

        case "554" =>
          certificateType = "50"
        //外国人居留证

        case "555" =>
          certificateType = "51"
        //外国人临时居留证

        case "711" =>
          certificateType = "49"
        //边境通行证

        case "771" =>
          certificateType = "36"
        //铁路员工证

        case "781" =>
          certificateType = "37"
        //机组人员证

        case "990" =>
          certificateType = "99"
        //其他证件

        case "45" =>
          certificateType = "45"
        //外国人通行证

        case "47" =>
          certificateType = "47"
        //外国人回国证

        case "27" =>
          certificateType = "27"
        //归国证明书

        case "28" =>
          certificateType = "28"
        //台湾同胞旅游证明书

        case _ =>
          certificateType = code

      }
    }
    certificateType
  }

  /**
    * 指纹指位转换
    */
  def converFingerFgp(fgp: String): scala.collection.mutable.HashMap[String,String] = {
    val map = new scala.collection.mutable.HashMap[String,String]
    if (!strIsEmpty(fgp)) {
      val fgpInt = fgp.toInt
      if (fgpInt <= 10) { //平面
        map.put("fgp_case", "0")
        map.put("fgp", fgpInt + "")
      }
      else if (fgpInt <= 20) { //滚动
        map.put("fgp_case", "1")
        map.put("fgp", (fgpInt - 10) + "")
      }
      else map.put("fgp", fgpInt + "")
    }
    map
  }

  /**
    * 指掌纹特征提取方式代码转换
    */
  def converExtractMethod(code: String): String = {
    var extractMethod = ""
    if (!strIsEmpty(code)){
      code match {
        case "A" =>
          extractMethod = "A"
        //自动提取

        case "U" =>
          extractMethod = "U"
        //自动提取且需要人工编辑

        case "E" =>
          extractMethod = "E"
        //自动提取且已经人工编辑

        case "M" =>
          extractMethod = "M"
        //人工抽取

        case "O" =>
          extractMethod = "O"
        //其它

        case _ =>
          extractMethod = "O"
        //若匹配不到，返回其它

      }
    }
    extractMethod
  }

  /**
    * 指掌纹缺失情况代码转换
    */
  def converDefect(code: String): String = {
    var defect = ""
    if (!strIsEmpty(code)){
      code match {
        case "0" =>
          defect = "0"
        //正常

        case "1" =>
          defect = "1"
        //残缺

        case "2" =>
          defect = "2"
        //系统设置不采集

        case "3" =>
          defect = "3"
        //受伤未采集

        case "9" =>
          defect = "9"
        //其他缺失情况

        case _ =>
          defect = "9"
        //若匹配不到，返回其他缺失情况

      }
    }
    defect
  }

  /**
    * 指纹纹型代码转换
    */
  def converFingerPattern(code: String): String = {
    var fingerPattern = ""
    if (!strIsEmpty(code)){
      code match {
        case "1" =>
          fingerPattern = "1"
        //弓型

        case "2" =>
          fingerPattern = "2"
        //左箕

        case "3" =>
          fingerPattern = "3"
        //右箕

        case "4" =>
          fingerPattern = "4"
        //斗

        case "6" =>
          fingerPattern = "0"
        //不确定

        case _ =>
          fingerPattern = "0"
        //若匹配不到，返回不确定

      }
    }
    fingerPattern
  }

  /**
    * 掌纹掌位转换
    */
  def converPalmFgp(fgp: String): String = {
    var converFgp = ""
    if (!strIsEmpty(fgp)){
      fgp match {
        case "31" =>
          converFgp = "11"
        //右手平面掌纹

        case "32" =>
          converFgp = "12"
        //左手平面掌纹

        case "33" =>
          converFgp = "17"
        //右手侧面掌纹

        case "34" =>
          converFgp = "18"
        //左手侧面掌纹

        case "35" =>
          converFgp = "35"
        //右手平面全掌纹

        case "36" =>
          converFgp = "36"
        //左手平面全掌纹

        case "39" =>
          converFgp = "39"
        //不确定掌纹

        case _ =>
          converFgp = "39"
        //若匹配不到，返回不确定掌纹

      }
    }
    converFgp
  }

  /**
    * 掌纹三角位置类型代码转换
    */
  def converPositionType(code: String): String = {
    var positionType = ""
    if (!strIsEmpty(code)){
      code match {
        case "00" =>
          positionType = "00"
        //未知位置三角

        case "01" =>
          positionType = "01"
        //右手腕部三角

        case "02" =>
          positionType = "02"
        //右手食指指根三角

        case "03" =>
          positionType = "03"
        //右手中指指根三角

        case "04" =>
          positionType = "04"
        //右手环指指根三角

        case "05" =>
          positionType = "05"
        //右手小指指根三角

        case "06" =>
          positionType = "06"
        //左手腕部三角

        case "07" =>
          positionType = "07"
        //左手食指指根三角

        case "08" =>
          positionType = "08"
        //左手中指指根三角

        case "09" =>
          positionType = "09"
        //左手环指指根三角

        case "10" =>
          positionType = "10"
        //左手小指指根三角

        case _ =>
          positionType = code

      }
    }
    positionType
  }

  /**
    * 人像照片类型代码
    */
  def converType(code: String): String = {
    var photoType = ""
    if (!strIsEmpty(code)){
      code match {
        case "1" =>
          photoType = "1"
        //正面像

        case "2" =>
          photoType = "2"
        //左侧像

        case "4" =>
          photoType = "3"
        //右侧像

        case "9" =>
          photoType = "9"

        case _ =>
          photoType = "9"
        //匹配不到，返回其他

      }
    }
    photoType
  }

  /**
    * 乳突线颜色代码转换
    */
  def converRidgeColor(code: String): String = {
    var ridgeColor = ""
    if (!strIsEmpty(code)){
      code match {
        case "1" =>
          ridgeColor = "1"
        //白色

        case "2" =>
          ridgeColor = "2"
        //黑色

        case "9" =>
          ridgeColor = "9"

        case _ =>
          ridgeColor = "9"

      }
    }
    ridgeColor
  }

  /**
    * 指掌纹比对任务类型代码转换
    */
  def converQueryType(code: String): String = {
    var queryType = ""
    if (!strIsEmpty(code)){
      code match {
        case "0" =>
          queryType = "0"
        //查重

        case "1" =>
          queryType = "2"
        //倒查

        case "2" =>
          queryType = "1"
        //正查

        case "3" =>
          queryType = "3"
        //串查

        case "9" =>
          queryType = "9"
        //未知

        case _ =>
          queryType = "9"
        //匹配不到，归为未知

      }
    }
    queryType
  }

  /**
    * 案件类别代码转换
    */
  def converCaseClass(code: String): String = {
    var caseClass = ""
    if (!strIsEmpty(code)){
      code match {
        case "01000000" =>
          caseClass = "010000"
        //危害国家安全案

        case "01000100" =>
          caseClass = "010110"
        //背叛国家案

        case "01000200" =>
          caseClass = "010120"
        //分裂国家案

        case "01000300" =>
          caseClass = "010130"
        //煽动分裂国家案

        case "01000400" =>
          caseClass = "010140"
        //武装叛乱、暴乱案

        case "01000401" =>
          caseClass = "010150"
        //策动武装暴乱案

        case "01000500" =>
          caseClass = "010160"
        //颠覆国家政权案

        case "01000700" =>
          caseClass = "010180"
        //资助危害国家安全犯罪活动案

        case "01000600" =>
          caseClass = "010190"
        //煽动颠覆国家政权案

        case "01000800" =>
          caseClass = "010210"
        //投敌叛变案

        case "01000900" =>
          caseClass = "010220"
        //叛逃案

        case "01001000" =>
          caseClass = "010310"
        //间谍案

        case "01001200" =>
          caseClass = "010320"
        //资敌案

        case "01001100" =>
          caseClass = "010400"
        //为境外窃取、刺探、收买、非法提供国家秘密、情报案

        case "01001101" =>
          caseClass = "010410"
        //为境外窃取国家秘密情报案

        case "01001103" =>
          caseClass = "010420"
        //为境外刺探国家秘密情报案

        case "01001105" =>
          caseClass = "010430"
        //为境外收买国家秘密情报案

        case "01001107" =>
          caseClass = "010440"
        //为境外非法提供国家秘密情报案

        case "02000000" =>
          caseClass = "020000"
        //危害公共安全案

        case "02000500" =>
          caseClass = "020100"
        //以危险方法危害公共安全案

        case "02000100" =>
          caseClass = "020101"
        //放火案

        case "02000200" =>
          caseClass = "020102"
        //决水案

        case "02000300" =>
          caseClass = "020103"
        //爆炸案

        case "02000400" =>
          caseClass = "020120"
        //投放危险物质案

        case "02000600" =>
          caseClass = "020111"
        //失火案

        case "02000700" =>
          caseClass = "020112"
        //过失决水案

        case "02000800" =>
          caseClass = "020113"
        //过失爆炸案

        case "02000900" =>
          caseClass = "020121"
        //过失投放危险物质案

        case "02001000" =>
          caseClass = "020119"
        //过失以危险方法危害公共安全案

        case "02001100" =>
          caseClass = "020201"
        //破坏交通工具案

        case "02001200" =>
          caseClass = "020202"
        //破坏交通设施案

        case "02001300" =>
          caseClass = "020203"
        //破坏电力设备案

        case "02001400" =>
          caseClass = "020205"
        //破坏易燃易爆设备案

        case "02002401" =>
          caseClass = "020206"
        //破坏广播电视设施案

        case "02002402" =>
          caseClass = "020207"
        //破坏公用电信设施案

        case "02001500" =>
          caseClass = "020221"
        //过失损坏交通工具案

        case "02001600" =>
          caseClass = "020222"
        //过失损坏交通设施案

        case "02001700" =>
          caseClass = "020223"
        //过失损坏电力设备案

        case "02001800" =>
          caseClass = "020225"
        //过失损坏易燃易爆设备案

        case "02002501" =>
          caseClass = "020226"
        //过失损坏广播电视设施案

        case "02002502" =>
          caseClass = "020227"
        //过失损坏公用电信设施案

        case "02001900" =>
          caseClass = "020301"
        //组织、领导参加恐怖组织案

        case "02002100" =>
          caseClass = "020311"
        //劫持航空器案

        case "02002201" =>
          caseClass = "020312"
        //劫持船只案

        case "02002202" =>
          caseClass = "020313"
        //劫持汽车案

        case "02002000" =>
          caseClass = "020314"
        //资助恐怖活动案

        case "02002300" =>
          caseClass = "020331"
        //暴力危及飞行安全案

        case "02002600" =>
          caseClass = "020400"
        //非法制造、买卖、运输、邮寄、储存枪支、弹药、爆炸物案

        case "02002601" =>
          caseClass = "020401"
        //非法制造枪支弹药案

        case "02002603" =>
          caseClass = "020406"
        //非法制造爆炸物案

        case "02002604" =>
          caseClass = "020402"
        //非法买卖枪支弹药案

        case "02002606" =>
          caseClass = "020407"
        //非法买卖爆炸物案

        case "02002607" =>
          caseClass = "020403"
        //非法运输枪支弹药案

        case "02002609" =>
          caseClass = "020408"
        //非法运输爆炸物案

        case "02002610" =>
          caseClass = "020404"
        //非法邮寄枪支弹药案

        case "02002612" =>
          caseClass = "020409"
        //非法邮寄爆炸物案

        case "02002613" =>
          caseClass = "020405"
        //非法储存枪支弹药案

        case "02002615" =>
          caseClass = "020410"
        //非法储存爆炸物案

        case "02002700" =>
          caseClass = "021000"
        //非法制造、买卖、运输、储存危险物质案

        case "02002701" =>
          caseClass = "021001"
        //非法制造危险物质案

        case "02002702" =>
          caseClass = "021002"
        //非法买卖危险物质案

        case "02002703" =>
          caseClass = "021003"
        //非法运输危险物质案

        case "02002704" =>
          caseClass = "021004"
        //非法储存危险物质案

        case "02002800" =>
          caseClass = "020500"
        //违反枪支弹药管理案

        case "02002801" =>
          caseClass = "020501"
        //违规制造枪支案

        case "02002802" =>
          caseClass = "020502"
        //违规销售枪支案

        case "02002900" =>
          caseClass = "020900"
        //盗窃、抢夺枪支、弹药、爆炸物、危险物质案

        case "02002901" =>
          caseClass = "020911"
        //盗窃枪支案

        case "02002903" =>
          caseClass = "020914"
        //盗窃爆炸物案

        case "02002904" =>
          caseClass = "020927"
        //盗窃危险物质案

        case "02002905" =>
          caseClass = "020920"
        //抢夺枪支、弹药、爆炸物案

        case "02002907" =>
          caseClass = "020924"
        //抢夺爆炸物案

        case "02002908" =>
          caseClass = "020928"
        //抢夺危险物质案

        case "02003000" =>
          caseClass = "020800"
        //抢劫枪支、弹药、爆炸物、危险物质案

        case "02003001" =>
          caseClass = "020810"
        //抢劫枪支、弹药案

        case "02003003" =>
          caseClass = "020820"
        //抢劫爆炸物案

        case "02003004" =>
          caseClass = "020830"
        //抢劫危险物质案

        case "02003100" =>
          caseClass = "020511"
        //非法持有枪支、弹药案

        case "02003103" =>
          caseClass = "020512"
        //非法私藏枪支、弹药案

        case "02003201" =>
          caseClass = "020522"
        //非法出租枪支案

        case "02003202" =>
          caseClass = "020521"
        //非法出借枪支案

        case "02003300" =>
          caseClass = "020531"
        //丢失枪支不报案

        case "02003400" =>
          caseClass = "020541"
        //非法携带枪支、弹药危及公共安全案

        case "02003403" =>
          caseClass = "020601"
        //非法携带管制刀具危及公共安全案

        case "02003404" =>
          caseClass = "020602"
        //非法携带危险物品危及公共安全案

        case "02003500" =>
          caseClass = "020701"
        //重大飞行事故案

        case "02003600" =>
          caseClass = "020702"
        //铁路运行安全事故案

        case "02003700" =>
          caseClass = "020703"
        //交通肇事案

        case "02003900" =>
          caseClass = "020700"
        //重大责任事故案

        case "02004100" =>
          caseClass = "020711"
        //重大劳动安全事故案

        case "02004300" =>
          caseClass = "020712"
        //危险物品肇事案

        case "02004400" =>
          caseClass = "020713"
        //工程重大安全事故案

        case "02004500" =>
          caseClass = "020714"
        //教育设施重大安全事故案

        case "02004600" =>
          caseClass = "020715"
        //消防责任事故案

        case "02003800" =>
          caseClass = "020716"
        //危险驾驶案

        case "02004000" =>
          caseClass = "020717"
        //强令违章冒险作业案

        case "02004200" =>
          caseClass = "020718"
        //大型群众性活动重大安全事故案

        case "02004701" =>
          caseClass = "020719"
        //不报安全事故案

        case "02004702" =>
          caseClass = "020720"
        //谎报安全事故案

        case "03000000" =>
          caseClass = "030000"
        //破坏社会主义市场经济秩序案

        case "03010000" =>
          caseClass = "030100"
        //生产、销售伪劣商品案

        case "03010100" =>
          caseClass = "030101"
        //生产、销售伪劣产品案

        case "03010200" =>
          caseClass = "030102"
        //生产销售假药案

        case "03010300" =>
          caseClass = "030103"
        //生产、销售劣药案

        case "03010400" =>
          caseClass = "030131"
        //生产、销售不符合安全标准的食品案

        case "03010500" =>
          caseClass = "030132"
        //生产、销售有毒有害食品案

        case "03010600" =>
          caseClass = "030141"
        //生产、销售不符合标准的医用器材案

        case "03010700" =>
          caseClass = "030151"
        //生产、销售不符合安全标准的产品案

        case "03010801" =>
          caseClass = "030105"
        //生产、销售伪劣农药案

        case "03010802" =>
          caseClass = "030104"
        //生产、销售伪劣兽药案

        case "03010803" =>
          caseClass = "030111"
        //生产、销售伪劣化肥案

        case "03010804" =>
          caseClass = "030121"
        //生产、销售伪劣种子案

        case "03010900" =>
          caseClass = "030161"
        //生产、销售不符合卫生标准的化妆品案

        case "03020000" =>
          caseClass = "030200"
        //走私案

        case "03020100" =>
          caseClass = "030201"
        //走私武器、弹药案

        case "03020200" =>
          caseClass = "030210"
        //走私核材料案

        case "03020300" =>
          caseClass = "030230"
        //走私假币案

        case "03020400" =>
          caseClass = "030220"
        //走私文物案

        case "03020500" =>
          caseClass = "030240"
        //走私贵重金属案

        case "03020600" =>
          caseClass = "030250"
        //走私珍贵动物、珍贵动物制品案

        case "03020700" =>
          caseClass = "030291"
        //走私国家禁止进出口的货物、物品案

        case "03020800" =>
          caseClass = "030270"
        //走私淫秽物品

        case "03020900" =>
          caseClass = "030290"
        //走私废物案

        case "03021000" =>
          caseClass = "030280"
        //走私普通货物、物品案

        case "03030000" =>
          caseClass = "030300"
        //妨害对公司、企业的管理秩序案

        case "03030100" =>
          caseClass = "030301"
        //虚报注册资本案

        case "03030201" =>
          caseClass = "030311"
        //虚假出资案

        case "03030202" =>
          caseClass = "030312"
        //抽逃出资案

        case "03030301" =>
          caseClass = "030321"
        //欺诈发行股票案

        case "03030302" =>
          caseClass = "030322"
        //欺诈发行债券案

        case "03030400" =>
          caseClass = "030375"
        //违规披露露重要信息案

        case "03030500" =>
          caseClass = "030332"
        //妨害清算案

        case "03030601" =>
          caseClass = "030378"
        //隐匿会计凭证、会计账簿、财务会计报告案

        case "03030602" =>
          caseClass = "030379"
        //故意销毁会计凭证、会计账簿、财务会计报告案

        case "03030700" =>
          caseClass = "030377"
        //虚假破产案

        case "03030800" =>
          caseClass = "030380"
        //非国家工作人员受贿案

        case "03030900" =>
          caseClass = "030381"
        //对非国家工作人员行贿案

        case "03031000" =>
          caseClass = "030382"
        //对外国公职人员、国际公共组织官员行贿案

        case "03031100" =>
          caseClass = "030351"
        //非法经营同类营业案

        case "03031200" =>
          caseClass = "030352"
        //为亲友非法牟利案

        case "03031300" =>
          caseClass = "030361"
        //签订、履行合同失职被骗案

        case "03031400" =>
          caseClass = "030383"
        //国有公司、企业、事业单位人员失职案

        case "03031500" =>
          caseClass = "030384"
        //国有公司、企业、事业单位人员滥用职权案

        case "03031600" =>
          caseClass = "030373"
        //徇私舞弊低价折股国有资产案

        case "03031602" =>
          caseClass = "030374"
        //徇私舞弊低价出售国有资产案

        case "03031700" =>
          caseClass = "030385"
        //背信损害上市公司利益案

        case "03040000" =>
          caseClass = "030400"
        //破坏金融管理秩序案

        case "03040100" =>
          caseClass = "030401"
        //伪造货币案

        case "03040201" =>
          caseClass = "030411"
        //出售假币案

        case "03040202" =>
          caseClass = "030415"
        //购买假币案

        case "03040203" =>
          caseClass = "030420"
        //运输假币案

        case "03040300" =>
          caseClass = "030421"
        //金融工作人员购买假币、以假币换取货币案

        case "03040400" =>
          caseClass = "030440"
        //持有、使用假币案

        case "03040500" =>
          caseClass = "030441"
        //变造货币案

        case "03040700" =>
          caseClass = "030447"
        //伪造、变造、转让金融机构经营许可证、批准文件案

        case "03040701" =>
          caseClass = "030451"
        //擅自设立金融机构案

        case "03040703" =>
          caseClass = "030452"
        //转让金融机构许可证案

        case "03040800" =>
          caseClass = "030453"
        //高利转贷案

        case "03040901" =>
          caseClass = "030479"
        //骗取贷款案

        case "03040902" =>
          caseClass = "030480"
        //骗取票据承兑案

        case "03040903" =>
          caseClass = "030481"
        //骗取金融票证案

        case "03041000" =>
          caseClass = "030454"
        //非法吸收公众存款案

        case "03041100" =>
          caseClass = "030446"
        //伪造、变造金融票证案

        case "03041200" =>
          caseClass = "030482"
        //妨害信用卡管理案

        case "03041301" =>
          caseClass = "030483"
        //窃取信用卡信息案

        case "03041302" =>
          caseClass = "030484"
        //收买信用卡信息案

        case "03041303" =>
          caseClass = "030485"
        //非法提供信用卡信息案

        case "03041400" =>
          caseClass = "030443"
        //伪造、变造国家有价证券案

        case "03041501" =>
          caseClass = "030444"
        //伪造、变造股票案

        case "03041503" =>
          caseClass = "030445"
        //伪造、变造公司企业债券案

        case "03041600" =>
          caseClass = "030455"
        //擅自发行股票、公司、企业债券案

        case "03041700" =>
          caseClass = "030456"
        //内幕交易、泄露内幕信息案

        case "03041800" =>
          caseClass = "030487"
        //利用未公开信息交易案

        case "03041900" =>
          caseClass = "030457"
        //编造并传播证券、期货交易虚假信息案

        case "03042000" =>
          caseClass = "030458"
        //诱骗投资者买卖证券、期货合约案

        case "03042100" =>
          caseClass = "030459"
        //操纵证券、期货市场案

        case "03042200" =>
          caseClass = "030489"
        //背信运用受托财产案

        case "03042300" =>
          caseClass = "030490"
        //违法运用资金案

        case "03042400" =>
          caseClass = "030462"
        //违法发放贷款案

        case "03042500" =>
          caseClass = "030491"
        //吸收客户资金不入账案

        case "03042600" =>
          caseClass = "030492"
        //违规出具金融票证案

        case "03042700" =>
          caseClass = "030471"
        //对违法票据承兑、付款、保证案

        case "03042800" =>
          caseClass = "030474"
        //逃汇案

        case "03042900" =>
          caseClass = "030477"
        //洗钱案

        case "03043000" =>
          caseClass = "030476"
        //骗购外汇案

        case "03050000" =>
          caseClass = "030500"
        //金融诈骗案

        case "03050100" =>
          caseClass = "030510"
        //集资诈骗案

        case "03050200" =>
          caseClass = "030520"
        //贷款诈骗案

        case "03050300" =>
          caseClass = "030530"
        //票据诈骗案

        case "03050400" =>
          caseClass = "030540"
        //金融凭证诈骗案

        case "03050500" =>
          caseClass = "030550"
        //信用证诈骗案

        case "03050600" =>
          caseClass = "030560"
        //信用卡诈骗案

        case "03050700" =>
          caseClass = "030570"
        //有价证券诈骗案

        case "03050800" =>
          caseClass = "030580"
        //保险诈骗案

        case "03060000" =>
          caseClass = "030600"
        //危害税收征管案

        case "03060100" =>
          caseClass = "030601"
        //逃税案

        case "03060200" =>
          caseClass = "030602"
        //抗税案

        case "03060300" =>
          caseClass = "030603"
        //逃避追缴欠税案

        case "03060400" =>
          caseClass = "030604"
        //骗取出口退税案

        case "03060500" =>
          caseClass = "030611"
        //虚开增值税专用发票用于骗取出口退税、抵扣税款发票案

        case "03060600" =>
          caseClass = "030664"
        //虚开发票案

        case "03060701" =>
          caseClass = "030612"
        //伪造增值税专用发票案

        case "03060702" =>
          caseClass = "030613"
        //出售伪造的增值税专用发票案

        case "03060800" =>
          caseClass = "030621"
        //非法出售增值税专用发票案

        case "03060901" =>
          caseClass = "030622"
        //非法购买增值税专用发票案

        case "03060902" =>
          caseClass = "030623"
        //购买伪造的增值税专用发票案

        case "03061001" =>
          caseClass = "030631"
        //非法制造用于骗取出口退税、抵扣税款发票案

        case "03061002" =>
          caseClass = "030632"
        //出售非法制造的用于骗取出口退税、抵扣税款发票案

        case "03061101" =>
          caseClass = "030633"
        //非法制造发票案

        case "03061102" =>
          caseClass = "030641"
        //出售非法制造的发票案

        case "03061200" =>
          caseClass = "030651"
        //非法出售用于骗取出口退税、抵扣税款发票案

        case "03061300" =>
          caseClass = "030661"
        //非法出售发票案

        case "03061400" =>
          caseClass = "030665"
        //持有伪造的发票案

        case "03070000" =>
          caseClass = "030700"
        //侵犯知识产权案

        case "03070100" =>
          caseClass = "030701"
        //假冒注册商标案

        case "03070200" =>
          caseClass = "030710"
        //销售假冒注册商标的商品案

        case "03070301" =>
          caseClass = "030720"
        //非法制造注册商标标识案

        case "03070302" =>
          caseClass = "030740"
        //销售非法制造的注册商标标识案

        case "03070400" =>
          caseClass = "030750"
        //假冒专利案

        case "03070500" =>
          caseClass = "030760"
        //侵犯著作权案

        case "03070600" =>
          caseClass = "030770"
        //销售侵权复制品案

        case "03070700" =>
          caseClass = "030780"
        //侵犯商业秘密案

        case "03080000" =>
          caseClass = "030800"
        //扰乱市场秩序案

        case "03080101" =>
          caseClass = "030801"
        //损害商业信誉案

        case "03080102" =>
          caseClass = "030802"
        //损害商品声誉案

        case "03080200" =>
          caseClass = "030803"
        //虚假广告案

        case "03080300" =>
          caseClass = "030804"
        //串通投标案

        case "03080400" =>
          caseClass = "030805"
        //合同诈骗案

        case "03080500" =>
          caseClass = "030871"
        //组织领导传销活动案

        case "03080600" =>
          caseClass = "030806"
        //非法经营案

        case "03080602" =>
          caseClass = "030730"
        //非法出版物案

        case "03080613" =>
          caseClass = "030478"
        //非法买卖外汇案

        case "03080700" =>
          caseClass = "030807"
        //强迫交易案

        case "03080800" =>
          caseClass = "030819"
        //伪造倒卖伪造的有价票证案

        case "03080801" =>
          caseClass = "030811"
        //伪造车票案

        case "03080802" =>
          caseClass = "030829"
        //倒卖伪造的有价票证案

        case "03080901" =>
          caseClass = "030831"
        //倒卖车票案

        case "03080902" =>
          caseClass = "030832"
        //倒卖船票案

        case "03081001" =>
          caseClass = "030841"
        //非法转让土地使用权案

        case "03081002" =>
          caseClass = "030842"
        //非法倒卖土地使用权案

        case "03081100" =>
          caseClass = "030851"
        //提供虚假证明文件案

        case "03081200" =>
          caseClass = "030852"
        //出具证明文件重大失实案

        case "03081300" =>
          caseClass = "030861"
        //逃避商检案

        case "04000000" =>
          caseClass = "040000"
        //侵犯公民人身权利、民主权利案

        case "04000100" =>
          caseClass = "040101"
        //故意杀人案

        case "04000200" =>
          caseClass = "040102"
        //过失致人死亡案

        case "04000300" =>
          caseClass = "040103"
        //故意伤害案

        case "04000400" =>
          caseClass = "040500"
        //组织出卖人体器官案

        case "04000500" =>
          caseClass = "040104"
        //过失致人重伤案

        case "04000600" =>
          caseClass = "040105"
        //强奸案

        case "04000700" =>
          caseClass = "040107"
        //强制猥亵、侮辱妇女案

        case "04000800" =>
          caseClass = "040108"
        //猥亵儿童案

        case "04000900" =>
          caseClass = "040109"
        //非法拘禁案

        case "04001000" =>
          caseClass = "040110"
        //绑架案

        case "04001100" =>
          caseClass = "040112"
        //拐卖妇女、儿童案

        case "04001200" =>
          caseClass = "040113"
        //收买被拐卖的妇女儿童案

        case "04001300" =>
          caseClass = "040114"
        //聚众阻碍解救被收买的妇女、儿童案

        case "04001400" =>
          caseClass = "040119"
        //诬告陷害案

        case "04001500" =>
          caseClass = "040116"
        //强迫劳动案

        case "04001600" =>
          caseClass = "040600"
        //雇用童工从事危重劳动案

        case "04001700" =>
          caseClass = "040117"
        //非法搜查案

        case "04001800" =>
          caseClass = "040118"
        //非法侵入住宅案

        case "04001900" =>
          caseClass = "040120"
        //侮辱案

        case "04002000" =>
          caseClass = "040121"
        //诽谤案

        case "04002100" =>
          caseClass = "040122"
        //刑讯逼供案

        case "04002200" =>
          caseClass = "040123"
        //暴力取证案

        case "04002300" =>
          caseClass = "040124"
        //虐待被监管人案

        case "04002400" =>
          caseClass = "040310"
        //煽动民族仇恨、民族歧视案

        case "04002500" =>
          caseClass = "040320"
        //出版歧视、侮辱少数民族作品案

        case "04002600" =>
          caseClass = "040330"
        //非法剥夺公民宗教信仰自由案

        case "04002700" =>
          caseClass = "040340"
        //侵犯少数民族风俗习惯案

        case "04002800" =>
          caseClass = "040210"
        //侵犯通信自由案

        case "04002901" =>
          caseClass = "040220"
        //私自开拆邮件、电报案

        case "04002903" =>
          caseClass = "040230"
        //私自隐匿邮件、电报案

        case "04002905" =>
          caseClass = "040240"
        //私自毁弃邮件、电报案

        case "04003000" =>
          caseClass = "040700"
        //出售、非法提供公民个人信息案

        case "04003100" =>
          caseClass = "040800"
        //非法获取公民个人信息案

        case "04003200" =>
          caseClass = "040250"
        //报复陷害案

        case "04003301" =>
          caseClass = "040260"
        //打击报复会计人员案

        case "04003302" =>
          caseClass = "040270"
        //打击报复统计人员案

        case "04003400" =>
          caseClass = "040280"
        //破坏选举案

        case "04003500" =>
          caseClass = "040410"
        //暴力干涉婚姻自由案

        case "04003600" =>
          caseClass = "040420"
        //重婚案

        case "04003700" =>
          caseClass = "040430"
        //破坏军婚案

        case "04003800" =>
          caseClass = "040440"
        //虐待案

        case "04003900" =>
          caseClass = "040450"
        //遗弃案

        case "04004000" =>
          caseClass = "040460"
        //拐骗儿童案

        case "04004100" =>
          caseClass = "040900"
        //组织残疾人、儿童乞讨案

        case "04004200" =>
          caseClass = "041000"
        //组织未成年人进行违反治安管理活动案

        case "05000000" =>
          caseClass = "050000"
        //侵犯财产案

        case "05000100" =>
          caseClass = "050100"
        //抢劫案

        case "05000200" =>
          caseClass = "050200"
        //盗窃案

        case "05000300" =>
          caseClass = "050300"
        //诈骗案

        case "05000400" =>
          caseClass = "050400"
        //抢夺案

        case "05000600" =>
          caseClass = "050500"
        //侵占案

        case "05000700" =>
          caseClass = "050600"
        //职务侵占案

        case "05000800" =>
          caseClass = "050710"
        //挪用资金案

        case "05000900" =>
          caseClass = "050700"
        //挪用特定款物案

        case "05001000" =>
          caseClass = "050800"
        //敲诈勒索案

        case "05001100" =>
          caseClass = "050900"
        //故意毁坏财物案

        case "05001200" =>
          caseClass = "051000"
        //破坏生产经营案

        case "05000500" =>
          caseClass = "051100"
        //聚众哄抢案

        case "05001300" =>
          caseClass = "051200"
        //拒不支付劳动报酬案

        case "06000000" =>
          caseClass = "060000"
        //妨害社会管理案

        case "06010000" =>
          caseClass = "060100"
        //扰乱公共秩序案

        case "06010100" =>
          caseClass = "060101"
        //妨害公务案

        case "06010200" =>
          caseClass = "060105"
        //煽动暴力抗拒法律实施案

        case "06010300" =>
          caseClass = "060106"
        //招摇撞骗案

        case "06010400" =>
          caseClass = "060109"
        //伪造、变造国家机关公文、证件、印章案

        case "06010500" =>
          caseClass = "060111"
        //盗窃、抢夺国家机关公文、证件、印章案

        case "06010507" =>
          caseClass = "060112"
        //毁灭国家机关公文、证件、印章案

        case "06010600" =>
          caseClass = "060144"
        //伪造公司、企业、事业单位、人民团体印章案

        case "06010700" =>
          caseClass = "060113"
        //伪造、变造居民身份证案

        case "06010801" =>
          caseClass = "060114"
        //非法生产警用装备案

        case "06010802" =>
          caseClass = "060115"
        //非法买卖警用装备案

        case "06010900" =>
          caseClass = "060116"
        //非法获取国家秘密案

        case "06011000" =>
          caseClass = "060117"
        //非法持有国家绝密、机密文件、资料、物品案

        case "06011100" =>
          caseClass = "060118"
        //非法生产、销售间谍专用器材案

        case "06011200" =>
          caseClass = "060119"
        //非法使用窃听专用器材案

        case "06011300" =>
          caseClass = "060121"
        //非法侵入计算机信息系统案

        case "06011400" =>
          caseClass = "060145"
        //非法获取计算机信息系统数据、非法控制计算机信息系统案

        case "06011500" =>
          caseClass = "060147"
        //提供侵入、非法控制计算机信息系统程序、工具案

        case "06011600" =>
          caseClass = "060122"
        //破坏计算机信息系统案

        case "06011700" =>
          caseClass = "060131"
        //扰乱无线电通讯管理秩序案

        case "06011800" =>
          caseClass = "060132"
        //聚众扰乱社会秩序案

        case "06011900" =>
          caseClass = "060133"
        //聚众冲击国家机关案

        case "06012001" =>
          caseClass = "060134"
        //聚众扰乱公共场所秩序案

        case "06012002" =>
          caseClass = "060135"
        //聚众扰乱交通秩序案

        case "06012100" =>
          caseClass = "060148"
        //投放虚假危险物质案

        case "06012200" =>
          caseClass = "060149"
        //编造、故意传播虚假恐怖信息案

        case "06012300" =>
          caseClass = "060136"
        //聚众斗殴案

        case "06012400" =>
          caseClass = "060137"
        //寻衅滋事案

        case "06012500" =>
          caseClass = "060139"
        //组织、领导黑社会性质组织案

        case "06012503" =>
          caseClass = "060140"
        //参加黑社会性质组织案

        case "06012600" =>
          caseClass = "060141"
        //入境发展黑社会组织案

        case "06012700" =>
          caseClass = "060142"
        //包庇、纵容黑社会性质组织案

        case "06012800" =>
          caseClass = "060138"
        //传授犯罪方法案

        case "06012900" =>
          caseClass = "060143"
        //非法集会、游行、示威案

        case "06013000" =>
          caseClass = "060146"
        //非法携带武器参加集会、游行、示威案

        case "06013100" =>
          caseClass = "060158"
        //破坏集会、游行、示威案

        case "06013200" =>
          caseClass = "060160"
        //侮辱国旗、国徽案

        case "06013300" =>
          caseClass = "060171"
        //组织和利用会道门破坏法律实施案

        case "06013400" =>
          caseClass = "060170"
        //组织和利用会道门致人死亡案

        case "06013500" =>
          caseClass = "060190"
        //聚众淫乱案

        case "06013600" =>
          caseClass = "060193"
        //引诱未成年人聚众淫乱案

        case "06013700" =>
          caseClass = "060196"
        //盗窃、侮辱尸体案

        case "06013800" =>
          caseClass = "060197"
        //赌博案

        case "06013900" =>
          caseClass = "060151"
        //开设赌场案

        case "06014000" =>
          caseClass = "060198"
        //故意延误投递邮件案

        case "06020000" =>
          caseClass = "060200"
        //妨害司法案

        case "06020100" =>
          caseClass = "060201"
        //伪证案

        case "06020300" =>
          caseClass = "060211"
        //辩护人、诉讼代理人毁灭证据、伪造证据、妨害作证案

        case "06020400" =>
          caseClass = "060214"
        //妨害作证案

        case "06020500" =>
          caseClass = "060212"
        //帮助毁灭、伪造证据案

        case "06020600" =>
          caseClass = "060215"
        //打击报复证人案

        case "06020700" =>
          caseClass = "060221"
        //扰乱法庭秩序案

        case "06020800" =>
          caseClass = "060222"
        //窝藏、包庇案

        case "06020900" =>
          caseClass = "060223"
        //拒绝提供间谍犯罪证据案

        case "06021000" =>
          caseClass = "060257"
        //掩饰、隐瞒犯罪所得、犯罪所得收益案

        case "06021100" =>
          caseClass = "060231"
        //拒不执行判决、裁定案

        case "06021200" =>
          caseClass = "060241"
        //非法处置查封、扣押、冻结的财产案

        case "06021300" =>
          caseClass = "060251"
        //破坏监管秩序案

        case "06021400" =>
          caseClass = "060252"
        //脱逃案

        case "06021500" =>
          caseClass = "060253"
        //劫夺被押解人员案

        case "06021600" =>
          caseClass = "060254"
        //组织越狱案

        case "06021700" =>
          caseClass = "060255"
        //暴动越狱案

        case "06021800" =>
          caseClass = "060256"
        //聚众持械劫狱案

        case "06030000" =>
          caseClass = "060300"
        //妨害国（边）境管理案

        case "06030100" =>
          caseClass = "060320"
        //组织他人偷越国（边）境案

        case "06030200" =>
          caseClass = "060340"
        //骗取出境证件案

        case "06030300" =>
          caseClass = "060350"
        //提供伪造、变造、的出入境证件案

        case "06030400" =>
          caseClass = "060360"
        //出售出入境证件案

        case "06030500" =>
          caseClass = "060330"
        //运送他人偷越国（边）境案

        case "06030600" =>
          caseClass = "060310"
        //偷越国（边）境案

        case "06030700" =>
          caseClass = "060370"
        //破坏界碑、界桩案

        case "06030800" =>
          caseClass = "060380"
        //破坏永久性测量标志案

        case "06040000" =>
          caseClass = "060400"
        //妨害文物管理案

        case "06040100" =>
          caseClass = "060401"
        //故意损毁文物案

        case "06040200" =>
          caseClass = "060442"
        //故意损毁名胜古迹案

        case "06040300" =>
          caseClass = "060402"
        //过失损毁文物案

        case "06040401" =>
          caseClass = "060411"
        //非法向外国人出售珍贵文物案

        case "06040402" =>
          caseClass = "060412"
        //非法向外国人赠送珍贵文物案

        case "06040500" =>
          caseClass = "060421"
        //倒卖文物案

        case "06040601" =>
          caseClass = "060422"
        //非法出售文物藏品案

        case "06040602" =>
          caseClass = "060423"
        //非法私赠文物藏品案

        case "06040701" =>
          caseClass = "060431"
        //盗掘古文化遗址案

        case "06040702" =>
          caseClass = "060441"
        //盗掘古墓葬案

        case "06040800" =>
          caseClass = "060443"
        //盗掘古人类化石、古脊椎动物化石案

        case "06040901" =>
          caseClass = "060451"
        //抢夺国有档案

        case "06040902" =>
          caseClass = "060452"
        //窃取国有档案

        case "06041001" =>
          caseClass = "060453"
        //擅自出卖国有档案

        case "06041002" =>
          caseClass = "060454"
        //擅自转让国有档案

        case "06050000" =>
          caseClass = "060500"
        //危害公共卫生案

        case "06050100" =>
          caseClass = "060501"
        //妨害传染病防治案

        case "06050200" =>
          caseClass = "060510"
        //传染病菌种、毒种扩散案

        case "06050300" =>
          caseClass = "060520"
        //妨害国境卫生检疫案

        case "06050400" =>
          caseClass = "060530"
        //非法组织卖血案

        case "06050500" =>
          caseClass = "060531"
        //强迫卖血案

        case "06050601" =>
          caseClass = "060532"
        //非法采集、供应血液案

        case "06050603" =>
          caseClass = "060533"
        //非法制作、供应血液制品案

        case "06050701" =>
          caseClass = "060534"
        //采集、供应血液事故案

        case "06050703" =>
          caseClass = "060535"
        //制作、供应血液制品事故案

        case "06050800" =>
          caseClass = "060540"
        //医疗事故案

        case "06050900" =>
          caseClass = "060550"
        //非法行医案

        case "06051000" =>
          caseClass = "060560"
        //非法进行节育手术案

        case "06051100" =>
          caseClass = "060570"
        //妨害动植物防疫、检疫案

        case "06060000" =>
          caseClass = "060600"
        //破坏环境资源保护案

        case "06060100" =>
          caseClass = "060601"
        //污染环境案

        case "06060200" =>
          caseClass = "060602"
        //非法处置进口的固体废物案

        case "06060300" =>
          caseClass = "060603"
        //擅自进口固体废物案

        case "06060400" =>
          caseClass = "060631"
        //非法捕捞水产品案

        case "06060500" =>
          caseClass = "060611"
        //非法猎捕、杀害珍贵、濒危野生动物案

        case "06060600" =>
          caseClass = "060612"
        //非法收购珍贵、濒危野生动物、珍贵、濒危野生动物制品案

        case "06060700" =>
          caseClass = "060632"
        //非法狩猎案

        case "06060800" =>
          caseClass = "060633"
        //非法占用农用地案

        case "06060900" =>
          caseClass = "060634"
        //非法采矿案

        case "06061000" =>
          caseClass = "060635"
        //破坏性采矿案

        case "06061100" =>
          caseClass = "060621"
        //非法采伐、毁坏国家重点保护植物案

        case "06061201" =>
          caseClass = "060636"
        //非法收购国家重点保护植物及其制品案

        case "06061203" =>
          caseClass = "060637"
        //非法运输国家重点保护植物及其制品案

        case "06061205" =>
          caseClass = "060638"
        //非法加工国家重点保护植物及其制品案

        case "06061207" =>
          caseClass = "060639"
        //非法出售国家重点保护植物及其制品案

        case "06061300" =>
          caseClass = "060622"
        //盗伐林木案

        case "06061400" =>
          caseClass = "060623"
        //滥伐林木案

        case "06061500" =>
          caseClass = "060624"
        //非法收购、运输盗伐、滥伐的林木案

        case "06070000" =>
          caseClass = "060700"
        //走私、贩卖、运输、制造毒品案

        case "06070101" =>
          caseClass = "060701"
        //走私毒品案

        case "06070102" =>
          caseClass = "060702"
        //贩卖毒品案

        case "06070103" =>
          caseClass = "060703"
        //运输毒品案

        case "06070104" =>
          caseClass = "060704"
        //制造毒品案

        case "06070200" =>
          caseClass = "060710"
        //非法持有毒品案

        case "06070300" =>
          caseClass = "060720"
        //包庇毒品犯罪分子案

        case "06070400" =>
          caseClass = "060721"
        //窝藏、转移、隐瞒毒品、毒赃案

        case "06070500" =>
          caseClass = "060722"
        //走私制毒物品案

        case "06070600" =>
          caseClass = "060730"
        //非法买卖制毒物品案

        case "06070700" =>
          caseClass = "060731"
        //非法种植毒品原植物案

        case "06070801" =>
          caseClass = "060732"
        //非法买卖毒品原植物种苗案

        case "06070803" =>
          caseClass = "060733"
        //非法运输毒品原植物种苗案

        case "06070805" =>
          caseClass = "060734"
        //非法携带毒品原植物种苗案

        case "06070807" =>
          caseClass = "060735"
        //非法持有毒品原植物种苗案

        case "06070900" =>
          caseClass = "060740"
        //引诱、教唆、强迫他人吸毒案

        case "06070901" =>
          caseClass = "060741"
        //引诱他人吸毒案

        case "06070902" =>
          caseClass = "060742"
        //教唆他人吸毒案

        case "06070903" =>
          caseClass = "060743"
        //欺骗他人吸毒案

        case "06071000" =>
          caseClass = "060744"
        //强迫他人吸毒案

        case "06071100" =>
          caseClass = "060745"
        //容留他人吸毒案

        case "06071201" =>
          caseClass = "060750"
        //非法提供麻醉药品案

        case "06071202" =>
          caseClass = "060760"
        //非法提供精神药品案

        case "06080000" =>
          caseClass = "060800"
        //组织、强迫、引诱、介绍卖淫案

        case "06080100" =>
          caseClass = "060801"
        //组织卖淫案

        case "06080200" =>
          caseClass = "060810"
        //强迫卖淫案

        case "06080300" =>
          caseClass = "060820"
        //协助组织卖淫案

        case "06080401" =>
          caseClass = "060830"
        //引诱卖淫案

        case "06080402" =>
          caseClass = "060850"
        //容留卖淫案

        case "06080403" =>
          caseClass = "060860"
        //介绍卖淫案

        case "06080500" =>
          caseClass = "060840"
        //引诱幼女卖淫案

        case "06080600" =>
          caseClass = "060870"
        //传播性病案

        case "06080700" =>
          caseClass = "060880"
        //嫖宿幼女案

        case "06090100" =>
          caseClass = "060900"
        //制作、复制、出版、贩卖、传播淫秽物品牟利案

        case "06090101" =>
          caseClass = "060910"
        //制作淫秽物品案

        case "06090102" =>
          caseClass = "060920"
        //复制淫秽物品案

        case "06090103" =>
          caseClass = "060930"
        //出版淫秽物品案

        case "06090104" =>
          caseClass = "060940"
        //贩卖淫秽物品案

        case "06090200" =>
          caseClass = "060960"
        //为他人提供书号出版淫秽书刊案

        case "06090300" =>
          caseClass = "060950"
        //传播淫秽物品案

        case "06090400" =>
          caseClass = "060970"
        //组织播放淫秽音像制品案

        case "06090500" =>
          caseClass = "060980"
        //组织淫秽表演案

        case "07000000" =>
          caseClass = "070000"
        //危害国防利益 案

        case "07000100" =>
          caseClass = "070100"
        //阻碍军人执行职务案

        case "07000200" =>
          caseClass = "070200"
        //阻碍军事行动案

        case "07000301" =>
          caseClass = "070300"
        //破坏武器装备案

        case "07000302" =>
          caseClass = "070400"
        //破坏军事设施案

        case "07000303" =>
          caseClass = "070500"
        //破坏军事通信案

        case "07000501" =>
          caseClass = "070600"
        //故意提供不合格武器装备案

        case "07000502" =>
          caseClass = "070700"
        //故意提供不合格军事设施案

        case "07000601" =>
          caseClass = "070800"
        //过失提供不合格武器装备案

        case "07000602" =>
          caseClass = "070900"
        //过失提供不合格军事设施案

        case "07000700" =>
          caseClass = "071000"
        //聚众冲击军事禁区案

        case "07000800" =>
          caseClass = "071100"
        //聚众扰乱军事管理区秩序案

        case "07000900" =>
          caseClass = "071200"
        //冒充军人招摇撞骗案

        case "07001000" =>
          caseClass = "071300"
        //煽动军人逃离部队案

        case "07001100" =>
          caseClass = "071400"
        //雇用逃离部队军人案

        case "07001200" =>
          caseClass = "071500"
        //接送不合格兵员案

        case "07001300" =>
          caseClass = "071600"
        //伪造、变造、买卖武装部队公文、证件、印章案

        case "07001400" =>
          caseClass = "071700"
        //盗窃部队公文、证件、印章案

        case "07001404" =>
          caseClass = "071800"
        //抢夺部队公文、证件、印章案

        case "07001500" =>
          caseClass = "072000"
        //非法生产、买卖武装部队制式服装案

        case "07001600" =>
          caseClass = "071900"
        //伪造、盗窃、买卖、非法提供、非法使用武装部队专用标志案

        case "07001700" =>
          caseClass = "072100"
        //战时拒绝、逃避征召案

        case "07001800" =>
          caseClass = "072300"
        //战时拒绝、逃避服役案

        case "07001900" =>
          caseClass = "072400"
        //战时故意提供虚假敌情案

        case "07002000" =>
          caseClass = "072500"
        //战时造谣扰乱军心案

        case "07002100" =>
          caseClass = "072600"
        //战时窝藏逃离部队军人案

        case "07002201" =>
          caseClass = "072700"
        //战时拒绝军事订货案

        case "07002202" =>
          caseClass = "072800"
        //战时故意延误军事订货案

        case "07002300" =>
          caseClass = "072900"
        //战时拒绝军事征用案

        case "08000000" =>
          caseClass = "089900"
        //贪污贿赂案

        case "08000100" =>
          caseClass = "089901"
        //贪污案

        case "08000200" =>
          caseClass = "089902"
        //挪用公款案

        case "08000300" =>
          caseClass = "089903"
        //受贿案

        case "08000400" =>
          caseClass = "089904"
        //单位受贿案

        case "08000500" =>
          caseClass = "089905"
        //利用影响力受贿案

        case "08000600" =>
          caseClass = "089908"
        //行贿案

        case "08000700" =>
          caseClass = "089909"
        //对单位行贿案

        case "08000800" =>
          caseClass = "089910"
        //介绍贿赂案

        case "08000900" =>
          caseClass = "089911"
        //单位行贿案

        case "08001000" =>
          caseClass = "089912"
        //巨额财产来源不明案

        case "08001100" =>
          caseClass = "089913"
        //隐瞒境外存款案

        case "08001200" =>
          caseClass = "089914"
        //私分国有资产案

        case "08001300" =>
          caseClass = "089915"
        //私分罚没财物案

        case "09000000" =>
          caseClass = "090000"
        //渎职案

        case "09000100" =>
          caseClass = "090101"
        //滥用职权案

        case "09000200" =>
          caseClass = "090102"
        //玩忽职守案

        case "09000300" =>
          caseClass = "090103"
        //故意泄露国家秘密案

        case "09000400" =>
          caseClass = "090104"
        //过失泄露国家秘密案

        case "09000500" =>
          caseClass = "090105"
        //徇私枉法案

        case "09000600" =>
          caseClass = "090106"
        //民事、行政枉法裁判案

        case "09000700" =>
          caseClass = "090107"
        //执行判决、裁定失职案

        case "09000800" =>
          caseClass = "090108"
        //执行判决、裁定滥用职权案

        case "09000900" =>
          caseClass = "090109"
        //枉法仲裁案

        case "09001000" =>
          caseClass = "090110"
        //私放在押人员案

        case "09001100" =>
          caseClass = "090111"
        //失职致使在押人员脱逃案

        case "09001200" =>
          caseClass = "090112"
        //徇私舞弊减刑、假释、暂予监外执行案

        case "09001300" =>
          caseClass = "090113"
        //徇私舞弊不移交刑事案件案

        case "09001401" =>
          caseClass = "090114"
        //滥用管理公司职权案

        case "09001402" =>
          caseClass = "090115"
        //滥用管理证券职权案

        case "09001500" =>
          caseClass = "090116"
        //徇私舞弊不征、少征税款案

        case "09001601" =>
          caseClass = "090117"
        //徇私舞弊发售发票案

        case "09001602" =>
          caseClass = "090118"
        //徇私舞弊抵扣税款案

        case "09001603" =>
          caseClass = "090119"
        //徇私舞弊出口退税案

        case "09001700" =>
          caseClass = "090120"
        //违法提供出口退税凭证案

        case "09001801" =>
          caseClass = "090121"
        //国家机关工作人员签订合同失职被骗案

        case "09001802" =>
          caseClass = "090122"
        //国家机关工作人员履行合同失职被骗案

        case "09001900" =>
          caseClass = "090123"
        //违法发放林木采伐许可证案

        case "09002000" =>
          caseClass = "090124"
        //环境监管失职案

        case "09002100" =>
          caseClass = "090125"
        //食品监管渎职案

        case "09002200" =>
          caseClass = "090126"
        //传染病防治失职案

        case "09002300" =>
          caseClass = "090127"
        //非法批准征用、占用土地案

        case "09002400" =>
          caseClass = "090128"
        //非法低价出让国有土地使用权案

        case "09002500" =>
          caseClass = "090129"
        //放纵走私案

        case "09002600" =>
          caseClass = "090130"
        //商检徇私舞弊案

        case "09002700" =>
          caseClass = "090131"
        //商检失职案

        case "09002800" =>
          caseClass = "090132"
        //动植物检疫徇私舞弊案

        case "09002900" =>
          caseClass = "090133"
        //动植物检疫失职案

        case "09003000" =>
          caseClass = "090134"
        //放纵制售伪劣商品犯罪行为案

        case "09003100" =>
          caseClass = "090135"
        //办理偷越国（边）境人员出入境证件案

        case "09003200" =>
          caseClass = "090136"
        //放行偷越国（边）境人员案

        case "09003300" =>
          caseClass = "090137"
        //不解救被拐卖、绑架妇女、儿童案

        case "09003400" =>
          caseClass = "090138"
        //阻碍解救被拐卖、绑架妇女、儿童案

        case "09003500" =>
          caseClass = "090139"
        //帮助犯罪分子逃避处罚案

        case "09003601" =>
          caseClass = "090140"
        //招收公务员徇私舞弊案

        case "09003602" =>
          caseClass = "090141"
        //招收学生徇私舞弊案

        case "09003701" =>
          caseClass = "090142"
        //失职造成珍贵文物损毁案

        case "09003702" =>
          caseClass = "090143"
        //失职造成珍贵文物流失案

        case _ =>
          caseClass = ""

      }
    }
    caseClass
  }
  private def strIsEmpty(str: String):Boolean = {
    if (str == null || "" == str) true  else false
  }

  private def nvlString(str: String):String = {
    if (strIsEmpty(str)) "" else str
  }
}
