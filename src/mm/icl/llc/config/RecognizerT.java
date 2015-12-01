
package mm.icl.llc.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * <pre>
 * &lt;complexType name="recognizerT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataTypes" type="{}stringlistT"/>
 *         &lt;element name="Preprocessing" type="{}stringlistT"/>
 *         &lt;element name="Classification" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recognizerT", propOrder = {
    "dataTypes",
    "preprocessing",
    "classification"
})
public class RecognizerT {

    @XmlList
    @XmlElement(name = "DataTypes", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected List<String> dataTypes;
    @XmlList
    @XmlElement(name = "Preprocessing", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected List<String> preprocessing;
    @XmlElement(name = "Classification", required = true)
    protected String classification;

    /**
     * Gets the value of the dataTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDataTypes() {
        if (dataTypes == null) {
            dataTypes = new ArrayList<String>();
        }
        return this.dataTypes;
    }

    /**
     * Gets the value of the preprocessing property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the preprocessing property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPreprocessing().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPreprocessing() {
        if (preprocessing == null) {
            preprocessing = new ArrayList<String>();
        }
        return this.preprocessing;
    }

    /**
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassification() {
        return classification;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassification(String value) {
        this.classification = value;
    }

}
