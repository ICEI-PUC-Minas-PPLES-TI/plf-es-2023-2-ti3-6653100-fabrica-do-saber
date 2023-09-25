import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-religion-select',
  templateUrl: './religion-select.component.html',
  styleUrls: ['./religion-select.component.css']
})
export class ReligionSelectComponent {

  @Input() religion !: string;
  @Output() religionChange: EventEmitter<string> = new EventEmitter<string>();

  religions: string[] = ['Candomblé', 'Catolicismo', 'Espiritismo', 'Protestantismo', 'Umbanda', 'Outra', 'Não possui', 'Prefiro não declarar'];

  onReligionChange(event: any): void {
    this.religion = event.target.value;
    this.religionChange.emit(this.religion);
  }
}
