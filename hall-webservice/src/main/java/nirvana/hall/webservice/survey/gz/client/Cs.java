
package nirvana.hall.webservice.survey.gz.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cs">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="xcwzbh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cs", propOrder = {
    "xcwzbh"
})
public class Cs {

    protected String xcwzbh;

    /**
     * Gets the value of the xcwzbh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXcwzbh() {
        return xcwzbh;
    }

    /**
     * Sets the value of the xcwzbh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXcwzbh(String value) {
        this.xcwzbh = value;
    }

}
