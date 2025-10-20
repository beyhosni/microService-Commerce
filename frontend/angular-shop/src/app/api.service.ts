import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Injectable({ providedIn: 'root' })
export class ApiService {
  constructor(private http: HttpClient) {}
  products(){ return this.http.get<any[]>('/api/products'); }
  createOrder(order:any){ return this.http.post<any>('/api/orders', order); }
  pay(id:string){ return this.http.post<any>(`/api/orders/pay/${id}`, {}); }
}
