package nirvana.hall.support.services

import java.io.{BufferedInputStream, ByteArrayInputStream}
import java.security.cert.{CertificateFactory, X509Certificate}
import java.util.regex.Pattern

/**
  * pki信息解析类,本解析主要是解析NGINX配置好的SSL
  *
  *一、制作负载均衡使用的证书文件
  *	从相关部门获取对应的证书文件(my.jks)、证书的密码(mypass)、证书对应的名称，通常为IP或者域名(myalias)，
  *	通常jks文件包含了服务器的私钥(server.key)，服务器证书(server.crt)、公安根证书(ca.crt)
  *二、通过java的keytool工具导出成 PKCS12 格式文件(my.p12)
  *
  * 查看证书中对应的名称
  * keytool -list -v -keystore my.jks
  *
  *	keytool -importkeystore -srckeystore my.jks -destkeystore my.p12 -srcstoretype JKS -deststoretype PKCS12  \
  *		-srcstorepass mypass -deststorepass mypass -srcalias myalias -destalias myalias -srckeypass mypass -destkeypass mypass -noprompt
  *
  * 如果证书的Certificate chain length为1,则需要使用下面语句
  *	keytool -importkeystore -srckeystore my.jks -destkeystore my.p12 -srcstoretype JKS -deststoretype PKCS12  \
  *		-srcstorepass mypass -deststorepass mypass
  *
  *	上述命令在一行执行
  *
  *三、使用openssl把PKCS12文件(my.p12)转换成我们需要的pem文件(my.pem)
  *
  *	openssl pkcs12 -in my.p12 -out my.pem -passin pass:mypass -passout pass:mypass
  *
  *四、分离my.pem文件,导出服务器配置所需文件
  *	my.pem一个文件里面实际上包含了服务器做双向SSL证书认证的所有文件,打开my.pem能查看所有的key和证书
  *
  *	得到配置服务器使用的 server.crt,server.key,ca.crt 文件,上述访问的命令分别为：
  *	openssl pkcs12 -in my.p12  -nodes -nocerts -out server.key
  *	openssl pkcs12 -in my.p12  -nodes -nokeys -clcerts -out server.crt
  *	openssl pkcs12 -in my.p12  -nodes -nokeys -cacerts -out ca.crt
  *
  *
  *
  * 五、配置NGINX
  *		ssl  on;
  *		ssl_certificate  /path/to/server.crt;
  *		ssl_certificate_key  /path/to/server.key;
  *		ssl_client_certificate /path/to/ca.crt;
  *		ssl_verify_client on;
  *	  ssl_ciphers  ALL:!ADH:!EXPORT56:RC4+RSA:+HIGH:+MEDIUM:+LOW:+SSLv2:+EXP;
  *   ssl_prefer_server_ciphers   on;
  *   ssl_verify_depth        10;
  *
  *		# 此行方便在java中能够得到证书信息
  *		proxy_set_header    X-Forwarded-Proto https;
  *	  proxy_set_header X-Client-Cert $ssl_client_cert;
  *	  proxy_set_header X-Client-DN $ssl_client_s_dn;
  *
  * 六、上述配置后，在重新启动服务器的时候，老是让你输入私有key的密码，为此
  *
  * openssl rsa -in server.key -out server.key.unsecure
  *    修改NGINX配置：
  *    ssl_certificate_key  /path/to/server.key.unsecure;
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-06-03
  */
object PKIParser {
  case class PKIInfo(idCode: String, unitCode: String)
  case class PKIInfoWithName(name: String, idCode: String, unitCode: String)

