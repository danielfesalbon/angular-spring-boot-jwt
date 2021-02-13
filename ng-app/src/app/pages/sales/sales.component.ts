import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-sales',
  templateUrl: './sales.component.html',
  styleUrls: ['./sales.component.css'],
})
export class SalesComponent implements OnInit {
  constructor(
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) {}

  rangeDates: Date[];
  records: any[];
  salesmodal: boolean;
  total: any;
  row: any;
  page: any;
  options: any[];

  statuses: any[];
  status: any;

  ngOnInit(): void {
    this.salesmodal = false;
    this.statuses = [
      { label: 'SAVED', value: 'SAVED' },
      { label: 'COMPLETED', value: 'COMPLETED' },
      { label: 'CANCELLED', value: 'CANCELLED' },
      { label: 'PAID', value: 'PAID' },
    ];
    this.status = {};
    this.getpages(10, 0);
  }

  getpages(row, page) {
    this.service
      .gettxpage(
        row,
        this.status != null && this.status.value != null
          ? this.status.value
          : null,
        this.rangeDates != undefined && this.rangeDates[0] != null
          ? this.rangeDates[0].toLocaleDateString()
          : null,
        this.rangeDates != undefined && this.rangeDates[1] != null
          ? this.rangeDates[1].toLocaleDateString()
          : null
      )
      .subscribe(
        (res) => {
          this.total = res.count;
          this.row = res.row;
          this.options = res.rowoptions;
          this.page = page;
          this.gettransactions(
            this.row,
            this.page,
            this.status != null && this.status.value != null
              ? this.status.value
              : null,
            this.rangeDates != undefined && this.rangeDates[0] != null
              ? this.rangeDates[0].toLocaleDateString()
              : null,
            this.rangeDates != undefined && this.rangeDates[1] != null
              ? this.rangeDates[1].toLocaleDateString()
              : null
          );
        },
        (err) => {
          this.tokenService.checkSession(err);
        }
      );
  }

  viewtx(data) {
    this.router.navigate(['/main/purchase/' + data.transactionid]);
  }

  gettransactions(row, page, status, from, to) {
    this.service.gettransactions(row, page, status, from, to).subscribe(
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

  paginate(event) {
    this.page = event.page;
    this.row = event.rows;
    this.gettransactions(
      this.row,
      this.page,
      this.status != null && this.status.value != null
        ? this.status.value
        : null,
      this.rangeDates != undefined && this.rangeDates[0] != null
        ? this.rangeDates[0].toLocaleDateString()
        : null,
      this.rangeDates != undefined && this.rangeDates[1] != null
        ? this.rangeDates[1].toLocaleDateString()
        : null
    );
  }

  changestatus() {
    this.getpages(this.row, this.page);
  }

  downloadreport() {
    this.service.generatereport(
      this.status != null && this.status.value != null
        ? this.status.value
        : null,
      this.rangeDates != undefined && this.rangeDates[0] != null
        ? this.rangeDates[0].toLocaleDateString()
        : null,
      this.rangeDates != undefined && this.rangeDates[1] != null
        ? this.rangeDates[1].toLocaleDateString()
        : null
    );
  }

  selectdate() {
    this.getpages(10, 0);
  }
}
