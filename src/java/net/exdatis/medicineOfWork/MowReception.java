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
import java.sql.SQLException;
import java.sql.Timestamp;
import net.exdatis.wdb.CRUDdata;

/**
 *
 * @author morar
 */
public class MowReception implements CRUDdata{
    
    private int prijemId;
    private int cekanjeId;
    private Timestamp vremeUlaska;
    private Timestamp vremeIzlaska;
    
    /**
     * Trenutni status: 1= ceka, 2=primljen, 3=otpusten.
     */
    private int statusPacijenta;
    private boolean canEdit;

    public MowReception() {
        canEdit = false;
    }

    public MowReception(int cekanjeId) {
        this.cekanjeId = cekanjeId;
    }

    public MowReception(int prijemId, int cekanjeId, Timestamp vremeUlaska, Timestamp vremeIzlaska, int statusPacijenta, boolean canEdit) {
        this.prijemId = prijemId;
        this.cekanjeId = cekanjeId;
        this.vremeUlaska = vremeUlaska;
        this.vremeIzlaska = vremeIzlaska;
        this.statusPacijenta = statusPacijenta;
        this.canEdit = canEdit;
    }

    public int getPrijemId() {
        return prijemId;
    }

    public void setPrijemId(int prijemId) {
        this.prijemId = prijemId;
    }

    public int getCekanjeId() {
        return cekanjeId;
    }

    public void setCekanjeId(int cekanjeId) {
        this.cekanjeId = cekanjeId;
    }

    public Timestamp getVremeUlaska() {
        return vremeUlaska;
    }

    public void setVremeUlaska(Timestamp vremeUlaska) {
        this.vremeUlaska = vremeUlaska;
    }

    public Timestamp getVremeIzlaska() {
        return vremeIzlaska;
    }

    public void setVremeIzlaska(Timestamp vremeIzlaska) {
        this.vremeIzlaska = vremeIzlaska;
    }

    public int getStatusPacijenta() {
        return statusPacijenta;
    }

    public void setStatusPacijenta(int statusPacijenta) {
        this.statusPacijenta = statusPacijenta;
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
        String sql = "{call mow_reception_add(?, ?)}";
        try(CallableStatement cst = connection.prepareCall(sql);){
            cst.setInt(1, this.getCekanjeId());
            cst.registerOutParameter(2, java.sql.Types.INTEGER);
            if(cst.execute()){
                this.setPrijemId(cst.getInt(2));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            success = "Error: " + e.getMessage();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
