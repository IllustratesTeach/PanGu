package nirvana.hall.support.services

import java.io.{BufferedInputStream, ByteArrayInputStream}
import java.security.cert.{CertificateFactory, X509Certificate}
import java.util.regex.{Matcher, Pattern}

/**
  * pki信息解析类
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-06-03
  */
object PKIParser {
  case class PKIInfo(idCode: String, unitCode: String)
  case class PKIInfoWithName(name: String, idCode: String, unitCode: String)

  def main(args: Array[String]) {
    val sslClientCertContent: String = "-----BEGIN CERTIFICATE----- MIIDczCCAtygAwIBAgIQQGa5SHGlIiR5poPxldEJlTANBgkqhkiG9w0BAQUFADBD MQswCQYDVQQGEwJDTjELMAkGA1UECBMCNTExCzAJBgNVBAcTAjAwMQswCQYDVQQH EwIwMDENMAsGA1UEAxMEU0NDQTAeFw0xMTAyMjMwNzU0NTRaFw0xNjAxMjgwNzU0 NTRaMIGQMQswCQYDVQQGEwJDTjELMAkGA1UECBMCNTExCzAJBgNVBAcTAjAwMQsw CQYDVQQHEwIwMDELMAkGA1UEChMCNjAxCzAJBgNVBAsTAjAwMQswCQYDVQQLEwIw MDEzMDEGA1UEAx4qc4tPygAgADUAMQAwADcAOAAxADEAOQA3ADYAMAA0ADIAOQAz ADMAOQAyMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZ7r0z0x9MDhgl1PB1 s+JOez9CKT3lLVY2R/eMtzXKb6rwCbpoXyqu/NvqG2KLtIU4pksGFuaogE+V11v+ IfmA+pVS9ttTIwkgm3VcqmkG6HImWA/Ef4Brxa5+6IfeokwGB8Ib6s78MLA5wPTY TVUxsllNkiclUbJJcbPIu/kdrQIDAQABo4IBGDCCARQwHwYDVR0jBBgwFoAUCH25 aJXHR3R/kWpfHZ27m7qwvpswHQYDVR0OBBYEFFtYjoj267KLq0150GoFSWqZdMG8 MAsGA1UdDwQEAwIHgDAMBgNVHRMEBTADAQEAMGkGA1UdHwRiMGAwXqBcoFqkWDBW MQswCQYDVQQGEwJDTjELMAkGA1UECBMCNTExCzAJBgNVBAcTAjAwMQswCQYDVQQH EwIwMDENMAsGA1UEAxMEU0NDQTERMA8GA1UEAxMIY3JsNV8xODYwEgYIKlaZwvRx AAEEBhMEMjIwMDASBggqVpnC9HEAAgQGEwQwMWIwMBAGCCpWmcL0cQADBAQTAjQy MBIGCCpWmcL0cQAEBAYTBDAwMDMwDQYJKoZIhvcNAQEFBQADgYEAgcwkTjrhGRR9 bVcGTWCamxzyEmAZ55auJdQXHQ5eVXxnq4rYFGgX1RQtSjv+e/HcJol1OQWkULSC 369h75VVqtTtxQN2ea4PHgrPbHlEPafsGMtEkMT6BJdDzfE5EJhAd69QFjO97rAs KefLxYuJJLbCSLacNQw83oUZ6DnmVgE= -----END CERTIFICATE-----"
    val parse1 = parseCert(sslClientCertContent)
    System.out.println(parse1.idCode)
    System.out.println(parse1.name)
    System.out.println(parse1.unitCode)
    System.out.println("----------------")
    val sslClientSContent: String = "/C=CN/ST=51/L=01/L=08/O=99/OU=02/OU=00/CN=_ l\\xE2\\x00 \\x005\\x002\\x000\\x001\\x000\\x003\\x001\\x009\\x007\\x009\\x000\\x003\\x000\\x008\\x004\\x000\\x001\\x003"
    val parse2 = parseDN(sslClientSContent)
    System.out.println(parse2.idCode)
    System.out.println(parse2.unitCode)
  }

  private val valueMatcher = "CN=([^\\s]+)\\s+(\\d+),\\s+OU=(\\d+),\\s+OU=(\\d+),\\s+O=(\\d+),\\s+L=(\\d+),\\s+L=(\\d+),\\s+ST=(\\d+),\\s+C=CN".r

  def parseDN(dnContent: String): PKIInfo = {
    val idCode = dnContent.substring(dnContent.lastIndexOf(' ')).replaceAll("\\\\x00", "").split("/")(0).trim
    val pre1: String = dnContent.substring(0, dnContent.indexOf("/CN="))
    val regex: String = "[^0-9]"
    val pattren: Pattern = Pattern.compile(regex)
    val `match`: Matcher = pattren.matcher(pre1)
    val unitCode = `match`.replaceAll("").trim
    PKIInfo(idCode,unitCode)
  }

  def parseCert(certContent: String): PKIInfoWithName = {
    val array: Array[String] = certContent.split(" ")
    //将原字符串转换成正常的crt文件格式
    val newContent = array.zipWithIndex.map {
      case (line, i) =>
        if (i >= 1 && i < array.length - 2) {
          line + "\r\n"
        } else line + " "
    }.mkString("").trim

    val bi: BufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(newContent.getBytes("GBK")))
    val certFactory: CertificateFactory = CertificateFactory.getInstance("X.509")
    val cert: X509Certificate = certFactory.generateCertificate(bi).asInstanceOf[X509Certificate]
    val subjectContent = cert.getSubjectDN.getName
    //CN=张三 510722199541012021, OU=00, OU=00, O=60, L=00, L=00, ST=51, C=CN
    subjectContent match {
      case valueMatcher(cnName, zjhm, d1, d2, d3, d4, d5, d6) =>
        PKIInfoWithName(cnName,zjhm,d1 + d2 + d3 + d4 + d5 + d6)
      case other =>
        throw new IllegalStateException("invalid subject %s".format(other))
    }
  }
}

