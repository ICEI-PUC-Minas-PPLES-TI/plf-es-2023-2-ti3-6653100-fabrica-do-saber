import { Component } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { StudentService } from "../../../services/student.service";
import { Student } from "../../../interfaces/Student";

@Component({
  selector: 'app-student-edit',
  templateUrl: './student-edit.component.html',
  styleUrls: ['./student-edit.component.css']
})

export class StudentEditComponent {

  student!: Student[];

  constructor(private route: ActivatedRoute, private studentService: StudentService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const id = parseInt(<string>params.get('id'));
      this.student = this.getStudentById(id);
    });
  }

  getStudentById(id: number) {
    return this.studentService.getStudentById(id);
  }

  updateStudent() {
    console.log("estudante atualizado!");
  }


}
