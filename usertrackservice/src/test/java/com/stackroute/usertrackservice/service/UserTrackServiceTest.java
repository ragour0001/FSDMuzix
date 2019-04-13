package com.stackroute.usertrackservice.service;

import com.stackroute.usertrackservice.domain.Artist;
import com.stackroute.usertrackservice.domain.Image;
import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.exception.TrackNotFoundException;
import com.stackroute.usertrackservice.repository.UserTrackRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserTrackServiceTest {

    @Mock
    private UserTrackRepository userTrackRepository;

    private User user;
    private Track track;
    private Artist artist;
    private List<Track> list;

    @InjectMocks
    UserTrackServiceImpl userTrackService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Image image = new Image(1, "http:url", "large");

        artist = new Artist(101, "John", "new url", image);

        track = new Track("Track1", "Mynewtrack", "new comments", "123", "new track url", artist);
        list = new ArrayList<>();
        list.add(track);
        user = new User("John123", "john@gmail.com", list);
    }

    @After
    public void tearDown() {
        userTrackRepository.deleteAll();
        list = null;
        artist = null;
        track = null;
        user = null;
    }

    @Test
    public void testSaveUserTrackSuccess() throws TrackAlreadyExistsException {
        user = new User("John156", "john@gmail.com", null);
        when(userTrackRepository.findByUserName(user.getUserName())).thenReturn(user);
        User fetchedUser = userTrackService.saveUserTrackToWishList(track, user.getUserName());
        Assert.assertEquals(fetchedUser, user);
        verify(userTrackRepository, timeout(1)).findByUserName(user.getUserName());
        verify(userTrackRepository,times(1)).save(user);
    }

    @Test
    public void testDeleteUserTrackFromWishList() throws TrackNotFoundException {
        when(userTrackRepository.findByUserName(user.getUserName())).thenReturn(user);
        User fetchedUser = userTrackService.deleteUserTrackFromWishList(user.getUserName(),track.getTrackId());
        Assert.assertEquals(fetchedUser,user);
        verify(userTrackRepository,times(1)).findByUserName(user.getUserName());
        verify(userTrackRepository,times(1)).save(user);
    }

    @Test
    public void testUpdateCommentForTrack() throws TrackNotFoundException {
        when(userTrackRepository.findByUserName(user.getUserName())).thenReturn(user);
        User fetchedUser = userTrackService.updateCommentForTrack("new updated comments",track.getTrackId(),user.getUserName());
        Assert.assertEquals(fetchedUser.getTrackList().get(0).getComments(), "new updated comments");
        verify(userTrackRepository,times(1)).findByUserName(user.getUserName());
        verify(userTrackRepository,times(1)).save(user);
    }

    @Test
    public void testGetAllUserTrackFromWishList() throws Exception {
        when(userTrackRepository.findByUserName(user.getUserName())).thenReturn(user);
        List<Track> fetchedList = userTrackService.getAllUserTrackFromWishList(user.getUserName());
        Assert.assertEquals(fetchedList, list);
        verify(userTrackRepository,times(1)).findByUserName(user.getUserName());
    }

}
