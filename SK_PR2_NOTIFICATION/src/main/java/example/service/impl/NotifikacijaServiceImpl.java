package example.service.impl;

import example.domain.Notifikacija;
import example.domain.TipNotifikacije;
import example.dto.NotifikacijaDTO;
import example.dto.NotifikacijeCreateDTO;
import example.mapper.NotifikacijaMapper;
import example.repository.NotifikacijaRepository;
import example.repository.TipNotifikacijeRepository;
import example.service.NotifikacijaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class NotifikacijaServiceImpl implements NotifikacijaService {

    private NotifikacijaRepository notifikacijaRepository;
    private NotifikacijaMapper notifikacijaMapper;
    private TipNotifikacijeRepository tipNotifikacijeRepository;

    public NotifikacijaServiceImpl(NotifikacijaRepository notifikacijaRepository, NotifikacijaMapper notifikacijaMapper, TipNotifikacijeRepository tipNotifikacijeRepository) {
        this.notifikacijaRepository = notifikacijaRepository;
        this.notifikacijaMapper = notifikacijaMapper;
        this.tipNotifikacijeRepository = tipNotifikacijeRepository;
    }

    @Override
    public NotifikacijaDTO dodajNotifikaciju(NotifikacijeCreateDTO createNotificationDto) {
        String tipNotifikacijeName = createNotificationDto.getTipNotifikacije().getType();
        System.out.println(tipNotifikacijeName);
        TipNotifikacije existingTipNotifikacije = tipNotifikacijeRepository.findNotificationTypeByType(tipNotifikacijeName).get();
        Notifikacija notifikacija = notifikacijaMapper.createNotifikacijaDTOToNotifikacije(createNotificationDto);
        notifikacija.setTipNotifikacije(existingTipNotifikacije);
        notifikacijaRepository.save(notifikacija);
        return notifikacijaMapper.notifikacijaToNotifikacijaDTO(notifikacija);
    }

    @Override
    public List<NotifikacijaDTO> getSveKorisniceNotifikacije(Long id) {
        List<Notifikacija> listaNotifikacija = notifikacijaRepository.findNotifikacijaByKorisnikId(id);

        List<NotifikacijaDTO> listaDTONotifikacije = new ArrayList<>();

        for(Notifikacija notif : listaNotifikacija)
        {
            listaDTONotifikacije.add(notifikacijaMapper.notifikacijaToNotifikacijaDTO(notif));
        }

        return listaDTONotifikacije;
    }

    @Override
    public List<NotifikacijaDTO> getSveNotifikacije() {
        List<Notifikacija> listaNotifikacija = notifikacijaRepository.getAllNotifikacije();
        List<NotifikacijaDTO> listaDTONotifikacije = new ArrayList<>();

        for(Notifikacija notif : listaNotifikacija)
        {
            listaDTONotifikacije.add(notifikacijaMapper.notifikacijaToNotifikacijaDTO(notif));
        }

        return listaDTONotifikacije;
    }
}
