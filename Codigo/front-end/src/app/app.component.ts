import {Component} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'front-end';
  showHeader: boolean = true;

  // oculta o header na pagina de login, deixei desativado enquanto a autenticacao por JWT nao estiver concluida
  constructor(private router: Router) {
    router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // this.showHeader = (event.url == '/login');
      }
    });
  }
}
