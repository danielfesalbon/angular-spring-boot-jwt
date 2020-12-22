import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private service: BackendService,
    private messageService: MessageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.onsign();
    this.credentials = {};
  }

  tosignin: boolean;
  toregister: boolean;
  credentials: any;


  onreg() {
    this.toregister = true;
    this.tosignin = false;
  }

  onsign() {
    this.toregister = false;
    this.tosignin = true;
  }


  usersignin() {
    this.service.userlogin(this.credentials.username, this.credentials.password).subscribe(res => {
      if (res.flag == 'Accepted') {
        localStorage.setItem('user', res.username);
        localStorage.setItem('token', res.token);
        this.router.navigate(['/main']);
      }
    }, err => {
      this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.error.message });
    });
  }

}
