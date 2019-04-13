package com.stackroute.usertrackservice.service;

import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.exception.TrackNotFoundException;
import com.stackroute.usertrackservice.exception.UserAlreadyExistsException;

import java.util.List;

public interface UserTrackService {

    User saveUserTrackToWishList(Track track , String userName) throws TrackAlreadyExistsException;

    User deleteUserTrackFromWishList(String userName , String trackId) throws TrackNotFoundException;

    User updateCommentForTrack(String comments , String trackId , String userName) throws TrackNotFoundException;

    List<Track> getAllUserTrackFromWishList(String userName) throws Exception;

    User registerUser(User user) throws UserAlreadyExistsException;
}
