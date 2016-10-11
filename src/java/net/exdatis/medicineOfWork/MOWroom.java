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
public class MOWroom {
    
    private int roomId;
    private String roomCode;
    private String roomName;

    public MOWroom() {
    }

    public MOWroom(String roomName) {
        this.roomName = roomName;
    }

    public MOWroom(String roomCode, String roomName) {
        this.roomCode = roomCode;
        this.roomName = roomName;
    }

    public MOWroom(int roomId, String roomCode, String roomName) {
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.roomName = roomName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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
    
    public static Map<String, Object> roomMap(Connection connection){
        Map<String, Object> m = new LinkedHashMap<>();
        String sql = "Select mwr_id, mwr_name From medicine_waiting_room Order by mwr_name";
        try(PreparedStatement pst = connection.prepareStatement(sql);){
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int currId = rs.getInt(1);
                String currName = rs.getString(2);
                m.put(currName, currId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MOWroom.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return m;
    }    
}
