package example.controller;

import example.dto.TipNotifikacijeDTO;
import example.service.TipNotifikacijeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tip_notifikacije")
public class TipNotifikacijeController {

    private TipNotifikacijeService tipNotifikacijeService;

    public TipNotifikacijeController(TipNotifikacijeService tipNotifikacijeService) {
        this.tipNotifikacijeService = tipNotifikacijeService;
    }

    @PostMapping("/insert")
    public ResponseEntity<TipNotifikacijeDTO> saveNotificationType(@RequestBody @Validated TipNotifikacijeDTO tipNotifikacijeDTO) {
        return new ResponseEntity<>(tipNotifikacijeService.dodajTipNotifikacije(tipNotifikacijeDTO), HttpStatus.CREATED);
    }

    @GetMapping("/getType/{id}")
    public ResponseEntity<String> getType(@PathVariable Long id) {
        String type = tipNotifikacijeService.getTipNotifikacije(id).getType();
        return new ResponseEntity<>(type, HttpStatus.OK);
    }


}