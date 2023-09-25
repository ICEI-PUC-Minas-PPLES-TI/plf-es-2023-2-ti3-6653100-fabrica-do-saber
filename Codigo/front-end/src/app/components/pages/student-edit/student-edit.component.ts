import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {StudentService} from "../../../services/student.service";
import {Student} from "../../../interfaces/Student";
import {Guardian} from "../../../interfaces/Guardian";
import {ToastrService} from "ngx-toastr";

@Component({
    selector: 'app-student-edit',
    templateUrl: './student-edit.component.html',
    styleUrls: ['./student-edit.component.css']
})

export class StudentEditComponent {

    student!: Student;
    father!: Guardian;
    mother!: Guardian;

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
            [this.father, this.mother] = [this.student.guardians[0], this.student.guardians[1]];
        });
    }

    updateStudent() {
        console.log("estudante atualizado!");
    }

    cancel(): void {
        this.toastr.error('Ação cancelada');
        this.router.navigate(['/student-list']);
    }

}
