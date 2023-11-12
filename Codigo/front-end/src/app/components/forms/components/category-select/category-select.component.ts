import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SelectValue } from '../../../../interfaces/SelectValue';

@Component({
  selector: 'app-category-select',
  templateUrl: './category-select.component.html',
  styleUrls: ['./category-select.component.css']
})
export class CategorySelectComponent {

  @Input() category !: string;
  @Output() categoryChange: EventEmitter<string> = new EventEmitter<string>();

  categories: SelectValue[] = [
    {name: 'Pagamento funcionário', value: 'PAGAMENTO_FUNCIONARIO'},
    {name: 'Despesa infraestrutura', value: 'DESPESA_INFRAESTRUTURA'},
    {name: 'Marketing institucional', value: 'MARKETING_INSTITUCIONAL'},
    {name: 'Projetos pedagógicos', value: 'PROJETOS_PEDAGOGICOS'},
    {name: 'Custos administrativos', value: 'CUSTOS_ADMINISTRATIVOS'},
    {name: 'Eventos escolares', value: 'EVENTOS_ESCOLARES'},
    {name: 'Serviços de manutenção', value: 'SERVICOS_MANUTENCAO'},
    {name: 'Compra de material', value: 'COMPRA_MATERIAL'}
  ];

  onCategoryChange(event: any): void {
    this.category = event.target.value;
    this.categoryChange.emit(this.category);
  }
}
