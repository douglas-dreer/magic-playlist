package br.com.ddreer.magicplaylistapi.dto;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface ModelBaseTest {
    void mustReturnSuccessWhenInitializeWithConstructor();

    void mustReturnSuccessWhenInitializeWithSetters();

    void mustReturnSuccessWhenInitializeWithBuilder();

    void mustReturnSuccessWhenConvertToEntity();

    void mustReturnSuccessWhenConvertToJSON() throws JsonProcessingException;

    void mustReturnSuccessWhenConterToObject() throws IOException;
}
