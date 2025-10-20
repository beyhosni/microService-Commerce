import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from './api.service';
@Component({ standalone:true, selector:'cart', imports:[CommonModule],
  template:`<h2>Panier</h2>
  <div class="card" *ngFor="let i of items; let idx = index">{{i.sku}} x {{i.qty}} <button (click)="remove(idx)">Retirer</button></div>
  <button (click)="checkout()" [disabled]="!items.length">Commander</button>`})
export class CartComponent {
  items:any[]=JSON.parse(localStorage.getItem('cart')||'[]');
  constructor(private api:ApiService){}
  remove(idx:number){ this.items.splice(idx,1); localStorage.setItem('cart', JSON.stringify(this.items)); }
  checkout(){ const order={userId:'u-123', items:this.items}; this.api.createOrder(order).subscribe(o=>{ localStorage.removeItem('cart'); alert('Commande: '+o.orderId); }); }
}
