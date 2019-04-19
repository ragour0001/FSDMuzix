import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
export const USER_NAME = "userName";
export const TOKEN_NAME = "jwt_token";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private springRegisterEndPoint: string;
  private springSaveEndPoint: string;

  constructor(private httpClient: HttpClient) { 

    this.springRegisterEndPoint = 
      "http://localhost:8086/api/v1/usertrackservice/";
    this.springSaveEndPoint = 
      "http://localhost:8085/api/v1/userservice/";  
  }

  registerUser(newUser) {
    const url = this.springRegisterEndPoint + "register";
    return this.httpClient.post(url, newUser, { observe: "response" });
  }

  saveUser(newUser) {
    const url = this.springSaveEndPoint + "save";
    return this.httpClient.post(url, newUser);
  }

  loginUser(newUser) {
    const url = this.springSaveEndPoint + "login";
    sessionStorage.setItem(USER_NAME, newUser.userName);
    return this.httpClient.post(url, newUser, { observe: "response" });
  }

  getToken() {
    return localStorage.getItem(TOKEN_NAME);
  }

  isTokenExpired(token?: string): boolean {
    if (localStorage.getItem(TOKEN_NAME)) {
      return true;
    } else {
      return false;
    }
  }

  logout() {
    sessionStorage.removeItem(USER_NAME);
    sessionStorage.clear();
    localStorage.removeItem(TOKEN_NAME);
    sessionStorage.clear();
    console.log("logged out!!!");
  }
}
