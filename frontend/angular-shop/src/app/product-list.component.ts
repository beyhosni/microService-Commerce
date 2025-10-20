import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from './api.service';
@Component({ standalone: true, selector: 'product-list', imports: [CommonModule],
  template: `<h2>Produits</h2><div class="row">
  <div class="col" *ngFor="let p of products">
    <div class="card"><div><strong>{{p.name}}</strong></div>
    <div>SKU: {{p.sku}}</div><div>Prix: {{p.price}}</div><div>Stock: {{p.stock}}</div>
    <button (click)="add(p)">Ajouter au panier</button></div></div></div>` })
export class ProductListComponent {
  api = inject(ApiService); products:any[]=[];
  ngOnInit(){ this.api.products().subscribe(x=>this.products=x); }
  add(p:any){ const k='cart'; const cart=JSON.parse(localStorage.getItem(k)||'[]'); cart.push({sku:p.sku,qty:1}); localStorage.setItem(k, JSON.stringify(cart)); alert('Ajouté'); }
}
