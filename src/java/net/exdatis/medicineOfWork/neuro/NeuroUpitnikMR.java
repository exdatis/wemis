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
 * Prosti upitnik jest nije za neuro-psihijatriju (medicina rada).
 * @author morar
 */
public class NeuroUpitnikMR implements CRUDdata{
    
    private int upitnikId;
    private int upitnikPrijem;
    private int traumeBGS;
    private int traumeSGS;
    private int nesvestice;
    private int vrtoglavice;
    private int padavica;
    private int neurotskeSmetnje;
    private int alkohol;
    private int drogaLekovi;
    private int lecenBolnica;
    private int lecenAmbulanta;
    private int neuroStatus;
    private int psihoStatus;
    private int eeg;
    private boolean canEdit;
    
    public NeuroUpitnikMR() {
    }

    public int getUpitnikId() {
        return upitnikId;
    }

    public void setUpitnikId(int upitnikId) {
        this.upitnikId = upitnikId;
    }

    public int getUpitnikPrijem() {
        return upitnikPrijem;
    }

    public void setUpitnikPrijem(int upitnikPrijem) {
        this.upitnikPrijem = upitnikPrijem;
    }

    public int getTraumeBGS() {
        return traumeBGS;
    }

    public void setTraumeBGS(int traumeBGS) {
        this.traumeBGS = traumeBGS;
    }

    public int getTraumeSGS() {
        return traumeSGS;
    }

    public void setTraumeSGS(int traumeSGS) {
        this.traumeSGS = traumeSGS;
    }

    public int getNesvestice() {
        return nesvestice;
    }

    public void setNesvestice(int nesvestice) {
        this.nesvestice = nesvestice;
    }

    public int getVrtoglavice() {
        return vrtoglavice;
    }

    public void setVrtoglavice(int vrtoglavice) {
        this.vrtoglavice = vrtoglavice;
    }

    public int getPadavica() {
        return padavica;
    }

    public void setPadavica(int padavica) {
        this.padavica = padavica;
    }

    public int getNeurotskeSmetnje() {
        return neurotskeSmetnje;
    }

    public void setNeurotskeSmetnje(int neurotskeSmetnje) {
        this.neurotskeSmetnje = neurotskeSmetnje;
    }

    public int getAlkohol() {
        return alkohol;
    }

    public void setAlkohol(int alkohol) {
        this.alkohol = alkohol;
    }

    public int getDrogaLekovi() {
        return drogaLekovi;
    }

    public void setDrogaLekovi(int drogaLekovi) {
        this.drogaLekovi = drogaLekovi;
    }

    public int getLecenBolnica() {
        return lecenBolnica;
    }

    public void setLecenBolnica(int lecenBolnica) {
        this.lecenBolnica = lecenBolnica;
    }

    public int getLecenAmbulanta() {
        return lecenAmbulanta;
    }

    public void setLecenAmbulanta(int lecenAmbulanta) {
        this.lecenAmbulanta = lecenAmbulanta;
    }

    public int getNeuroStatus() {
        return neuroStatus;
    }

    public void setNeuroStatus(int neuroStatus) {
        this.neuroStatus = neuroStatus;
    }

    public int getPsihoStatus() {
        return psihoStatus;
    }

    public void setPsihoStatus(int psihoStatus) {
        this.psihoStatus = psihoStatus;
    }

    public int getEeg() {
        return eeg;
    }

    public void setEeg(int eeg) {
        this.eeg = eeg;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    @Override
    public String insertRec(Connection connection) {
        // update or insert
        String success = "no";
        String sql = "{call save_neuromr_upitnik(?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,?)}";
        try(CallableStatement cst = connection.prepareCall(sql);){
            cst.setInt(1, this.getUpitnikPrijem());
            cst.setInt(2, this.getTraumeBGS());
            cst.setInt(3, this.getTraumeSGS());
            cst.setInt(4, this.getNesvestice());
            cst.setInt(5, this.getVrtoglavice());
            cst.setInt(6, this.getPadavica());
            cst.setInt(7, this.getNeurotskeSmetnje());
            cst.setInt(8, this.getAlkohol());
            cst.setInt(9, this.getDrogaLekovi());
            cst.setInt(10, this.getLecenBolnica());
            cst.setInt(11, this.getLecenAmbulanta());
            cst.setInt(12, this.getNeuroStatus());
            cst.setInt(13, this.getPsihoStatus());
            cst.setInt(14, this.getEeg());
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
