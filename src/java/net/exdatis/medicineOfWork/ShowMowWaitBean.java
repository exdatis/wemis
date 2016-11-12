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

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import net.exdatis.operator.CurrentUserBean;
import net.exdatis.wdb.Wdb;

/**
 *
 * @author morar
 */
@ManagedBean(name = "swWait", eager = true)
@SessionScoped
public class ShowMowWaitBean implements Serializable{

    // za db
    private final String currentUser = CurrentUserBean.getDB_USER();
    private final String currentPwd = CurrentUserBean.getDB_PWD();
    private final String currentHost = CurrentUserBean.getDB_HOST();
    
    // pogled=class(model)
    private int swId;
    private int swRoom;
    private int swWait;
    private String swRoomName;
    private String swPersonName;
    private String swPersonJMBG;
    private String swReasonName;
    private Timestamp swTime;
    
    // za poruke
    private String errorMsg;
    
    // za pretragu
    private Map<String, Object> argumentTypes;
    private int currentArg;
    
    // lista cekanja
    private ArrayList<ShowMowWait> standby;
    // selektovano cekanje
    private ShowMowWait selektovanoCekanje;
    // konekcija sa bazom
    private final Connection connection = Wdb.getDbConnection(this.currentUser, this.currentPwd, this.currentHost);
    
    // novouneti prijem ID broj
    public static int uradjeniPrijem;
    // moram da dodam i staticke promenjlive za osnovne podatke pacijenta
    public static String prezimeIme;
    public static String jmbg;
    public static String uverenje;
    // inicijalizuj ArrayList standby
    @PostConstruct
    void init(){
        standby = new ArrayList<>();
        argumentTypes = new LinkedHashMap<>();
        argumentTypes = ShowMowWait.argMap();
        //standby = ShowMowWait.getAllWait(connection, 3);
    }

    public ShowMowWaitBean() {
        //standby = new ArrayList<>();
        //this.setCurrentArg(3);
    }

    public int getSwId() {
        return swId;
    }

    public void setSwId(int swId) {
        this.swId = swId;
    }

    public int getSwRoom() {
        return swRoom;
    }

    public void setSwRoom(int swRoom) {
        this.swRoom = swRoom;
    }

    public int getSwWait() {
        return swWait;
    }

    public void setSwWait(int swWait) {
        this.swWait = swWait;
    }

    public String getSwRoomName() {
        return swRoomName;
    }

    public void setSwRoomName(String swRoomName) {
        this.swRoomName = swRoomName;
    }

    public String getSwPersonName() {
        return swPersonName;
    }

    public void setSwPersonName(String swPersonName) {
        this.swPersonName = swPersonName;
    }

    public String getSwPersonJMBG() {
        return swPersonJMBG;
    }

    public void setSwPersonJMBG(String swPersonJMBG) {
        this.swPersonJMBG = swPersonJMBG;
    }

    public String getSwReasonName() {
        return swReasonName;
    }

    public void setSwReasonName(String swReasonName) {
        this.swReasonName = swReasonName;
    }

    public Timestamp getSwTime() {
        return swTime;
    }

    public void setSwTime(Timestamp swTime) {
        this.swTime = swTime;
    }

    public int getCurrentArg() {
        return currentArg;
    }

    public void setCurrentArg(int currentArg) {
        this.currentArg = currentArg;
    }

    public ArrayList<ShowMowWait> getStandby() {
        return standby;
    }

    public Map<String, Object> getArgumentTypes() {
        return argumentTypes;
    }
    
    public void setArgumentTypes(Map<String, Object> args){
        argumentTypes = args;
    }

    public void setStandby(ArrayList<ShowMowWait> standby) {
        this.standby = standby;
    }

    public ShowMowWait getSelektovanoCekanje() {
        return selektovanoCekanje;
    }

    public void setSelektovanoCekanje(ShowMowWait selektovanoCekanje) {
        this.selektovanoCekanje = selektovanoCekanje;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static int getUradjeniPrijem() {
        return uradjeniPrijem;
    }

    public static void setUradjeniPrijem(int uradjeniPrijem) {
        ShowMowWaitBean.uradjeniPrijem = uradjeniPrijem;
    }

    public static String getPrezimeIme() {
        return prezimeIme;
    }

    public static void setPrezimeIme(String prezimeIme) {
        ShowMowWaitBean.prezimeIme = prezimeIme;
    }

    public static String getJmbg() {
        return jmbg;
    }

    public static void setJmbg(String jmbg) {
        ShowMowWaitBean.jmbg = jmbg;
    }

    public static String getUverenje() {
        return uverenje;
    }

    public static void setUverenje(String uverenje) {
        ShowMowWaitBean.uverenje = uverenje;
    }
    
    
    
    // test za pretragu(mozda treba popraviti)
    public void searchWait(){
        standby.clear();
        System.out.println("Izabrani argument---------------------------------------------" + currentArg);
        standby = ShowMowWait.getAllWait(connection, currentArg);
        
        //return null; // TODO: uraditi redirekciju na drugu jsf stranu uz slanje argumenta.
    }
    
    public String primiPacijenta(ShowMowWait cekanje){
        this.setSelektovanoCekanje(cekanje);
        // unwsi podatak u bazu
        MowReception m = new MowReception();
        m.setCekanjeId(cekanje.getSwId());
        setUradjeniPrijem(m.getPrijemId());
        setPrezimeIme(cekanje.getSwPersonName());
        setJmbg(cekanje.getSwPersonJMBG());
        setUverenje(cekanje.getSwReasonName());
        standby.remove(cekanje);
        String msg = m.insertRec(connection);
        if(! msg.equalsIgnoreCase("yes")){
            // treba i mesto za poruke
            this.setErrorMsg(msg);
            return null;
        }
        String url = "";
        switch(cekanje.getSwRoom()){
            case 1:
                url = "psiholog";
                break;
            case 2:
                url = "ocno";
                break;
            case 3:
                url = "neuro";
                break;
            case 4:
                url = "spec";
                break;
            default:
                url = null;
                break;
        }
        return url;
    }
    
}
   
