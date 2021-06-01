import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router} from '@angular/router';
import {TokenStorageService} from './token-storage.service';

const TOKEN_KEY = 'AuthToken';
const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  constructor(public router: Router, public token: TokenStorageService) {
  }

  // canActivate(route: ActivatedRouteSnapshot): boolean {
  //   const expectedRole = route.data.expectedRole;
  //   const token = this.token.getToken();
  //   if (token == null) {
  //     window.alert('Please log in!');
  //     this.router.navigate(['main']);
  //   }
  //   if (sessionStorage.getItem(TOKEN_KEY)) {
  //     JSON.parse(sessionStorage.getItem(AUTHORITIES_KEY)).forEach(authority => {
  //       if (authority.authority !== expectedRole) {
  //         window.alert('You do not have the authority to access this page!');
  //         this.router.navigate(['main']);
  //         return false;
  //       }
  //     });
  //   }
  //   return true;
  // }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data.expectedRole;
    const expectedRoles = route.data.expectedRoles;
    const token = this.token.getToken();
    if (token == null) {
      window.alert('Please log in!');
      this.router.navigate(['main']);
    }
    let flag = false;
    if (sessionStorage.getItem(TOKEN_KEY)) {
      JSON.parse(sessionStorage.getItem(AUTHORITIES_KEY)).forEach(authority => {
        if (expectedRole !== undefined && expectedRole !== null) {
          if (authority.authority === expectedRole) {
            flag = true;
          }
        } else if (expectedRoles !== undefined && expectedRoles !== null && expectedRoles.length > 0) {
          expectedRoles.forEach(role => {
            if (authority.authority === role) {
              flag = true;
            }
          });
        }
      });
      if (!flag) {
        window.alert('Nemate pravo pristupa ovoj stranici!');
        this.router.navigate(['main']);
      }
    }
    return flag;
  }

}
