package com.stackroute.usertrackservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.usertrackservice.domain.Artist;
import com.stackroute.usertrackservice.domain.Image;
import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.service.UserTrackService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserTrackController.class)
public class UserTrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserTrackService userTrackService;

    private Artist artist;
    private Image image;
    private Track track;
    private User user;
    private List<Track> trackList;

    @Before
    public void setUp() {

        trackList = new ArrayList<>();
        image = new Image(1, "http:url", "large");
        artist = new Artist(101, "John", "new url", image);
        track = new Track("Track1", "Mynewtrack", "new comments", "123", "new track url", artist);

        trackList.add(track);

        image = new Image(2, "http:url", "large");
        artist = new Artist(102, "Johnny", "new url", image);
        track = new Track("Track2", "Mynewtrack123", "new comments updated", "123", "new track url", artist);
        trackList.add(track);

        user = new User("John", "john@gmail.com", trackList);
    }

    @After
    public void tearDown() {

        image = null;
        artist = null;
        track = null;
        user = null;

    }

    //any() is Mockito Argument Matchers - any()
    @Test
    public void testSaveTrackSuccess() throws Exception {

        when(userTrackService.saveUserTrackToWishList(any(), eq(user.getUserName()))).thenReturn(user);

        mockMvc.perform(post("/api/v1/usertrackservice/user/{userName}/track", user.getUserName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(track)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(userTrackService, times(1)).saveUserTrackToWishList(any(), eq(user.getUserName()));

    }

    @Test
    public void testSaveTrackFailure() throws Exception {

        when(userTrackService.saveUserTrackToWishList(any(), eq(user.getUserName()))).thenThrow(TrackAlreadyExistsException.class);

        mockMvc.perform(post("/api/v1/usertrackservice/user/{userName}/track", user.getUserName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(track)))
                .andExpect(status().isConflict())
                .andDo(print());

        verify(userTrackService, times(1)).saveUserTrackToWishList(any(), eq(user.getUserName()));

    }

    @Test
    public void testUpdateCommentSuccess() throws Exception {

        when(userTrackService.updateCommentForTrack(track.getComments(), track.getTrackId(), user.getUserName())).thenReturn(user);
        mockMvc.perform(patch("/api/v1/usertrackservice/user/{userName}/track", user.getUserName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(track)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userTrackService, times(1)).updateCommentForTrack(track.getComments(),track.getTrackId(),user.getUserName());

    }

    @Test
    public void testDeleteTrack() throws Exception {

        when(userTrackService.deleteUserTrackFromWishList(user.getUserName(), track.getTrackId())).thenReturn(user);
        mockMvc.perform(delete("/api/v1/usertrackservice/user/{userName}/track", user.getUserName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(track)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userTrackService, times(1)).deleteUserTrackFromWishList(user.getUserName(),track.getTrackId());

    }

    @Test
    public void testGetAllTrackFromWishList() throws Exception {

        when(userTrackService.getAllUserTrackFromWishList(user.getUserName())).thenReturn(trackList);
        mockMvc.perform(get("/api/v1/usertrackservice/user/{userName}/tracks", user.getUserName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(track)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userTrackService, times(1)).getAllUserTrackFromWishList(user.getUserName());

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
