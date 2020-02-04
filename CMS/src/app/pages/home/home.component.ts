import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private httpService: HttpClientService) { }

  ngOnInit() {
    setInterval(() => {
      //code goes here that will be run every 1 hr.  
      this.checkToken();
    }, 3600000);
  }

  checkToken() {
    //let time = (new Date(sessionStorage.getItem('expiration')).getTime() - new Date().getTime());
    let t = new Date(sessionStorage.getItem('expiration')).valueOf() - new Date().valueOf();
    let time = t / 3600000;
    console.log(new Date(time).getMinutes());
    console.log(sessionStorage.getItem('jwt'));
    if (time < 1) {
      console.log('token expiring in 1hr..')
      //if expiring within 1 hr
      if (sessionStorage.getItem('jwt') != null && sessionStorage.getItem('jwt') != undefined) {
        console.log("refreshing token");
        this.httpService.refreshToken().subscribe(res => {
          console.log(res);
          if (res.hasOwnProperty('jwt')) {
            sessionStorage.setItem('jwt', res['jwt']);
            sessionStorage.setItem('expiration', res['expiration'].toString());
          }
        });
      }
    }
  }

}
