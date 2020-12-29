import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.css']
})
export class PurchaseComponent implements OnInit {

  constructor(
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }

  records: any[];
  purchase: any[];
  productid: any;
  product: any;
  purchasetx: any;

  ngOnInit(): void {
    this.service.getpurchases().subscribe(res => {
      this.records = res;
    }, err => {
      console.log(err);
    });
    this.purchasetx = {};
    this.purchasetx.transactionvalue = 0;
    this.purchasetx.transactionchange = 0;
    this.productid = '';
    this.product = {};
    this.purchase = [];
  }


  searchproduct() {
    this.service.searchproduct(this.productid).subscribe(res => {
      this.product = res;
    }, err => {
      this.product = {};
      this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.error });
    });
  }


  addtopurchase() {
    this.purchase.push(this.product);
    this.product = {};
    this.productid = '';
    let total = 0;
    this.purchase.forEach(p => {
      total = total + p.amount;
    });
    this.purchasetx.transactionvalue = total;
  }

  computetotal() {
    if (this.product.price != null && this.product.quantity != null) {
      this.product.amount = +this.product.price * +this.product.quantity;
    }
  }

  removeproduct(data) {
    this.purchase = this.purchase.filter(res => res != data);
    let total = 0;
    this.purchase.forEach(p => {
      total = total + p.amount;
    });
    this.purchasetx.transactionvalue = total;
  }

  computechange() {
    if (this.purchasetx.transactionvalue != null && this.purchasetx.transactionpayment != null) {
      this.purchasetx.transactionchange = (+this.purchasetx.transactionpayment) - (+this.purchasetx.transactionvalue);
    }
  }


  confirmpurchase() {
    this.confirmationService.confirm({
      message: "Save new purchase supply.",
      accept: () => {
        this.submitpurchase();
      }
    });
  }


  submitpurchase() {
    let param: any = {};
    this.purchasetx.username = this.tokenService.getUser();
    param.prodpertrans = this.purchase;
    param.purchasetx = this.purchasetx;
    this.service.submitpurchase(param).subscribe(res => {
      if (res.flag == "success") {
        this.ngOnInit();
        this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event });
      }
    }, err => {
      this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.message });
    });
  }


  viewpurchase(data) {
    this.router.navigate(["/main/purchase/" + data.transactionid]);
  }

}
