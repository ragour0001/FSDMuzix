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
  private springLoginEndPoint: string;

  constructor(private httpClient: HttpClient) { 

    // this.springRegisterEndPoint = 
    //   "http://localhost:8086/api/v1/usertrackservice/";
    
    // this.springSaveEndPoint = 
    //   "http://localhost:8085/api/v1/userservice/";  

   // this.springRegisterEndPoint = "http://localhost:8087/orchestrationservice/api/v1/user";
      this.springRegisterEndPoint = "http://localhost:8087/usertrackservice/api/v1/usertrackservice/register";    
      this.springLoginEndPoint = "http://localhost:8087/authenticationservice/api/v1/userservice/login";

  }

  registerUser(newUser) {
    const url = this.springRegisterEndPoint;
    return this.httpClient.post(url, newUser, { observe: "response" });
  }

  // saveUser(newUser) {
  //   const url = this.springSaveEndPoint + "save";
  //   return this.httpClient.post(url, newUser);
  // }

  loginUser(newUser) {
    const url = this.springLoginEndPoint;
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
