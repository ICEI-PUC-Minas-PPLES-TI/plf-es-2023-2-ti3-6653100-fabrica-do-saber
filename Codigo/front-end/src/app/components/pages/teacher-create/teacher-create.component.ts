import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { catchError, tap } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

import { TeacherService } from '../../../services/teacher/teacher.service';
import { Teacher } from '../../../interfaces/Teacher';
import { TeacherImp } from '../../../classes/teacher/teacher-imp';

@Component({
  selector: 'app-teacher-create',
  templateUrl: './teacher-create.component.html',
  styleUrls: ['./teacher-create.component.css']
})
export class TeacherCreateComponent {

  teacher: Teacher = new TeacherImp();

  constructor(private router: Router, private toastr: ToastrService, private teacherService: TeacherService) {
  }

  createTeacher(): void {
    this.teacher.salary = this.teacherService.formatCurrency(this.teacher.salary);
    let op: boolean = confirm('Deseja criar o professor?');
    if (op)
      this.teacherService.createTeacher(this.teacher)
        .pipe(
          tap((response): void => {
            this.router.navigate(['/teacher-list']);
          }),
          catchError(err => {
            throw err;
          })
        )
        .subscribe();
  }

  cancel(): void {
    this.router.navigate(['/teacher-list']);
  }

}
