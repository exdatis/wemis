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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.exdatis.location.Location;
import net.exdatis.operator.CurrentUserBean;
import net.exdatis.wdb.Wdb;

/**
 *
 * @author morar
 */

@ManagedBean(name = "personBean", eager = true)
@ViewScoped
public class PersonBean {
    // za db
    private final String currentUser = CurrentUserBean.getDB_USER();
    private final String currentPwd = CurrentUserBean.getDB_PWD();
    private final String currentHost = CurrentUserBean.getDB_HOST();
    // person
    private int personId;
    private String personCode;
    private String personName;
    private String personLBO;
    private String personJMBG;
    private String personHealthCard;
    private int personSubstation;
    
    private String errorMessage;
    
    private ArrayList<Person> persons;
    private Person selectedPerson;
    
    private Connection connection = Wdb.getDbConnection(currentUser, currentPwd, currentHost);;
    
    private Map<String, Object> locations = Location.getLocationsMap(connection);;

    public PersonBean() throws ClassNotFoundException {

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

    public int getPersonSubstation() {
        return personSubstation;
    }

    public void setPersonSubstation(int personSubstation) {
        this.personSubstation = personSubstation;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    
    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public Map<String, Object> getLocations() {
        return locations;
    }

    public void setLocations(Map<String, Object> locations) {
        this.locations = locations;
    }
    
    public void resetValues(){
        this.setErrorMessage(null);
        this.setPersonCode(null);
        this.setPersonName(null);
        this.setPersonLBO(null);
        this.setPersonJMBG(null);
        this.setPersonHealthCard(null);
        this.setPersonSubstation(689); // pancevo
        
    }
    
    public String addPerson(){
        return null;
        
    }
    
}
