import {Component} from '@angular/core';
import {Teacher} from '../../../interfaces/Teacher';
import {ActivatedRoute, Router} from '@angular/router';
import {TeacherService} from '../../../services/teacher/teacher.service';
import {catchError, tap} from 'rxjs';

@Component({
  selector: 'app-teacher-edit',
  templateUrl: './teacher-edit.component.html',
  styleUrls: ['./teacher-edit.component.css']
})
export class TeacherEditComponent {

  teacher!: Teacher;
  teacherId: number = 0;

  constructor(private route: ActivatedRoute, private teacherService: TeacherService, private router: Router) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params): void => {
      const id: number = parseInt(<string>params.get('id'));
      this.teacherId = id;
      this.getTeacherById(id);
    });
  }

  getTeacherById(id: number): void {
    this.teacherService.getTeacherById(id).subscribe((teacher: Teacher): void => {
      this.teacher = teacher;
      console.log(this.teacher.salary);
    });
  }

  updateTeacher(): void {
    /*todo: melhorar tratamento de salario do professor*/
    this.teacher.salary = this.checkSalaryUpdate(this.teacher.salary);
    this.teacherService.updateTeacher(this.teacherId, this.teacher)
      .pipe(
        tap((response): void => {
        }),
        catchError(err => {
          throw err;
        }))
      .subscribe();
  }

  cancel(): void {
    this.router.navigate(['/teacher-list']);
  }

  checkSalaryUpdate(salary: any): any {
    return typeof salary === 'string' ? this.teacherService.formatCurrency(salary) : salary;
  }
}
