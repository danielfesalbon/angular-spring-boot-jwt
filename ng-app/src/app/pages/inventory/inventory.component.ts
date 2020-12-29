import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  constructor(
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router
  ) { }


  products: any[];
  productmodal: boolean;
  product: any;
  onview: boolean;

  ngOnInit(): void {
    this.product = {};
    this.productmodal = false;
    this.onview = false;
    this.getallproduct();
  }


  getallproduct() {
    this.service.getproducts().subscribe(res => {
      this.products = res;
    }, err => {

    });
  }


  newproduct() {
    this.onview = false;
    this.product = {};
    this.product.active = false;
    this.product.stock = 0;
    this.product.lastmax = 0;
    this.productmodal = true;
  }


  saveproduct() {
    this.confirmationService.confirm({
      message: 'Save product details.',
      accept: () => {
        this.product.declaredprice = +this.product.declaredprice;
        this.service.saveproduct(this.product).subscribe(res => {
          if (res.flag == "success") {
            this.getallproduct();
            this.productmodal = false;
            this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event });
          }
        }, err => {
          this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.message });
        });
      }
    });
  }

  deleteproduct(id) {
    this.confirmationService.confirm({
      message: 'Delete product.',
      accept: () => {
        this.product.declaredprice = +this.product.declaredprice;
        this.service.deleteproduct(id).subscribe(res => {
          if (res.flag == "success") {
            this.getallproduct();
            this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event });
          }
        }, err => {
          this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.message });
        });
      }
    });
  }


  viewproduct(data) {
    this.onview = true;
    this.product = data;
    this.productmodal = true;
  }

}
