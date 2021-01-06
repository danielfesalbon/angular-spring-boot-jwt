import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css']
})
export class SalesComponent implements OnInit {

  constructor(
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }

  rangeDates: Date[];
  records: any[];
  salesmodal: boolean;
  total: any;
  row: any;
  options: any[];

  ngOnInit(): void {
    this.salesmodal = false;
    this.getpages(10);
  }

  getpages(row) {
    this.service.gettxpage(row).subscribe(res => {
      this.total = res.count;
      this.row = res.row;
      this.options = res.rowoptions;
      this.gettransactions(this.row, 0);
      console.log(res);
    }, err => {
      console.log(err);
    });
  }


  viewtx(data) {
    this.router.navigate(["/main/purchase/" + data.transactionid]);
  }

  gettransactions(row, page) {
    this.service.gettransactions(row, page).subscribe(res => {
      this.records = res;
      this.records.forEach(r => {
        let datetime: Date = new Date(r.transactiondate);
        datetime.setHours(r.transactiontime.split(':')[0], r.transactiontime.split(':')[1], r.transactiontime.split(':')[2]);
        r.transactiondate = datetime;
      });
    }, err => { });
  }

  paginate(event) {
    this.gettransactions(event.rows, event.page);
    //event.first = Index of the first record
    //event.rows = Number of rows to display in new page
    //event.page = Index of the new page
    //event.pageCount = Total number of pages
  }


}
