import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  constructor(
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private router: Router,
    private tokenService: TokenService,

  ) { }

  ngOnInit(): void {
  }



  onlogout() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to log out?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.tokenService.destroy();
        this.router.navigate(['/login']);
      }
    });
  }

}
