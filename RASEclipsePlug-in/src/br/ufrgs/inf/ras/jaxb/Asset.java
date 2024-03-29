//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.14 at 12:55:41 AM BRST 
//


package br.ufrgs.inf.ras.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.PlatformObject;

/**
 * xmi:ordered=true
 * 
 * association order is (these are role names)
 *  1. profile
 *  2. description
 *  3. classification
 *  4. solution
 *  5. usage
 *  6. relatedAsset
 * 
 * This is a descriptive container for an asset's artifacts. The artifacts may include models, source code, requirements, test cases, documentation, and so on.
 * 
 * Every RAS manifest document begins with a single Asset element.  This element defines the identity of the reusable software asset.
 * 
 * An asset package is always in RAS format.  However, it may not be RAS-compliant, meaning it passes all integrity constraints as documented in RAS (e.g., required content supplied).
 * 
 * An asset package contains or references the artifacts of the asset itself. The artifacts of the asset are the things that are actually reused. Some artifacts are descriptive content which helps the Asset Conumer understand the asset, and provide guidance on how to apply the asset. This guidance may be in the form of documentation, or may be executable install programs or scripts that automate the injection of an asset's artifacts into another project (such binaries can be included as part of the descriptive content of the asset's package).
 * 
 * Assets vary in their size, complexity, and variability.
 * 
 * The asset package, as a whole, should provide enough information to allow the Asset Consumer to decide if he/she wants to purchase/use the asset.
 * 
 * <p>Java class for Asset complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Asset">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="classification" type="{http://defaultprofile.ecore}Classification" minOccurs="0"/>
 *         &lt;element name="solution" type="{http://defaultprofile.ecore}Solution"/>
 *         &lt;element name="usage" type="{http://defaultprofile.ecore}Usage" minOccurs="0"/>
 *         &lt;element name="relatedAsset" type="{http://defaultprofile.ecore}RelatedAsset" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="profile" type="{http://defaultprofile.ecore}Profile"/>
 *         &lt;element name="description" type="{http://defaultprofile.ecore}Description" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="state" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="accessRights" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="shortDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Asset", propOrder = {
    "classification",
    "solution",
    "usage",
    "relatedAssets",
    "profile",
    "description"
})
@XmlRootElement(name = "Asset")
public class Asset extends PlatformObject {

    protected Classification classification;
    @XmlElement(required = true)
    protected Solution solution;
    protected Usage usage;
    @XmlElement(name = "relatedAsset")
    protected List<RelatedAsset> relatedAssets;
    @XmlElement(required = true)
    protected Profile profile;
    protected Description description;
    @XmlAttribute
    protected String name;
    @XmlAttribute
    protected String id;
    @XmlAttribute
    protected String date;
    @XmlAttribute
    protected String state;
    @XmlAttribute
    protected String version;
    @XmlAttribute
    protected String accessRights;
    @XmlAttribute
    protected String shortDescription;
    @XmlTransient
    private ListenerList listeners;

    /**
     * Gets the value of the classification property.
     * 
     * @return
     *     possible object is
     *     {@link Classification }
     *     
     */
    public Classification getClassification() {
        return classification;
    }

    /**
     * Sets the value of the classification property.
     * 
     * @param value
     *     allowed object is
     *     {@link Classification }
     *     
     */
    public void setClassification(Classification value) {
        this.classification = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the solution property.
     * 
     * @return
     *     possible object is
     *     {@link Solution }
     *     
     */
    public Solution getSolution() {
        return solution;
    }

    /**
     * Sets the value of the solution property.
     * 
     * @param value
     *     allowed object is
     *     {@link Solution }
     *     
     */
    public void setSolution(Solution value) {
        this.solution = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the usage property.
     * 
     * @return
     *     possible object is
     *     {@link Usage }
     *     
     */
    public Usage getUsage() {
        return usage;
    }

    /**
     * Sets the value of the usage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Usage }
     *     
     */
    public void setUsage(Usage value) {
        this.usage = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the relatedAssets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relatedAssets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelatedAssets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedAsset }
     * 
     * 
     */
    public List<RelatedAsset> getRelatedAssets() {
        if (relatedAssets == null) {
            relatedAssets = new ArrayList<RelatedAsset>();
        }
        return this.relatedAssets;
    }

    /**
     * Gets the value of the profile property.
     * 
     * @return
     *     possible object is
     *     {@link Profile }
     *     
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Sets the value of the profile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Profile }
     *     
     */
    public void setProfile(Profile value) {
        this.profile = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Description }
     *     
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Description }
     *     
     */
    public void setDescription(Description value) {
        this.description = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the accessRights property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessRights() {
        return accessRights;
    }

    /**
     * Sets the value of the accessRights property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessRights(String value) {
        this.accessRights = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the shortDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortDescription(String value) {
        this.shortDescription = value;
        firePropertyChange();
    }

    public void addListener(IListener listener) {
        if (listeners == null) {
            listeners = new ListenerList();
        }
        listeners.add(listener);
    }

    public void removeListener(IListener listener) {
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                listeners = null;
            }
        }
    }

    private void firePropertyChange() {
        if (listeners != null) {
            for (Object listener : listeners.getListeners()) {
                ((IListener) listener).objectChanged();
            }
        }
    }
}
