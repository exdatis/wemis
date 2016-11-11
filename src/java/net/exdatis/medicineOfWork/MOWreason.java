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

/**
 * Razlog zasto je dosao( po koje uverenje)
 * @author morar
 */
public class MOWreason {
    
    private int reasonId;
    private String reasonCode;
    private String reasonName;

    public MOWreason() {
    }

    public MOWreason(String reasonName) {
        this.reasonName = reasonName;
    }

    public MOWreason(String reasonCode, String reasonName) {
        this.reasonCode = reasonCode;
        this.reasonName = reasonName;
    }

    public MOWreason(int reasonId, String reasonCode, String reasonName) {
        this.reasonId = reasonId;
        this.reasonCode = reasonCode;
        this.reasonName = reasonName;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }
    
    /**
     * static metod za punjenje cmb-a.
     * @param connection
     * @return  (map<String, object>)
     */
    public static Map<String, Object> reasonMap(Connection connection){
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("Izaberite uverenje", 0);
        String sql = "SELECT mr_id, mr_name FROM medicine_reason Order By mr_name";
        try(PreparedStatement pst = connection.prepareStatement(sql);){
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                m.put(name, id);
            }
        }catch(SQLException e){
            System.out.println("Reason Map: " + e.getMessage());
            return null;
        }
        
        return m;
    }
    
}
