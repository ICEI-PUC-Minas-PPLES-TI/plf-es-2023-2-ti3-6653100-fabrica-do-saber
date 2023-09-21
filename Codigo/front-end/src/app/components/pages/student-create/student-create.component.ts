import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-student-create',
  templateUrl: './student-create.component.html',
  styleUrls: ['./student-create.component.css']
})
export class StudentCreateComponent {

  states = [
    'Acre', 'Alagoas', 'Amapá', 'Amazonas', 'Bahia', 'Ceará', 'Distrito Federal',
    'Espírito Santo', 'Goiás', 'Maranhão', 'Mato Grosso', 'Mato Grosso do Sul',
    'Minas Gerais', 'Pará', 'Paraíba', 'Paraná', 'Pernambuco', 'Piauí',
    'Rio de Janeiro', 'Rio Grande do Norte', 'Rio Grande do Sul', 'Rondônia',
    'Roraima', 'Santa Catarina', 'São Paulo', 'Sergipe', 'Tocantins'
  ];

  races = ['Amarelo', 'Branco', 'Indígena', 'Pardo', 'Preto', 'Outra', 'Prefiro não declarar'];

  religions = ['Candomblé', 'Catolicismo', 'Espiritismo', 'Protestantismo', 'Umbanda', 'Outra', 'Não possui', 'Prefiro não declarar'];

  // Address Checkbox
  @ViewChild('showAddressCheckbox') showAddressCheckbox!: ElementRef<HTMLInputElement>;
  showAddressFields = true;

  toggleAddressFields() {
    this.showAddressFields = !this.showAddressCheckbox.nativeElement.checked;
  }
}
