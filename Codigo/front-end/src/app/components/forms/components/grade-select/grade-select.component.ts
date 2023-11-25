import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SelectValue} from '../../../../interfaces/SelectValue';

@Component({
  selector: 'app-grade-select',
  templateUrl: './grade-select.component.html',
  styleUrls: ['./grade-select.component.css']
})
export class GradeSelectComponent {

  @Input() grade !: string;
  @Output() gradeChange: EventEmitter<string> = new EventEmitter<string>;

  grades: SelectValue[] = [
    {name: '1° Série', value: '1 Série'},
    {name: '2° Série', value: '2 Série'},
    {name: '3° Série', value: '3 Série'},
    {name: '4° Série', value: '4 Série'},
    {name: '5° Série', value: '5 Série'}
  ];

  onGradeChange(event: any): void {
    this.grade = event.target.value;
    this.gradeChange.emit(this.grade);
  }
}
