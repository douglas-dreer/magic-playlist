package br.com.ddreer.magicplaylistapi.controller;

import br.com.ddreer.magicplaylistapi.controller.common.BaseController;
import br.com.ddreer.magicplaylistapi.entity.Record;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import br.com.ddreer.magicplaylistapi.service.common.RecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/record")
@Log4j2
public class RecordController extends BaseController<Record, RecordDTO, RecordService> {
    public RecordController(RecordService service) {
        super(service);
    }
    @GetMapping(value = "", params = "name")
    public ResponseEntity<List<RecordDTO>> findByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(service.findByName(name));
    }
}
