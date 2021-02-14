import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css'],
})
export class InventoryComponent implements OnInit {
  constructor(
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService,
    private sanitizer: DomSanitizer
  ) {}

  products: any[];
  productmodal: boolean;
  product: any;
  onview: boolean;
  path: any;
  uploadedFiles: any[] = [];
  mult: boolean;

  ngOnInit(): void {
    this.mult = false;
    this.product = {};
    this.productmodal = false;
    this.onview = false;
    this.getpath();
    this.getallproduct();
  }

  transform(imagestring) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(imagestring);
  }

  getallproduct() {
    this.service.getproducts().subscribe(
      (res) => {
        this.products = res;
      },
      (err) => {
        this.tokenService.checkSession(err);
      }
    );
  }

  getpath() {
    this.service.getsettings().subscribe(
      (res) => {
        this.path = res.imgpath;
      },
      (err) => {
        this.tokenService.checkSession(err);
      }
    );
  }

  newproduct() {
    this.onview = false;
    this.product = {};
    this.product.active = false;
    this.product.stock = 0;
    this.product.lastmax = 0;
    this.productmodal = true;
    this.uploadedFiles = [];
  }

  myUploader(event, product) {
    for (let file of event.files) {
      this.uploadedFiles.push(file);
    }
    this.pushFile(this.uploadedFiles[0], product);
    //event.files == files to upload
  }

  toBase64 = (file) =>
    new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = (error) => reject(error);
    });

  async pushFile(f: File, product) {
    const file = f;
    const result = await this.toBase64(file).catch((e) => Error(e));
    let reqbody = {
      base64: result,
      filename: file.name,
      productid: product.productid,
    };
    this.service.saveimage(reqbody).subscribe(
      (res) => {
        if (res.flag == 'success') {
          this.getallproduct();
          this.productmodal = false;
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
    if (result instanceof Error) {
      this.messageService.add({
        key: 'bc',
        severity: 'error',
        summary: 'Failed',
        detail: result.message,
      });
      return;
    }
  }

  saveproduct() {
    this.confirmationService.confirm({
      message: 'Save product details.',
      accept: () => {
        this.product.declaredprice = +this.product.declaredprice;
        this.product.stock = +this.product.stock;
        this.service.saveproduct(this.product).subscribe(
          (res) => {
            if (res.flag == 'success') {
              this.getallproduct();
              this.productmodal = false;
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
      },
    });
  }

  deleteproduct(id) {
    this.confirmationService.confirm({
      message: 'Delete product.',
      accept: () => {
        this.product.declaredprice = +this.product.declaredprice;
        this.service.deleteproduct(id).subscribe(
          (res) => {
            if (res.flag == 'success') {
              this.getallproduct();
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
      },
    });
  }

  viewproduct(data) {
    this.onview = true;
    this.product = data;
    this.productmodal = true;
    this.uploadedFiles = [];
  }
}
