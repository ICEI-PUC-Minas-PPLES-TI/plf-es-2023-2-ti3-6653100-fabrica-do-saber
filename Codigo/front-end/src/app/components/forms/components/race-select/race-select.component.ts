import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SelectValue} from '../../../../interfaces/SelectValue';

@Component({
  selector: 'app-race-select',
  templateUrl: './race-select.component.html',
  styleUrls: ['./race-select.component.css']
})
export class RaceSelectComponent {

  @Input() race !: string;
  @Output() raceChange: EventEmitter<string> = new EventEmitter<string>();

  races: SelectValue[] = [
    {name: 'Amarelo', value: 'AMARELO'},
    {name: 'Branco', value: 'BRANCO'},
    {name: 'Indígena', value: 'INDIGENA'},
    {name: 'Pardo', value: 'PARDO'},
    {name: 'Preto', value: 'PRETO'},
    {name: 'Outra', value: 'OUTRA'},
    {name: 'Prefiro não declarar', value: 'PREFIRO_NAO_DECLARAR'}
  ];

  onRaceChange(event: any): void {
    this.race = event.target.value;
    this.raceChange.emit(this.race);
  }
}
