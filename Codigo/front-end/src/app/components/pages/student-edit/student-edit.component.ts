import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {StudentService} from "../../../services/student/student.service";
import {Student} from "../../../interfaces/Student";
import {Parent} from "../../../interfaces/Parent";
import {ToastrService} from "ngx-toastr";

@Component({
    selector: 'app-student-edit',
    templateUrl: './student-edit.component.html',
    styleUrls: ['./student-edit.component.css']
})

export class StudentEditComponent {

    student!: Student;
    father!: Parent;
    mother!: Parent;

    constructor(private route: ActivatedRoute, private studentService: StudentService, private router: Router, private toastr: ToastrService) {
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
            [this.father, this.mother] = [this.student.parents[0], this.student.parents[1]];
        });
    }

    updateStudent() {
        console.log("estudante atualizado!");
    }

    cancel(): void {
        this.toastr.error('Acao cancelada');
        this.router.navigate(['/student-list']);
    }

}
