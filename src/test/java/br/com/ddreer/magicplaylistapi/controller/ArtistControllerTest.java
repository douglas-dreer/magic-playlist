package br.com.ddreer.magicplaylistapi.controller;

import br.com.ddreer.magicplaylistapi.controller.common.BaseControllerTest;
import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import br.com.ddreer.magicplaylistapi.service.ArtistServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArtistControllerTest extends InformationGenerator implements BaseControllerTest {
    private final static String ENDPOINT = "/artist";
    private final static String ARTIST_NOT_SAVED = "Unable to save artist";
    private final static String NOT_DELETED = "Unable to delete artist";
    private final static List<ArtistDTO> resultList = Collections.singletonList(createAnArtistDTOForTests());
    private static final ArtistDTO result = createAnArtistDTOForTests();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArtistServiceImpl service;

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
    void mustReturnListartistDTOSuccessWhenFindByRealName() throws Exception {
        String name = result.getRealName();

        when(service.findByRealName(anyString())).thenReturn(resultList);

        MockHttpServletRequestBuilder getMethod = get(ENDPOINT)
                .queryParam("real-name", name);

        mockMvc.perform(getMethod)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void mustReturnListartistDTOSuccessWhenFindByArtistName() throws Exception {
        String name = result.getArtisticName();

        when(service.findByArtisticName(anyString())).thenReturn(resultList);

        MockHttpServletRequestBuilder getMethod = get(ENDPOINT)
                .queryParam("artist-name", name);

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
        when(service.save(any())).thenThrow(new BusinessException(ARTIST_NOT_SAVED));

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
