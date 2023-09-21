import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.css']
})
export class UserCreateComponent {

  constructor(private router: Router, private toastr: ToastrService) {}

  cancel() {
    this.toastr.error('Ação Cancelada');
    this.router.navigate(['/']);
  }

}
