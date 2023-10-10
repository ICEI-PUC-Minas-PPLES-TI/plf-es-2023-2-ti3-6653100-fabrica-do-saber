import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from '@angular/router';

export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {

  const isAuthenticated: string | null = localStorage.getItem('AuthorizationToken');
  const router:Router = new Router()

  if (isAuthenticated)
    return true;
  else {
    router.navigate(['/login']);
    return false;
  }

};
