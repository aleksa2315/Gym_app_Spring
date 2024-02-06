package example.service.impl;

import example.domain.TipNotifikacije;
import example.dto.TipNotifikacijeDTO;
import example.mapper.TipNotifikacijeMapper;
import example.repository.TipNotifikacijeRepository;
import example.service.TipNotifikacijeService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TipNotifikacijeServiceImpl implements TipNotifikacijeService {
    private TipNotifikacijeRepository tipNotifikacijeRepository;
    private TipNotifikacijeMapper tipNotifikacijeMapper;

    public TipNotifikacijeServiceImpl(TipNotifikacijeRepository tipNotifikacijeRepository, TipNotifikacijeMapper tipNotifikacijeMapper) {
        this.tipNotifikacijeRepository = tipNotifikacijeRepository;
        this.tipNotifikacijeMapper = tipNotifikacijeMapper;
    }

    @Override
    public TipNotifikacijeDTO dodajTipNotifikacije(TipNotifikacijeDTO tipNotifikacijeDTO) {
        TipNotifikacije tipNotifikacije = tipNotifikacijeMapper.tipNotifikacijeDTOtoTipNotifikacije(tipNotifikacijeDTO);
        tipNotifikacijeRepository.save(tipNotifikacije);
        return tipNotifikacijeDTO;
    }

    @Override
    public TipNotifikacijeDTO getTipoviNotifikacije(String tip) throws NotFoundException {
        return tipNotifikacijeRepository.findNotificationTypeByType(tip)
                .map(tipNotifikacijeMapper::tipNotifikacijetoTipNotifikacijeDTO)
                .orElseThrow(() -> new NotFoundException(String.format("Notifikacija tipa: %tip nije pronadjena.", tip)));
    }

    @Override
    public TipNotifikacijeDTO getTipNotifikacije(Long id) {
        return tipNotifikacijeRepository.findById(id)
                .map(tipNotifikacijeMapper::tipNotifikacijetoTipNotifikacijeDTO)
                .orElseThrow(() -> new RuntimeException("Tip notifikacije nije pronadjen."));
    }

}
