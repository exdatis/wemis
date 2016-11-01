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
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.exdatis.operator.CurrentUserBean;
import net.exdatis.wdb.Wdb;

/**
 *
 * @author morar
 */
@ManagedBean(name = "swWait", eager = true)
@ViewScoped
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
    
    // za pretragu
    private String searchText;
    private final Map<String, Object> argumentTypes = ShowMowWait.argMap();
    private int currentArg;
    
    // lista cekanja
    private ArrayList<ShowMowWait> standby;
    // konekcija sa bazom
    private final Connection connection = Wdb.getDbConnection(this.currentUser, this.currentPwd, this.currentHost);
    // inicijalizuj ArrayList standby
    @PostConstruct
    void init(){
        this.standby = new ArrayList<>();
    }

    public ShowMowWaitBean() {
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

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
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

    public void setStandby(ArrayList<ShowMowWait> standby) {
        this.standby = standby;
    }
    
    // test za pretragu(mozda treba popraviti)
    public String searchWait(){
        this.standby = ShowMowWait.getAllWait(connection, currentArg);
        
        return null; // TODO: uraditi redirekciju na drugu jsf stranu uz slanje argumenta.
    }
    
}
   
