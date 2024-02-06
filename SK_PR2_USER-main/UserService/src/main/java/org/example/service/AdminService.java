package org.example.service;

public interface AdminService {
    void zabraniPristup(Integer korisnik_id);

    void odobriPristup(Integer korisnik_id);

    boolean isZabranjen(Integer korisnik_id);
}
