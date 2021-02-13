import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-txdetails',
  templateUrl: './txdetails.component.html',
  styleUrls: ['./txdetails.component.css'],
})
export class TxdetailsComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService,
    private _location: Location
  ) {}

  purchase: any;
  purchaseitems: any[];
  statuses: any[];
  status: any;

  ngOnInit(): void {
    this.purchase = {};
    this.purchaseitems = [];
    this.statuses = [
      { label: 'SAVED', value: 'SAVED' },
      { label: 'COMPLETED', value: 'COMPLETED' },
      { label: 'CANCELLED', value: 'CANCELLED' },
      { label: 'PAID', value: 'PAID' },
    ];

    this.activatedRoute.paramMap.subscribe((params) => {
      let id = params.get('transactionid');
      id != undefined && id != null ? this.getpurchase(id) : (id = null);
    });
  }

  getpurchase(id) {
    this.purchase = {};
    this.purchaseitems = [];
    this.service.getpurchase(id).subscribe(
      (res) => {
        this.purchase = res.purchasetx;
        this.purchaseitems = res.prodpertrans;
        this.status = this.statuses.find(
          (i) => i.value == res.purchasetx.transactionstatus
        );
      },
      (err) => {
        this.tokenService.checkSession(err);
        this.messageService.add({
          key: 'bc',
          severity: 'error',
          summary: 'Failed',
          detail: err.error,
        });
      }
    );
  }

  updatetx() {
    this.purchase.transactionstatus = this.status.value;
    this.service.updatetx(this.purchase).subscribe(
      (res) => {
        if (res.flag == 'success') {
          this.messageService.add({
            key: 'bc',
            severity: 'success',
            summary: 'Success',
            detail: res.event,
          });
          this.ngOnInit();
        } else {
          this.messageService.add({
            key: 'bc',
            severity: 'error',
            summary: 'Failed',
            detail: 'Update failed.',
          });
        }
      },
      (err) => {
        this.tokenService.checkSession(err);
        this.messageService.add({
          key: 'bc',
          severity: 'error',
          summary: 'Failed',
          detail: err.message,
        });
      }
    );
  }

  goback() {
    this._location.back();
  }
}
