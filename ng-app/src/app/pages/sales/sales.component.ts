import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css']
})
export class SalesComponent implements OnInit {

  constructor() { }

  rangeDates: Date[];
  records: any[];
  salesmodal: boolean;

  ngOnInit(): void {
    this.salesmodal = false;
    this.records = [
      { transactionid: "TX-001", amount: "200.00", txdate: new Date(), user: "User" },
      { transactionid: "TX-002", amount: "200.00", txdate: new Date(), user: "User" },
      { transactionid: "TX-003", amount: "200.00", txdate: new Date(), user: "User" },
      { transactionid: "TX-004", amount: "200.00", txdate: new Date(), user: "User" },
      { transactionid: "TX-005", amount: "200.00", txdate: new Date(), user: "User" },
      { transactionid: "TX-006", amount: "200.00", txdate: new Date(), user: "User" },
      { transactionid: "TX-007", amount: "200.00", txdate: new Date(), user: "User" },
    ];
  }


  viewtx() {
    this.salesmodal = true;
  }

}
