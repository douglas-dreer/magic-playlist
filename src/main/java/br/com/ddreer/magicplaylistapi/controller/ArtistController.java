package br.com.ddreer.magicplaylistapi.controller;

import br.com.ddreer.magicplaylistapi.controller.common.BaseController;
import br.com.ddreer.magicplaylistapi.entity.Artist;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import br.com.ddreer.magicplaylistapi.service.common.ArtistService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artist")
@Log4j2
public class ArtistController extends BaseController<Artist, ArtistDTO, ArtistService> {
    public ArtistController(ArtistService service) {
        super(service);
    }

    @GetMapping(value = "", params = "real-name")
    public ResponseEntity<List<ArtistDTO>> findByRealName(@RequestParam(name = "real-name") String realName) {
        return ResponseEntity.ok(service.findByRealName(realName));
    }

    @GetMapping(value = "", params = "artist-name")
    public ResponseEntity<List<ArtistDTO>> findByArtistName(@RequestParam(name = "artist-name") String artistName) {
        return ResponseEntity.ok(service.findByRealName(artistName));
    }
}
