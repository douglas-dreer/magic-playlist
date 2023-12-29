package br.com.ddreer.magicplaylistapi.entity.common;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface BaseEntityTest {
    void mustReturnSuccessWhenInitializeWithConstructor();

    void mustReturnSuccessWhenInitializeWithSetters();

    void mustReturnSuccessWhenInitializeWithBuilder();

    void mustReturnSuccessWhenConvertToDTO();

    void mustReturnSuccessWhenConvertToJSON() throws JsonProcessingException;

    void mustReturnSUccessWhenConterToObject() throws IOException;
}
