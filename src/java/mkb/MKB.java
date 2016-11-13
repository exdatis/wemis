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
package mkb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Medjunarodna Klasifikacija Bolesti(MKB). ICD.
 * @author morar
 */
public class MKB {
    
    public enum MkbArgument{
        PUNA_SIFRA,
        DELIMICNA_SIFRA,
        LATIN,
        SERBIAN
    }
    
    private int mkbId;
    private String mkbSifra;
    private String mkbLatin;
    private String mkbSrbija;
    
    public MKB() {
    }

    public MKB(String mkbSifra, String mkbLatin) {
        this.mkbSifra = mkbSifra;
        this.mkbLatin = mkbLatin;
    }

    public MKB(int mkbId, String mkbSifra, String mkbLatin) {
        this.mkbId = mkbId;
        this.mkbSifra = mkbSifra;
        this.mkbLatin = mkbLatin;
    }

    public MKB(int mkbId, String mkbSifra, String mkbLatin, String mkbSrbija) {
        this.mkbId = mkbId;
        this.mkbSifra = mkbSifra;
        this.mkbLatin = mkbLatin;
        this.mkbSrbija = mkbSrbija;
    }

    public int getMkbId() {
        return mkbId;
    }

    public void setMkbId(int mkbId) {
        this.mkbId = mkbId;
    }

    public String getMkbSifra() {
        return mkbSifra;
    }

    public void setMkbSifra(String mkbSifra) {
        this.mkbSifra = mkbSifra;
    }

    public String getMkbLatin() {
        return mkbLatin;
    }

    public void setMkbLatin(String mkbLatin) {
        this.mkbLatin = mkbLatin;
    }

    public String getMkbSrbija() {
        return mkbSrbija;
    }

    public void setMkbSrbija(String mkbSrbija) {
        this.mkbSrbija = mkbSrbija;
    }
    
    public static MKB pronadjiPoPunosSifri(Connection connection, String sifra){
        MKB m = new MKB();
        String sql = "SELECT * FROM icd WHERE i_code = ?";
        try(PreparedStatement pst = connection.prepareStatement(sql);){
            pst.setString(1, sifra);
            ResultSet r = pst.executeQuery();
            while(r.next()){
                m.setMkbId(r.getInt(1));
                m.setMkbSifra(r.getString(2));
                m.setMkbLatin(r.getString(3));
                m.setMkbSrbija(r.getString(4));
            }
        }catch(SQLException e){
            String msg = "Error: " + e.getMessage();
            System.out.println(msg);
            return null;
        }
        
        return m;
    }
    
    public static ArrayList<MKB> pronadjiPoDelimicnojSifri(Connection connection, String sifra){
        ArrayList<MKB> list = new ArrayList<>();
        String arg = "%" + sifra + "%";
        String sql = "SELECT * FROM icd WHERE LOWER(i_code) LIKE LOWER(?)";
        try (PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, arg);
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                MKB m = new MKB();
                m.setMkbId(r.getInt(1));
                m.setMkbSifra(r.getString(2));
                m.setMkbLatin(r.getString(3));
                m.setMkbSrbija(r.getString(4));
                list.add(m);
            }
        } catch (SQLException e) {
            String msg = "Error: " + e.getMessage();
            System.out.println(msg);
            return list;
        }

        return list;
    }
     
    public static ArrayList<MKB> pronadjiNaLatinskom(Connection connection, String tekst){
        ArrayList<MKB> list = new ArrayList<>();
        
        String sql = "SELECT * FROM icd WHERE MATCH(i_latin) AGAINST(?)";
        try (PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, tekst);
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                MKB m = new MKB();
                m.setMkbId(r.getInt(1));
                m.setMkbSifra(r.getString(2));
                m.setMkbLatin(r.getString(3));
                m.setMkbSrbija(r.getString(4));
                list.add(m);
            }
        } catch (SQLException e) {
            String msg = "Error: " + e.getMessage();
            System.out.println(msg);
            return list;
        }

        return list;
    }
    
    public static ArrayList<MKB> pronadjiNaSrpskom(Connection connection, String tekst){
        ArrayList<MKB> list = new ArrayList<>();
        
        String sql = "SELECT * FROM icd WHERE MATCH(i_serbian) AGAINST(?)";
        try (PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, tekst);
            ResultSet r = pst.executeQuery();
            while (r.next()) {
                MKB m = new MKB();
                m.setMkbId(r.getInt(1));
                m.setMkbSifra(r.getString(2));
                m.setMkbLatin(r.getString(3));
                m.setMkbSrbija(r.getString(4));
                list.add(m);
            }
        } catch (SQLException e) {
            String msg = "Error: " + e.getMessage();
            System.out.println(msg);
            return list;
        }

        return list;
    }
    
    public static Map<String, Object> mkbMapaArgumenata(){
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("Puna sifra", MkbArgument.PUNA_SIFRA);
        m.put("Delimicna sifra", MkbArgument.DELIMICNA_SIFRA);
        m.put("Latinski tekst", MkbArgument.LATIN);
        m.put("Tekst na srpskom", MkbArgument.SERBIAN);
        return m;
    }
    
}
