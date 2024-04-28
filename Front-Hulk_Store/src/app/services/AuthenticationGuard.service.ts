import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateChild, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthenticationGuard implements CanActivateChild {
  constructor(private router: Router) {
  }

    canActivateChild(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean|UrlTree>|Promise<boolean|UrlTree>|boolean|UrlTree {
        const usuario = sessionStorage.getItem('usuario');
        if (usuario) {
        return true;
        }
        return this.router.parseUrl('/');
    }
}