import {Component, Input} from '@angular/core';
import {Student} from "../../../interfaces/Student";

@Component({
  selector: 'app-student-form',
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent {

  @Input() student!: Student;
  @Input() title!: string;

  constructor() {
  }

  onHomeStateChange(newState: string): void {
    this.student.homeState = newState;
  }

  onReligionChange(newReligion: string): void {
    this.student.religion = newReligion;
    console.log(this.student.religion)
  }
}
