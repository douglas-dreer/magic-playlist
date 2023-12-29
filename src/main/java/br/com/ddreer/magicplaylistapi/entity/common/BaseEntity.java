package br.com.ddreer.magicplaylistapi.entity.common;

import br.com.ddreer.magicplaylistapi.utility.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static br.com.ddreer.magicplaylistapi.utility.Converter.convertTo;

@AllArgsConstructor
@Getter
public abstract class BaseEntity<T> {
    private Class<T> modelClass;

    protected BaseEntity() {
        this.modelClass = getModelClass();
    }

    private Class<T> getModelClass() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }

    public T toDTO() {
        return convertTo(this, modelClass);
    }

    public String toJSON() throws JsonProcessingException {
        return Converter.toJSON(this);
    }

    public T toObject(String dados) throws IOException {
        return Converter.toObject(dados, modelClass);
    }
}
