import { Component } from '@angular/core';
import {User} from '../../../interfaces/User';
import {UserImp} from '../../../classes/user/user-imp';
import {UserService} from '../../../services/user/user.service';
import {catchError, tap} from 'rxjs';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  user: User = new UserImp();

  constructor(private userService: UserService, private router: Router) {
  }

  login(): void {
    console.log(this.user)
    this.userService.login(this.user)
      .pipe(
        tap((response): void => {
          this.router.navigate(['/']);
        }),
        catchError(err => {
          throw err;
        }))
      .subscribe();
  }
}
