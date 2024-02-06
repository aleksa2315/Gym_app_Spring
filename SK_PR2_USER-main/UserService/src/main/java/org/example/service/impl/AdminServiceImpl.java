package org.example.service.impl;

import org.example.domain.Zabrane;
import org.example.repository.ZabraneRepository;
import org.example.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final ZabraneRepository zabraneRepository;

    public AdminServiceImpl(ZabraneRepository zabraneRepository) {
        this.zabraneRepository = zabraneRepository;
    }

    @Override
    public void zabraniPristup(Integer korisnik_id) {
        zabraneRepository.updateZabranaStatus(korisnik_id, true);
    }

    @Override
    public void odobriPristup(Integer korisnik_id) {
        zabraneRepository.updateZabranaStatus(korisnik_id, false);
    }

    @Override
    public boolean isZabranjen(Integer korisnik_id) {
        if(!zabraneRepository.findById(korisnik_id).isPresent())
            return false;
        return zabraneRepository.findById(korisnik_id).get().isZabranjen();
    }
}
