package org.example.view;

import org.example.MyApp;
import org.example.model.KorisniciModel;
import org.example.model.NotifikacijeModel;
import org.example.model.TerminiTableModel;
import org.example.model.ZakazaniTableModel;
import org.example.restClient.UserServiceClient;
import org.example.restClient.dto.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class AdminView extends JPanel {
    private JToolBar toolBar;
    private JPanel desktop;
    private JTable jTable;
    private TerminiTableModel terminiTableModel;
    private KorisniciModel korisniciTableModel;
    private NotifikacijeModel notifikacijeModel;
    private ZakazaniTableModel zakazaniTableModel;

    private JSplitPane leftSplit;

    private AdminToolPanel adminToolPanel;

    private UserServiceClient userServiceClient;
    public AdminView(){
        this.terminiTableModel = new TerminiTableModel();
        this.korisniciTableModel = new KorisniciModel();
        this.notifikacijeModel = new NotifikacijeModel();
        this.zakazaniTableModel = new ZakazaniTableModel();
        userServiceClient = new UserServiceClient();

        this.toolBar = new Toolbar();
        add(toolBar,BorderLayout.NORTH);

        this.desktop = new JPanel();
        this.desktop.setLayout(new BorderLayout());

        KorisnikKlijentDTO k = userServiceClient.getPodaci();
        this.adminToolPanel = new AdminToolPanel("Korisnik : " + k.getIme() + " " + k.getPrezime());

        jTable = new JTable();
        jTable.setFillsViewportHeight(true);
        jTable.setRowSelectionAllowed(true);
        jTable.setColumnSelectionAllowed(false);


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, adminToolPanel, new JScrollPane(jTable));

        this.add(splitPane,BorderLayout.CENTER);
    }


    public void initZauzetiTerminListTable() throws IOException {
        ZakazaniTerminListaDto zakazaniTerminListaDto = userServiceClient.getSviZakazaniTreninzi();
        zakazaniTableModel.removeRows();
        zakazaniTableModel.getZakazaniTerminListaDto().getContent().clear();
        zakazaniTerminListaDto.getContent().forEach(zakazaniTerminDto -> {
            zakazaniTableModel.addRow(new Object[]{
                    zakazaniTerminDto.getId(),
                    zakazaniTerminDto.getTerminTreningaDto().getNazivSale(),
                    zakazaniTerminDto.getCena(),
                    zakazaniTerminDto.getKlijentId(),
                    zakazaniTerminDto.getTerminTreningaDto().getDatum(),
                    zakazaniTerminDto.getTerminTreningaDto().getVremePocetka()});
        });
        jTable.setModel(zakazaniTableModel);
        MyApp.getInstance().refreshPanel();
    }
    public void initSlobodniTerminiListTable() {
        TerminTreningaListDto terminTreningaListDto = userServiceClient.getSviSlobodniTreninzi();
        terminiTableModel.removeRows();
        terminiTableModel.getTerminTreningaListDto().getContent().clear();
        terminTreningaListDto.getContent().forEach(terminTreningaDto -> {
            terminiTableModel.addRow(new Object[]{
                    terminTreningaDto.getNazivSale(),
                    terminTreningaDto.getNazivTreninga(),
                    terminTreningaDto.getDatum(),
                    terminTreningaDto.getVremePocetka(),
                    terminTreningaDto.getMaksimalanBrojUcesnika()});
        });
        jTable.setModel(terminiTableModel);
        MyApp.getInstance().refreshPanel();
    }
    public void initNotifikacijeListTable() {
        NotifikacijeListaDto notifikacijeListaDto = userServiceClient.getNotifikacije();
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
    public void tipNotifikacije() {
        DodajTipNotifikacijeDialog dodajTipNotifikacijeDialog = new DodajTipNotifikacijeDialog(this);
    }
    public void dodajTipNotifikacije(){
        Map<String, String> podaci = new HashMap<>(DodajTipNotifikacijeDialog.getPodaci());
        TipNotifikacijeDto tipNotifikacijeDto = new TipNotifikacijeDto();
        tipNotifikacijeDto.setType(podaci.get("tip"));
        tipNotifikacijeDto.setMessage(podaci.get("poruka"));
        userServiceClient.dodajTipNotifikacije(tipNotifikacijeDto);
    }

    public void KorisniciListTable(){
        KorisniciListaDto korisniciListaDto = userServiceClient.getKorisnici();
        korisniciTableModel.removeRows();
        korisniciTableModel.getKorisniciListaDto().getContent().clear();
        korisniciListaDto.getContent().forEach(korisniciDto -> {
            korisniciTableModel.addRow(new Object[]{
                    korisniciDto.getId(),
                    korisniciDto.getIme(),
                    korisniciDto.getPrezime(),
                    korisniciDto.getEmail(),
                    korisniciDto.getUsername(),
                    korisniciDto.getDatumRodjenja(),
                    korisniciDto.isZabranjenPristup()
                    });
            });
        jTable.setModel(korisniciTableModel);
        MyApp.getInstance().refreshPanel();
    }
    public void zabraniPristup() {
        korisniciTableModel.getKorisniciListaDto().getContent().forEach(korisniciDto -> {
            if (korisniciDto.getId().equals(jTable.getValueAt(jTable.getSelectedRow(),0))){
                korisniciDto.setZabranjenPristup(true);
                userServiceClient.zabraniPistup(korisniciDto);
            }
        });
        KorisniciListTable();
    }

    public void odobriPristup() {
        korisniciTableModel.getKorisniciListaDto().getContent().forEach(korisniciDto -> {
            if (korisniciDto.getId().equals(jTable.getValueAt(jTable.getSelectedRow(),0))){
                korisniciDto.setZabranjenPristup(false);
                userServiceClient.odobriPristup(korisniciDto);
            }
        });
        KorisniciListTable();
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

        if(!Objects.equals(podaci.get("datumRodjenja"), "")) {
            date = dateFormat.parse(podaci.get("datumRodjenja"));
            korisniciDto.setDatumRodjenja(LocalDate.parse(dateFormat.format(date)));
        }else{
            date = k.getDatumRodjenja();
            korisniciDto.setDatumRodjenja(LocalDate.parse(dateFormat.format(date)));
        }
        korisniciDto.setPassword(k.getPassword());
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
        adminToolPanel.getLabel().setText("Korisnik : " + korisniciDto.getIme() + " " + korisniciDto.getPrezime());

        userServiceClient.izmeniPodatke(korisniciDto);
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

    public AdminToolPanel getToolPanel() {
        return adminToolPanel;
    }

    public void setToolPanel(AdminToolPanel adminToolPanel) {
        this.adminToolPanel = adminToolPanel;
    }

    public UserServiceClient getUserServiceClient() {
        return userServiceClient;
    }

    public void setUserServiceClient(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }



}
