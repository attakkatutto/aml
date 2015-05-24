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
    private int agentMessage, numberOfEntity, maxEdgesPerEntity;
    private int numberParents, numberPartners,numberDummies;
    private double employeeMean, employeeStdDev,
            freelanceMean, freelanceStdDev,
            bigCompanyMean, bigCompanyStdDev,
            smallCompanyMean, smallCompanyStdDev;
    private WindowType windowType;

    private Config() {}

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
    public int getAgentMessage() {
        return agentMessage;
    }

    public void setAgentMessage(int num) {
        agentMessage = num;
    }

    @XmlElement
    public int getMaxNumberDummies() {
        return numberDummies;
    }

    public void setMaxNumberDummies(int num) {
        numberDummies = num;
    }
    
    @XmlElement
    public int getNumberOfEntity() {
        return numberOfEntity;
    }

    public void setNumberOfEntity(int num) {
        numberOfEntity = num;
    }

    @XmlElement
    public int getMaxEdgesPerEntity() {
        return maxEdgesPerEntity;
    }

    public void setMaxEdgesPerEntity(int num) {
        maxEdgesPerEntity = num;
    }

    @XmlElement
    public int getNumberPartners() {
        return numberPartners;
    }

    public void setNumberPartners(int num) {
        numberPartners = num;
    }

    @XmlElement
    public int getNumberParents() {
        return numberParents;
    }

    public void setNumberParents(int num) {
        numberParents = num;
    }

    @XmlElement
    public double getEmployeeMean() {
        return employeeMean;
    }

    public void setEmployeeMean(double num) {
        employeeMean = num;
    }
    
    @XmlElement
    public double getEmployeeStdDev() {
        return employeeStdDev;
    }

    public void setEmployeeStdDev(double num) {
        employeeStdDev = num;
    }
    
    @XmlElement
    public double getFreelanceMean() {
        return freelanceMean;
    }

    public void setFreelanceMean(double num) {
        freelanceMean = num;
    }

    @XmlElement
    public double getFreelanceStdDev() {
        return freelanceStdDev;
    }

    public void setFreelanceStdDev(double num) {
        freelanceStdDev = num;
    }
    
    @XmlElement
    public double getSmallCompanyMean() {
        return smallCompanyMean;
    }

    public void setSmallCompanyMean(double num) {
        smallCompanyMean = num;
    }

    @XmlElement
    public double getSmallCompanyStdDev() {
        return smallCompanyStdDev;
    }

    public void setSmallCompanyStdDev(double num) {
        smallCompanyStdDev = num;
    }
    
    @XmlElement
    public double getBigCompanyMean() {
        return bigCompanyMean;
    }

    public void setBigCompanyMean(double num) {
        bigCompanyMean = num;
    }
    
    @XmlElement
    public double getBigCompanyStdDev() {
        return bigCompanyStdDev;
    }

    public void setBigCompanyStdDev(double num) {
        bigCompanyStdDev = num;
    }
    
    @XmlElement
    public WindowType getScoreWindowType() {
        return windowType;
    }

    public void setScoreWindowType(WindowType type) {
        windowType = type;
    }
}
