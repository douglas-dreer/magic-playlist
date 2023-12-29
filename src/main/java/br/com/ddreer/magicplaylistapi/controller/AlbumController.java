package br.com.ddreer.magicplaylistapi.controller;

import br.com.ddreer.magicplaylistapi.controller.common.BaseController;
import br.com.ddreer.magicplaylistapi.entity.Album;
import br.com.ddreer.magicplaylistapi.model.AlbumDTO;
import br.com.ddreer.magicplaylistapi.service.common.AlbumService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
@Log4j2
public class AlbumController extends BaseController<Album, AlbumDTO, AlbumService> {
    public AlbumController(AlbumService service) {
        super(service);
    }
}
