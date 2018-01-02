package nirvana.hall.api.internal.fpt

/**
  * Created by zqLuo
  */
object CodeConverterV70New {

  final val PLANE_FINGER = "1"
  final val SCROLL_FINGER = "0"
  /**
    * 采集信息原因代码转换
    */
  def converCollectionReason(code: String): String = {
    var collectionReason:String = ""
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
          collectionReason = code
        //若匹配不到，返回原值

      }
    }
    collectionReason
  }

  /**
    * 性别转换
    */
  def converGender(code: String): String = {
    var gender:String = ""
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
          gender = code

      }
    }
    gender
  }

  /**
    * 国籍转换
    */
  def converNativeplace(code: String): String = {
    var nativeplace:String  = ""
    if (!strIsEmpty(code)){
      code match {
        case "002" =>
          nativeplace = "002"
        //大西洋群岛

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
        //安提瓜

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
        //波斯尼亚和黑塞哥维那

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

        case "158" => //中国台湾

        case "156" =>
          nativeplace = "156"
        //中国

        case "" =>
          nativeplace = ""
        //

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

        case "203" =>
          nativeplace = "200"
        //捷克

        case "200" =>
          nativeplace = "201"
        //斯洛伐克

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

        case "268" =>
          nativeplace = "268"
        //格鲁吉亚

        case "270" =>
          nativeplace = "270"
        //冈比亚

        case "276" =>
          nativeplace = "280"
        //德意志联邦共和国

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
        //赫德岛

        case "336" =>
          nativeplace = "336"
        //梵蒂冈

        case "340" =>
          nativeplace = "340"
        //洪都拉斯

        case "344" =>
          nativeplace = "344"
        //香港

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
        //巴勒斯坦

        case "376" =>
          nativeplace = "376"
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
        //约翰斯顿岛

        case "398" =>
          nativeplace = "398"
        //哈萨克斯坦

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
        //南朝鲜

        case "414" =>
          nativeplace = "414"
        //科威持

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

        case "434" =>
          nativeplace = "434"
        //利比亚

        case "438" =>
          nativeplace = "438"
        //列支敦土登

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

        case "482" =>
          nativeplace = "482"
        //梅利利亚

        case "484" =>
          nativeplace = "484"
        //墨西哥

        case "488" =>
          nativeplace = "488"
        //中途岛

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

        case "536" =>
          nativeplace = "536"
        //中间地带

        case "540" =>
          nativeplace = "540"
        //新喀里多尼亚

        case "548" =>
          nativeplace = "548"
        //瓦努阿图

        case "554" =>
          nativeplace = "554"
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

        case "582" =>
          nativeplace = "582"
        //太平洋群岛

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
        //巴基斯坦

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
        //菲律滨

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

        case "659" =>
        case "660" =>
          nativeplace = "658"
        //圣基茨-尼维斯-安圭拉

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

        case "704" =>
          nativeplace = "704"
        //越南

        case "706" =>
          nativeplace = "706"
        //索马里

        case "710" =>
          nativeplace = "710"
        //南非(阿札尼亚)

        case "716" =>
          nativeplace = "716"
        //津巴布韦

        case "720" =>
          nativeplace = "720"
        //民主也门

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

        case "849" =>
          nativeplace = "849"
        //美属太平洋群岛

        case "850" =>
          nativeplace = "850"
        //美属维尔京群岛

        case "854" =>
          nativeplace = "854"
        //上沃尔特

        case "858" =>
          nativeplace = "858"
        //乌拉圭

        case "862" =>
          nativeplace = "862"
        //委内瑞拉

        case "872" =>
          nativeplace = "872"
        //威克岛

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

      }
    }
    nativeplace
  }

  /**
    * 民族转换
    */
  def converNation(code: String): String = {
    var nation:String = ""
    if (!strIsEmpty(code)) {
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
        //傈僳族

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
        //毛南族

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
        //德昂族

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

        case "81" =>
          nation = "81"
        //穿青人族

        case "97" =>
          nation = "97"

        case "98" =>
          nation = "98"
        //外国血统中国籍人士

        case _ =>
          nation = "97"
        //若匹配不到，归入其他

      }
    }
    nation
  }

  /**
    * 证件类型转换
    */
  def converCertificateType(code: String): String = {
    var certificateType:String = ""
    if (!strIsEmpty(code)){
      code match {
        case "111" =>
          certificateType = "111"
        //居民身份证

        case "112" =>
          certificateType = "112"
        //临时居民身份证

        case "13" =>
          certificateType = "113"
        //户口簿

        case "114" =>
          certificateType = "114"
        //中国人民解放军军官证

        case "115" =>
          certificateType = "115"
        //中国人民武装警察部队警官证

        case "116" =>
          certificateType = "116"
        //暂住证

        case "117" =>
          certificateType = "117"
        //出生医学证明

        case "121" =>
          certificateType = "121"
        //法官证

        case "123" =>
          certificateType = "123"
        //警官证

        case "125" =>
          certificateType = "125"
        //检察官证

        case "127" =>
          certificateType = "127"
        //律师证

        case "15" =>
          certificateType = "129"
        //记者证

        case "12" =>
          certificateType = "131"
        //工作证

        case "16" =>
          certificateType = "133"
        //学生证

        case "40" =>
          certificateType = "151"
        //出人证

        case "65" =>
          certificateType = "153"
        //临时出人证

        case "64" =>
          certificateType = "155"
        //住宿证

        case "25" =>
          certificateType = "157"
        //医疗证

        case "26" =>
          certificateType = "159"
        //劳保证

        case "161" =>
          certificateType = "161"
        //献血证

        case "163" =>
          certificateType = "163"
        //保险单

        case "14" =>
          certificateType = "191"
        //会员证

        case "211" =>
          certificateType = "211"
        //离休证

        case "213" =>
          certificateType = "213"
        //退休证

        case "215" =>
          certificateType = "215"
        //老年证

        case "23" =>
          certificateType = "217"
        //残疾证

        case "219" =>
          certificateType = "219"
        //结婚证

        case "221" =>
          certificateType = "221"
        //离婚证

        case "223" =>
          certificateType = "223"
        //独生子女证

        case "225" =>
          certificateType = "225"
        //毕业证书

        case "227" =>
          certificateType = "227"
        //肄业证

        case "229" =>
          certificateType = "229"
        //结业证

        case "231" =>
          certificateType = "231"
        //学位证

        case "22" =>
          certificateType = "233"
        //军人通行证

        case "21" =>
          certificateType = "291"
        //证明信

        case "24" =>
          certificateType = "311"
        //持枪证

        case "313" =>
          certificateType = "313"
        //枪证

        case "315" =>
          certificateType = "315"
        //枪支(弹药)携运许可证

        case "317" =>
          certificateType = "317"
        //砍伐证

        case "319" =>
          certificateType = "319"
        //准运证

        case "321" =>
          certificateType = "321"
        //准购证

        case "60" =>
          certificateType = "323"
        //粮油证

        case "62" =>
          certificateType = "325"
        //购煤证

        case "63" =>
          certificateType = "327"
        //购煤气证

        case "329" =>
          certificateType = "329"
        //房屋产权证

        case "331" =>
          certificateType = "331"
        //土地使用证

        case "71" =>
          certificateType = "333"
        //车辆通行证

        case "72" =>
          certificateType = "335"
        //机动车驾驶证

        case "337" =>
          certificateType = "337"
        //机动车行驶证

        case "339" =>
          certificateType = "339"
        //机动车登记证书

        case "341" =>
          certificateType = "341"
        //机动车年检合格证

        case "343" =>
          certificateType = "343"
        //春运临时检验合格证

        case "345" =>
          certificateType = "345"
        //飞机驾驶证

        case "347" =>
          certificateType = "347"
        //船舶驾驶证

        case "349" =>
          certificateType = "349"
        //船舶行驶证

        case "73" =>
          certificateType = "351"
        //自行车行驶证

        case "81" =>
          certificateType = "353"
        //汽车号牌

        case "82" =>
          certificateType = "355"
        //拖拉机牌

        case "83" =>
          certificateType = "357"
        //摩托车牌

        case "84" =>
          certificateType = "359"
        //船舶牌

        case "85" =>
          certificateType = "361"
        //三轮车牌

        case "86" =>
          certificateType = "363"
        //自行车牌

        case "391" =>
          certificateType = "391"
        //残疾人机动轮椅车牌

        case "31" =>
          certificateType = "411"
        //外交护照

        case "32" =>
          certificateType = "412"
        //公务护照

        case "33" =>
          certificateType = "413"
        //因公普通护照

        case "34" =>
          certificateType = "414"
        //普通护照

        case "48" =>
          certificateType = "415"
        //旅行证

        case "416" =>
          certificateType = "416"
        //入出境通行证

        case "52" =>
          certificateType = "417"
        //外国人出入境证

        case "46" =>
          certificateType = "418"
        //外国人旅行证

        case "35" =>
          certificateType = "419"
        //海员证

        case "420" =>
          certificateType = "420"
        //香港特别行政区护照

        case "421" =>
          certificateType = "421"
        //澳门特别行政区护照

        case "423" =>
          certificateType = "423"
        //澳门特别行政区旅行证

        case "511" =>
          certificateType = "511"
        //台湾居民来往大陆通行证

        case "512" =>
          certificateType = "512"
        //台湾居民来往大陆通行证(一次有效)

        case "42" =>
          certificateType = "513"
        //往来港澳通行证

        case "43" =>
          certificateType = "515"
        //前往港澳通行证

        case "44" =>
          certificateType = "516"
        //港澳同胞回乡证(通行卡)

        case "517" =>
          certificateType = "517"
        //大陆居民往来台湾通行证

        case "41" =>
          certificateType = "518"
        //因公往来香港澳门特别行政区通行证

        case "551" =>
          certificateType = "551"
        //华侨回国定居证

        case "552" =>
          certificateType = "552"
        //台湾居民定居证

        case "553" =>
          certificateType = "553"
        //外国人永久居留证

        case "50" =>
          certificateType = "554"
        //外国人居留证

        case "51" =>
          certificateType = "555"
        //外国人临时居留证

        case "556" =>
          certificateType = "556"
        //入籍证书

        case "557" =>
          certificateType = "557"
        //出籍证书

        case "558" =>
          certificateType = "558"
        //复籍证书

        case "611" =>
          certificateType = "611"
        //外籍船员住宿证

        case "612" =>
          certificateType = "612"
        //随船工作证

        case "620" =>
          certificateType = "620"
        //海上值勤证(红色)

        case "621" =>
          certificateType = "621"
        //海上值勤证(蓝色)

        case "631" =>
          certificateType = "631"
        //出海船民证

        case "633" =>
          certificateType = "633"
        //出海船舶户口簿

        case "634" =>
          certificateType = "634"
        //出海船舶边防登记簿

        case "635" =>
          certificateType = "635"
        //搭靠台轮许可证

        case "636" =>
          certificateType = "636"
        //台湾居民登陆证

        case "637" =>
          certificateType = "637"
        //台湾船员登陆证

        case "638" =>
          certificateType = "638"
        //外国船员登陆证

        case "639" =>
          certificateType = "639"
        //对台劳务人员登轮作业证

        case "640" =>
          certificateType = "640"
        //合资船船员登陆证

        case "641" =>
          certificateType = "641"
        //合资船船员登轮作业证

        case "642" =>
          certificateType = "642"
        //粤港澳流动渔民证

        case "643" =>
          certificateType = "643"
        //粤港澳临时流动渔民证

        case "644" =>
          certificateType = "644"
        //粤港澳流动渔船户口簿

        case "645" =>
          certificateType = "645"
        //航行港澳船舶证明书

        case "646" =>
          certificateType = "646"
        //往来港澳小型船舶查验簿

        case "650" =>
          certificateType = "650"
        //劳务人员登轮作业证

        case "49" =>
          certificateType = "711"
        //边境管理区通行证

        case "721" =>
          certificateType = "721"
        //中朝鸭绿江、图们江水文作业证

        case "722" =>
          certificateType = "722"
        //朝中鸭绿江、图们江水文作业证

        case "723" =>
          certificateType = "723"
        //中朝流筏固定代表证

        case "724" =>
          certificateType = "724"
        //朝中流筏固定代表证

        case "725" =>
          certificateType = "725"
        //中朝鸭绿江、图们江船员证

        case "726" =>
          certificateType = "726"
        //朝中鸭绿江、图们江船员证

        case "727" =>
          certificateType = "727"
        //中朝边境地区公安总代表证

        case "728" =>
          certificateType = "728"
        //朝中边境地区公安总代表证

        case "729" =>
          certificateType = "729"
        //中朝边境地区公安副总代表证

        case "730" =>
          certificateType = "730"
        //朝中边境地区公安副总代表证

        case "731" =>
          certificateType = "731"
        //中朝边境地区公安代表证

        case "732" =>
          certificateType = "732"
        //朝中边境地区公安代表证

        case "733" =>
          certificateType = "733"
        //中朝边境地区出入境通行证(甲、乙种本)

        case "734" =>
          certificateType = "734"
        //朝中边境公务通行证

        case "735" =>
          certificateType = "735"
        //朝中边境住民国境通行证

        case "736" =>
          certificateType = "736"
        //中蒙边境地区出入境通行证(甲、乙种本)

        case "737" =>
          certificateType = "737"
        //蒙中边境地区出入境通行证

        case "738" =>
          certificateType = "738"
        //中缅边境地区出入境通行证

        case "739" =>
          certificateType = "739"
        //缅甸中国边境通行证

        case "740" =>
          certificateType = "740"
        //云南省边境地区境外边民入出境证

        case "741" =>
          certificateType = "741"
        //中尼边境地区出入境通行证

        case "742" =>
          certificateType = "742"
        //尼中边境地区出入境通行证

        case "743" =>
          certificateType = "743"
        //中越边境地区出入境通行证

        case "744" =>
          certificateType = "744"
        //越中边境地区出入境通行证

        case "745" =>
          certificateType = "745"
        //中老边境地区出入境通行证

        case "746" =>
          certificateType = "746"
        //老中边境地区出入境通行证

        case "747" =>
          certificateType = "747"
        //中印边境地区出入境通行证

        case "748" =>
          certificateType = "748"
        //印中边境地区出入境通行证

        case "761" =>
          certificateType = "761"
        //深圳市过境耕作证

        case "765" =>
          certificateType = "765"
        //贸易证

        case "36" =>
          certificateType = "771"
        //铁路员工证

        case "37" =>
          certificateType = "781"
        //机组人员证

        case "99" =>
        case "45" =>
        case "47" =>
        case "27" =>
        case "28" =>
          certificateType = "990"

        case _ =>
          certificateType = code

      }
    }
    certificateType
  }

  /**
    * 指纹指位转换
    */
  def converFingerFgp(fgpCase: String, fgp: String): String = {
    var converFgp:String = ""
    if (!strIsEmpty(fgp)){
      val fgpTemp = (nvlString(fgpCase) + nvlString(fgp)).toInt
      converFgp = "%02d".format(fgpTemp)
      converFgp match {
        case "01" =>
          converFgp = "01"
          //三面捺印右手拇指

        case "02" =>
          converFgp = "02"
          //三面捺印右手食指

        case "03" =>
          converFgp = "03"
          //三面捺印右手中指

        case "04" =>
          converFgp = "04"
          //三面捺印右手环指

        case "05" =>
          converFgp = "05"
          //三面捺印右手小指

        case "06" =>
          converFgp = "06"
          //三面捺印左手拇指

        case "07" =>
          converFgp = "07"
          //三面捺印左手食指

        case "08" =>
          converFgp = "08"
          //三面捺印左手中指

        case "09" =>
          converFgp = "09"
          //三面捺印左手环指

        case "10" =>
          converFgp = "10"
          //三面捺印左手小指

        case "11" =>
          converFgp = "11"
          //平面捺印右手拇指

        case "12" =>
          converFgp = "12"
          //平面捺印右手食指

        case "13" =>
          converFgp = "13"
          //平面捺印右手中指

        case "14" =>
          converFgp = "14"
          //平面捺印右手环指

        case "15" =>
          converFgp = "15"
          //平面捺印右手小指

        case "16" =>
          converFgp = "16"
          //平面捺印左手拇指

        case "17" =>
          converFgp = "17"
          //平面捺印左手食指

        case "18" =>
          converFgp = "18"
          //平面捺印左手中指

        case "19" =>
          converFgp = "19"
          //平面捺印左手环指

        case "110" =>
          converFgp = "20"
          //平面捺印左手小指

        case "21" =>
          converFgp = "21"
          //平面右手四连指

        case "22" =>
          converFgp = "22"
          //平面左手四连指

        case "23" =>
          converFgp = "23"
          //平面左右手拇指

        case "99" =>
          converFgp = "99"
          //不确定指位

        case _ =>
          converFgp = "99"
          //若匹配不到，返回不确定指位

      }
    }
    converFgp
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
          extractMethod = code

      }
    }
    extractMethod
  }

  /**
    * 指掌纹缺失情况代码转换
    */
  def converDefect(code: String): String = {
    var defect:String = ""
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
          defect = code

      }
    }
    defect
  }

  /**
    * 指纹纹型代码转换
    */
  def converFingerPattern(code: String): String = {
    var fingerPattern:String = ""
    if (!strIsEmpty(code)){
      code match {
        case "1" =>
          fingerPattern = "1"
        //弓型纹

        case "2" =>
          fingerPattern = "2"
        //左箕型纹

        case "3" =>
          fingerPattern = "3"
        //右箕型纹

        case "4" =>
          fingerPattern = "4"
        //斗型纹

        case "5" =>
          fingerPattern = "5"
        //缺指

        case "0" =>
        case "6" =>
          fingerPattern = "6"
        //未知

        case "9" =>
          fingerPattern = "9"
        //其它
        case _ =>
          fingerPattern = code
        //匹配不到，返回原值

      }
    }
    fingerPattern
  }

  /**
    * 掌纹掌位转换
    */
  def converPalmFgp(fgp: String): String = {
    var converFgp:String = ""
    if (!strIsEmpty(fgp)){
      fgp match {
        case "11" =>
          converFgp = "31"
        //右手平面掌纹

        case "12" =>
          converFgp = "32"
        //左手平面掌纹

        case "17" =>
          converFgp = "33"
        //右手侧面掌纹

        case "18" =>
          converFgp = "34"
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

      }
    }
    fgp
  }

  /**
    * 掌纹三角位置类型代码转换
    */
  def converPositionType(code: String): String = {
    var positionType:String = ""
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
    var photoType:String = ""
    if (!strIsEmpty(code)){
      code match {
        case "1" =>
          photoType = "1"
        //正面像

        case "2" =>
          photoType = "2"
        //左侧像

        case "3" =>
          photoType = "4"
        //右侧像

        case "9" =>
          photoType = "9"

        case _ =>
          photoType = code

      }
    }
    photoType
  }

  /**
    * 乳突线颜色代码转换
    */
  def converRidgeColor(code: String): String = {
    var ridgeColor:String = ""
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
          ridgeColor = code

      }
    }
    ridgeColor
  }

  /**
    * 指掌纹比对任务类型代码转换
    */
  def converQueryType(code: String): String = {
    var queryType:String = ""
    if (!strIsEmpty(code)){
      code match {
        case "0" =>
          queryType = "0"
        //查重

        case "1" =>
          queryType = "1"
        //倒查

        case "2" =>
          queryType = "2"
        //正查

        case "3" =>
          queryType = "3"
        //串查

        case "9" =>
          queryType = "9"

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
    var caseClass:String = ""
    if (!strIsEmpty(code)){
      code match {
        case "010000" =>
        case "010100" =>
        case "019900" =>
          caseClass = "01000000"
        //危害国家安全案

        case "010110" =>
          caseClass = "01000100"
        //背叛国家案

        case "010120" =>
          caseClass = "01000200"
        //分裂国家案

        case "010130" =>
          caseClass = "01000300"
        //煽动分裂国家案

        case "010140" =>
          caseClass = "01000400"
        //武装叛乱、暴乱案

        case "010150" =>
          caseClass = "01000401"
        //武装叛乱案

        case "01000402" =>
          caseClass = "01000402"
        //武装暴乱案

        case "010160" =>
          caseClass = "01000500"
        //颠覆国家政权案

        case "010180" =>
          caseClass = "01000700"
        //资助危害国家安全犯罪活动案

        case "010190" =>
          caseClass = "01000600"
        //煽动颠覆国家政权案

        case "010200" =>
        case "010210" =>
          caseClass = "01000800"
        //投敌叛变案

        case "010220" =>
          caseClass = "01000900"
        //叛逃案

        case "010300" =>
        case "010310" =>
          caseClass = "01001000"
        //间谍案

        case "010320" =>
          caseClass = "01001200"
        //资敌案

        case "010400" =>
          caseClass = "01001100"
        //为境外窃取、刺探、收买、非法提供国家秘密、情报案

        case "010410" =>
          caseClass = "01001101"
        //为境外窃取国家秘密案

        case "01001102" =>
          caseClass = "01001102"
        //为境外窃取情报案

        case "010420" =>
          caseClass = "01001103"
        //为境外刺探国家秘密案

        case "01001104" =>
          caseClass = "01001104"
        //为境外刺探情报案

        case "010430" =>
          caseClass = "01001105"
        //为境外收买国家秘密案

        case "01001106" =>
          caseClass = "01001106"
        //为境外收买情报案

        case "010440" =>
          caseClass = "01001107"
        //为境外非法提供国家秘密案

        case "01001108" =>
          caseClass = "01001108"
        //为境外非法提供情报案

        case "020000" =>
        case "020109" =>
        case "020200" =>
        case "029900" =>
          caseClass = "02000000"
        //危害公共安全案

        case "020100" =>
          caseClass = "02000500"
        //以危险方法危害公共安全案

        case "020101" =>
          caseClass = "02000100"
        //放火案

        case "020102" =>
          caseClass = "02000200"
        //决水案

        case "020103" =>
          caseClass = "02000300"
        //爆炸案

        case "020104" =>
        case "020120" =>
          caseClass = "02000400"
        //投放危险物质案

        case "020111" =>
          caseClass = "02000600"
        //失火案

        case "020112" =>
          caseClass = "02000700"
        //过失决水案

        case "020113" =>
          caseClass = "02000800"
        //过失爆炸案

        case "020121" =>
        case "020114" =>
          caseClass = "02000900"
        //过失投放危险物质案

        case "020119" =>
          caseClass = "02001000"
        //过失以危险方法危害公共安全案

        case "020201" =>
          caseClass = "02001100"
        //破坏交通工具案

        case "020202" =>
          caseClass = "02001200"
        //破坏交通设施案

        case "020203" =>
          caseClass = "02001300"
        //破坏电力设备案

        case "020205" =>
        case "020204" =>
          caseClass = "02001400"
        //破坏易燃易爆设备案

        case "02002400" =>
          caseClass = "02002400"
        //破坏广播电视设施、公用电信设施案

        case "020206" =>
          caseClass = "02002401"
        //破坏广播电视设施案

        case "020207" =>
          caseClass = "02002402"
        //破坏公用电信设施案

        case "020221" =>
          caseClass = "02001500"
        //过失损坏交通工具案

        case "020222" =>
          caseClass = "02001600"
        //过失损坏交通设施案

        case "020223" =>
          caseClass = "02001700"
        //过失损坏电力设备案

        case "020224" =>
        case "020225" =>
          caseClass = "02001800"
        //过失损坏易燃易爆设备案

        case "02002500" =>
          caseClass = "02002500"
        //过失损坏广播电视设施、公用电信设施案

        case "020226" =>
          caseClass = "02002501"
        //过失损坏广播电视设施案

        case "020227" =>
          caseClass = "02002502"
        //过失损坏公用电信设施案

        case "020300" =>
        case "020301" =>
          caseClass = "02001900"
        //组织、领导、参加恐怖组织案

        case "02001901" =>
          caseClass = "02001901"
        //组织恐怖组织案

        case "02001902" =>
          caseClass = "02001902"
        //领导恐怖组织案

        case "02001903" =>
          caseClass = "02001903"
        //参加恐怖组织案

        case "020311" =>
          caseClass = "02002100"
        //劫持航空器案

        case "02002200" =>
          caseClass = "02002200"
        //劫持船只、汽车案

        case "020312" =>
          caseClass = "02002201"
        //劫持船只案

        case "020313" =>
          caseClass = "02002202"
        //劫持汽车案

        case "020314" =>
          caseClass = "02002000"
        //资助恐怖活动案

        case "020331" =>
          caseClass = "02002300"
        //暴力危及飞行安全案

        case "020400" =>
          caseClass = "02002600"
        //非法制造、买卖、运输、邮寄、储存枪支、弹药、爆炸物案

        case "020401" =>
          caseClass = "02002601"
        //非法制造枪支案

        case "02002602" =>
          caseClass = "02002602"
        //非法制造弹药案

        case "020406" =>
          caseClass = "02002603"
        //非法制造爆炸物案

        case "020402" =>
          caseClass = "02002604"
        //非法买卖枪支案

        case "02002605" =>
          caseClass = "02002605"
        //非法买卖弹药案

        case "020407" =>
          caseClass = "02002606"
        //非法买卖爆炸物案

        case "020403" =>
          caseClass = "02002607"
        //非法运输枪支案

        case "02002608" =>
          caseClass = "02002608"
        //非法运输弹药案

        case "020408" =>
          caseClass = "02002609"
        //非法运输爆炸物案

        case "020404" =>
          caseClass = "02002610"
        //非法邮寄枪支案

        case "02002611" =>
          caseClass = "02002611"
        //非法邮寄弹药案

        case "020409" =>
          caseClass = "02002612"
        //非法邮寄爆炸物案

        case "020405" =>
          caseClass = "02002613"
        //非法储存枪支案

        case "02002614" =>
          caseClass = "02002614"
        //非法储存弹药案

        case "020410" =>
          caseClass = "02002615"
        //非法储存爆炸物案

        case "020411" =>
        case "020412" =>
        case "021000" =>
          caseClass = "02002700"
        //非法制造、买卖、运输、储存危险物质案

        case "021001" =>
          caseClass = "02002701"
        //非法制造危险物质案

        case "021002" =>
          caseClass = "02002702"
        //非法买卖危险物质案

        case "021003" =>
          caseClass = "02002703"
        //非法运输危险物质案

        case "021004" =>
          caseClass = "02002704"
        //非法储存危险物质案

        case "020500" =>
          caseClass = "02002800"
        //违规制造、销售枪支案

        case "020501" =>
          caseClass = "02002801"
        //违规制造枪支案

        case "020502" =>
          caseClass = "02002802"
        //违规销售枪支案

        case "020910" =>
        case "020900" =>
          caseClass = "02002900"
        //盗窃、抢夺枪支、弹药、爆炸物、危险物质案

        case "020912" =>
        case "020913" =>
        case "020911" =>
          caseClass = "02002901"
        //盗窃枪支案

        case "02002902" =>
          caseClass = "02002902"
        //盗窃弹药案

        case "020915" =>
        case "020916" =>
        case "020914" =>
          caseClass = "02002903"
        //盗窃爆炸物案

        case "020927" =>
          caseClass = "02002904"
        //盗窃危险物质案

        case "020921" =>
        case "020922" =>
        case "020923" =>
        case "020920" =>
          caseClass = "02002905"
        //抢夺枪支案

        case "02002906" =>
          caseClass = "02002906"
        //抢夺弹药案

        case "020925" =>
        case "020926" =>
        case "020924" =>
          caseClass = "02002907"
        //抢夺爆炸物案

        case "020928" =>
          caseClass = "02002908"
        //抢夺危险物质案

        case "020800" =>
          caseClass = "02003000"
        //抢劫枪支、弹药、爆炸物、危险物质案

        case "020811" =>
        case "020812" =>
        case "020810" =>
          caseClass = "02003001"
        //抢劫枪支案

        case "02003002" =>
          caseClass = "02003002"
        //抢劫弹药案

        case "020821" =>
        case "020822" =>
        case "020820" =>
          caseClass = "02003003"
        //抢劫爆炸物案

        case "020830" =>
          caseClass = "02003004"
        //抢劫危险物质案

        case "020511" =>
          caseClass = "02003100"
        //非法持有、私藏枪支、弹药案

        case "02003101" =>
          caseClass = "02003101"
        //非法持有枪支案

        case "02003102" =>
          caseClass = "02003102"
        //非法持有弹药案

        case "020512" =>
          caseClass = "02003103"
        //私藏枪支案

        case "02003104" =>
          caseClass = "02003104"
        //私藏弹药案

        case "02003200" =>
          caseClass = "02003200"
        //非法出租、出借枪支案

        case "020522" =>
          caseClass = "02003201"
        //非法出租枪支案

        case "020521" =>
          caseClass = "02003202"
        //非法出借枪支案

        case "020531" =>
          caseClass = "02003300"
        //丢失枪支不报案

        case "020541" =>
          caseClass = "02003400"
        //非法携带枪支、弹药、管制刀具、危险物品危及公共安全案

        case "02003401" =>
          caseClass = "02003401"
        //非法携带枪支危及公共安全案

        case "02003402" =>
          caseClass = "02003402"
        //非法携带弹药危及公共安全案

        case "020601" =>
          caseClass = "02003403"
        //非法携带管制刀具危及公共安全案

        case "020600" =>
        case "020602" =>
          caseClass = "02003404"
        //非法携带危险物品危及公共安全案

        case "020701" =>
          caseClass = "02003500"
        //重大飞行事故案

        case "020702" =>
          caseClass = "02003600"
        //铁路运营安全事故案

        case "020703" =>
          caseClass = "02003700"
        //交通肇事案

        case "020700" =>
          caseClass = "02003900"
        //重大责任事故案

        case "020711" =>
          caseClass = "02004100"
        //重大劳动安全事故案

        case "020712" =>
          caseClass = "02004300"
        //危险物品肇事案

        case "020713" =>
          caseClass = "02004400"
        //工程重大安全事故案

        case "020714" =>
          caseClass = "02004500"
        //教育设施重大安全事故案

        case "020715" =>
          caseClass = "02004600"
        //消防责任事故案

        case "020716" =>
          caseClass = "02003800"
        //危险驾驶案

        case "020717" =>
          caseClass = "02004000"
        //强令违章冒险作业案

        case "020718" =>
          caseClass = "02004200"
        //大型群众性活动重大安全事故案

        case "02004700" =>
          caseClass = "02004700"
        //不报、谎报安全事故案

        case "020719" =>
          caseClass = "02004701"
        //不报安全事故案

        case "020720" =>
          caseClass = "02004702"
        //谎报安全事故案

        case "030000" =>
          caseClass = "03000000"
        //破坏社会主义市场经济秩序案

        case "030100" =>
          caseClass = "03010000"
        //生产、销售伪劣商品案

        case "030101" =>
          caseClass = "03010100"
        //生产、销售伪劣产品案

        case "03010101" =>
          caseClass = "03010101"
        //生产伪劣产品案

        case "03010102" =>
          caseClass = "03010102"
        //销售伪劣产品案

        case "030102" =>
          caseClass = "03010200"
        //生产、销售假药案

        case "03010201" =>
          caseClass = "03010201"
        //生产假药案

        case "03010202" =>
          caseClass = "03010202"
        //销售假药案

        case "030103" =>
          caseClass = "03010300"
        //生产、销售劣药案

        case "03010301" =>
          caseClass = "03010301"
        //生产劣药案

        case "03010302" =>
          caseClass = "03010302"
        //销售劣药案

        case "030131" =>
          caseClass = "03010400"
        //生产、销售不符合安全标准的食品案

        case "03010401" =>
          caseClass = "03010401"
        //生产不符合安全标准的食品案

        case "03010402" =>
          caseClass = "03010402"
        //销售不符合安全标准的食品案

        case "030132" =>
          caseClass = "03010500"
        //生产、销售有毒、有害食品案

        case "03010501" =>
          caseClass = "03010501"
        //生产有毒食品案

        case "03010502" =>
          caseClass = "03010502"
        //生产有害食品案

        case "03010503" =>
          caseClass = "03010503"
        //销售有毒食品案

        case "03010504" =>
          caseClass = "03010504"
        //销售有害食品案

        case "030141" =>
          caseClass = "03010600"
        //生产、销售不符合标准的医用器材案

        case "03010601" =>
          caseClass = "03010601"
        //生产不符合标准的医用器材案

        case "03010602" =>
          caseClass = "03010602"
        //销售不符合标准的医用器材案

        case "030151" =>
          caseClass = "03010700"
        //生产、销售不符合安全标准的产品案

        case "03010701" =>
          caseClass = "03010701"
        //生产不符合安全标准的产品案

        case "03010702" =>
          caseClass = "03010702"
        //销售不符合安全标准的产品案

        case "03010800" =>
          caseClass = "03010800"
        //生产、销售伪劣农药、兽药、化肥、种子案

        case "030105" =>
          caseClass = "03010801"
        //生产伪劣农药案

        case "030104" =>
          caseClass = "03010802"
        //生产伪劣兽药案

        case "030111" =>
          caseClass = "03010803"
        //生产伪劣化肥案

        case "030121" =>
          caseClass = "03010804"
        //生产伪劣种子案

        case "03010805" =>
          caseClass = "03010805"
        //销售伪劣农药案

        case "03010806" =>
          caseClass = "03010806"
        //销售伪劣兽药案

        case "03010807" =>
          caseClass = "03010807"
        //销售伪劣化肥案

        case "03010808" =>
          caseClass = "03010808"
        //销售伪劣种子案

        case "030161" =>
          caseClass = "03010900"
        //生产、销售不符合卫生标准的化妆品案

        case "03010901" =>
          caseClass = "03010901"
        //生产不符合卫生标准的化妆品案

        case "03010902" =>
          caseClass = "03010902"
        //销售不符合卫生标准的化妆品案

        case "030200" =>
          caseClass = "03020000"
        //走私案

        case "030201" =>
          caseClass = "03020100"
        //走私武器、弹药案

        case "03020101" =>
          caseClass = "03020101"
        //走私武器案

        case "03020102" =>
          caseClass = "03020102"
        //走私弹药案

        case "030210" =>
          caseClass = "03020200"
        //走私核材料案

        case "030230" =>
          caseClass = "03020300"
        //走私假币案

        case "030220" =>
          caseClass = "03020400"
        //走私文物案

        case "030241" =>
        case "030242" =>
        case "030240" =>
          caseClass = "03020500"
        //走私贵重金属案

        case "030250" =>
        case "030260" =>
          caseClass = "03020600"
        //走私珍贵动物、珍贵动物制品案

        case "03020601" =>
          caseClass = "03020601"
        //走私珍贵动物案

        case "03020602" =>
          caseClass = "03020602"
        //走私珍贵动物制品案

        case "030291" =>
          caseClass = "03020700"
        //走私国家禁止进出口的货物、物品案

        case "03020701" =>
          caseClass = "03020701"
        //走私国家禁止进出口的货物案

        case "03020702" =>
          caseClass = "03020702"
        //走私国家禁止进出口的物品案

        case "030270" =>
          caseClass = "03020800"
        //走私淫秽物品案

        case "030290" =>
          caseClass = "03020900"
        //走私废物案

        case "030280" =>
          caseClass = "03021000"
        //走私普通货物、物品案

        case "03021001" =>
          caseClass = "03021001"
        //走私普通货物案

        case "03021002" =>
          caseClass = "03021002"
        //走私普通物品案

        case "030300" =>
          caseClass = "03030000"
        //妨害对公司、企业的管理秩序案

        case "030301" =>
          caseClass = "03030100"
        //虚报注册资本案

        case "03030200" =>
          caseClass = "03030200"
        //虚假出资、抽逃出资案

        case "030311" =>
          caseClass = "03030201"
        //虚假出资案

        case "030312" =>
          caseClass = "03030202"
        //抽逃出资案

        case "03030300" =>
          caseClass = "03030300"
        //欺诈发行股票、债券案

        case "030321" =>
          caseClass = "03030301"
        //欺诈发行股票案

        case "030322" =>
          caseClass = "03030302"
        //欺诈发行债券案

        case "030375" =>
        case "030376" =>
          caseClass = "03030400"
        //违规披露、不披露重要信息案

        case "030332" =>
          caseClass = "03030500"
        //妨害清算案

        case "03030600" =>
          caseClass = "03030600"
        //隐匿、故意销毁会计凭证、会计帐簿、财务会计报告案

        case "030378" =>
          caseClass = "03030601"
        //隐匿会计凭证、会计帐簿、财务会计报告案

        case "030379" =>
          caseClass = "03030602"
        //故意销毁会计凭证、会计帐簿、财务会计报告案

        case "030377" =>
          caseClass = "03030700"
        //虚假破产案

        case "030380" =>
          caseClass = "03030800"
        //非国家工作人员受贿案

        case "030381" =>
          caseClass = "03030900"
        //对非国家工作人员行贿案

        case "030382" =>
          caseClass = "03031000"
        //对外国公职人员、国际公共组织官员行贿案

        case "030351" =>
          caseClass = "03031100"
        //非法经营同类营业案

        case "030352" =>
          caseClass = "03031200"
        //为亲友非法牟利案

        case "030361" =>
          caseClass = "03031300"
        //签订、履行合同失职被骗案

        case "03031301" =>
          caseClass = "03031301"
        //签订合同失职被骗案

        case "03031302" =>
          caseClass = "03031302"
        //履行合同失职被骗案

        case "030383" =>
          caseClass = "03031400"
        //国有公司、企业、事业单位人员失职案

        case "030384" =>
          caseClass = "03031500"
        //国有公司、企业、事业单位人员滥用职权案

        case "030373" =>
        case "030372" =>
          caseClass = "03031600"
        //徇私舞弊低价折股、出售国有资产案

        case "03031601" =>
          caseClass = "03031601"
        //徇私舞弊低价折股案

        case "030374" =>
          caseClass = "03031602"
        //徇私舞弊低价出售国有资产案

        case "030385" =>
          caseClass = "03031700"
        //背信损害上市公司利益案

        case "030400" =>
          caseClass = "03040000"
        //破坏金融管理秩序案

        case "030401" =>
          caseClass = "03040100"
        //伪造货币案

        case "03040200" =>
          caseClass = "03040200"
        //出售、购买、运输假币案

        case "030411" =>
          caseClass = "03040201"
        //出售假币案

        case "030415" =>
          caseClass = "03040202"
        //购买假币案

        case "030420" =>
          caseClass = "03040203"
        //运输假币案

        case "030421" =>
          caseClass = "03040300"
        //金融工作人员购买假币、以假币换取货币案

        case "03040301" =>
          caseClass = "03040301"
        //金融工作人员购买假币案

        case "03040302" =>
          caseClass = "03040302"
        //金融工作人员以假币换取货币案

        case "030440" =>
          caseClass = "03040400"
        //持有、使用假币案

        case "03040401" =>
          caseClass = "03040401"
        //持有假币案

        case "03040402" =>
          caseClass = "03040402"
        //使用假币案

        case "030441" =>
        case "030442" =>
          caseClass = "03040500"
        //变造货币案

        case "03040600" =>
          caseClass = "03040600"
        //擅自设立金融机构案

        case "030447" =>
          caseClass = "03040700"
        //伪造、变造、转让金融机构经营许可证、批准文件案

        case "030451" =>
          caseClass = "03040701"
        //伪造金融机构经营许可证、批准文件案

        case "03040702" =>
          caseClass = "03040702"
        //变造金融机构经营许可证、批准文件案

        case "030452" =>
          caseClass = "03040703"
        //转让金融机构经营许可证、批准文件案

        case "030453" =>
          caseClass = "03040800"
        //高利转贷案

        case "03040900" =>
          caseClass = "03040900"
        //骗取贷款、票据承兑、金融票证案

        case "030479" =>
          caseClass = "03040901"
        //骗取贷款案

        case "030480" =>
          caseClass = "03040902"
        //骗取票据承兑案

        case "030481" =>
          caseClass = "03040903"
        //骗取金融票证案

        case "030454" =>
          caseClass = "03041000"
        //非法吸收公众存款案

        case "030446" =>
          caseClass = "03041100"
        //伪造、变造金融票证案

        case "03041101" =>
          caseClass = "03041101"
        //伪造金融票证案

        case "03041102" =>
          caseClass = "03041102"
        //变造金融票证案

        case "030482" =>
          caseClass = "03041200"
        //妨害信用卡管理案

        case "03041300" =>
          caseClass = "03041300"
        //窃取、收买、非法提供信用卡信息案

        case "030483" =>
          caseClass = "03041301"
        //窃取信用卡信息案

        case "030484" =>
          caseClass = "03041302"
        //收买信用卡信息案

        case "030485" =>
          caseClass = "03041303"
        //非法提供信用卡信息案

        case "030443" =>
          caseClass = "03041400"
        //伪造、变造国家有价证券案

        case "03041500" =>
          caseClass = "03041500"
        //伪造、变造股票、公司、企业债券案

        case "030444" =>
          caseClass = "03041501"
        //伪造股票案

        case "03041502" =>
          caseClass = "03041502"
        //变造股票案

        case "030445" =>
          caseClass = "03041503"
        //伪造公司、企业债券案

        case "03041504" =>
          caseClass = "03041504"
        //变造公司、企业债券案

        case "030455" =>
          caseClass = "03041600"
        //擅自发行股票、公司、企业债券案

        case "03041601" =>
          caseClass = "03041601"
        //擅自发行股票案

        case "03041602" =>
          caseClass = "03041602"
        //擅自发行公司、企业债券案

        case "030456" =>
          caseClass = "03041700"
        //内幕交易、泄露内幕信息案

        case "030487" =>
          caseClass = "03041800"
        //利用未公开信息交易案

        case "030457" =>
          caseClass = "03041900"
        //编造并传播证券、期货交易虚假信息案

        case "030458" =>
          caseClass = "03042000"
        //诱骗投资者买卖证券、期货合约案

        case "03042001" =>
          caseClass = "03042001"
        //诱骗投资者买卖证券案

        case "03042002" =>
          caseClass = "03042002"
        //诱骗投资者买卖期货合约案

        case "030459" =>
          caseClass = "03042100"
        //操纵证券、期货市场案

        case "03042101" =>
          caseClass = "03042101"
        //操纵证券市场案

        case "03042102" =>
          caseClass = "03042102"
        //操纵期货市场案

        case "030489" =>
          caseClass = "03042200"
        //背信运用受托财产案

        case "030490" =>
          caseClass = "03042300"
        //违法运用资金案

        case "030463" =>
        case "030464" =>
        case "030462" =>
          caseClass = "03042400"
        //违法发放贷款案

        case "030491" =>
          caseClass = "03042500"
        //吸收客户资金不入账案

        case "030492" =>
          caseClass = "03042600"
        //违规出具金融票证案

        case "03042601" =>
          caseClass = "03042601"
        //违规出具保函案

        case "03042602" =>
          caseClass = "03042602"
        //违规出具信用证案

        case "03042603" =>
          caseClass = "03042603"
        //违规出具汇票案

        case "03042604" =>
          caseClass = "03042604"
        //违规出具本票案

        case "03042605" =>
          caseClass = "03042605"
        //违规出具支票案

        case "03042606" =>
          caseClass = "03042606"
        //违规出具存单案

        case "03042607" =>
          caseClass = "03042607"
        //违规出具资信证明案

        case "030471" =>
          caseClass = "03042700"
        //对违法票据承兑、付款、保证案

        case "03042701" =>
          caseClass = "03042701"
        //对违法票据承兑案

        case "03042702" =>
          caseClass = "03042702"
        //对违法票据付款案

        case "03042703" =>
          caseClass = "03042703"
        //对违法票据保证案

        case "030474" =>
        case "030475" =>
          caseClass = "03042800"
        //逃汇案

        case "030477" =>
          caseClass = "03042900"
        //洗钱案

        case "030476" =>
          caseClass = "03043000"
        //骗购外汇案

        case "030500" =>
          caseClass = "03050000"
        //金融诈骗案

        case "030510" =>
          caseClass = "03050100"
        //集资诈骗案

        case "030520" =>
          caseClass = "03050200"
        //贷款诈骗案

        case "030530" =>
          caseClass = "03050300"
        //票据诈骗案

        case "030540" =>
          caseClass = "03050400"
        //金融凭证诈骗案

        case "030550" =>
          caseClass = "03050500"
        //信用证诈骗案

        case "030560" =>
          caseClass = "03050600"
        //信用卡诈骗案

        case "030570" =>
          caseClass = "03050700"
        //有价证券诈骗案

        case "030580" =>
          caseClass = "03050800"
        //保险诈骗案

        case "030600" =>
          caseClass = "03060000"
        //危害税收征管案

        case "030601" =>
          caseClass = "03060100"
        //逃税案

        case "030602" =>
          caseClass = "03060200"
        //抗税案

        case "030603" =>
          caseClass = "03060300"
        //逃避追缴欠税案

        case "030604" =>
        case "030605" =>
          caseClass = "03060400"
        //骗取出口退税案

        case "030611" =>
          caseClass = "03060500"
        //虚开增值税专用发票、用于骗取出口退税、抵扣税款发票案

        case "030664" =>
          caseClass = "03060600"
        //虚开发票案

        case "03060700" =>
          caseClass = "03060700"
        //伪造、出售伪造的增值税专用发票案

        case "030612" =>
          caseClass = "03060701"
        //伪造增值税专用发票案

        case "030613" =>
          caseClass = "03060702"
        //出售伪造的增值税专用发票案

        case "030621" =>
          caseClass = "03060800"
        //非法出售增值税专用发票案

        case "03060900" =>
          caseClass = "03060900"
        //非法购买增值税专用发票、购买伪造的增值税专用发票案

        case "030622" =>
          caseClass = "03060901"
        //非法购买增值税专用发票案

        case "030623" =>
          caseClass = "03060902"
        //购买伪造的增值税专用发票案

        case "03061000" =>
          caseClass = "03061000"
        //非法制造、出售非法制造的用于骗取出口退税、抵扣税款发票案

        case "030631" =>
          caseClass = "03061001"
        //非法制造用于骗取出口退税、抵扣税款发票案

        case "030632" =>
          caseClass = "03061002"
        //出售非法制造的用于骗取出口退税、抵扣税款发票案

        case "03061100" =>
          caseClass = "03061100"
        //非法制造、出售非法制造的发票案

        case "030633" =>
          caseClass = "03061101"
        //非法制造发票案

        case "030641" =>
          caseClass = "03061102"
        //出售非法制造的发票案

        case "030651" =>
          caseClass = "03061200"
        //非法出售用于骗取出口退税、抵扣税款发票案

        case "030661" =>
          caseClass = "03061300"
        //非法出售发票案

        case "030665" =>
          caseClass = "03061400"
        //持有伪造的发票案

        case "030700" =>
          caseClass = "03070000"
        //侵犯知识产权案

        case "030701" =>
          caseClass = "03070100"
        //假冒注册商标案

        case "030710" =>
          caseClass = "03070200"
        //销售假冒注册商标的商品案

        case "03070300" =>
          caseClass = "03070300"
        //非法制造、销售非法制造的注册商标标识案

        case "030720" =>
          caseClass = "03070301"
        //非法制造注册商标标识案

        case "030740" =>
          caseClass = "03070302"
        //销售非法制造的注册商标标识案

        case "030750" =>
          caseClass = "03070400"
        //假冒专利案

        case "030760" =>
          caseClass = "03070500"
        //侵犯著作权案

        case "030770" =>
          caseClass = "03070600"
        //销售侵权复制品案

        case "030780" =>
          caseClass = "03070700"
        //侵犯商业秘密案

        case "030800" =>
          caseClass = "03080000"
        //扰乱市场秩序案

        case "03080100" =>
          caseClass = "03080100"
        //损害商业信誉、商品声誉案

        case "030801" =>
          caseClass = "03080101"
        //损害商业信誉案

        case "030802" =>
          caseClass = "03080102"
        //损害商品声誉案

        case "030803" =>
          caseClass = "03080200"
        //虚假广告案

        case "030804" =>
          caseClass = "03080300"
        //串通投标案

        case "030805" =>
          caseClass = "03080400"
        //合同诈骗案

        case "030871" =>
          caseClass = "03080500"
        //组织、领导传销活动案

        case "030806" =>
          caseClass = "03080600"
        //非法经营案

        case "03080601" =>
          caseClass = "03080601"
        //非法经营烟草制品案

        case "030730" =>
          caseClass = "03080602"
        //非法经营出版物案

        case "03080603" =>
          caseClass = "03080603"
        //非法经营药品案

        case "03080604" =>
          caseClass = "03080604"
        //非法经营彩票案

        case "03080605" =>
          caseClass = "03080605"
        //非法买卖批文、许可证、证明案

        case "03080606" =>
          caseClass = "03080606"
        //非法销售未上市公司股权案

        case "03080607" =>
          caseClass = "03080607"
        //非法经营证券投资咨询业务案

        case "03080608" =>
          caseClass = "03080608"
        //非法经营其他证券业务案

        case "03080609" =>
          caseClass = "03080609"
        //非法经营黄金期货案

        case "03080610" =>
          caseClass = "03080610"
        //非法经营外汇期货案

        case "03080611" =>
          caseClass = "03080611"
        //非法经营大宗商品期货案

        case "03080612" =>
          caseClass = "03080612"
        //非法经营保险业务案

        case "030478" =>
          caseClass = "03080613"
        //非法买卖外汇案

        case "03080614" =>
          caseClass = "03080614"
        //非法经营网络炒汇案

        case "03080615" =>
          caseClass = "03080615"
        //非法买卖黄金案

        case "03080616" =>
          caseClass = "03080616"
        //非法经营电讯业务案

        case "03080617" =>
          caseClass = "03080617"
        //非法经营食盐案

        case "03080618" =>
          caseClass = "03080618"
        //非法从事支付结算业务案

        case "03080619" =>
          caseClass = "03080619"
        //利用POS机刷卡套现案

        case "03080620" =>
          caseClass = "03080620"
        //传销案

        case "03080621" =>
          caseClass = "03080621"
        //高利放贷案

        case "03080622" =>
          caseClass = "03080622"
        //未经许可经营专营、专卖或限制买卖的其他业务、物品案

        case "03080623" =>
          caseClass = "03080623"
        //非法倒卖银行票据案

        case "03080624" =>
          caseClass = "03080624"
        //非法生产、销售盐酸克仑特罗等药品案

        case "03080625" =>
          caseClass = "03080625"
        //非法添加盐酸克仑特罗等药品或销售添加此类药品的饲料案

        case "03080626" =>
          caseClass = "03080626"
        //在灾害期间哄抬物价、牟取暴利案

        case "03080627" =>
          caseClass = "03080627"
        //擅自设立互联网上网服务场所或从事上网服务经营活动案

        case "030807" =>
          caseClass = "03080700"
        //强迫交易案

        case "030819" =>
          caseClass = "03080800"
        //伪造、倒卖伪造的有价票证案

        case "030811" =>
        case "030812" =>
        case "030813" =>
          caseClass = "03080801"
        //伪造有价票证案

        case "030821" =>
        case "030822" =>
        case "030823" =>
        case "030829" =>
          caseClass = "03080802"
        //倒卖伪造的有价票证案

        case "03080900" =>
          caseClass = "03080900"
        //倒卖车票、船票案

        case "030831" =>
          caseClass = "03080901"
        //倒卖车票案

        case "030832" =>
          caseClass = "03080902"
        //倒卖船票案

        case "03081000" =>
          caseClass = "03081000"
        //非法转让、倒卖土地使用权案

        case "030841" =>
          caseClass = "03081001"
        //非法转让土地使用权案

        case "030842" =>
          caseClass = "03081002"
        //非法倒卖土地使用权案

        case "030851" =>
        case "030460" =>
          caseClass = "03081100"
        //提供虚假证明文件案

        case "030852" =>
          caseClass = "03081200"
        //出具证明文件重大失实案

        case "039900" =>
        case "030861" =>
          caseClass = "03081300"
        //逃避商检案

        case "040100" =>
        case "040200" =>
        case "040000" =>
        case "049900" =>
          caseClass = "04000000"
        //侵犯公民人身权利、民主权利案

        case "040101" =>
          caseClass = "04000100"
        //故意杀人案

        case "040102" =>
          caseClass = "04000200"
        //过失致人死亡案

        case "040103" =>
          caseClass = "04000300"
        //故意伤害案

        case "040500" =>
          caseClass = "04000400"
        //组织出卖人体器官案

        case "040104" =>
          caseClass = "04000500"
        //过失致人重伤案

        case "040105" =>
        case "040106" =>
          caseClass = "04000600"
        //强奸案

        case "040107" =>
          caseClass = "04000700"
        //强制猥亵、侮辱妇女案

        case "04000701" =>
          caseClass = "04000701"
        //强制猥亵妇女案

        case "04000702" =>
          caseClass = "04000702"
        //强制侮辱妇女案

        case "040108" =>
          caseClass = "04000800"
        //猥亵儿童案

        case "040109" =>
          caseClass = "04000900"
        //非法拘禁案

        case "040110" =>
        case "040111" =>
          caseClass = "04001000"
        //绑架案

        case "040112" =>
          caseClass = "04001100"
        //拐卖妇女、儿童案

        case "04001101" =>
          caseClass = "04001101"
        //拐卖妇女案

        case "04001102" =>
          caseClass = "04001102"
        //拐卖儿童案

        case "040113" =>
          caseClass = "04001200"
        //收买被拐卖的妇女、儿童案

        case "04001201" =>
          caseClass = "04001201"
        //收买被拐卖的妇女案

        case "04001202" =>
          caseClass = "04001202"
        //收买被拐卖的儿童案

        case "040115" =>
        case "040114" =>
          caseClass = "04001300"
        //聚众阻碍解救被收买的妇女、儿童案

        case "04001301" =>
          caseClass = "04001301"
        //聚众阻碍解救被收买的妇女案

        case "04001302" =>
          caseClass = "04001302"
        //聚众阻碍解救被收买的儿童案

        case "040119" =>
          caseClass = "04001400"
        //诬告陷害案

        case "040116" =>
          caseClass = "04001500"
        //强迫劳动案

        case "040600" =>
          caseClass = "04001600"
        //雇用童工从事危重劳动案

        case "040117" =>
          caseClass = "04001700"
        //非法搜查案

        case "040118" =>
          caseClass = "04001800"
        //非法侵入住宅案

        case "040120" =>
          caseClass = "04001900"
        //侮辱案

        case "040121" =>
          caseClass = "04002000"
        //诽谤案

        case "040122" =>
          caseClass = "04002100"
        //刑讯逼供案

        case "040123" =>
          caseClass = "04002200"
        //暴力取证案

        case "040124" =>
          caseClass = "04002300"
        //虐待被监管人案

        case "040310" =>
          caseClass = "04002400"
        //煽动民族仇恨、民族歧视案

        case "040320" =>
          caseClass = "04002500"
        //出版歧视、侮辱少数民族作品案

        case "040300" =>
        case "040330" =>
          caseClass = "04002600"
        //非法剥夺公民宗教信仰自由案

        case "040340" =>
          caseClass = "04002700"
        //侵犯少数民族风俗习惯案

        case "040210" =>
          caseClass = "04002800"
        //侵犯通信自由案

        case "04002900" =>
          caseClass = "04002900"
        //私自开拆、隐匿、毁弃邮件、电报案

        case "040220" =>
          caseClass = "04002901"
        //私自开拆邮件案

        case "04002902" =>
          caseClass = "04002902"
        //私自开拆电报案

        case "040230" =>
          caseClass = "04002903"
        //私自隐匿邮件案

        case "04002904" =>
          caseClass = "04002904"
        //私自隐匿电报案

        case "040240" =>
          caseClass = "04002905"
        //私自毁弃邮件案

        case "04002906" =>
          caseClass = "04002906"
        //私自毁弃电报案

        case "040700" =>
          caseClass = "04003000"
        //出售、非法提供公民个人信息案

        case "04003001" =>
          caseClass = "04003001"
        //出售公民个人信息案

        case "04003002" =>
          caseClass = "04003002"
        //非法提供公民个人信息案

        case "040800" =>
          caseClass = "04003100"
        //非法获取公民个人信息案

        case "040250" =>
          caseClass = "04003200"
        //报复陷害案

        case "04003300" =>
          caseClass = "04003300"
        //打击报复会计、统计人员案

        case "040260" =>
          caseClass = "04003301"
        //打击报复会计人员案

        case "040270" =>
          caseClass = "04003302"
        //打击报复统计人员案

        case "040280" =>
          caseClass = "04003400"
        //破坏选举案

        case "040400" =>
        case "040410" =>
          caseClass = "04003500"
        //暴力干涉婚姻自由案

        case "040420" =>
          caseClass = "04003600"
        //重婚案

        case "040430" =>
          caseClass = "04003700"
        //破坏军婚案

        case "040440" =>
          caseClass = "04003800"
        //虐待案

        case "040450" =>
          caseClass = "04003900"
        //遗弃案

        case "040460" =>
          caseClass = "04004000"
        //拐骗儿童案

        case "040900" =>
          caseClass = "04004100"
        //组织残疾人、儿童乞讨案

        case "04004101" =>
          caseClass = "04004101"
        //组织残疾人乞讨案

        case "04004102" =>
          caseClass = "04004102"
        //组织儿童乞讨案

        case "041000" =>
          caseClass = "04004200"
        //组织未成年人进行违反治安管理活动案

        case "050000" =>
          caseClass = "05000000"
        //侵犯财产案

        case "050101" =>
        case "050102" =>
        case "050103" =>
        case "050110" =>
        case "050111" =>
        case "050112" =>
        case "050113" =>
        case "050120" =>
        case "050130" =>
        case "050131" =>
        case "050132" =>
        case "050140" =>
        case "050150" =>
        case "050160" =>
        case "050100" =>
          caseClass = "05000100"
        //抢劫案

        case "050201" =>
        case "050202" =>
        case "050203" =>
        case "050204" =>
        case "050210" =>
        case "050211" =>
        case "050212" =>
        case "050216" =>
        case "050220" =>
        case "050221" =>
        case "050222" =>
        case "050223" =>
        case "050224" =>
        case "050227" =>
        case "050230" =>
        case "050235" =>
        case "050236" =>
        case "050237" =>
        case "050240" =>
        case "050200" =>
          caseClass = "05000200"
        //盗窃案

        case "050300" =>
          caseClass = "05000300"
        //诈骗案

        case "050400" =>
          caseClass = "05000400"
        //抢夺案

        case "050500" =>
          caseClass = "05000600"
        //侵占案

        case "050600" =>
          caseClass = "05000700"
        //职务侵占案

        case "050720" =>
        case "050730" =>
        case "050740" =>
        case "050750" =>
        case "050710" =>
          caseClass = "05000800"
        //挪用资金案

        case "050700" =>
          caseClass = "05000900"
        //挪用特定款物案

        case "050800" =>
          caseClass = "05001000"
        //敲诈勒索案

        case "050900" =>
          caseClass = "05001100"
        //故意毁坏财物案

        case "051000" =>
          caseClass = "05001200"
        //破坏生产经营案

        case "051100" =>
          caseClass = "05000500"
        //聚众哄抢案

        case "051200" =>
          caseClass = "05001300"
        //拒不支付劳动报酬案

        case "060000" =>
          caseClass = "06000000"
        //妨害社会管理秩序案

        case "060100" =>
          caseClass = "06010000"
        //扰乱公共秩序案

        case "060102" =>
        case "060103" =>
        case "060104" =>
        case "060101" =>
          caseClass = "06010100"
        //妨害公务案

        case "060105" =>
          caseClass = "06010200"
        //煽动暴力抗拒法律实施案

        case "060107" =>
        case "060108" =>
        case "060106" =>
          caseClass = "06010300"
        //招摇撞骗案

        case "060110" =>
        case "060109" =>
          caseClass = "06010400"
        //伪造、变造、买卖国家机关公文、证件、印章案

        case "06010401" =>
          caseClass = "06010401"
        //伪造国家机关公文案

        case "06010402" =>
          caseClass = "06010402"
        //伪造国家机关证件案

        case "06010403" =>
          caseClass = "06010403"
        //伪造国家机关印章案

        case "06010404" =>
          caseClass = "06010404"
        //变造国家机关公文案

        case "06010405" =>
          caseClass = "06010405"
        //变造国家机关证件案

        case "06010406" =>
          caseClass = "06010406"
        //变造国家机关印章案

        case "06010407" =>
          caseClass = "06010407"
        //买卖国家机关公文案

        case "06010408" =>
          caseClass = "06010408"
        //买卖国家机关证件案

        case "06010409" =>
          caseClass = "06010409"
        //买卖国家机关印章案

        case "060111" =>
          caseClass = "06010500"
        //盗窃、抢夺、毁灭国家机关公文、证件、印章案

        case "06010501" =>
          caseClass = "06010501"
        //盗窃国家机关公文案

        case "06010502" =>
          caseClass = "06010502"
        //盗窃国家机关证件案

        case "06010503" =>
          caseClass = "06010503"
        //盗窃国家机关印章案

        case "06010504" =>
          caseClass = "06010504"
        //抢夺国家机关公文案

        case "06010505" =>
          caseClass = "06010505"
        //抢夺国家机关证件案

        case "06010506" =>
          caseClass = "06010506"
        //抢夺国家机关印章案

        case "060112" =>
          caseClass = "06010507"
        //毁灭国家机关公文案

        case "06010508" =>
          caseClass = "06010508"
        //毁灭国家机关证件案

        case "06010509" =>
          caseClass = "06010509"
        //毁灭国家机关印章案

        case "060144" =>
          caseClass = "06010600"
        //伪造公司、企业、事业单位、人民团体印章案

        case "06010601" =>
          caseClass = "06010601"
        //伪造公司、企业印章案

        case "06010602" =>
          caseClass = "06010602"
        //伪造事业单位印章案

        case "06010603" =>
          caseClass = "06010603"
        //伪造人民团体印章案

        case "060113" =>
          caseClass = "06010700"
        //伪造、变造居民身份证案

        case "06010701" =>
          caseClass = "06010701"
        //伪造居民身份证案

        case "06010702" =>
          caseClass = "06010702"
        //变造居民身份证案

        case "06010800" =>
          caseClass = "06010800"
        //非法生产、买卖警用装备案

        case "060114" =>
          caseClass = "06010801"
        //非法生产警用装备案

        case "060115" =>
          caseClass = "06010802"
        //非法买卖警用装备案

        case "060116" =>
          caseClass = "06010900"
        //非法获取国家秘密案

        case "060117" =>
          caseClass = "06011000"
        //非法持有国家绝密、机密文件、资料、物品案

        case "06011001" =>
          caseClass = "06011001"
        //非法持有国家绝密文件案

        case "06011002" =>
          caseClass = "06011002"
        //非法持有国家机密文件案

        case "06011003" =>
          caseClass = "06011003"
        //非法持有国家绝密资料案

        case "06011004" =>
          caseClass = "06011004"
        //非法持有国家机密资料案

        case "06011005" =>
          caseClass = "06011005"
        //非法持有国家绝密物品案

        case "06011006" =>
          caseClass = "06011006"
        //非法持有国家机密物品案

        case "060118" =>
          caseClass = "06011100"
        //非法生产、销售间谍专用器材案

        case "06011101" =>
          caseClass = "06011101"
        //非法生产间谍专用器材案

        case "06011102" =>
          caseClass = "06011102"
        //非法销售间谍专用器材案

        case "060120" =>
        case "060119" =>
          caseClass = "06011200"
        //非法使用窃听、窃照专用器材案

        case "060125" =>
        case "060126" =>
        case "060127" =>
        case "060128" =>
        case "060129" =>
        case "060130" =>
        case "060121" =>
          caseClass = "06011300"
        //非法侵入计算机信息系统案

        case "060145" =>
          caseClass = "06011400"
        //非法获取计算机信息系统数据、非法控制计算机信息系统案

        case "06011401" =>
          caseClass = "06011401"
        //非法获取计算机信息系统数据案

        case "06011402" =>
          caseClass = "06011402"
        //非法控制计算机信息系统案

        case "060147" =>
          caseClass = "06011500"
        //提供侵入、非法控制计算机信息系统程序、工具案

        case "06011501" =>
          caseClass = "06011501"
        //提供侵入计算机信息系统程序案

        case "06011502" =>
          caseClass = "06011502"
        //提供侵入计算机信息系统工具案

        case "06011503" =>
          caseClass = "06011503"
        //提供非法控制计算机信息系统程序案

        case "06011504" =>
          caseClass = "06011504"
        //提供非法控制计算机信息系统工具案

        case "060123" =>
        case "060124" =>
        case "060122" =>
          caseClass = "06011600"
        //破坏计算机信息系统案

        case "060131" =>
          caseClass = "06011700"
        //扰乱无线电通讯管理秩序案

        case "060132" =>
          caseClass = "06011800"
        //聚众扰乱社会秩序案

        case "060133" =>
          caseClass = "06011900"
        //聚众冲击国家机关案

        case "06012000" =>
          caseClass = "06012000"
        //聚众扰乱公共场所秩序、交通秩序案

        case "060134" =>
          caseClass = "06012001"
        //聚众扰乱公共场所秩序案

        case "060135" =>
          caseClass = "06012002"
        //聚众扰乱交通秩序案

        case "060148" =>
          caseClass = "06012100"
        //投放虚假危险物质案

        case "060149" =>
          caseClass = "06012200"
        //编造、故意传播虚假恐怖信息案

        case "06012201" =>
          caseClass = "06012201"
        //编造虚假恐怖信息案

        case "06012202" =>
          caseClass = "06012202"
        //故意传播虚假恐怖信息案

        case "060136" =>
          caseClass = "06012300"
        //聚众斗殴案

        case "060137" =>
          caseClass = "06012400"
        //寻衅滋事案

        case "060139" =>
          caseClass = "06012500"
        //组织、领导、参加黑社会性质组织案

        case "06012501" =>
          caseClass = "06012501"
        //组织黑社会性质组织案

        case "06012502" =>
          caseClass = "06012502"
        //领导黑社会性质组织案

        case "060140" =>
          caseClass = "06012503"
        //参加黑社会性质组织案

        case "060141" =>
          caseClass = "06012600"
        //入境发展黑社会组织案

        case "060142" =>
          caseClass = "06012700"
        //包庇、纵容黑社会性质组织案

        case "06012701" =>
          caseClass = "06012701"
        //包庇黑社会性质组织案

        case "06012702" =>
          caseClass = "06012702"
        //纵容黑社会性质组织案

        case "060138" =>
          caseClass = "06012800"
        //传授犯罪方法案

        case "060143" =>
          caseClass = "06012900"
        //非法集会、游行、示威案

        case "06012901" =>
          caseClass = "06012901"
        //非法集会案

        case "06012902" =>
          caseClass = "06012902"
        //非法游行案

        case "06012903" =>
          caseClass = "06012903"
        //非法示威案

        case "060150" =>
        case "060154" =>
        case "060146" =>
          caseClass = "06013000"
        //非法携带武器、管制刀具、爆炸物参加集会、游行、示威案

        case "06013001" =>
          caseClass = "06013001"
        //非法携带武器参加集会案

        case "06013002" =>
          caseClass = "06013002"
        //非法携带武器参加游行案

        case "06013003" =>
          caseClass = "06013003"
        //非法携带武器参加示威案

        case "06013004" =>
          caseClass = "06013004"
        //非法携带管制刀具参加集会案

        case "06013005" =>
          caseClass = "06013005"
        //非法携带管制刀具参加游行案

        case "06013006" =>
          caseClass = "06013006"
        //非法携带管制刀具参加示威案

        case "06013007" =>
          caseClass = "06013007"
        //非法携带爆炸物参加集会案

        case "06013008" =>
          caseClass = "06013008"
        //非法携带爆炸物参加游行案

        case "06013009" =>
          caseClass = "06013009"
        //非法携带爆炸物参加示威案

        case "060158" =>
          caseClass = "06013100"
        //破坏集会、游行、示威案

        case "06013101" =>
          caseClass = "06013101"
        //破坏集会案

        case "06013102" =>
          caseClass = "06013102"
        //破坏游行案

        case "06013103" =>
          caseClass = "06013103"
        //破坏示威案

        case "060160" =>
          caseClass = "06013200"
        //侮辱国旗、国徽案

        case "06013201" =>
          caseClass = "06013201"
        //侮辱国旗案

        case "06013202" =>
          caseClass = "06013202"
        //侮辱国徽案

        case "060166" =>
        case "060167" =>
        case "060168" =>
        case "060180" =>
        case "060181" =>
        case "060182" =>
        case "060183" =>
        case "060184" =>
        case "060185" =>
        case "060186" =>
        case "060187" =>
        case "060188" =>
        case "060171" =>
          caseClass = "06013300"
        //组织、利用会道门、邪教组织、利用迷信破坏法律实施案

        case "06013301" =>
          caseClass = "06013301"
        //组织、利用会道门破坏法律实施案

        case "06013302" =>
          caseClass = "06013302"
        //组织、利用邪教组织破坏法律实施案

        case "06013303" =>
          caseClass = "06013303"
        //利用邪教组织破坏法律实施案

        case "060172" =>
        case "060173" =>
        case "060170" =>
          caseClass = "06013400"
        //组织、利用会道门、邪教组织、利用迷信致人死亡案

        case "06013401" =>
          caseClass = "06013401"
        //组织、利用会道门致人死亡案

        case "06013402" =>
          caseClass = "06013402"
        //组织、利用邪教组织致人死亡案

        case "06013403" =>
          caseClass = "06013403"
        //利用迷信致人死亡案

        case "060190" =>
          caseClass = "06013500"
        //聚众淫乱案

        case "060193" =>
          caseClass = "06013600"
        //引诱未成年人聚众淫乱案

        case "060196" =>
          caseClass = "06013700"
        //盗窃、侮辱尸体案

        case "06013701" =>
          caseClass = "06013701"
        //盗窃尸体案

        case "06013702" =>
          caseClass = "06013702"
        //侮辱尸体案

        case "060197" =>
          caseClass = "06013800"
        //赌博案

        case "060151" =>
          caseClass = "06013900"
        //开设赌场案

        case "060198" =>
          caseClass = "06014000"
        //故意延误投递邮件案

        case "060200" =>
          caseClass = "06020000"
        //妨害司法案

        case "060201" =>
          caseClass = "06020100"
        //伪证案

        case "060211" =>
          caseClass = "06020300"
        //辩护人、诉讼代理人毁灭证据、伪造证据、妨害作证案

        case "06020301" =>
          caseClass = "06020301"
        //辩护人毁灭证据案

        case "06020302" =>
          caseClass = "06020302"
        //辩护人伪造证据案

        case "06020303" =>
          caseClass = "06020303"
        //辩护人妨害作证案

        case "06020304" =>
          caseClass = "06020304"
        //诉讼代理人毁灭证据案

        case "06020305" =>
          caseClass = "06020305"
        //诉讼代理人伪造证据案

        case "06020306" =>
          caseClass = "06020306"
        //诉讼代理人妨害作证案

        case "060214" =>
          caseClass = "06020400"
        //妨害作证案

        case "060212" =>
          caseClass = "06020500"
        //帮助毁灭、伪造证据案

        case "06020501" =>
          caseClass = "06020501"
        //帮助毁灭证据案

        case "06020502" =>
          caseClass = "06020502"
        //帮助伪造证据案

        case "060215" =>
          caseClass = "06020600"
        //打击报复证人案

        case "060221" =>
          caseClass = "06020700"
        //扰乱法庭秩序案

        case "060224" =>
        case "060222" =>
          caseClass = "06020800"
        //窝藏、包庇案

        case "06020801" =>
          caseClass = "06020801"
        //窝藏案

        case "06020802" =>
          caseClass = "06020802"
        //包庇案

        case "060223" =>
          caseClass = "06020900"
        //拒绝提供间谍犯罪证据案

        case "060257" =>
          caseClass = "06021000"
        //掩饰、隐瞒犯罪所得、犯罪所得收益案

        case "06021001" =>
          caseClass = "06021001"
        //掩饰、隐瞒犯罪所得

        case "06021002" =>
          caseClass = "06021002"
        //掩饰、隐瞒犯罪所得收益

        case "060231" =>
          caseClass = "06021100"
        //拒不执行判决、裁定案

        case "06021101" =>
          caseClass = "06021101"
        //拒不执行判决案

        case "06021102" =>
          caseClass = "06021102"
        //拒不执行裁定案

        case "060241" =>
          caseClass = "06021200"
        //非法处置查封、扣押、冻结的财产案

        case "06021201" =>
          caseClass = "06021201"
        //非法处置查封的财产案

        case "06021202" =>
          caseClass = "06021202"
        //非法处置扣押的财产案

        case "06021203" =>
          caseClass = "06021203"
        //非法处置冻结的财产案

        case "060251" =>
          caseClass = "06021300"
        //破坏监管秩序案

        case "060252" =>
          caseClass = "06021400"
        //脱逃案

        case "060253" =>
          caseClass = "06021500"
        //劫夺被押解人员案

        case "060254" =>
          caseClass = "06021600"
        //组织越狱案

        case "060255" =>
          caseClass = "06021700"
        //暴动越狱案

        case "060256" =>
          caseClass = "06021800"
        //聚众持械劫狱案

        case "060300" =>
          caseClass = "06030000"
        //妨害国（边）境管理案

        case "060320" =>
          caseClass = "06030100"
        //组织他人偷越国（边）境案

        case "060340" =>
          caseClass = "06030200"
        //骗取出境证件案

        case "060350" =>
          caseClass = "06030300"
        //提供伪造、变造的出入境证件案

        case "06030301" =>
          caseClass = "06030301"
        //提供伪造的出入境证件案

        case "06030302" =>
          caseClass = "06030302"
        //提供变造的出入境证件案

        case "060360" =>
          caseClass = "06030400"
        //出售出入境证件案

        case "060330" =>
          caseClass = "06030500"
        //运送他人偷越国（边）境案

        case "060310" =>
          caseClass = "06030600"
        //偷越国（边）境案

        case "060370" =>
          caseClass = "06030700"
        //破坏界碑、界桩案

        case "06030701" =>
          caseClass = "06030701"
        //破坏界碑案

        case "06030702" =>
          caseClass = "06030702"
        //破坏界桩案

        case "060380" =>
          caseClass = "06030800"
        //破坏永久性测量标志案

        case "060400" =>
          caseClass = "06040000"
        //妨害文物管理案

        case "060401" =>
          caseClass = "06040100"
        //故意损毁文物案

        case "060442" =>
          caseClass = "06040200"
        //故意损毁名胜古迹案

        case "060402" =>
          caseClass = "06040300"
        //过失损毁文物案

        case "06040400" =>
          caseClass = "06040400"
        //非法向外国人出售、赠送珍贵文物案

        case "060411" =>
          caseClass = "06040401"
        //非法向外国人出售珍贵文物案

        case "060412" =>
          caseClass = "06040402"
        //非法向外国人赠送珍贵文物案

        case "060421" =>
          caseClass = "06040500"
        //倒卖文物案

        case "06040600" =>
          caseClass = "06040600"
        //非法出售、私赠文物藏品案

        case "060422" =>
          caseClass = "06040601"
        //非法出售文物藏品案

        case "060423" =>
          caseClass = "06040602"
        //非法私赠文物藏品案

        case "06040700" =>
          caseClass = "06040700"
        //盗掘古文化遗址、古墓葬案

        case "060431" =>
          caseClass = "06040701"
        //盗掘古文化遗址案

        case "060441" =>
          caseClass = "06040702"
        //盗掘古墓葬案

        case "060443" =>
          caseClass = "06040800"
        //盗掘古人类化石、古脊椎动物化石案

        case "06040801" =>
          caseClass = "06040801"
        //盗掘古人类化石案

        case "06040802" =>
          caseClass = "06040802"
        //盗掘古脊椎动物化石案

        case "06040900" =>
          caseClass = "06040900"
        //抢夺、窃取国有档案案

        case "060451" =>
          caseClass = "06040901"
        //抢夺国有档案案

        case "060452" =>
          caseClass = "06040902"
        //窃取国有档案案

        case "06041000" =>
          caseClass = "06041000"
        //擅自出卖、转让国有档案案

        case "060453" =>
          caseClass = "06041001"
        //擅自出卖国有档案案

        case "060454" =>
          caseClass = "06041002"
        //擅自转让国有档案案

        case "060500" =>
          caseClass = "06050000"
        //危害公共卫生案

        case "060501" =>
          caseClass = "06050100"
        //妨害传染病防治案

        case "060510" =>
          caseClass = "06050200"
        //传染病菌种、毒种扩散案

        case "06050201" =>
          caseClass = "06050201"
        //传染病菌种扩散案

        case "06050202" =>
          caseClass = "06050202"
        //毒种扩散案

        case "060520" =>
          caseClass = "06050300"
        //妨害国境卫生检疫案

        case "060530" =>
          caseClass = "06050400"
        //非法组织卖血案

        case "060531" =>
          caseClass = "06050500"
        //强迫卖血案

        case "06050600" =>
          caseClass = "06050600"
        //非法采集、供应血液、制作、供应血液制品案

        case "060532" =>
          caseClass = "06050601"
        //非法采集血液案

        case "06050602" =>
          caseClass = "06050602"
        //非法供应血液案

        case "060533" =>
          caseClass = "06050603"
        //非法制作血液制品案

        case "06050604" =>
          caseClass = "06050604"
        //非法供应血液制品案

        case "06050700" =>
          caseClass = "06050700"
        //采集、供应血液、制作、供应血液制品事故案

        case "060534" =>
          caseClass = "06050701"
        //采集血液事故案

        case "06050702" =>
          caseClass = "06050702"
        //供应血液事故案

        case "060535" =>
          caseClass = "06050703"
        //制作血液制品事故案

        case "06050704" =>
          caseClass = "06050704"
        //供应血液制品事故案

        case "060540" =>
          caseClass = "06050800"
        //医疗事故案

        case "060550" =>
          caseClass = "06050900"
        //非法行医案

        case "060560" =>
          caseClass = "06051000"
        //非法进行节育手术案

        case "060570" =>
          caseClass = "06051100"
        //妨害动植物防疫、检疫案

        case "06051101" =>
          caseClass = "06051101"
        //妨害动植物防疫案

        case "06051102" =>
          caseClass = "06051102"
        //妨害动植物检疫案

        case "060600" =>
          caseClass = "06060000"
        //破坏环境资源保护案

        case "060601" =>
          caseClass = "06060100"
        //污染环境案

        case "060602" =>
          caseClass = "06060200"
        //非法处置进口的固体废物案

        case "060603" =>
          caseClass = "06060300"
        //擅自进口固体废物案

        case "060631" =>
          caseClass = "06060400"
        //非法捕捞水产品案

        case "060611" =>
          caseClass = "06060500"
        //非法猎捕、杀害珍贵、濒危野生动物案

        case "06060501" =>
          caseClass = "06060501"
        //非法猎捕珍贵野生动物案

        case "06060502" =>
          caseClass = "06060502"
        //非法猎捕濒危野生动物案

        case "06060503" =>
          caseClass = "06060503"
        //非法杀害珍贵野生动物案

        case "06060504" =>
          caseClass = "06060504"
        //非法杀害濒危野生动物案

        case "060613" =>
        case "060614" =>
        case "060612" =>
          caseClass = "06060600"
        //非法收购、运输、出售珍贵、濒危野生动物、珍贵、濒危野生动物制品案

        case "06060601" =>
          caseClass = "06060601"
        //非法收购珍贵野生动物案

        case "06060602" =>
          caseClass = "06060602"
        //非法收购濒危野生动物案

        case "06060603" =>
          caseClass = "06060603"
        //非法收购珍贵野生动物制品案

        case "06060604" =>
          caseClass = "06060604"
        //非法收购濒危野生动物制品案

        case "06060605" =>
          caseClass = "06060605"
        //非法运输珍贵野生动物案

        case "06060606" =>
          caseClass = "06060606"
        //非法运输濒危野生动物案

        case "06060607" =>
          caseClass = "06060607"
        //非法运输珍贵野生动物制品案

        case "06060608" =>
          caseClass = "06060608"
        //非法运输濒危野生动物制品案

        case "06060609" =>
          caseClass = "06060609"
        //非法出售珍贵野生动物案

        case "06060610" =>
          caseClass = "06060610"
        //非法出售濒危野生动物案

        case "06060611" =>
          caseClass = "06060611"
        //非法出售珍贵野生动物制品案

        case "06060612" =>
          caseClass = "06060612"
        //非法出售濒危野生动物制品案

        case "060632" =>
          caseClass = "06060700"
        //非法狩猎案

        case "060633" =>
          caseClass = "06060800"
        //非法占用农用地案

        case "060634" =>
          caseClass = "06060900"
        //非法采矿案

        case "060635" =>
          caseClass = "06061000"
        //破坏性采矿案

        case "060621" =>
          caseClass = "06061100"
        //非法采伐、毁坏国家重点保护植物案

        case "06061101" =>
          caseClass = "06061101"
        //非法采伐国家重点保护植物案

        case "06061102" =>
          caseClass = "06061102"
        //毁坏国家重点保护植物案

        case "06061200" =>
          caseClass = "06061200"
        //非法收购、运输、加工、出售国家重点保护植物、国家重点保护植物制品案

        case "060636" =>
          caseClass = "06061201"
        //非法收购国家重点保护植物案

        case "06061202" =>
          caseClass = "06061202"
        //非法收购国家重点保护植物制品案

        case "060637" =>
          caseClass = "06061203"
        //非法运输国家重点保护植物案

        case "06061204" =>
          caseClass = "06061204"
        //非法运输国家重点保护植物制品案

        case "060638" =>
          caseClass = "06061205"
        //非法加工国家重点保护植物案

        case "06061206" =>
          caseClass = "06061206"
        //非法加工国家重点保护植物制品案

        case "060639" =>
          caseClass = "06061207"
        //非法出售国家重点保护植物案

        case "06061208" =>
          caseClass = "06061208"
        //非法出售国家重点保护植物制品案

        case "060622" =>
          caseClass = "06061300"
        //盗伐林木案

        case "060623" =>
          caseClass = "06061400"
        //滥伐林木案

        case "060624" =>
          caseClass = "06061500"
        //非法收购、运输盗伐、滥伐的林木案

        case "06061501" =>
          caseClass = "06061501"
        //非法收购盗伐的林木案

        case "06061502" =>
          caseClass = "06061502"
        //非法收购滥伐的林木案

        case "06061503" =>
          caseClass = "06061503"
        //运输盗伐的林木案

        case "06061504" =>
          caseClass = "06061504"
        //运输滥伐的林木案

        case "060700" =>
          caseClass = "06070000"
        //走私、贩卖、运输、制造毒品案

        case "06070100" =>
          caseClass = "06070100"

        case "060701" =>
          caseClass = "06070101"
        //走私毒品案

        case "060702" =>
          caseClass = "06070102"
        //贩卖毒品案

        case "060703" =>
          caseClass = "06070103"
        //运输毒品案

        case "060704" =>
          caseClass = "06070104"
        //制造毒品案

        case "060710" =>
          caseClass = "06070200"
        //非法持有毒品案

        case "060720" =>
          caseClass = "06070300"
        //包庇毒品犯罪分子案

        case "060721" =>
          caseClass = "06070400"
        //窝藏、转移、隐瞒毒品、毒赃案

        case "06070401" =>
          caseClass = "06070401"
        //窝藏毒品案

        case "06070402" =>
          caseClass = "06070402"
        //窝藏毒赃案

        case "06070403" =>
          caseClass = "06070403"
        //转移毒品案

        case "06070404" =>
          caseClass = "06070404"
        //转移毒赃案

        case "06070405" =>
          caseClass = "06070405"
        //隐瞒毒品案

        case "06070406" =>
          caseClass = "06070406"
        //隐瞒毒赃案

        case "060722" =>
          caseClass = "06070500"
        //走私制毒物品案

        case "060730" =>
          caseClass = "06070600"
        //非法买卖制毒物品案

        case "060731" =>
          caseClass = "06070700"
        //非法种植毒品原植物案

        case "06070800" =>
          caseClass = "06070800"
        //非法买卖、运输、携带、持有毒品原植物种子、幼苗案

        case "060732" =>
          caseClass = "06070801"
        //非法买卖毒品原植物种子案

        case "06070802" =>
          caseClass = "06070802"
        //非法买卖毒品原植物幼苗案

        case "060733" =>
          caseClass = "06070803"
        //非法运输毒品原植物种子案

        case "06070804" =>
          caseClass = "06070804"
        //非法运输毒品原植物幼苗案

        case "060734" =>
          caseClass = "06070805"
        //非法携带毒品原植物种子案

        case "06070806" =>
          caseClass = "06070806"
        //非法携带毒品原植物幼苗案

        case "060735" =>
          caseClass = "06070807"
        //非法持有毒品原植物种子案

        case "06070808" =>
          caseClass = "06070808"
        //非法持有毒品原植物幼苗案

        case "060740" =>
          caseClass = "06070900"
        //引诱、教唆、欺骗他人吸毒案

        case "060741" =>
          caseClass = "06070901"
        //引诱他人吸毒案

        case "060742" =>
          caseClass = "06070902"
        //教唆他人吸毒案

        case "060743" =>
          caseClass = "06070903"
        //欺骗他人吸毒案

        case "060744" =>
          caseClass = "06071000"
        //强迫他人吸毒案

        case "060745" =>
          caseClass = "06071100"
        //容留他人吸毒案

        case "06071200" =>
          caseClass = "06071200"
        //非法提供麻醉药品、精神药品案

        case "060750" =>
          caseClass = "06071201"
        //非法提供麻醉药品案

        case "060760" =>
          caseClass = "06071202"
        //非法提供精神药品案

        case "060800" =>
          caseClass = "06080000"
        //组织、强迫、引诱、容留、介绍卖淫案

        case "060801" =>
          caseClass = "06080100"
        //组织卖淫案

        case "060810" =>
          caseClass = "06080200"
        //强迫卖淫案

        case "060820" =>
          caseClass = "06080300"
        //协助组织卖淫案

        case "06080400" =>
          caseClass = "06080400"
        //引诱、容留、介绍卖淫案

        case "060830" =>
          caseClass = "06080401"
        //引诱卖淫案

        case "060850" =>
          caseClass = "06080402"
        //容留卖淫案

        case "060860" =>
          caseClass = "06080403"
        //介绍卖淫案

        case "060840" =>
          caseClass = "06080500"
        //引诱幼女卖淫案

        case "060870" =>
          caseClass = "06080600"
        //传播性病案

        case "060880" =>
          caseClass = "06080700"
        //嫖宿幼女案

        case "06090000" =>
          caseClass = "06090000"
        //制作、贩卖、传播淫秽物品案

        case "060900" =>
          caseClass = "06090100"
        //制作、复制、出版、贩卖、传播淫秽物品牟利案

        case "060910" =>
          caseClass = "06090101"
        //制作淫秽物品牟利案

        case "060920" =>
          caseClass = "06090102"
        //复制淫秽物品牟利案

        case "060930" =>
          caseClass = "06090103"
        //出版淫秽物品牟利案

        case "060940" =>
          caseClass = "06090104"
        //贩卖淫秽物品牟利案

        case "06090105" =>
          caseClass = "06090105"
        //传播淫秽物品牟利案

        case "060960" =>
          caseClass = "06090200"
        //为他人提供书号出版淫秽书刊案

        case "060950" =>
          caseClass = "06090300"
        //传播淫秽物品案

        case "060970" =>
          caseClass = "06090400"
        //组织播放淫秽音像制品案

        case "060980" =>
          caseClass = "06090500"
        //组织淫秽表演案

        case "079900" =>
        case "070000" =>
          caseClass = "07000000"
        //危害国防利益案

        case "070100" =>
          caseClass = "07000100"
        //阻碍军人执行职务案

        case "070200" =>
          caseClass = "07000200"
        //阻碍军事行动案

        case "07000300" =>
          caseClass = "07000300"
        //破坏武器装备、军事设施、军事通信案

        case "070300" =>
          caseClass = "07000301"
        //破坏武器装备案

        case "070400" =>
          caseClass = "07000302"
        //破坏军事设施案

        case "070500" =>
          caseClass = "07000303"
        //破坏军事通信案

        case "07000400" =>
          caseClass = "07000400"
        //过失损坏武器装备、军事设施、军事通信案

        case "07000401" =>
          caseClass = "07000401"
        //过失损坏武器装备案

        case "07000402" =>
          caseClass = "07000402"
        //过失损坏军事设施案

        case "07000403" =>
          caseClass = "07000403"
        //过失损坏军事通信案

        case "07000500" =>
          caseClass = "07000500"
        //故意提供不合格武器装备、军事设施案

        case "070600" =>
          caseClass = "07000501"
        //故意提供不合格武器装备案

        case "070700" =>
          caseClass = "07000502"
        //故意提供不合格军事设施案

        case "07000600" =>
          caseClass = "07000600"
        //过失提供不合格武器装备、军事设施案

        case "070800" =>
          caseClass = "07000601"
        //过失提供不合格武器装备案

        case "070900" =>
          caseClass = "07000602"
        //过失提供不合格军事设施案

        case "071000" =>
          caseClass = "07000700"
        //聚众冲击军事禁区案

        case "071100" =>
          caseClass = "07000800"
        //聚众扰乱军事管理区秩序案

        case "071200" =>
          caseClass = "07000900"
        //冒充军人招摇撞骗案

        case "071300" =>
          caseClass = "07001000"
        //煽动军人逃离部队案

        case "071400" =>
          caseClass = "07001100"
        //雇用逃离部队军人案

        case "071500" =>
          caseClass = "07001200"
        //接送不合格兵员案

        case "071600" =>
          caseClass = "07001300"
        //伪造、变造、买卖武装部队公文、证件、印章案

        case "07001301" =>
          caseClass = "07001301"
        //伪造武装部队公文案

        case "07001302" =>
          caseClass = "07001302"
        //伪造武装部队证件案

        case "07001303" =>
          caseClass = "07001303"
        //伪造武装部队印章案

        case "07001304" =>
          caseClass = "07001304"
        //变造武装部队公文案

        case "07001305" =>
          caseClass = "07001305"
        //变造武装部队证件案

        case "07001306" =>
          caseClass = "07001306"
        //变造武装部队印章案

        case "07001307" =>
          caseClass = "07001307"
        //买卖武装部队公文案

        case "07001308" =>
          caseClass = "07001308"
        //买卖武装部队证件案

        case "07001309" =>
          caseClass = "07001309"
        //买卖武装部队印章案

        case "071700" =>
          caseClass = "07001400"
        //盗窃、抢夺武装部队公文、证件、印章案

        case "07001401" =>
          caseClass = "07001401"
        //盗窃武装部队公文案

        case "07001402" =>
          caseClass = "07001402"
        //盗窃武装部队证件案

        case "07001403" =>
          caseClass = "07001403"
        //盗窃武装部队印章案

        case "071800" =>
          caseClass = "07001404"
        //抢夺武装部队公文案

        case "07001405" =>
          caseClass = "07001405"
        //抢夺武装部队证件案

        case "07001406" =>
          caseClass = "07001406"
        //抢夺武装部队印章案

        case "072000" =>
          caseClass = "07001500"
        //非法生产、买卖武装部队制式服装案

        case "07001501" =>
          caseClass = "07001501"
        //非法生产武装部队制式服装案

        case "07001502" =>
          caseClass = "07001502"
        //非法买卖武装部队制式服装案

        case "071900" =>
          caseClass = "07001600"
        //伪造、盗窃、买卖、非法提供、非法使用武装部队专用标志案

        case "07001601" =>
          caseClass = "07001601"
        //伪造武装部队专用标志案

        case "07001602" =>
          caseClass = "07001602"
        //盗窃武装部队专用标志案

        case "07001603" =>
          caseClass = "07001603"
        //买卖武装部队专用标志案

        case "07001604" =>
          caseClass = "07001604"
        //非法提供武装部队专用标志案

        case "07001605" =>
          caseClass = "07001605"
        //非法使用武装部队专用标志案

        case "072200" =>
        case "072100" =>
          caseClass = "07001700"
        //战时拒绝、逃避征召、军事训练案

        case "07001701" =>
          caseClass = "07001701"
        //战时拒绝征召案

        case "07001702" =>
          caseClass = "07001702"
        //战时拒绝军事训练案

        case "07001703" =>
          caseClass = "07001703"
        //战时逃避征召案

        case "07001704" =>
          caseClass = "07001704"
        //战时逃避军事训练案

        case "072300" =>
          caseClass = "07001800"
        //战时拒绝、逃避服役案

        case "07001801" =>
          caseClass = "07001801"
        //战时拒绝服役案

        case "07001802" =>
          caseClass = "07001802"
        //战时逃避服役案

        case "072400" =>
          caseClass = "07001900"
        //战时故意提供虚假敌情案

        case "072500" =>
          caseClass = "07002000"
        //战时造谣扰乱军心案

        case "072600" =>
          caseClass = "07002100"
        //战时窝藏逃离部队军人案

        case "07002200" =>
          caseClass = "07002200"
        //战时拒绝、故意延误军事订货案

        case "072700" =>
          caseClass = "07002201"
        //战时拒绝军事订货案

        case "072800" =>
          caseClass = "07002202"
        //战时故意延误军事订货案

        case "072900" =>
          caseClass = "07002300"
        //战时拒绝军事征用案

        case "089900" =>
          caseClass = "08000000"
        //贪污贿赂案

        case "089901" =>
          caseClass = "08000100"
        //贪污案

        case "089902" =>
          caseClass = "08000200"
        //挪用公款案

        case "089903" =>
        case "030461" =>
          caseClass = "08000300"
        //受贿案

        case "089904" =>
          caseClass = "08000400"
        //单位受贿案

        case "089906" =>
        case "089907" =>
        case "089905" =>
          caseClass = "08000500"
        //利用影响力受贿案

        case "089908" =>
          caseClass = "08000600"
        //行贿案

        case "089909" =>
          caseClass = "08000700"
        //对单位行贿案

        case "089910" =>
          caseClass = "08000800"
        //介绍贿赂案

        case "089911" =>
          caseClass = "08000900"
        //单位行贿案

        case "089912" =>
          caseClass = "08001000"
        //巨额财产来源不明案

        case "089913" =>
          caseClass = "08001100"
        //隐瞒境外存款案

        case "089914" =>
          caseClass = "08001200"
        //私分国有资产案

        case "089915" =>
          caseClass = "08001300"
        //私分罚没财物案

        case "090100" =>
        case "090000" =>
          caseClass = "09000000"
        //渎职案

        case "090101" =>
          caseClass = "09000100"
        //滥用职权案

        case "090102" =>
          caseClass = "09000200"
        //玩忽职守案

        case "090103" =>
          caseClass = "09000300"
        //故意泄露国家秘密案

        case "090104" =>
          caseClass = "09000400"
        //过失泄露国家秘密案

        case "090105" =>
          caseClass = "09000500"
        //徇私枉法案

        case "090106" =>
          caseClass = "09000600"
        //民事、行政枉法裁判案

        case "09000601" =>
          caseClass = "09000601"
        //民事枉法裁判案

        case "09000602" =>
          caseClass = "09000602"
        //行政枉法裁判案

        case "090107" =>
          caseClass = "09000700"
        //执行判决、裁定失职案

        case "09000701" =>
          caseClass = "09000701"
        //执行判决失职案

        case "09000702" =>
          caseClass = "09000702"
        //执行裁定失职案

        case "090108" =>
          caseClass = "09000800"
        //执行判决、裁定滥用职权案

        case "09000801" =>
          caseClass = "09000801"
        //执行判决滥用职权案

        case "09000802" =>
          caseClass = "09000802"
        //执行裁定滥用职权案

        case "090109" =>
          caseClass = "09000900"
        //枉法仲裁案

        case "090110" =>
          caseClass = "09001000"
        //私放在押人员案

        case "090111" =>
          caseClass = "09001100"
        //失职致使在押人员脱逃案

        case "090112" =>
          caseClass = "09001200"
        //徇私舞弊减刑、假释、暂予监外执行案

        case "09001201" =>
          caseClass = "09001201"
        //徇私舞弊减刑案

        case "09001202" =>
          caseClass = "09001202"
        //徇私舞弊假释案

        case "09001203" =>
          caseClass = "09001203"
        //徇私舞弊暂予监外执行案

        case "090113" =>
          caseClass = "09001300"
        //徇私舞弊不移交刑事案件案

        case "09001400" =>
          caseClass = "09001400"
        //滥用管理公司、证券职权案

        case "090114" =>
          caseClass = "09001401"
        //滥用管理公司职权案

        case "090115" =>
          caseClass = "09001402"
        //滥用管理证券职权案

        case "090116" =>
          caseClass = "09001500"
        //徇私舞弊不征、少征税款案

        case "09001501" =>
          caseClass = "09001501"
        //徇私舞弊不征税款案

        case "09001502" =>
          caseClass = "09001502"
        //徇私舞弊少征税款案

        case "09001600" =>
          caseClass = "09001600"
        //徇私舞弊发售发票、抵扣税款、出口退税案

        case "090117" =>
          caseClass = "09001601"
        //徇私舞弊发售发票案

        case "090118" =>
          caseClass = "09001602"
        //徇私舞弊抵扣税款案

        case "090119" =>
          caseClass = "09001603"
        //徇私舞弊出口退税案

        case "090120" =>
          caseClass = "09001700"
        //违法提供出口退税凭证案

        case "09001800" =>
          caseClass = "09001800"
        //国家机关工作人员签订、履行合同失职被骗案

        case "090121" =>
          caseClass = "09001801"
        //国家机关工作人员签订合同失职被骗案

        case "090122" =>
          caseClass = "09001802"
        //国家机关工作人员履行合同失职被骗案

        case "090123" =>
          caseClass = "09001900"
        //违法发放林木采伐许可证案

        case "090124" =>
          caseClass = "09002000"
        //环境监管失职案

        case "090125" =>
          caseClass = "09002100"
        //食品监管渎职案

        case "090126" =>
          caseClass = "09002200"
        //传染病防治失职案

        case "090127" =>
          caseClass = "09002300"
        //非法批准征用、占用土地案

        case "09002301" =>
          caseClass = "09002301"
        //非法批准征用土地案

        case "09002302" =>
          caseClass = "09002302"
        //非法占用土地案

        case "090128" =>
          caseClass = "09002400"
        //非法、低价出让国有土地使用权案

        case "09002401" =>
          caseClass = "09002401"
        //非法出让国有土地使用权案

        case "09002402" =>
          caseClass = "09002402"
        //低价出让国有土地使用权案

        case "090129" =>
          caseClass = "09002500"
        //放纵走私案

        case "090130" =>
          caseClass = "09002600"
        //商检徇私舞弊案

        case "090131" =>
          caseClass = "09002700"
        //商检失职案

        case "090132" =>
          caseClass = "09002800"
        //动植物检疫徇私舞弊案

        case "090133" =>
          caseClass = "09002900"
        //动植物检疫失职案

        case "090134" =>
          caseClass = "09003000"
        //放纵制售伪劣商品犯罪行为案

        case "090135" =>
          caseClass = "09003100"
        //办理偷越国（边）境人员出入境证件案

        case "090136" =>
          caseClass = "09003200"
        //放行偷越国（边）境人员案

        case "090137" =>
          caseClass = "09003300"
        //不解救被拐卖、绑架妇女、儿童案

        case "09003301" =>
          caseClass = "09003301"
        //不解救被拐卖妇女案

        case "09003302" =>
          caseClass = "09003302"
        //不解救被绑架妇女案

        case "09003303" =>
          caseClass = "09003303"
        //不解救被拐卖儿童案

        case "09003304" =>
          caseClass = "09003304"
        //不解救被绑架儿童案

        case "090138" =>
          caseClass = "09003400"
        //阻碍解救被拐卖、绑架妇女、儿童案

        case "09003401" =>
          caseClass = "09003401"
        //阻碍解救被拐卖妇女案

        case "09003402" =>
          caseClass = "09003402"
        //阻碍解救被绑架妇女案

        case "09003403" =>
          caseClass = "09003403"
        //阻碍解救被拐卖儿童案

        case "09003404" =>
          caseClass = "09003404"
        //阻碍解救被绑架儿童案

        case "090139" =>
          caseClass = "09003500"
        //帮助犯罪分子逃避处罚案

        case "09003600" =>
          caseClass = "09003600"
        //招收公务员、学生徇私舞弊案

        case "090140" =>
          caseClass = "09003601"
        //招收公务员徇私舞弊案

        case "090141" =>
          caseClass = "09003602"
        //招收学生徇私舞弊案

        case "09003700" =>
          caseClass = "09003700"
        //失职造成珍贵文物损毁、流失案

        case "090142" =>
          caseClass = "09003701"
        //失职造成珍贵文物损毁案

        case "090143" =>
          caseClass = "09003702"
        //失职造成珍贵文物流失案

        case "10000000" =>
          caseClass = "10000000"
        //军人违反职责案

        case "10000100" =>
          caseClass = "10000100"
        //战时违抗命令案

        case "10000200" =>
          caseClass = "10000200"
        //隐瞒、谎报军情案

        case "10000201" =>
          caseClass = "10000201"
        //隐瞒军情案

        case "10000202" =>
          caseClass = "10000202"
        //谎报军情案

        case "10000300" =>
          caseClass = "10000300"
        //拒传、假传军令案

        case "10000301" =>
          caseClass = "10000301"
        //拒传军令案

        case "10000302" =>
          caseClass = "10000302"
        //假传军令案

        case "10000400" =>
          caseClass = "10000400"
        //投降案

        case "10000500" =>
          caseClass = "10000500"
        //战时临阵脱逃案

        case "10000600" =>
          caseClass = "10000600"
        //擅离、玩忽军事职守案

        case "10000601" =>
          caseClass = "10000601"
        //擅离军事职守案

        case "10000602" =>
          caseClass = "10000602"
        //玩忽军事职守案

        case "10000700" =>
          caseClass = "10000700"
        //阻碍执行军事职务案

        case "10000800" =>
          caseClass = "10000800"
        //指使部属违反职责案

        case "10000900" =>
          caseClass = "10000900"
        //违令作战消极案

        case "10001000" =>
          caseClass = "10001000"
        //拒不救援友邻部队案

        case "10001100" =>
          caseClass = "10001100"
        //军人叛逃案

        case "10001200" =>
          caseClass = "10001200"
        //非法获取军事秘密案

        case "10001300" =>
          caseClass = "10001300"
        //为境外窃取、刺探、收买、非法提供军事秘密案

        case "10001301" =>
          caseClass = "10001301"
        //为境外窃取军事秘密案

        case "10001302" =>
          caseClass = "10001302"
        //为境外刺探军事秘密案

        case "10001303" =>
          caseClass = "10001303"
        //为境外收买军事秘密案

        case "10001304" =>
          caseClass = "10001304"
        //为境外非法提供军事秘密案

        case "10001400" =>
          caseClass = "10001400"
        //故意泄露军事秘密案

        case "10001500" =>
          caseClass = "10001500"
        //过失泄露军事秘密案

        case "10001600" =>
          caseClass = "10001600"
        //战时造谣惑众案

        case "10001700" =>
          caseClass = "10001700"
        //战时自伤案

        case "10001800" =>
          caseClass = "10001800"
        //逃离部队案

        case "10001900" =>
          caseClass = "10001900"
        //武器装备肇事案

        case "10002000" =>
          caseClass = "10002000"
        //擅自改变武器装备编配用途案

        case "10002100" =>
          caseClass = "10002100"
        //盗窃、抢夺武器装备、军用物资案

        case "10002101" =>
          caseClass = "10002101"
        //盗窃武器装备案

        case "10002102" =>
          caseClass = "10002102"
        //盗窃军用物资案

        case "10002103" =>
          caseClass = "10002103"
        //抢夺武器装备案

        case "10002104" =>
          caseClass = "10002104"
        //抢夺军用物资案

        case "10002200" =>
          caseClass = "10002200"
        //非法出卖、转让武器装备案

        case "10002201" =>
          caseClass = "10002201"
        //非法出卖武器装备案

        case "10002202" =>
          caseClass = "10002202"
        //非法转让武器装备案

        case "10002300" =>
          caseClass = "10002300"
        //遗弃武器装备案

        case "10002400" =>
          caseClass = "10002400"
        //遗失武器装备案

        case "10002500" =>
          caseClass = "10002500"
        //擅自出卖、转让军队房地产案

        case "10002501" =>
          caseClass = "10002501"
        //擅自出卖房地产案

        case "10002502" =>
          caseClass = "10002502"
        //擅自转让军队房地产案

        case "10002600" =>
          caseClass = "10002600"
        //虐待部属案

        case "10002700" =>
          caseClass = "10002700"
        //遗弃伤病军人案

        case "10002800" =>
          caseClass = "10002800"
        //战时拒不救治伤病军人案

        case "10002900" =>
          caseClass = "10002900"
        //战时残害居民、掠夺居民财物案

        case "10002901" =>
          caseClass = "10002901"
        //战时残害居民案

        case "10002902" =>
          caseClass = "10002902"
        //战时掠夺居民财物案

        case "10003000" =>
          caseClass = "10003000"
        //私放俘虏案

        case "10003100" =>
          caseClass = "10003100"
        //虐待俘虏案

        case _ =>
          caseClass = code
        //若匹配不到，返回原值

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
