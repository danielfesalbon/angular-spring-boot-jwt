import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root',
})
export class BackendService {
  servicelink = environment.rest_url;

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  userlogin(username, password) {
    return this.http.post<any>(this.servicelink + '/authenticate/user', {
      username: username,
      password: password,
    });
  }

  saveproduct(product) {
    return this.http.post<any>(this.servicelink + '/product/save', product);
  }

  getproducts() {
    return this.http.get<any>(this.servicelink + '/product/list');
  }

  getproductfortx() {
    return this.http.get<any>(this.servicelink + '/product/order/list');
  }

  deleteproduct(id) {
    return this.http.delete<any>(this.servicelink + '/product/delete/' + id);
  }

  getusers() {
    return this.http.get<any>(this.servicelink + '/user/list');
  }

  saveuser(user) {
    return this.http.post<any>(this.servicelink + '/user/save', user);
  }

  deleteuser(id) {
    return this.http.delete<any>(this.servicelink + '/user/delete/' + id);
  }

  searchproduct(productid) {
    return this.http.get<any>(this.servicelink + '/product/get/' + productid);
  }

  submitpurchase(purchase) {
    return this.http.post<any>(this.servicelink + '/purchase/submit', purchase);
  }

  getpurchases(row, page) {
    return this.http.get<any>(
      this.servicelink + '/purchase/list?row=' + row + '&page=' + page
    );
  }

  getpurchase(id) {
    return this.http.get<any>(this.servicelink + '/transaction/get/' + id);
  }

  submittransaction(transaction) {
    return this.http.post<any>(
      this.servicelink + '/transaction/submit',
      transaction
    );
  }

  gettransactions(row, page, status, from, to) {
    return this.http.get<any>(
      this.servicelink +
        '/transaction/list?row=' +
        row +
        '&page=' +
        page +
        '&status=' +
        status +
        '&from=' +
        from +
        '&to=' +
        to
    );
  }

  gettxpage(row, status, from, to) {
    return this.http.get<any>(
      this.servicelink +
        '/transaction/page?row=' +
        row +
        '&status=' +
        status +
        '&from=' +
        from +
        '&to=' +
        to
    );
  }

  getpurchasepage(row) {
    return this.http.get<any>(this.servicelink + '/purchase/page/' + row);
  }

  updatetx(transaction) {
    return this.http.put<any>(
      this.servicelink + '/transaction/update',
      transaction
    );
  }

  getsettings() {
    return this.http.get<any>(this.servicelink + '/dashboard/settings');
  }

  generatereport(status, from, to) {
    window.open(
      this.servicelink +
        '/file/generate' +
        '?status=' +
        status +
        '&from=' +
        from +
        '&to=' +
        to +
        '&user=' +
        this.tokenService.getUser()
    );
  }

  validateresetpassword(user) {
    return this.http.post<any>(this.servicelink + '/user/validate/reset', user);
  }

  resetpassword(user) {
    return this.http.post<any>(this.servicelink + '/user/reset/password', user);
  }

  changepassword(user) {
    return this.http.post<any>(this.servicelink + '/user/changepassword', user);
  }
}
