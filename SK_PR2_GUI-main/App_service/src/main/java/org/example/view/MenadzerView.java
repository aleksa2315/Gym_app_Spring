package org.example.view;

import org.example.MyApp;
import org.example.model.NotifikacijeModel;
import org.example.model.TerminiTableModel;
import org.example.model.ZakazaniTableModel;
import org.example.restClient.GymServiceClient;
import org.example.restClient.UserServiceClient;
import org.example.restClient.dto.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class MenadzerView extends JPanel {
    private JToolBar toolBar;
    private JPanel desktop;
    private JTable jTable;
    private TerminiTableModel terminiTableModel;
    private NotifikacijeModel notifikacijeModel;
    private ZakazaniTableModel zakazaniTableModel;

    private JSplitPane leftSplit;

    private MenadzerToolPanel menadzerToolPanel;

    private UserServiceClient userServiceClient;
    private GymServiceClient gymServiceClient;
    public MenadzerView(){
        userServiceClient = new UserServiceClient();
        gymServiceClient = new GymServiceClient();

        this.toolBar = new Toolbar();
        add(toolBar,BorderLayout.NORTH);

        this.desktop = new JPanel();
        this.desktop.setLayout(new BorderLayout());

        KorisnikKlijentDTO k = userServiceClient.getPodaci();
        this.menadzerToolPanel = new MenadzerToolPanel("Korisnik : " + k.getIme() + " " + k.getPrezime());

        terminiTableModel = new TerminiTableModel();
        notifikacijeModel = new NotifikacijeModel();
        zakazaniTableModel = new ZakazaniTableModel();

        jTable = new JTable(terminiTableModel);
        jTable.setFillsViewportHeight(true);
        jTable.setRowSelectionAllowed(true);
        jTable.setColumnSelectionAllowed(false);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menadzerToolPanel, new JScrollPane(jTable));

        this.add(splitPane,BorderLayout.CENTER);
        setVisible(true);
    }


    public void initNotifikacijeListTable() {
        NotifikacijeListaDto notifikacijeListaDto = userServiceClient.getNotifikacijeById();
        notifikacijeModel.removeRows();
        notifikacijeModel.getNotifikacijeListaDto().getContent().clear();
        notifikacijeListaDto.getContent().forEach(notifikacijeDto -> {
            notifikacijeModel.addRow(new Object[]{
                    notifikacijeDto.getDatumSlanja(),
                    notifikacijeDto.getEmail(),
                    notifikacijeDto.getKorisnikId(),
                    notifikacijeDto.getMessage(),
                    notifikacijeDto.getTipNotifikacije()});
        });
        jTable.setModel(notifikacijeModel);
        MyApp.getInstance().refreshPanel();
    }

    public void izmenaPodatakaOSali() {
        IzmeniPodatkeOSaliDialog editUserDataDialog = new IzmeniPodatkeOSaliDialog(this);
    }

    public void izmeniPodatkeOSali(){
        Map<String, String> podaci = new HashMap<>(IzmeniPodatkeOSaliDialog.getPodaci());
        FiskulturnaSalaDTO fiskulturnaSalaDTO = new FiskulturnaSalaDTO();
        FiskulturnaSalaDTO f = gymServiceClient.getPodaciFiskulturnaSala();
        if(!Objects.equals(podaci.get("ime"), ""))
            fiskulturnaSalaDTO.setIme(podaci.get("ime"));
        else{
            fiskulturnaSalaDTO.setIme(f.getIme());
        }
        if(!Objects.equals(podaci.get("kratak_opis"), ""))
            fiskulturnaSalaDTO.setKratakOpis(podaci.get("kratak_opis"));
        else{
            fiskulturnaSalaDTO.setKratakOpis(f.getKratakOpis());
        }
        if(!Objects.equals(podaci.get("broj_personalnih_trenera"), ""))
            fiskulturnaSalaDTO.setBroj_personalnih_trenera(Integer.parseInt(podaci.get("broj_personalnih_trenera")));
        else{
            fiskulturnaSalaDTO.setBroj_personalnih_trenera(f.getBroj_personalnih_trenera());
        }
        fiskulturnaSalaDTO.setId(f.getId());
        System.out.println(fiskulturnaSalaDTO.getIme());
        System.out.println(fiskulturnaSalaDTO.getKratakOpis());
        System.out.println(fiskulturnaSalaDTO.getBroj_personalnih_trenera());
        System.out.println(fiskulturnaSalaDTO.getId());

        gymServiceClient.izmeniPodatkeOSali(fiskulturnaSalaDTO);
    }

    public void izmenaPodataka(){
        EditUserDataDialog editUserDataDialog = new EditUserDataDialog(this,"");
    }

    public void izmeniPodatke() throws ParseException {
        Map<String, String> podaci = new HashMap<>(EditUserDataDialog.getPodaci());
        KorisniciDto korisniciDto = new KorisniciDto();
        KorisnikKlijentDTO k = userServiceClient.getPodaci();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        if(!Objects.equals(podaci.get("username"), ""))
            korisniciDto.setUsername(podaci.get("username"));
        else{
            korisniciDto.setUsername(k.getUsername());
        }
        korisniciDto.setPassword(k.getPassword());
        if(!Objects.equals(podaci.get("datumRodjenja"), "")) {
            date = dateFormat.parse(podaci.get("datumRodjenja"));
            korisniciDto.setDatumRodjenja(LocalDate.parse(dateFormat.format(date)));
        }else{
            date = k.getDatumRodjenja();
            korisniciDto.setDatumRodjenja(LocalDate.parse(dateFormat.format(date)));
        }

        if(!Objects.equals(podaci.get("email"), ""))
            korisniciDto.setEmail(podaci.get("email"));
        else{
            korisniciDto.setEmail(k.getEmail());
        }

        if(!Objects.equals(podaci.get("ime"), ""))
            korisniciDto.setIme(podaci.get("ime"));
        else{
            korisniciDto.setIme(k.getIme());
        }

        if(!Objects.equals(podaci.get("prezime"), ""))
            korisniciDto.setPrezime(podaci.get("prezime"));
        else{
            korisniciDto.setPrezime(k.getPrezime());
        }

        korisniciDto.setId(Math.toIntExact(userServiceClient.getKorisnikId()));
        menadzerToolPanel.getLabel().setText("Korisnik : " + korisniciDto.getIme() + " " + korisniciDto.getPrezime());

        userServiceClient.izmeniPodatke(korisniciDto);
    }

    public void promeniSifru(){
        EditUserDataDialog editUserDataDialog = new EditUserDataDialog(this,"sifra");
    }

    public void izmeniSifru(){
        Map<String, String> podaci = new HashMap<>(EditUserDataDialog.getPodaci());
        KorisniciDto korisniciDto = new KorisniciDto();
        KorisnikKlijentDTO k = userServiceClient.getPodaci();
        if(!Objects.equals(podaci.get("password"), ""))
            korisniciDto.setPassword(podaci.get("password"));
        else{
            korisniciDto.setPassword(k.getPassword());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        korisniciDto.setUsername(k.getUsername());
        date = k.getDatumRodjenja();
        korisniciDto.setDatumRodjenja(LocalDate.parse(dateFormat.format(date)));
        korisniciDto.setEmail(k.getEmail());
        korisniciDto.setIme(k.getIme());
        korisniciDto.setPrezime(k.getPrezime());

        korisniciDto.setId(Math.toIntExact(userServiceClient.getKorisnikId()));
        userServiceClient.izmeniSifru(korisniciDto);
    }

    public void otkaziTrening() {
        AtomicReference<ZakazaniTerminDTO> zakazaniTerminDTO1= new AtomicReference<>(new ZakazaniTerminDTO());
        zakazaniTableModel.getZakazaniTerminListaDto().getContent().forEach(zakazaniTerminDTO -> {
            if (zakazaniTerminDTO.getId().equals(jTable.getValueAt(jTable.getSelectedRow(),0))){
                zakazaniTerminDTO1.set(zakazaniTerminDTO);
            }
        });
        gymServiceClient.obrisiZakazaniTermin(zakazaniTerminDTO1.get().getTerminTreningaDto().getId());
        initZauzetiTerminiTable();
    }

    public void initZauzetiTerminiTable() {
        ZakazaniTerminListaDto zakazaniTerminListaDto = gymServiceClient.getZauzetiZaSalu();
        zakazaniTableModel.removeRows();
        zakazaniTableModel.getZakazaniTerminListaDto().getContent().clear();
        zakazaniTerminListaDto.getContent().forEach(zakazaniTerminDTO -> {
            zakazaniTableModel.addRow(new Object[]{
                    zakazaniTerminDTO.getId(),
                    zakazaniTerminDTO.getTerminTreningaDto().getNazivSale(),
                    zakazaniTerminDTO.getCena(),
                    zakazaniTerminDTO.getKlijentId(),
                    zakazaniTerminDTO.getTerminTreningaDto().getDatum(),
                    zakazaniTerminDTO.getTerminTreningaDto().getVremePocetka()});
            zakazaniTableModel.getZakazaniTerminListaDto().getContent().add(zakazaniTerminDTO);
        });
        jTable.setModel(zakazaniTableModel);
        MyApp.getInstance().refreshPanel();

    }

    public void initSlobodniTerminiTable() {
        TerminTreningaListDto terminTreningaListDto = gymServiceClient.getSlobodniZaSalu();
        terminiTableModel.removeRows();
        terminiTableModel.getTerminTreningaListDto().getContent().clear();
        terminTreningaListDto.getContent().forEach(terminTreningaDto -> {
            terminiTableModel.addRow(new Object[]{
                    terminTreningaDto.getNazivSale(),
                    terminTreningaDto.getNazivTreninga(),
                    terminTreningaDto.getDatum(),
                    terminTreningaDto.getVremePocetka(),
                    terminTreningaDto.getMaksimalanBrojUcesnika()});
            terminiTableModel.getTerminTreningaListDto().getContent().add(terminTreningaDto);
        });
        jTable.setModel(terminiTableModel);
        MyApp.getInstance().refreshPanel();
    }

    public void dodajNoviTermin() {
        Map<String, String> podaci = new HashMap<>(DodajTerminDijalog.getPodaci());
        TerminTreningaDto terminTreningaDto = new TerminTreningaDto();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        if(!Objects.equals(podaci.get("idSale"), ""))
            terminTreningaDto.setIdSale(Long.valueOf(podaci.get("idSale")));
        else{
            terminTreningaDto.setIdSale(1L);
        }
        if(!Objects.equals(podaci.get("nazivTreninga"), ""))
            terminTreningaDto.setIdTreninga(Long.valueOf(podaci.get("nazivTreninga")));
        else{
            terminTreningaDto.setIdTreninga(1L);
        }
        if(!Objects.equals(podaci.get("datum"), "")){
            try {
                date = dateFormat.parse(podaci.get("datum"));
                terminTreningaDto.setDatum(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else{
            terminTreningaDto.setDatum(new Date(String.valueOf(LocalDate.now())));
        }
        if(!Objects.equals(podaci.get("vremePocetka"), "")) {
            System.out.println(podaci.get("vremePocetka"));
            terminTreningaDto.setVremePocetka(Time.valueOf(podaci.get("vremePocetka")));
        }else{
            terminTreningaDto.setVremePocetka(Time.valueOf(LocalTime.now()));
        }
        if(!Objects.equals(podaci.get("maksimalanBrojUcesnika"), ""))
            terminTreningaDto.setMaksimalanBrojUcesnika(Integer.parseInt(podaci.get("maksimalanBrojUcesnika")));
        else{
            terminTreningaDto.setMaksimalanBrojUcesnika(10);
        }
        if(!Objects.equals(podaci.get("cena"), ""))
            terminTreningaDto.setCena(Integer.parseInt(podaci.get("cena")));
        else{
            terminTreningaDto.setCena(1000);
        }
        gymServiceClient.dodajNoviTermin(terminTreningaDto);
    }

    public void dodajTermin() {
        DodajTerminDijalog dodajTerminDialog = new DodajTerminDijalog(this);
    }

    public JToolBar getToolBar() {
        return toolBar;
    }

    public void setToolBar(JToolBar toolBar) {
        this.toolBar = toolBar;
    }

    public JPanel getDesktop() {
        return desktop;
    }

    public void setDesktop(JPanel desktop) {
        this.desktop = desktop;
    }

    public JTable getjTable() {
        return jTable;
    }

    public void setjTable(JTable jTable) {
        this.jTable = jTable;
    }

    public TerminiTableModel getTerminiTableModel() {
        return terminiTableModel;
    }

    public void setTerminiTableModel(TerminiTableModel terminiTableModel) {
        this.terminiTableModel = terminiTableModel;
    }

    public JSplitPane getLeftSplit() {
        return leftSplit;
    }

    public void setLeftSplit(JSplitPane leftSplit) {
        this.leftSplit = leftSplit;
    }

    public MenadzerToolPanel getToolPanel() {
        return menadzerToolPanel;
    }

    public void setToolPanel(MenadzerToolPanel menadzerToolPanel) {
        this.menadzerToolPanel = menadzerToolPanel;
    }

    public UserServiceClient getUserServiceClient() {
        return userServiceClient;
    }

    public void setUserServiceClient(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }
}
