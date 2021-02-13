import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.css'],
})
export class PurchaseComponent implements OnInit {
  constructor(
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) {}

  records: any[];
  purchase: any[];
  productid: any;
  product: any;
  purchasetx: any;
  total: any;
  row: any;
  options: any[];

  ngOnInit(): void {
    this.purchasetx = {};
    this.purchasetx.transactionvalue = 0;
    this.purchasetx.transactionchange = 0;
    this.productid = '';
    this.product = {};
    this.purchase = [];
    this.getpages(10);
  }

  paginate(event) {
    this.getpurchases(event.rows, event.page);
    //event.first = Index of the first record
    //event.rows = Number of rows to display in new page
    //event.page = Index of the new page
    //event.pageCount = Total number of pages
  }

  searchproduct() {
    this.service.searchproduct(this.productid).subscribe(
      (res) => {
        this.product = res;
      },
      (err) => {
        this.tokenService.checkSession(err);
        this.product = {};
        this.messageService.add({
          key: 'bc',
          severity: 'error',
          summary: 'Failed',
          detail: err.error,
        });
      }
    );
  }

  getpages(row) {
    this.service.getpurchasepage(row).subscribe(
      (res) => {
        this.total = res.count;
        this.row = res.row;
        this.options = res.rowoptions;
        this.getpurchases(this.row, 0);
      },
      (err) => {
        this.tokenService.checkSession(err);
      }
    );
  }

  addtopurchase() {
    this.purchase.push(this.product);
    this.product = {};
    this.productid = '';
    let total = 0;
    this.purchase.forEach((p) => {
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
    this.purchase = this.purchase.filter((res) => res != data);
    let total = 0;
    this.purchase.forEach((p) => {
      total = total + p.amount;
    });
    this.purchasetx.transactionvalue = total;
  }

  computechange() {
    if (
      this.purchasetx.transactionvalue != null &&
      this.purchasetx.transactionpayment != null
    ) {
      this.purchasetx.transactionchange =
        +this.purchasetx.transactionpayment - +this.purchasetx.transactionvalue;
    }
  }

  confirmpurchase() {
    this.confirmationService.confirm({
      message: 'Save new purchase supply.',
      accept: () => {
        this.submitpurchase();
      },
    });
  }

  submitpurchase() {
    let param: any = {};
    this.purchasetx.username = this.tokenService.getUser();
    param.prodpertrans = this.purchase;
    param.purchasetx = this.purchasetx;
    this.service.submitpurchase(param).subscribe(
      (res) => {
        if (res.flag == 'success') {
          this.ngOnInit();
          this.messageService.add({
            key: 'bc',
            severity: 'success',
            summary: 'Success',
            detail: res.event,
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

  viewpurchase(data) {
    this.router.navigate(['/main/purchase/' + data.transactionid]);
  }

  getpurchases(row, page) {
    this.service.getpurchases(row, page).subscribe(
      (res) => {
        this.records = res;
        this.records.forEach((r) => {
          let datetime: Date = new Date(r.transactiondate);
          datetime.setHours(
            r.transactiontime.split(':')[0],
            r.transactiontime.split(':')[1],
            r.transactiontime.split(':')[2]
          );
          r.transactiondate = datetime;
        });
      },
      (err) => {
        this.tokenService.checkSession(err);
      }
    );
  }
}
