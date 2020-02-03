import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { HttpClientService } from './http-client.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient, private httpService: HttpClientService) { }

  authenticate(username, password) {
    this.httpService.login(username, password).subscribe(res => {
      console.log(res);
      if (res.hasOwnProperty('jwt')) {
        console.log("asdsad")
        sessionStorage.setItem('jwt', res['jwt']);
        sessionStorage.setItem('username', username);
        return true;
      } else {
        return false;
      }
    });
  }

  isloggedin() {
    let user = sessionStorage.getItem('username');
    return !(user === null);
  }

  logout() {
    sessionStorage.clear();
  }

}

export class User {
  username: string;
  password: string;
}
