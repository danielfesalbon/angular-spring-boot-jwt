import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BackendService {


  servicelink = environment.rest_url;

  constructor(private http: HttpClient) { }


  userlogin(username, password) {
    return this.http.post<any>(this.servicelink + '/authenticate/user', { username: username, password: password });
  }

}
