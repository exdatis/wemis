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
package net.exdatis.person;

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
import net.exdatis.wdb.CRUDdata;

/**
 *
 * @author morar
 */
public class Person implements CRUDdata{
    
    public enum ArgType{
        FULL_JMBG,
        PARTIAL_JMBG,
        FULL_LBO,
        PARTIAL_LBO,
        HEALTH_CARD,
        PARTIAL_NAME
    }
    
    private int personId;
    private String personCode;
    private String personName;
    private String personLBO;
    private String personJMBG;
    private String personHealthCard;
    private String personDocument;
    private int personSubstation;
    private String locationZip;
    private String locationName;
    private boolean canEdit;

    public Person() {
        this.canEdit = false;
    }

    public Person(String personName, String personJMBG, int personSubstation) {
        this.personName = personName;
        this.personJMBG = personJMBG;
        this.personSubstation = personSubstation;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
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

    public String getPersonDocument() {
        return personDocument;
    }

    public void setPersonDocument(String personDocument) {
        this.personDocument = personDocument;
    }
    

    public int getPersonSubstation() {
        return personSubstation;
    }

    public void setPersonSubstation(int personSubstation) {
        this.personSubstation = personSubstation;
    }

    public String getLocationZip() {
        return locationZip;
    }

    public void setLocationZip(String locationZip) {
        this.locationZip = locationZip;
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
    
    @Override
    public String insertRec(Connection connection) {
        String success = "no";
        String sql = "{call person_add(?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try(CallableStatement cst = connection.prepareCall(sql);){
            cst.setString(1, this.getPersonCode());
            cst.setString(2, this.getPersonName());
            cst.setString(3, this.getPersonLBO());
            cst.setString(4, this.getPersonJMBG());
            cst.setString(5, this.getPersonHealthCard());
            cst.setString(6, this.getPersonDocument());
            cst.setInt(7, this.getPersonSubstation());
            cst.registerOutParameter(8, java.sql.Types.INTEGER);
            boolean added = cst.execute();
            this.setPersonId(cst.getInt(8));
        }catch(SQLException e){
            success = e.getMessage();
            return success;        
    }
        success = "yes";
        return success;
    }

    @Override
    public String updateRec(Connection connection){
        String success = "no";
        String sql = "{call person_update(?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cst = connection.prepareCall(sql);) {
            cst.setInt(1, this.getPersonId());
            cst.setString(2, this.getPersonCode());
            cst.setString(3, this.getPersonName());
            cst.setString(4, this.getPersonLBO());
            cst.setString(5, this.getPersonJMBG());
            cst.setString(6, this.getPersonHealthCard());
            cst.setString(7, this.getPersonDocument());
            cst.setInt(8, this.getPersonSubstation());
    
            boolean updated = cst.execute();
        } catch (Exception e) {
            success = e.getMessage();
            return success;
        }
        success = "yes";
        return success;
    }

    @Override
    public String deleteRec(Connection connection){
        String success = "no";
        String sql = "{call person_delete(?)}";

        try (CallableStatement cst = connection.prepareCall(sql);) {
            cst.setInt(1, this.getPersonId());
            boolean deleted = cst.execute();
        } catch (Exception e) {
            success = e.getMessage();
            return success;
        }
        success = "yes";
        return success;
    }
    