  def main(args: Array[String]) {
    val sslClientCertContent: String = "-----BEGIN CERTIFICATE----- MIIDczCCAtygAwIBAgIQQGa5SHGlIiR5poPxldEJlTANBgkqhkiG9w0BAQUFADBD MQswCQYDVQQGEwJDTjELMAkGA1UECBMCNTExCzAJBgNVBAcTAjAwMQswCQYDVQQH EwIwMDENMAsGA1UEAxMEU0NDQTAeFw0xMTAyMjMwNzU0NTRaFw0xNjAxMjgwNzU0 NTRaMIGQMQswCQYDVQQGEwJDTjELMAkGA1UECBMCNTExCzAJBgNVBAcTAjAwMQsw CQYDVQQHEwIwMDELMAkGA1UEChMCNjAxCzAJBgNVBAsTAjAwMQswCQYDVQQLEwIw MDEzMDEGA1UEAx4qc4tPygAgADUAMQAwADcAOAAxADEAOQA3ADYAMAA0ADIAOQAz ADMAOQAyMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZ7r0z0x9MDhgl1PB1 s+JOez9CKT3lLVY2R/eMtzXKb6rwCbpoXyqu/NvqG2KLtIU4pksGFuaogE+V11v+ IfmA+pVS9ttTIwkgm3VcqmkG6HImWA/Ef4Brxa5+6IfeokwGB8Ib6s78MLA5wPTY TVUxsllNkiclUbJJcbPIu/kdrQIDAQABo4IBGDCCARQwHwYDVR0jBBgwFoAUCH25 aJXHR3R/kWpfHZ27m7qwvpswHQYDVR0OBBYEFFtYjoj267KLq0150GoFSWqZdMG8 MAsGA1UdDwQEAwIHgDAMBgNVHRMEBTADAQEAMGkGA1UdHwRiMGAwXqBcoFqkWDBW MQswCQYDVQQGEwJDTjELMAkGA1UECBMCNTExCzAJBgNVBAcTAjAwMQswCQYDVQQH EwIwMDENMAsGA1UEAxMEU0NDQTERMA8GA1UEAxMIY3JsNV8xODYwEgYIKlaZwvRx AAEEBhMEMjIwMDASBggqVpnC9HEAAgQGEwQwMWIwMBAGCCpWmcL0cQADBAQTAjQy MBIGCCpWmcL0cQAEBAYTBDAwMDMwDQYJKoZIhvcNAQEFBQADgYEAgcwkTjrhGRR9 bVcGTWCamxzyEmAZ55auJdQXHQ5eVXxnq4rYFGgX1RQtSjv+e/HcJol1OQWkULSC 369h75VVqtTtxQN2ea4PHgrPbHlEPafsGMtEkMT6BJdDzfE5EJhAd69QFjO97rAs KefLxYuJJLbCSLacNQw83oUZ6DnmVgE= -----END CERTIFICATE-----"
    //val sslClientCertContent: String = "-----BEGIN CERTIFICATE-----\tMIIDiTCCAvKgAwIBAgIQeT+cZ0/ChOmO7zFEEtmBaTANBgkqhkiG9w0BAQUFADBD\tMQswCQYDVQQGEwJDTjELMAkGA1UECBMCMzExCzAJBgNVBAcTAjAwMQswCQYDVQQH\tEwIwMDENMAsGA1UEAxMEU0hDQTAeFw0xNTEyMjgxNjAwMDFaFw0yMDEyMDExNjAw\tMDFaMIGSMQswCQYDVQQGEwJDTjELMAkGA1UECBMCMzExCzAJBgNVBAcTAjAwMQsw\tCQYDVQQHEwIwMDELMAkGA1UEChMCMDUxCzAJBgNVBAsTAjE2MQswCQYDVQQLEwIw\tMDE1MDMGA1UEAx4smH6AAGVPACAAMwAxADAAMQAwADcAMQA5ADYAMQAwADEAMgA5\tADIAMAA3ADQwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMWxQGK24/XrV8lb\tUdchAKZ9OwF7RWit19Nm8fwu2xw8jVgIiQtiZIpt/qwLDU0xa21rkXP3tKtYevvJ\tJFQFD7y23haD50UFy7ztxA/+ymrIHMz9x25gZk2dhW13ihr1je8sLHR+iQUsy9wT\tko6kczHt1PolU8LTxfG0Lq0/K5iNAgMBAAGjggEsMIIBKDAfBgNVHSMEGDAWgBRY\t48HFWstaeL2og617LIO9XAFz7jAdBgNVHQ4EFgQUADbh8MQtzte89lf8uLJ6C+Aq\t6JQwCwYDVR0PBAQDAgeAMAwGA1UdEwQFMAMBAQAwaQYDVR0fBGIwYDBeoFygWqRY\tMFYxCzAJBgNVBAYTAkNOMQswCQYDVQQIEwIzMTELMAkGA1UEBxMCMDAxCzAJBgNV\tBAcTAjAwMQ0wCwYDVQQDEwRTSENBMREwDwYDVQQDEwhjcmw1XzIyODASBggqVpnC\t9HEABQQGEwQxMTAxMBIGCCpWmcL0cQABBAYTBDEzMDgwEgYIKlaZwvRxAAIEBhME\tMDE5MTAQBggqVpnC9HEAAwQEEwIwNTASBggqVpnC9HEABAQGEwQwMDAzMA0GCSqG\tSIb3DQEBBQUAA4GBAC9JHNbnsHpM312RGRXc+hE635bLDw5KfwdXeHPttUBTb4wj\tJRbh6GfqCR+HIdsFInLi8LsviPweHT7MeM810UxiwPBAqEA2jIQmz957MHLgcFW5\teNgK2Ht/cUx/4kk7olDkESsEU7P1estM1M8CSCs03Tb0orDaUl+e1ZRV3eaF\t-----END CERTIFICATE-----"
    //val sslClientCertContent: String = "-----BEGIN CERTIFICATE----- MIIDiTCCAvKgAwIBAgIQeT+cZ0/ChOmO7zFEEtmBaTANBgkqhkiG9w0BAQUFADBD        MQswCQYDVQQGEwJDTjELMAkGA1UECBMCMzExCzAJBgNVBAcTAjAwMQswCQYDVQQH        EwIwMDENMAsGA1UEAxMEU0hDQTAeFw0xNTEyMjgxNjAwMDFaFw0yMDEyMDExNjAw     MDFaMIGSMQswCQYDVQQGEwJDTjELMAkGA1UECBMCMzExCzAJBgNVBAcTAjAwMQsw        CQYDVQQHEwIwMDELMAkGA1UEChMCMDUxCzAJBgNVBAsTAjE2MQswCQYDVQQLEwIw    MDE1MDMGA1UEAx4smH6AAGVPACAAMwAxADAAMQAwADcAMQA5ADYAMQAwADEAMgA5 ADIAMAA3ADQwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMWxQGK24/XrV8lb        UdchAKZ9OwF7RWit19Nm8fwu2xw8jVgIiQtiZIpt/qwLDU0xa21rkXP3tKtYevvJ     JFQFD7y23haD50UFy7ztxA/+ymrIHMz9x25gZk2dhW13ihr1je8sLHR+iQUsy9wT        ko6kczHt1PolU8LTxfG0Lq0/K5iNAgMBAAGjggEsMIIBKDAfBgNVHSMEGDAWgBRY        48HFWstaeL2og617LIO9XAFz7jAdBgNVHQ4EFgQUADbh8MQtzte89lf8uLJ6C+Aq     6JQwCwYDVR0PBAQDAgeAMAwGA1UdEwQFMAMBAQAwaQYDVR0fBGIwYDBeoFygWqRY        MFYxCzAJBgNVBAYTAkNOMQswCQYDVQQIEwIzMTELMAkGA1UEBxMCMDAxCzAJBgNV        BAcTAjAwMQ0wCwYDVQQDEwRTSENBMREwDwYDVQQDEwhjcmw1XzIyODASBggqVpnC     9HEABQQGEwQxMTAxMBIGCCpWmcL0cQABBAYTBDEzMDgwEgYIKlaZwvRxAAIEBhME        MDE5MTAQBggqVpnC9HEAAwQEEwIwNTASBggqVpnC9HEABAQGEwQwMDAzMA0GCSqG        SIb3DQEBBQUAA4GBAC9JHNbnsHpM312RGRXc+hE635bLDw5KfwdXeHPttUBTb4wj     JRbh6GfqCR+HIdsFInLi8LsviPweHT7MeM810UxiwPBAqEA2jIQmz957MHLgcFW5        eNgK2Ht/cUx/4kk7olDkESsEU7P1estM1M8CSCs03Tb0orDaUl+e1ZRV3eaF    -----END CERTIFICATE-----";
    //val sslClientCertContent : String = "-----BEGIN CERTIFICATE----- MIIDiTCCAvKgAwIBAgIQeT+cZ0/ChOmO7zFEEtmBaTANBgkqhkiG9w0BAQUFADBD        MQswCQYDVQQGEwJDTjELMAkGA1UECBMCMzExCzAJBgNVBAcTAjAwMQswCQYDVQQH        EwIwMDENMAsGA1UEAxMEU0hDQTAeFw0xNTEyMjgxNjAwMDFaFw0yMDEyMDExNjAw     MDFaMIGSMQswCQYDVQQGEwJDTjELMAkGA1UECBMCMzExCzAJBgNVBAcTAjAwMQsw        CQYDVQQHEwIwMDELMAkGA1UEChMCMDUxCzAJBgNVBAsTAjE2MQswCQYDVQQLEwIw    MDE1MDMGA1UEAx4smH6AAGVPACAAMwAxADAAMQAwADcAMQA5ADYAMQAwADEAMgA5 ADIAMAA3ADQwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMWxQGK24/XrV8lb        UdchAKZ9OwF7RWit19Nm8fwu2xw8jVgIiQtiZIpt/qwLDU0xa21rkXP3tKtYevvJ     JFQFD7y23haD50UFy7ztxA/+ymrIHMz9x25gZk2dhW13ihr1je8sLHR+iQUsy9wT        ko6kczHt1PolU8LTxfG0Lq0/K5iNAgMBAAGjggEsMIIBKDAfBgNVHSMEGDAWgBRY        48HFWstaeL2og617LIO9XAFz7jAdBgNVHQ4EFgQUADbh8MQtzte89lf8uLJ6C+Aq     6JQwCwYDVR0PBAQDAgeAMAwGA1UdEwQFMAMBAQAwaQYDVR0fBGIwYDBeoFygWqRY        MFYxCzAJBgNVBAYTAkNOMQswCQYDVQQIEwIzMTELMAkGA1UEBxMCMDAxCzAJBgNV        BAcTAjAwMQ0wCwYDVQQDEwRTSENBMREwDwYDVQQDEwhjcmw1XzIyODASBggqVpnC     9HEABQQGEwQxMTAxMBIGCCpWmcL0cQABBAYTBDEzMDgwEgYIKlaZwvRxAAIEBhME        MDE5MTAQBggqVpnC9HEAAwQEEwIwNTASBggqVpnC9HEABAQGEwQwMDAzMA0GCSqG        SIb3DQEBBQUAA4GBAC9JHNbnsHpM312RGRXc+hE635bLDw5KfwdXeHPttUBTb4wj     JRbh6GfqCR+HIdsFInLi8LsviPweHT7MeM810UxiwPBAqEA2jIQmz957MHLgcFW5        eNgK2Ht/cUx/4kk7olDkESsEU7P1estM1M8CSCs03Tb0orDaUl+e1ZRV3eaF    -----END CERTIFICATE-----";

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
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(pre1)
    val unitCode = matcher.replaceAll("").trim
    PKIInfo(idCode,unitCode)
  }

  def parseCert(certContent: String): PKIInfoWithName = {
    val c = certContent.replaceAll("\t"," ").replaceAll("( )+"," ")
    val array: Array[String] = c.split(" ")
    //将原字符串转换成正常的crt文件格式
    val newContent = array.zipWithIndex.map {
      case (line, i) =>
        if (i >= 1 && i < array.length - 2) {
          line + "\r\n"
        } else line + " "
    }.mkString("").trim
    //println(newContent)
    val bi: BufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(newContent.getBytes("GBK")))
    val certFactory: CertificateFactory = CertificateFactory.getInstance("X.509")
    val cert: X509Certificate = certFactory.generateCertificate(bi).asInstanceOf[X509Certificate]
    val subjectContent = cert.getSubjectDN.getName
    //CN=张三 510722199541012021, OU=00, OU=00, O=60, L=00, L=00, ST=51, C=CN
    subjectContent match {
      case valueMatcher(cnName, zjhm, d1, d2, d3, d4, d5, d6) =>
        PKIInfoWithName(cnName,zjhm,d6+d1 + d2 + d3 + d4 + d5)
      case other =>
        throw new IllegalStateException("invalid subject %s".format(other))
    }
  }
}

