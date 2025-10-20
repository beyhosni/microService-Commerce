import { Routes } from '@angular/router';
import { ProductListComponent } from './product-list.component';
import { CartComponent } from './cart.component';
import { OrdersComponent } from './orders.component';
export const routes: Routes = [
  { path: '', component: ProductListComponent },
  { path: 'cart', component: CartComponent },
  { path: 'orders', component: OrdersComponent }
];
