import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../../../interfaces/User';
import { UserService } from '../../../services/user/user.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent {

  user!: User;

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
    this.findCurrentUser();
  }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/login']);
  }

  findCurrentUser(): void {
    this.userService.findCurrentUser().subscribe((user: User): void => {
      this.user = user;
    });
  }
}
