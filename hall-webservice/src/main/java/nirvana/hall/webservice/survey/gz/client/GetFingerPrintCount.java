
package nirvana.hall.webservice.survey.gz.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getFingerPrintCount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getFingerPrintCount">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="asjfsdd_xzqhdm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zzhwlx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xckybh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kssj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jssj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getFingerPrintCount", propOrder = {
    "userID",
    "password",
    "asjfsddXzqhdm",
    "zzhwlx",
    "xckybh",
    "kssj",
    "jssj"
})
public class GetFingerPrintCount {

    protected String userID;
    protected String password;
    @XmlElement(name = "asjfsdd_xzqhdm")
    protected String asjfsddXzqhdm;
    protected String zzhwlx;
    protected String xckybh;
    protected String kssj;
    protected String jssj;

    /**
     * Gets the value of the userID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the asjfsddXzqhdm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsjfsddXzqhdm() {
        return asjfsddXzqhdm;
    }

    /**
     * Sets the value of the asjfsddXzqhdm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsjfsddXzqhdm(String value) {
        this.asjfsddXzqhdm = value;
    }

    /**
     * Gets the value of the zzhwlx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZzhwlx() {
        return zzhwlx;
    }

    /**
     * Sets the value of the zzhwlx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZzhwlx(String value) {
        this.zzhwlx = value;
    }

    /**
     * Gets the value of the xckybh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXckybh() {
        return xckybh;
    }

    /**
     * Sets the value of the xckybh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXckybh(String value) {
        this.xckybh = value;
    }

    /**
     * Gets the value of the kssj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKssj() {
        return kssj;
    }

    /**
     * Sets the value of the kssj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKssj(String value) {
        this.kssj = value;
    }

    /**
     * Gets the value of the jssj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJssj() {
        return jssj;
    }

    /**
     * Sets the value of the jssj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJssj(String value) {
        this.jssj = value;
    }

}
