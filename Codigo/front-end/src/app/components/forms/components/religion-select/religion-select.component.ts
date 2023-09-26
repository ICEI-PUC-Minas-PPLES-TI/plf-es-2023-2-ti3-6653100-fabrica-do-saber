import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SelectValue} from '../../../../interfaces/SelectValue';

@Component({
  selector: 'app-religion-select',
  templateUrl: './religion-select.component.html',
  styleUrls: ['./religion-select.component.css']
})
export class ReligionSelectComponent {

  @Input() religion !: string;
  @Output() religionChange: EventEmitter<string> = new EventEmitter<string>();

  religions: SelectValue[] = [
    { name: 'Candomblé', value: 'CANDOMBLE' },
    { name: 'Catolicismo', value: 'CATOLICISMO' },
    { name: 'Espiritismo', value: 'ESPIRITISMO' },
    { name: 'Protestantismo', value: 'PROTESTANTISMO' },
    { name: 'Umbanda', value: 'UMBANDA' },
    { name: 'Outra', value: 'OUTRA' },
    { name: 'Não possui', value: 'NAO_POSSUI' },
    { name: 'Prefiro não declarar', value: 'PREFIRO_NAO_DECLARAR' }
  ];


  onReligionChange(event: any): void {
    this.religion = event.target.value;
    this.religionChange.emit(this.religion);
  }
}
