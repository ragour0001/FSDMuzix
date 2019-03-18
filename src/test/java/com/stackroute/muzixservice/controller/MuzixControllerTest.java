package com.stackroute.muzixservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.muzixservice.domain.Artist;
import com.stackroute.muzixservice.domain.Image;
import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exception.TrackAlreadyExistsException;
import com.stackroute.muzixservice.service.MuzixService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(MuzixController.class)
public class MuzixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MuzixService muzixService;

    private Image image;
    private Artist artist;
    private Track track;

    private List<Track> trackList;

    @Before
    public void setUp() throws Exception {

        trackList = new ArrayList<>();
        image = new Image(1, "http:url", "large");
        artist = new Artist(101, "John", "new url",image);
        track = new Track("Track1", "Mynewtrack","new comments","123","new trak url", artist);

        trackList.add(track);

        image = new Image(2, "http:url", "large");
        artist = new Artist(102, "Johnny", "new url",image);
        track = new Track("Track2", "Mynewtrack123","new comments Updated!!!","123","new trak url", artist);

        trackList.add(track);
    }

    @After
    public void tearDown() throws Exception {
        image = null;
        artist = null;
        track = null;
    }

    @Test
    public void testSaveTrackSuccess() throws Exception {
        when(muzixService.saveTrackToWishList(any())).thenReturn(track);
        mockMvc.perform(post("/api/v1/muzixservice/track")
                .contentType(MediaType.APPLICATION_JSON).
                        content(jsonToString(track)))
                .andExpect(status().isCreated())
                .andDo(print());
        verify(muzixService, times(1)).saveTrackToWishList(any());
    }

    @Test
    public void testSaveTrackFailure() throws Exception {
        when(muzixService.saveTrackToWishList(any())).thenThrow(TrackAlreadyExistsException.class);
        mockMvc.perform(post("/api/v1/muzixservice/track")
                .contentType(MediaType.APPLICATION_JSON).
                        content(jsonToString(track)))
                .andExpect(status().isConflict())
                .andDo(print());
        verify(muzixService, times(1)).saveTrackToWishList(any());
    }

    @Test
    public void testUpdateCommentSuccess() throws Exception {
        when(muzixService.updateCommentForTrack((track.getComments()), (track.getTrackId()))).thenReturn(track);
        mockMvc.perform(put("/api/v1/muzixservice/track/Track2")
                .contentType(MediaType.APPLICATION_JSON).
                        content(jsonToString(track)))
                .andExpect(status().isOk())
                .andDo(print());
        verify(muzixService, times(1)).updateCommentForTrack(track.getComments(),track.getTrackId());
    }

    @Test
    public void testDeleteTrack() throws Exception {
        when(muzixService.deleteTrackFromWishList(track.getTrackId())).thenReturn(true);
        mockMvc.perform(delete("/api/v1/muzixservice/track/Track2")
                .contentType(MediaType.APPLICATION_JSON).
                        content(jsonToString(track)))
                .andExpect(status().isOk())
                .andDo(print());
        verify(muzixService, times(1)).deleteTrackFromWishList(track.getTrackId());
    }

    @Test
    public void testGetAllTrackFromWishList() throws Exception {
        when(muzixService.getAllTrackFromWishList()).thenReturn(trackList);
        mockMvc.perform(get("/api/v1/muzixservice/tracks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(track)))
                .andExpect(status().isOk())
                .andDo(print());
        verify(muzixService, times(1)).getAllTrackFromWishList();
    }



    private static String jsonToString(final Object obj) throws JsonProcessingException {

        String result;

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(obj);
            result = jsonContent;
        } catch (JsonProcessingException e) {
            result = "JSON processing error!!!";
        }

        return result;
    }


}