import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {StudentService} from '../../../services/student/student.service';
import {Student} from '../../../interfaces/Student';
import {Parent} from '../../../interfaces/Parent';
import {ToastrService} from 'ngx-toastr';

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
      const id: number = parseInt(<string>params.get('id'));
      this.studentId = id;
      this.getStudentById(id);
      console.log();
    });
  }

  getStudentById(id: number): void {
    this.studentService.getStudentById(id).subscribe((student: Student): void => {
      this.student = student;
      [this.parent00, this.parent01] = [this.student.parents[0], this.student.parents[1]];
      console.log(student);
    });
  }

  updateStudent() {
    this.studentService.updateStudent(this.studentId, this.student).subscribe();
    this.router.navigate(['/student-list']);
  }

  cancel(): void {
    this.router.navigate(['/student-list']);
  }

}
