import { Injectable } from '@angular/core';
//import { CanActivate } from '@angular/router';
import { AuthenticationService } from '../authentication/authentication.service';
import { Router} from '@angular/router';
import { CanActivate } from '@angular/router/src/interfaces';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(
    private authService: AuthenticationService,
    private route: Router
  ) { }

  canActivate() {
    if (this.authService.isTokenExpired()) {
      console.log('In ACanActivate');
      return true;
    }
    this.route.navigate(['/login']);
    return false;
  }
}
