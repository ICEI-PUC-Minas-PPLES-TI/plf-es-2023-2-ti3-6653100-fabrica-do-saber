import {Component} from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {


  menuItems = [
    {label: 'Página Inicial', link: '/'},
    {label: 'Alunos Cadastrados', link: '/student-list'},
    {label: 'Professores Cadastrados', link: '/teacher-list'},
    {label: 'Turmas Cadastrados', link: '/team-list'},
    {label: 'Cadastrar Usuário', link: '/user-create'},
    {label: 'Página do usuário', link: '/user-page'}
  ];

  isMenuOpen = false;

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
