package org.example.restClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.example.MyApp;
import org.example.restClient.dto.*;
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
import java.util.Date;

public class GymServiceClient {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient;

    public GymServiceClient() {
        httpClient = HttpClient.newHttpClient();
    }

    public FiskulturnaSalaDTO getPodaciFiskulturnaSala() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/gym/api/fiskulturne-sale/vrati-salu"))
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), FiskulturnaSalaDTO.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void izmeniPodatkeOSali(FiskulturnaSalaDTO fiskulturnaSalaDTO) {
        try {
            String requestBody = objectMapper.writeValueAsString(fiskulturnaSalaDTO);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8084/gym/api/fiskulturne-sale/sacuvaj-ili-azuriraj"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(fiskulturnaSalaDTO)))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
            if(response.statusCode() == 200){
                JOptionPane.showMessageDialog(null, "Uspesno ste izmenili podatke o sali");
            }
        } catch (IOException | InterruptedException e) {
        }
    }

    public void zakaziTrening(TerminTreningaDto terminTreningaDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8084/gym/api/termin-treninga/zakazi-termin/"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(terminTreningaDto)))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
            if(response.statusCode() == 200){
                JOptionPane.showMessageDialog(null, "Uspesno ste zakazali trening");
            }
        } catch (IOException | InterruptedException e) {
        }
    }

    public void otkaziTrening(ZakazaniTerminDTO zakazaniTerminDTO) {
        try {
            System.out.println("zakazaniTerminDTO");
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(objectMapper.writeValueAsString(zakazaniTerminDTO));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8084/gym/api/termin-treninga/otkaziTermin"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(zakazaniTerminDTO)))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
            if(response.statusCode() == 200){
                JOptionPane.showMessageDialog(null, "Uspesno ste otkazali trening");
            }
        } catch (IOException | InterruptedException e) {
        }
    }

    public ZakazaniTerminListaDto getZauzetiZaSalu() {
        String imeSale = getImeSale();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URI.create("http://localhost:8084/gym/api/termin-treninga/izlistaj-zauzete-termine-za-salu") + "/" + imeSale))
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
                int klientId = getIdKorisnika().intValue();
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

    public TerminTreningaListDto getSlobodniZaSalu() {
        String imeSale = getImeSale();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URI.create("http://localhost:8084/gym/api/termin-treninga/izlizstaj-slobodno-za-salu") + "/" + imeSale))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
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

    private String getImeSale(){
        Long id = getIdKorisnika();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/users/api/korisnici/getImeSale/" + id))
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Long getIdKorisnika(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/users/api/korisnici/getUserID"))
                .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Long.valueOf(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void obrisiZakazaniTermin(Long id) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8084/gym/api/termin-treninga/obrisi-termin/" + id))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                    .DELETE()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
            if(response.statusCode() == 200){
                JOptionPane.showMessageDialog(null, "Uspesno ste obrisali trening");
            }
        } catch (IOException | InterruptedException e) {
        }
    }

    public void dodajNoviTermin(TerminTreningaDto terminTreningaDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8084/gym/api/termin-treninga/dodaj-termin"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + MyApp.getInstance().getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(terminTreningaDto)))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                JOptionPane.showMessageDialog(null, "Uspesno ste dodali novi trening");
            }
        } catch (IOException | InterruptedException e) {
        }
    }
}
