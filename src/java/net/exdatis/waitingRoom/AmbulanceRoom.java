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
package net.exdatis.waitingRoom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author morar
 */
public class AmbulanceRoom {
    
    private int ambulanceRoomId;
    private String ambulanceRoomCode;
    private String ambulanceRoomName;

    public AmbulanceRoom() {
    }

    public AmbulanceRoom(String ambulanceRoomName) {
        this.ambulanceRoomName = ambulanceRoomName;
    }

    public AmbulanceRoom(String ambulanceRoomCode, String ambulanceRoomName) {
        this.ambulanceRoomCode = ambulanceRoomCode;
        this.ambulanceRoomName = ambulanceRoomName;
    }

    public AmbulanceRoom(int ambulanceRoomId, String ambulanceRoomCode, String ambulanceRoomName) {
        this.ambulanceRoomId = ambulanceRoomId;
        this.ambulanceRoomCode = ambulanceRoomCode;
        this.ambulanceRoomName = ambulanceRoomName;
    }

    public int getAmbulanceRoomId() {
        return ambulanceRoomId;
    }

    public void setAmbulanceRoomId(int ambulanceRoomId) {
        this.ambulanceRoomId = ambulanceRoomId;
    }

    public String getAmbulanceRoomCode() {
        return ambulanceRoomCode;
    }

    public void setAmbulanceRoomCode(String ambulanceRoomCode) {
        this.ambulanceRoomCode = ambulanceRoomCode;
    }

    public String getAmbulanceRoomName() {
        return ambulanceRoomName;
    }

    public void setAmbulanceRoomName(String ambulanceRoomName) {
        this.ambulanceRoomName = ambulanceRoomName;
    }
    
    public static Map<String, Object> roomMap(Connection connection){
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("Izaberite čekaonicu", 0);
        String sql = "Select awr_id, awr_name From ambulance_waiting_room Order by awr_name";
        try(PreparedStatement pst = connection.prepareStatement(sql);){
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int currId = rs.getInt(1);
                String currName = rs.getString(2);
                m.put(currName, currId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AmbulanceRoom.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return m;
    }
    
}
