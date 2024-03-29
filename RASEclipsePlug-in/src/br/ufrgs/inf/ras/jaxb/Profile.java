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


/**
 * xmi:ordered=true
 * 
 * property order is (these are role names)
 *  1. description
 *  2. relatedProfile
 * 
 * An asset is defined by one profile; a profile describes the asset's type. The profile can have different versions and should declare its lineage or ancestry from other profiles. The related-profile captures information on the profile's lineage.
 * 
 * The Profile element in the XML schema includes information about the format of the manifest itself.  It identifies exactly which version of this specification and which RAS profile should be used to validate the manifest document for compliance.  A profile defines the structure and semantics of an asset's manifest document.  Every RAS manifest document must identify the profile that can be used to validate it.  Every profile is derived from another profile with the one exception being the original Core profile, which was defined by the first version of the RAS and for which there is no XML Schema produced. Profiles can extend directly from Core RAS or from any other profile such as the Default Profile for version 2.1. These derived profiles can only add elements and attributes to the manifest's XML Schema, and/or associate new semantics to existing elements.  They cannot remove elements or attributes from the XML Schema.  In general derived profiles are more restrictive. This attempts to make it easier for tools to gracefully handle assets created with profiles defined after the tooling was created.
 * 
 * <p>Java class for Profile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Profile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://defaultprofile.ecore}Description" minOccurs="0"/>
 *         &lt;element name="relatedProfile" type="{http://defaultprofile.ecore}RelatedProfile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="history" type="{http://defaultprofile.ecore}Description" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="idHistory" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="versionMajor" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="versionMinor" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="artifact" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="element" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="classificationSchema" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="dependencyKind" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="requiredElement" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="requiredAttribute" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="semanticConstraint" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Profile", propOrder = {
    "description",
    "relatedProfiles",
    "histories"
})
@XmlRootElement(name = "Profile")
public class Profile {

    protected Description description;
    @XmlElement(name = "relatedProfile")
    protected List<RelatedProfile> relatedProfiles;
    @XmlElement(name = "history")
    protected List<Description> histories;
    @XmlAttribute
    protected String name;
    @XmlAttribute
    protected String idHistory;
    @XmlAttribute
    protected Integer versionMajor;
    @XmlAttribute
    protected Integer versionMinor;
    @XmlAttribute
    protected String artifact;
    @XmlAttribute
    protected String element;
    @XmlAttribute
    protected String classificationSchema;
    @XmlAttribute
    protected String dependencyKind;
    @XmlAttribute
    protected String requiredElement;
    @XmlAttribute
    protected String requiredAttribute;
    @XmlAttribute
    protected String semanticConstraint;
    @XmlTransient
    private ListenerList listeners;
    
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
     * Gets the value of the relatedProfiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relatedProfiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelatedProfiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedProfile }
     * 
     * 
     */
    public List<RelatedProfile> getRelatedProfiles() {
        if (relatedProfiles == null) {
            relatedProfiles = new ArrayList<RelatedProfile>();
        }
        return this.relatedProfiles;
    }

    /**
     * Gets the value of the histories property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the histories property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHistories().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Description }
     * 
     * 
     */
    public List<Description> getHistories() {
        if (histories == null) {
            histories = new ArrayList<Description>();
        }
        return this.histories;
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
     * Gets the value of the idHistory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdHistory() {
        return idHistory;
    }

    /**
     * Sets the value of the idHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdHistory(String value) {
        this.idHistory = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the versionMajor property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVersionMajor() {
        return versionMajor;
    }

    /**
     * Sets the value of the versionMajor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVersionMajor(Integer value) {
        this.versionMajor = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the versionMinor property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVersionMinor() {
        return versionMinor;
    }

    /**
     * Sets the value of the versionMinor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVersionMinor(Integer value) {
        this.versionMinor = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the artifact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtifact() {
        return artifact;
    }

    /**
     * Sets the value of the artifact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtifact(String value) {
        this.artifact = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the element property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElement() {
        return element;
    }

    /**
     * Sets the value of the element property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElement(String value) {
        this.element = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the classificationSchema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassificationSchema() {
        return classificationSchema;
    }

    /**
     * Sets the value of the classificationSchema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassificationSchema(String value) {
        this.classificationSchema = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the dependencyKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDependencyKind() {
        return dependencyKind;
    }

    /**
     * Sets the value of the dependencyKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDependencyKind(String value) {
        this.dependencyKind = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the requiredElement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequiredElement() {
        return requiredElement;
    }

    /**
     * Sets the value of the requiredElement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequiredElement(String value) {
        this.requiredElement = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the requiredAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequiredAttribute() {
        return requiredAttribute;
    }

    /**
     * Sets the value of the requiredAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequiredAttribute(String value) {
        this.requiredAttribute = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the semanticConstraint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSemanticConstraint() {
        return semanticConstraint;
    }

    /**
     * Sets the value of the semanticConstraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSemanticConstraint(String value) {
        this.semanticConstraint = value;
        firePropertyChange();
    }

    public void addListener(IListener listener) {
        if (listeners == null) {
            listeners = new ListenerList();
        }
        listeners.add(listener);
    }

    public void removeProfileListener(IListener listener) {
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
