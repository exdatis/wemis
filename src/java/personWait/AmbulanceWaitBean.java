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
package personWait;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.exdatis.operator.CurrentUserBean;
import net.exdatis.waitingRoom.AmbulanceRoom;
import net.exdatis.wdb.Wdb;
import net.exdatis.patientStatus.PatientPriority;
import net.exdatis.patientStatus.PatientStatus;

/**
 *
 * @author morar
 */

@ManagedBean(name = "ambulanceWaitBean", eager = true)
@ViewScoped
public class AmbulanceWaitBean implements Serializable {
    
    // za db
    private final String currentUser = CurrentUserBean.getDB_USER();
    private final String currentPwd = CurrentUserBean.getDB_PWD();
    private final String currentHost = CurrentUserBean.getDB_HOST();
    
    private int awId;
    private Timestamp awTime;
    private int awPriority;
    private int awRoom;
    private int awPerson;
    private int awStatus;
    private String dbUser;
    
    private String roomCode;
    private String roomName;
    private String personName;
    private String personJMBG;
    private String personLBO;
    private String personHealthCard;
    private String priorityName;
    private String statusName;
    
    private String errorMessage;
    
    private AmbulanceWait selectedAw;
    
    private static int newPerson;
    
    private final Connection connection = Wdb.getDbConnection(this.currentUser, this.currentPwd, this.currentHost);
    /**
     * Lista prioriteta za comboBox;
     */
    private Map<String, Object> priorities = PatientPriority.getPriorityMap(this.connection);
    
    /**
     * Lista ambulatnih cekaonica za comboBox.
     */
    private Map<String, Object> rooms = AmbulanceRoom.roomMap(connection);
    
    private Map<String, Object> status = PatientStatus.getStatusMap(this.connection);
    
    /**
     * Lista kreiranih cekanja(ambulanta). Var ce biti wait,
     */
    private ArrayList<AmbulanceWait> standby;
    
    @PostConstruct
    void init(){
        this.standby = new ArrayList<>();
    }

    public AmbulanceWaitBean() {
    }

    public int getAwId() {
        return awId;
    }

    public void setAwId(int awId) {
        this.awId = awId;
    }

    public Timestamp getAwTime() {
        return awTime;
    }

    public void setAwTime(Timestamp awTime) {
        this.awTime = awTime;
    }

    public int getAwPriority() {
        return awPriority;
    }

    public void setAwPriority(int awPriority) {
        this.awPriority = awPriority;
    }

    public int getAwRoom() {
        return awRoom;
    }

    public void setAwRoom(int awRoom) {
        this.awRoom = awRoom;
    }

    public int getAwPerson() {
        return awPerson;
    }

    public void setAwPerson(int awPerson) {
        this.awPerson = awPerson;
    }

    public int getAwStatus() {
        return awStatus;
    }

    public void setAwStatus(int awStatus) {
        this.awStatus = awStatus;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public String getPersonLBO() {
        return personLBO;
    }

    public void setPersonLBO(String personLBO) {
        this.personLBO = personLBO;
    }

    public String getPersonHealthCard() {
        return personHealthCard;
    }

    public void setPersonHealthCard(String personHealthCard) {
        this.personHealthCard = personHealthCard;
    }

    public AmbulanceWait getSelectedAw() {
        return selectedAw;
    }

    public void setSelectedAw(AmbulanceWait selectedAw) {
        this.selectedAw = selectedAw;
    }

    public static int getNewPerson() {
        return newPerson;
    }

    public static void setNewPerson(int newPerson) {
        AmbulanceWaitBean.newPerson = newPerson;
    }

    public Map<String, Object> getPriorities() {
        return priorities;
    }

    public void setPriorities(Map<String, Object> priorities) {
        this.priorities = priorities;
    }

    public Map<String, Object> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Object> rooms) {
        this.rooms = rooms;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    
    
    public Map<String, Object> getStatus() {
        return status;
    }

    public void setStatus(Map<String, Object> status) {
        this.status = status;
    }
    
    public ArrayList<AmbulanceWait> getStandby() {
        return standby;
    }

    public void setStandby(ArrayList<AmbulanceWait> standby) {
        this.standby = standby;
    }
    
    public String addWait(){
        // ako nije izabran pacijent
        if(newPerson == 0){
            String msg = "Morate najpre izabrati pacijenta za prijavu! Nakon toga odredite prioritet i čekaonicu.";
            this.setErrorMessage(msg);
            return null;
        }
        return null; // TODO
    }
    
    public String deleteWait(AmbulanceWait w){
        return null;
    }
    
    public String refreshWait(){
        this.setErrorMessage(null);
        this.standby = AmbulanceWait.getTodayAw(this.connection);
        return null;
    }
    
}
