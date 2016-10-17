/*
 * Copyright (C) 2016 morar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.exdatis.medicineOfWork;

import java.sql.Connection;
import java.sql.Timestamp;
import net.exdatis.wdb.CRUDdata;

/**
 * Cekanje za medicinu rada.
 * @author morar
 */
public class MOWwait implements CRUDdata{
    
    private int waitId;
    private Timestamp waitTime;
    private int waitReason;
    private int waitPerson;
    private String currDbUser;
    private boolean canEdit;
    
    // iz pogleda
    private String reasonName;
    private String personName;
    private String personJMBG;

    public MOWwait() {
        this.canEdit = false;
    }

    public MOWwait(int waitReason, int waitPerson) {
        this.waitReason = waitReason;
        this.waitPerson = waitPerson;
    }

    public MOWwait(int waitReason, int waitPerson, boolean canEdit) {
        this.waitReason = waitReason;
        this.waitPerson = waitPerson;
        this.canEdit = canEdit;
    }

    public MOWwait(int waitId, Timestamp waitTime, int waitReason, int waitPerson, String currDbUser, boolean canEdit) {
        this.waitId = waitId;
        this.waitTime = waitTime;
        this.waitReason = waitReason;
        this.waitPerson = waitPerson;
        this.currDbUser = currDbUser;
        this.canEdit = canEdit;
    }

    public int getWaitId() {
        return waitId;
    }

    public void setWaitId(int waitId) {
        this.waitId = waitId;
    }

    public Timestamp getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Timestamp waitTime) {
        this.waitTime = waitTime;
    }

    public int getWaitReason() {
        return waitReason;
    }

    public void setWaitReason(int waitReason) {
        this.waitReason = waitReason;
    }

    public int getWaitPerson() {
        return waitPerson;
    }

    public void setWaitPerson(int waitPerson) {
        this.waitPerson = waitPerson;
    }

    public String getCurrDbUser() {
        return currDbUser;
    }

    public void setCurrDbUser(String currDbUser) {
        this.currDbUser = currDbUser;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonJMBG() {
        return personJMBG;
    }

    public void setPersonJMBG(String personJMBG) {
        this.personJMBG = personJMBG;
    }
    

    @Override
    public String insertRec(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String updateRec(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String deleteRec(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
