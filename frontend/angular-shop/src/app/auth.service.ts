import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
@Injectable({ providedIn: 'root' })
export class AuthService {
  private kc = new Keycloak({ url: 'http://localhost:8085', realm: 'ecommerce', clientId: 'gateway' });
  constructor(){ this.kc.init({ onLoad: 'check-sso' }); }
  authenticated(){ return this.kc.authenticated ?? false; }
  login(){ this.kc.login(); }
  logout(){ this.kc.logout({ redirectUri: window.location.origin }); }
}
