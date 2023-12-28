package br.com.ddreer.magicplaylistapi.controller;

import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import br.com.ddreer.magicplaylistapi.service.ArtistServiceImpl;
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
@RequestMapping("/artist")
@AllArgsConstructor
@Log4j2
public class ArtistController {
    private ArtistServiceImpl service;

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ArtistDTO> findById(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(service.findById(uuid));
    }

    @GetMapping(value = "", params = "real-name")
    public ResponseEntity<List<ArtistDTO>> findByRealName(@RequestParam(name = "real-name") String realName) {
        return ResponseEntity.ok(service.findByRealName(realName));
    }

    @GetMapping(value = "", params = "artist-name")
    public ResponseEntity<List<ArtistDTO>> findByArtistName(@RequestParam(name = "artist-name") String artistName) {
        return ResponseEntity.ok(service.findByRealName(artistName));
    }

    @PostMapping(value = "")
    public ResponseEntity<ArtistDTO> save(@Validated @RequestBody ArtistDTO artistRequest, HttpServletRequest httpServletRequest) {
        try {
            ArtistDTO artistSaved = service.save(artistRequest);

            if (artistSaved == null) {
                throw new BusinessException("There was an error saving the artist");
            }

            URI location = new URI(String.format("%s/%s", httpServletRequest.getRequestURL(), artistSaved.getId()));
            return ResponseEntity.created(location).body(artistSaved);


        } catch (BusinessException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(null);
        } catch (URISyntaxException e) {
            throw new BusinessException(e.getLocalizedMessage());
        }
    }

    @PutMapping(value = "/{uuid}")
    public ResponseEntity<ArtistDTO> edit(@PathVariable("uuid") UUID uuid, @RequestBody ArtistDTO artistRequest, HttpServletRequest httpServletRequest) {
        try {
            if (!artistRequest.getId().equals(uuid)) {
                throw new BadRequestException("The id code is different from the one sent.");
            }

            ArtistDTO artistEdited = service.edit(artistRequest);

            if (artistEdited == null) {
                throw new BusinessException("There was an error saving the recorder");
            }

            URI location = new URI(String.format("%s/%s", httpServletRequest.getRequestURL(), artistEdited.getId()));
            return ResponseEntity.created(location).body(artistEdited);

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
                throw new BusinessException("Unable to save the artist. Check if the code exists.");
            }
            return ResponseEntity.ok("The artist deleted with success");
        } catch (BusinessException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
