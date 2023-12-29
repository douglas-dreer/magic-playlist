package br.com.ddreer.magicplaylistapi.controller;

import br.com.ddreer.magicplaylistapi.controller.common.BaseControllerTest;
import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import br.com.ddreer.magicplaylistapi.service.RecordServiceImpl;
import br.com.ddreer.magicplaylistapi.utility.InformationGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecordControllerTest extends InformationGenerator implements BaseControllerTest {
    private final static String ENDPOINT = "/record";
    private final static String RECORD_NOT_SAVED = "Unable to save record";
    private static final RecordDTO result = createARecordDTOForTests();
    private static final List<RecordDTO> resultList = Collections.singletonList(createARecordDTOForTests());
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecordServiceImpl service;

    @Override
    @Test
    public void mustReturnSuccessWhenList() throws Exception {
        MockHttpServletRequestBuilder getMethod = get(ENDPOINT);

        when(service.list()).thenReturn(resultList);

        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Override
    @Test
    public void mustReturnSuccessWhenFindById() throws Exception {
        UUID id = result.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.findById(any())).thenReturn(result);

        MockHttpServletRequestBuilder getMethod = get(uri);

        mockMvc.perform(getMethod)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void mustReturnSuccessWhenFindByName() throws Exception {
        when(service.findByName(any())).thenReturn(resultList);

        MockHttpServletRequestBuilder getMethod = get(ENDPOINT);
        getMethod.queryParam("name", result.getName());

        mockMvc.perform(getMethod)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenSave() throws Exception {
        when(service.save(any())).thenReturn(result);

        MockHttpServletRequestBuilder postMethod = post(ENDPOINT)
                .content(result.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Override
    @Test
    public void mustReturnInternalErrorExceptionWhenSave() throws Exception {
        when(service.save(any())).thenThrow(new BusinessException(RECORD_NOT_SAVED));

        MockHttpServletRequestBuilder postMethod = post(ENDPOINT)
                .content(result.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }

    @Override
    @Test
    public void mustReturnSuccessWhenEdit() throws Exception {
        UUID id = result.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.edit(any())).thenReturn(result);

        MockHttpServletRequestBuilder putMethod = put(uri)
                .content(result.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(putMethod)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Override
    @Test
    public void mustReturnBadRequestExceptionWhenEdit() throws Exception {
        UUID id = UUID.randomUUID();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.edit(any())).thenReturn(result);

        MockHttpServletRequestBuilder putMethod = put(uri)
                .content(result.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(putMethod)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Override
    @Test
    public void mustReturnNotFoundExceptionWhenEdit() throws Exception {
        UUID id = result.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.edit(any())).thenReturn(null);

        MockHttpServletRequestBuilder putMethod = put(uri)
                .content(result.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(putMethod)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenDelete() throws Exception {
        UUID id = result.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.delete(any())).thenReturn(true);

        MockHttpServletRequestBuilder deleteMethod = delete(uri)
                .content(result.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(deleteMethod)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenDelete() throws Exception {
        UUID id = result.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.delete(any())).thenReturn(false);

        MockHttpServletRequestBuilder deleteMethod = delete(uri)
                .content(result.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(deleteMethod)
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
