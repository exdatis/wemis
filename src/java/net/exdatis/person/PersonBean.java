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

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.exdatis.location.Location;
import net.exdatis.operator.CurrentUserBean;
import net.exdatis.wdb.Wdb;
import personWait.AmbulanceWaitBean;

/**
 *
 * @author morar
 */
@ManagedBean(name = "personBean", eager = true)
@ViewScoped
public class PersonBean implements Serializable{

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
    private String locationZip;
    private String locationName;
    
    private String searchText;
    private Map<String, Object> argumentTypes = Person.argMap();
    private Person.ArgType currentSearchArgument;

    private String errorMessage;
    private String newPatientMsg;

    private ArrayList<Person> persons;
    private Person selectedPerson;

    private final Connection connection = Wdb.getDbConnection(currentUser, currentPwd, currentHost);
      
    private Map<String, Object> locations = Location.getLocationsMap(connection);


    public PersonBean() throws ClassNotFoundException {

    }
    
    @PostConstruct
    public void init(){
        this.persons = new ArrayList<>();
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

    public String getLocationZip() {
        return locationZip;
    }

    public void setLocationZip(String locationZip) {
        this.locationZip = locationZip;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    
    
    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getNewPatientMsg() {
        return newPatientMsg;
    }

    public void setNewPatientMsg(String newPatientMsg) {
        this.newPatientMsg = newPatientMsg;
    }
    
    

    public Map<String, Object> getArgumentTypes() {
        return argumentTypes;
    }

    public void setArgumentTypes(Map<String, Object> argumentTypes) {
        this.argumentTypes = argumentTypes;
    }

    public Person.ArgType getCurrentSearchArgument() {
        return currentSearchArgument;
    }

    public void setCurrentSearchArgument(Person.ArgType currentSearchArgument) {
        this.currentSearchArgument = currentSearchArgument;
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

    public void resetValues() {
        this.clearMessages();
        this.setPersonCode(null);
        this.setPersonName(null);
        this.setPersonLBO(null);
        this.setPersonJMBG(null);
        this.setPersonHealthCard(null);
        this.setPersonSubstation(0); // pancevo 689

    }

    public String addPerson() throws SQLException {
        this.clearMessages();
        // proveri pre slanja na server
        if(personName.isEmpty() || personJMBG.isEmpty()){
            String msg = "Error: Prezime-ime i matični broj su obavezni podaci.";
            this.setErrorMessage(msg);
            return null;
        }
        Person p = new Person();
        p.setPersonCode(personCode);
        p.setPersonName(personName);
        p.setPersonLBO(personLBO);
        p.setPersonJMBG(personJMBG);
        p.setPersonHealthCard(personHealthCard);
        p.setPersonSubstation(personSubstation);
        String msg = p.insertRec(this.getConnection());
        if(msg.equalsIgnoreCase("yes")){
            Person added = Person.getPersonById(connection, p.getPersonId());
            persons.add(added);
            return null;
        }
        // else
        msg = "Error: " + msg;
        this.setErrorMessage(msg);
        return null;
    }
    
    public String deletePerson(Person p) throws SQLException{
        this.clearMessages();
        String msg = p.deleteRec(this.getConnection());
        if(msg.equalsIgnoreCase("yes")){
            persons.remove(p);
            return null;
        }
        // else
        msg = "Error: " + msg;
        this.setErrorMessage(msg);
        return null;
    }
    
    public String editPerson(Person p){
        this.clearMessages();
        this.setSelectedPerson(p);
        p.setCanEdit(true);
        return null;
    }
    
    public String updatePerson() throws SQLException{
        // proveri greskom pritisnuto dugme
        if(this.getSelectedPerson() == null){
            String msg = "Error: Nema selektovanih slogova za izmenu.";
            this.setErrorMessage(msg);
            return null;
        }
        // resetuj poruke
        this.clearMessages();
        // update selektovanog sloga
        String success = this.getSelectedPerson().updateRec(this.getConnection());
        if(success.equalsIgnoreCase("yes")){
            for(Person p : persons){
                p.setCanEdit(false);
            }
            // eset selected Person
            this.setSelectedPerson(null);
            return null;
        }
        // else set errorMsg
        success = "Error: " + success;
        this.setErrorMessage(success);
        return null;
    }

    public String searchPerson() {
        this.resetPersonForWait(); // nema iabranih pacijenata
        // pretrazi pacijente-osobe
        switch (this.currentSearchArgument) {
            case FULL_JMBG:
                this.setPersons(Person.getPersonByJMBG(this.connection, this.searchText));
                break;
            case PARTIAL_JMBG:
                this.setPersons(Person.getPersonByJMBGpart(this.connection, this.searchText));
                break;
            case FULL_LBO:
                this.setPersons(Person.getPersonByLBO(this.connection, this.searchText));
                break;
            case PARTIAL_LBO:
                this.setPersons(Person.getPersonByLBOGpart(this.connection, this.searchText));
                break;
            case HEALTH_CARD:
                this.setPersons(Person.getPersonByHealthCard(this.connection, this.searchText));
                break;
            case PARTIAL_NAME:
                this.setPersons(Person.getPersonByName(this.connection, this.searchText));
                break;
            default:
                this.setPersons(Person.getPersonByJMBG(this.connection, this.searchText));
                break;
        }
        return null;
    }
    
    public void resetPersonForWait(){
        // reset newPatient from AmbulanceWaitBean (static method)
        this.clearMessages();
        AmbulanceWaitBean.setNewPerson(0);       
    }
    
    public String setPersonForWait(Person p){
        // set newPatient from AmbulanceWaitBean (static method)
        this.clearMessages();
        AmbulanceWaitBean.setNewPerson(p.getPersonId());  
        String prepareMsg = String.format("Selektovan pacijent ID: %d - %s. Popunite ostale podatke i snimite prijavu.", p.getPersonId(), p.getPersonName());
        this.setNewPatientMsg(prepareMsg);
        return null;
    }
    
    /**
     * Obrisi postojece poruke.
     */
    public void clearMessages(){
        this.setErrorMessage(null);
        this.setNewPatientMsg(null);
    }

}
