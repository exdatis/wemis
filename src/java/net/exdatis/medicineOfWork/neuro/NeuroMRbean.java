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

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mkb.MKB;
import net.exdatis.medicineOfWork.ShowMowWaitBean;
import net.exdatis.operator.CurrentUserBean;
import net.exdatis.wdb.Wdb;

/**
 * kontroler za neuro (medicina rada).
 * @author morar
 */

@ManagedBean(name = "neuroMRbean", eager = true)
@ViewScoped
public class NeuroMRbean implements Serializable{
    
    // za db
    private final String currentUser = CurrentUserBean.getDB_USER();
    private final String currentPwd = CurrentUserBean.getDB_PWD();
    private final String currentHost = CurrentUserBean.getDB_HOST();
    
    private final Connection connection;
    
    /*
        Kao glavni podatak tu je id prijema koji je napravljen pre 
        koriscenja ovog bean-a.
    */
    
    private int prijemID; // njega treba prvog pronaci
    // uzeti osnovne pdoatke pacijenta za ugodniji rad da lekar ne zaboravi skim radi???
    private String infoPacijent;
    
    /*
        Opsti odeljak, poruke.
    */
    
    private String poruka;
    
    /*
        Clanovi klase za anamnezu (neuropsihijatrija - medicina rada).
    */
    
    private int anamnezaId;
    private int anamnezaPrijem;
    private String anamnezaTekst;
    
    private NeuroAnamnezaMR selectedAnamneza;
    
    /* Clanovi klase za upitnik (neuropsihijatar - medicina rada) */
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
    
    private NeuroUpitnikMR selectedUpitnik;
    /* Clanovi klase za neuro-mkb (lista dijagnoza , neuropsihijatrija - 
       medicina rada.
    */
    
    private int dijagnozaId;
    private int dijagnozaPrijem;
    private int dijagnozaMKB;
    private String dijagnozaKomentar;
    // iz pogleda
    private String dijagnozaLatin;
    private String dijagnozaSerbian;
    
    private NeuroMKBmr selectedNeuroMKB;
    
    private ArrayList<NeuroMKBmr> listaDijagnoza;
    private Map<String, Object> argPretrageMKB;
    // ovo je lista pronadjenih MKB sifara na osnovu pretrage
    private ArrayList<mkb.MKB> mkbLista;
    private mkb.MKB selectedMKB;
    
    /*
        Clanovi klase zakljucak (neuropsihijatrija - medicina rada).
    */
    
    private int zakljucakId;
    private int zakljucakPrijem;
    private int zakljucakSposoban; // jedan jeste default je 0-nije sposoban
    private String zakljucakKomentar;
    
    private NeuroZakljucakMR selectedZakljucak;
    
    /**
     * podrazumevani konstruktor
     */
    public NeuroMRbean() {
        // test konekcija u konstruktoru -- videcemo kako ovo ide
        connection = Wdb.getDbConnection(currentUser, currentPwd, currentHost);
    }
    
    @PostConstruct
    void init(){
        // najpre prijem
        prijemID = ShowMowWaitBean.uradjeniPrijem; // id prijema
        // iniciranje lista, klasa i sl.
        selectedAnamneza = NeuroAnamnezaMR.getAnamnezaPoPrijemu(connection, prijemID);
        selectedUpitnik = NeuroUpitnikMR.getUpitnikPoPrijemu(connection, prijemID);
        selectedNeuroMKB = new NeuroMKBmr();
        listaDijagnoza = NeuroMKBmr.getDijagnozePoPrijemu(connection, prijemID);
        // pretraga mkb
        argPretrageMKB = MKB.mkbMapaArgumenata();
        // lista mkb sifarnika pronadjena u pretrazi
        mkbLista = new ArrayList<>();
        // dijagnoza selektovana za unos (kod neuro mkb)
        selectedMKB = new MKB();
        // akrivan -selektovan zakljucak
        selectedZakljucak = NeuroZakljucakMR.getZakljucakPoPrijemu(connection, prijemID);
        // kreiraj info poruku
        String ime = ShowMowWaitBean.prezimeIme;
        String jmb = ShowMowWaitBean.jmbg;
        String uverenje = ShowMowWaitBean.uverenje;
        String info = " " + ime + " JMBG: " + jmb + " uverenje: " + uverenje;
        setInfoPacijent(info);
    }
    
    // get-set

    public int getPrijemID() {
        return prijemID;
    }

    public void setPrijemID(int prijemID) {
        this.prijemID = prijemID;
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

    public NeuroAnamnezaMR getSelectedAnamneza() {
        return selectedAnamneza;
    }

    public void setSelectedAnamneza(NeuroAnamnezaMR selectedAnamneza) {
        this.selectedAnamneza = selectedAnamneza;
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

    public NeuroUpitnikMR getSelectedUpitnik() {
        return selectedUpitnik;
    }

    public void setSelectedUpitnik(NeuroUpitnikMR selectedUpitnik) {
        this.selectedUpitnik = selectedUpitnik;
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
    
    

    public NeuroMKBmr getSelectedNeuroMKB() {
        return selectedNeuroMKB;
    }

    public void setSelectedNeuroMKB(NeuroMKBmr selectedNeuroMKB) {
        this.selectedNeuroMKB = selectedNeuroMKB;
    }

    public ArrayList<NeuroMKBmr> getListaDijagnoza() {
        return listaDijagnoza;
    }

    public void setListaDijagnoza(ArrayList<NeuroMKBmr> listaDijagnoza) {
        this.listaDijagnoza = listaDijagnoza;
    }

    public Map<String, Object> getArgPretrageMKB() {
        return argPretrageMKB;
    }

    public void setArgPretrageMKB(Map<String, Object> argPretrageMKB) {
        this.argPretrageMKB = argPretrageMKB;
    }

    public ArrayList<MKB> getMkbLista() {
        return mkbLista;
    }

    public void setMkbLista(ArrayList<MKB> mkbLista) {
        this.mkbLista = mkbLista;
    }

    public MKB getSelectedMKB() {
        return selectedMKB;
    }

    public void setSelectedMKB(MKB selectedMKB) {
        this.selectedMKB = selectedMKB;
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

    public NeuroZakljucakMR getSelectedZakljucak() {
        return selectedZakljucak;
    }

    public void setSelectedZakljucak(NeuroZakljucakMR selectedZakljucak) {
        this.selectedZakljucak = selectedZakljucak;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getCurrentPwd() {
        return currentPwd;
    }

    public String getCurrentHost() {
        return currentHost;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getInfoPacijent() {
        return infoPacijent;
    }

    public void setInfoPacijent(String infoPacijent) {
        this.infoPacijent = infoPacijent;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
    
    
    
    // sacuvaj anamnezu
    public String saveAnamneza(){
        setPoruka("Test poruka broj jedan");
        return null;
    }
    
    
    
    
}
