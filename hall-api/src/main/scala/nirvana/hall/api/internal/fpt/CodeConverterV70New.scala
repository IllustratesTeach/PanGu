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
          caseClass = "010000"
        //危害国家安全案

        case "010100" =>
          caseClass = "010100"
        //背叛、分裂国家案

        case "010110" =>
          caseClass = "010110"
        //背叛国家案

        case "010120" =>
          caseClass = "010120"
        //分裂国家案

        case "010130" =>
          caseClass = "010130"
        //煽动,分裂国家案

        case "010140" =>
          caseClass = "010140"
        //武装叛乱、暴乱案

        case "010150" =>
          caseClass = "010150"
        //策动武装暴乱案

        case "010160" =>
          caseClass = "010160"
        //颠覆国家政权案

        case "010170" =>
          caseClass = "010170"
        //煽动颠覆国家政权案

        case "010180" =>
          caseClass = "010180"
        //资助危害国家安全案

        case "010200" =>
          caseClass = "010200"
        //叛变、叛逃案

        case "010210" =>
          caseClass = "010210"
        //投敌叛逃案

        case "010220" =>
          caseClass = "010220"
        //叛逃案

        case "010300" =>
          caseClass = "010300"
        //间谍、资敌案

        case "010310" =>
          caseClass = "010310"
        //间谍案

        case "010320" =>
          caseClass = "010320"
        //资敌案

        case "010400" =>
          caseClass = "010400"
        //为境外窃取、刺探、收买、非法提供国家秘密情报案

        case "010410" =>
          caseClass = "010410"
        //为境外窃取国家秘密情报案

        case "010420" =>
          caseClass = "010420"
        //为境外刺探国家秘密情报案

        case "010430" =>
          caseClass = "010430"
        //为境外收买国家秘密情报案

        case "010440" =>
          caseClass = "010440"
        //为境外非法提供国家秘密情报案

        case "019900" =>
          caseClass = "019900"
        //其他危害国家安全案

        case "020000" =>
          caseClass = "020000"
        //危害公共安全案

        case "020100" =>
          caseClass = "020100"
        //以危险方法危害公共安全案

        case "020101" =>
          caseClass = "020101"
        //放火案

        case "020102" =>
          caseClass = "020102"
        //决水案

        case "020103" =>
          caseClass = "020103"
        //爆炸案

        case "020104" =>
          caseClass = "020104"
        //投毒案

        case "020109" =>
          caseClass = "020109"
        //以其他危险方法危害公共安全案

        case "020111" =>
          caseClass = "020111"
        //失火案

        case "020112" =>
          caseClass = "020112"
        //过失决水案

        case "020113" =>
          caseClass = "020113"
        //过失爆炸案

        case "020114" =>
          caseClass = "020114"
        //过失投毒案

        case "020119" =>
          caseClass = "020119"
        //过失以其他危险方法危害公共安全案

        case "020200" =>
          caseClass = "020200"
        //危害交通运输、公用设备安全案

        case "020201" =>
          caseClass = "020201"
        //破坏交通工具案

        case "020202" =>
          caseClass = "020202"
        //破坏交通设施案

        case "020203" =>
          caseClass = "020203"
        //破坏电力设备案

        case "020204" =>
          caseClass = "020204"
        //破坏燃气设备案

        case "020205" =>
          caseClass = "020205"
        //破坏易燃易爆设备案

        case "020206" =>
          caseClass = "020206"
        //破坏广播电视设施案

        case "020207" =>
          caseClass = "020207"
        //破坏公用电信设施案

        case "020221" =>
          caseClass = "020221"
        //过失损坏交通工具案

        case "020222" =>
          caseClass = "020222"
        //过失损坏交通设施案

        case "020223" =>
          caseClass = "020223"
        //过失损坏电力设备案

        case "020224" =>
          caseClass = "020224"
        //过失损坏燃气设备案

        case "020225" =>
          caseClass = "020225"
        //过失损坏易燃易爆设备案

        case "020226" =>
          caseClass = "020226"
        //过失损坏广播电视设施案

        case "020227" =>
          caseClass = "020227"
        //过失损坏公用电信设施案

        case "020300" =>
          caseClass = "020300"
        //实施恐怖、劫持案

        case "020301" =>
          caseClass = "020301"
        //组织、领导恐怖组织案

        case "020311" =>
          caseClass = "020311"
        //劫持航空器案

        case "020312" =>
          caseClass = "020312"
        //劫持船只案

        case "020313" =>
          caseClass = "020313"
        //劫持汽车案

        case "020331" =>
          caseClass = "020331"
        //暴力危及飞行安全案

        case "020400" =>
          caseClass = "020400"
        //非法制造，买卖，运输，邮寄，储存枪支弹药、爆炸物案

        case "020401" =>
          caseClass = "020401"
        //非法制造枪支弹药案

        case "020402" =>
          caseClass = "020402"
        //非法买卖枪支弹药案

        case "020403" =>
          caseClass = "020403"
        //非法运输枪支弹药案

        case "020404" =>
          caseClass = "020404"
        //非法邮寄枪支弹药案

        case "020405" =>
          caseClass = "020405"
        //非法储存枪支弹药案

        case "020406" =>
          caseClass = "020406"
        //非法制造爆炸物案

        case "020407" =>
          caseClass = "020407"
        //非法买卖爆炸物案

        case "020408" =>
          caseClass = "020408"
        //非法运输爆炸物案

        case "020409" =>
          caseClass = "020409"
        //非法邮寄爆炸物案

        case "020410" =>
          caseClass = "020410"
        //非法储存爆炸物案

        case "020411" =>
          caseClass = "020411"
        //非法买卖核材料案

        case "020412" =>
          caseClass = "020412"
        //非法运输核材料案

        case "020500" =>
          caseClass = "020500"
        //违反枪支弹药管理案

        case "020501" =>
          caseClass = "020501"
        //企业违规制造枪支案

        case "020502" =>
          caseClass = "020502"
        //企业销售制造枪支案

        case "020511" =>
          caseClass = "020511"
        //非法持有枪支弹药案

        case "020512" =>
          caseClass = "020512"
        //非法私藏枪弹案

        case "020521" =>
          caseClass = "020521"
        //非法出借枪弹案

        case "020522" =>
          caseClass = "020522"
        //非法出租枪弹案

        case "020531" =>
          caseClass = "020531"
        //丢失枪支不报案

        case "020541" =>
          caseClass = "020541"
        //非法携带枪支弹药危及公共安全案

        case "020600" =>
          caseClass = "020600"
        //违反危险物品管理案

        case "020601" =>
          caseClass = "020601"
        //非法携带管制刀具危及公共安全案

        case "020602" =>
          caseClass = "020602"
        //非法携带危险品危及公共安全案

        case "020700" =>
          caseClass = "020700"
        //重大责任事故案

        case "020701" =>
          caseClass = "020701"
        //重大飞行事故案

        case "020702" =>
          caseClass = "020702"
        //铁路运营安全事故案

        case "020703" =>
          caseClass = "020703"
        //交通肇事案

        case "020711" =>
          caseClass = "020711"
        //重大劳动安全事故案

        case "020712" =>
          caseClass = "020712"
        //危险物品肇事案

        case "020713" =>
          caseClass = "020713"
        //工程重大安全事故案

        case "020714" =>
          caseClass = "020714"
        //教育设施重大安全事故案

        case "020715" =>
          caseClass = "020715"
        //消防责任事故案

        case "020800" =>
          caseClass = "020800"
        //抢劫枪支、弹药、爆炸物案

        case "020810" =>
          caseClass = "020810"
        //抢劫枪支弹药案

        case "020811" =>
          caseClass = "020811"
        //抢劫国家机关枪支弹药案

        case "020812" =>
          caseClass = "020812"
        //抢劫军、警、民兵枪支弹药案

        case "020820" =>
          caseClass = "020820"
        //抢劫爆炸物案

        case "020821" =>
          caseClass = "020821"
        //抢劫国家机关爆炸物案

        case "020822" =>
          caseClass = "020822"
        //抢劫军、警、民兵爆炸物案

        case "020900" =>
          caseClass = "020900"
        //盗窃、抢夺枪支、弹药、爆炸物案

        case "020910" =>
          caseClass = "020910"
        //盗窃枪支、弹药、爆炸物案

        case "020911" =>
          caseClass = "020911"
        //盗窃枪支、弹药案

        case "020912" =>
          caseClass = "020912"
        //盗窃国家机关枪支、弹药案

        case "020913" =>
          caseClass = "020913"
        //盗窃军、警、民兵枪支、弹药案

        case "020914" =>
          caseClass = "020914"
        //盗窃爆炸物案

        case "020915" =>
          caseClass = "020915"
        //盗窃国家机关爆炸物案

        case "020916" =>
          caseClass = "020916"
        //盗窃军、警、民兵爆炸物案

        case "020920" =>
          caseClass = "020920"
        //抢夺枪支、弹药、爆炸物案

        case "020921" =>
          caseClass = "020921"
        //抢夺枪支、弹药案

        case "020922" =>
          caseClass = "020922"
        //抢夺国家机关枪支弹药案

        case "020923" =>
          caseClass = "020923"
        //抢夺军、警、民兵枪支弹药案

        case "020924" =>
          caseClass = "020924"
        //抢夺爆炸物案

        case "020925" =>
          caseClass = "020925"
        //抢夺国家机关爆炸物案

        case "020926" =>
          caseClass = "020926"
        //抢夺军、警、民兵爆炸物案

        case "029900" =>
          caseClass = "029900"
        //其他危害公共安全案

        case "030000" =>
          caseClass = "030000"
        //破坏社会主义市场经济秩序案

        case "030100" =>
          caseClass = "030100"
        //生产、销售假冒伪劣商品（产品）案

        case "030101" =>
          caseClass = "030101"
        //生产、销售伪劣产品案

        case "030102" =>
          caseClass = "030102"
        //生产、销售假药案

        case "030103" =>
          caseClass = "030103"
        //生产、销售劣药案

        case "030104" =>
          caseClass = "030104"
        //生产、销售伪劣兽药案

        case "030105" =>
          caseClass = "030105"
        //生产、销售伪劣农药案

        case "030111" =>
          caseClass = "030111"
        //生产、销售伪劣化肥案

        case "030121" =>
          caseClass = "030121"
        //生产、销售伪劣种子案

        case "030131" =>
          caseClass = "030131"
        //生产、销售不符合卫生标准食品案

        case "030132" =>
          caseClass = "030132"
        //生产、销售有毒、有害食品案

        case "030141" =>
          caseClass = "030141"
        //生产、销售不符合标准的医用器材案

        case "030151" =>
          caseClass = "030151"
        //生产、销售不符合安全标准的产品案

        case "030161" =>
          caseClass = "030161"
        //生产、销售不符合卫生标准化妆品案

        case "030200" =>
          caseClass = "030200"
        //走私案

        case "030201" =>
          caseClass = "030201"
        //走私武器、弹药案

        case "030210" =>
          caseClass = "030210"
        //走私核材料案

        case "030220" =>
          caseClass = "030220"
        //走私文物案

        case "030230" =>
          caseClass = "030230"
        //走私假币案

        case "030240" =>
          caseClass = "030240"
        //走私贵重金属案

        case "030241" =>
          caseClass = "030241"
        //走私黄金案

        case "030242" =>
          caseClass = "030242"
        //走私白银案

        case "030250" =>
          caseClass = "030250"
        //走私珍贵动物及其制品案

        case "030260" =>
          caseClass = "030260"
        //走私珍稀植物及其制品案

        case "030270" =>
          caseClass = "030270"
        //走私淫秽物品案

        case "030280" =>
          caseClass = "030280"
        //走私一般货物物品案

        case "030290" =>
          caseClass = "030290"
        //走私固体废料案

        case "030300" =>
          caseClass = "030300"
        //妨害对公司、企业的管理秩序案

        case "030301" =>
          caseClass = "030301"
        //虚报注册资本案

        case "030311" =>
          caseClass = "030311"
        //虚假出资案

        case "030312" =>
          caseClass = "030312"
        //抽逃出资案

        case "030321" =>
          caseClass = "030321"
        //欺诈发行股票案

        case "030322" =>
          caseClass = "030322"
        //欺诈发行债券案

        case "030331" =>
          caseClass = "030331"
        //提供虚假财会报告案

        case "030332" =>
          caseClass = "030332"
        //妨害清算案

        case "030341" =>
          caseClass = "030341"
        //公司、企业人员受贿案

        case "030342" =>
          caseClass = "030342"
        //对公司、企业人员行贿案

        case "030351" =>
          caseClass = "030351"
        //非法经营同类营业案

        case "030352" =>
          caseClass = "030352"
        //为亲友非法牟利案

        case "030361" =>
          caseClass = "030361"
        //签定、履行合同失职被骗案

        case "030371" =>
          caseClass = "030371"
        //徇私舞弊造成破产案

        case "030372" =>
          caseClass = "030372"
        //徇私舞弊造成亏损案

        case "030373" =>
          caseClass = "030373"
        //徇私舞弊低价折股国有资产案

        case "030374" =>
          caseClass = "030374"
        //徇私舞弊低价出售国有资产案

        case "030400" =>
          caseClass = "030400"
        //破坏金融管理秩序案

        case "030401" =>
          caseClass = "030401"
        //伪造货币案

        case "030411" =>
          caseClass = "030411"
        //出售假币案

        case "030415" =>
          caseClass = "030415"
        //购买假币案

        case "030420" =>
          caseClass = "030420"
        //运输假币案

        case "030421" =>
          caseClass = "030421"
        //金融工作人员购买假币以假币换取货币案

        case "030440" =>
          caseClass = "030440"
        //持有、使用假币案

        case "030441" =>
          caseClass = "030441"
        //变造货币案

        case "030442" =>
          caseClass = "030442"
        //伪造、变造国库券案

        case "030443" =>
          caseClass = "030443"
        //伪造、变造国家其他有价证券案

        case "030444" =>
          caseClass = "030444"
        //伪造、变造股票案

        case "030445" =>
          caseClass = "030445"
        //伪造、变造公司企业债券案

        case "030446" =>
          caseClass = "030446"
        //伪造、变造金融票证案

        case "030447" =>
          caseClass = "030447"
        //伪造、变造、转让金融机构经营许可证案

        case "030451" =>
          caseClass = "030451"
        //擅自设立金融机构案

        case "030452" =>
          caseClass = "030452"
        //转让金融机构许可证案

        case "030453" =>
          caseClass = "030453"
        //高利转贷案

        case "030454" =>
          caseClass = "030454"
        //非法吸收公众存款案

        case "030455" =>
          caseClass = "030455"
        //擅自发行股票、公司企业债券案

        case "030456" =>
          caseClass = "030456"
        //内幕交易、泄露内幕信息案

        case "030457" =>
          caseClass = "030457"
        //编造并传播证券交易虚假信息案

        case "030458" =>
          caseClass = "030458"
        //诱骗投资者买卖证券案

        case "030459" =>
          caseClass = "030459"
        //操纵证券交易价格案

        case "030460" =>
          caseClass = "030460"
        //保险公司人员虚假理赔案

        case "030461" =>
          caseClass = "030461"
        //金融机构人员受贿案

        case "030462" =>
          caseClass = "030462"
        //违法发放贷款案

        case "030463" =>
          caseClass = "030463"
        //违法向关系人发放贷款案

        case "030464" =>
          caseClass = "030464"
        //用帐外客户资金非法拆借、发放贷款案

        case "030466" =>
          caseClass = "030466"
        //非法出具金融票证案

        case "030471" =>
          caseClass = "030471"
        //对违法票据承兑付款、保证案

        case "030474" =>
          caseClass = "030474"
        //逃汇案

        case "030475" =>
          caseClass = "030475"
        //套汇案

        case "030476" =>
          caseClass = "030476"
        //骗汇案

        case "030477" =>
          caseClass = "030477"
        //洗钱案

        case "030478" =>
          caseClass = "030478"
        //非法买卖外汇案

        case "030500" =>
          caseClass = "030500"
        //金融诈骗案

        case "030510" =>
          caseClass = "030510"
        //集资诈骗案

        case "030520" =>
          caseClass = "030520"
        //贷款诈骗案

        case "030530" =>
          caseClass = "030530"
        //票据诈骗案

        case "030540" =>
          caseClass = "030540"
        //金融凭证诈骗案

        case "030550" =>
          caseClass = "030550"
        //信用证诈骗案

        case "030560" =>
          caseClass = "030560"
        //信用卡诈骗案

        case "030570" =>
          caseClass = "030570"
        //有价证券诈骗案

        case "030580" =>
          caseClass = "030580"
        //保险诈骗案

        case "030600" =>
          caseClass = "030600"
        //危害税收征管案

        case "030601" =>
          caseClass = "030601"
        //偷税案

        case "030602" =>
          caseClass = "030602"
        //抗税案

        case "030603" =>
          caseClass = "030603"
        //逃避追缴欠税案

        case "030604" =>
          caseClass = "030604"
        //骗取出口退税案

        case "030605" =>
          caseClass = "030605"
        //骗取国家出口免征税款案

        case "030611" =>
          caseClass = "030611"
        //虚开增值税发票用于骗取出口退税、抵扣税款发票案

        case "030612" =>
          caseClass = "030612"
        //伪造增值税专用发票案

        case "030613" =>
          caseClass = "030613"
        //出售伪造的增值税专用发票案

        case "030621" =>
          caseClass = "030621"
        //非法出售增值税专用发票案

        case "030622" =>
          caseClass = "030622"
        //非法购买增值税专用发票案

        case "030623" =>
          caseClass = "030623"
        //购买伪造的增值税专用发票案

        case "030631" =>
          caseClass = "030631"
        //非法制造用于骗取出口退税、抵扣税款发票案

        case "030632" =>
          caseClass = "030632"
        //出售非法制造的用于骗取出口退税、抵扣税款发票案

        case "030633" =>
          caseClass = "030633"
        //非法制造发票案

        case "030641" =>
          caseClass = "030641"
        //出售非法制造的发票案

        case "030651" =>
          caseClass = "030651"
        //非法出售用于骗取出口退税、抵扣税款发票案

        case "030661" =>
          caseClass = "030661"
        //非法出售发票案

        case "030662" =>
          caseClass = "030662"
        //盗窃增值税专用发票案

        case "030663" =>
          caseClass = "030663"
        //盗窃退税、抵扣税款专用发票案

        case "030700" =>
          caseClass = "030700"
        //侵犯知识产权案

        case "030701" =>
          caseClass = "030701"
        //假冒注册商标案

        case "030710" =>
          caseClass = "030710"
        //销售假冒注册商标的商品案

        case "030720" =>
          caseClass = "030720"
        //非法制造的注册商标标识案

        case "030730" =>
          caseClass = "030730"
        //非法出版物案

        case "030740" =>
          caseClass = "030740"
        //销售非法制造的注册商标标识案

        case "030750" =>
          caseClass = "030750"
        //假冒专利案

        case "030760" =>
          caseClass = "030760"
        //侵犯著作权案

        case "030770" =>
          caseClass = "030770"
        //销售侵权复制品案

        case "030780" =>
          caseClass = "030780"
        //侵犯商业秘密案

        case "030800" =>
          caseClass = "030800"
        //扰乱市场秩序案

        case "030801" =>
          caseClass = "030801"
        //损害商业信誉案

        case "030802" =>
          caseClass = "030802"
        //损害商品声誉案

        case "030803" =>
          caseClass = "030803"
        //虚假广告案

        case "030804" =>
          caseClass = "030804"
        //串通投标案

        case "030805" =>
          caseClass = "030805"
        //合同诈骗案

        case "030806" =>
          caseClass = "030806"
        //非法经营案

        case "030807" =>
          caseClass = "030807"
        //强迫交易案

        case "030811" =>
          caseClass = "030811"
        //伪造车票案

        case "030812" =>
          caseClass = "030812"
        //伪造船票案

        case "030813" =>
          caseClass = "030813"
        //伪造邮票案

        case "030819" =>
          caseClass = "030819"
        //伪造其他有价票证案

        case "030821" =>
          caseClass = "030821"
        //倒卖伪造车票案

        case "030822" =>
          caseClass = "030822"
        //倒卖伪造船票案

        case "030823" =>
          caseClass = "030823"
        //倒卖伪造邮票案

        case "030829" =>
          caseClass = "030829"
        //倒卖伪造其他有价票证案

        case "030831" =>
          caseClass = "030831"
        //倒卖车票案

        case "030832" =>
          caseClass = "030832"
        //倒卖船票案

        case "030841" =>
          caseClass = "030841"
        //非法转让土地使用权案

        case "030842" =>
          caseClass = "030842"
        //非法倒卖土地使用权案

        case "030851" =>
          caseClass = "030851"
        //中介组织人员提供虚假证明文件案

        case "030852" =>
          caseClass = "030852"
        //中介组织人员出具证明文件重大失实案

        case "030861" =>
          caseClass = "030861"
        //逃避商检案

        case "039900" =>
          caseClass = "039900"
        //其他破坏社会主义市场经济秩序案

        case "040000" =>
          caseClass = "040000"
        //侵犯公民人身权利、民主权利案

        case "040100" =>
          caseClass = "040100"
        //侵犯人身权利案

        case "040101" =>
          caseClass = "040101"
        //故意杀人案

        case "040102" =>
          caseClass = "040102"
        //过失致人死亡案

        case "040103" =>
          caseClass = "040103"
        //故意伤害案

        case "040104" =>
          caseClass = "040104"
        //过失致人重伤案

        case "040105" =>
          caseClass = "040105"
        //强奸案

        case "040106" =>
          caseClass = "040106"
        //奸淫幼女案

        case "040107" =>
          caseClass = "040107"
        //强制猥亵、侮辱妇女案

        case "040108" =>
          caseClass = "040108"
        //猥亵儿童案

        case "040109" =>
          caseClass = "040109"
        //非法拘禁案

        case "040110" =>
          caseClass = "040110"
        //绑架案

        case "040111" =>
          caseClass = "040111"
        //偷盗婴幼儿勒索案

        case "040112" =>
          caseClass = "040112"
        //拐卖妇女、儿童案

        case "040113" =>
          caseClass = "040113"
        //收买被拐卖的妇女、儿童案

        case "040114" =>
          caseClass = "040114"
        //聚众阻碍解救被收买的妇女、儿童案

        case "040115" =>
          caseClass = "040115"
        //暴力阻碍解救被收买的妇女、儿童案

        case "040116" =>
          caseClass = "040116"
        //强迫职工劳动案

        case "040117" =>
          caseClass = "040117"
        //非法搜查案

        case "040118" =>
          caseClass = "040118"
        //非法侵入住宅案

        case "040119" =>
          caseClass = "040119"
        //诬告陷害罪

        case "040120" =>
          caseClass = "040120"
        //侮辱案

        case "040121" =>
          caseClass = "040121"
        //诽谤案

        case "040122" =>
          caseClass = "040122"
        //刑讯逼供案

        case "040123" =>
          caseClass = "040123"
        //暴力取证案

        case "040124" =>
          caseClass = "040124"
        //虐待被监管人员案

        case "040200" =>
          caseClass = "040200"
        //侵犯民主权利案

        case "040210" =>
          caseClass = "040210"
        //侵犯通讯自由案

        case "040220" =>
          caseClass = "040220"
        //私拆邮件案

        case "040230" =>
          caseClass = "040230"
        //隐匿邮件电报案

        case "040240" =>
          caseClass = "040240"
        //毁弃邮件电报案

        case "040250" =>
          caseClass = "040250"
        //报复陷害案

        case "040260" =>
          caseClass = "040260"
        //打击报复会计人员案

        case "040270" =>
          caseClass = "040270"
        //打击报复统计人员案

        case "040280" =>
          caseClass = "040280"
        //破坏选举案

        case "040300" =>
          caseClass = "040300"
        //破坏民族平等、宗教信仰案

        case "040310" =>
          caseClass = "040310"
        //煽动民族仇恨、民族歧视案

        case "040320" =>
          caseClass = "040320"
        //出版歧视、侮辱少数民族作品案

        case "040330" =>
          caseClass = "040330"
        //非法剥夺公民宗教信仰自由案

        case "040340" =>
          caseClass = "040340"
        //侵犯少数民族风俗习惯案

        case "040400" =>
          caseClass = "040400"
        //妨害婚姻家庭权利案

        case "040410" =>
          caseClass = "040410"
        //暴力干涉婚姻自由案

        case "040420" =>
          caseClass = "040420"
        //重婚案

        case "040430" =>
          caseClass = "040430"
        //破坏军婚案

        case "040440" =>
          caseClass = "040440"
        //虐待案

        case "040450" =>
          caseClass = "040450"
        //遗弃案

        case "040460" =>
          caseClass = "040460"
        //拐骗儿童案

        case "049900" =>
          caseClass = "049900"
        //其他侵犯公民人身权利、民主权利案

        case "050000" =>
          caseClass = "050000"
        //侵犯财产案

        case "050100" =>
          caseClass = "050100"
        //抢劫案

        case "050101" =>
          caseClass = "050101"
        //入户抢劫案

        case "050102" =>
          caseClass = "050102"
        //拦路抢劫案

        case "050103" =>
          caseClass = "050103"
        //在公共交通工具上抢劫案

        case "050110" =>
          caseClass = "050110"
        //抢劫银行或其他金融机构案

        case "050111" =>
          caseClass = "050111"
        //抢劫珠宝店案

        case "050112" =>
          caseClass = "050112"
        //抢劫提(送)款员案

        case "050113" =>
          caseClass = "050113"
        //抢劫运钞车案

        case "050120" =>
          caseClass = "050120"
        //抢劫出租汽车案

        case "050130" =>
          caseClass = "050130"
        //抢劫军用物资案

        case "050131" =>
          caseClass = "050131"
        //抢劫抢险、救灾、救济物资案

        case "050132" =>
          caseClass = "050132"
        //抢劫牲畜案

        case "050140" =>
          caseClass = "050140"
        //抢劫精神药物和麻醉药品案

        case "050150" =>
          caseClass = "050150"
        //冒充军警持枪抢劫案

        case "050160" =>
          caseClass = "050160"
        //持枪抢劫案

        case "050200" =>
          caseClass = "050200"
        //盗窃案

        case "050201" =>
          caseClass = "050201"
        //入室盗窃案

        case "050202" =>
          caseClass = "050202"
        //盗窃精神药物和麻醉药品案

        case "050203" =>
          caseClass = "050203"
        //盗窃易制毒化学品案

        case "050204" =>
          caseClass = "050204"
        //盗窃金融机构案

        case "050210" =>
          caseClass = "050210"
        //盗窃运输物资案

        case "050211" =>
          caseClass = "050211"
        //盗窃铁路器材案

        case "050212" =>
          caseClass = "050212"
        //盗窃珍贵文物案

        case "050216" =>
          caseClass = "050216"
        //盗窃电脑芯片案

        case "050220" =>
          caseClass = "050220"
        //盗窃货物案

        case "050221" =>
          caseClass = "050221"
        //盗窃旅财案

        case "050222" =>
          caseClass = "050222"
        //盗窃路财案

        case "050223" =>
          caseClass = "050223"
        //盗窃汽车案

        case "050224" =>
          caseClass = "050224"
        //盗窃摩托车案

        case "050227" =>
          caseClass = "050227"
        //盗窃自行车案

        case "050230" =>
          caseClass = "050230"
        //盗窃保险柜案

        case "050235" =>
          caseClass = "050235"
        //盗用他人通讯设施案

        case "050236" =>
          caseClass = "050236"
        //盗接通信线路案

        case "050237" =>
          caseClass = "050237"
        //盗窃牲畜案

        case "050240" =>
          caseClass = "050240"
        //扒窃案

        case "050300" =>
          caseClass = "050300"
        //诈骗案

        case "050400" =>
          caseClass = "050400"
        //抢夺案

        case "050500" =>
          caseClass = "050500"
        //侵占案

        case "050600" =>
          caseClass = "050600"
        //职务侵占案

        case "050700" =>
          caseClass = "050700"
        //挪用特定款物案

        case "050710" =>
          caseClass = "050710"
        //挪用资金案

        case "050720" =>
          caseClass = "050720"
        //挪用公款案

        case "050730" =>
          caseClass = "050730"
        //挪用救灾、抢险、防汛款物案

        case "050740" =>
          caseClass = "050740"
        //挪用优抚款物案

        case "050750" =>
          caseClass = "050750"
        //挪用扶贫,移民救济款物案

        case "050800" =>
          caseClass = "050800"
        //敲诈勒索案

        case "050900" =>
          caseClass = "050900"
        //故意毁坏财物案

        case "051000" =>
          caseClass = "051000"
        //破坏生产经营案

        case "051100" =>
          caseClass = "051100"
        //聚众哄抢案

        case "060000" =>
          caseClass = "060000"
        //妨碍社会管理秩序案

        case "060100" =>
          caseClass = "060100"
        //扰乱公共秩序案

        case "060101" =>
          caseClass = "060101"
        //阻碍执行职务案

        case "060102" =>
          caseClass = "060102"
        //阻碍人大代表执行职务案

        case "060103" =>
          caseClass = "060103"
        //阻碍红十字会依法履行职责案

        case "060104" =>
          caseClass = "060104"
        //阻碍安全机关、公安机关执行职务案

        case "060105" =>
          caseClass = "060105"
        //煽动暴力抗拒法律实施案

        case "060106" =>
          caseClass = "060106"
        //招摇撞骗案

        case "060107" =>
          caseClass = "060107"
        //冒充国家工作人员招摇撞骗案

        case "060108" =>
          caseClass = "060108"
        //冒充警察招摇撞骗案

        case "060109" =>
          caseClass = "060109"
        //伪造、变造公文证件印章案

        case "060110" =>
          caseClass = "060110"
        //买卖公文、证件、印章案

        case "060111" =>
          caseClass = "060111"
        //盗窃、抢夺公文证件印章案

        case "060112" =>
          caseClass = "060112"
        //毁灭公文、证件、印章案

        case "060113" =>
          caseClass = "060113"
        //伪造、变造居民身份证案

        case "060114" =>
          caseClass = "060114"
        //非法生产警服、警用标志、警械案

        case "060115" =>
          caseClass = "060115"
        //非法买卖警服、警用标志、警械案

        case "060116" =>
          caseClass = "060116"
        //非法获取国家机密案

        case "060117" =>
          caseClass = "060117"
        //非法持有国家绝密、机密文件、资料、物品案

        case "060118" =>
          caseClass = "060118"
        //非法生产、销售间谍专用器材案

        case "060119" =>
          caseClass = "060119"
        //非法使用窃听器材案

        case "060120" =>
          caseClass = "060120"
        //非法使用窃照器材案

        case "060121" =>
          caseClass = "060121"
        //非法侵入计算机信息系统案

        case "060122" =>
          caseClass = "060122"
        //破坏计算机信息系统案

        case "060123" =>
          caseClass = "060123"
        //破坏计算机信息系统数据和应用程序案

        case "060124" =>
          caseClass = "060124"
        //故意制作传播计算机破坏性程序案

        case "060125" =>
          caseClass = "060125"
        //利用计算机金融诈骗犯罪案

        case "060126" =>
          caseClass = "060126"
        //利用计算机盗窃案

        case "060127" =>
          caseClass = "060127"
        //利用计算机贪污案

        case "060129" =>
          caseClass = "060129"
        //利用计算机窃取国家机密案

        case "060130" =>
          caseClass = "060130"
        //利用计算机实施其他犯罪案

        case "060131" =>
          caseClass = "060131"
        //扰乱无线电通讯管理秩序案

        case "060132" =>
          caseClass = "060132"
        //聚众扰乱社会秩序案

        case "060133" =>
          caseClass = "060133"
        //聚众冲击国家机关案

        case "060134" =>
          caseClass = "060134"
        //聚众扰乱公共场所秩序案

        case "060135" =>
          caseClass = "060135"
        //聚众扰乱交通秩序案

        case "060136" =>
          caseClass = "060136"
        //聚众斗殴案

        case "060137" =>
          caseClass = "060137"
        //寻衅滋事案

        case "060138" =>
          caseClass = "060138"
        //传授犯罪方法案

        case "060139" =>
          caseClass = "060139"
        //组织、领导黑社会性质组织案

        case "060140" =>
          caseClass = "060140"
        //参加黑社会性质组织案

        case "060141" =>
          caseClass = "060141"
        //入境发展黑社会组织案

        case "060142" =>
          caseClass = "060142"
        //包庇、纵容黑社会性质组织案

        case "060143" =>
          caseClass = "060143"
        //非法集会、游行、示威案

        case "060146" =>
          caseClass = "060146"
        //非法携带武器参加集会、游行、示威案

        case "060150" =>
          caseClass = "060150"
        //非法携带管制刀具参加集会、游行、示威案

        case "060154" =>
          caseClass = "060154"
        //非法携带爆炸物参加集会、游行、示威案

        case "060158" =>
          caseClass = "060158"
        //破坏集会、示威、游行案

        case "060160" =>
          caseClass = "060160"
        //侮辱国旗、国徽案

        case "060165" =>
          caseClass = "060165"
        //组织和利用会道门、邪教组织或者利用迷信破坏法律实施案

        case "060166" =>
          caseClass = "060166"
        //组织和利用会道门破坏法律实施案

        case "060167" =>
          caseClass = "060167"
        //组织和利用邪教组织破坏法律实施案

        case "060168" =>
          caseClass = "060168"
        //组织和利用迷信破坏法律实施案

        case "060170" =>
          caseClass = "060170"
        //组织和利用会道门、邪教组织或者利用迷信致人死亡案

        case "060171" =>
          caseClass = "060171"
        //组织和利用会道门致人死亡案

        case "060172" =>
          caseClass = "060172"
        //组织和利用邪教组织致人死亡案

        case "060173" =>
          caseClass = "060173"
        //组织和利用迷信致人死亡案

        case "060180" =>
          caseClass = "060180"
        //组织和利用会道门、邪教组织或者利用迷信奸淫妇女或诈骗钱财案

        case "060181" =>
          caseClass = "060181"
        //组织和利用会道门、邪教组织或者利用迷信奸淫妇女案

        case "060182" =>
          caseClass = "060182"
        //组织和利用会道门奸淫妇女案

        case "060183" =>
          caseClass = "060183"
        //组织和利用邪教组织奸淫妇女案

        case "060184" =>
          caseClass = "060184"
        //组织和利用迷信奸淫妇女案

        case "060185" =>
          caseClass = "060185"
        //组织和利用会道门、邪教组织或者利用迷信诈骗钱财案

        case "060186" =>
          caseClass = "060186"
        //组织和利用会道门诈骗钱财案

        case "060187" =>
          caseClass = "060187"
        //组织和利用邪教组织诈骗钱财案

        case "060188" =>
          caseClass = "060188"
        //组织和利用迷信诈骗钱财案

        case "060190" =>
          caseClass = "060190"
        //聚众淫乱案

        case "060193" =>
          caseClass = "060193"
        //引诱未成年人聚众淫乱案

        case "060196" =>
          caseClass = "060196"
        //盗窃、侮辱尸体案

        case "060197" =>
          caseClass = "060197"
        //赌博案

        case "060198" =>
          caseClass = "060198"
        //故意延误投递邮件案

        case "060200" =>
          caseClass = "060200"
        //妨害司法案

        case "060201" =>
          caseClass = "060201"
        //伪证案

        case "060211" =>
          caseClass = "060211"
        //辩护人、诉讼代理人毁灭、伪造证据案

        case "060212" =>
          caseClass = "060212"
        //帮助毁灭、伪造证据案

        case "060213" =>
          caseClass = "060213"
        //司法人员毁灭、伪造证据案

        case "060214" =>
          caseClass = "060214"
        //妨害作证案

        case "060215" =>
          caseClass = "060215"
        //打击报复证人案

        case "060221" =>
          caseClass = "060221"
        //扰乱法庭秩序案

        case "060222" =>
          caseClass = "060222"
        //窝藏、包庇案

        case "060223" =>
          caseClass = "060223"
        //拒绝提供间谍犯罪证据案

        case "060224" =>
          caseClass = "060224"
        //窝藏、转移、收购、销售赃物案

        case "060231" =>
          caseClass = "060231"
        //拒不执行判决、裁定案

        case "060241" =>
          caseClass = "060241"
        //非法处置查封、扣押、冻结财产案

        case "060251" =>
          caseClass = "060251"
        //破坏监管秩序案

        case "060252" =>
          caseClass = "060252"
        //脱逃案

        case "060253" =>
          caseClass = "060253"
        //劫夺被解押人员案

        case "060254" =>
          caseClass = "060254"
        //组织越狱案

        case "060255" =>
          caseClass = "060255"
        //暴动越狱案

        case "060256" =>
          caseClass = "060256"
        //聚众持械劫狱案

        case "060300" =>
          caseClass = "060300"
        //妨害国(边)境管理案

        case "060310" =>
          caseClass = "060310"
        //偷越国(边)境案

        case "060320" =>
          caseClass = "060320"
        //组织他人偷越国(边)境案

        case "060330" =>
          caseClass = "060330"
        //运送他人偷越国(边)境案

        case "060340" =>
          caseClass = "060340"
        //骗取出境证件案

        case "060350" =>
          caseClass = "060350"
        //提供伪造、变造的出入境证件案

        case "060360" =>
          caseClass = "060360"
        //出售出入境证件案

        case "060370" =>
          caseClass = "060370"
        //破坏界碑、界桩案

        case "060380" =>
          caseClass = "060380"
        //破坏永久性测量标志案

        case "060400" =>
          caseClass = "060400"
        //妨害文物管理案

        case "060401" =>
          caseClass = "060401"
        //故意毁坏文物案

        case "060402" =>
          caseClass = "060402"
        //过失毁坏文物案

        case "060411" =>
          caseClass = "060411"
        //非法向外国人出售珍贵文物案

        case "060421" =>
          caseClass = "060421"
        //倒卖文物案

        case "060422" =>
          caseClass = "060422"
        //非法出售文物藏品案

        case "060423" =>
          caseClass = "060423"
        //非法赠送文物藏品案

        case "060431" =>
          caseClass = "060431"
        //故意损毁名胜古迹案

        case "060441" =>
          caseClass = "060441"
        //盗掘古文化遗址案

        case "060442" =>
          caseClass = "060442"
        //盗掘古墓葬案

        case "060443" =>
          caseClass = "060443"
        //盗掘古人类化石、古脊椎动物化石案

        case "060451" =>
          caseClass = "060451"
        //抢夺国有档案案

        case "060452" =>
          caseClass = "060452"
        //窃取国有档案案

        case "060453" =>
          caseClass = "060453"
        //擅自出卖国家档案案

        case "060454" =>
          caseClass = "060454"
        //擅自转让国家档案案

        case "060500" =>
          caseClass = "060500"
        //危害公共卫生案

        case "060501" =>
          caseClass = "060501"
        //妨害传染病防治案

        case "060510" =>
          caseClass = "060510"
        //传染病菌种、毒种扩散案

        case "060520" =>
          caseClass = "060520"
        //妨害国境卫生检疫案

        case "060530" =>
          caseClass = "060530"
        //非法组织卖血案

        case "060531" =>
          caseClass = "060531"
        //强迫卖血案

        case "060532" =>
          caseClass = "060532"
        //非法采集、供应血液案

        case "060533" =>
          caseClass = "060533"
        //非法制作、供应血液制品案

        case "060534" =>
          caseClass = "060534"
        //采集、供应血液事故案

        case "060535" =>
          caseClass = "060535"
        //制作、供应血液制品事故案

        case "060540" =>
          caseClass = "060540"
        //医疗事故案

        case "060550" =>
          caseClass = "060550"
        //非法行医案

        case "060560" =>
          caseClass = "060560"
        //非法进行节育手术案

        case "060570" =>
          caseClass = "060570"
        //逃避动植物检疫案

        case "060600" =>
          caseClass = "060600"
        //破坏环境资源保护案

        case "060601" =>
          caseClass = "060601"
        //重大环境污染事故案

        case "060602" =>
          caseClass = "060602"
        //非法处置进口固体废物案

        case "060603" =>
          caseClass = "060603"
        //擅自进口固体废物案

        case "060611" =>
          caseClass = "060611"
        //非法捕猎、杀害珍贵、濒危野生动物案

        case "060612" =>
          caseClass = "060612"
        //非法收购珍贵、濒危野生动物及其制品案

        case "060613" =>
          caseClass = "060613"
        //非法运输珍贵、濒危野生动物及其制品案

        case "060614" =>
          caseClass = "060614"
        //非法出售珍贵、濒危野生动物及其制品案

        case "060621" =>
          caseClass = "060621"
        //非法采伐、毁坏珍贵树木案

        case "060622" =>
          caseClass = "060622"
        //盗伐林木案

        case "060623" =>
          caseClass = "060623"
        //滥伐林木案

        case "060624" =>
          caseClass = "060624"
        //非法收购盗伐、滥伐的林木案

        case "060631" =>
          caseClass = "060631"
        //非法捕捞水产品案

        case "060632" =>
          caseClass = "060632"
        //非法狩猎案

        case "060633" =>
          caseClass = "060633"
        //非法占用耕地案

        case "060634" =>
          caseClass = "060634"
        //非法采矿案

        case "060635" =>
          caseClass = "060635"
        //破坏性采矿案

        case "060700" =>
          caseClass = "060700"
        //走私贩卖、运输、制造毒品案

        case "060701" =>
          caseClass = "060701"
        //走私毒品案

        case "060702" =>
          caseClass = "060702"
        //贩卖毒品案

        case "060703" =>
          caseClass = "060703"
        //运输毒品案

        case "060704" =>
          caseClass = "060704"
        //制造毒品案

        case "060710" =>
          caseClass = "060710"
        //非法持有毒品案

        case "060720" =>
          caseClass = "060720"
        //包庇毒品犯罪分子案

        case "060721" =>
          caseClass = "060721"
        //窝藏、转移、隐瞒毒品、毒赃案

        case "060722" =>
          caseClass = "060722"
        //走私制毒物品案

        case "060730" =>
          caseClass = "060730"
        //非法买卖制毒物品案

        case "060731" =>
          caseClass = "060731"
        //非法种植毒品原植物案

        case "060732" =>
          caseClass = "060732"
        //非法买卖毒品原植物种苗案

        case "060733" =>
          caseClass = "060733"
        //非法运输毒品原植物种苗案

        case "060734" =>
          caseClass = "060734"
        //非法携带毒品原植物种苗案

        case "060735" =>
          caseClass = "060735"
        //非法持有毒品原植物种苗案

        case "060736" =>
          caseClass = "060736"
        //非法运输携带制毒毒品进出境案

        case "060740" =>
          caseClass = "060740"
        //引诱、教唆、强迫他人吸毒案

        case "060741" =>
          caseClass = "060741"
        //引诱他人吸毒案

        case "060742" =>
          caseClass = "060742"
        //教唆他人吸毒案

        case "060743" =>
          caseClass = "060743"
        //欺骗他人吸毒案

        case "060744" =>
          caseClass = "060744"
        //强迫他人吸毒案

        case "060745" =>
          caseClass = "060745"
        //容留他人吸毒案

        case "060750" =>
          caseClass = "060750"
        //非法提供麻醉药品案

        case "060760" =>
          caseClass = "060760"
        //非法提供精神药品案

        case "060800" =>
          caseClass = "060800"
        //组织、强迫、引诱、容留、介绍卖淫案

        case "060801" =>
          caseClass = "060801"
        //组织卖淫案

        case "060810" =>
          caseClass = "060810"
        //强迫卖淫案

        case "060820" =>
          caseClass = "060820"
        //协助组织卖淫案

        case "060830" =>
          caseClass = "060830"
        //引诱卖淫案

        case "060840" =>
          caseClass = "060840"
        //引诱幼女卖淫案

        case "060850" =>
          caseClass = "060850"
        //容留卖淫案

        case "060860" =>
          caseClass = "060860"
        //介绍卖淫案

        case "060870" =>
          caseClass = "060870"
        //传播性病案

        case "060880" =>
          caseClass = "060880"
        //嫖宿幼女案

        case "060900" =>
          caseClass = "060900"
        //制作、复制、出版、贩卖、传播淫秽物品牟利案

        case "060910" =>
          caseClass = "060910"
        //制作淫秽物品案

        case "060920" =>
          caseClass = "060920"
        //复制淫秽物品案

        case "060930" =>
          caseClass = "060930"
        //出版淫秽物品案

        case "060940" =>
          caseClass = "060940"
        //贩卖淫秽物品案

        case "060950" =>
          caseClass = "060950"
        //传播淫秽物品案

        case "060960" =>
          caseClass = "060960"
        //提供书号出版淫秽书刊案

        case "060970" =>
          caseClass = "060970"
        //组织播放淫秽音像制品案

        case "060980" =>
          caseClass = "060980"
        //组织淫秽表演案

        case "069900" =>
          caseClass = "069900"
        //其他妨害社会管理秩序案

        case "070000" =>
          caseClass = "070000"
        //危害国防利益案

        case "070100" =>
          caseClass = "070100"
        //阻碍军人执行职务案

        case "070200" =>
          caseClass = "070200"
        //阻碍军事行动案

        case "070300" =>
          caseClass = "070300"
        //破坏武器装备案

        case "070400" =>
          caseClass = "070400"
        //破坏军事设施案

        case "070500" =>
          caseClass = "070500"
        //破坏军事通信案

        case "070600" =>
          caseClass = "070600"
        //故意提供不合格武器装备案

        case "070700" =>
          caseClass = "070700"
        //故意提供不合格军事设施案

        case "070800" =>
          caseClass = "070800"
        //过失提供不合格武器装备案

        case "070900" =>
          caseClass = "070900"
        //过失提供不合格军事设施案

        case "071000" =>
          caseClass = "071000"
        //聚众冲击军事禁区案

        case "071100" =>
          caseClass = "071100"
        //聚众扰乱军事管理区秩序案

        case "071200" =>
          caseClass = "071200"
        //冒充军人招摇撞骗案

        case "071300" =>
          caseClass = "071300"
        //煽动逃离部队案

        case "071400" =>
          caseClass = "071400"
        //雇佣逃离部队军人案

        case "071500" =>
          caseClass = "071500"
        //接送不合格兵员案

        case "071600" =>
          caseClass = "071600"
        //伪造、变造、买卖部队公文、证件、印章案

        case "071700" =>
          caseClass = "071700"
        //盗窃部队公文、证件、印章案

        case "071800" =>
          caseClass = "071800"
        //抢夺部队公文、证件、印章案

        case "072000" =>
          caseClass = "072000"
        //非法生产、买卖军用标志案

        case "072100" =>
          caseClass = "072100"
        //战时拒绝、逃避征召案

        case "072200" =>
          caseClass = "072200"
        //战时拒绝、逃避军事训练案

        case "072300" =>
          caseClass = "072300"
        //战时拒绝、逃避服役案

        case "072400" =>
          caseClass = "072400"
        //战时故意提供虚假情报案

        case "072500" =>
          caseClass = "072500"
        //战时造谣惑众扰乱军心案

        case "072600" =>
          caseClass = "072600"
        //战时窝藏逃离部队军人案

        case "072700" =>
          caseClass = "072700"
        //战时拒绝军事订货案

        case "072800" =>
          caseClass = "072800"
        //战时延误军事订货案

        case "072900" =>
          caseClass = "072900"
        //战时拒绝军事征用案

        case "079900" =>
          caseClass = "079900"
        //其他危害国防利益案

        case "089900" =>
          caseClass = "080000"
        //贪污贿路案

        case "090000" =>
          caseClass = "090000"
        //渎职案

        case "100000" =>
          caseClass = "100000"
        //军人违反职责案

        case _ =>
          caseClass = code

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
