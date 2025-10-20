import { Component, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from './auth.service';
@Component({
  selector: 'app-root', standalone: true, imports: [RouterOutlet, RouterLink],
  template: `<header><strong>Angular Shop</strong>
    <a routerLink="/">Produits</a><a routerLink="/cart">Panier</a><a routerLink="/orders">Commandes</a>
    <span style="margin-left:auto"></span>
    <button *ngIf="!auth.authenticated()" (click)="auth.login()">Login</button>
    <button *ngIf="auth.authenticated()" (click)="auth.logout()">Logout</button>
  </header><div class="container"><router-outlet></router-outlet></div>`
}) export class AppComponent { auth = inject(AuthService); }
