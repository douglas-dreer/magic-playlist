package br.com.ddreer.magicplaylistapi.controller;

import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import br.com.ddreer.magicplaylistapi.service.RecordServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecordControllerTest {
    private final static String ENDPOINT = "/record";
    private final static String NOT_SAVED = "Unable to save record";
    private final static String NOT_DELETED = "Unable to delete record";
    private static List<RecordDTO> recordList = new ArrayList<>();
    private static RecordDTO record = new RecordDTO();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecordServiceImpl service;

    @BeforeAll
    public static void setup() {
        record.setId(UUID.randomUUID());
        record.setName("Globa Records");
        record.setCity(CityEnum.RIO);
        record.setFoundingYear(1990);
        record.setActive(true);

        recordList = Collections.singletonList(record);
    }

    @Test
    void mustReturnListRecordDTOSuccessWhenList() throws Exception {
        MockHttpServletRequestBuilder getMethod = get(ENDPOINT);
        when(service.list()).thenReturn(recordList);
        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    void mustReturnRecordDTOSuccessWhenFindById() throws Exception {
        UUID id = record.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.findById(any())).thenReturn(record);

        MockHttpServletRequestBuilder getMethod = get(uri);

        mockMvc.perform(getMethod)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void mustReturnListRecordDTOSuccessWhenFindByName() throws Exception {
        String name = record.getName();

        when(service.findByName(anyString())).thenReturn(recordList);

        MockHttpServletRequestBuilder getMethod = get(ENDPOINT)
                .queryParam("name", name);

        mockMvc.perform(getMethod)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void mustReturnRecordDTOSuccessWhenSave() throws Exception {
        when(service.save(any())).thenReturn(record);

        MockHttpServletRequestBuilder postMethod = post(ENDPOINT)
                .content(record.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void mustReturnRecordDTOBusinessExceptionWhenSave() throws Exception {
        when(service.save(any())).thenThrow(new BusinessException(NOT_SAVED));

        MockHttpServletRequestBuilder postMethod = post(ENDPOINT)
                .content(record.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    void mustReturnRecordDTOSuccessWhenEdit() throws Exception {
        UUID id = record.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.edit(any())).thenReturn(record);

        MockHttpServletRequestBuilder putMethod = put(uri)
                .content(record.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(putMethod)
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void mustReturnRecordDTOBadRequestExceptionWhenEdit() throws Exception {
        UUID id = UUID.randomUUID();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.edit(any())).thenReturn(record);

        MockHttpServletRequestBuilder putMethod = put(uri)
                .content(record.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(putMethod)
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void mustReturnRecordDTOBusinessExceptionWhenEdit() throws Exception {
        UUID id = record.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.edit(any())).thenThrow(new BusinessException(NOT_SAVED));

        MockHttpServletRequestBuilder putMethod = put(uri)
                .content(record.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(putMethod)
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }

    @Test
    void mustReturnRecordDTOSuccessWhenDelete() throws Exception {
        UUID id = record.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.delete(any())).thenReturn(true);

        MockHttpServletRequestBuilder deleteMethod = delete(uri)
                .content(record.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(deleteMethod)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void mustReturnRecordDTOBusinessExceptionWhenDelete() throws Exception {
        UUID id = record.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.delete(any())).thenThrow(new BusinessException(NOT_DELETED));

        MockHttpServletRequestBuilder deleteMethod = delete(uri)
                .content(record.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(deleteMethod)
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }

}
