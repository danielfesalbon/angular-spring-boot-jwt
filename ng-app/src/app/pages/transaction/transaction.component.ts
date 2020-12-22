import { Component, OnInit } from '@angular/core';
import { SelectItem } from 'primeng/api';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {


  products: any[];
  sortOptions: SelectItem[];
  sortOrder: number;
  sortField: string;
  cartmodal: boolean;
  order: any[];

  constructor() { }

  ngOnInit(): void {
    this.cartmodal = false;
    this.sortOptions = [
      { label: 'Price High to Low', value: '!price' },
      { label: 'Price Low to High', value: 'price' }
    ];

    this.order = [
      { productid: 'name1', quantity: 5 },
      { productid: 'name1', quantity: 5 },
      { productid: 'name1', quantity: 5 },
      { productid: 'name1', quantity: 5 },
      { productid: 'name1', quantity: 5 },
      { productid: 'name1', quantity: 5 },
      { productid: 'name1', quantity: 5 },
      { productid: 'name1', quantity: 5 },
    ];

    this.products = [
      { name: 'name1', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name2', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name3', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name4', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name5', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name6', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name7', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name8', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name9', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name9', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name9', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name9', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name9', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name9', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name9', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
      { name: 'name9', description: 'description', category: 'category', inventoryStatus: 'LOWSTOCK', price: 123, rating: 1 },
    ]


  }


  onSortChange(event) {
    let value = event.value;

    if (value.indexOf('!') === 0) {
      this.sortOrder = -1;
      this.sortField = value.substring(1, value.length);
    }
    else {
      this.sortOrder = 1;
      this.sortField = value;
    }
  }


  viewcart() {
    this.cartmodal = true;
  }

}
