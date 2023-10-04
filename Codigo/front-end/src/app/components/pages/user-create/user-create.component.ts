import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {User} from '../../../interfaces/User';
import {UserService} from '../../../services/user/user.service';
import {UserImp} from '../../../classes/user/user-imp';

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.css']
})
export class UserCreateComponent {

  user: User = new UserImp();

  constructor(private router: Router, private toastr: ToastrService, private userService: UserService) {
  }

  createUser() {
    this.userService.createUser(this.user).subscribe();
  }

  cancel() {
    this.router.navigate(['/']);
  }

}
