/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.global;

import aml.global.Enums.WindowType;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ddefalco
 */
@XmlRootElement
public class Config {

    private static Config _instance;
    private int maxAgentMessage, maxNumberOfEntity, maxTransactionsPerEntity;
    private int maxNumberParents, maxNumberPartners;
    private double personMean, personStdDev,companyMean, companyStdDev;
    private WindowType windowType;

    private Config() {
        maxAgentMessage = 0;
        maxNumberOfEntity = 0;
        maxTransactionsPerEntity = 0;
        maxNumberPartners = 0;
        maxNumberParents = 0;
        personMean = 0;
        personStdDev = 0;
        companyMean = 0;
        companyStdDev = 0;
    }

    public static Config getInstance() {
        if (_instance == null) {
            _instance = unmashall();
            return _instance;
        }
        return _instance;
    }

    private static Config unmashall() {
        try {
            File file = new File("res\\Config.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return _instance = (Config) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, e);
            return new Config();
        }
    }

    @XmlElement
    public int getMaxAgentMessage() {
        return maxAgentMessage;
    }

    public void setMaxAgentMessage(int num) {
        maxAgentMessage = num;
    }

    @XmlElement
    public int getMaxNumberOfEntity() {
        return maxNumberOfEntity;
    }

    public void setMaxNumberOfEntity(int num) {
        maxNumberOfEntity = num;
    }

    @XmlElement
    public int getMaxTransactionsPerEntity() {
        return maxTransactionsPerEntity;
    }

    public void setMaxTransactionsPerEntity(int num) {
        maxTransactionsPerEntity = num;
    }

    @XmlElement
    public int getMaxNumberPartners() {
        return maxNumberPartners;
    }

    public void setMaxNumberPartners(int num) {
        maxNumberPartners = num;
    }

    @XmlElement
    public int getMaxNumberParents() {
        return maxNumberParents;
    }

    public void setMaxNumberParents(int num) {
        maxNumberParents = num;
    }

    @XmlElement
    public double getPersonMean() {
        return personMean;
    }

    public void setPersonMean(double num) {
        personMean = num;
    }

    @XmlElement
    public double getPersonStdDev() {
        return personStdDev;
    }

    public void setPersonStdDev(double num) {
        personStdDev = num;
    }
    
    @XmlElement
    public double getCompanyMean() {
        return companyMean;
    }

    public void setCompanyMean(double num) {
        companyMean = num;
    }

    @XmlElement
    public double getCompanyStdDev() {
        return companyStdDev;
    }

    public void setCompanyStdDev(double num) {
        companyStdDev = num;
    }
    
    @XmlElement
    public WindowType getScoreWindowType() {
        return windowType;
    }

    public void setScoreWindowType(WindowType type) {
        windowType = type;
    }
}
