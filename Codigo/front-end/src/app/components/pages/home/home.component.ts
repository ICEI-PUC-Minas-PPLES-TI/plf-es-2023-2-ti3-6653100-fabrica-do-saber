import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  cards = [
    {
      title: 'Alunos',
      imageUrl: '../../../assets/img/ger-alunos.png',
      route: '/student-list'
    },
    {
      title: 'Funcionários',
      imageUrl: '../../../assets/img/funcionarios.png',
      route: '/teacher-list'
    },
    {
      title: 'Financeiro',
      imageUrl: '../../../assets/img/financeiro.png',
      route: '/transaction-list'
    },
    {
      title: 'Turmas',
      imageUrl: '../../../assets/img/turmas.png',
      route: '/team-list'
    },
    {
      title: 'Relatórios',
      imageUrl: '../../../assets/img/relatorios.png',
      route: '/reports'
    },
    {
      title: 'Creche de Férias',
      imageUrl: '../../../assets/img/ferias.png',
      route: '/vacation-team-list'
    }
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
