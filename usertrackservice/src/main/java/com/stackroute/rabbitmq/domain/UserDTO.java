package com.stackroute.rabbitmq.domain;

import com.stackroute.usertrackservice.domain.Track;

import java.util.List;

public class UserDTO {

  private String userName;
  private String email;
  private String password;
  private List<TrackDTO> trackList;

  public List<TrackDTO> getTrackList() {
    return trackList;
  }

  public void setTrackList(List<TrackDTO> trackList) {
    this.trackList = trackList;
  }

  public UserDTO() {
  }

  public UserDTO(String userName, String email, String password) {
    this.userName = userName;
    this.email = email;
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "UserDTO{" +
      "userName='" + userName + '\'' +
      ", email='" + email + '\'' +
      ", password='" + password + '\'' +
      '}';
  }
}
