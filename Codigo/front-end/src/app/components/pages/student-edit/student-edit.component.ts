import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {StudentService} from "../../../services/student.service";
import {Student} from "../../../interfaces/Student";

@Component({
  selector: 'app-student-edit',
  templateUrl: './student-edit.component.html',
  styleUrls: ['./student-edit.component.css']
})

export class StudentEditComponent {

  student!: Student;

  constructor(private route: ActivatedRoute, private studentService: StudentService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params): void => {
      const id: number = parseInt(<string>params.get('id'));
      this.getStudentById(id);
    });
  }

  getStudentById(id: number): void {
    this.studentService.getStudentById(id).subscribe((student: Student): void => {
      this.student = student;
    });
  }

  updateStudent() {
    console.log("estudante atualizado!");
  }


}
