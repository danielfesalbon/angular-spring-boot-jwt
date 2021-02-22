import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService, MessageService, SelectItem } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css'],
})
export class TransactionComponent implements OnInit {
  products: any[];
  sortOptions: SelectItem[];
  sortOrder: number;
  sortField: string;
  cartmodal: boolean;
  order: any[];
  transaction: any;
  statuses: any[];
  status: any;

  constructor(
    private activatedRoute: ActivatedRoute,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }

  ngOnInit(): void {
    this.transaction = {};
    this.transaction.transactionvalue = 0;
    this.transaction.transactionpayment = 0;
    this.transaction.transactionchange = 0;
    this.cartmodal = false;
    this.sortOptions = [
      { label: 'Price High to Low', value: '!declaredprice' },
      { label: 'Price Low to High', value: 'declaredprice' },
      { label: 'Stock High to Low', value: '!stock' },
      { label: 'Stock Low to High', value: 'stock' },
    ];

    this.statuses = [
      { label: 'SAVED', value: 'SAVED' },
      { label: 'COMPLETED', value: 'COMPLETED' },
      { label: 'CANCELLED', value: 'CANCELLED' },
      { label: 'PAID', value: 'PAID' },
    ];

    this.status = { label: 'COMPLETED', value: 'COMPLETED' };
    this.order = [];
    this.getallproducts();
  }

  getallproducts() {
    this.service.getproductfortx().subscribe(
      (res) => {
        this.products = res;
        console.log(this.products);
      },
      (err) => {
        this.tokenService.checkSession(err);
      }
    );
  }

  onSortChange(event) {
    let value = event.value;

    if (value.indexOf('!') === 0) {
      this.sortOrder = -1;
      this.sortField = value.substring(1, value.length);
    } else {
      this.sortOrder = 1;
      this.sortField = value;
    }
  }

  viewcart() {
    this.cartmodal = true;
  }

  addquantity(data) {
    data.quantity = +data.quantity;
    if (data.quantity + 1 <= data.stock) {
      data.quantity = data.quantity + 1;
    }
  }

  subtractquantity(data) {
    data.quantity = +data.quantity;
    if (data.quantity - 1 > 0) {
      data.quantity = data.quantity - 1;
    }
  }

  addtocart(data) {
    let prod: any = {};
    if (this.order.length != 0) {
      prod = this.order.find((i) => {
        return i.productid == data.productid;
      });
    } else {
      prod = null;
    }
    if (prod != undefined && prod != null) {
      let quantity = +this.order.find((i) => i.productid == data.productid)
        .quantity;
      quantity = +quantity + +data.quantity;
      if (+quantity <= +data.stock) {
        this.order.find(
          (i) => i.productid == data.productid
        ).quantity = quantity;
        this.order.find((i) => i.productid == data.productid).amount =
          +quantity * +data.declaredprice;
        this.messageService.add({
          key: 'bc',
          severity: 'success',
          summary: 'Item added',
          detail: data.quantity + ' ' + data.productname + ' was added.',
        });
      } else {
        this.messageService.add({
          key: 'bc',
          severity: 'error',
          summary: 'Invalid routine',
          detail: "Stock and quantity doesn't match",
        });
        //insert toast invalid routine -> stock : quantity validation
      }
    } else {
      prod = {};
      prod.productid = data.productid;
      prod.quantity = data.quantity;
      prod.amount = +data.quantity * +data.declaredprice;
      if (+data.quantity <= +data.stock) {
        this.order.push(prod);
        this.messageService.add({
          key: 'bc',
          severity: 'success',
          summary: 'Item added',
          detail: data.quantity + ' ' + data.productname + ' was added.',
        });
      } else {
        this.messageService.add({
          key: 'bc',
          severity: 'error',
          summary: 'Invalid routine',
          detail: "Stock and quantity doesn't match",
        });
        //insert toast invalid routine -> stock : quantity validation
      }
    }
    data.quantity = 1;
    this.transaction.transactionvalue = 0;
    this.order.forEach((i) => {
      this.transaction.transactionvalue =
        this.transaction.transactionvalue + +i.amount;
    });
  }

  confirmtx() {
    this.confirmationService.confirm({
      message: 'Submit new transaction.',
      accept: () => {
        this.submittx();
      },
    });
  }

  submittx() {
    let param: any = {};
    this.transaction.username = this.tokenService.getUser();
    param.prodpertrans = this.order;
    this.transaction.transactionstatus = this.status.value;
    param.purchasetx = this.transaction;
    this.service.submittransaction(param).subscribe(
      (res) => {
        if (res.flag == 'success') {
          this.service.generatereceipt(res.id);
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

  computechange() {
    this.transaction.transactionpayment = +this.transaction.transactionpayment;
    this.transaction.transactionchange =
      +this.transaction.transactionpayment - +this.transaction.transactionvalue;
  }

  clear() {
    this.order = [];
    this.transaction = {};
    this.transaction.transactionvalue = 0;
    this.transaction.transactionpayment = 0;
    this.transaction.transactionchange = 0;
    this.status = { label: 'COMPLETED', value: 'COMPLETED' };
  }
}
