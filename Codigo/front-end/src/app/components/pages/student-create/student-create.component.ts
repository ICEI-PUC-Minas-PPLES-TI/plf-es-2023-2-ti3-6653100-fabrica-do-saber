import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {StudentService} from '../../../services/student/student.service';
import {StudentImp} from '../../../classes/student/student-imp';
import {Student} from '../../../interfaces/Student';
import {catchError, tap} from 'rxjs';

@Component({
  selector: 'app-student-create',
  templateUrl: './student-create.component.html',
  styleUrls: ['./student-create.component.css']
})
export class StudentCreateComponent {

  student: Student = new StudentImp();


  constructor(private router: Router, private studentService: StudentService) {
  }

  createStudent(): void {
    this.studentService.createStudent(this.student)
      .pipe(
        tap((response): void => {
          this.router.navigate(['/student-list']);
        }),
        catchError(err => {
          throw err;
        }))
      .subscribe();
  }

  cancel(): void {
    this.router.navigate(['/student-list']);
  }

}
