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
 * Klasa koja obradjuje anamnezu u medicini rada za neuro-psihijatriju,
 * @author morar
 */
public class NeuroAnamnezaMR implements CRUDdata{
    
    private int anamnezaId;
    private int anamnezaPrijem;
    private String anamnezaTekst;

    public NeuroAnamnezaMR() {
    }

    public NeuroAnamnezaMR(int anamnezaPrijem) {
        this.anamnezaPrijem = anamnezaPrijem;
    }

    public NeuroAnamnezaMR(int anamnezaPrijem, String anamnezaTekst) {
        this.anamnezaPrijem = anamnezaPrijem;
        this.anamnezaTekst = anamnezaTekst;
    }

    public int getAnamnezaId() {
        return anamnezaId;
    }

    public void setAnamnezaId(int anamnezaId) {
        this.anamnezaId = anamnezaId;
    }

    public int getAnamnezaPrijem() {
        return anamnezaPrijem;
    }

    public void setAnamnezaPrijem(int anamnezaPrijem) {
        this.anamnezaPrijem = anamnezaPrijem;
    }

    public String getAnamnezaTekst() {
        return anamnezaTekst;
    }

    public void setAnamnezaTekst(String anamnezaTekst) {
        this.anamnezaTekst = anamnezaTekst;
    }
    
    

    @Override
    public String insertRec(Connection connection) {
        String success = "no";
        // ako postoji update-uje u suprotnom insertuje
        String sql = "{call save_neuro_anamneza(?, ?)}";
        try(CallableStatement cst = connection.prepareCall(sql);){
            cst.setInt(1, this.getAnamnezaPrijem());
            cst.setString(2, this.getAnamnezaTekst());
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
