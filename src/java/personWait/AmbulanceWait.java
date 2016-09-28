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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author morar
 */
public class AmbulanceWait {
    
    private int awId;
    private Timestamp awTime;
    private int awPriority;
    private int awRoom;
    private int awPerson;
    private int awStatus;
    /**
     * Samo korisnik koji je uneo podataka u bazu.
     */
    private String awDbUser;
    
    private String roomCode;
    private String roomName;
    // polja iz pogleda
    private String personName;
    private String personLBO;
    private String personJMBG;
    private String personHealthCard;

    public AmbulanceWait() {
    }

    public AmbulanceWait(int awPriority, int awRoom, int awPerson) {
        this.awPriority = awPriority;
        this.awRoom = awRoom;
        this.awPerson = awPerson;
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

    public String getAwDbUser() {
        return awDbUser;
    }

    public void setAwDbUser(String awDbUser) {
        this.awDbUser = awDbUser;
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

    public String getPersonLBO() {
        return personLBO;
    }

    public void setPersonLBO(String personLBO) {
        this.personLBO = personLBO;
    }

    public String getPersonJMBG() {
        return personJMBG;
    }

    public void setPersonJMBG(String personJMBG) {
        this.personJMBG = personJMBG;
    }

    public String getPersonHealthCard() {
        return personHealthCard;
    }

    public void setPersonHealthCard(String personHealthCard) {
        this.personHealthCard = personHealthCard;
    }
    
    public static Map<String, Object> getPriorityMap(){
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("Normalan", 3);
        m.put("Deca, trudnice, stara lica", 2);
        m.put("Urgentan", 1);
        return m;              
    }
    
    public static ArrayList<AmbulanceWait> getTodayAw(Connection connection){
        ArrayList<AmbulanceWait> aw = new ArrayList<>();
        
        String sql = "{call ambulance_wait_today()}";
        try(CallableStatement cst = connection.prepareCall(sql);){
            ResultSet rs = cst.executeQuery();
            while(rs.next()){
                AmbulanceWait w = new AmbulanceWait();
                //TODO raspakuj i poredjaj elemente.
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AmbulanceWait.class.getName()).log(Level.SEVERE, null, ex);
            return aw;
        }
        
        return aw;
    }
    
}