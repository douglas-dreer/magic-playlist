package br.com.ddreer.magicplaylistapi.controller;

import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import br.com.ddreer.magicplaylistapi.service.ArtistServiceImpl;
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
public class ArtistControllerTest {
    private final static String ENDPOINT = "/artist";
    private final static String NOT_SAVED = "Unable to save artist";
    private final static String NOT_DELETED = "Unable to delete artist";
    private static List<ArtistDTO> artistList = new ArrayList<>();
    private static ArtistDTO artist = new ArtistDTO();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArtistServiceImpl service;

    @BeforeAll
    public static void setup() {
        artist.setId(UUID.randomUUID());
        artist.setArtisticName("JhanZão");
        artist.setRealName("João da Silva");
        artist.setNationality(CityEnum.SAO);
        artist.setDebutYear(1999);
        artist.setActive(true);

        artistList = Collections.singletonList(artist);
    }

    @Test
    void mustReturnListartistDTOSuccessWhenList() throws Exception {
        MockHttpServletRequestBuilder getMethod = get(ENDPOINT);
        when(service.list()).thenReturn(artistList);
        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    void mustReturnArtistDTOSuccessWhenFindById() throws Exception {
        UUID id = artist.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.findById(any())).thenReturn(artist);

        MockHttpServletRequestBuilder getMethod = get(uri);

        mockMvc.perform(getMethod)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void mustReturnListartistDTOSuccessWhenFindByRealName() throws Exception {
        String name = artist.getRealName();

        when(service.findByRealName(anyString())).thenReturn(artistList);

        MockHttpServletRequestBuilder getMethod = get(ENDPOINT)
                .queryParam("real-name", name);

        mockMvc.perform(getMethod)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void mustReturnListartistDTOSuccessWhenFindByArtistName() throws Exception {
        String name = artist.getArtisticName();

        when(service.findByArtisticName(anyString())).thenReturn(artistList);

        MockHttpServletRequestBuilder getMethod = get(ENDPOINT)
                .queryParam("artist-name", name);

        mockMvc.perform(getMethod)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void mustReturnArtistDTOSuccessWhenSave() throws Exception {
        when(service.save(any())).thenReturn(artist);

        MockHttpServletRequestBuilder postMethod = post(ENDPOINT)
                .content(artist.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void mustReturnArtistDTOBusinessExceptionWhenSave() throws Exception {
        when(service.save(any())).thenThrow(new BusinessException(NOT_SAVED));

        MockHttpServletRequestBuilder postMethod = post(ENDPOINT)
                .content(artist.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    void mustReturnArtistDTOSuccessWhenEdit() throws Exception {
        UUID id = artist.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.edit(any())).thenReturn(artist);

        MockHttpServletRequestBuilder putMethod = put(uri)
                .content(artist.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(putMethod)
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void mustReturnArtistDTOBadRequestExceptionWhenEdit() throws Exception {
        UUID id = UUID.randomUUID();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.edit(any())).thenReturn(artist);

        MockHttpServletRequestBuilder putMethod = put(uri)
                .content(artist.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(putMethod)
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void mustReturnArtistDTOBusinessExceptionWhenEdit() throws Exception {
        UUID id = artist.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.edit(any())).thenThrow(new BusinessException(NOT_SAVED));

        MockHttpServletRequestBuilder putMethod = put(uri)
                .content(artist.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(putMethod)
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }

    @Test
    void mustReturnArtistDTOSuccessWhenDelete() throws Exception {
        UUID id = artist.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.delete(any())).thenReturn(true);

        MockHttpServletRequestBuilder deleteMethod = delete(uri)
                .content(artist.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(deleteMethod)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void mustReturnArtistDTOBusinessExceptionWhenDelete() throws Exception {
        UUID id = artist.getId();
        String uri = String.format("%s/%s", ENDPOINT, id);

        when(service.delete(any())).thenThrow(new BusinessException(NOT_DELETED));

        MockHttpServletRequestBuilder deleteMethod = delete(uri)
                .content(artist.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(deleteMethod)
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }

}
