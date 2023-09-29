import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {User} from '../../../interfaces/User';
import {UserService} from '../../../services/user/user.service';

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.css']
})
export class UserCreateComponent {

  user: User = {fullName: '', email: '', password: '', createDate: '25/10/2023'};

  constructor(private router: Router, private toastr: ToastrService, private userService: UserService) {
  }

  createUser() {
    this.userService.createUser(this.user).subscribe();
  }

  cancel() {
    this.router.navigate(['/']);
  }

}
