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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.exdatis.waitingRoom.AmbulanceRoom;
import net.exdatis.wdb.CRUDdata;

/**
 * Raspored cekaonica za pregled, nabrajanje prema zahtevu stranke za uverenje.
 * @author morar
 */
public class MOWwaitRooms implements CRUDdata{
    
    private int mowwRoomId;
    private int mowwRoomRoomId;
    private int mowwRoomWaitId;
    private boolean canEdit;
    // iz pogleda
    private String mowwRoomName;

    public MOWwaitRooms() {
    }

    public MOWwaitRooms(int mowwRoomRoomId, int mowwRoomWaitId) {
        this.mowwRoomRoomId = mowwRoomRoomId;
        this.mowwRoomWaitId = mowwRoomWaitId;
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

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
    
    

    public String getMowwRoomName() {
        return mowwRoomName;
    }

    public void setMowwRoomName(String mowwRoomName) {
        this.mowwRoomName = mowwRoomName;
    }
    
    public static ArrayList<MOWwaitRooms> getCurrentRooms(Connection connection, int wait_id) {
        ArrayList<MOWwaitRooms> mwr = new ArrayList<>();
        String sql = "Select * from moww_wait_rooms Where mowwr_wait = ?";

        try (PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setInt(1, wait_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                MOWwaitRooms w = new MOWwaitRooms();
                w.setMowwRoomId(rs.getInt(1));
                w.setMowwRoomRoomId(rs.getInt(2));
                w.setMowwRoomWaitId(rs.getInt(3));
                w.setMowwRoomName(rs.getString(4));
                mwr.add(w);
            }
        } catch (SQLException e) {
            System.out.println("Room of MOWW: " + e.getMessage());
            return null;
        }

        return mwr;
    }
    
    public static MOWwaitRooms getRoomById(Connection connection, int currId){
        String sql = "Select * from moww_wait_rooms Where mowwr_id = ?";
        MOWwaitRooms w = new MOWwaitRooms();
        try(PreparedStatement pst = connection.prepareStatement(sql);){
            pst.setInt(1, currId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                w.setMowwRoomId(rs.getInt(1));
                w.setMowwRoomRoomId(rs.getInt(2));
                w.setMowwRoomWaitId(rs.getInt(3));
                w.setMowwRoomName(rs.getString(4));
            }
        }catch(SQLException e){
            System.out.println("Wait by ID: " + e.getMessage());
            return null;
        }
        
        return w;
    }
    
    public static Map<String, Object> roomMap(Connection connection){
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("Izaberite pregled", 0);
        String sql = "Select mwr_id, mwr_name From medicine_waiting_room Order by mwr_name";
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

    @Override
    public String insertRec(Connection connection) {
        String success = "no";
        String sql = "{call moww_room_add(?, ?, ?)}";
        
        try(CallableStatement cst = connection.prepareCall(sql);){
            cst.setInt(1, this.getMowwRoomRoomId());
            cst.setInt(2, this.getMowwRoomWaitId());
            cst.registerOutParameter(3, java.sql.Types.INTEGER);
            boolean added = cst.execute();
            this.setMowwRoomId(cst.getInt(3));
        }catch(SQLException e){
            success = e.getMessage();
            return success;
        }
        
        success = "yes";
        return success;
    }

    @Override
    public String updateRec(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String deleteRec(Connection connection) {
        String success = "no";
        String sql = "{call moww_room_delete(?)}";
        
        try(CallableStatement cst = connection.prepareCall(sql);){
            cst.setInt(1, this.getMowwRoomId());
            boolean deleted = cst.execute();
        }catch(SQLException e){
            success = e.getMessage();
            return success;
        }
        
        success = "yes";
        return success;
    }
    
}
