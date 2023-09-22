import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-custom-button',
  templateUrl: './custom-button.component.html',
  styleUrls: ['./custom-button.component.css']
})
export class CustomButtonComponent {

  @Input() type: string = 'button';
  @Input() class: string = '';
  @Input() innerHtml: string = '';
  @Output() click: EventEmitter<void> = new EventEmitter<void>();

  onClick(): void {
    this.click.emit();
  }
}
