import { Component } from '@angular/core';
import {UserService} from '../../../services/user/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent {

  constructor(private userService: UserService, private router: Router) {
  }

  logout():void {
    this.userService.logout();
    this.router.navigate(['/login']);
  }
}
