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
package net.exdatis.patientStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Samo da se pokupe podaci iz tabele general_status.
 * @author morar
 */
public class PatientStatus {
    
    private int statusId;
    private String statusName;

    public PatientStatus() {
    }

    public PatientStatus(String statusName) {
        this.statusName = statusName;
    }

    public PatientStatus(int statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }
    
    public static Map<String, Object> getStatusMap(Connection connection){
        Map<String, Object> m = new LinkedHashMap<>();
        String sql = "Select * From general_status Order by gs_id";
        try(PreparedStatement pst = connection.prepareStatement(sql);){
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                m.put(rs.getString(2), rs.getInt(1));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PatientStatus.class.getName()).log(Level.SEVERE, null, ex);
            return m;
        }
        return m;
    }
    
}
