
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
    "kNo"
})
@XmlRootElement(name = "getCaseNo")
public class GetCaseNo {

    @XmlElement(name = "UserName", required = true, nillable = true)
    protected String userName;
    @XmlElement(name = "Passwrod", required = true, nillable = true)
    protected String passwrod;
    @XmlElement(name = "KNo", required = true, nillable = true)
    protected String kNo;

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

}
