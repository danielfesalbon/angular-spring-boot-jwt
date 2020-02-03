import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router } from '@angular/router';
import { HttpClientService } from 'src/app/services/http-client.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private http: HttpClient,
    private httpService: HttpClientService
  ) { }

  username: string;
  password: string;
  invalid: boolean = false;

  ngOnInit() {
  }

  login() {
    this.httpService.login(this.username, this.password).subscribe(res => {
      if (res.hasOwnProperty('jwt')) {
        sessionStorage.setItem('jwt', res['jwt']);
        sessionStorage.setItem('username', this.username);
        this.router.navigate(['/home']);
      }
    });
  }

  checkHeader() {

  }

}
