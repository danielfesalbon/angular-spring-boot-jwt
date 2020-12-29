import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-txdetails',
  templateUrl: './txdetails.component.html',
  styleUrls: ['./txdetails.component.css']
})
export class TxdetailsComponent implements OnInit {

  constructor(
    private activatedRoute: ActivatedRoute,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }


  purchase: any;
  purchaseitems: any[];

  ngOnInit(): void {
    this.purchase = {};
    this.purchaseitems = [];
    this.activatedRoute.paramMap.subscribe(params => {
      let id = params.get('transactionid');
      id != undefined && id != null ? this.getpurchase(id) : id = null;
    });
  }



  getpurchase(id) {
    this.purchase = {};
    this.purchaseitems = [];
    this.service.getpurchase(id).subscribe(res => {
      console.log(res);
      this.purchase = res.purchasetx;
      this.purchaseitems = res.prodpertrans;
    }, err => {
      this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.error });
    });
  }

}
