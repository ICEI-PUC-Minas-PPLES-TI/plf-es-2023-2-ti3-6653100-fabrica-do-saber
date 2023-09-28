import {Component} from '@angular/core';
import {Teacher} from '../../../interfaces/Teacher';
import {ActivatedRoute, Router} from '@angular/router';
import {TeacherService} from '../../../services/teacher/teacher.service';

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
      this.getStudentById(id);
    });
  }

  getStudentById(id: number): void {
    this.teacherService.getTeacherById(id).subscribe((teacher: Teacher): void => {
      this.teacher = teacher;
      console.log(this.teacher);
    });
  }

  updateStudent() {
    this.teacherService.updateTeacher(this.teacherId, this.teacher).subscribe();
    this.router.navigate(['/teacher-list']);
  }

  cancel(): void {
    this.router.navigate(['/teacher-list']);
  }
}