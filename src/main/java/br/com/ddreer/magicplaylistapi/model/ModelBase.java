package br.com.ddreer.magicplaylistapi.model;

import br.com.ddreer.magicplaylistapi.utility.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

import static br.com.ddreer.magicplaylistapi.utility.Converter.convertTo;

@NoArgsConstructor
public abstract class ModelBase<T> {
    private Class<T> entityClass;

    protected ModelBase(Class<T> entityClass) {
        Objects.requireNonNull(entityClass, "entityClass must not be null");
        this.entityClass = entityClass;
    }

    public T toEntity() {
        return convertTo(this, entityClass);
    }

    public String toJSON() throws JsonProcessingException {
        return Converter.toJSON(this);
    }

    public T toObject(String dados) throws IOException {
        return Converter.toObject(dados, entityClass);
    }
}
