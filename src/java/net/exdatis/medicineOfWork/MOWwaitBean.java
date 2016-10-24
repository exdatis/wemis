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

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.exdatis.operator.CurrentUserBean;
import net.exdatis.wdb.Wdb;

/**
 *
 * @author morar
 */

@ManagedBean(name="mowBean", eager = true)
@ViewScoped
public class MOWwaitBean implements Serializable{
    
    // za db
    private final String currentUser = CurrentUserBean.getDB_USER();
    private final String currentPwd = CurrentUserBean.getDB_PWD();
    private final String currentHost = CurrentUserBean.getDB_HOST();

    // iz tabele
    private int waitId;
    private Timestamp waitTime;
    private int waitReason;
    private int waitPerson;
    
    // dodatak iz pogleda
    private String reasonName;
    private String personName;
    private String personJMBG; 
    
    // za poruke
    private String errorMessage;
    private String prepareMessage;
    
    private MOWwait selectedWait;
    
    private static int newPerson;
    
    // za sobe-preglede koje osoba ceka
    private int mowwRoomId;
    private int mowwRoomRoomId;
    private int mowwRoomWaitId;
    // iz pogleda
    private String mowwRoomName;
    private MOWwaitRooms currentRoom;
    private ArrayList<MOWwaitRooms> thisRooms;
    
    private final Connection connection = Wdb.getDbConnection(this.currentUser, this.currentPwd, this.currentHost);
    
    private Map<String, Object> reasons = MOWreason.reasonMap(connection);
    private Map<String, Object> rooms = MOWwaitRooms.roomMap(connection);
    
    /**
     * Lista kreiranih cekanja(var wait)
     */
    private ArrayList<MOWwait> standby;
    
    /**
     * Iniciraj listu da moze da prihvati podatke.
     */
    @PostConstruct
    void init(){
        this.standby = new ArrayList<>();
        this.thisRooms = new ArrayList<>();
    }

    public MOWwaitBean() {
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getPrepareMessage() {
        return prepareMessage;
    }

    public void setPrepareMessage(String prepareMessage) {
        this.prepareMessage = prepareMessage;
    }

    public MOWwait getSelectedWait() {
        return selectedWait;
    }

    public void setSelectedWait(MOWwait selectedWait) {
        this.selectedWait = selectedWait;
    }

    public static int getNewPerson() {
        return newPerson;
    }

    public static void setNewPerson(int newPerson) {
        MOWwaitBean.newPerson = newPerson;
    }

    public Map<String, Object> getReasons() {
        return reasons;
    }

    public void setReasons(Map<String, Object> reasons) {
        this.reasons = reasons;
    }

    public ArrayList<MOWwait> getStandby() {
        return standby;
    }

    public void setStandby(ArrayList<MOWwait> standby) {
        this.standby = standby;
    }

    public int getMowwRoomId() {
        return mowwRoomId;
    }

    public void setMowwRoomId(int mowwRoomId) {
        this.mowwRoomId = mowwRoomId;
    }

    public int getMowwRoomRoomId() {
        return mowwRoomRoomId;
    }

    public void setMowwRoomRoomId(int mowwRoomRoomId) {
        this.mowwRoomRoomId = mowwRoomRoomId;
    }

    public int getMowwRoomWaitId() {
        return mowwRoomWaitId;
    }

    public void setMowwRoomWaitId(int mowwRoomWaitId) {
        this.mowwRoomWaitId = mowwRoomWaitId;
    }

    public String getMowwRoomName() {
        return mowwRoomName;
    }

    public void setMowwRoomName(String mowwRoomName) {
        this.mowwRoomName = mowwRoomName;
    }

    public MOWwaitRooms getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(MOWwaitRooms currentRoom) {
        this.currentRoom = currentRoom;
    }

    public ArrayList<MOWwaitRooms> getThisRooms() {
        return thisRooms;
    }

    public void setThisRooms(ArrayList<MOWwaitRooms> thisRooms) {
        this.thisRooms = thisRooms;
    }

    public Map<String, Object> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Object> rooms) {
        this.rooms = rooms;
    }
    
    
    public String addWait(){
        this.setErrorMessage(null);
        if(newPerson == 0){
            String msg = "Morate najpre izabrati pacijenta za prijavu! Nakon toga selektujte razlog dolaska.";
            this.setErrorMessage(msg);
            return null;
        }
        
        // kreiraj novu instancu klase i promeni stanje.
        MOWwait m = new MOWwait();
        m.setWaitReason(waitReason);
        m.setWaitPerson(newPerson);
        String msg = m.insertRec(connection);
        if(msg.equalsIgnoreCase("yes")){
            MOWwait newWait = MOWwait.getWaitById(connection, m.getWaitId());
            standby.add(newWait);
            return null;
        }
        
        // else error
        msg = "Error: " + msg;
        this.setErrorMessage(msg);
        return null;
    }
    
    public String refreshWait(){
        this.setErrorMessage(null);
        this.setPrepareMessage(null);
        standby = MOWwait.getTodayWait(connection);
        return null;
    }
    
    public String addRoom(){
        return null;
    }
    
    public String deleteRoom(MOWwaitRooms r){
        this.setErrorMessage(null);
        // proveri status pre slanja
       /* if(w.getAwStatus() > 1){
            String msg = "Error: Primljeni i otpušteni pacijenti nemogu se brisati.";
            this.setErrorMessage(msg);
            return null;
        } */
        String msg = r.deleteRec(connection);
        if(msg.equalsIgnoreCase("yes")){
            this.rooms.remove(r);
            return null;
        }
        // else
        msg = "Error: " + msg;
        this.setErrorMessage(msg);
        return null;
    }
    
    public String refreshRooms(){
        return null;
    }
}
