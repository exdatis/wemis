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
package net.exdatis.operator;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import net.exdatis.config.ReadDbHost;
import net.exdatis.wdb.Wdb;

/**
 * Korisnik aplikacije.
 * @author morar
 */

@ManagedBean(name = "currentUserBean", eager = true)
@SessionScoped
public class CurrentUserBean {
    
    private String userName;
    private String userPassword;
    private Map<String, Object> hosts =  ReadDbHost.getDbHostMap();
    private String currentMessage;
    
    /**
     * Server host za uspostavljanje konekcije sa bazom podataka;
     */
    private String dbHost;

    public CurrentUserBean() {
 
    }

    public CurrentUserBean(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.dbHost = "localhost";
    }

    public CurrentUserBean(String userName, String userPassword, String dbHost) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.dbHost = dbHost;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(String currentMessage) {
        this.currentMessage = currentMessage;
    }
    
    

    public Map<String, Object> getHosts() {
        return hosts;
    }

    public void setHosts(Map<String, Object> hosts) {
        this.hosts = hosts;
    }
    
    
    
    public void login(){
        this.setCurrentMessage(null);
        Connection connection = null;
        
        try{
            connection = Wdb.getDbConnection(this.userName, this.userPassword, this.dbHost);
        } catch (ClassNotFoundException ex) {
            this.setCurrentMessage(ex.getMessage());
        }catch(Exception e){
            this.setCurrentMessage(e.getMessage());
        }
        
        // ako je konekcija ok
        if(connection != null){
            String msg = "Uspešna prijava na sistem.";
            this.setCurrentMessage(msg);
        }else{
            String errorMsg = "Neuspešna prijava na sistem. Pokušajte ponovo. ";
            this.setCurrentMessage(errorMsg);
        }
        
    }
    
    
}
