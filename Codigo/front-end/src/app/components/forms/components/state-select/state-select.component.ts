import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SelectValue} from '../../../../interfaces/SelectValue';

@Component({
  selector: 'app-state-select',
  templateUrl: './state-select.component.html',
  styleUrls: ['./state-select.component.css']
})
export class StateSelectComponent {

  @Input() homeState!: string;
  @Output() homeStateChange: EventEmitter<string> = new EventEmitter<string>();

  states: SelectValue[] = [
    { name: 'Acre', value: 'ACRE' },
    { name: 'Alagoas', value: 'ALAGOAS' },
    { name: 'Amapá', value: 'AMAPA' },
    { name: 'Amazonas', value: 'AMAZONAS' },
    { name: 'Bahia', value: 'BAHIA' },
    { name: 'Ceará', value: 'CEARA' },
    { name: 'Distrito Federal', value: 'DISTRITO_FEDERAL' },
    { name: 'Espírito Santo', value: 'ESPIRITO_SANTO' },
    { name: 'Goiás', value: 'GOIAS' },
    { name: 'Maranhão', value: 'MARANHAO' },
    { name: 'Mato Grosso', value: 'MATO_GROSSO' },
    { name: 'Mato Grosso do Sul', value: 'MATO_GROSSO_DO_SUL' },
    { name: 'Minas Gerais', value: 'MINAS_GERAIS' },
    { name: 'Pará', value: 'PARA' },
    { name: 'Paraíba', value: 'PARAIBA' },
    { name: 'Paraná', value: 'PARANA' },
    { name: 'Pernambuco', value: 'PERNAMBUCO' },
    { name: 'Piauí', value: 'PIAUI' },
    { name: 'Rio de Janeiro', value: 'RIO_DE_JANEIRO' },
    { name: 'Rio Grande do Norte', value: 'RIO_GRANDE_DO_NORTE' },
    { name: 'Rio Grande do Sul', value: 'RIO_GRANDE_DO_SUL' },
    { name: 'Rondônia', value: 'RONDONIA' },
    { name: 'Roraima', value: 'RORAIMA' },
    { name: 'Santa Catarina', value: 'SANTA_CATARINA' },
    { name: 'São Paulo', value: 'SAO_PAULO' },
    { name: 'Sergipe', value: 'SERGIPE' },
    { name: 'Tocantins', value: 'TOCANTINS' }
  ];

  onStateChange(event: any): void {
    this.homeState = event.target.value;
    this.homeStateChange.emit(this.homeState);
  }
}
