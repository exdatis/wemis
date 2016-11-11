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
package net.exdatis.location;

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
public class Location {
    
    private int locationId;
    private String locationPostal;
    private String locationName;
    private boolean canEdit;

    public Location() {
    }

    public Location(String locationName) {
        this.locationName = locationName;
    }

    public Location(String locationPostal, String locationName) {
        this.locationPostal = locationPostal;
        this.locationName = locationName;
    }

    public Location(int locationId, String locationPostal, String locationName) {
        this.locationId = locationId;
        this.locationPostal = locationPostal;
        this.locationName = locationName;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationPostal() {
        return locationPostal;
    }

    public void setLocationPostal(String locationPostal) {
        this.locationPostal = locationPostal;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
    
    public static Map<String, Object> getLocationsMap(Connection connection){
        Map<String, Object> map = new LinkedHashMap<>();
        // blank
        map.put("Izaberite mesto", 0);
        String sql = "Select l_id, l_name from locations Order by l_name";
        try(PreparedStatement pst = connection.prepareStatement(sql);){
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                map.put(name, id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return map;
    }

    
}
