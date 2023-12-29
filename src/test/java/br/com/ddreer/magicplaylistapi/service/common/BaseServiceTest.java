package br.com.ddreer.magicplaylistapi.service.common;

public interface BaseServiceTest {
    void mustReturnSuccessWhenList();

    void mustReturnSuccessWhenFindById();

    void mustReturnBusinessExceptionWhenFindById();

    void mustReturnSuccessWhenSave();

    void mustReturnBusinessExceptionWhenSave();

    void mustReturnSuccessWhenEdit();

    void mustReturnBusinessExceptionWhenEdit();

    void mustReturnSuccessWhenDelete();

    void mustReturnBusinessExceptionWhenDelete();
}
