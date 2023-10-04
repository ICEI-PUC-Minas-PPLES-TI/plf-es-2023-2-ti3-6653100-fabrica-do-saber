import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {StudentService} from '../../../services/student/student.service';
import {Student} from '../../../interfaces/Student';
import {Parent} from '../../../interfaces/Parent';
import {catchError, tap} from 'rxjs';

@Component({
  selector: 'app-student-edit',
  templateUrl: './student-edit.component.html',
  styleUrls: ['./student-edit.component.css']
})

export class StudentEditComponent {

  student!: Student;
  studentId: number = 0;
  parent00!: Parent;
  parent01!: Parent;

  constructor(private route: ActivatedRoute, private studentService: StudentService, private router: Router) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params): void => {
      this.studentId = parseInt(<string>params.get('id'));
      this.getStudentById(this.studentId);
    });
  }

  getStudentById(id: number): void {
    this.studentService.getStudentById(id).subscribe((student: Student): void => {
      /*todo: ajustar de acordo com mudancas do back-end*/
      if (student.team)
        student.team = {id: student.team.id};

      this.student = student;
      [this.parent00, this.parent01] = [this.student.parents[0], this.student.parents[1]];
    });
  }


  updateStudent(): void {
    this.studentService.updateStudent(this.studentId, this.student)
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
