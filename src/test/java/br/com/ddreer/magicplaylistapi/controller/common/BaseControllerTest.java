package br.com.ddreer.magicplaylistapi.controller.common;

public interface BaseControllerTest {
    void mustReturnSuccessWhenList() throws Exception;

    void mustReturnSuccessWhenFindById() throws Exception;

    void mustReturnSuccessWhenSave() throws Exception;

    void mustReturnInternalErrorExceptionWhenSave() throws Exception;

    void mustReturnSuccessWhenEdit() throws Exception;

    void mustReturnBadRequestExceptionWhenEdit() throws Exception;

    void mustReturnNotFoundExceptionWhenEdit() throws Exception;

    void mustReturnSuccessWhenDelete() throws Exception;

    void mustReturnBusinessExceptionWhenDelete() throws Exception;

}
