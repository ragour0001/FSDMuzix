package com.stackroute.usertrackservice.controller;

import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.exception.TrackNotFoundException;
import com.stackroute.usertrackservice.exception.UserAlreadyExistsException;
import com.stackroute.usertrackservice.service.UserTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/usertrackservice")
public class UserTrackController {

    private UserTrackService userTrackService;
    private ResponseEntity responseEntity;

    public UserTrackController() {
    }

    @Autowired
    public UserTrackController(UserTrackService userTrackService) {
        this.userTrackService = userTrackService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody User user) throws UserAlreadyExistsException {

        try {
            userTrackService.registerUser(user);
            responseEntity = new ResponseEntity<User>(user , HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException();
        }

        return responseEntity;
    }

    @PostMapping("/user/{userName}/track")
    public ResponseEntity<?> saveUserTrackToWishList(@RequestBody Track track , @PathVariable("userName") String userName) throws TrackAlreadyExistsException {
        try {
            User user = userTrackService.saveUserTrackToWishList(track , userName);
            responseEntity = new ResponseEntity(user , HttpStatus.CREATED);
        } catch (TrackAlreadyExistsException e) {
            throw new TrackAlreadyExistsException();
        }
        catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @DeleteMapping("user/{userName}/{trackId}")
    public ResponseEntity<?> deleteUserTrackFromWishList(@PathVariable ("userName") String userName , @PathVariable ("trackId") String trackId) throws TrackNotFoundException {

        try {
            User user = userTrackService.deleteUserTrackFromWishList(userName , trackId);
            responseEntity = new ResponseEntity(user , HttpStatus.OK);
        } catch (TrackNotFoundException e) {
            throw new TrackNotFoundException();
        } catch (Exception e) {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @PatchMapping("user/{userName}/track")
    public ResponseEntity<?> updateCommentForUserTrack( @RequestBody Track track , @PathVariable ("userName") String userName) throws TrackNotFoundException {

        try {
            userTrackService.updateCommentForTrack(track.getComments() , track.getTrackId() , userName);
            responseEntity = new ResponseEntity(track , HttpStatus.OK);
        } catch (TrackNotFoundException e) {
            throw new TrackNotFoundException();
        } catch (Exception e) {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @GetMapping("user/{userName}/tracks")
    public ResponseEntity<?> getAllUserTrackFromWishList(@PathVariable ("userName") String userName) {

        try {
            responseEntity = new ResponseEntity(userTrackService.getAllUserTrackFromWishList(userName), HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}
