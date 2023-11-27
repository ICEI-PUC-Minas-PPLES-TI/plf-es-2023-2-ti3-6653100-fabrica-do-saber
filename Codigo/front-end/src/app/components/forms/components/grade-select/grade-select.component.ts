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
    {name: '1° Série', value: 'PRIMEIRA_SERIE'},
    {name: '2° Série', value: 'SEGUNDA_SERIE'},
    {name: '3° Série', value: 'TERCEIRA_SERIE'},
    {name: '4° Série', value: 'QUARTA_SERIE'},
    {name: '5° Série', value: 'QUINTA_SERIE'}
  ];

  onGradeChange(event: any): void {
    this.grade = event.target.value;
    this.gradeChange.emit(this.grade);
  }
}
