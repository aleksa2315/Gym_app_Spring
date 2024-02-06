package org.example.restClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.*;
import org.example.MyApp;
import org.example.restClient.dto.*;
import org.example.view.TipNotifikacijeDto;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceClient {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static final String URL = "";
    private final HttpClient httpClient;

    OkHttpClient client = new OkHttpClient();

    public UserServiceClient() {
        httpClient = HttpClient.newHttpClient();
    }

    public ZakazaniTerminListaDto getSviZakazaniTreninzi(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/gym/api/termin-treninga/izlistaj-Termine-sve"))
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONArray(response.body());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            ZakazaniTerminListaDto terminTreningaListDto = new ZakazaniTerminListaDto();
            ZakazaniTerminListaDto zakazaniTerminListaDto = new ZakazaniTerminListaDto();
            for (int i = 0 ; i < jsonArray.length(); i++) {
                JSONObject object1 = jsonArray.getJSONObject(i);
                System.out.println(object1);
                int id = object1.getInt("id");
                int klientId = getKorisnikId().intValue();
                int cena = object1.getInt("cena");
                TerminTreningaDto terminTreningaDto = new TerminTreningaDto();
                int salaId = object1.getJSONObject("sala").getInt("id");
                String salaIme = object1.getJSONObject("sala").getString("ime");
                int tipTreningaId = object1.getJSONObject("tipTreninga").getInt("id");
                String tipTreningaNaziv = object1.getJSONObject("tipTreninga").getString("naziv");
                String datum = object1.getString("datum").substring(0, 10);
                String vremePocetka = object1.getString("vremePocetka");
                int maksimalanBrojUcesnika = object1.getInt("maksimalanBrojUcesnika");
                int brojUcesnika = object1.getInt("brojUcesnika");

                terminTreningaDto.setIdSale((long) salaId);
                terminTreningaDto.setIdTreninga((long) tipTreningaId);

                Date date = dateFormat.parse(datum);
                LocalDate localDate = LocalDate.parse(dateFormat.format(date));
                localDate = localDate.plusDays(1);
                date = dateFormat.parse(localDate.toString());

                terminTreningaDto.setDatum(date);
                terminTreningaDto.setVremePocetka(Time.valueOf(vremePocetka));
                terminTreningaDto.setMaksimalanBrojUcesnika(maksimalanBrojUcesnika);
                terminTreningaDto.setNazivTreninga(tipTreningaNaziv);
                terminTreningaDto.setNazivSale(salaIme);
                terminTreningaDto.setBrojUcesnika(brojUcesnika);

                ZakazaniTerminDTO zakazaniTerminDTO = new ZakazaniTerminDTO();
                zakazaniTerminDTO.setId((long) id);
                zakazaniTerminDTO.setKlijentId(klientId);
                zakazaniTerminDTO.setCena(cena);
                zakazaniTerminDTO.setTerminTreningaDto(terminTreningaDto);

                zakazaniTerminListaDto.getContent().add(zakazaniTerminDTO);
            }
            return zakazaniTerminListaDto;
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public NotifikacijeListaDto getNotifikacije() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/notification/api/notifikacija/getAllNotifications"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JSONArray jsonArray = new JSONArray(response.body());

            NotifikacijeListaDto notifikacijeListaDto = new NotifikacijeListaDto();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object1 = jsonArray.getJSONObject(i);
                int id = object1.getInt("id");
                String email = object1.getString("email");
                int korisnikId = object1.getInt("korisnikId");
                String message = object1.getString("message");
                String datum = object1.getString("datumSlanja").substring(0,10);
                String tipNotifikacijeNaziv = object1.getJSONObject("tipNotifikacije").getString("type");



                NotifikacijeDto notifikacijeDto = new NotifikacijeDto();
                notifikacijeDto.setId((long) id);
                notifikacijeDto.setEmail(email);
                notifikacijeDto.setKorisnikId(korisnikId);
                notifikacijeDto.setMessage(message);
                notifikacijeDto.setDatumSlanja(LocalDate.parse(datum));
                notifikacijeDto.setTipNotifikacije(tipNotifikacijeNaziv);

                notifikacijeListaDto.getContent().add(notifikacijeDto);
            }
            return notifikacijeListaDto;
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public void dodajTipNotifikacije(TipNotifikacijeDto tipNotifikacijeDto) {
        ObjectMapper objectMapper = new ObjectMapper();

        String requestBody = null;

        try {
            requestBody = objectMapper.writeValueAsString(tipNotifikacijeDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/notification/api/tip_notifikacije/insert"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201){
                JOptionPane.showMessageDialog(null, "Uspesno ste dodali tip notifikacije");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public NotifikacijeListaDto getNotifikacijeById() {
        Long id = getKorisnikId();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/notification/api/notifikacija/getNotificationsForClientId/" + id))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JSONArray jsonArray = new JSONArray(response.body());

            NotifikacijeListaDto notifikacijeListaDto = new NotifikacijeListaDto();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object1 = jsonArray.getJSONObject(i);
                int id1 = object1.getInt("id");
                String email = object1.getString("email");
                int korisnikId = object1.getInt("korisnikId");
                String message = object1.getString("message");
                String datum = object1.getString("datumSlanja").substring(0, 10);
                String tipNotifikacijeNaziv = object1.getJSONObject("tipNotifikacije").getString("type");

                NotifikacijeDto notifikacijeDto = new NotifikacijeDto();
                notifikacijeDto.setId((long) id1);
                notifikacijeDto.setEmail(email);
                notifikacijeDto.setKorisnikId(korisnikId);
                notifikacijeDto.setMessage(message);
                notifikacijeDto.setDatumSlanja(LocalDate.parse(datum));
                notifikacijeDto.setTipNotifikacije(tipNotifikacijeNaziv);

                notifikacijeListaDto.getContent().add(notifikacijeDto);
            }
            return notifikacijeListaDto;
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ZakazaniTerminListaDto getTreninziZaKorisnika(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/gym/api/termin-treninga/izlistaj-Termine-Korisnika"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONArray(response.body());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ZakazaniTerminListaDto zakazaniTerminListaDto = new ZakazaniTerminListaDto();
            for (int i = 0 ; i < jsonArray.length(); i++){
                JSONObject object1 = jsonArray.getJSONObject(i);
                System.out.println(object1);
                int id = object1.getInt("id");
                int klientId = getKorisnikId().intValue();
                int terminTreningaId = object1.getJSONObject("terminTreninga").getInt("id");
                int cena = object1.getInt("cena");
                TerminTreningaDto terminTreningaDto = new TerminTreningaDto();
                int salaId = object1.getJSONObject("terminTreninga").getJSONObject("sala").getInt("id");
                String salaIme = object1.getJSONObject("terminTreninga").getJSONObject("sala").getString("ime");
                int tipTreningaId = object1.getJSONObject("terminTreninga").getJSONObject("tipTreninga").getInt("id");
                String tipTreningaNaziv = object1.getJSONObject("terminTreninga").getJSONObject("tipTreninga").getString("naziv");
                String datum = object1.getJSONObject("terminTreninga").getString("datum").substring(0,10);
                String vremePocetka = object1.getJSONObject("terminTreninga").getString("vremePocetka");
                int maksimalanBrojUcesnika = object1.getJSONObject("terminTreninga").getInt("maksimalanBrojUcesnika");
                int brojUcesnika = object1.getJSONObject("terminTreninga").getInt("brojUcesnika");

                terminTreningaDto.setIdSale((long) salaId);
                terminTreningaDto.setId((long) terminTreningaId);
                terminTreningaDto.setIdTreninga((long) tipTreningaId);

                Date date = dateFormat.parse(datum);
                LocalDate localDate = LocalDate.parse(dateFormat.format(date));
                localDate = localDate.plusDays(1);
                date = dateFormat.parse(localDate.toString());

                terminTreningaDto.setDatum(date);
                terminTreningaDto.setVremePocetka(Time.valueOf(vremePocetka));
                terminTreningaDto.setMaksimalanBrojUcesnika(maksimalanBrojUcesnika);
                terminTreningaDto.setNazivTreninga(tipTreningaNaziv);
                terminTreningaDto.setNazivSale(salaIme);
                terminTreningaDto.setBrojUcesnika(brojUcesnika);

                ZakazaniTerminDTO zakazaniTerminDTO = new ZakazaniTerminDTO();
                zakazaniTerminDTO.setId((long) id);
                zakazaniTerminDTO.setKlijentId(klientId);
                zakazaniTerminDTO.setCena(cena);
                zakazaniTerminDTO.setTerminTreningaDto(terminTreningaDto);

                zakazaniTerminListaDto.getContent().add(zakazaniTerminDTO);
            }return zakazaniTerminListaDto;
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public TerminTreningaListDto getSviSlobodniTreninzi() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/gym/api/termin-treninga/izlistaj-slobodne-termine"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONArray(response.body());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            TerminTreningaListDto terminTreningaListDto = new TerminTreningaListDto();
            for (int i = 0 ; i < jsonArray.length(); i++){
                JSONObject object1 = jsonArray.getJSONObject(i);
                int id = object1.getInt("id");
                int salaId = object1.getJSONObject("sala").getInt("id");
                String salaIme = object1.getJSONObject("sala").getString("ime");
                int tipTreningaId = object1.getJSONObject("tipTreninga").getInt("id");
                String tipTreningaNaziv = object1.getJSONObject("tipTreninga").getString("naziv");
                String datum = object1.getString("datum").substring(0,10);
                String vremePocetka = object1.getString("vremePocetka");
                int maksimalanBrojUcesnika = object1.getInt("maksimalanBrojUcesnika");
                int brojUcesnika = object1.getInt("brojUcesnika");

                TerminTreningaDto terminTreningaDto = new TerminTreningaDto();
                terminTreningaDto.setIdSale(Long.valueOf(salaId));
                terminTreningaDto.setId(Long.valueOf(id));
                terminTreningaDto.setIdTreninga(Long.valueOf(tipTreningaId));

                Date date = dateFormat.parse(datum);
                LocalDate localDate = LocalDate.parse(dateFormat.format(date));
                localDate = localDate.plusDays(1);
                date = dateFormat.parse(localDate.toString());

                terminTreningaDto.setDatum(date);

                terminTreningaDto.setVremePocetka(Time.valueOf(vremePocetka));
                terminTreningaDto.setMaksimalanBrojUcesnika(maksimalanBrojUcesnika);
                terminTreningaDto.setNazivTreninga(tipTreningaNaziv);
                terminTreningaDto.setNazivSale(salaIme);
                terminTreningaDto.setBrojUcesnika(brojUcesnika);

                terminTreningaListDto.getContent().add(terminTreningaDto);
            }
            return terminTreningaListDto;
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public KorisnikKlijentDTO getPodaci(){
        Long id = getKorisnikId();
        KorisnikKlijentDTO korisnikKlijentDTO = new KorisnikKlijentDTO();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/users/api/korisnici/getUser/" + id))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .build();
        HttpResponse<String> response = null;
//{"username":"MarkoMaric","email":"maric@gmail.com","datumRodjenja":"2001-10-25",
// "ime":"Marko","prezime":"Maric","clanskaKarta":"12","zakazaniTreninzi":3}
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject data = new JSONObject(response.body());
            korisnikKlijentDTO.setEmail(data.getString("email"));
            korisnikKlijentDTO.setIme(data.getString("ime"));
            korisnikKlijentDTO.setUsername(data.getString("username"));
            korisnikKlijentDTO.setPrezime(data.getString("prezime"));
            korisnikKlijentDTO.setDatumRodjenja(dateFormat.parse(data.getString("datumRodjenja")));
            korisnikKlijentDTO.setPassword(data.getString("password"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return korisnikKlijentDTO;
    }

    public KorisniciListaDto getKorisnici() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/users/api/korisnici"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray jsonArray = jsonResponse.getJSONArray("content");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            KorisniciListaDto korisniciListaDto = new KorisniciListaDto();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object1 = jsonArray.getJSONObject(i);
                int id = object1.getInt("id");
                String username = object1.getString("username");
                String ime = object1.getString("ime");
                String prezime = object1.getString("prezime");
                String email = object1.getString("email");
                String datum = object1.getString("datumRodjenja").substring(0,10);

                KorisniciDto korisniciDto = new KorisniciDto();
                korisniciDto.setId(id);
                korisniciDto.setUsername(username);
                korisniciDto.setIme(ime);
                korisniciDto.setPrezime(prezime);
                korisniciDto.setEmail(email);

                request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8084/users/api/admin/pristupi/" + id))
                        .header("Content-Type", "application/json")
                        .build();
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                korisniciDto.setZabranjenPristup(Boolean.parseBoolean(response.body()));

                korisniciDto.setDatumRodjenja(LocalDate.parse(datum));
                korisniciListaDto.getContent().add(korisniciDto);
            }
            return korisniciListaDto;
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void zabraniPistup(KorisniciDto korisniciDto){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/users/api/admin/zabrani-pristup/" + korisniciDto.getId()))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200){
                JOptionPane.showMessageDialog(null, "Uspesno ste zabranili pristup korisniku");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void odobriPristup(KorisniciDto korisniciDto){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/users/api/admin/odobri-pristup/" + korisniciDto.getId()))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200){
                JOptionPane.showMessageDialog(null, "Uspesno ste odobrili pristup korisniku");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void izmeniSifru(KorisniciDto korisniciDto){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String requestBody = null;

        try {
            requestBody = objectMapper.writeValueAsString(korisniciDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/users/api/profil/promeni-lozinku"))
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200){
                JOptionPane.showMessageDialog(null, "Uspesno ste izmenili sifru");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void izmeniPodatke(KorisniciDto korisniciDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(korisniciDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/users/api/profil"))
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200){
                JOptionPane.showMessageDialog(null, "Uspesno ste izmenili podatke korisniku");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Long getKorisnikId(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/users/api/korisnici/getUserID"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Long.parseLong(response.body());
    }

    public boolean registrujKorisnika(String username, String password, String email, String ime, String prezime, String dateOfBirth, String userType){
        if (userType.equalsIgnoreCase("klijent")) {

            Map<String, Object> requestData = new HashMap<>();
            requestData.put("username", username);
            requestData.put("password", password);
            requestData.put("email", email);
            requestData.put("datumRodjenja", dateOfBirth);
            requestData.put("ime", ime);
            requestData.put("prezime", prezime);
            requestData.put("tipKorisnikaId", 3);
            requestData.put("tipKorisnikaNaziv", "KLIJENT");

            String jsonBody = mapToJson(requestData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8084/users/api/korisnici/register-user"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = null;
            try {
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (response.statusCode() == 201) {
                return true;
            }
        }else if (userType.equalsIgnoreCase("menadzer")){
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("username", username);
            requestData.put("password", password);
            requestData.put("email", email);
            requestData.put("datumRodjenja", dateOfBirth);
            requestData.put("ime", ime);
            requestData.put("prezime", prezime);
            requestData.put("tipKorisnikaId", 2);
            requestData.put("tipKorisnikaNaziv", "MENADZER");

            String jsonBody = mapToJson(requestData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8084/users/api/korisnici/register-manager"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = null;
            try {
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (response.statusCode() == 201) {
                return true;
            }
        }
        return false;
    }

    public ZakazaniTerminListaDto filtrirajPoDanu(String dan){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/gym/api/termin-treninga/filtirajPoDanu"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(dan))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JSONArray jsonArray = new JSONArray(response.body());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ZakazaniTerminListaDto zakazaniTerminListaDto = new ZakazaniTerminListaDto();
            for (int i = 0 ; i < jsonArray.length(); i++){
                JSONObject object1 = jsonArray.getJSONObject(i);
                System.out.println(object1);
                int id = object1.getInt("id");
                int klientId = getKorisnikId().intValue();
                int terminTreningaId = object1.getJSONObject("terminTreninga").getInt("id");
                int cena = object1.getInt("cena");
                TerminTreningaDto terminTreningaDto = new TerminTreningaDto();
                int salaId = object1.getJSONObject("terminTreninga").getJSONObject("sala").getInt("id");
                String salaIme = object1.getJSONObject("terminTreninga").getJSONObject("sala").getString("ime");
                int tipTreningaId = object1.getJSONObject("terminTreninga").getJSONObject("tipTreninga").getInt("id");
                String tipTreningaNaziv = object1.getJSONObject("terminTreninga").getJSONObject("tipTreninga").getString("naziv");
                String datum = object1.getJSONObject("terminTreninga").getString("datum").substring(0,10);
                String vremePocetka = object1.getJSONObject("terminTreninga").getString("vremePocetka");
                int maksimalanBrojUcesnika = object1.getJSONObject("terminTreninga").getInt("maksimalanBrojUcesnika");
                int brojUcesnika = object1.getJSONObject("terminTreninga").getInt("brojUcesnika");

                terminTreningaDto.setIdSale((long) salaId);
                terminTreningaDto.setId((long) terminTreningaId);
                terminTreningaDto.setIdTreninga((long) tipTreningaId);

                Date date = dateFormat.parse(datum);
                LocalDate localDate = LocalDate.parse(dateFormat.format(date));
                localDate = localDate.plusDays(1);
                date = dateFormat.parse(localDate.toString());

                terminTreningaDto.setDatum(date);
                terminTreningaDto.setVremePocetka(Time.valueOf(vremePocetka));
                terminTreningaDto.setMaksimalanBrojUcesnika(maksimalanBrojUcesnika);
                terminTreningaDto.setNazivTreninga(tipTreningaNaziv);
                terminTreningaDto.setNazivSale(salaIme);
                terminTreningaDto.setBrojUcesnika(brojUcesnika);

                ZakazaniTerminDTO zakazaniTerminDTO = new ZakazaniTerminDTO();
                zakazaniTerminDTO.setId((long) id);
                zakazaniTerminDTO.setKlijentId(klientId);
                zakazaniTerminDTO.setCena(cena);
                zakazaniTerminDTO.setTerminTreningaDto(terminTreningaDto);

                zakazaniTerminListaDto.getContent().add(zakazaniTerminDTO);
            }return zakazaniTerminListaDto;
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public ZakazaniTerminListaDto filtrirajPoTipu(String tip){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/gym/api/termin-treninga/filtirajPoIndividualni-Grupni"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(tip))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONArray(response.body());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ZakazaniTerminListaDto zakazaniTerminListaDto = new ZakazaniTerminListaDto();
            for (int i = 0 ; i < jsonArray.length(); i++){
                JSONObject object1 = jsonArray.getJSONObject(i);
                System.out.println(object1);
                int id = object1.getInt("id");
                int klientId = getKorisnikId().intValue();
                int terminTreningaId = object1.getJSONObject("terminTreninga").getInt("id");
                int cena = object1.getInt("cena");
                TerminTreningaDto terminTreningaDto = new TerminTreningaDto();
                int salaId = object1.getJSONObject("terminTreninga").getJSONObject("sala").getInt("id");
                String salaIme = object1.getJSONObject("terminTreninga").getJSONObject("sala").getString("ime");
                int tipTreningaId = object1.getJSONObject("terminTreninga").getJSONObject("tipTreninga").getInt("id");
                String tipTreningaNaziv = object1.getJSONObject("terminTreninga").getJSONObject("tipTreninga").getString("naziv");
                String datum = object1.getJSONObject("terminTreninga").getString("datum").substring(0,10);
                String vremePocetka = object1.getJSONObject("terminTreninga").getString("vremePocetka");
                int maksimalanBrojUcesnika = object1.getJSONObject("terminTreninga").getInt("maksimalanBrojUcesnika");
                int brojUcesnika = object1.getJSONObject("terminTreninga").getInt("brojUcesnika");

                terminTreningaDto.setIdSale((long) salaId);
                terminTreningaDto.setId((long) terminTreningaId);
                terminTreningaDto.setIdTreninga((long) tipTreningaId);

                Date date = dateFormat.parse(datum);
                LocalDate localDate = LocalDate.parse(dateFormat.format(date));
                localDate = localDate.plusDays(1);
                date = dateFormat.parse(localDate.toString());

                terminTreningaDto.setDatum(date);
                terminTreningaDto.setVremePocetka(Time.valueOf(vremePocetka));
                terminTreningaDto.setMaksimalanBrojUcesnika(maksimalanBrojUcesnika);
                terminTreningaDto.setNazivTreninga(tipTreningaNaziv);
                terminTreningaDto.setNazivSale(salaIme);
                terminTreningaDto.setBrojUcesnika(brojUcesnika);

                ZakazaniTerminDTO zakazaniTerminDTO = new ZakazaniTerminDTO();
                zakazaniTerminDTO.setId((long) id);
                zakazaniTerminDTO.setKlijentId(klientId);
                zakazaniTerminDTO.setCena(cena);
                zakazaniTerminDTO.setTerminTreningaDto(terminTreningaDto);

                zakazaniTerminListaDto.getContent().add(zakazaniTerminDTO);
            }return zakazaniTerminListaDto;
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public ZakazaniTerminListaDto filtrirajPoNazivu(String naziv){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/gym/api/termin-treninga/filtrirajPoNazivu"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(naziv))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONArray(response.body());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ZakazaniTerminListaDto zakazaniTerminListaDto = new ZakazaniTerminListaDto();
            for (int i = 0 ; i < jsonArray.length(); i++){
                JSONObject object1 = jsonArray.getJSONObject(i);
                System.out.println(object1);
                int id = object1.getInt("id");
                int klientId = getKorisnikId().intValue();
                int terminTreningaId = object1.getJSONObject("terminTreninga").getInt("id");
                int cena = object1.getInt("cena");
                TerminTreningaDto terminTreningaDto = new TerminTreningaDto();
                int salaId = object1.getJSONObject("terminTreninga").getJSONObject("sala").getInt("id");
                String salaIme = object1.getJSONObject("terminTreninga").getJSONObject("sala").getString("ime");
                int tipTreningaId = object1.getJSONObject("terminTreninga").getJSONObject("tipTreninga").getInt("id");
                String tipTreningaNaziv = object1.getJSONObject("terminTreninga").getJSONObject("tipTreninga").getString("naziv");
                String datum = object1.getJSONObject("terminTreninga").getString("datum").substring(0,10);
                String vremePocetka = object1.getJSONObject("terminTreninga").getString("vremePocetka");
                int maksimalanBrojUcesnika = object1.getJSONObject("terminTreninga").getInt("maksimalanBrojUcesnika");
                int brojUcesnika = object1.getJSONObject("terminTreninga").getInt("brojUcesnika");

                terminTreningaDto.setIdSale((long) salaId);
                terminTreningaDto.setId((long) terminTreningaId);
                terminTreningaDto.setIdTreninga((long) tipTreningaId);

                Date date = dateFormat.parse(datum);
                LocalDate localDate = LocalDate.parse(dateFormat.format(date));
                localDate = localDate.plusDays(1);
                date = dateFormat.parse(localDate.toString());

                terminTreningaDto.setDatum(date);
                terminTreningaDto.setVremePocetka(Time.valueOf(vremePocetka));
                terminTreningaDto.setMaksimalanBrojUcesnika(maksimalanBrojUcesnika);
                terminTreningaDto.setNazivTreninga(tipTreningaNaziv);
                terminTreningaDto.setNazivSale(salaIme);
                terminTreningaDto.setBrojUcesnika(brojUcesnika);

                ZakazaniTerminDTO zakazaniTerminDTO = new ZakazaniTerminDTO();
                zakazaniTerminDTO.setId((long) id);
                zakazaniTerminDTO.setKlijentId(klientId);
                zakazaniTerminDTO.setCena(cena);
                zakazaniTerminDTO.setTerminTreningaDto(terminTreningaDto);

                zakazaniTerminListaDto.getContent().add(zakazaniTerminDTO);
            }return zakazaniTerminListaDto;
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void otkaziTermin(ZakazaniTerminDTO zakazaniTerminDTO){

    }

    public ZakazaniTerminDTO dodajTermin(){
        return null;
    }

    private static String mapToJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (json.length() > 1) {
                json.append(",");
            }
            json.append("\"").append(entry.getKey()).append("\":");
            if (entry.getValue() instanceof String) {
                json.append("\"").append(entry.getValue()).append("\"");
            } else {
                json.append(entry.getValue());
            }
        }
        json.append("}");
        return json.toString();
    }
}