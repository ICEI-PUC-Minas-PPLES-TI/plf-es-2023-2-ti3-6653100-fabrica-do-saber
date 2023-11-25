import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SelectValue } from '../../../../interfaces/SelectValue';

@Component({
  selector: 'app-formation-select',
  templateUrl: './formation-select.component.html',
  styleUrls: ['./formation-select.component.css']
})
export class FormationSelectComponent {

  @Input() academicFormationStatus !: string;
  @Output() academicFormationStatusChange: EventEmitter<string> = new EventEmitter<string>;

  academicFormationStatusArr: SelectValue[] = [
    {name: 'Cursando', value: 'Cursando'},
    {name: 'Concluído', value: 'Concluído'}
  ];

  onAcademicFormationStatusChange(event: any): void {
    this.academicFormationStatus = event.target.value;
    this.academicFormationStatusChange.emit(this.academicFormationStatus);
  }

}
