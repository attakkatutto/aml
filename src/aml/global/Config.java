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
    private int numberAgentMessage, numberOfEntity, maxEdgesPerEntity;
    private int numberParents, numberPartners,numberDummies;
    private double employeeMean, employeeStdDev,
            freelanceMean, freelanceStdDev,
            bigCompanyMean, bigCompanyStdDev,
            smallCompanyMean, smallCompanyStdDev;
    private int laundererPercentage;
    private WindowType windowType;

    private Config() {}

    public static Config instance() {
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
    public int getNumberAgentMessage() {
        return numberAgentMessage;
    }

    public void setNumberAgentMessage(int num) {
        numberAgentMessage = num;
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
    public double getEmployeeMeanHonest() {
        return employeeMean;
    }

    public void setEmployeeMeanHonest(double num) {
        employeeMean = num;
    }
    
    @XmlElement
    public double getEmployeeStdDevHonest() {
        return employeeStdDev;
    }

    public void setEmployeeStdDevHonest(double num) {
        employeeStdDev = num;
    }
    
    @XmlElement
    public double getFreelanceMeanHonest() {
        return freelanceMean;
    }

    public void setFreelanceMeanHonest(double num) {
        freelanceMean = num;
    }

    @XmlElement
    public double getFreelanceStdDevHonest() {
        return freelanceStdDev;
    }

    public void setFreelanceStdDevHonest(double num) {
        freelanceStdDev = num;
    }
    
    @XmlElement
    public double getSmallCompanyMeanHonest() {
        return smallCompanyMean;
    }

    public void setSmallCompanyMeanHonest(double num) {
        smallCompanyMean = num;
    }

    @XmlElement
    public double getSmallCompanyStdDevHonest() {
        return smallCompanyStdDev;
    }

    public void setSmallCompanyStdDevHonest(double num) {
        smallCompanyStdDev = num;
    }
    
    @XmlElement
    public double getBigCompanyMeanHonest() {
        return bigCompanyMean;
    }

    public void setBigCompanyMeanHonest(double num) {
        bigCompanyMean = num;
    }
    
    @XmlElement
    public double getBigCompanyStdDevHonest() {
        return bigCompanyStdDev;
    }

    public void setBigCompanyStdDevHonest(double num) {
        bigCompanyStdDev = num;
    }
            
    @XmlElement
    public double getEmployeeMeanLaunderer() {
        return employeeMean;
    }

    public void setEmployeeMeanLaunderer(double num) {
        employeeMean = num;
    }
    
    @XmlElement
    public double getEmployeeStdDevLaunderer() {
        return employeeStdDev;
    }

    public void setEmployeeStdDevLaunderer(double num) {
        employeeStdDev = num;
    }
    
    @XmlElement
    public double getFreelanceMeanLaunderer() {
        return freelanceMean;
    }

    public void setFreelanceMeanLaunderer(double num) {
        freelanceMean = num;
    }

    @XmlElement
    public double getFreelanceStdDevLaunderer() {
        return freelanceStdDev;
    }

    public void setFreelanceStdDevLaunderer(double num) {
        freelanceStdDev = num;
    }
    
    @XmlElement
    public double getSmallCompanyMeanLaunderer() {
        return smallCompanyMean;
    }

    public void setSmallCompanyMeanLaunderer(double num) {
        smallCompanyMean = num;
    }

    @XmlElement
    public double getSmallCompanyStdDevLaunderer() {
        return smallCompanyStdDev;
    }

    public void setSmallCompanyStdDevLaunderer(double num) {
        smallCompanyStdDev = num;
    }
    
    @XmlElement
    public double getBigCompanyMeanLaunderer() {
        return bigCompanyMean;
    }

    public void setBigCompanyMeanLaunderer(double num) {
        bigCompanyMean = num;
    }
    
    @XmlElement
    public double getBigCompanyStdDevLaunderer() {
        return bigCompanyStdDev;
    }

    public void setBigCompanyStdDevLaunderer(double num) {
        bigCompanyStdDev = num;
    }

    @XmlElement
    public int getLaundererPercentage() {
        return laundererPercentage;
    }

    public void setLaundererPercentage(int laundererPercentage) {
        this.laundererPercentage = laundererPercentage;
    }        
    
    @XmlElement
    public WindowType getScoreWindowType() {
        return windowType;
    }

    public void setScoreWindowType(WindowType type) {
        windowType = type;
    }
}
