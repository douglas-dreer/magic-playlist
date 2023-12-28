package br.com.ddreer.magicplaylistapi.controller;

import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import br.com.ddreer.magicplaylistapi.service.RecordServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/record")
@AllArgsConstructor
@Log4j2
public class RecordController {
    private RecordServiceImpl service;

    @GetMapping
    public ResponseEntity<List<RecordDTO>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RecordDTO> findById(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(service.findById(uuid));
    }

    @GetMapping(value = "", params = "name")
    public ResponseEntity<List<RecordDTO>> findByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @PostMapping(value = "")
    public ResponseEntity<RecordDTO> save(@Validated @RequestBody RecordDTO recordRequest, HttpServletRequest httpServletRequest) {
        try {
            RecordDTO recordSaved = service.save(recordRequest);

            if (recordSaved == null) {
                throw new BusinessException("There was an error saving the recorder");
            }

            URI location = new URI(String.format("%s/%s", httpServletRequest.getRequestURL(), recordSaved.getId()));
            return ResponseEntity.created(location).body(recordSaved);


        } catch (BusinessException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(value = "/{uuid}")
    public ResponseEntity<RecordDTO> edit(@PathVariable("uuid") UUID uuid, @RequestBody RecordDTO recordRequest, HttpServletRequest httpServletRequest) {
        try {
            if (!recordRequest.getId().equals(uuid)) {
                throw new BadRequestException("The id code is different from the one sent.");
            }

            RecordDTO recordEdited = service.edit(recordRequest);

            if (recordEdited == null) {
                throw new BusinessException("There was an error saving the recorder");
            }

            URI location = new URI(String.format("%s/%s", httpServletRequest.getRequestURL(), recordEdited.getId()));
            return ResponseEntity.created(location).body(recordEdited);

        } catch (BusinessException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(null);
        } catch (URISyntaxException | BadRequestException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> delete(@PathVariable("uuid") UUID uuid) {
        try {
            if (!service.delete(uuid)) {
                throw new BusinessException("Unable to save the record. Check if the code exists.");
            }
            return ResponseEntity.ok("The record deleted with success");
        } catch (BusinessException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
