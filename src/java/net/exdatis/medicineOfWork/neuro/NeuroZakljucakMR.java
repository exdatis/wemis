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
import java.sql.SQLException;
import net.exdatis.wdb.CRUDdata;

/**
 *
 * @author morar
 */
public class NeuroZakljucakMR implements CRUDdata{
    
    private int zakljucakId;
    private int zakljucakPrijem;
    private int zakljucakSposoban; // jedan jeste default je 0-nije sposoban
    private String zakljucakKomentar;
    private boolean canEdit;


    public NeuroZakljucakMR() {
    }
    
    public NeuroZakljucakMR(int zakljucakPrijem, int zakljucakSposoban) {
        this.zakljucakPrijem = zakljucakPrijem;
        this.zakljucakSposoban = zakljucakSposoban;
    }

    public NeuroZakljucakMR(int zakljucakId, int zakljucakPrijem, int zakljucakSposoban, String zakljucakKomentar, boolean canEdit) {
        this.zakljucakId = zakljucakId;
        this.zakljucakPrijem = zakljucakPrijem;
        this.zakljucakSposoban = zakljucakSposoban;
        this.zakljucakKomentar = zakljucakKomentar;
        this.canEdit = canEdit;
    }

    public int getZakljucakId() {
        return zakljucakId;
    }

    public void setZakljucakId(int zakljucakId) {
        this.zakljucakId = zakljucakId;
    }

    public int getZakljucakPrijem() {
        return zakljucakPrijem;
    }

    public void setZakljucakPrijem(int zakljucakPrijem) {
        this.zakljucakPrijem = zakljucakPrijem;
    }

    public int getZakljucakSposoban() {
        return zakljucakSposoban;
    }

    public void setZakljucakSposoban(int zakljucakSposoban) {
        this.zakljucakSposoban = zakljucakSposoban;
    }

    public String getZakljucakKomentar() {
        return zakljucakKomentar;
    }

    public void setZakljucakKomentar(String zakljucakKomentar) {
        this.zakljucakKomentar = zakljucakKomentar;
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
        String sql = "{call save_neuro_zakljucak(?, ?, ?)}";
        
        try(CallableStatement cst = connection.prepareCall(sql);){
            cst.setInt(1, this.getZakljucakPrijem());
            cst.setInt(2, this.getZakljucakSposoban());
            cst.setString(3, this.getZakljucakKomentar());
            boolean added = cst.execute();
            
        }catch(SQLException e){
            String msg = "Error: " + e.getMessage();
            System.out.println(msg);
            return msg;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
