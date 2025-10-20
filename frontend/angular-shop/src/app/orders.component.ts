import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from './api.service';

@Component({
  standalone: true,
  selector: 'orders',
  imports: [CommonModule, FormsModule],
  template: `
  <h2>Paiement</h2>
  <input placeholder="Order ID" [(ngModel)]="orderId">
  <button (click)="pay()">Payer</button>
  <div *ngIf="result" class="card">{{result | json}}</div>
  `
})
export class OrdersComponent {
  orderId = '';
  result: any;
  constructor(private api: ApiService){}
  pay(){ this.api.pay(this.orderId).subscribe(r => this.result = r); }
}
