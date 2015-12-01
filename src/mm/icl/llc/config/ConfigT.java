


package mm.icl.llc.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * <pre>
 * &lt;complexType name="configT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AR" type="{}recognizerT"/>
 *         &lt;element name="Emotion" type="{}recognizerT"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "configT", propOrder = {
    "ar",
    "emotion"
})
public class ConfigT {

    @XmlElement(name = "AR", required = true)
    protected RecognizerT ar;
    @XmlElement(name = "Emotion", required = true)
    protected RecognizerT emotion;

    /**
     * 
     * @return
     *     possible object is
     *     {@link RecognizerT }
     *     
     */
    public RecognizerT getAR() {
        return ar;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link RecognizerT }
     *     
     */
    public void setAR(RecognizerT value) {
        this.ar = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link RecognizerT }
     *     
     */
    public RecognizerT getEmotion() {
        return emotion;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link RecognizerT }
     *     
     */
    public void setEmotion(RecognizerT value) {
        this.emotion = value;
    }

}
