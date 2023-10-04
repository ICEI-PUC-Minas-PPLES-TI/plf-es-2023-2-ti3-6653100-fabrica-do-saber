import {Component} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title: string = 'front-end';
  showHeader: boolean = true;

  constructor(private router: Router) {
    router.events.subscribe((event): void => {
      if (event instanceof NavigationEnd) {
        this.showHeader = (event.url !== '/login');
      }
    });
  }
}
