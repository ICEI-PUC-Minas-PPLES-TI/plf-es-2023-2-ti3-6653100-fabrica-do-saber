import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-btn-custom',
  templateUrl: './btn-custom.component.html',
  styleUrls: ['./btn-custom.component.css']
})
export class BtnCustomComponent {

  @Input() type: string = 'button';
  @Input() class: string = '';
  @Input() innerHtml: string = '';

  

}
