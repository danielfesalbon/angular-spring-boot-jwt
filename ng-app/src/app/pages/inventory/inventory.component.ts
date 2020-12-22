import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  constructor() { }


  products: any[];

  ngOnInit(): void {
    this.products = [
      { productid: '123', productname: 'PRODUCT 1', declaredprice: 100, stock: 10 },
      { productid: '123', productname: 'PRODUCT 1', declaredprice: 100, stock: 10 },
      { productid: '123', productname: 'PRODUCT 1', declaredprice: 100, stock: 10 },
      { productid: '123', productname: 'PRODUCT 1', declaredprice: 100, stock: 10 },
      { productid: '123', productname: 'PRODUCT 1', declaredprice: 100, stock: 10 },
      { productid: '123', productname: 'PRODUCT 1', declaredprice: 100, stock: 10 },
      { productid: '123', productname: 'PRODUCT 1', declaredprice: 100, stock: 10 },
    ]
  }

}
