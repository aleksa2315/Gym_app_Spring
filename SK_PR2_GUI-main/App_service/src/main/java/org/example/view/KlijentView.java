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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class KlijentView extends JPanel {
    private JToolBar toolBar;
    private JPanel desktop;
    private JTable jTable;
    private TerminiTableModel terminiTableModel;
    private NotifikacijeModel notifikacijeModel;
    private ZakazaniTableModel zakazaniTableModel;

    private JSplitPane leftSplit;

    private KlijentToolPanel klijentToolPanel;

    private UserServiceClient userServiceClient;
    private GymServiceClient gymServiceClient;
    public KlijentView(){
        userServiceClient = new UserServiceClient();
        gymServiceClient = new GymServiceClient();


        this.toolBar = new Toolbar();
        add(toolBar,BorderLayout.NORTH);

        this.desktop = new JPanel();
        this.desktop.setLayout(new BorderLayout());


        KorisnikKlijentDTO k = userServiceClient.getPodaci();
        this.klijentToolPanel = new KlijentToolPanel("Korisnik : " + k.getIme() + " " + k.getPrezime());

        terminiTableModel = new TerminiTableModel();
        notifikacijeModel = new NotifikacijeModel();
        zakazaniTableModel = new ZakazaniTableModel();
        jTable = new JTable();
        jTable.setFillsViewportHeight(true);
        jTable.setRowSelectionAllowed(true);
        jTable.setColumnSelectionAllowed(false);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, klijentToolPanel, new JScrollPane(jTable));

        this.add(splitPane,BorderLayout.CENTER);
        setVisible(true);
    }

    public void initZauzetiTerminiListTable() {
        ZakazaniTerminListaDto zakazaniTerminListaDto = userServiceClient.getTreninziZaKorisnika();
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
            terminiTableModel.getTerminTreningaListDto().getContent().add(terminTreningaDto);
        });
        jTable.setModel(terminiTableModel);
        MyApp.getInstance().refreshPanel();
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

    public void zakaziTrening() {
        terminiTableModel.getTerminTreningaListDto().getContent().forEach(terminTreningaDto -> {
            if (terminTreningaDto.getNazivSale().equals(jTable.getValueAt(jTable.getSelectedRow(),0))
            && terminTreningaDto.getNazivTreninga().equals(jTable.getValueAt(jTable.getSelectedRow(),1))
            && terminTreningaDto.getDatum().equals(jTable.getValueAt(jTable.getSelectedRow(),2))
            && terminTreningaDto.getVremePocetka().equals(jTable.getValueAt(jTable.getSelectedRow(),3))){
                gymServiceClient.zakaziTrening(terminTreningaDto);
            }
        });
    }

    public void otkaziTrening() {
        zakazaniTableModel.getZakazaniTerminListaDto().getContent().forEach(zakazaniTerminDTO -> {
            if(zakazaniTerminDTO.getId().equals(jTable.getValueAt(jTable.getSelectedRow(),0))){
                gymServiceClient.otkaziTrening(zakazaniTerminDTO);
            }
        });
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
        klijentToolPanel.getLabel().setText("Korisnik : " + korisniciDto.getIme() + " " + korisniciDto.getPrezime());

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


    public void filtrirajPoNazivu() {
        String naziv = JOptionPane.showInputDialog("Unesi naziv");
        ZakazaniTerminListaDto zakazaniTerminListaDto = userServiceClient.filtrirajPoNazivu(naziv);
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

    public void filtrirajPoDanu() {
        String dan = JOptionPane.showInputDialog("Unesi dan");
        ZakazaniTerminListaDto zakazaniTerminListaDto = userServiceClient.filtrirajPoDanu(dan);
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

    public void filtrirajPoTipu() {
        String tip = JOptionPane.showInputDialog("Unesi tip");
        ZakazaniTerminListaDto zakazaniTerminListaDto = userServiceClient.filtrirajPoTipu(tip);
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

    public KlijentToolPanel getToolPanel() {
        return klijentToolPanel;
    }

    public void setToolPanel(KlijentToolPanel klijentToolPanel) {
        this.klijentToolPanel = klijentToolPanel;
    }

    public UserServiceClient getUserServiceClient() {
        return userServiceClient;
    }

    public void setUserServiceClient(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

}
