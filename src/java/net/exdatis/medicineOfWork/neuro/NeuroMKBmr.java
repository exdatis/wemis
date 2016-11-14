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
package net.exdatis.medicineOfWork.neuro;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import net.exdatis.wdb.CRUDdata;

/**
 * Dijagnostika vezana za MKB(ICD) u medicini rada(neuro-psihijatrija).
 * @author morar
 */
public class NeuroMKBmr implements CRUDdata{
    private int dijagnozaId;
    private int dijagnozaPrijem;
    private int dijagnozaMKB;
    private String dijagnozaKomentar;
    // iz pogleda
    private String dijagnozaLatin;
    private String dijagnozaSerbian;
    
    private boolean canEdit;

    public NeuroMKBmr() {
    }

    public int getDijagnozaId() {
        return dijagnozaId;
    }

    public void setDijagnozaId(int dijagnozaId) {
        this.dijagnozaId = dijagnozaId;
    }

    public int getDijagnozaPrijem() {
        return dijagnozaPrijem;
    }

    public void setDijagnozaPrijem(int dijagnozaPrijem) {
        this.dijagnozaPrijem = dijagnozaPrijem;
    }

    public int getDijagnozaMKB() {
        return dijagnozaMKB;
    }

    public void setDijagnozaMKB(int dijagnozaMKB) {
        this.dijagnozaMKB = dijagnozaMKB;
    }

    public String getDijagnozaKomentar() {
        return dijagnozaKomentar;
    }

    public void setDijagnozaKomentar(String dijagnozaKomentar) {
        this.dijagnozaKomentar = dijagnozaKomentar;
    }

    public String getDijagnozaLatin() {
        return dijagnozaLatin;
    }

    public void setDijagnozaLatin(String dijagnozaLatin) {
        this.dijagnozaLatin = dijagnozaLatin;
    }

    public String getDijagnozaSerbian() {
        return dijagnozaSerbian;
    }

    public void setDijagnozaSerbian(String dijagnozaSerbian) {
        this.dijagnozaSerbian = dijagnozaSerbian;
    }
    
    

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
    
    public static ArrayList<NeuroMKBmr> getDijagnozePoPrijemu(Connection connection, int prijemId){
        ArrayList<NeuroMKBmr> m = new ArrayList<>();
        String sql = "select * from view_mr_neuro_mkb where mnm_prijem = ?";
        try(PreparedStatement pst = connection.prepareStatement(sql);){
            pst.setInt(1, prijemId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                NeuroMKBmr mr = new NeuroMKBmr();
                mr.setDijagnozaId(rs.getInt(1));
                mr.setDijagnozaPrijem(rs.getInt(2));
                mr.setDijagnozaMKB(rs.getInt(3));
                mr.setDijagnozaKomentar(rs.getString(4));
                mr.setDijagnozaLatin(rs.getString(5));
                mr.setDijagnozaSerbian(rs.getString(6));
                m.add(mr);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return m;
        }
        
        return m;
    }

    @Override
    public String insertRec(Connection connection) {
        String success = "no";
        String sql = "{call save_neuro_mkb(?, ?, ?)}";
        
        try(CallableStatement cst = connection.prepareCall(sql);){
            cst.setInt(1, this.getDijagnozaPrijem());
            cst.setInt(2, this.getDijagnozaMKB());
            cst.setString(3, this.getDijagnozaKomentar());
            boolean added = cst.execute();
        }catch(SQLException e){
            String msg = "Error: " + e.getMessage();
            System.out.println(msg);
            return msg;
        }
        
        return success;
    }

    @Override
    public String updateRec(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String deleteRec(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
