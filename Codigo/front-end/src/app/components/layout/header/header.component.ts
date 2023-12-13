import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  menuItems = [
    {label: 'Início', link: '/'},
    {label: 'Alunos', link: '/student-list'},
    {label: 'Professores', link: '/teacher-list'},
    {label: 'Turmas', link: '/team-list'},
    {label: 'Creches de Férias', link: '/vacation-team-list'},
    {label: 'Financeiro', link: '/transaction-list'},
    {label: 'Página do usuário', link: '/user-page'},
    
  ];

  isMenuOpen: boolean = false;

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
