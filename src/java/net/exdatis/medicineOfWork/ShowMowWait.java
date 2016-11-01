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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Prikazi one koji su na cekanju u medicini rada
 * koristeci pogled iz baze: view_mowr_wait.
 * @author morar
 */
public class ShowMowWait {
    
    private int swId;
    private int swRoom;
    private int swWait;
    private String swRoomName;
    private String swPersonName;
    private String swPersonJMBG;
    private String swReasonName;
    private Timestamp swTime;
    private boolean canEdit;

    public ShowMowWait() {
        this.canEdit = false;
    }

    public int getSwId() {
        return swId;
    }

    public void setSwId(int swId) {
        this.swId = swId;
    }

    public int getSwRoom() {
        return swRoom;
    }

    public void setSwRoom(int swRoom) {
        this.swRoom = swRoom;
    }

    public int getSwWait() {
        return swWait;
    }

    public void setSwWait(int swWait) {
        this.swWait = swWait;
    }

    public String getSwRoomName() {
        return swRoomName;
    }

    public void setSwRoomName(String swRoomName) {
        this.swRoomName = swRoomName;
    }

    public String getSwPersonName() {
        return swPersonName;
    }

    public void setSwPersonName(String swPersonName) {
        this.swPersonName = swPersonName;
    }

    public String getSwPersonJMBG() {
        return swPersonJMBG;
    }

    public void setSwPersonJMBG(String swPersonJMBG) {
        this.swPersonJMBG = swPersonJMBG;
    }

    public String getSwReasonName() {
        return swReasonName;
    }

    public void setSwReasonName(String swReasonName) {
        this.swReasonName = swReasonName;
    }

    public Timestamp getSwTime() {
        return swTime;
    }

    public void setSwTime(Timestamp swTime) {
        this.swTime = swTime;
    }
    
    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
    
    public static Map<String, Object> argMap(){
        Map<String, Object> m = new LinkedHashMap<>();
        // pretraga po kompletnom jmbg broju
        m.put("Neuro-psihijatar", 3);
        m.put("Očno", 2);
        m.put("Psiholog", 1);
        m.put("Specijalistički", 4);
        
        return m;        
    }
    
    /**
     * Vrati sva cekanja koja nisu prosla, bez obzira na status(otpusten ili ne).
     * @param connection
     * @param arg
     * @return 
     */
    public static ArrayList<ShowMowWait> getAllWait(Connection connection, int arg){
        ArrayList<ShowMowWait> sw = new ArrayList<>();
        String sql = "select * from view_mowr_wait Where mowwr_room = ?";
        
        try(PreparedStatement pst = connection.prepareStatement(sql);){
            pst.setInt(1, arg);
            ResultSet r = pst.executeQuery();
            while(r.next()){
                ShowMowWait s = new ShowMowWait();
                s.setSwId(r.getInt(1));
                s.setSwRoom(r.getInt(2));
                s.setSwWait(r.getInt(3));
                s.setSwRoomName(r.getString(4));
                s.setSwPersonName(r.getString(5));
                s.setSwPersonJMBG(r.getString(6));
                s.setSwReasonName(r.getString(7));
                s.setSwTime(r.getTimestamp(8));
                sw.add(s);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        
        return sw;
    }
    
}
