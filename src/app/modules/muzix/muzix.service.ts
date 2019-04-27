import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Track } from './track';
import { USER_NAME } from '../authentication/authentication.service';
import { HttpHeaders } from '@angular/common/http';
//import { HttpHeaders } from '@angular/common/http/src/headers';

@Injectable({
  providedIn: 'root'
})
export class MuzixService {

  thirdPartyApi: string;
  apiKey: string;
  springEndPoint: string;
  userName: string;

  constructor(private httpClient: HttpClient) { 

    this.thirdPartyApi = 'http://ws.audioscrobbler.com/2.0?method=geo.gettoptracks&country=';
    this.apiKey = '&api_key=af4e0fd51216e7a0b662da8e95580206&format=json';
    //this.springEndPoint = 'http://localhost:8084/api/v1/muzixservice/';
    // this.springEndPoint = 'http://localhost:8086/api/v1/usertrackservice/'

    this.springEndPoint = "http://localhost:8087/usertrackservice/api/v1/usertrackservice/";
  }

  getTrackDetails(country: string): Observable<any> {
    const url = `${this.thirdPartyApi}${country}${this.apiKey}`;

    return this.httpClient.get(url);
  }

  addTrackToWishList(track: Track) {
    this.userName = sessionStorage.getItem(USER_NAME);
    const url = this.springEndPoint + "user/" + this.userName + "/track";
    //console.log("new url" +url);
    return this.httpClient.post(url, track, {
      observe: "response"
    });
  }

  getAllTracksforWishList(): Observable<Track[]> {
    //const url = this.springEndPoint + "tracks";
    this.userName = sessionStorage.getItem(USER_NAME);
    const url = this.springEndPoint + "user/" + this.userName + "/tracks";
    return this.httpClient.get<Track[]>(url);
  }

  deleteTrackFromWishList(track: Track) {
   // const url = this.springEndPoint + "track/" + `${track.trackId}`;
   this.userName = sessionStorage.getItem(USER_NAME);
   const url = this.springEndPoint + "user/" + this.userName + "/" + track.trackId;
  //  const options = {
  //    headers: new HttpHeaders({
  //      'Content-Type': 'application/json',
  //    }),
  //    body: track
  //  };
   console.log("In delete :", track);
  //  return this.httpClient.delete(url, options);
   return this.httpClient.delete(url);
    //return this.httpClient.delete(url, { responseType: "text"});
  }

  updateComments(track) {
    this.userName = sessionStorage.getItem(USER_NAME);
    const url = this.springEndPoint + "user/" + this.userName + "/track";
    return this.httpClient.patch(url, track, { observe: "response"});
   // const id = track.trackId;
   // const url = this.springEndPoint + "track/" + `${id}`;
  //return this.httpClient.put(url, track, { observe: "response"});
  }

  filterArtists(tracks: Array<Track>, artistName: string) {
    const results = tracks.filter(track => {
      return track.artist.name.match(artistName);
    });
    console.log("Filtered data :", results);
    return results;
  }
}
