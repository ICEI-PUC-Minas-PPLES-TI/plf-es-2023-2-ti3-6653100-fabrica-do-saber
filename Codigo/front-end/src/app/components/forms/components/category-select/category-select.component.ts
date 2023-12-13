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
    { name: 'Pagamento aos funcionários', value: 'PAYROLL' },
    { name: 'Despesas em infraestrutura', value: 'INFRASTRUCTURE_EXPENSE' },
    { name: 'Marketing institucional', value: 'INSTITUTIONAL_MARKETING' },
    { name: 'Projetos educacionais', value: 'EDUCATIONAL_PROJECTS' },
    { name: 'Custos administrativos', value: 'ADMINISTRATIVE_COSTS' },
    { name: 'Eventos escolares', value: 'SCHOOL_EVENTS' },
    { name: 'Serviços de manutenção', value: 'MAINTENANCE_SERVICES' },
    { name: 'Material escolar', value: 'EDUCATIONAL_MATERIAL' }
  ];

  onCategoryChange(event: any): void {
    this.category = event.target.value;
    this.categoryChange.emit(this.category);
  }
}
