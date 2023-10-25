import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from './auth.service';

export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {

  const authService: AuthService = new AuthService();
  const router: Router = new Router();

  if (authService.isAuthenticated())
    return true;
  else {
    router.navigate(['/login']);
    return false;
  }

};