    public static ArrayList<Person> getPersonByJMBG(Connection connection, String jmbg){
        ArrayList<Person> p = new ArrayList<>();
        
        String sql = "Select * From person_v Where p_jmbg = ?";
        
        try(PreparedStatement pst = connection.prepareStatement(sql);){
            pst.setString(1, jmbg);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Person currPerson = new Person();
                currPerson.setPersonId(rs.getInt(1));
                currPerson.setPersonCode(rs.getString(2));
                currPerson.setPersonName(rs.getString(3));
                currPerson.setPersonLBO(rs.getString(4));
                currPerson.setPersonJMBG(rs.getString(5));
                currPerson.setPersonHealthCard(rs.getString(6));
                currPerson.setPersonDocument(rs.getString(7));
                currPerson.setPersonSubstation(rs.getInt(8));
                currPerson.setLocationZip(rs.getString(9));
                currPerson.setLocationName(rs.getString(10));
                p.add(currPerson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return p;
        
    }

    public static ArrayList<Person> getPersonByJMBGpart(Connection connection, String jmbg) {
        ArrayList<Person> p = new ArrayList<>();
        String arg = "%" + jmbg + "%";
        String sql = "Select * From person_v Where lower(p_jmbg) like lower(?) Order by p_name Limit 100";

        try (PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, arg);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Person currPerson = new Person();
                currPerson.setPersonId(rs.getInt(1));
                currPerson.setPersonCode(rs.getString(2));
                currPerson.setPersonName(rs.getString(3));
                currPerson.setPersonLBO(rs.getString(4));
                currPerson.setPersonJMBG(rs.getString(5));
                currPerson.setPersonHealthCard(rs.getString(6));
                currPerson.setPersonDocument(rs.getString(7));
                currPerson.setPersonSubstation(rs.getInt(8));
                currPerson.setLocationZip(rs.getString(9));
                currPerson.setLocationName(rs.getString(10));              
                p.add(currPerson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return p;

    }    
    public static ArrayList<Person> getPersonByLBO(Connection connection, String lbo){
        ArrayList<Person> p = new ArrayList<>();
        
        String sql = "Select * From person_v Where p_lbo = ?";
        
        try(PreparedStatement pst = connection.prepareStatement(sql);){
            pst.setString(1, lbo);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Person currPerson = new Person();
                currPerson.setPersonId(rs.getInt(1));
                currPerson.setPersonCode(rs.getString(2));
                currPerson.setPersonName(rs.getString(3));
                currPerson.setPersonLBO(rs.getString(4));
                currPerson.setPersonJMBG(rs.getString(5));
                currPerson.setPersonHealthCard(rs.getString(6));
                currPerson.setPersonDocument(rs.getString(7));
                currPerson.setPersonSubstation(rs.getInt(8));
                currPerson.setLocationZip(rs.getString(9));
                currPerson.setLocationName(rs.getString(10));              
                p.add(currPerson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return p;
        
    }

    public static ArrayList<Person> getPersonByLBOGpart(Connection connection, String lbo) {
        ArrayList<Person> p = new ArrayList<>();
        String arg = "%" + lbo + "%";
        String sql = "Select * From person_v Where lower(p_lbo) like lower(?) Order by p_name Limit 100";

        try (PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, arg);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Person currPerson = new Person();
                currPerson.setPersonId(rs.getInt(1));
                currPerson.setPersonCode(rs.getString(2));
                currPerson.setPersonName(rs.getString(3));
                currPerson.setPersonLBO(rs.getString(4));
                currPerson.setPersonJMBG(rs.getString(5));
                currPerson.setPersonHealthCard(rs.getString(6));
                currPerson.setPersonDocument(rs.getString(7));
                currPerson.setPersonSubstation(rs.getInt(8));
                currPerson.setLocationZip(rs.getString(9));
                currPerson.setLocationName(rs.getString(10));              
                p.add(currPerson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return p;

    }   
    
    public static ArrayList<Person> getPersonByHealthCard(Connection connection, String card){
        ArrayList<Person> p = new ArrayList<>();
        
        String sql = "Select * From person_v Where p_health_card = ?";
        
        try(PreparedStatement pst = connection.prepareStatement(sql);){
            pst.setString(1, card);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Person currPerson = new Person();
                currPerson.setPersonId(rs.getInt(1));
                currPerson.setPersonCode(rs.getString(2));
                currPerson.setPersonName(rs.getString(3));
                currPerson.setPersonLBO(rs.getString(4));
                currPerson.setPersonJMBG(rs.getString(5));
                currPerson.setPersonHealthCard(rs.getString(6));
                currPerson.setPersonDocument(rs.getString(7));
                currPerson.setPersonSubstation(rs.getInt(8));
                currPerson.setLocationZip(rs.getString(9));
                currPerson.setLocationName(rs.getString(10));              
                p.add(currPerson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return p;
        
    }
    
    public static ArrayList<Person> getPersonByName(Connection connection, String name) {
        ArrayList<Person> p = new ArrayList<>();
        String arg = "%" + name + "%";
        String sql = "Select * From person_v Where lower(p_name) like lower(?) Order by p_name Limit 100";

        try (PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, arg);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Person currPerson = new Person();
                currPerson.setPersonId(rs.getInt(1));
                currPerson.setPersonCode(rs.getString(2));
                currPerson.setPersonName(rs.getString(3));
                currPerson.setPersonLBO(rs.getString(4));
                currPerson.setPersonJMBG(rs.getString(5));
                currPerson.setPersonHealthCard(rs.getString(6));
                currPerson.setPersonDocument(rs.getString(7));
                currPerson.setPersonSubstation(rs.getInt(8));
                currPerson.setLocationZip(rs.getString(9));
                currPerson.setLocationName(rs.getString(10));               
                p.add(currPerson);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + "Error>>>>>>>>>>>");
            return null;
        }
        return p;

    }   
    
    public static Person getPersonById(Connection connection, int currentId){
        String sql = "Select * From person_v Where p_id = ?";
        Person currPerson = new Person();
        try (PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setInt(1, currentId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                currPerson.setPersonId(rs.getInt(1));
                currPerson.setPersonCode(rs.getString(2));
                currPerson.setPersonName(rs.getString(3));
                currPerson.setPersonLBO(rs.getString(4));
                currPerson.setPersonJMBG(rs.getString(5));
                currPerson.setPersonHealthCard(rs.getString(6));
                currPerson.setPersonDocument(rs.getString(7));
                currPerson.setPersonSubstation(rs.getInt(8));
                currPerson.setLocationZip(rs.getString(9));
                currPerson.setLocationName(rs.getString(10));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return currPerson;

    }
    
    public static Map<String, Object> argMap(){
        Map<String, Object> m = new LinkedHashMap<>();
        // pretraga po kompletnom jmbg broju
        m.put("Kompletan JMBG", ArgType.FULL_JMBG);
        m.put("Delimičan JMBG", ArgType.PARTIAL_JMBG);
        m.put("Kompletan LBO", ArgType.FULL_LBO);
        m.put("Delimičan LBO", ArgType.PARTIAL_LBO);
        m.put("Zdravstvena", ArgType.HEALTH_CARD);
        m.put("Prezime/ime", ArgType.PARTIAL_NAME);
        
        return m;
        
    }
}
