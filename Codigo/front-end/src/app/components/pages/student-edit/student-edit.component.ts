import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Component } from '@angular/core';

import { catchError, tap } from 'rxjs';

import { Student } from '../../../interfaces/Student';
import { StudentService } from '../../../services/student/student.service';
import { Parent } from '../../../interfaces/Parent';
import { Team } from '../../../interfaces/Team';
import { TeamService } from '../../../services/team/team.service';


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

  constructor(private route: ActivatedRoute, private studentService: StudentService, private router: Router, private teamService: TeamService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap): void => {
      this.studentId = parseInt(<string>params.get('id'));
      this.getStudentById(this.studentId);
    });
  }

  getStudentById(id: number): void {
    this.studentService.getStudentById(id).subscribe((student: Student): void => {

      // if (student.team)
      //   student.team = { id: student.team.id };

      this.student = student;
      [this.parent00, this.parent01] = [this.student.parents[0], this.student.parents[1]];
    });
  }

  updateStudent(): void {
    let op: boolean = confirm('Deseja atualizar o aluno?');
    console.log(this.student)
    if (op) {
      this.studentService.updateStudent(this.studentId, this.student)
        .pipe(
          tap((response): void => {
            this.router.navigate(['/student-list']);
          }),
          catchError(err => {
            throw err;
          }))
        .subscribe();
      this.router.navigate(['/student-list']);
    }
  }

  cancel(): void {
    this.router.navigate(['/student-list']);
  }

}
