/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.entity;

import java.io.Serializable;

/**
 * Class rapresents a bank transaction in the network
 * @author ddefalco
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String idSource;
    private String idTarget;
    private double amount;
    private short month;
    private short year;
    private String sourceType;
    private String targetType;
    private String honest;

    public Transaction() {
    }

    public Transaction(String id) {
        this.id = id;
    }

    public Transaction(String id, String idSource, String idTarget, double amount, short month,short year,String honest) {
        this.id = id;
        this.idSource = idSource;
        this.idTarget = idTarget;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.honest = honest;
    }

    /**
     * Identifier of the transaction
     * @return 
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Source of the transaction
     * @return 
     */
    public String getIdSource() {
        return idSource;
    }

    public void setIdSource(String idSource) {
        this.idSource = idSource;
    }

    /**
     * Target of the transaction
     * @return 
     */
    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }

    /**
     * Amount of the transaction
     * @return 
     */
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Month of the transaction
     * @return 
     */
    public short getMonth() {
        return month;
    }

    public void setMonth(short month) {
        this.month = month;
    }

    /**
     * Year of the transaction
     * @return 
     */
    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }    
    
    /**
     * Category of source agent
     * of the transaction
     * @return 
     */
    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * Category of target agent
     * of the transaction
     * @return 
     */
    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    /**
     * Is honest? YES or NO
     * @return 
     */
    public String getHonest() {
        return honest;
    }

    public void setHonest(String honest) {
        this.honest = honest;
    }    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aml.entity.Transaction[ id=" + id + " ]";
    }
    
}
