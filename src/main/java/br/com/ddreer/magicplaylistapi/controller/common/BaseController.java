package br.com.ddreer.magicplaylistapi.controller.common;

import br.com.ddreer.magicplaylistapi.entity.common.BaseEntity;
import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.common.BaseModel;
import br.com.ddreer.magicplaylistapi.service.common.BaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseController<T extends BaseEntity, M extends BaseModel, S extends BaseService<T, M>> {
    protected S service;

    protected BaseController(S service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<M>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<M> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.of(Optional.ofNullable(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<M> save(@Validated @RequestBody M model, HttpServletRequest request) throws URISyntaxException {
        try {
            M savedEntity = service.save(model);
            URI location = new URI(String.format("%s/%s", request.getRequestURL(), savedEntity.getId()));
            return ResponseEntity.created(location).body(savedEntity);
        } catch (BusinessException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<M> edit(@PathVariable("uuid") UUID uuid, @Validated @RequestBody M model) {
        M editedEntity = service.edit(model);

        if (editedEntity == null) {
            return ResponseEntity.notFound().build();
        }

        if (!model.getId().equals(uuid)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(editedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        if (!service.delete(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Deleted successfully");
    }
}
