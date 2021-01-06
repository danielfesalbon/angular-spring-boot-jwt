import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BackendService {


  servicelink = environment.rest_url;

  constructor(private http: HttpClient) { }


  userlogin(username, password) {
    return this.http.post<any>(this.servicelink + '/authenticate/user', { username: username, password: password });
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
    return this.http.delete<any>(this.servicelink + '/product/delete/' + id)
  }

  getusers() {
    return this.http.get<any>(this.servicelink + '/user/list');
  }

  saveuser(user) {
    return this.http.post<any>(this.servicelink + '/user/save', user);
  }

  deleteuser(id) {
    return this.http.delete<any>(this.servicelink + '/user/delete/' + id)
  }

  searchproduct(productid) {
    return this.http.get<any>(this.servicelink + '/product/get/' + productid);
  }

  submitpurchase(purchase) {
    return this.http.post<any>(this.servicelink + '/purchase/submit', purchase);
  }

  getpurchases(row, page) {
    return this.http.get<any>(this.servicelink + '/purchase/list?row=' + row + '&page=' + page);
  }

  getpurchase(id) {
    return this.http.get<any>(this.servicelink + '/purchase/get/' + id);
  }

  submittransaction(transaction) {
    return this.http.post<any>(this.servicelink + '/transaction/submit', transaction);
  }

  gettransactions(row, page) {
    return this.http.get<any>(this.servicelink + '/transaction/list?row=' + row + '&page=' + page);
  }

  gettxpage(row) {
    return this.http.get<any>(this.servicelink + '/transaction/page/' + row);
  }

  getpurchasepage(row) {
    return this.http.get<any>(this.servicelink + '/purchase/page/' + row);
  }

}
