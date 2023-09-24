import { Component } from '@angular/core';

@Component({
  selector: 'app-religion-select',
  templateUrl: './religion-select.component.html',
  styleUrls: ['./religion-select.component.css']
})
export class ReligionSelectComponent {

  religions: String[] = ['Candomblé', 'Catolicismo', 'Espiritismo', 'Protestantismo', 'Umbanda', 'Outra', 'Não possui', 'Prefiro não declarar'];

}
