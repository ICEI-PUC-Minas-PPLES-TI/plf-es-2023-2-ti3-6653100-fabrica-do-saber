import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  menuItems = [
    {label: 'Início', link: '/'},
    {label: 'Alunos Cadastrados', link: '/student-list'},
    {label: 'Professores Cadastrados', link: '/teacher-list'},
    {label: 'Turmas Cadastradas', link: '/team-list'},
    {label: 'Creches de Férias Cadastradas', link: '/vacation-team-list'},
    {label: 'Página do usuário', link: '/user-page'},
    {label: 'Financeiro', link: '/transaction-list'}
  ];

  isMenuOpen: boolean = false;

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
