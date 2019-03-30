import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Track } from './track';

@Injectable({
  providedIn: 'root'
})
export class MuzixService {

  thirdPartyApi: string;
  apiKey: string;
  springEndPoint: string;

  constructor(private httpClient: HttpClient) { 

    this.thirdPartyApi = 'http://ws.audioscrobbler.com/2.0?method=geo.gettoptracks&country=';
    this.apiKey = '&api_key=af4e0fd51216e7a0b662da8e95580206&format=json';
    this.springEndPoint = 'http://localhost:8084/api/v1/muzixservice/';
  }

  getTrackDetails(country: string): Observable<any> {
    const url = `${this.thirdPartyApi}${country}${this.apiKey}`;

    return this.httpClient.get(url);
  }

  addTrackToWishList(track: Track) {
    const url = this.springEndPoint + "track";
    return this.httpClient.post(url, track, {
      observe: "response"
    });
  }

  getAllTracksforWishList(): Observable<Track[]> {
    const url = this.springEndPoint + "tracks";
    return this.httpClient.get<Track[]>(url);
  }

  deleteTrackFromWishList(track: Track) {
    const url = this.springEndPoint + "track/" + `${track.trackId}`;
    return this.httpClient.delete(url, { responseType: "text"});
  }

  updateComments(track) {
    const id = track.trackId;
    const url = this.springEndPoint + "track/" + `${id}`;
    return this.httpClient.put(url, track, { observe: "response"});
  }
}
