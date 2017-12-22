
package nirvana.hall.webservice.survey.gz.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Passwrod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Cardtype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CardNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Result_type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userName",
    "passwrod",
    "kNo",
    "sNo",
    "cardtype",
    "cardNo",
    "resultType"
})
@XmlRootElement(name = "FBUseCondition")
public class FBUseCondition {

    @XmlElement(name = "UserName", required = true, nillable = true)
    protected String userName;
    @XmlElement(name = "Passwrod", required = true, nillable = true)
    protected String passwrod;
    @XmlElement(name = "KNo", required = true, nillable = true)
    protected String kNo;
    @XmlElement(name = "SNo", required = true, nillable = true)
    protected String sNo;
    @XmlElement(name = "Cardtype", required = true, nillable = true)
    protected String cardtype;
    @XmlElement(name = "CardNo", required = true, nillable = true)
    protected String cardNo;
    @XmlElement(name = "Result_type", required = true, nillable = true)
    protected String resultType;

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the passwrod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPasswrod() {
        return passwrod;
    }

    /**
     * Sets the value of the passwrod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPasswrod(String value) {
        this.passwrod = value;
    }

    /**
     * Gets the value of the kNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKNo() {
        return kNo;
    }

    /**
     * Sets the value of the kNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKNo(String value) {
        this.kNo = value;
    }

    /**
     * Gets the value of the sNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSNo() {
        return sNo;
    }

    /**
     * Sets the value of the sNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSNo(String value) {
        this.sNo = value;
    }

    /**
     * Gets the value of the cardtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardtype() {
        return cardtype;
    }

    /**
     * Sets the value of the cardtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardtype(String value) {
        this.cardtype = value;
    }

    /**
     * Gets the value of the cardNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * Sets the value of the cardNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNo(String value) {
        this.cardNo = value;
    }

    /**
     * Gets the value of the resultType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * Sets the value of the resultType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultType(String value) {
        this.resultType = value;
    }

}
