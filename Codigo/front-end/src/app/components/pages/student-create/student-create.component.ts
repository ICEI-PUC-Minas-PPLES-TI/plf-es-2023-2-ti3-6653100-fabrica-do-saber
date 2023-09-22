import {Component, ElementRef, ViewChild} from '@angular/core';

@Component({
  selector: 'app-student-create',
  templateUrl: './student-create.component.html',
  styleUrls: ['./student-create.component.css']
})
export class StudentCreateComponent {
  constructor() {
  }

  @ViewChild('showAddressCheckbox') showAddressCheckbox!: ElementRef<HTMLInputElement>;

}
