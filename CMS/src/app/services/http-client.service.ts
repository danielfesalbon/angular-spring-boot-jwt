import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
//import { User } from '../model/user';


/*export class User{
  constructor(
    public username:string,
    public password:string,
    public status:string,
  ) {}
  
}*/

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    return this.http.post('http://localhost:8080/security/authenticate', { 'username': username, 'password': password });
  }

  refreshToken() {
    console.log("refreshing token")
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + sessionStorage.getItem('jwt')
    });
    let options = { headers: headers };
    return this.http.post('http://localhost:8080/security/tokenrefresh', sessionStorage.getItem('jwt'), options);
  }

}
