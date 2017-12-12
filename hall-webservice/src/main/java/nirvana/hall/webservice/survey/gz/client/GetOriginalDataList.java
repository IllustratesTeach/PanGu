
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
 *         &lt;element name="UnitCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UpdateTimeFrom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UpdateTimeTo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StartNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EndNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "unitCode",
    "kNo",
    "updateTimeFrom",
    "updateTimeTo",
    "startNum",
    "endNum"
})
@XmlRootElement(name = "getOriginalDataList")
public class GetOriginalDataList {

    @XmlElement(name = "UserName", required = true, nillable = true)
    protected String userName;
    @XmlElement(name = "Passwrod", required = true, nillable = true)
    protected String passwrod;
    @XmlElement(name = "UnitCode", required = true, nillable = true)
    protected String unitCode;
    @XmlElement(name = "KNo", required = true, nillable = true)
    protected String kNo;
    @XmlElement(name = "UpdateTimeFrom", required = true, nillable = true)
    protected String updateTimeFrom;
    @XmlElement(name = "UpdateTimeTo", required = true, nillable = true)
    protected String updateTimeTo;
    @XmlElement(name = "StartNum")
    protected int startNum;
    @XmlElement(name = "EndNum")
    protected int endNum;

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
     * Gets the value of the unitCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * Sets the value of the unitCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitCode(String value) {
        this.unitCode = value;
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
     * Gets the value of the updateTimeFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateTimeFrom() {
        return updateTimeFrom;
    }

    /**
     * Sets the value of the updateTimeFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateTimeFrom(String value) {
        this.updateTimeFrom = value;
    }

    /**
     * Gets the value of the updateTimeTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateTimeTo() {
        return updateTimeTo;
    }

    /**
     * Sets the value of the updateTimeTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateTimeTo(String value) {
        this.updateTimeTo = value;
    }

    /**
     * Gets the value of the startNum property.
     * 
     */
    public int getStartNum() {
        return startNum;
    }

    /**
     * Sets the value of the startNum property.
     * 
     */
    public void setStartNum(int value) {
        this.startNum = value;
    }

    /**
     * Gets the value of the endNum property.
     * 
     */
    public int getEndNum() {
        return endNum;
    }

    /**
     * Sets the value of the endNum property.
     * 
     */
    public void setEndNum(int value) {
        this.endNum = value;
    }

}
