import { Component } from '@angular/core';

@Component({
  selector: 'app-race-select',
  templateUrl: './race-select.component.html',
  styleUrls: ['./race-select.component.css']
})
export class RaceSelectComponent {

  races: String[] = ['Amarelo', 'Branco', 'Indígena', 'Pardo', 'Preto', 'Outra', 'Prefiro não declarar'];

}
