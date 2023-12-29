package br.com.ddreer.magicplaylistapi.service.common;

import br.com.ddreer.magicplaylistapi.entity.common.BaseEntity;
import br.com.ddreer.magicplaylistapi.model.common.BaseModel;

import java.util.List;
import java.util.UUID;

public interface BaseService<T extends BaseEntity, M extends BaseModel> {
    List<M> list();

    M findById(UUID id);

    M save(M model);

    M edit(M model);

    boolean delete(UUID id);
}
